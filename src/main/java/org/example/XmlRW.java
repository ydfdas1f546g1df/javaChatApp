package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XmlRW {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Element rootElement;
    Element un;
    Element pw;
    Element uinf;

    String password;
    String username;
    final String confXmlPath = "./conf.xml";
    private String root = "chatApp";

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setRoot(String root) {
        this.root = root;
    }


    public XmlRW() throws ParserConfigurationException {
        docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        rootElement = doc.createElement(root);
        doc.appendChild(rootElement);


    }


    public void closeXML() {
        un = doc.createElement("username");
        pw = doc.createElement("password");
        uinf = doc.createElement("userInfos");

        //salary.setAttribute("hash", "SHA512");
        un.setTextContent(username);
        pw.setTextContent(password);

        uinf.appendChild(un);
        uinf.appendChild(pw);

        rootElement.appendChild(uinf);
        writeDocToXML(doc);
    }

    private void writeDocToXML(Document doc) {
        String usernamex = System.getProperty("user.name");
        try (FileOutputStream output =
                     new FileOutputStream(confXmlPath)) {
            //new FileOutputStream("C:\\Users\\" + usernamex + "\\AppData\\Local\\ChatApp\\config\\conf.xml")) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void writeXml(Document doc, OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    public NodeList readXML() throws IOException, SAXException {

        Document doc = docBuilder.parse(new File(confXmlPath));
        NodeList list = doc.getElementsByTagName(root);
        return list;
    }

    public NodeList dummyNodeList(){
        Document doc = docBuilder.newDocument();
        Element x = doc.createElement(root);
        doc.appendChild(x);
        Element y = doc.createElement("userinfos");
        x.appendChild(y);
        Element z = doc.createElement("username");
        z.setTextContent("");
        y.appendChild(z);

        return doc.getElementsByTagName(root);
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        XmlRW x = new XmlRW();
        //x.setPassword("x");
        //x.setUsername("x");
        //x.closeXML();


        NodeList list = x.readXML();
        System.out.println(((Element) list.item(0)).getElementsByTagName("username").item(0).getTextContent());
        if (((Element) list.item(0)).getElementsByTagName("username").item(0).getTextContent() == ""){
            System.out.println("y");
        }
    }
}
