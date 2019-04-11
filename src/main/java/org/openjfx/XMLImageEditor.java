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

    private ClassLoader classLoader;
    private InputStream xmlInputStream;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document document;

    XMLImageEditor() {
        try {
            classLoader = getClass().getClassLoader();
            xmlInputStream = classLoader.getResourceAsStream("images.xml");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
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

    public void PrintImages() {
        ArrayList<XMLImage> xmlImages = GetXMLImages();
        for (int i = 0; i < xmlImages.size(); i++) {
            System.out.println("Tag: " + xmlImages.get(i).GetTag());
            System.out.println("Name: " + xmlImages.get(i).GetName());
            System.out.println("Path: " + xmlImages.get(i).GetPath());
        }
    }
}
