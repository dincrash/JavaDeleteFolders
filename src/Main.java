import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.glass.ui.Cursor.setVisible;

public class Main extends Frame implements ActionListener {
    private static String processName;
    private static String userPC;

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.TextFieldExample();
    }

    TextField tf1, tf2, tf3, tf4, tf5, tf6;
    Button b1, b2;
    public String s2;
    private static final String TASKLIST = "pslist ";
    private static final String KILL = "pskill ";

    public static boolean isProcessRunning(String serviceName, String userPC) throws Exception {

        Process p = Runtime.getRuntime().exec(TASKLIST + userPC);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {

            System.out.println(line);
            if (line.contains(serviceName)) {
                return true;
            }
        }

        return false;

    }

    public static void killProcess(String serviceName, String userPC) throws Exception {

        Runtime.getRuntime().exec(KILL + userPC + " " + serviceName);

    }

    public void TextFieldExample() throws Exception {
        
        tf1 = new TextField();
        tf1.setBounds(50, 50, 150, 20);
        tf3 = new TextField();
        tf3.setBounds(50, 100, 150, 20);
        tf3.setEditable(false);
        tf2 = new TextField();
        tf2.setBounds(50, 150, 150, 20);
        tf2.setEditable(false);
        tf4 = new TextField();
        tf4.setBounds(250, 50, 150, 20);
        tf5 = new TextField();
        tf5.setBounds(250, 100, 150, 20);

        tf6 = new TextField();
        tf6.setBounds(250, 150, 150, 20);
        tf6.setEditable(false);

        b1 = new Button("Удалить");
        b1.setBounds(50, 200, 50, 50);
        b1.addActionListener(this);

        b2 = new Button("Выкл");
        b2.setBounds(250, 200, 50, 50);
        b2.addActionListener(this);
        add(tf1);
        add(b1);
        add(tf3);
        add(tf2);
        add(tf4);
        add(tf5);
        add(tf6);
        add(b2);

        setSize(500, 400);
        setLayout(null);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        String s1 = tf1.getText();
        s2 = "\\" + "\\" + s1;
        s2 = s2.replace(':', '$');
        s2 = s2.replace('\t', '\\');
        String s3 = s1;
        s3 = s3.replace('\t', '$');
        String s4 = s3.replaceAll("\\$(.*)", "");
        s4 = "\\" + "\\" + s4 + "\\c$";

        if (e.getSource() == b1) {
            System.out.println(s2);
            final File directory = new File(s2);
            final File pk = new File(s4);
            if (pk.exists()) {
                if (directory.exists()) {
                    deleteDirectory(directory);
                    if (directory.exists()) {
                        String result = "Процесс запущен";
                        tf3.setText(result);
                        tf2.setText(s2);
                    } else {
                        String result = "Папка удалена";
                        tf3.setText(result);
                        tf2.setText(s2);
                    }
                } else {
                    String result = "Папки(Файла) нет";
                    tf3.setText(result);
                    tf2.setText(s2);

                }
            } else {
                String result = "ПК не вкл";
                tf3.setText(result);
                tf2.setText(s4);
            }

        }
        if (e.getSource() == b2) {
            //nameService
            String ss1 = tf4.getText();
            processName = ss1;
//            processName = "\\" + "\\" + ss1;

            //userPC
            String ss2 = tf5.getText();
            userPC = ss2;
            userPC = "\\" + "\\" + ss2;
            try {
                if (isProcessRunning(processName, userPC)) {
                    killProcess(processName, userPC);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }

    public boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return (directory.delete());
    }
}