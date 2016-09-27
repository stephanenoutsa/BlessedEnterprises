package com.blessedenterprises.models;

/**
 * Created by stephnoutsa on 9/13/16.
 */
public class User {

    // Private variables
    int _uid;
    String name, date, loginTime, logoutTime;

    // Empty constructor
    public User() {

    }

    // Constructor
    public User(int _uid, String name, String date, String loginTime, String logoutTime) {
        this._uid = _uid;
        this.name = name;
        this.date = date;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    // Constructor
    public User(String name, String date, String loginTime, String logoutTime) {
        this.name = name;
        this.date = date;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    // Getter and Setter methods
    public int get_uid() {
        return _uid;
    }

    public void set_uid(int _uid) {
        this._uid = _uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }
}
