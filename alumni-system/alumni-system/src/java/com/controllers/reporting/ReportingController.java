/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.reporting;

import com.models.reporting.AlumniReport;
import com.models.reporting.EventReport;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jdbc.utility.JDBCUtility;
import com.google.gson.Gson;

/**
 *
 * @author hafizul
 */
@WebServlet(name = "ReportingControl", urlPatterns = {"/ReportingControl"})
public class ReportingController extends HttpServlet {
        
        private ArrayList<AlumniReport> getInfoFromDatabase(){
            ArrayList<AlumniReport> alumni = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            String status = "";
            
            try{
//-----------------Number of Alumni Graduate by Year--------------------------//            
            String query5 = "SELECT alumniGraduateYearDiploma, COUNT(alumniGraduateYearDiploma) FROM alumni GROUP BY alumniGraduateYearDiploma ORDER BY alumniGraduateYearDiploma DESC";
            preparedStatement = JDBCUtility.getCon().prepareStatement(query5);

            ResultSet rs5 = preparedStatement.executeQuery();

                while(rs5.next()){
                    AlumniReport alumni2 = new AlumniReport();
                    alumni2.setAlumniGraduateYearDiploma(String.valueOf(rs5.getInt("alumniGraduateYearDiploma")));
                    alumni2.setTotalAlumniGraduateYearBachelor(rs5.getInt("COUNT(alumniGraduateYearDiploma)"));

                    alumni.add(alumni2);
                }
            
            }
            catch(SQLException e){
                status = "INVALID ACCOUNT";
            }

            return alumni;
        }
        
        private ArrayList<EventReport> getEventReportsFromDatabase() {
            final int LIMIT_LATEST_YEARS = 5;
            
            ArrayList<EventReport> eventReports = new ArrayList<>();
            PreparedStatement preparedStatementEventNumberPerYear = null;
            PreparedStatement preparedStatementEventSponsor = null;
            
            try {
                String queryEventNumberPerYear = "SELECT extract(YEAR FROM eventDate), COUNT(extract(YEAR FROM eventDate)) FROM event GROUP BY extract(YEAR FROM eventDate) ORDER BY (extract(YEAR FROM eventDate)) DESC LIMIT " + LIMIT_LATEST_YEARS + ";";
                String queryEventSponsor = "SELECT extract(YEAR FROM eventDate), SUM(eventSponsorPackageAmt * eventSponsorGatheredAmt) FROM event WHERE eventSponsor = '1' GROUP BY extract(YEAR FROM eventDate) ORDER BY (extract(YEAR FROM eventDate)) DESC LIMIT " + LIMIT_LATEST_YEARS + ";";
                
                preparedStatementEventNumberPerYear = JDBCUtility.getCon().prepareStatement(queryEventNumberPerYear);
                preparedStatementEventSponsor = JDBCUtility.getCon().prepareStatement(queryEventSponsor);
                
                ResultSet resultEventSponsor = preparedStatementEventSponsor.executeQuery();
                ResultSet resultEventNumberPerYear = preparedStatementEventNumberPerYear.executeQuery();
                
                while(resultEventNumberPerYear.next() && resultEventSponsor.next()) {
                    EventReport eventReport = new EventReport();
                    
                    eventReport.setEventYear(resultEventNumberPerYear.getString("extract(YEAR FROM eventDate)"));
                    eventReport.setNumberOfEventPerYear(resultEventNumberPerYear.getInt("COUNT(extract(YEAR FROM eventDate))"));
                    eventReport.setTotalSponsorAmountPerYear(resultEventSponsor.getDouble("SUM(eventSponsorPackageAmt * eventSponsorGatheredAmt)"));
                    eventReport.setEventSponsorYear(resultEventSponsor.getString("extract(YEAR FROM eventDate)"));
                    
                    eventReports.add(eventReport);
                }
            }
            catch (SQLException ex) {
                System.out.println(ex);
            }
            
            return eventReports;
        }
        
        private ArrayList<AlumniReport> getAlumniGraduateYearFromDatabase() {
            final int LIMIT_LATEST_YEARS = 5;
            
            PreparedStatement preparedStatementDiploma = null;
            PreparedStatement preparedStatementBachelor = null;
            PreparedStatement preparedStatementMaster = null;
            
            ArrayList<AlumniReport> alumniReports = new ArrayList<>();
            
            try{
//-----------------Number of Alumni Graduate by Year--------------------------//            
                String queryDiploma = "SELECT alumniGraduateYearDiploma, COUNT(alumniGraduateYearDiploma) FROM alumni WHERE alumniGraduateYearDiploma != 0000 GROUP BY alumniGraduateYearDiploma ORDER BY alumniGraduateYearDiploma DESC LIMIT " + LIMIT_LATEST_YEARS + ";";
                String queryBachelor = "SELECT alumniGraduateYearBachelor, COUNT(alumniGraduateYearBachelor) FROM alumni WHERE alumniGraduateYearBachelor != 0000 GROUP BY alumniGraduateYearBachelor ORDER BY alumniGraduateYearBachelor DESC LIMIT " + LIMIT_LATEST_YEARS + ";";
                String queryMaster = "SELECT alumniGraduateYearMaster, COUNT(alumniGraduateYearMaster) FROM alumni WHERE alumniGraduateYearMaster != 0000 GROUP BY alumniGraduateYearMaster ORDER BY alumniGraduateYearMaster DESC LIMIT " + LIMIT_LATEST_YEARS + ";";
                
                preparedStatementDiploma = JDBCUtility.getCon().prepareStatement(queryDiploma);
                preparedStatementBachelor = JDBCUtility.getCon().prepareStatement(queryBachelor);
                preparedStatementMaster = JDBCUtility.getCon().prepareStatement(queryMaster);

                ResultSet resultDiploma = preparedStatementDiploma.executeQuery();
                ResultSet resultBachelor = preparedStatementBachelor.executeQuery();
                ResultSet resultMaster = preparedStatementMaster.executeQuery();
                
                int[][] totalAlumniPerYear = new int[3][LIMIT_LATEST_YEARS];
                String[][] years = new String[3][LIMIT_LATEST_YEARS];
                
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < LIMIT_LATEST_YEARS; j++) {
                       totalAlumniPerYear[i][j] = 0;
                       years[i][j] = "0000";
                    }
                }
                
                int j = 0;
                int i = 0;
                
                while(resultDiploma.next()) {
                    totalAlumniPerYear[i][j] = resultDiploma.getInt("COUNT(alumniGraduateYearDiploma)");
                    years[i][j] = resultDiploma.getString("alumniGraduateYearDiploma");
                    j++;
                }
                resultDiploma.close();
                
                j = 0;
                i++;
                while(resultBachelor.next()) {
                    totalAlumniPerYear[i][j] = resultBachelor.getInt("COUNT(alumniGraduateYearBachelor)");
                    years[i][j] = resultBachelor.getString("alumniGraduateYearBachelor");
                    j++;
                }
                resultBachelor.close();
                
                j = 0;
                i++;
                while(resultMaster.next()) {
                    totalAlumniPerYear[i][j] = resultMaster.getInt("COUNT(alumniGraduateYearMaster)");
                    years[i][j] = resultMaster.getString("alumniGraduateYearMaster");
                    j++;
                }
                resultMaster.close();
                
                for(int k = 0; k < LIMIT_LATEST_YEARS; k++) {
                    AlumniReport alumniReport = new AlumniReport();
                    
                    // 0 - Diploma, 1 - Bachelor, 2 - Master
                    alumniReport.setAlumniGraduateYearDiploma(years[0][k].substring(0, 4));
                    alumniReport.setAlumniGraduateYearBachelor(years[1][k].substring(0, 4));
                    alumniReport.setAlumniGraduateYearMaster(years[2][k].substring(0, 4));
                    
                    // 0 - Diploma, 1 - Bachelor, 2 - Master
                    alumniReport.setTotalAlumniGraduateYearDiploma(totalAlumniPerYear[0][k]);
                    alumniReport.setTotalAlumniGraduateYearBachelor(totalAlumniPerYear[1][k]);
                    alumniReport.setTotalAlumniGraduateYearMaster(totalAlumniPerYear[2][k]);
                    
                    alumniReports.add(alumniReport);
                }
                
                return alumniReports;
            }
            catch(SQLException e) {
                System.out.println(e);
            }
            return null;
        }
    
        private void getAlumniNationality(AlumniReport alumniReport) {
            
            PreparedStatement preparedStatementMalaysian = null;
            PreparedStatement preparedStatementNonMalaysian = null;
            
            try {
                String queryMalaysian = "SELECT COUNT(alumniID) FROM alumni WHERE lower(alumniAddressCountry) = 'malaysia';";
                
                preparedStatementMalaysian = JDBCUtility.getCon().prepareStatement(queryMalaysian);
                
                ResultSet resultMalaysian = preparedStatementMalaysian.executeQuery();
                
                int malaysian = 0;
                if(resultMalaysian.next()) {
                    malaysian = resultMalaysian.getInt("COUNT(alumniID)");
                }
                
                String queryNonMalaysian = "SELECT COUNT(alumniID) FROM alumni WHERE lower(alumniAddressCountry) != 'malaysia';";
                
                preparedStatementNonMalaysian = JDBCUtility.getCon().prepareStatement(queryNonMalaysian);
                
                ResultSet resultNonMalaysian = preparedStatementNonMalaysian.executeQuery();
                
                int nonMalaysian = 0;
                if(resultNonMalaysian.next()) {
                    nonMalaysian = resultNonMalaysian.getInt("COUNT(alumniID)");
                }
                
                alumniReport.setTotalMalaysianAlumni(malaysian);
                alumniReport.setTotalNonMalaysianAlumni(nonMalaysian);
            }
            catch(SQLException ex) {
                
            }
        }
        
        private void getAlumniStatus(AlumniReport alumniReport) {
            
            PreparedStatement preparedStatementQualifiedArchitect = null;
            PreparedStatement preparedStatementNotQualifiedArchitect = null;
            
            try {
                String queryQualifiedArchitect = "SELECT COUNT(alumniProfStatus) FROM alumni WHERE lower(alumniProfStatus) = 'sir';";
                
                preparedStatementQualifiedArchitect = JDBCUtility.getCon().prepareStatement(queryQualifiedArchitect);
                
                ResultSet resultQualifiedArchitect = preparedStatementQualifiedArchitect.executeQuery();
                
                int qualifiedArchitect = 0;
                if(resultQualifiedArchitect.next()) {
                    qualifiedArchitect = resultQualifiedArchitect.getInt("COUNT(alumniProfStatus)");
                }
                
                String queryNotNotQualifiedArchitect = "SELECT COUNT(alumniProfStatus) FROM alumni WHERE lower(alumniProfStatus) != 'sir';";
                
                preparedStatementNotQualifiedArchitect = JDBCUtility.getCon().prepareStatement(queryNotNotQualifiedArchitect);
                
                ResultSet resultNotQualifiedArchitect = preparedStatementNotQualifiedArchitect.executeQuery();
                
                int notQualifiedArchitect = 0;
                if(resultNotQualifiedArchitect.next()) {
                    notQualifiedArchitect = resultNotQualifiedArchitect.getInt("COUNT(alumniProfStatus)");
                }
                
                alumniReport.setTotalAlumniDoNotHaveProfessionalStatus(notQualifiedArchitect);
                alumniReport.setTotalAlumniHaveProfessionalStatus(qualifiedArchitect);
            }
            catch(SQLException ex) {
                System.out.println(ex);
            }
        }
        
        private void getTotalAlumni(AlumniReport alumniReport) {
            
            PreparedStatement preparedStatementTotalDiploma = null;
            PreparedStatement preparedStatementTotalBachelor = null;
            PreparedStatement preparedStatementTotalMaster = null;
            
            try {
                String queryTotalDiploma = "SELECT COUNT(`alumniGraduateYearDiploma`) FROM `alumni` WHERE `alumniGraduateYearDiploma` != 0000;";
                String queryTotalBachelor = "SELECT COUNT(`alumniGraduateYearBachelor`) FROM `alumni` WHERE `alumniGraduateYearBachelor` != 0000;";
                String queryTotalMaster = "SELECT COUNT(`alumniGraduateYearMaster`) FROM `alumni` WHERE `alumniGraduateYearMaster` != 0000;";
                
                preparedStatementTotalDiploma = JDBCUtility.getCon().prepareStatement(queryTotalDiploma);
                preparedStatementTotalBachelor = JDBCUtility.getCon().prepareStatement(queryTotalBachelor);
                preparedStatementTotalMaster = JDBCUtility.getCon().prepareStatement(queryTotalMaster);
                
                ResultSet resultTotalDiploma = preparedStatementTotalDiploma.executeQuery();
                ResultSet resultTotalBachelor = preparedStatementTotalBachelor.executeQuery();
                ResultSet resultTotalMaster = preparedStatementTotalMaster.executeQuery();
                
                if(resultTotalDiploma.next() && resultTotalBachelor.next() && resultTotalMaster.next()) {
                    alumniReport.setTotalAlumniGraduateYearDiploma(resultTotalDiploma.getInt("COUNT(`alumniGraduateYearDiploma`)"));
                    alumniReport.setTotalAlumniGraduateYearBachelor(resultTotalBachelor.getInt("COUNT(`alumniGraduateYearBachelor`)"));
                    alumniReport.setTotalAlumniGraduateYearMaster(resultTotalMaster.getInt("COUNT(`alumniGraduateYearMaster`)"));
                }
            }
            catch(SQLException ex) {
                System.out.println(ex);
            }
        }
        
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestType = request.getParameter("requestType");
        
        if(requestType == null || requestType.length() == 0) {
            
        }
        else if(requestType.equalsIgnoreCase("viewDashboard")) {
            AlumniReport alumniReport = new AlumniReport();

            request.setAttribute("alumniReports", getAlumniGraduateYearFromDatabase());
            request.setAttribute("eventReports", getEventReportsFromDatabase());

            getAlumniNationality(alumniReport);
            getAlumniStatus(alumniReport);
            getTotalAlumni(alumniReport);

            request.setAttribute("alumniReport", alumniReport);

            request.getRequestDispatcher("/WEB-INF/reporting/jsp/views/dashboard.jsp").forward(request, response);
        }               
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()){
            ArrayList<AlumniReport> alumnis = getInfoFromDatabase();
            Gson gson = new Gson();
            String jsonString = gson.toJson(alumnis);
            
            out.println(jsonString);
            
            out.close();
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
