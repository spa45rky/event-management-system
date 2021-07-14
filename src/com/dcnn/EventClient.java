package com.dcnn;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;

public class EventClient {

    public static Scanner read = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket s = new Socket("localhost", 4040);
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        DataInputStream input = new DataInputStream(s.getInputStream());

        ClientUI beginUI = new ClientUI();
        initialMenu(output, input, s, beginUI);

    }

    static void initialMenu(DataOutputStream output, DataInputStream input, Socket s, ClientUI screen) throws IOException, ClassNotFoundException{
//        String menuResponse = input.readUTF();
////        System.out.println("Connected to server!");
////        System.out.println(menuResponse);
////        System.out.println("Enter your choice: ");
////        int chooseMenu = read.nextInt();
////        output.writeInt(chooseMenu);
////
////        switch(chooseMenu){
////            case 1: loginClient(output, input, s);
////            case 2: registerClient(output, input, s);
////        }
    }

    static void loginClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        //clearing up space for input
        read.nextLine();

        String loginUser = input.readUTF();
        System.out.println(loginUser);
        String username = read.nextLine();
        output.writeUTF(username);
        String loginPass = input.readUTF();
        System.out.println(loginPass);
        String pass = read.nextLine();
        output.writeUTF(pass);

        boolean loginSuccess = input.readBoolean();
        if(loginSuccess == true){
            adminClient(output,input,s);
        }
        System.out.println("Error! No user found!");
        System.exit(0);
//        output.flush();
//        loginClient(output, input, s);
//        input.close();
//        output.close();
//        s.close();
    }

    static void registerClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        read.nextLine(); // clearing up space for input
        String username = input.readUTF();
        System.out.println(username);
        String sendUser = read.nextLine();
        output.writeUTF(sendUser);
        String pass = input.readUTF();
        System.out.println(pass);
        String sendPass = read.nextLine();
        output.writeUTF(sendPass);
        String name = input.readUTF();
        System.out.println(name);
        String sendName = read.nextLine();
        output.writeUTF(sendName);
        String age = input.readUTF();
        System.out.println(age);
        int sendAge = read.nextInt();
        output.writeInt(sendAge);
        String gender = input.readUTF();
        read.nextLine();
        System.out.println(gender);
        String sendGenderText = read.nextLine();
        char sendGender = sendGenderText.charAt(0);
        output.writeChar(sendGender);

        String msg = input.readUTF();
        System.out.println(msg);
        loginClient(output, input, s);
    }

    static void adminClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        String adminMenu = input.readUTF();
        System.out.println(adminMenu);
        int adminChoice = read.nextInt();
        output.writeInt(adminChoice);
        switch (adminChoice){
            case 1: userClient(output, input, s);
            case 2: eventClient(output, input, s);
            case 3: mgmtClient(output, input, s);
            case 4: loginClient(output, input , s);
        }
    }

    static void userClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        String userMenu = input.readUTF();
        System.out.println(userMenu);
        int userChoice = read.nextInt();
        output.writeInt(userChoice);
        switch (userChoice){
            case 1: userAdd(output, input, s);
            case 2: userView(output,input, s);
            case 3: userSearch(output, input, s);
            case 4: adminClient(output, input, s);
        }
    }

    static void userAdd(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        read.nextLine(); // clearing up space for input
        String username = input.readUTF();
        System.out.println(username);
        String sendUser = read.nextLine();
        output.writeUTF(sendUser);
        String pass = input.readUTF();
        System.out.println(pass);
        String sendPass = read.nextLine();
        output.writeUTF(sendPass);
        String name = input.readUTF();
        System.out.println(name);
        String sendName = read.nextLine();
        output.writeUTF(sendName);
        String age = input.readUTF();
        System.out.println(age);
        int sendAge = read.nextInt();
        output.writeInt(sendAge);
        String gender = input.readUTF();
        read.nextLine();
        System.out.println(gender);
        String sendGenderText = read.nextLine();
        char sendGender = sendGenderText.charAt(0);
        output.writeChar(sendGender);

        String msg = input.readUTF();
        System.out.println(msg);
        userClient(output, input, s);
    }

    static void userView(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        System.out.println("---------------------------------");
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<User> getUsers = (ArrayList<User>)objInput.readObject();

        for (int i = 0; i < getUsers.size(); i++){
            System.out.println("Username: "+getUsers.get(i).username);
            System.out.println("Password: "+getUsers.get(i).pass);
            System.out.println("Name: "+getUsers.get(i).name);
            System.out.println("Role: "+getUsers.get(i).role);
            System.out.println("ID: "+getUsers.get(i).ID);
            System.out.println("Age: "+getUsers.get(i).age);
            System.out.println("Gender: "+getUsers.get(i).gender);
            System.out.println("---------------------------------");
        }

        userClient(output, input, s);

//        input.close();
//        output.close();
//        objInput.close();
//        s.close();
    }

    static void userSearch(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);

        read.nextLine();

        String search = input.readUTF();
        System.out.println(search);
        String nameSearch = read.nextLine();
        output.writeUTF(nameSearch);

        boolean isTrue = input.readBoolean();

        User searched = new User();

        if (isTrue == true){
            searched = (User)objInput.readObject();
            System.out.println("Username: "+searched.username);
            System.out.println("Password: "+searched.pass);
            System.out.println("Name: "+searched.name);
            System.out.println("Role: "+searched.role);
            System.out.println("ID: "+searched.ID);
            System.out.println("Age: "+searched.age);
            System.out.println("Gender: "+searched.gender);
            System.out.println("---------------------------------");
        }
        else {
            System.out.println("No user found with that name!");
        }

        userClient(output, input, s);
    }

    static void eventClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        String clientMenu = input.readUTF();
        System.out.println(clientMenu);
        int userChoice = read.nextInt();
        output.writeInt(userChoice);
        switch (userChoice){
            case 1: eventAdd(output, input, s);
            case 2: eventView(output,input, s);
            case 3: eventSearch(output, input, s);
            case 4: adminClient(output, input, s);
        }
    }

    static void eventAdd(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        read.nextLine(); // clearing up space for input
        String name = input.readUTF();
        System.out.println(name);
        String sendName = read.nextLine();
        output.writeUTF(sendName);
        String venue = input.readUTF();
        System.out.println(venue);
        String sendVenue = read.nextLine();
        output.writeUTF(sendVenue);
        String startDate = input.readUTF();
        System.out.println(startDate);
        System.out.println("MUST BE IN THIS FORMAT: dd/MM/yyyy");
        String sendStart = read.nextLine();
        output.writeUTF(sendStart);
        String endDate = input.readUTF();
        System.out.println(endDate);
        System.out.println("MUST BE IN THIS FORMAT: dd/MM/yyyy");
        String sendEnd = read.nextLine();
        output.writeUTF(sendEnd);


        String msg = input.readUTF();
        System.out.println(msg);
        eventClient(output, input, s);
    }

    static void eventView(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        System.out.println("---------------------------------");
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<Event> getEvent = (ArrayList<Event>)objInput.readObject();

        for (int i = 0; i < getEvent.size(); i++){
            System.out.println("Name: "+getEvent.get(i).Name);
            System.out.println("Venue: "+getEvent.get(i).Venue);
            System.out.println("Duration: "+getEvent.get(i).Duration);
            System.out.println("Start Date: "+getEvent.get(i).StartDate);
            System.out.println("End Date: "+getEvent.get(i).EndDate);
            System.out.println("ID: "+getEvent.get(i).ID);
            System.out.println("---------------------------------");
        }

        eventClient(output, input, s);

//        input.close();
//        output.close();
//        objInput.close();
//        s.close();
    }

    static void eventSearch(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);

        read.nextLine();

        String search = input.readUTF();
        System.out.println(search);
        String nameSearch = read.nextLine();
        output.writeUTF(nameSearch);

        boolean isTrue = input.readBoolean();

        Event searched = new Event();

        if (isTrue == true){
            searched = (Event)objInput.readObject();
            System.out.println("Name: "+searched.Name);
            System.out.println("Venue: "+searched.Venue);
            System.out.println("Duration: "+searched.Duration);
            System.out.println("Start Date: "+searched.StartDate);
            System.out.println("End Date: "+searched.EndDate);
            System.out.println("ID: "+searched.ID);
            System.out.println("---------------------------------");
        }
        else {
            System.out.println("No event found with that name!");
        }

        eventClient(output, input, s);
    }

    static void mgmtClient(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        String clientMenu = input.readUTF();
        System.out.println(clientMenu);
        int userChoice = read.nextInt();
        output.writeInt(userChoice);
        switch (userChoice){
            case 1: mgmtAdd(output, input, s);
            case 2: mgmtView(output,input, s);
            case 3: mgmtSearch(output, input, s);
            case 4: System.out.println("Error!");
            case 5: adminClient(output, input, s);
        }
    }

    static void mgmtAdd(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        // clearing input
        read.nextLine();

        String name = input.readUTF();
        System.out.println(name);
        String sendName = read.nextLine();
        output.writeUTF(sendName);
        Boolean isPresent = input.readBoolean();

        if (isPresent == false){
            System.out.println("That event does not exist! try again");
            mgmtClient(output, input , s);
        }

        String users = input.readUTF();
        System.out.println(users);
        String sendUser = read.nextLine();
        output.writeUTF(sendUser);

        Boolean isUser = input.readBoolean();

        if (isUser == false){
            System.out.println("That user does not exist! try again");
            mgmtClient(output, input , s);
        }

        String msg = input.readUTF();
        System.out.println(msg);

        mgmtClient(output, input , s);
    }

    static void mgmtView(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        System.out.println("---------------------------------");
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);
        ArrayList<Management> getEvent = (ArrayList<Management>)objInput.readObject();

        for (int i = 0; i < getEvent.size(); i++){
            System.out.println("Team ID: "+getEvent.get(i).TeamID);
            System.out.println("Event ID: "+getEvent.get(i).EventID);
            System.out.println("Users: "+getEvent.get(i).teamMembers[0]);
            System.out.println("---------------------------------");
        }

        mgmtClient(output, input, s);

//        input.close();
//        output.close();
//        objInput.close();
//        s.close();
    }

    static void mgmtSearch(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        // for receiving objects over a socket
        InputStream iStream = s.getInputStream();
        ObjectInputStream objInput = new ObjectInputStream(iStream);

        read.nextLine();

        String search = input.readUTF();
        System.out.println(search);
        String nameSearch = read.nextLine();
        output.writeUTF(nameSearch);

        boolean isTrue = input.readBoolean();

        Event searched = new Event();

        if (isTrue == true){
            searched = (Event)objInput.readObject();
            System.out.println("Name: "+searched.Name);
            System.out.println("Venue: "+searched.Venue);
            System.out.println("Duration: "+searched.Duration);
            System.out.println("Start Date: "+searched.StartDate);
            System.out.println("End Date: "+searched.EndDate);
            System.out.println("ID: "+searched.ID);
            System.out.println("---------------------------------");
        }
        else {
            System.out.println("No event found with that name!");
        }

        eventClient(output, input, s);
    }

    static void userUI(DataOutputStream output, DataInputStream input, Socket s) throws IOException, ClassNotFoundException {
        String title = "Welcome to the Event Management Screen, User!";
        String viewEvent = "View Events";
        String viewReg = "View Registrations";
        String logout = "Logout";
    }


}