package com.eminence.drive13.model;

public class LeadsModel {
String id,lead_id,trip_date,trip_time,trip_from,trip_destination,created_at,clientname,clientnumber,purpose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientnumber() {
        return clientnumber;
    }

    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getTrip_time() {
        return trip_time;
    }

    public void setTrip_time(String trip_time) {
        this.trip_time = trip_time;
    }

    public String getTrip_from() {
        return trip_from;
    }

    public void setTrip_from(String trip_from) {
        this.trip_from = trip_from;
    }

    public String getTrip_destination() {
        return trip_destination;
    }

    public void setTrip_destination(String trip_destination) {
        this.trip_destination = trip_destination;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
