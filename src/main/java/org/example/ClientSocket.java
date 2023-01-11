package org.example;


import java.io.*;
import java.net.*;

public class ClientSocket {
    private Socket s;
    private BufferedReader in;
    private PrintWriter out;
    private final static int port = 8888;
    private final static String host = "localhost";
    Thread inputReader;


    public Thread getInputReader() {
        return inputReader;
    }

    public ClientSocket() {
        try {
            s = new Socket(host, port);
        } catch (IOException e) {
            System.exit(42);
        }
        try {
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        inputReader = new Thread(new InputReader(in));
        inputReader.start();
        inputReader.setName("inputReader");
    }
    public void sendThings(String s){
        out.println(s);
    }

    public void stopSocket() {
        try {
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean ping(String host, int port) {

        String timeStamp = "";
        Socket socket1 = null;//from w ww.  j  a  v a 2s  . c  om
        BufferedReader br1 = null;
        try {
            socket1 = new Socket(host, port);
            br1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            timeStamp = br1.readLine();
            socket1.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

class InputReader implements Runnable {

    String input;
    BufferedReader in;

    public InputReader(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        loopClient(in);
    }

    private void loopClient(BufferedReader in) {
        while (true) {
            try {
                input = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!(input == null)) {
                System.out.println(input);
                App.inputQuery.add(input);
            }
        }
    }
}
