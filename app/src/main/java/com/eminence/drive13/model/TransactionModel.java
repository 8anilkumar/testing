package com.eminence.drive13.model;

public class TransactionModel {
    String id;
    String transaction_for;
    String name;
    String location;
    String bill_photo;
    String selfi_photo;
    String insertdate;
    String rname;
    String rmobile;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRmobile() {
        return rmobile;
    }

    public void setRmobile(String rmobile) {
        this.rmobile = rmobile;
    }

    public String getBillaount() {
        return billaount;
    }

    public void setBillaount(String billaount) {
        this.billaount = billaount;
    }

    String billaount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransaction_for() {
        return transaction_for;
    }

    public void setTransaction_for(String transaction_for) {
        this.transaction_for = transaction_for;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBill_photo() {
        return bill_photo;
    }

    public void setBill_photo(String bill_photo) {
        this.bill_photo = bill_photo;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getSelfi_photo() {
        return selfi_photo;
    }

    public void setSelfi_photo(String selfi_photo) {
        this.selfi_photo = selfi_photo;
    }
}
