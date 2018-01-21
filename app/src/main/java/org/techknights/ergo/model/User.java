package org.techknights.ergo.model;

/**
 * Created by Chathura Wijesinghe <cdanasiri@gmail.com> on 1/17/18.
 */

public class User {
    private String name;
    private String uid;
    private String email;
    private String userType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
