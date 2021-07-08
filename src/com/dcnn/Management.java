package com.dcnn;

import java.io.*;
import java.util.ArrayList;

public class Management implements Serializable{
    int TeamID;
    int EventID;
    String[] teamMembers;

    Management() {
        TeamID = 0;
        EventID = 0;
    }

    Management(int TeamID, int EventID, String[] teamMembers) {
        this.TeamID = TeamID;
        this.EventID = EventID;
        this.teamMembers = teamMembers;
    }

    public void MgmtWrite(ArrayList<Management> E){
        try{
            FileOutputStream fos = new FileOutputStream("management.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(E);

            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Management> MgmtRead(){
        ArrayList<Management> mgmtData = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("management.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            mgmtData = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return mgmtData;
    }
}
