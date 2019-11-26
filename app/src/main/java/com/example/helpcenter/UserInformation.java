package com.example.helpcenter;

public class UserInformation{
    private String age, date, gender, patient_name, phone_number,doctor_name,time, department_name,feedback;

    public UserInformation() {
    }

    public UserInformation(String age, String date, String gender, String patient_name, String phone_number, String doctor_name, String time, String department_name,String feedback) {
        this.age = age;
        this.date = date;
        this.gender = gender;
        this.patient_name = patient_name;
        this.phone_number = phone_number;
        this.doctor_name = doctor_name;
        this.time = time;
        this.department_name = department_name;
        this.feedback = feedback;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
