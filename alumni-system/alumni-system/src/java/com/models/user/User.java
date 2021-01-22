package com.models.user;

import java.io.Serializable;

public interface User extends Serializable{   
    
    public String getEmail();
    public void setEmail(String email);
    public String getUserID();
    public void setUserID(String userID);
    public String getName();
    public void setName(String name);
    public String getPassword();
    public void setPassword(String password);
    public String getPhoneNum();
    public void setPhoneNum(String phoneNum);
    public String getRole();
    public void setRole(String role);
    public void createUser(int id, String name, String email, String password, String role, String phoneNum);
    public void deleteUser(int UserID);
    public User getAllUser();
    public void setUserDetails(String userID, String name, String email, String password, String role, String phoneNum);
    public void updateProfile(String email, String name, String password, String role, String phoneNum);
}