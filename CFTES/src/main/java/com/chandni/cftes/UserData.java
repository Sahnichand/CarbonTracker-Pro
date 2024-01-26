package com.chandni.cftes;

public class UserData {

    private static UserData instance;
    private User user;

    private UserData() {
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void logout() {
        instance = null;
    }
}
