package me.spruce.creeperclient.gui.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.spruce.creeperclient.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;

public final class AltLoginThread extends Thread {
    private final String password;
    private final String username;
    private final Minecraft mc = Minecraft.getMinecraft();

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        Client.status = ChatFormatting.GRAY + "Waiting...";
    }

    private YggdrasilUserAuthentication createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        return auth;
    }

    public String getStatus() {
        return Client.status;
    }

    public void run() {
        if (this.password.equals("")) {
            Client.IMC.setSession(new Session(this.username, "", "", "mojang"));
            Client.status = ChatFormatting.GREEN + "Logged in as " + this.username + " (offline)";
        } else {
            Client.status = ChatFormatting.YELLOW + "Logging in...";
            YggdrasilUserAuthentication auth = this.createSession(this.username, this.password);

            try {
                auth.logIn();
                Session session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                Client.status = ChatFormatting.GREEN + "Logged in as " + session.getUsername();
                Client.IMC.setSession(session);
                Client.savedAlts.put(username + ":" + password, session);
            } catch (AuthenticationException var6) {
                if (var6.getMessage().contains("Invalid username or password.") || var6.getMessage().toLowerCase().contains("account migrated")) {
                    Client.status = ChatFormatting.RED + "Invalid username or password!";
                } else {
                    Client.status = ChatFormatting.RED + "Login failed!";
                }
                var6.printStackTrace();
            }
        }
    }

    public void setStatus(String status) {
        Client.status = status;
    }
}
