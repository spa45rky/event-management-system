package com.dcnn;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User implements Serializable{
    String username, pass, name;
    int role, ID, age;
    char gender;

    User() {
        username = "nulluser";
        pass= "nulluser";
        name = "null user";
        role = 0;
        ID = 0;
        age = 0;
        gender = 'M';
    }

    User(String username, String pass, String name, int role, int ID, int age, char gender) {
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.role = role;
        this.ID = ID;
        this.age = age;
        this.gender = gender;
    }

    public boolean login(String username, String pass) {
        ArrayList<User> userList = UserRead();
        for (int i = 0; i < userList.size();  i++) {
            User temp = userList.get(i);
            if (temp.username.equals(username)) {
                if (temp.pass.equals(pass)) {
                    return true;
                }
            }
        }
        return false;

    }

    public void register(User newUser) {
        User toStore = new User(newUser.username, newUser.pass, newUser.name, newUser.role, newUser.ID, newUser.age, newUser.gender);
        ArrayList<User> existingUsers = UserRead();
        existingUsers.add(toStore);
        UserWrite(existingUsers);
    }

    public void UserWrite(ArrayList<User> E){
        try{
            FileOutputStream fos = new FileOutputStream("users.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(E);

            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> UserRead(){
        ArrayList<User> userData = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("users.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            userData = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userData;
    }

}
