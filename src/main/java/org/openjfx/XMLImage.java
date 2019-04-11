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

    public String GetTag() {
        return tag;
    }
    public String GetName() {
        return name;
    }
    public String GetPath() {
        return path;
    }
    public String GetURIPath() {
        return "file:///" + path;
    }
}
