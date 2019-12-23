
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main extends JFrame implements ActionListener {
    private static String processName;
    private static String userPC;
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.TextFieldExample();
    }
    JTextField tf1, tf2, tf3, tf4, tf5, tf6,tf7;
    JLabel q1,q2,q3;
    JButton b1, b2,b3;
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
        tf1 = new JTextField();
        tf1.setBounds(50, 50, 150, 20);

        tf2 = new JTextField();
        tf2.setBounds(50, 150, 150, 20);
        tf2.setEditable(false);

        tf3 = new JTextField();
        tf3.setBounds(50, 100, 150, 20);
        tf3.setEditable(false);

        tf4 = new JTextField();
        tf4.setBounds(250, 50, 150, 20);
        tf5 = new JTextField();
        tf5.setBounds(250, 100, 150, 20);

        tf6 = new JTextField();
        tf6.setBounds(250, 150, 150, 20);
        tf6.setEditable(false);

        tf7 = new JTextField();
        tf7.setBounds(50, 350, 400, 200);
        tf7.setEditable(false);

        b1 = new JButton("Удалить");
        b1.setBounds(50, 200, 100, 50);
        b1.addActionListener(this);

        b2 = new JButton("taskkill");
        b2.setBounds(250, 275, 180, 50);
        b2.addActionListener(this);

        b3 = new JButton("tasklist");
        b3.setBounds(50, 275, 180, 50);
        b3.addActionListener(this);

        q1= new JLabel("Путь к папке удаления");
        q1.setBounds(50,30,150,20);
        q2= new JLabel("Имя завершаемого процесса");
        q2.setBounds(250,30,180,20);
        q3= new JLabel("Имя компьютера");
        q3.setBounds(250,80,150,20);
        add(tf1);
        add(b1);
        add(tf3);
        add(tf2);
        add(tf4);
        add(tf5);
        add(tf6);
        add(tf7);
        add(b2);
        add(q1);
        add(q2);
        add(q3);
        add(b3);

        setSize(500, 800);
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

        if (e.getSource() == b3) {
            //nameService
            String ss1 = tf7.getText();

            userPC = "\\" + "\\" + ss1;

            try {
                Process p = Runtime.getRuntime().exec(TASKLIST + userPC);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {

                    System.out.println(line);
                }
            } catch (IOException ex) {
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