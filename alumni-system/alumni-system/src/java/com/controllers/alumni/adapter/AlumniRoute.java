/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.alumni.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mohda
 */
public interface AlumniRoute {

    void forwardPage(HttpServletRequest request, HttpServletResponse response, String path);

    void includePage(HttpServletRequest request, HttpServletResponse response, String path);

}
