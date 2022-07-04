package com.icube.bobChat.Server;

import com.icube.bobChat.UI.ChatUI;
import de.leonhard.storage.Json;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class FetchMessages extends TimerTask {
    private int i;
    private Json settings = new Json("settings", System.getProperty("user.home") + "\\BoBChat\\Data");
    Logger l = LogManager.getRootLogger();
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
        for (String response : ProcessResponse.s) {
            String[] a = response.split(",");
            response = a[0] + ":";
            i = 0;
            while (i <= a.length) {
                if (i != 1) {
                    if (i != 0) {
                        response = response + a[i - 1];
                    }
                }
                ProcessResponse.s.remove(response);
                i++;
            }
            ui.updateAndRepaintFrame(response);
        }
    }
}
