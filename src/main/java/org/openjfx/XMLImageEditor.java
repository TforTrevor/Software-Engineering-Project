package org.openjfx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLImageEditor {

    private Document document;

    XMLImageEditor() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream xmlInputStream = classLoader.getResourceAsStream("images.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlInputStream);
            document.getDocumentElement().normalize();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<XMLImage> GetXMLImages() {
        ArrayList<XMLImage> xmlImageList = new ArrayList<>();
        try {
            NodeList imageGroupList = document.getElementsByTagName("imageGroup");
            for (int i = 0; i < imageGroupList.getLength(); i++) {
                Node imageGroupNode = imageGroupList.item(i);
                if (imageGroupNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element imageGroupElement = (Element) imageGroupNode;
                    NodeList imageList = imageGroupElement.getElementsByTagName("image");
                    for (int j = 0; j < imageList.getLength(); j++) {
                        Node imageNode = imageList.item(j);
                        if (imageNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element imageElement = (Element) imageNode;
                            String tag = imageGroupElement.getAttribute("tag");
                            String name = imageElement.getAttribute("name");
                            String path = imageElement.getElementsByTagName("path").item(0).getTextContent();
                            XMLImage xmlImage = new XMLImage(tag, name, path);
                            xmlImageList.add(xmlImage);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlImageList;
    }

    void CreateXMLImage(String tag, String name, String path) {
        try {
            Element pathElement = document.createElement("path");
            pathElement.setTextContent(path);

            Element imageElement = document.createElement("image");
            imageElement.setAttribute("name", name);
            imageElement.appendChild(pathElement);

            NodeList imageGroupList = document.getElementsByTagName("imageGroup");
            for (int i = 0; i < imageGroupList.getLength(); i++) {
                Node imageGroupNode = imageGroupList.item(i);
                if (imageGroupNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element imageGroupElement = (Element) imageGroupNode;

                    if (imageGroupElement.getAttribute("tag").equals(tag)) {
                        imageGroupElement.appendChild(imageElement);
                        return;
                    }
                }
            }
            Element imageGroupElement = document.createElement("imageGroup");
            imageGroupElement.setAttribute("tag", tag);
            document.getDocumentElement().appendChild(imageGroupElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void PrintImages() {
        ArrayList<XMLImage> xmlImages = GetXMLImages();
        for (int i = 0; i < xmlImages.size(); i++) {
            System.out.println("Tag: " + xmlImages.get(i).GetTag());
            System.out.println("Name: " + xmlImages.get(i).GetName());
            System.out.println("Path: " + xmlImages.get(i).GetPath());
        }
    }
}
