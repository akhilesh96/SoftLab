package com.example.android.softlab;

/**
 * Created by Akhilesh on 10/31/2017.
 */

public class Student {
    String regNo;
    String course;
    String name;
    String fname;
    String mname;
    String address;
    String district;
    String state;
    String city;
    String phoneNo;
    String sex;
    String dob;
    String email;

    public Student() {

    }

    public Student(String regNo, String course, String name, String fname, String mname, String address, String district,
                   String state, String city, String phoneNo, String sex, String dob, String email) {

        this.regNo = regNo;
        this.course = course;
        this.name = name;
        this.fname = fname;
        this.mname = mname;
        this.address = address;
        this.district = district;
        this.state = state;
        this.city = city;
        this.phoneNo = phoneNo;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
