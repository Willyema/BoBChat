package com.icube.bobChat.Server;

import com.icube.bobChat.UI.ChatUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Timer;

public class ProcessResponse {
    public static ArrayList<String> s = new ArrayList<String>();
    public static String lastMessage = new String();
    static Logger l = LogManager.getRootLogger();
    private static boolean chatUI = false;
    static ChatUI cui = ChatUI.getInstance();
    public static void Process(String msg){
        lastMessage = msg;
        if (chatUI == true) {
            if (msg.contains(",")) {
                s.add(msg);
            }
            if (s.toArray().length > 15){
                s.remove(14);
                String s3 = "";
                for (String s2 : s) {
                    s3 = s3 + s2 + System.lineSeparator();
                }
                cui.textPane1.setText(s3);
            }
        } else {
            if (s.toArray().length >= 3) {
                s.remove(0);
            }
        }
        System.out.println(s);
        l.info(msg);
    }
    public static void setChatUI(boolean b){
        chatUI = b;
        s = new ArrayList<String>();
        Timer t = new Timer();
        t.scheduleAtFixedRate(FetchMessages.getInstance(), 0, 5000);
    }
    public static void cleanMessages(){
        s = new ArrayList<>();
    }
}
