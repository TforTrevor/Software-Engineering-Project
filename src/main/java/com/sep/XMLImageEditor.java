package com.sep;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.ArrayList;

public class XMLImageEditor {

    private Document document;
    //private String filePath = "C:/Users/Trevor/Desktop/images.xml";
    private String filePath = "images.xml";

    XMLImageEditor() {
        try {
            //ClassLoader classLoader = getClass().getClassLoader();
            //InputStream xmlInputStream = classLoader.getResourceAsStream("images.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //document = dBuilder.parse(xmlInputStream)
            if (imagesDocumentExists()) {
                System.out.println("Opening existing images document");
                document = dBuilder.parse(filePath);
                document.getDocumentElement().normalize();
            } else {
                System.out.println("Created new images document");
                document = dBuilder.newDocument();
                Element rootElement = document.createElement("images");
                document.appendChild(rootElement);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean imagesDocumentExists() {
        Path path = Paths.get(filePath);
        return Files.exists(path);
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
            File file = new File(path);
            Element pathElement = document.createElement("path");
            pathElement.setTextContent(file.getAbsolutePath());

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
                        WriteToXMLFile();
                        return;
                    }
                }
            }
            Element imageGroupElement = document.createElement("imageGroup");
            imageGroupElement.setAttribute("tag", tag);
            imageGroupElement.appendChild(imageElement);
            document.getDocumentElement().appendChild(imageGroupElement);
            WriteToXMLFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void RemoveXMLImage(String path) {
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
                            if (path.equals(imageElement.getElementsByTagName("path").item(0).getTextContent())) {
                                imageElement.getParentNode().removeChild(imageElement);
                                WriteToXMLFile();
                                System.out.println("Removing node");
                                return;
                            }
                        }
                    }
                }
            }
            System.out.println("No such node found");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            System.out.println("Created image index");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
