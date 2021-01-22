/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.reporting;

import java.io.Serializable;

/**
 *
 * @author hafizul
 */
public class EventReport implements Serializable {

    private String eventYear;
    private int numberOfEventPerYear;
    private String eventSponsorYear;
    private double totalSponsorAmountPerYear;

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public int getNumberOfEventPerYear() {
        return numberOfEventPerYear;
    }

    public void setNumberOfEventPerYear(int numberOfEventPerYear) {
        this.numberOfEventPerYear = numberOfEventPerYear;
    }

    public String getEventSponsorYear() {
        return eventSponsorYear;
    }

    public void setEventSponsorYear(String eventSponsorYear) {
        this.eventSponsorYear = eventSponsorYear;
    }

    public double getTotalSponsorAmountPerYear() {
        return totalSponsorAmountPerYear;
    }

    public void setTotalSponsorAmountPerYear(double totalSponsorAmountPerYear) {
        this.totalSponsorAmountPerYear = totalSponsorAmountPerYear;
    }
    
    
}