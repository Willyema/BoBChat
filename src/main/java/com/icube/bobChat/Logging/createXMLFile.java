package com.icube.bobChat.Logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class createXMLFile {
    public InputStream createXMLFile(){
        ConfigurationBuilder<BuiltConfiguration> builder
                = ConfigurationBuilderFactory.newConfigurationBuilder();
        AppenderComponentBuilder file
                = builder.newAppender("log", "Console");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        //file.addAttribute("fileName", System.getProperty("user.home")+"\\BoBChat\\Logs\\Log-"+dtf.format(now)+"\\log.log");

        LayoutComponentBuilder standard
                = builder.newLayout("PatternLayout");
        standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        file.add(standard);
        builder.add(file);
        RootLoggerComponentBuilder rootLogger
                = builder.newRootLogger(Level.ALL);
        rootLogger.add(builder.newAppenderRef("log"));

        builder.add(rootLogger);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            builder.writeXmlConfiguration(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = new ByteArrayInputStream(out.toByteArray());
        Configurator.initialize(builder.build());
        return is;
    }
}
