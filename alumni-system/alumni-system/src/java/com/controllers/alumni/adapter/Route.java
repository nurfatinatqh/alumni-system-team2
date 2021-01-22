/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.alumni.adapter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mohda
 */
public class Route implements AlumniRoute{
    private static final RouteGenerator ROUTE = new RouteGenerator();
    
    @Override
    public void forwardPage(HttpServletRequest request, HttpServletResponse response, String path) {

        try {

            ROUTE.forwardPageRoute(request, response, path);

        } catch (IOException | ServletException e) {

            System.out.println("Error Occur");
        }
    }

    @Override
    public void includePage(HttpServletRequest request, HttpServletResponse response, String path) {

        try {

            ROUTE.includePageRoute(request, response, path);

        } catch (IOException | ServletException e) {

            System.out.println("Error Occur");
        }

    }
}
