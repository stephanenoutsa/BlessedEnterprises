package com.blessedenterprises.models;

/**
 * Created by stephnoutsa on 9/13/16.
 */
public class Code {

    // Private variables
    int _cid;
    String code, date;

    // Empty constructor
    public Code() {

    }

    // Constructor
    public Code(int _cid, String code, String date) {
        this._cid = _cid;
        this.code = code;
        this.date = date;
    }

    // Constructor
    public Code(String code, String date) {
        this.code = code;
        this.date = date;
    }

    // Getter and Setter methods
    public int get_cid() {
        return _cid;
    }

    public void set_cid(int _cid) {
        this._cid = _cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
