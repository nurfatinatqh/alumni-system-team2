/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.user.factoryMethod;

import com.models.alumni.Alumni;
import com.models.user.Admin;
import com.models.user.Staff;
import com.models.user.User;

/**
 *
 * @author nurfa
 */
public class Creator {
    public  User FactoryMethod(String user) {
        switch (user) {
            case "ADMIN":
                return new Admin();
            case "STAFF":
                return new Staff();
            default:
                return new Alumni();
        }
    }
}
