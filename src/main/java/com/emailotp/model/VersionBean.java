package com.emailotp.model;

public class VersionBean {
    private String version;

    public VersionBean() {
    }

    public VersionBean(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "version='" + version + '\'' +
                '}';
    }
}
