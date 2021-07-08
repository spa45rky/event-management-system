package com.dcnn;
import javax.swing.*;

public class ClientUI {
    static void initialScreen(){
        JFrame start = new JFrame();

        JButton button = new JButton("next");
        button.setBounds(130,100,100,40);

        start.add(button);

        start.setSize(400,500);
        start.setLayout(null);
        start.setVisible(true);
    }

    public static void main(String[] args){
        initialScreen();
    }
}
