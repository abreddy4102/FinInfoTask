package com.example.fininfotask;

public class DataModel {

    String emailId, mobileNumber;

    public DataModel(String emailId, String mobileNumber) {
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
