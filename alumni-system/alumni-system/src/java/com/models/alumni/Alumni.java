/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.alumni;

import com.models.user.User;

/**
 * @author PC
 * @version 1.0
 * @created 27-Dec-2020 9:28:30 PM
 */
public class Alumni implements User {

    private String alumniAddress1;
    private String alumniAddress2;
    private String alumniAddressCity;
    private String alumniAddressCountry;
    private String alumniAddressPostCode;
    private String alumniAddressState;
    private String alumniCurEmployer;
    private String alumniCurJob;
    private double alumniCurSalary;

    private String alumniFieldOfSpecializationDiploma;
    private int alumniGraduateYearDiploma;
    private int alumniStartStudyYearDiploma;
    private int alumniBatchDiploma;

    private String alumniFieldOfSpecializationBachelor;
    private int alumniGraduateYearBachelor;
    private int alumniStartStudyYearBachelor;
    private int alumniBatchBachelor;

    private String alumniFieldOfSpecializationMaster;
    private int alumniGraduateYearMaster;
    private int alumniStartStudyYearMaster;
    private int alumniBatchMaster;

    private String alumniID;
    private String alumniPrevEmployer;
    private String alumniPrevJob;
    private double alumniPrevSalary;
    private String alumniProfilePicture;
    private String alumniProfStatus;
    private int alumniProfStatusYearGained;
    private String employerAddress1;
    private String employerAddress2;
    private String employerAddressCity;
    private String employerAddressCountry;
    private String employerAddressPostCode;
    private String employerAddressState;
    
    public String email;
    public String name;
    public String password;
    public String phoneNum;
    public String role;
    public String userID;
    
    public Alumni() {}    
    
    @Override
    public String getEmail() { return email; }

    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getUserID() { return userID; }

    @Override
    public void setUserID(String userID) { this.userID = userID; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public String getPassword() { return password; }

    @Override
    public void setPassword(String password) { this.password = password; }

    @Override
    public String getPhoneNum() { return phoneNum; }

    @Override
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    @Override
    public String getRole() { return role; }

    @Override
    public void setRole(String role) { this.role = role; }

    @Override
    public void createUser(int id, String name, String email, String password, String role, String phoneNum){
        char result1 = phoneNum.charAt(0);
        char result2 = phoneNum.charAt(1);
        char result3 = phoneNum.charAt(2);
        this.userID = "ALU"+id+Character.toString(result1)+Character.toString(result2)+Character.toString(result3);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNum = phoneNum;
    }

    @Override
    public void deleteUser(int UserID){ }

    @Override
    public User getAllUser(){ return null; }

    @Override
    public void setUserDetails(String userID, String name, String email, String password, String role, String phoneNum){
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNum = phoneNum;
    }

    @Override
    public void updateProfile(String email, String name, String password, String role, String phoneNum){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNum = phoneNum;
    }

    // Get Alumni Personal Information
    /**
     *
     * @param address1
     * @param address2
     * @param city
     * @param postCode
     * @param state
     * @param country
     */
    public void setAlumniAddress(String address1, String address2, String city, String postCode, String state, String country) {
        this.alumniAddress1 = address1;
        this.alumniAddress2 = address2;
        this.alumniAddressCity = city;
        this.alumniAddressPostCode = postCode;
        this.alumniAddressState = state;
        this.alumniAddressCountry = country;
    }

    /**
     *
     * @param alumniGraduateYear
     * @param alumniStartStudy
     * @param alumniFieldOfSpecialization
     * @param alumniBatch
     */
    public void setAlumniEducationalInfoDiploma(int alumniGraduateYear, int alumniStartStudy, String alumniFieldOfSpecialization, int alumniBatch) {
        this.alumniGraduateYearDiploma = alumniGraduateYear;
        this.alumniStartStudyYearDiploma = alumniStartStudy;
        this.alumniFieldOfSpecializationDiploma = alumniFieldOfSpecialization;
        this.alumniBatchDiploma = alumniBatch;
    }

    public void setAlumniEducationalInfoBachelor(int alumniGraduateYear, int alumniStartStudy, String alumniFieldOfSpecialization, int alumniBatch) {
        this.alumniGraduateYearBachelor = alumniGraduateYear;
        this.alumniStartStudyYearBachelor = alumniStartStudy;
        this.alumniFieldOfSpecializationBachelor = alumniFieldOfSpecialization;
        this.alumniBatchBachelor = alumniBatch;
    }

    public void setAlumniEducationalInfoMaster(int alumniGraduateYear, int alumniStartStudy, String alumniFieldOfSpecialization, int alumniBatch) {
        this.alumniGraduateYearMaster = alumniGraduateYear;
        this.alumniStartStudyYearMaster = alumniStartStudy;
        this.alumniFieldOfSpecializationMaster = alumniFieldOfSpecialization;
        this.alumniBatchMaster = alumniBatch;
    }

    /**
     *
     * @param prevJob
     * @param prevSalary
     * @param curJob
     * @param curSalary
     * @param prevEmployer
     * @param curEmployer
     */
    public void setAlumniEmploymentInfo(String prevJob, double prevSalary, String curJob, double curSalary, String prevEmployer, String curEmployer) {
        this.alumniPrevJob = prevJob;
        this.alumniPrevSalary = prevSalary;
        this.alumniPrevEmployer = prevEmployer;

        this.alumniCurJob = curJob;
        this.alumniCurSalary = curSalary;
        this.alumniCurEmployer = curEmployer;
    }

    /**
     *
     * @param alumniID
     * @param alumniProfStatus
     * @param alumniProfStatusYearGained
     * @param alumniName
     * @param alumniEmail
     * @param alumniPhoneNumber
     * @param alumniProfilePicture
     */
    public void setAlumniPersonalInfo(String alumniID, String alumniProfStatus, int alumniProfStatusYearGained, String alumniProfilePicture) {
        this.alumniID = alumniID;
        this.alumniProfStatus = alumniProfStatus;
        this.alumniProfStatusYearGained = alumniProfStatusYearGained;
        this.alumniProfilePicture = alumniProfilePicture;
    }

    /**
     *
     * @param address1
     * @param address2
     * @param city
     * @param state
     * @param country
     * @param postCode
     */
    public void setEmployerAddress(String address1, String address2, String city, String postCode, String state, String country) {
        this.employerAddress1 = address1;
        this.employerAddress2 = address2;
        this.employerAddressCity = city;
        this.employerAddressPostCode = postCode;
        this.employerAddressState = state;
        this.employerAddressCountry = country;
    }

    public String getAlumniAddress1() {
        return alumniAddress1;
    }

    public String getAlumniAddress2() {
        return alumniAddress2;
    }

    public String getAlumniAddressCity() {
        return alumniAddressCity;
    }

    public String getAlumniAddressCountry() {
        return alumniAddressCountry;
    }

    public String getAlumniAddressPostCode() {
        return alumniAddressPostCode;
    }

    public String getAlumniAddressState() {
        return alumniAddressState;
    }

    public String getAlumniCurEmployer() {
        return alumniCurEmployer;
    }

    public String getAlumniCurJob() {
        return alumniCurJob;
    }

    public double getAlumniCurSalary() {
        return alumniCurSalary;
    }

    public String getAlumniFieldOfSpecializationDiploma() {
        return alumniFieldOfSpecializationDiploma;
    }

    public int getAlumniGraduateYearDiploma() {
        return alumniGraduateYearDiploma;
    }

    public int getAlumniStartStudyYearDiploma() {
        return alumniStartStudyYearDiploma;
    }

    public int getAlumniBatchDiploma() {
        return alumniBatchDiploma;
    }

    public String getAlumniFieldOfSpecializationBachelor() {
        return alumniFieldOfSpecializationBachelor;
    }

    public int getAlumniGraduateYearBachelor() {
        return alumniGraduateYearBachelor;
    }

    public int getAlumniStartStudyYearBachelor() {
        return alumniStartStudyYearBachelor;
    }

    public int getAlumniBatchBachelor() {
        return alumniBatchBachelor;
    }

    public String getAlumniFieldOfSpecializationMaster() {
        return alumniFieldOfSpecializationMaster;
    }

    public int getAlumniGraduateYearMaster() {
        return alumniGraduateYearMaster;
    }

    public int getAlumniStartStudyYearMaster() {
        return alumniStartStudyYearMaster;
    }

    public int getAlumniBatchMaster() {
        return alumniBatchMaster;
    }

    public String getAlumniID() {
        return alumniID;
    }

    public String getAlumniPrevEmployer() {
        return alumniPrevEmployer;
    }

    public String getAlumniPrevJob() {
        return alumniPrevJob;
    }

    public double getAlumniPrevSalary() {
        return alumniPrevSalary;
    }
    
    public void setAlumniProfilePicture(String alumniProfilePicture) {
        this.alumniProfilePicture = alumniProfilePicture;
    }

    public String getAlumniProfilePicture() {
        return alumniProfilePicture;
    }

    public String getAlumniProfStatus() {
        return alumniProfStatus;
    }

    public int getAlumniProfStatusYearGained() {
        return alumniProfStatusYearGained;
    }

    public String getEmployerAddress1() {
        return employerAddress1;
    }

    public String getEmployerAddress2() {
        return employerAddress2;
    }

    public String getEmployerAddressCity() {
        return employerAddressCity;
    }

    public String getEmployerAddressCountry() {
        return employerAddressCountry;
    }

    public String getEmployerAddressPostCode() {
        return employerAddressPostCode;
    }

    public String getEmployerAddressState() {
        return employerAddressState;
    }
}
