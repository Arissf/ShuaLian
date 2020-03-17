package com.hengshitong.shualianzhifs.entmy;

public class MessageEvent2 {
    private String mesage;
    private int zongmun;
    private String type;

    public int getZongmun() {
        return zongmun;
    }

    public void setZongmun(int zongmun) {
        this.zongmun = zongmun;
    }

    public MessageEvent2(String mesage, int zongmun, String type) {
        this.mesage = mesage;
        this.zongmun = zongmun;
        this.type=type;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
