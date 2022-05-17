package com.icube.bobChat;

import com.icube.bobChat.Logging.LoggerManager;
import com.icube.bobChat.Server.EchoServer;
import com.icube.bobChat.UI.LoginGUI;
import de.leonhard.storage.Json;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Main {
    public static void main(String[] args) {
        Json settings = new Json("settings", System.getProperty("user.home") + "\\BoBChat\\Data");
        System.out.println("Instantiating logger");
        settings.remove("serverIP");
        LoggerManager lm = new LoggerManager();
        lm.setUpLogger();
        try {
            System.out.println("Thread sleepin");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger l = LogManager.getRootLogger();
        try{
            LoginGUI g = new LoginGUI();
            System.out.println("Works here");
            g.createUI();
            System.out.println("Works here");
            EchoServer es = new EchoServer();
            System.out.println("Works here");
            es.run();
            System.out.println("Works here");
        } catch (Exception e) {
         l.error("Error in starting gui! "+e.getMessage());
         for (StackTraceElement el : e.getStackTrace()){
             l.error(el.toString());
         }
        }
    }
}