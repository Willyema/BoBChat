package com.icube.bobChat.Logging;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class LoggerManager {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    File masterFolder = new File(System.getProperty("user.home")+"\\BoBChat\\");

    public void setUpLogger(){
        createLoggerFolder();
        try {
            createProprietiesFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createLoggerFolder() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        File f = new File(masterFolder.getPath()+"\\Logs\\Log-"+dtf.format(now));
        File f2 = new File(f.getPath()+"\\log.log");
        System.out.println(f2.getAbsolutePath());
        f.mkdirs();
        try {
            f2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProprietiesFile() throws FileNotFoundException {
        File f = new File(System.getProperty("user.home")+"\\BoBChat\\Settings\\");
        File f2 = new File(f.getPath()+"\\log4j2.xml");
        f2.delete();
        Properties prop = new Properties();
        createXMLFile xml = new createXMLFile();
        InputStream is = xml.createXMLFile();
        InputStream input = null;
        try {
            Path temp = Files.createTempFile("log4j2", ".xml");
            Files.copy(is, temp, StandardCopyOption.REPLACE_EXISTING);
            input = new FileInputStream(temp.toFile());
            f.mkdirs();
            f2.createNewFile();
        } catch(Exception e){
            e.printStackTrace();
        }
        InputStream is2 = null;
        try {
            copyInputStreamToFile(input, f2);
            is2 = new FileInputStream(f2);
            prop.load(is2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

}
