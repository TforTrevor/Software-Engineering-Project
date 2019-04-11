package org.openjfx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;

public class XMLImagePaths {

    private Document document;

    void PrintImages() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream xmlInputStream = classLoader.getResourceAsStream("images.xml");
        String path = "C:/Users/Trevor/Desktop/images.xml";
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlInputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("imageGroup");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList nodeList2 = element.getElementsByTagName("image");
                    for (int j = 0; j < nodeList2.getLength(); j++) {
                        Node node2 = nodeList2.item(j);
                        if (node2.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) node2;
                            System.out.println("Tag: " + element.getAttribute("tag"));
                            System.out.println("Name: " + element2.getElementsByTagName("name").item(0).getTextContent());
                            System.out.println("Path: " + element2.getElementsByTagName("path").item(0).getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
