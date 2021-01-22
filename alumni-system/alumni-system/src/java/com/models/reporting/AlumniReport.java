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
public class AlumniReport implements Serializable {
    private String alumniGraduateYearDiploma;
    private int totalAlumniGraduateYearDiploma;
    
    private String alumniGraduateYearBachelor;
    private int totalAlumniGraduateYearBachelor;
    
    private String alumniGraduateYearMaster;
    private int totalAlumniGraduateYearMaster;
    
    private int totalMalaysianAlumni;
    private int totalNonMalaysianAlumni;
    private int totalAlumniHaveProfessionalStatus;
    private int totalAlumniDoNotHaveProfessionalStatus;
    
    // Constructor with no arguments
    public AlumniReport() {}

    public String getAlumniGraduateYearDiploma() {
        return alumniGraduateYearDiploma;
    }

    public void setAlumniGraduateYearDiploma(String alumniGraduateYearDiploma) {
        this.alumniGraduateYearDiploma = alumniGraduateYearDiploma;
    }

    public int getTotalAlumniGraduateYearDiploma() {
        return totalAlumniGraduateYearDiploma;
    }

    public void setTotalAlumniGraduateYearDiploma(int totalAlumniGraduateYearDiploma) {
        this.totalAlumniGraduateYearDiploma = totalAlumniGraduateYearDiploma;
    }

    public String getAlumniGraduateYearBachelor() {
        return alumniGraduateYearBachelor;
    }

    public void setAlumniGraduateYearBachelor(String alumniGraduateYearBachelor) {
        this.alumniGraduateYearBachelor = alumniGraduateYearBachelor;
    }

    public int getTotalAlumniGraduateYearBachelor() {
        return totalAlumniGraduateYearBachelor;
    }

    public void setTotalAlumniGraduateYearBachelor(int totalAlumniGraduateYearBachelor) {
        this.totalAlumniGraduateYearBachelor = totalAlumniGraduateYearBachelor;
    }

    public String getAlumniGraduateYearMaster() {
        return alumniGraduateYearMaster;
    }

    public void setAlumniGraduateYearMaster(String alumniGraduateYearMaster) {
        this.alumniGraduateYearMaster = alumniGraduateYearMaster;
    }

    public int getTotalAlumniGraduateYearMaster() {
        return totalAlumniGraduateYearMaster;
    }

    public void setTotalAlumniGraduateYearMaster(int totalAlumniGraduateYearMaster) {
        this.totalAlumniGraduateYearMaster = totalAlumniGraduateYearMaster;
    }
    
    public int getTotalMalaysianAlumni() {
        return totalMalaysianAlumni;
    }

    public void setTotalMalaysianAlumni(int totalMalaysianAlumni) {
        this.totalMalaysianAlumni = totalMalaysianAlumni;
    }

    public int getTotalNonMalaysianAlumni() {
        return totalNonMalaysianAlumni;
    }

    public void setTotalNonMalaysianAlumni(int totalNonMalaysianAlumni) {
        this.totalNonMalaysianAlumni = totalNonMalaysianAlumni;
    }

    public int getTotalAlumniHaveProfessionalStatus() {
        return totalAlumniHaveProfessionalStatus;
    }

    public void setTotalAlumniHaveProfessionalStatus(int totalAlumniHaveProfessionalStatus) {
        this.totalAlumniHaveProfessionalStatus = totalAlumniHaveProfessionalStatus;
    }

    public int getTotalAlumniDoNotHaveProfessionalStatus() {
        return totalAlumniDoNotHaveProfessionalStatus;
    }

    public void setTotalAlumniDoNotHaveProfessionalStatus(int totalAlumniDoNotHaveProfessionalStatus) {
        this.totalAlumniDoNotHaveProfessionalStatus = totalAlumniDoNotHaveProfessionalStatus;
    }
    
}
