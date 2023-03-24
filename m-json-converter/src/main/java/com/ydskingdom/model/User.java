package com.ydskingdom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class User {
    private String name;

    private String tel;

    private String age;

    private String address;

    public User(String name, String tel, String age, String address) {
        this.name = name;
        this.tel = tel;
        this.age = age;
        this.address = address;
    }

    @JsonProperty("TEL")
    public String getTel() {
        return tel;
    }

    @JsonProperty("NAME")
    public String getName() {
        return name;
    }

    @JsonProperty("AGE")
    public String getAge() {
        return age;
    }

    @JsonProperty("ADDRESS")
    public String getAddress() {
        return address;
    }
}
