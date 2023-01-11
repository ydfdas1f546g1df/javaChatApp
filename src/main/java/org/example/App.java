package org.example;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    private JPanel panelMain;
    private JScrollPane content;
    private JLabel usernameLabel;
    private JScrollPane rooms;
    public static ArrayList<String> inputQuery;
    private static String username;
    private static String password;
    static ClientSocket socket;

    /*
    ERROR Codes
    0: No Errors
    42: Cant Reach Server
     */

    public App() {

    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        inputQuery = new ArrayList<>();
        socket = new ClientSocket();

        Auth.auth(username, password, socket, inputQuery);



        JFrame frame = new JFrame("App");
        frame.setSize(500, 600);
        frame.setResizable(false);
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setVisible(true);
    }




}
