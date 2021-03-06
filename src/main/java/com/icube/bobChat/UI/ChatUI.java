package com.icube.bobChat.UI;

import com.icube.bobChat.Server.EchoClient;
import com.icube.bobChat.Server.ProcessResponse;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import de.leonhard.storage.Json;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.Locale;

public class ChatUI {
    private JPanel p;
    private JButton sendButton;
    private JTextField messageToSendTextField;
    public JTextPane textPane1;
    private JButton quitButton;
    private JLabel rvl;
    private JFrame f = new JFrame("BoBChat");
    private EchoClient ec;
    private Json settings = new Json("settings", System.getProperty("user.home") + "\\BoBChat\\Data");
    private Logger l = LogManager.getRootLogger();
    private static ChatUI istanza = null;

    private ChatUI() {
    }

    public static synchronized ChatUI getInstance() {
        if (istanza == null) {
            istanza = new ChatUI();
        }
        return istanza;
    }


    public void createUI() {
        try {
            DefaultCaret caret = (DefaultCaret) textPane1.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            f.add(p);
            registerListeners();
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setBounds(100, 100, 300, 500);
            f.setResizable(false);
            f.setVisible(true);
            ec = new EchoClient(settings.getString("serverIP"));
            l.info("Server ip: " + settings.getString("serverIP"));
            ProcessResponse.setChatUI(true);
        } catch (Exception e) {
            l.error("Exception in ChatUI");
            for (StackTraceElement el : e.getStackTrace()) {
                l.error(el.toString());
            }
        }
    }

    private void registerListeners() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ec.sendEcho("msg:" + messageToSendTextField.getText());
                    ZonedDateTime now2 = ZonedDateTime.now();
                    l.info("Now: " + now2.toInstant().toEpochMilli());
                } catch (Exception ex) {
                    rvl.setText("Internal error");
                }
                rvl.setText("Connecting to server...");
                f.repaint();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitOrLogDiag qold = new quitOrLogDiag();
                qold.createUI();
                f.setVisible(false);
            }
        });
    }

    public void updateAndRepaintFrame(String msg) {
        String oldpanel = textPane1.getText();
        oldpanel = oldpanel + msg + System.lineSeparator();
        textPane1.setText(oldpanel);
        rvl.setText("Messages received");
        f.repaint();
    }

    public void cleanChat() {
        textPane1.setText("");
        ProcessResponse.cleanMessages();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        p = new JPanel();
        p.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        sendButton = new JButton();
        sendButton.setText("Send");
        p.add(sendButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messageToSendTextField = new JTextField();
        messageToSendTextField.setText("Message to send");
        p.add(messageToSendTextField, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("JetBrains Mono", -1, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Chatroom");
        p.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setText("Quit");
        p.add(quitButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rvl = new JLabel();
        Font rvlFont = this.$$$getFont$$$("JetBrains Mono", -1, 16, rvl.getFont());
        if (rvlFont != null) rvl.setFont(rvlFont);
        rvl.setText("");
        p.add(rvl, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        p.add(scrollPane1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane1 = new JTextPane();
        scrollPane1.setViewportView(textPane1);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return p;
    }

}
