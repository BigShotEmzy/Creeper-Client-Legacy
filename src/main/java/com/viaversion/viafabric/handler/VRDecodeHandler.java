package com.viaversion.viafabric.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Sharable
public class VRDecodeHandler extends MessageToMessageDecoder {
   public UserConnection info;
   public boolean handledCompression;
   public boolean skipDoubleTransform;

   public void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List out) throws Exception {
      Object var10000 = null;
      if (this.skipDoubleTransform) {
         this.skipDoubleTransform = false;
         out.add(bytebuf.retain());
      } else if (!this.info.checkIncomingPacket()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.info.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
            if (needsCompress) {
               CommonTransformer.compress(ctx, transformedBuf);
               this.skipDoubleTransform = true;
            }

            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }

      }
   }

   public void decode(ChannelHandlerContext var1, Object var2, List var3) throws Exception {
      Object var10000 = null;
      this.decode(var1, (ByteBuf)var2, var3);
   }

   public VRDecodeHandler(UserConnection info) {
      this.info = info;
   }

   public UserConnection getInfo() {
      Object var10000 = null;
      return this.info;
   }

   public boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
      Object var10000 = null;
      if (this.handledCompression) {
         return false;
      } else {
         int decoderIndex = ctx.pipeline().names().indexOf("decompress");
         if (decoderIndex == -1) {
            return false;
         } else {
            this.handledCompression = true;
            if (decoderIndex > ctx.pipeline().names().indexOf("via-decoder")) {
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

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      Object var10000 = null;
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
