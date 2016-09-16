package com.blessedenterprises.models;

/**
 * Created by stephnoutsa on 9/13/16.
 */
public class Count {

    // Private variables
    int _id, log;

    // Empty constructor
    public Count() {

    }

    // Constructor
    public Count(int _id, int log) {
        this._id = _id;
        this.log = log;
    }

    // Constructor
    public Count(int log) {
        this.log = log;
    }

    // Getter and Setter methods
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getLog() {
        return log;
    }

    public void setLog(int log) {
        this.log = log;
    }
}
