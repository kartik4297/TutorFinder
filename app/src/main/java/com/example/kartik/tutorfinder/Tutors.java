package com.example.kartik.tutorfinder;

/**
 * Created by kartik on 23-01-2018.
 */

public class Tutors {

    private String name,gender,mobile,email,address;
    private int age;

    public Tutors(String name, String gender, String mobile, String email, String address, int age) {
        this.setName(name);
        this.setGender(gender);
        this.setMobile(mobile);
        this.setEmail(email);;
        this.setAddress(address);
        this.setAge(age);
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
