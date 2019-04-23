package com.sep;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLSettingsEditor {

    private Document document;
    //private String filePath = "C:/Users/Trevor/Desktop/images.xml";
    private String filePath = "settings.xml";

    XMLSettingsEditor() {
        try {
            //ClassLoader classLoader = getClass().getClassLoader();
            //InputStream xmlInputStream = classLoader.getResourceAsStream("images.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //document = dBuilder.parse(xmlInputStream);
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                System.out.println("Opening existing settings document");
                document = dBuilder.parse(filePath);
                document.getDocumentElement().normalize();
            } else {
                System.out.println("Created new settings document");
                document = dBuilder.newDocument();
                Element rootElement = document.createElement("settings");
                document.appendChild(rootElement);
                SetSearchPath(System.getProperty("user.home"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void SetSearchPath(String path) {
        NodeList pathList = document.getElementsByTagName("searchPath");
        if (pathList.getLength() > 0) {
            pathList.item(0).setTextContent(path);
            WriteToXMLFile();
            return;
        }
        Element pathElement = document.createElement("searchPath");
        pathElement.setTextContent(path);
        document.getDocumentElement().appendChild(pathElement);
        WriteToXMLFile();
    }

    String GetSearchPath() {
        NodeList pathList = document.getElementsByTagName("searchPath");
        if (pathList.getLength() > 0) {
            return pathList.item(0).getTextContent();
        }
        return null;
    }

    private void WriteToXMLFile() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            System.out.println("Created path in settings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
