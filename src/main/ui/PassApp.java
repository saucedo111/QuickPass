package ui;

import model.Event;
import model.EventLog;
import model.Password;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;


/*
Main UI Class containing main game frame,
controls app flow and read user inputs, also displays relevant information
Some websites used for research and example code generally:
https://www.javatpoint.com/java-swing
https://www.guru99.com/java-swing-gui.html
https://www.educba.com/joptionpane-in-java/
http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Display_Image_with_Swing_GUI.htm
Image sources:
https://www.pexels.com/photo/grey-and-white-short-fur-cat-104827/
Dog provided
https://www.ctrl.blog/entry/lastpass-client-trust.html
 */
public class PassApp implements ActionListener {
    private static final String JSON_STORE = "./data/user.json";
    static User user = new User("Joe", "123");
    private static JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private static JsonReader jsonReader = new JsonReader(JSON_STORE);
    JFrame frame;
    ImageIcon imgDog;
    ImageIcon imgCat;
    ImageIcon imgLast;
    static JLabel l1;
    static JButton b1;
    static JButton b2;
    static JButton b3;
    static JButton b4;
    JMenuBar mb;
    JMenuItem m11;
    JMenuItem m12;

    /*
    Main UI
     */
    public static void passApp() {
        new PassApp();
    }


    // MODIFIES: user
    // EFFECTS: loads passwords from json file to user
    private static void loadUser() {
        try {
            user = jsonReader.read();
            user.logEvent("Passwords loaded");
            infoBox("Loaded " + user.getId() + " from " + JSON_STORE, "Load");
        } catch (IOException e) {
            infoBox("Unable to read from file: " + JSON_STORE, "Load");
        }
    }


    // EFFECTS: save user's passwords to json file
    private static void saveUser(User user) {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            user.logEvent("Passwords saved");
            infoBox("Saved " + user.getId() + " to " + JSON_STORE, "Save");
        } catch (FileNotFoundException e) {
            infoBox("Unable to write to file: " + JSON_STORE, "Save");
        }
    }


    // MODIFIES: this
    // EFFECTS: Modifies password based on user selection
    public static void modifyPassword(Password p) {
        if (p == null) {
            infoBox("Password not found", "Error");
        } else {
            optionsP(p);
        }

    }

    // Note: Order is not guaranteed on return due to hashset
    // EFFECTS: displays all current user passwords
    public static void displayPasswords(User u) {
        int i = 0;
        for (Password p : u.getPasswords()) {
            i++;
            infoBox(i + " : Password title: " + p.getTitle() + " Password: " + p.getPassword(),
                    "Password number: " + i);
        }
        u.logEvent("Passwords displayed");
    }


    // MODIFIES: this
    // EFFECTS: displays options to user
    public PassApp() {
        innit();
        frame.setLayout(new BorderLayout());
        frame.add(l1, BorderLayout.PAGE_START);
        frame.add(b1, BorderLayout.CENTER);
        frame.add(b2, BorderLayout.LINE_START);
        frame.add(b3, BorderLayout.LINE_END);
        frame.add(b4, BorderLayout.PAGE_END);
        frame.setJMenuBar(mb);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        m11.addActionListener(this);
        m12.addActionListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up main display frame
    public void innit() {
        frame = new JFrame("Last Pass");
        setFrameClose();
        imgLast = new ImageIcon("data/lastpass-glowing-keyboard.jpeg");
        frame.setIconImage(imgLast.getImage());
        l1 = new JLabel("Hello, " + user.getId() + "," + " welcome to QuickPass", SwingConstants.CENTER);
        b1 = new JButton("Add a password");
        b2 = new JButton("Search for a password");
        b3 = new JButton("Generate a password");
        b4 = new JButton("Display Passwords");
        mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        mb.add(m1);
        m11 = new JMenuItem("Save");
        m12 = new JMenuItem("Load");
        m1.add(m11);
        m1.add(m12);
        imgDog = new ImageIcon("data/tobs.jpg");
        imgCat = new ImageIcon("data/cat.jpg");
    }

    // MODIFIES: this
    // EFFECTS: action listener for user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add a password")) {
            addP();
        } else if (e.getActionCommand().equals("Search for a password")) {
            searchP();
        } else if (e.getActionCommand().equals("Generate a password")) {
            genP();
        } else if (e.getActionCommand().equals("Display Passwords")) {
            displayPasswords(user);
        } else if (e.getSource() == m11) {
            saveUser(user);
        } else if (e.getSource() == m12) {
            loadUser();
        }

    }


    // MODIFIES: user
    // EFFECTS: adds password to user passwords with given parameter
    public void addP() {
        frame.setVisible(false);
        String t = (String) JOptionPane.showInputDialog(null, "Title: ", null,
                JOptionPane.PLAIN_MESSAGE, imgDog, null, "");
        if (t != null) {
            String p = (String) JOptionPane.showInputDialog(null, "Password: ", null,
                    JOptionPane.PLAIN_MESSAGE, imgCat, null, "");
            if (!(t == null) && !(p == null)) {
                user.addPass(new Password(t, p));
            }
        }
        frame.setVisible(true);
    }


    // MODIFIES: user
    // EFFECTS: adds password with user-defined parameters to user passwords
    public static void genP() {
        String title = JOptionPane.showInputDialog("Title: ");
        if (title != null) {
            int length = Integer.parseInt(JOptionPane.showInputDialog("Enter password length: "));
            boolean cap =
                    Boolean.parseBoolean(JOptionPane.showInputDialog("Use upper case letters?(true/false)"));
            boolean low = Boolean.parseBoolean(JOptionPane.showInputDialog("Use lower case letters?(true/false)"));
            boolean num = Boolean.parseBoolean(JOptionPane.showInputDialog("Use numbers?(true/false)"));
            boolean sym = Boolean.parseBoolean(JOptionPane.showInputDialog("Use symbols?(true/false)"));
            if (cap || low || num || sym) {
                String gp = Password.generatePass(cap, low, num, sym, length);
                user.addPass(new Password(title, gp));
                infoBox("Generated password has title: " + title + " password: " + gp, "Gen");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays option to edit password
    public static void optionsP(Password p) {
        infoBox("Password title " + p.getTitle() + " password " + p.getPassword(), "option");
        int a = JOptionPane.showConfirmDialog(null, "Yes to edit password, no to delete password");
        if (a == JOptionPane.YES_OPTION) {
            editP(p);
        } else if (a == JOptionPane.NO_OPTION) {
            user.removePass(p);
        }
    }


    // MODIFIES: this
    // EFFECTS: edits given password
    public static void editP(Password p) {
        String n = JOptionPane.showInputDialog("New title: ");
        p.setTitle(n);
        String ps = JOptionPane.showInputDialog("New password: ");
        p.setPassword(ps);
        EventLog.getInstance().logEvent(new Event("Password " + n + " edited to."));
        infoBox("New password title is " + p.getTitle() + " password is " + p.getPassword(), "Set");
    }


    // EFFECTS: searches for password with given title in user passwords
    public static void searchP() {
        String name = JOptionPane.showInputDialog("Title: ");
        if (name != null) {
            Password ps = user.findPass(name);
            modifyPassword(ps);
        }
    }

    // EFFECTS: displays information to user with given messages and title
    // Taken from: https://stackoverflow.com/questions/7080205/popup-message-boxes
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: changes frame close operation to display log on console
    public void setFrameClose() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e);
                }
                System.exit(0);
            }
        });
    }

}
