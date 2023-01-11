package org.example;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Auth {
    public static void auth(String username, String password, ClientSocket socket, ArrayList<String> inputQuery) throws ParserConfigurationException, IOException, SAXException {
        XmlRW xmlRW = new XmlRW();
        NodeList xclist;
        boolean ex = true;
        try {
            xclist = xmlRW.readXML();
        }catch (Exception e){
            xclist = xmlRW.dummyNodeList();
            System.err.println(e);
        }

        if (!"".equals(((Element) xclist.item(0)).getElementsByTagName("username").item(0).getTextContent())) {
            return;
        } else {
            boolean x = false;
            username = JOptionPane.showInputDialog("Username");
            password = JOptionPane.showInputDialog("Password");
            socket.sendThings("AUTH " + username + " " + password);
            //System.out.println(inputQuery.get(inputQuery.size() - 1));
            System.out.println(inputQuery);
            while (x) {
                if (inputQuery.size() >= 1) {
                    if ((inputQuery.get(inputQuery.size() - 1).equals("INFO Authentication failed -1")) || (inputQuery.get(inputQuery.size() - 1).equals("INFO Authentication failed -2"))) {
                        while (x) {
                            if (inputQuery.get(inputQuery.size() - 1).equals("INFO Authenticated")) {
                                x = true;
                                return;
                            } else {
                                JOptionPane.showMessageDialog(null, "False Username/Password");
                                username = JOptionPane.showInputDialog("Username");
                                password = JOptionPane.showInputDialog("Password");
                                socket.sendThings("AUTH " + username + " " + password);
                            }
                        }
                    }
                }
            }

            xmlRW.setPassword(password);
            xmlRW.setUsername(username);
            xmlRW.closeXML();
        }
    }


}

