package com.dcnn;
import sun.security.util.Password;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ClientUI {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4040);
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        DataInputStream input = new DataInputStream(s.getInputStream());

        loginScreen(output, input, s);

    }

    static void loginScreen(DataOutputStream output, DataInputStream input, Socket s){
        JFrame welcome = new JFrame("EMS");

        Image bg = Toolkit.getDefaultToolkit().getImage("/bg-abstract.jpg");

        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(welcome);
        JLabel success = new JLabel("WRONG CREDENTIALS!");
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                welcome.setVisible(false);
                loginScreen(output, input, s);
            }
        });

        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("EVENT MANAGEMENT SYSTEM!");
        title.setBounds(380, 30, 500, 100);
        title.setFont(header);

        JLabel username = new JLabel("Enter Username: ");
        username.setBounds(550, 160, 200, 30);

        JLabel password = new JLabel("Enter password: ");
        password.setBounds(550, 260, 200, 30);

        JTextField userfield = new JTextField();
        userfield.setBounds(500, 200, 200, 30);

        JPasswordField passfield = new JPasswordField();
        passfield.setBounds(500, 300, 200, 30);


        JButton button = new JButton("Login");
        button.setBounds(500,350,90,40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getUser = userfield.getText();
                String getPass = String.valueOf(passfield.getPassword());
//                boolean isUser = false;
//                boolean isAdmin = false;
                try {
                    output.writeInt(1);
                    output.writeUTF(getUser);
                    output.writeUTF(getPass);
                    System.out.println("DATA SENT!");
                    boolean isUser = input.readBoolean();
                    boolean isAdmin = input.readBoolean();
                    if (isUser == true){
                        welcome.setVisible(false);
                        if (isAdmin == true){
                            adminScreen(output, input, s);
                        } else {
                            userScreen(output, input, s, getUser);
                        }
                    } else {
                        popup.setVisible(true);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton regButton = new JButton("Register");
        regButton.setBounds(610, 350, 90, 40);
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registerScreen(output, input, s);
                welcome.setVisible(false);
            }
        });


        welcome.add(button);
        welcome.add(title);
        welcome.add(username);
        welcome.add(password);
        welcome.add(userfield);
        welcome.add(passfield);
        welcome.add(regButton);

        welcome.setSize(1280,720);
        welcome.getContentPane().setBackground(new Color(231,226,255));
        welcome.setLayout(null);
        welcome.setLocationRelativeTo(null);
        welcome.setVisible(true);
        welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void registerScreen(DataOutputStream output, DataInputStream input, Socket s){
        JFrame register = new JFrame("Registration");


        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(register);
        JLabel success = new JLabel("SUCCESSFULLY REGISTERED!");
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                register.setVisible(false);
                loginScreen(output, input, s);
            }
        });



        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("REGISTRATION");
        title.setBounds(490, 30, 500, 100);
        title.setFont(header);

        JLabel username = new JLabel("Enter Username: ");
        username.setBounds(550, 140, 200, 30);

        JLabel password = new JLabel("Enter password: ");
        password.setBounds(550, 240, 200, 30);

        JLabel name = new JLabel("Enter Name: ");
        name.setBounds(550, 340, 200, 30);

        JLabel age = new JLabel("Enter Age: ");
        age.setBounds(550, 440, 200, 30);

        JLabel gender = new JLabel("Enter Gender: ");
        gender.setBounds(550, 540, 200, 30);

        JTextField userfield = new JTextField();
        userfield.setBounds(500, 180, 200, 30);

        JPasswordField passfield = new JPasswordField();
        passfield.setBounds(500, 280, 200, 30);

        JTextField namefield = new JTextField();
        namefield.setBounds(500, 380, 200, 30);

        JTextField agefield = new JTextField();
        agefield.setBounds(500, 480, 200, 30);

        JTextField genderfield = new JTextField();
        genderfield.setBounds(500, 580, 200, 30);

        JButton signup = new JButton("Register");
        signup.setBounds(550, 620, 90, 40);
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DATA COLLECTED!");
                String getUser = userfield.getText();
                String getPass = String.valueOf(passfield.getPassword());
                String getName = namefield.getText();
                String getAge = agefield.getText();
                String getGender = genderfield.getText();
                try {
                output.writeUTF(getUser);
                output.writeUTF(getPass);
                output.writeUTF(getName);
                output.writeUTF(getAge);
                output.writeUTF(getGender);
                System.out.println("DATA SENT!");
                String result = input.readUTF();
                System.out.println(result);
                popup.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        register.add(title);
        register.add(username);
        register.add(password);
        register.add(name);
        register.add(age);
        register.add(gender);
        register.add(userfield);
        register.add(passfield);
        register.add(namefield);
        register.add(agefield);
        register.add(genderfield);
        register.add(signup);


        register.setSize(1280,720);
        register.getContentPane().setBackground(new Color(231,226,255));
        register.setLayout(null);
        register.setLocationRelativeTo(null);
        register.setVisible(true);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    static void adminScreen(DataOutputStream output, DataInputStream input, Socket s){
        JFrame admin = new JFrame("Admin Menu");

        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("ADMIN MENU");
        title.setBounds(530, 30, 500, 100);
        title.setFont(header);

        JButton UserMenu = new JButton("User Management");
        UserMenu.setBounds(550, 150, 150, 40);
        UserMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.setVisible(false);
                userMgmt(output, input, s);
                try {
                    output.writeInt(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }});


        JButton EventMenu = new JButton("Event Management");
        EventMenu.setBounds(550, 250, 150, 40);
        EventMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.setVisible(false);
                eventMgmt(output, input, s);
                try {
                    output.writeInt(2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }});

        JButton back = new JButton("Back");
        back.setBounds(550, 350, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(4);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                admin.setVisible(false);
                loginScreen(output, input, s);
            }});

        admin.add(title);
        admin.add(UserMenu);
        admin.add(EventMenu);
        admin.add(back);

        admin.setSize(1280, 720);
        admin.getContentPane().setBackground(new Color(231,226,255));
        admin.setLayout(null);
        admin.setLocationRelativeTo(null);
        admin.setVisible(true);
        admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void userScreen(DataOutputStream output, DataInputStream input, Socket s, String getUser) {
        JFrame userPanel = new JFrame("User Menu");

        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("USER MENU");
        title.setBounds(530, 30, 500, 100);
        title.setFont(header);

        JButton UserMenu = new JButton("Register for Event");
        UserMenu.setBounds(550, 150, 150, 40);
        UserMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.setVisible(false);
                try {
                    output.writeInt(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    regEvent(output, input, s, getUser);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});


        JButton EventMenu = new JButton("Cancel Registration");
        EventMenu.setBounds(550, 250, 150, 40);
        EventMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                userPanel.setVisible(false);
                try {
                    cancelEvent(output, input, s, getUser);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});

        JButton back = new JButton("Back");
        back.setBounds(550, 350, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(3);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                userPanel.setVisible(false);
                loginScreen(output, input, s);
            }});

        userPanel.add(title);
        userPanel.add(UserMenu);
        userPanel.add(EventMenu);
        userPanel.add(back);

        userPanel.setSize(1280, 720);
        userPanel.getContentPane().setBackground(new Color(231,226,255));
        userPanel.setLayout(null);
        userPanel.setLocationRelativeTo(null);
        userPanel.setVisible(true);
        userPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void regEvent (DataOutputStream output, DataInputStream input, Socket s, String getUser) throws IOException, ClassNotFoundException {
        JFrame regEvents = new JFrame("Register Events");
        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("VIEW ALL EVENTS");
        title.setBounds(280, 30, 500, 100);
        title.setFont(header);

        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(regEvents);
        JLabel success = new JLabel("REGISTERED FOR EVENT!");
        success.setBounds(50, 50, 100, 50);
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                regEvents.setVisible(false);
                userScreen(output, input, s, getUser);
            }
        });

        Object[] col = {"ID","Name", "Venue", "Start Date", "End Date", "Duration", "Attendees"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.setColumnIdentifiers(col);
        JTable eventData = new JTable(tableModel);

        eventData.setBounds(280, 150, 800, 480);

        tableModel.addRow(col);


        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<Event> getEvents = (ArrayList<Event>)objInput.readObject();

        for (int i = 0; i < getEvents.size(); i++){
            int id = getEvents.get(i).ID;
            String name = getEvents.get(i).Name;
            String venue = getEvents.get(i).Venue;
            Date start = getEvents.get(i).StartDate;
            Date end = getEvents.get(i).EndDate;
            int duration = getEvents.get(i).Duration;
            int attendees = getEvents.get(i).Attendees;

            Object[] data = {id,name, venue, start, end, duration, attendees};
            tableModel.addRow(data);
        }

        JButton back = new JButton("Back");
        back.setBounds(280, 630, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(100);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                regEvents.setVisible(false);
                userScreen(output, input, s, getUser);
            }});

        JLabel reg = new JLabel("Enter ID of Event to register: ");
        reg.setBounds(450, 630, 350, 40);

        JTextField regSubmit = new JTextField();
        regSubmit.setBounds(630, 630, 200, 40);

        JButton submit = new JButton("Submit");
        submit.setBounds(830, 630, 150, 40);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = regSubmit.getText();
                int getReg = Integer.parseInt(ID);
                try {
                    output.writeInt(getReg);
                    output.writeUTF(getUser);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                popup.setVisible(true);
            }
        });

        regEvents.add(title);
        regEvents.add(eventData);
        regEvents.add(back);
        regEvents.add(reg);
        regEvents.add(regSubmit);
        regEvents.add(submit);

        regEvents.setSize(1280, 720);
        regEvents.getContentPane().setBackground(new Color(231,226,255));
        regEvents.setLayout(null);
        regEvents.setLocationRelativeTo(null);
        regEvents.setVisible(true);
        regEvents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void cancelEvent (DataOutputStream output, DataInputStream input, Socket s, String getUser) throws IOException, ClassNotFoundException {
        JFrame cancelEvents = new JFrame("Cancel Registration");
        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("VIEW ALL EVENTS");
        title.setBounds(280, 30, 500, 100);
        title.setFont(header);

        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(cancelEvents);
        JLabel success = new JLabel("REGISTRATION CANCELLED!");
        success.setBounds(50, 50, 100, 50);
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                cancelEvents.setVisible(false);
                userScreen(output, input, s, getUser);
            }
        });

        Object[] col = {"ID","Name", "Venue", "Start Date", "End Date", "Duration", "Attendees"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.setColumnIdentifiers(col);
        JTable eventData = new JTable(tableModel);

        eventData.setBounds(280, 150, 800, 480);

        tableModel.addRow(col);

        output.writeUTF(getUser);

        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<Event> getEvents = (ArrayList<Event>)objInput.readObject();

        for (int i = 0; i < getEvents.size(); i++){
            int id = getEvents.get(i).ID;
            String name = getEvents.get(i).Name;
            String venue = getEvents.get(i).Venue;
            Date start = getEvents.get(i).StartDate;
            Date end = getEvents.get(i).EndDate;
            int duration = getEvents.get(i).Duration;
            int attendees = getEvents.get(i).Attendees;

            Object[] data = {id,name, venue, start, end, duration, attendees};
            tableModel.addRow(data);
        }

        JButton back = new JButton("Back");
        back.setBounds(280, 630, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(100);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                cancelEvents.setVisible(false);
                userScreen(output, input, s, getUser);
            }});

        JLabel reg = new JLabel("Enter ID of Event to unregister: ");
        reg.setBounds(450, 630, 350, 40);

        JTextField regSubmit = new JTextField();
        regSubmit.setBounds(630, 630, 200, 40);

        JButton submit = new JButton("Submit");
        submit.setBounds(830, 630, 150, 40);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = regSubmit.getText();
                int getReg = Integer.parseInt(ID);
                try {
                    output.writeInt(getReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                popup.setVisible(true);
            }
        });

        cancelEvents.add(title);
        cancelEvents.add(eventData);
        cancelEvents.add(back);
        cancelEvents.add(reg);
        cancelEvents.add(regSubmit);
        cancelEvents.add(submit);

        cancelEvents.setSize(1280, 720);
        cancelEvents.getContentPane().setBackground(new Color(231,226,255));
        cancelEvents.setLayout(null);
        cancelEvents.setLocationRelativeTo(null);
        cancelEvents.setVisible(true);
        cancelEvents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void userMgmt (DataOutputStream output, DataInputStream input, Socket s){
        JFrame user = new JFrame("User Management");

        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("USER MANAGEMENT");
        title.setBounds(480, 30, 500, 100);
        title.setFont(header);

        JButton addAdmin = new JButton("Add Admin");
        addAdmin.setBounds(550, 150, 150, 40);
        addAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                user.setVisible(false);
                try {
                    userAdmin(output, input, s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});


        JButton viewUsers = new JButton("View Users");
        viewUsers.setBounds(550, 250, 150, 40);
        viewUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setVisible(false);
                try {
                    output.writeInt(2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    viewUsers(output, input, s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});

        JButton delUsers = new JButton("Delete Users");
        delUsers.setBounds(550, 350, 150, 40);
        delUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setVisible(false);
                try {
                    output.writeInt(5);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    deleteUsers(output, input, s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});


        JButton back = new JButton("Back");
        back.setBounds(550, 450, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(4);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                user.setVisible(false);
                adminScreen(output, input, s);
            }});

        user.add(title);
        user.add(addAdmin);
        user.add(delUsers);
        user.add(viewUsers);
        user.add(back);

        user.setSize(1280, 720);
        user.getContentPane().setBackground(new Color(231,226,255));
        user.setLayout(null);
        user.setLocationRelativeTo(null);
        user.setVisible(true);
        user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void userAdmin(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        JFrame registerAdmin = new JFrame("Admin Registration");


        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(registerAdmin);
        JLabel success = new JLabel("SUCCESSFULLY REGISTERED!");
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                registerAdmin.setVisible(false);
                userMgmt(output, input, s);
            }
        });



        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("ADMIN REGISTRATION");
        title.setBounds(440, 30, 500, 100);
        title.setFont(header);

        JLabel username = new JLabel("Enter Username: ");
        username.setBounds(550, 140, 200, 30);

        JLabel password = new JLabel("Enter password: ");
        password.setBounds(550, 240, 200, 30);

        JLabel name = new JLabel("Enter Name: ");
        name.setBounds(550, 340, 200, 30);

        JLabel age = new JLabel("Enter Age: ");
        age.setBounds(550, 440, 200, 30);

        JLabel gender = new JLabel("Enter Gender: ");
        gender.setBounds(550, 540, 200, 30);

        JTextField userfield = new JTextField();
        userfield.setBounds(500, 180, 200, 30);

        JPasswordField passfield = new JPasswordField();
        passfield.setBounds(500, 280, 200, 30);

        JTextField namefield = new JTextField();
        namefield.setBounds(500, 380, 200, 30);

        JTextField agefield = new JTextField();
        agefield.setBounds(500, 480, 200, 30);

        JTextField genderfield = new JTextField();
        genderfield.setBounds(500, 580, 200, 30);

        JButton signup = new JButton("Register");
        signup.setBounds(550, 620, 90, 40);
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DATA COLLECTED!");
                String getUser = userfield.getText();
                String getPass = String.valueOf(passfield.getPassword());
                String getName = namefield.getText();
                String getAge = agefield.getText();
                String getGender = genderfield.getText();
                try {
                    output.writeUTF(getUser);
                    output.writeUTF(getPass);
                    output.writeUTF(getName);
                    output.writeUTF(getAge);
                    output.writeUTF(getGender);
                    System.out.println("DATA SENT!");
                    String result = input.readUTF();
                    System.out.println(result);
                    popup.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton back = new JButton("Back");
        back.setBounds(80, 80, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeUTF("null");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registerAdmin.setVisible(false);
                userMgmt(output, input, s);
            }});

        registerAdmin.add(title);
        registerAdmin.add(username);
        registerAdmin.add(password);
        registerAdmin.add(name);
        registerAdmin.add(age);
        registerAdmin.add(gender);
        registerAdmin.add(userfield);
        registerAdmin.add(passfield);
        registerAdmin.add(namefield);
        registerAdmin.add(agefield);
        registerAdmin.add(genderfield);
        registerAdmin.add(signup);
        registerAdmin.add(back);


        registerAdmin.setSize(1280,720);
        registerAdmin.getContentPane().setBackground(new Color(231,226,255));
        registerAdmin.setLayout(null);
        registerAdmin.setLocationRelativeTo(null);
        registerAdmin.setVisible(true);
        registerAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void viewUsers(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        JFrame viewUsers = new JFrame("View Users");
        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("VIEW ALL USERS");
        title.setBounds(280, 30, 500, 100);
        title.setFont(header);

        Object[] col = {"ID","Username", "Name", "Gender", "Age", "Role"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.setColumnIdentifiers(col);
        JTable userData = new JTable(tableModel);

        userData.setBounds(280, 150, 800, 480);

        tableModel.addRow(col);


        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<User> getUsers = (ArrayList<User>)objInput.readObject();

        for (int i = 0; i < getUsers.size(); i++){
            int id = getUsers.get(i).ID;
            String user = getUsers.get(i).username;
            String name = getUsers.get(i).name;
            char gender = getUsers.get(i).gender;
            int age = getUsers.get(i).age;
            int role = getUsers.get(i).role;

            Object[] data = {id,user, name, gender, age, role};
            tableModel.addRow(data);
        }

        JButton back = new JButton("Back");
        back.setBounds(280, 630, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUsers.setVisible(false);
                userMgmt(output, input, s);
            }});

        viewUsers.add(title);
        viewUsers.add(userData);
        viewUsers.add(back);

        viewUsers.setSize(1280, 720);
        viewUsers.getContentPane().setBackground(new Color(231,226,255));
        viewUsers.setLayout(null);
        viewUsers.setLocationRelativeTo(null);
        viewUsers.setVisible(true);
        viewUsers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void deleteUsers(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        System.out.println("DELETE USERS FRONTEND!");

        JFrame delUsers = new JFrame("Delete Users");
        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("DELETE USERS");
        title.setBounds(280, 30, 500, 100);
        title.setFont(header);

        Object[] col = {"ID","Username", "Name", "Gender", "Age", "Role"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.setColumnIdentifiers(col);
        JTable userData = new JTable(tableModel);

        userData.setBounds(280, 150, 800, 480);

        tableModel.addRow(col);

        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(delUsers);
        JLabel success = new JLabel("USER DELETED");
        success.setBounds(50, 50, 100, 50);
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                delUsers.setVisible(false);
                userMgmt(output, input, s);
            }
        });


        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<User> getUsers = (ArrayList<User>)objInput.readObject();

        for (int i = 0; i < getUsers.size(); i++){
            int id = getUsers.get(i).ID;
            String user = getUsers.get(i).username;
            String name = getUsers.get(i).name;
            char gender = getUsers.get(i).gender;
            int age = getUsers.get(i).age;
            int role = getUsers.get(i).role;

            Object[] data = {id,user, name, gender, age, role};
            tableModel.addRow(data);
        }

        JButton back = new JButton("Back");
        back.setBounds(280, 630, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(100);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                delUsers.setVisible(false);
                userMgmt(output, input, s);
            }});

        JLabel del = new JLabel("Enter ID of User to delete: ");
        del.setBounds(450, 630, 350, 40);

        JTextField delSubmit = new JTextField();
        delSubmit.setBounds(600, 630, 200, 40);

        JButton submit = new JButton("Submit");
        submit.setBounds(800, 630, 150, 40);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = delSubmit.getText();
                int getDel = Integer.parseInt(ID);
                try {
                    output.writeInt(getDel);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                popup.setVisible(true);
            }
        });

        delUsers.add(title);
        delUsers.add(userData);
        delUsers.add(back);
        delUsers.add(del);
        delUsers.add(submit);
        delUsers.add(delSubmit);

        delUsers.setSize(1280, 720);
        delUsers.getContentPane().setBackground(new Color(231,226,255));
        delUsers.setLayout(null);
        delUsers.setLocationRelativeTo(null);
        delUsers.setVisible(true);
        delUsers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void eventMgmt (DataOutputStream output, DataInputStream input, Socket s){
        JFrame event = new JFrame("Event Management");

        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("EVENT MANAGEMENT");
        title.setBounds(480, 30, 500, 100);
        title.setFont(header);

        JButton addAdmin = new JButton("Add Event");
        addAdmin.setBounds(550, 150, 150, 40);
        addAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                event.setVisible(false);
                addEvent(output, input, s);
            }});


        JButton viewUsers = new JButton("View Events");
        viewUsers.setBounds(550, 250, 150, 40);
        viewUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.setVisible(false);
                try {
                    output.writeInt(2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    viewEvent(output, input, s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }});


        JButton back = new JButton("Back");
        back.setBounds(550, 550, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeInt(4);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                event.setVisible(false);
                adminScreen(output, input, s);
            }});

        event.add(title);
        event.add(addAdmin);
        event.add(viewUsers);
        event.add(back);

        event.setSize(1280, 720);
        event.getContentPane().setBackground(new Color(231,226,255));
        event.setLayout(null);
        event.setLocationRelativeTo(null);
        event.setVisible(true);
        event.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void addEvent(DataOutputStream output, DataInputStream input, Socket s) {
        JFrame addEvent = new JFrame("Add Event");


        // POPUPS FOR SUCCESS AND FAILURE
        JDialog popup = new JDialog(addEvent);
        JLabel success = new JLabel("SUCCESSFULLY ADDED!");
        popup.setSize(200, 200);
        popup.add(success);
        popup.setLocationRelativeTo(null);
        popup.setVisible(false);
        popup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                addEvent.setVisible(false);
                eventMgmt(output, input, s);
            }
        });



        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("ADD EVENT");
        title.setBounds(520, 30, 500, 100);
        title.setFont(header);

        JLabel name = new JLabel("Enter Name: ");
        name.setBounds(550, 140, 200, 30);

        JLabel venue = new JLabel("Enter Venue: ");
        venue.setBounds(550, 240, 200, 30);

        JLabel stDate = new JLabel("Enter Start Date: ");
        stDate.setBounds(550, 340, 200, 30);

        JLabel edDate = new JLabel("Enter End Date: ");
        edDate.setBounds(550, 440, 200, 30);

        JTextField namefield = new JTextField();
        namefield.setBounds(500, 180, 200, 30);

        JTextField venuefield = new JTextField();
        venuefield.setBounds(500, 280, 200, 30);

        JTextField startfield = new JTextField();
        startfield.setBounds(500, 380, 200, 30);

        JTextField endfield = new JTextField();
        endfield.setBounds(500, 480, 200, 30);

        JButton submit = new JButton("Submit");
        submit.setBounds(550, 620, 90, 40);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DATA COLLECTED!");
                String getName = namefield.getText();
                String getVenue = venuefield.getText();
                String getStart = startfield.getText();
                String getEnd = endfield.getText();
                try {
                    output.writeUTF(getName);
                    output.writeUTF(getVenue);
                    output.writeUTF(getStart);
                    output.writeUTF(getEnd);
                    System.out.println("DATA SENT!");
                    String result = input.readUTF();
                    System.out.println(result);
                    popup.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton back = new JButton("Back");
        back.setBounds(80, 80, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeUTF("null");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                addEvent.setVisible(false);
                eventMgmt(output, input, s);
            }});

        addEvent.add(title);
        addEvent.add(name);
        addEvent.add(venue);
        addEvent.add(stDate);
        addEvent.add(edDate);
        addEvent.add(namefield);
        addEvent.add(venuefield);
        addEvent.add(startfield);
        addEvent.add(endfield);
        addEvent.add(submit);
        addEvent.add(back);


        addEvent.setSize(1280,720);
        addEvent.getContentPane().setBackground(new Color(231,226,255));
        addEvent.setLayout(null);
        addEvent.setLocationRelativeTo(null);
        addEvent.setVisible(true);
        addEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void viewEvent(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        JFrame viewEvents = new JFrame("View Events");
        Font header = new Font("sansserif", Font.BOLD, 30);
        JLabel title = new JLabel("VIEW ALL EVENTS");
        title.setBounds(280, 30, 500, 100);
        title.setFont(header);

        Object[] col = {"ID","Name", "Venue", "Start Date", "End Date", "Duration", "Attendees"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.setColumnIdentifiers(col);
        JTable eventData = new JTable(tableModel);

        eventData.setBounds(280, 150, 800, 480);

        tableModel.addRow(col);


        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<Event> getEvents = (ArrayList<Event>)objInput.readObject();

        for (int i = 0; i < getEvents.size(); i++){
            int id = getEvents.get(i).ID;
            String name = getEvents.get(i).Name;
            String venue = getEvents.get(i).Venue;
            Date start = getEvents.get(i).StartDate;
            Date end = getEvents.get(i).EndDate;
            int duration = getEvents.get(i).Duration;
            int attendees = getEvents.get(i).Attendees;

            Object[] data = {id,name, venue, start, end, duration, attendees};
            tableModel.addRow(data);
        }

        JButton back = new JButton("Back");
        back.setBounds(280, 630, 150, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEvents.setVisible(false);
                eventMgmt(output, input, s);
            }});

        viewEvents.add(title);
        viewEvents.add(eventData);
        viewEvents.add(back);

        viewEvents.setSize(1280, 720);
        viewEvents.getContentPane().setBackground(new Color(231,226,255));
        viewEvents.setLayout(null);
        viewEvents.setLocationRelativeTo(null);
        viewEvents.setVisible(true);
        viewEvents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
