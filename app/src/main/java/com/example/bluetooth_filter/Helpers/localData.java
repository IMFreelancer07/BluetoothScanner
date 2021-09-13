package com.example.bluetooth_filter.Helpers;

public class localData {

    private int status;
    private String id, tp, rfid, time;

    public localData(String id, String tp, String rfid, String time, int status) {

        this.id = id;
        this.tp = tp;
        this.rfid = rfid;
        this.time = time;
        this.status = status;

    }

    public localData(String tp, String rfid, int status) {
        this.tp = tp;
        this.rfid = rfid;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getTp() {
        return tp;
    }

    public String getRfid() {
        return rfid;
    }

    public String getTime() {
        return time;
    }



}
