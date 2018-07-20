package com.vesatago.userapp.modules.authentication;

public class SaveRegisteredUserData {

    private String address, name, village;
    private int mobile;
    private int landSize;

    public SaveRegisteredUserData(String address, String name, String village, int mobile, int
            landSize) {
        this.address = address;
        this.name = name;
        this.village = village;
        this.mobile = mobile;
        this.landSize = landSize;
    }

    public SaveRegisteredUserData() {
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getVillage() {
        return village;
    }

    public int getMobile() {
        return mobile;
    }

    public int getLandSize() {
        return landSize;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public void setLandSize(int landSize) {
        this.landSize = landSize;
    }
}
