package com.example.smartmoney;

public class Data {
    private int amount;
    private String type;
    private String note;
    private String id;
    private String date;

    public Data(){

    }

    public Data(int amount, String income_type, String income_note, String id, String mDate) {
        this.amount = amount;
        this.type = income_type;
        this.note= income_note;
        this.date= mDate;
        this.id = id;
    }

    public Data(String type, int amount) {
        this.type=type;
        this.amount=amount;
    }




    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
