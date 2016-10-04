package com.blessedenterprises.models;

/**
 * Created by stephnoutsa on 10/4/16.
 */
public class Host {

    // Private variables
    int _hid;
    String name, line;

    // Empty constructor
    public Host() {

    }

    // Constructor
    public Host(int _hid, String name, String line) {
        this._hid = _hid;
        this.name = name;
        this.line = line;
    }

    // Constructor
    public Host(String name, String line) {
        this.name = name;
        this.line = line;
    }

    // Getter and Setter methods
    public int get_hid() {
        return _hid;
    }

    public void set_hid(int _hid) {
        this._hid = _hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
