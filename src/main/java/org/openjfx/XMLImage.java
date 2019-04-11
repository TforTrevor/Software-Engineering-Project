package org.openjfx;

public class XMLImage {
    private String tag;
    private String name;
    private String path;

    XMLImage(String tag, String name, String path) {
        this.tag = tag;
        this.name = name;
        this.path = path;
    }

    String GetTag() {
        return tag;
    }
    String GetName() {
        return name;
    }
    String GetPath() {
        return path;
    }
    String GetURIPath() {
        return "file:///" + path;
    }
}
