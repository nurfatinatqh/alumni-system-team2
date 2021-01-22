/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.user;

/**
 *
 * @author ainal farhan
 */
public class Staff implements User {
    
    public String email;
    public String name;
    public String password;
    public String phoneNum;
    public String role;
    public String userID;
    
    public Staff() {}
    
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
        this.userID = "STF"+id+Character.toString(result1)+Character.toString(result2)+Character.toString(result3);
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
}
