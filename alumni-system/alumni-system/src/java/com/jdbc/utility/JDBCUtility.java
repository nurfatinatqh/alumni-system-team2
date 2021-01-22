/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdbc.utility;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nurfa
 */
public class JDBCUtility {
    private static Connection con = null;
        
    public static Connection getCon() {
        try {
            if(con == null || con.isClosed()) {
                con = createConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    public static Connection createConnection() {
        Connection con = null;
        try
           {
                String driver = "com.mysql.jdbc.Driver";
//               String dbName = "Q5XZNrd9uF";
//               String url = "jdbc:mysql://remotemysql.com:3306/" + dbName + "?";
//               String userName = "Q5XZNrd9uF";
//               String password = "KsH8OJBw10";

//               String dbName = "ainalfa_alumni_module-db";
//               String host = "johnny.heliohost.org";
//               String port = "3306";
//               String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
//               String userName = "ainalfa_ainal2";
//               String password = "ainal2@123";
               
                String dbName = "alumni_module-db";
                String host = "localhost";
                String port = "3307";
                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
                String userName = "root";
                String password = "";
               
//                String dbName = "alumni-system-db";
//                String host = "localhost";
//                String port = "3307";
//                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
//                String userName = "root";
//                String password = "";

                Class.forName (driver);
                con = DriverManager.getConnection(url, userName, password);
                DatabaseMetaData dma = con.getMetaData ();
                System.out.println("\nConnected to " + dma.getURL());
                System.out.println("Driver       " + dma.getDriverName());
                System.out.println("Version      " + dma.getDriverVersion());
                System.out.println("");
           }
           catch (SQLException ex)
           {
               while (ex != null)
               {
                   System.out.println ("SQLState: " +
                                       ex.getSQLState ());
                   System.out.println ("Message:  " +
                                       ex.getMessage ());
                   System.out.println ("Vendor:   " +
                                       ex.getErrorCode ());
                   ex = ex.getNextException ();
                   System.out.println ("");
               }

               System.out.println("Connection to the database error");
           }
           catch (ClassNotFoundException ex) {
           }
        return con; 
 }
    public static void jdbcConClose() {
	JDBCUtility.con = null;
    }
}
