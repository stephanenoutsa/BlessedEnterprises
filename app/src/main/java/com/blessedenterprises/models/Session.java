package com.blessedenterprises.models;

/**
 * Created by stephnoutsa on 9/12/16.
 */
public class Session {

    // Private variables
    int _sid;
    String status;

    // Empty constructor
    public Session() {

    }

    // Constructor
    public Session(int _sid, String status) {
        this._sid = _sid;
        this.status = status;
    }

    // Constructor
    public Session(String status) {
        this.status = status;
    }

    // Getter and Setter methods
    public int get_sid() {
        return _sid;
    }

    public void set_sid(int _sid) {
        this._sid = _sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
