package com.chandni.cftes;

public class UserCF {
    private final int id;
    private final String deviceName;
    private final String usageName;
    private final float cfValue;
    private final String location;

    public UserCF(int id, String deviceName, String usageName, float cfValue, String location) {
        this.id = id;
        this.deviceName = deviceName;
        this.usageName = usageName;
        this.cfValue = cfValue;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getUsageName() {
        return usageName;
    }

    public float getCfValue() {
        return cfValue;
    }

    public String getLocation() {
        return location;
    }
}
