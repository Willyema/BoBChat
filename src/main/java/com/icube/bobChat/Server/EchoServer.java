package com.icube.bobChat.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class EchoServer extends Thread{
    Logger l = LogManager.getRootLogger();
    private DatagramSocket socket;
    public boolean running;
    private byte[] buf = new byte[256];

    public EchoServer() {
        try {
            socket = new DatagramSocket(1419);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        running = true;
        ProcessResponse pc = new ProcessResponse();
        try {
            l.debug("Starting server...");
            while (running) {
                buf = new byte[8024];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String oldReceived = new String(packet.getData(), 0, packet.getLength());
                String received = getMessage(oldReceived);
                pc.Process(received);
            }
        } catch (Exception e) {
            l.error("Error in server");
            for (StackTraceElement el : e.getStackTrace()) {
                l.error(el.toString());
            }
            e.printStackTrace();
        }
        socket.close();
    }
    private String getMessage(String msg){
        int chars = 0;
        String splitMessage = "";
        if (isNumeric(String.valueOf(msg.charAt(3)))){
            chars = Integer.parseInt(msg.substring(0,4));
            l.debug("Message has 4 digits");
        } else if (isNumeric(String.valueOf(msg.charAt(2)))){
            chars = Integer.parseInt(msg.substring(0,3));
            l.debug("Message has 3 digits");
        } else if (isNumeric(String.valueOf(msg.charAt(1)))){
            chars = Integer.parseInt(msg.substring(0,2));
            l.debug("Message has 2 digits");
        } else {
            chars = Integer.parseInt(msg.substring(0,1));
            l.warn("Assuming message has 1 digit");
        }
        splitMessage = msg.substring(5,chars+5);
        return splitMessage;
    }

    private static boolean isNumeric(String string) {
        int intValue;
        Logger l = LogManager.getRootLogger();

        if(string == null || string.equals("")) {
            l.debug("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            l.debug("Input String cannot be parsed to Integer.");
        }
        return false;
    }
}