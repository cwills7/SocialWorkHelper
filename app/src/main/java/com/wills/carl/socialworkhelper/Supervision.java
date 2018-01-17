package com.wills.carl.socialworkhelper;

import android.graphics.Color;

import com.github.sundeepk.compactcalendarview.domain.Event;

/**
 * Created by Carl on 1/15/2018.
 */

public class Supervision {

    private long date;
    private int supervisionHours;
    private String supervisionType;
    private String supervisionNotes;
    private int ceuHours;
    private String ceuType;
    private String ceuNotes;
    private String isNew;

    public Supervision(long date){
        this.date = date;
        isNew = "Y";
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSupervisionHours() {
        return supervisionHours;
    }

    public void setSupervisionHours(int supervisionHours) {
        this.supervisionHours = supervisionHours;
    }

    public String getSupervisionType() {
        return supervisionType;
    }

    public void setSupervisionType(String supervisionType) {
        this.supervisionType = supervisionType;
    }

    public String getSupervisionNotes() {
        return supervisionNotes;
    }

    public void setSupervisionNotes(String supervisionNotes) {
        this.supervisionNotes = supervisionNotes;
    }

    public int getCeuHours() {
        return ceuHours;
    }

    public void setCeuHours(int ceuHours) {
        this.ceuHours = ceuHours;
    }

    public String getCeuType() {
        return ceuType;
    }

    public void setCeuType(String ceuType) {
        this.ceuType = ceuType;
    }

    public String getCeuNotes() {
        return ceuNotes;
    }

    public void setCeuNotes(String ceuNotes) {
        this.ceuNotes = ceuNotes;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public Event convertToEvent(){
        return new Event(Color.GREEN, this.getDate(), this.toString());
    }

    @Override
    public String toString(){
        String dataToString = "Supervision: " + this.getSupervisionHours() + " hours of type " + this.getSupervisionType() + "\n" +
                "Supervision notes: " + this.getSupervisionNotes() + "\n" +
                "CEU: " + this.getCeuHours() + " credits of type " + this.getCeuType() + "\n" +
                "CEU Notes: " + this.getCeuNotes() + "\n";
        return dataToString;
    }


}
