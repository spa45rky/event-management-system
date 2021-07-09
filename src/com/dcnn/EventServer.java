package com.dcnn;
import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.Date;
import java.util.Scanner;

public class EventServer {
    public static void main(String[] args) throws IOException, ParseException {
        // Setting up server sockets, and waiting on the client to connect
        ServerSocket socket = new ServerSocket(4040);
        System.out.println("Server started!");
        Socket socket1 = socket.accept();
        System.out.println("Client communication begins: 1 client connected!");

        // Defining input/output streams for communication between client and server
        DataInputStream input = new DataInputStream(socket1.getInputStream());
        DataOutputStream output = new DataOutputStream(socket1.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        loginScreen(input, output, socket1);


    }

     static void initialMenu( DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        String intro = "Welcome to the Event Management System!\n1: Login\n2:Register\n3:Exit";
        int initChoice = 0;
        output.writeUTF(intro);
        initChoice = input.readInt();
        switch (initChoice) {
            case 1: loginScreen(input, output, socket);
            case 2: registerScreen(input, output, socket);
        }

    }

    static void loginScreen(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("LOGIN SCREEN CALLED!");
//        String loginUser = "Enter Username: ";
//        output.writeUTF(loginUser);
//        String username = input.readUTF();
//        String loginPass = "Enter Password: ";
//        output.writeUTF(loginPass);
//        String pass = input.readUTF();

        int choice = input.readInt();
        if (choice == 2){
            registerScreen(input, output, socket);
        }


        String username = input.readUTF();
        System.out.println("Received username!");
        String pass = input.readUTF();
        System.out.println("Received password!");

        User check = new User();
        boolean isUser = check.login(username, pass);
        User temp = new User();
        ArrayList<User> allUsers = temp.UserRead();

        for (int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).username.equals(username)){
                temp = allUsers.get(i);
                break;
            }
        }


        if (isUser == true) {
//            output.writeBoolean(isUser);
//            adminScreen(input, output, socket);
            if (temp.role == 1) {
                output.writeBoolean(isUser);
                output.writeBoolean(true);
                adminScreen(input, output, socket);
            } else{
                output.writeBoolean(isUser);
                output.writeBoolean(false);
                userUI(input, output, socket);
            }
        } else {
            output.writeBoolean(isUser);
            output.writeBoolean(false);
            loginScreen(input, output, socket);
        }

//        output.flush();
//        loginScreen(input, output, socket);
//        System.exit(0);

//        input.close();
//        output.close();
//        socket.close();
    }

    static void registerScreen(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("REGISTER SCREEN CALLED!");

//        String username = "Enter Username: ";
//        output.writeUTF(username);
        String getUser = input.readUTF();
//        String pass = "Enter Password: ";
//        output.writeUTF(pass);
        String getPass = input.readUTF();
//        String name = "Enter Name: ";
//        output.writeUTF(name);
        String getName = input.readUTF();
//        String age = "Enter age: ";
//        output.writeUTF(age);
        String getAge = input.readUTF();
//        String gender = "Enter gender: ";
//        output.writeUTF(gender);
        String getGender = input.readUTF();

        System.out.println("DATA RECEIVED!");

        int setAge = 0;

        // VALIDATING DATA
        if (getAge.matches(".*\\d.")){
            setAge = Integer.parseInt(getAge);
        }

        char setGender = getGender.charAt(0);



        User temp = new User();
        ArrayList<User> allUsers = temp.UserRead();
        int tempID = 0;
        for (int i = 0; i < allUsers.size(); i++){
            if (allUsers.get(i).ID > tempID){
                tempID = allUsers.get(i).ID;
            }
        }

        int getID = tempID + 1;

        User newUser = new User(getUser, getPass, getName, 2, getID, setAge, setGender);
        newUser.register(newUser);
        String success = "User registered!";
        output.writeUTF(success);

        loginScreen(input, output, socket);
    }

    static void adminScreen(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("ADMIN SCREEN CALLED!");
//        String adminIntro = "1: User Screen\n2: Event Screen\n3: Management Screen\n4: Logout";
//        output.writeUTF(adminIntro);
        int adminChoice = input.readInt();
        switch (adminChoice){
            case 1: UserScreen(input, output, socket);
            case 2: EventScreen(input, output, socket);
            case 3: MgmtScreen(input, output, socket);
            case 4: loginScreen(input, output, socket);
        }
    }

    static void UserScreen (DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("USER MANAGEMENT SCREEN CALLED!");
//        String userIntro = "1: Add User\n2: View Users\n3: Search Users\n4: Back to previous Screen";
//        output.writeUTF(userIntro);
        int userChoice = input.readInt();
        switch (userChoice){
            case 1: userAdd(input, output, socket);
            case 2: userView(input, output, socket);
            case 3: userSearch(input, output, socket);
            case 4: adminScreen(input, output, socket);
            case 5: userDelete(input, output, socket);
        }
    }

    static void userAdd(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("NEW ADMIN SCREEN CALLED!");

//        String username = "Enter Username: ";
//        output.writeUTF(username);
        String getUser = input.readUTF();

        if (getUser.equals("null")){
            UserScreen(input, output, socket);
        }
//        String pass = "Enter Password: ";
//        output.writeUTF(pass);
        String getPass = input.readUTF();
//        String name = "Enter Name: ";
//        output.writeUTF(name);
        String getName = input.readUTF();
//        String age = "Enter age: ";
//        output.writeUTF(age);
        String getAge = input.readUTF();
//        String gender = "Enter gender: ";
//        output.writeUTF(gender);
        String getGender = input.readUTF();

        System.out.println("DATA RECEIVED!");

        int setAge = 0;

        // VALIDATING DATA
        if (getAge.matches(".*\\d.")){
            setAge = Integer.parseInt(getAge);
        }

        char setGender = getGender.charAt(0);



        User temp = new User();
        ArrayList<User> allUsers = temp.UserRead();
        int tempID = 0;
        for (int i = 0; i < allUsers.size(); i++){
            if (allUsers.get(i).ID > tempID){
                tempID = allUsers.get(i).ID;
            }
        }

        int getID = tempID + 1;

        User newUser = new User(getUser, getPass, getName, 1, getID, setAge, setGender);
        newUser.register(newUser);
        String success = "Admin registered!";
        output.writeUTF(success);

        UserScreen(input, output, socket);
    }

    static void userDelete(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("USER DELETE SCREEN CALLED!");
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        User temp = new User();
        ArrayList<User> allUsers = temp.UserRead();
        objStream.writeObject(allUsers);

        int delID = input.readInt();
        if (delID == 100){
            UserScreen(input, output, socket);
        }

        for (int i = 0; i < allUsers.size(); i++){
            if (allUsers.get(i).ID == delID){
                allUsers.remove(i);
                break;
            }
        }

        temp.UserWrite(allUsers);
        System.out.println("USER DELETED!");

        UserScreen(input, output, socket);
    }

    static void userView(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("USER VIEW SCREEN CALLED!");
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        User temp = new User();
        ArrayList<User> allUsers = temp.UserRead();
        objStream.writeObject(allUsers);

        UserScreen(input, output, socket);
//        input.close();
//        output.close();
//        objStream.close();
//        socket.close();
    }

    static void userSearch(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);


        String searchIntro = "Enter Name of User: ";
        output.writeUTF(searchIntro);
        String getSearch = input.readUTF();

        User user = new User();

        ArrayList<User> toSearch = user.UserRead();
        boolean isTrue = false;

        for (int i = 0; i < toSearch.size(); i++){
           if (toSearch.get(i).name.equals(getSearch)) {
               isTrue = true;
               user = toSearch.get(i);
           }
        }

        output.writeBoolean(isTrue);

        if (isTrue == true){
            objStream.writeObject(user);
        }

        UserScreen(input, output, socket);
    }

    static void EventScreen(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("EVENT MANAGEMENT SCREEN CALLED!");
//        String eventIntro = "1: Add Event\n2: View Events\n3: Search Events \n4:Go back to Previous Screen";
//        output.writeUTF(eventIntro);
        int userChoice = input.readInt();
        switch (userChoice){
            case 1: eventAdd(input, output, socket);
            case 2: eventView(input, output, socket);
            case 3: eventSearch(input, output, socket);
            case 4: adminScreen(input, output, socket);
        }
    }

    static void eventAdd(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("ADD EVENT CALLED!");
//        String name = "Enter Event Name: ";
//        output.writeUTF(name);
        String getName = input.readUTF();
        if (getName.equals("null")){
            EventScreen(input, output, socket);
        }
//        String venue = "Enter Venue: ";
//        output.writeUTF(venue);
        String getVenue = input.readUTF();


//        String startDate = "Enter Start Date: ";
//        output.writeUTF(startDate);
        String getStart = input.readUTF();
        // converting String to Date
        Date stDate = new SimpleDateFormat("dd/MM/yyyy").parse(getStart);

//        String endDate = "Enter End Date: ";
//        output.writeUTF(endDate);
        String getEnd = input.readUTF();
        // converting String to Date
        Date edDate = new SimpleDateFormat("dd/MM/yyyy").parse(getEnd);


        // Automatically assigning an ID to every new event
        Event event = new Event();
        ArrayList<Event> allEvents = event.EventRead();
        int eventCount = allEvents.size();
        int getID = eventCount + 1;

        //Calculating duration from Start and End Dates
        long diff = edDate.getTime() - stDate.getTime();
        float days = (diff/ (1000*60*60*24));
        int getDuration = Math.round(days);

        event.ID = getID;
        event.Name = getName;
        event.Venue = getVenue;
        event.StartDate = stDate;
        event.EndDate = edDate;
        event.Duration = getDuration;

        allEvents.add(event);

        event.EventWrite(allEvents);

        String success = "Event added!";
        output.writeUTF(success);

        EventScreen(input, output, socket);
    }

    static void eventView(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException{
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        Event temp = new Event();
        ArrayList<Event> allEvents = temp.EventRead();

        objStream.writeObject(allEvents);

        EventScreen(input, output, socket);
//        input.close();
//        output.close();
//        objStream.close();
//        socket.close();
    }

    static void eventSearch(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);


        String searchIntro = "Enter Name of Event: ";
        output.writeUTF(searchIntro);
        String getSearch = input.readUTF();

        Event event = new Event();

        ArrayList<Event> toSearch = event.EventRead();
        boolean isTrue = false;

        for (int i = 0; i < toSearch.size(); i++){
            if (toSearch.get(i).Name.equals(getSearch)) {
                isTrue = true;
                event = toSearch.get(i);
            }
        }

        output.writeBoolean(isTrue);

        if (isTrue == true){
            objStream.writeObject(event);
        }

        UserScreen(input, output, socket);
    }

    static void MgmtScreen(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        String eventIntro = "1: Add Management Team\n2: View Teams\n3: Search Teams \n4: Add Team Members [NOT IMPLEMENTED YET]\n5:Go back to Previous Screen";
        output.writeUTF(eventIntro);
        int userChoice = input.readInt();
        switch (userChoice){
            case 1: mgmtAdd(input, output, socket);
            case 2: mgmtView(input, output, socket);
            case 3: mgmtSearch(input, output, socket);
            case 4: adminScreen(input, output, socket);
            case 5: adminScreen(input, output, socket);
        }
    }

    static void mgmtAdd(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        String eventName = "Enter Event name for which team should be created: ";
        output.writeUTF(eventName);
        String getName = input.readUTF();

        //Searching database if Event exists or not
        Event event = new Event();

        ArrayList<Event> toSearch = event.EventRead();
        boolean isTrue = false;

        for (int i = 0; i < toSearch.size(); i++){
            if (toSearch.get(i).Name.equals(getName)) {
                isTrue = true;
                event = toSearch.get(i);
            }
        }
        System.out.println(isTrue);
        output.writeBoolean(isTrue);

        if (isTrue == false){
            MgmtScreen(input, output, socket);
        }

        String eventUsers = "Enter user's name to put into team: ";
        output.writeUTF(eventUsers);
        String getUser = input.readUTF();

        //Searching database if User exists or not
        User user = new User();

        ArrayList<User> toSearchUser = user.UserRead();
        boolean isUserTrue = false;

        for (int i = 0; i < toSearchUser.size(); i++){
            if (toSearchUser.get(i).name.equals(getUser)) {
                isUserTrue = true;
                user = toSearchUser.get(i);
            }
        }
        System.out.println(isUserTrue);
        output.writeBoolean(isUserTrue);

        if (isUserTrue == false){
            MgmtScreen(input, output, socket);
        }

        Management mgmt = new Management();

        ArrayList<Management> allMgmt = mgmt.MgmtRead();
        int mgmtCount = allMgmt.size();
        int getTeamID = mgmtCount + 1;

        String[] incUsers = new String[1];
        incUsers[0] = user.name;

        int getEventID = event.ID;
        String[] getTeamMembers = incUsers;

        mgmt.TeamID = getTeamID;
        mgmt.EventID = getEventID;
        mgmt.teamMembers = getTeamMembers;

        allMgmt.add(mgmt);
        mgmt.MgmtWrite(allMgmt);

        String success = "Successfully Added Management Team!";
        output.writeUTF(success);

        MgmtScreen(input, output, socket);
    }

    static void mgmtView(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException{
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        Management temp = new Management();
        ArrayList<Management> allMgmt = temp.MgmtRead();

        objStream.writeObject(allMgmt);

        MgmtScreen(input, output, socket);
//        input.close();
//        output.close();
//        objStream.close();
//        socket.close();
    }

    static void mgmtSearch(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        // For sending Objects over a socket
        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);


        String searchIntro = "Enter Name of Event: ";
        output.writeUTF(searchIntro);
        String getSearch = input.readUTF();

        Event event = new Event();

        ArrayList<Event> toSearch = event.EventRead();
        boolean isTrue = false;

        for (int i = 0; i < toSearch.size(); i++){
            if (toSearch.get(i).Name.equals(getSearch)) {
                isTrue = true;
                event = toSearch.get(i);
            }
        }

        output.writeBoolean(isTrue);

        if (isTrue == true){
            objStream.writeObject(event);
        }

        UserScreen(input, output, socket);
    }

    static void userUI (DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("USER PANEL CALLED!");
        int userChoice = input.readInt();
        switch (userChoice){
            case 1: eventRegister(input, output, socket);
            case 2: cancelRegister(input, output, socket);
            case 3: loginScreen(input, output, socket);
        }
    }

    static void eventRegister(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException {
        System.out.println("REGISTER EVENT SCREEN CALLED!");

        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        Event temp = new Event();
        ArrayList<Event> allEvents = temp.EventRead();

        objStream.writeObject(allEvents);

        int idReg = input.readInt();
        if(idReg == 100){
            userUI(input, output, socket);
        }
        String userReg = input.readUTF();

        for (int i = 0; i < allEvents.size(); i++){
            if (allEvents.get(i).ID == idReg){
                allEvents.get(i).attName.add(userReg);
                allEvents.get(i).Attendees++;
                break;
            }
        }

        temp.EventWrite(allEvents);
        System.out.println("SUCCESSFULLY REGISTERED!");

        userUI(input, output, socket);
    }

    static void cancelRegister(DataInputStream input, DataOutputStream output, Socket socket) throws IOException, ParseException{
        System.out.println("CANCEL REGISTRATION SCREEN CALLED!");

        OutputStream oStream = socket.getOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(oStream);
        Event temp = new Event();
        ArrayList<Event> allEvents = temp.EventRead();

        String getUser = input.readUTF();
        ArrayList<Event> filteredEvents = new ArrayList<Event>();

        for (int i = 0; i < allEvents.size(); i++){
            Event tempObj = allEvents.get(i);
            for (int j = 0; j < tempObj.Attendees; j++){
                if (tempObj.attName.get(j).equals(getUser)){
                    filteredEvents.add(tempObj);
                }
            }
        }

        objStream.writeObject(filteredEvents);

        int getID = input.readInt();

        for (int i = 0; i < allEvents.size(); i++){
            if (allEvents.get(i).ID == getID){
                for (int j = 0; j < allEvents.get(i).attName.size(); j++){
                    if (allEvents.get(i).attName.get(j).equals(getUser)){
                        allEvents.get(i).attName.remove(j);
                        allEvents.get(i).Attendees--;
                        break;
                    }
                }
            }
        }

        temp.EventWrite(allEvents);

        userUI(input, output, socket);
    }
}