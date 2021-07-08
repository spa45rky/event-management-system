package com.dcnn;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.time.*;


public class Event implements Serializable{
    String Name;
    String Venue;
    int Duration, ID;
    Date StartDate, EndDate;


    public Event() {
        ID = 0;
        Name = "null";
        Venue = "null";
        Duration = 0;
        StartDate = new Date();
        EndDate = new Date();
    }

    public Event(int id, String name, String venue, int duration, Date startDate, Date endDate) {
        this.ID = id;
        this.Name = name;
        this.Venue = venue;
        this.Duration = duration;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public void addEvent(Event event) {
        ArrayList<Event> storedEvents = EventRead();
        storedEvents.add(event);
        EventWrite(storedEvents);

        System.out.println("Successfully added!");
    }

    public void EventWrite(ArrayList<Event> E){
        try{
            FileOutputStream fos = new FileOutputStream("events.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(E);

            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Event> EventRead(){
        ArrayList<Event> eventData = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("events.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            eventData = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return eventData;
    }

}
