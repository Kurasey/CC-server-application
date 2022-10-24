package me.t.kaurami.service.setting;

public class Profile {
    private String name;
    private String xmlConfig;
    private String innerName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(String xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }
}
