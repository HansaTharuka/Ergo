package org.techknights.ergo.Retrofit.SingleViews;

/**
 * Created by Hansa on 1/20/2018.
 */

public class ProfileData {


    String fullName;
    String email;
    String profile_pic;
    String phone_number;

    public ProfileData(String id, String name, String email, String profile_pic) {

        this.fullName = name;
        this.email = email;
        this.profile_pic = profile_pic;
        this.phone_number = phone_number;
    }

    public String getName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
