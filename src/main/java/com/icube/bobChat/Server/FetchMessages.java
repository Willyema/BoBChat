package com.icube.bobChat.Server;

import com.icube.bobChat.UI.ChatUI;
import de.leonhard.storage.Json;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.Channel;
import java.util.TimerTask;

public class FetchMessages extends TimerTask {
    private int i;
    private Json settings = new Json("settings", System.getProperty("user.home") + "\\BoBChat\\Data");
    private static FetchMessages instance = null;
    
    private FetchMessages(){
        
    }

    public static synchronized FetchMessages getInstance() {
        if (instance == null) {
            instance = new FetchMessages();
        }
        return instance;
    }

    public void run() {
        ChatUI ui = ChatUI.getInstance();
        try {
            ui.cleanChat();
            EchoClient ec = new EchoClient(settings.getString("serverIP"));
            ec.sendEcho("update");
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("runs");
        for (String response : ProcessResponse.s) {
            System.out.println("response "+response);
            String[] a = response.split(",");
            response = a[0] + ":";
            System.out.println("response ewe "+ response);
            i = 0;
            while (i <= a.length) {
                if (i != 1) {
                    if (i != 0) {
                        response = response + a[i - 1];
                        System.out.println("response uwu "+ response);
                    }
                }
                ProcessResponse.s.remove(response);
                i++;
            }
            ui.updateAndRepaintFrame(response);
        }
    }
}
