package com.viaversion.viafabric.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Sharable
public class VREncodeHandler extends MessageToMessageEncoder {
   public UserConnection info;
   public boolean handledCompression;

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      Object var10000 = null;
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
      }
   }

   public boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
      Object var10000 = null;
      if (this.handledCompression) {
         return false;
      } else {
         int encoderIndex = ctx.pipeline().names().indexOf("compress");
         if (encoderIndex == -1) {
            return false;
         } else {
            this.handledCompression = true;
            if (encoderIndex > ctx.pipeline().names().indexOf("via-encoder")) {
               CommonTransformer.decompress(ctx, buf);
               ChannelHandler encoder = ctx.pipeline().get("via-encoder");
               ChannelHandler decoder = ctx.pipeline().get("via-decoder");
               ctx.pipeline().remove(encoder);
               ctx.pipeline().remove(decoder);
               ctx.pipeline().addAfter("compress", "via-encoder", encoder);
               ctx.pipeline().addAfter("decompress", "via-decoder", decoder);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void encode(ChannelHandlerContext var1, Object var2, List var3) throws Exception {
      Object var10000 = null;
      this.encode(var1, (ByteBuf)var2, var3);
   }

   public void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List out) throws Exception {
      Object var10000 = null;
      if (!this.info.checkOutgoingPacket()) {
         throw CancelEncoderException.generate(null);
      } else if (!this.info.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            if (needsCompress) {
               CommonTransformer.compress(ctx, transformedBuf);
            }

            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }

      }
   }

   public VREncodeHandler(UserConnection info) {
      this.info = info;
   }
}
