/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.alumni;

import com.controllers.alumni.adapter.Route;
import com.controllers.alumni.singleton.AlumniPageList;
import static com.controllers.alumni.singleton.AlumniPageList.ALUMNI_DEFAULT_PROFILE_PICTURE;
import com.controllers.alumni.singleton.AlumniRequestTypeList;
import com.controllers.alumni.singleton.AlumniSQLStatementList;
import com.jdbc.utility.JDBCUtility;
import com.models.alumni.Alumni;
import com.google.gson.Gson;
import com.models.user.User;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author PC
 */
@WebServlet("/mobile-api/alumniList")
public class AlumniController extends HttpServlet {

    private static ArrayList<Alumni> alumniList;

    private static Alumni currentAlumni;

    private static int totalAlumni = 0;

    private static int totalPages;
    private final int TOTAL_ALUMNI_PER_PAGE = 10;
    private static int currentPage = 1;

    private static final Comparator<Alumni> ASC_ALUMNI_NAME;
    private static final Comparator<Alumni> DESC_ALUMNI_NAME;

    // We initialize static variables inside a static block.
    static {
        ASC_ALUMNI_NAME = (Alumni alumni1, Alumni alumni2) -> alumni1.getName().compareTo(alumni2.getName());
        DESC_ALUMNI_NAME = (Alumni alumni1, Alumni alumni2) -> alumni2.getName().compareTo(alumni1.getName());
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (alumniList == null || alumniList.isEmpty()) {
                getAllAlumniInfoFromDatabase();
            }

            String requestType = request.getParameter("requestType");

            if (null == requestType) {
                out.println("<script>alert('requestType is null');</script>");
            } else switch (requestType) {
                case AlumniRequestTypeList.REQUEST_TYPE_VIEW_ALUMNI_LIST:
                    if (getAllAlumniInfoFromDatabase()) {
                        sortAlumniByNameAscendingOrder();
                        goToPage(1);
                        processViewAlumniList(request, response);
                    } else {
                        out.println("<script>alert('Error Happen when retrieving all of the alumni information from database');</script>");
                    }   break;
                case AlumniRequestTypeList.REQUEST_TYPE_UPDATE_ALUMN_PROFILE_PICTURE:
                    String uploadButton = request.getParameter("upload-btn");
                    String removeButton = request.getParameter("remove-btn");
                    String message = "";
                    if(uploadButton != null) {
                        message = processUpdateAlumniProfilePicture(request, response);
                    }
                    else if(removeButton != null) {
//                        final String pathString = request.getScheme() + "://" + "alumni-module.herokuapp.com" + "/assets/img/alumni/profile/default.png";
//                        final String pathString = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/assets/img/alumni/profile/default.png";
//                        final String pathString = request.getContextPath() + "/assets/img/alumni/profile/default.png";
//                        
//                        Path path = Paths.get(pathString);                  
//                        byte[] fileContent = Files.readAllBytes(defaultPicture.toPath());
//                        byte[] fileContent = Files.readAllBytes(path);
                        byte[] fileContent = java.util.Base64.getDecoder().decode(ALUMNI_DEFAULT_PROFILE_PICTURE);
                        
                        if(saveUpdatedProfilePictureIntoDatabase(fileContent)) {
                            String base64Image = Base64.getEncoder().encodeToString(fileContent);
                            currentAlumni.setAlumniProfilePicture(base64Image);
                            message = "Successfully restore default picture";
                        }
                        else {
                            message = "Failed to restore default picture";
                        }
                    }   
                    viewOverlay(request, response, message, AlumniRequestTypeList.REQUEST_TYPE_MANAGE_SELECTED_ALUMNI_INFO);
                    setAttributesForCurrentAlumni(request, response, "update");
                    new Route().includePage(request, response, AlumniPageList.MANAGE_ALUMNI_INFO_PAGE);
                    
                    break;
                case AlumniRequestTypeList.REQUEST_TYPE_MANAGE_ALUMNUS_ALUMNA_INFO:
                    if (setCurrentAlumni(request)) {
                        viewManageInfoPage(request, response);
                    } else {
                        HttpSession session = request.getSession();
                        
                        String messageForManageAlumniInfoList = "Error happen when retrieving current alumni information with alumniID =" + ((User)session.getAttribute("user")).getUserID() + " from database<br />";
                        
                        viewOverlay(request, response, messageForManageAlumniInfoList, AlumniRequestTypeList.REQUEST_TYPE_MANAGE_ALUMNUS_ALUMNA_INFO);
                    }   break;
                case AlumniRequestTypeList.REQUEST_TYPE_MANAGE_SELECTED_ALUMNI_INFO:
                    processManageSelectedAlumniInfo(request, response);
                    viewManageInfoPage(request, response);
                    break;
                case AlumniRequestTypeList.REQUEST_TYPE_DELETE_UPDATE_ALUMNI_INFO:
                    String btnUpdateSelected = request.getParameter("update-btn");
                    String btnDeleteSelected = request.getParameter("delete-btn");
                    if (btnUpdateSelected != null) {
                        processViewUpdateAlumniInfoPage(request, response);
                    } else if (btnDeleteSelected != null) {
                        processViewDeleteAlumniInfoPage(request, response);
                    }   break;
                case AlumniRequestTypeList.REQUEST_TYPE_UPDATE_ALUMNI_INFO:
                    String btnSaveSelected = request.getParameter("save-btn");
                    String btnCancelSelected = request.getParameter("cancel-btn");
                    String messageUpdateCancelInfo = "";
                    if (btnSaveSelected != null) {
                        if(saveUpdatedInfoIntoDatabase(request, response)) {
                            messageUpdateCancelInfo = "Successfully update the information";
                        }else {
                            messageUpdateCancelInfo = "Failed update the information";
                        }
                    } else if (btnCancelSelected != null) {
                        messageUpdateCancelInfo = "Any changes made have been reverted";
                    }   
                    viewOverlay(request, response, messageUpdateCancelInfo, AlumniRequestTypeList.REQUEST_TYPE_MANAGE_SELECTED_ALUMNI_INFO);
                    setAttributesForCurrentAlumni(request, response, "view");
                    new Route().includePage(request, response, AlumniPageList.MANAGE_ALUMNI_INFO_PAGE);
                    
                    break;
                case AlumniRequestTypeList.REQUEST_TYPE_CONFIRMATION_TO_DELETE_ALUMNI_INFO:
                    String btnYesSelected = request.getParameter("yes-btn");
                    String btnNoSelected = request.getParameter("no-btn");
                    if (btnYesSelected != null) {
                        String messageDeleteConfirmation = "";
                        
                        if (deleteAlumniAccountFromDatabase(request, response)) {
                            messageDeleteConfirmation = "Successfully delete alumni";
                        } else {
                            messageDeleteConfirmation = "Failed to delete alumni";
                        }
                        
                        viewOverlay(request, response, messageDeleteConfirmation, AlumniRequestTypeList.REQUEST_TYPE_VIEW_ALUMNI_LIST);
                        setContentForAlumniList(request);
                        
                        new Route().includePage(request, response, AlumniPageList.VIEW_ALUMNI_LIST_INFO_PAGE);
                    } else if (btnNoSelected != null) {
                        viewManageInfoPage(request, response);
                    }   break;
                case AlumniRequestTypeList.REQUEST_TYPE_VIEW_ALUMNI_LIST_AT_SELECTED_PAGE:
                    goToPage(Integer.parseInt(request.getParameter("pageNumber")));
                    processViewAlumniList(request, response);
                    break;
                case AlumniRequestTypeList.REQUEST_TYPE_FILTER_ALUMNI_INFO:
                    if (request.getParameter("filterReq").equalsIgnoreCase("orderByNameAtoZ")) {
                        sortAlumniByNameAscendingOrder();
                        request.setAttribute("filterReq", "orderByNameAtoZ");
                    } else if (request.getParameter("filterReq").equalsIgnoreCase("orderByNameZtoA")) {
                        sortAlumniByNameDescendingOrder();
                        request.setAttribute("filterReq", "orderByNameZtoA");
                    }   goToPage(1);
                    processViewAlumniList(request, response);
                    break;
                case AlumniRequestTypeList.REQUEST_TYPE_SEARCH_ALUMNI:
                    String searchBtn = request.getParameter("searchBtn");
                    String resetBtn = request.getParameter("resetBtn");
                    if (searchBtn != null) {
                        String searchReq = request.getParameter("searchReq");
                        String searchInfo = request.getParameter("searchInfo");
                        
                        if (searchReq.equalsIgnoreCase("searchByName")) {
                            searchByAlumniName(searchInfo);
                        } else if (searchReq.equalsIgnoreCase("searchByLocationInState")) {
                            searchByAlumniLocationInState(searchInfo);
                        }
                        
                        sortAlumniByNameAscendingOrder();
                        request.setAttribute("filterReq", "orderByNameAtoZ");
                        request.setAttribute("selectedSearchReq", searchReq);
                        goToPage(1);
                        processViewAlumniList(request, response);
                    } else if (resetBtn != null) {
                        if (getAllAlumniInfoFromDatabase()) {
                            sortAlumniByNameAscendingOrder();
                            goToPage(1);
                            processViewAlumniList(request, response);
                        } else {
                            out.println("Error Happen when retrieving all of the alumni information from database");
                        }
                    }   break;
                default:
                    break;
            }
        }
    }
    
    private String processUpdateAlumniProfilePicture(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String message = "";
        
        final String imageFileBase64 = request.getParameter("fileContentBase64");
        final String fileName = request.getParameter("uploadedFileName");

        if(imageFileBase64 == null) {
            if(fileName != null)
                message = "Failed to upload image : " + fileName;
            else 
                message = "Failed to upload image";
        }
        else {
            byte[] imageBytes = java.util.Base64.getDecoder().decode(imageFileBase64);
            
//            InputStream filecontent = imageFilePart.getInputStream();
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int bytesRead = -1;
//
//            while ((bytesRead = filecontent.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);                  
//            }
//
//            byte[] imageBytes = outputStream.toByteArray();

            if(saveUpdatedProfilePictureIntoDatabase(imageBytes)) {
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                currentAlumni.setAlumniProfilePicture(base64Image);
                message = "Successfully upload image into database";
            }
            else {
                message = "Failed to upload image into database";
            }
        }
        
        return message;
    }

    private void viewOverlay(HttpServletRequest request, HttpServletResponse response, String message, String requestType)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("processMessage", message);
        session.setAttribute("requestType", requestType);

        new Route().includePage(request, response, AlumniPageList.PROCESS_STATUS_OVERLAY_PAGE);
    }

    private void searchByAlumniName(String searchInfo) {
        ArrayList<Alumni> searchResults = new ArrayList<>();

        for (int i = 0; i < totalAlumni; i++) {
            if (alumniList.get(i).getName().toUpperCase().contains(searchInfo.toUpperCase())) {
                searchResults.add(alumniList.get(i));
            }
        }

        alumniList = searchResults;

        totalAlumni = searchResults.size();
        totalPages = totalAlumni / TOTAL_ALUMNI_PER_PAGE;

        int remain = totalAlumni % TOTAL_ALUMNI_PER_PAGE;
        if (remain > 0) {
            totalPages += 1;
        }
    }

    private void searchByAlumniLocationInState(String searchInfo) {
        ArrayList<Alumni> searchResults = new ArrayList<>();

        for (int i = 0; i < totalAlumni; i++) {
            if (alumniList.get(i).getAlumniAddressState().toUpperCase().contains(searchInfo.toUpperCase())) {
                searchResults.add(alumniList.get(i));
            }
        }

        alumniList = searchResults;

        totalAlumni = searchResults.size();
        totalPages = totalAlumni / TOTAL_ALUMNI_PER_PAGE;

        int remain = totalAlumni % TOTAL_ALUMNI_PER_PAGE;
        if (remain > 0) {
            totalPages += 1;
        }
    }

    private void sortAlumniByNameAscendingOrder() {
        Alumni[] alumniArray = new Alumni[totalAlumni];

        for (int i = 0; i < totalAlumni; i++) {
            alumniArray[i] = alumniList.get(i);
        }

        Arrays.sort(alumniArray, ASC_ALUMNI_NAME);

        for (int i = 0; i < totalAlumni; i++) {
            alumniList.set(i, alumniArray[i]);
        }
    }

    private void sortAlumniByNameDescendingOrder() {
        Alumni[] alumniArray = new Alumni[totalAlumni];

        for (int i = 0; i < totalAlumni; i++) {
            alumniArray[i] = alumniList.get(i);
        }

        Arrays.sort(alumniArray, DESC_ALUMNI_NAME);

        for (int i = 0; i < totalAlumni; i++) {
            alumniList.set(i, alumniArray[i]);
        }
    }

    private boolean setCurrentAlumni(HttpServletRequest request) {
        if (getAllAlumniInfoFromDatabase()) {
            currentAlumni = null;

            boolean status = false;

            HttpSession session = request.getSession();
            String currentAlumniID = ((User)session.getAttribute("user")).getUserID() ;

            for (int i = 0; i < totalAlumni; i++) {
                if (alumniList.get(i).getAlumniID().equals(currentAlumniID)) {
                    currentAlumni = alumniList.get(i);
                    status = true;
                    break;
                }
                status = false;
            }

            return status;
        } else {
            return false;
        }
    }

    private void processViewUpdateAlumniInfoPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAttributesForCurrentAlumni(request, response, "update");

        new Route().forwardPage(request, response, AlumniPageList.UPDATE_ALUMNI_INFO_PAGE);
    }

    private void processViewDeleteAlumniInfoPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAttributesForCurrentAlumni(request, response, "delete");

        new Route().forwardPage(request, response, AlumniPageList.DELETE_ALUMNI_INFO_PAGE);
    }

    private void viewManageInfoPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (currentAlumni == null) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                out.println("currentAlumni is null");

                return;
            }
        }

        setAttributesForCurrentAlumni(request, response, "view");
        
        new Route().forwardPage(request, response, AlumniPageList.MANAGE_ALUMNI_INFO_PAGE);
    }

//    private void forwardPage(HttpServletRequest request, HttpServletResponse response, String path)
//            throws ServletException, IOException {
//        RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(path);
//        dispatcher.forward(request, response);
//    }
    private void processManageSelectedAlumniInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedAlumniID = request.getParameter("selectedAlumniID");

        for (int i = 0; i < totalAlumni; i++) {
            if (alumniList.get(i).getAlumniID().equals(selectedAlumniID)) {
                currentAlumni = alumniList.get(i);
                break;
            }
        }
    }
    
    private void displaySelectedImage(HttpServletResponse response) 
        throws ServletException, IOException {
        byte[] byteData = Base64.getDecoder().decode(currentAlumni.getAlumniProfilePicture());
        
        response.setContentType("image/jpeg");
        response.setContentType("image/jpg");
        response.setContentType("image/png");
        
        response.getOutputStream().write(byteData);

        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private void setAttributesForCurrentAlumni(HttpServletRequest request, HttpServletResponse response, String manageAlumniProcess)
            throws ServletException, IOException {
        request.setAttribute("id", currentAlumni.getAlumniID());
        request.setAttribute("name", currentAlumni.getName());
        request.setAttribute("phoneNumber", currentAlumni.getPhoneNum());
        request.setAttribute("profilePicture", currentAlumni.getAlumniProfilePicture());
        request.setAttribute("profStatus", currentAlumni.getAlumniProfStatus());
        request.setAttribute("profStatusYearGained", currentAlumni.getAlumniProfStatusYearGained());

        request.setAttribute("alumniAddress1", currentAlumni.getAlumniAddress1());
        request.setAttribute("alumniAddress2", currentAlumni.getAlumniAddress2());
        request.setAttribute("alumniAddressCity", currentAlumni.getAlumniAddressCity());
        request.setAttribute("alumniAddressCountry", currentAlumni.getAlumniAddressCountry());
        request.setAttribute("alumniAddressPostCode", currentAlumni.getAlumniAddressPostCode());
        request.setAttribute("alumniAddressState", currentAlumni.getAlumniAddressState());
        request.setAttribute("email", currentAlumni.getEmail());

        request.setAttribute("batchDiploma", currentAlumni.getAlumniBatchDiploma());
        request.setAttribute("startStudyYearDiploma", currentAlumni.getAlumniStartStudyYearDiploma());
        request.setAttribute("fieldOfSpecializationDiploma", currentAlumni.getAlumniFieldOfSpecializationDiploma());
        request.setAttribute("graduateYearDiploma", currentAlumni.getAlumniGraduateYearDiploma());

        request.setAttribute("batchBachelor", currentAlumni.getAlumniBatchBachelor());
        request.setAttribute("startStudyYearBachelor", currentAlumni.getAlumniStartStudyYearBachelor());
        request.setAttribute("fieldOfSpecializationBachelor", currentAlumni.getAlumniFieldOfSpecializationBachelor());
        request.setAttribute("graduateYearBachelor", currentAlumni.getAlumniGraduateYearBachelor());

        request.setAttribute("batchMaster", currentAlumni.getAlumniBatchMaster());
        request.setAttribute("startStudyYearMaster", currentAlumni.getAlumniStartStudyYearMaster());
        request.setAttribute("fieldOfSpecializationMaster", currentAlumni.getAlumniFieldOfSpecializationMaster());
        request.setAttribute("graduateYearMaster", currentAlumni.getAlumniGraduateYearMaster());

        request.setAttribute("curEmployer", currentAlumni.getAlumniCurEmployer());
        request.setAttribute("curJob", currentAlumni.getAlumniCurJob());
        request.setAttribute("curSalary", currentAlumni.getAlumniCurSalary());

        request.setAttribute("prevEmployer", currentAlumni.getAlumniPrevEmployer());
        request.setAttribute("prevJob", currentAlumni.getAlumniPrevJob());
        request.setAttribute("prevSalary", currentAlumni.getAlumniPrevSalary());

        request.setAttribute("employerAddress1", currentAlumni.getEmployerAddress1());
        request.setAttribute("employerAddress2", currentAlumni.getEmployerAddress2());
        request.setAttribute("employerAddressCity", currentAlumni.getEmployerAddressCity());
        request.setAttribute("employerAddressCountry", currentAlumni.getEmployerAddressCountry());
        request.setAttribute("employerAddressPostCode", currentAlumni.getEmployerAddressPostCode());
        request.setAttribute("employerAddressState", currentAlumni.getEmployerAddressState());
        
        request.setAttribute("manageAlumniProcess", manageAlumniProcess);
    }
    
    private void setContentForAlumniList(HttpServletRequest request)
            throws ServletException, IOException {
        String[] alumniID = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniName = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniEmail = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniState = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniGraduationYearDiploma = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniGraduationYearBachelor = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniGraduationYearMaster = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniProfStatus = new String[TOTAL_ALUMNI_PER_PAGE];
        String[] alumniCurJob = new String[TOTAL_ALUMNI_PER_PAGE];

        int j = currentPage * TOTAL_ALUMNI_PER_PAGE - TOTAL_ALUMNI_PER_PAGE;

        for (int i = 0; i < TOTAL_ALUMNI_PER_PAGE; i++, j++) {
            if (alumniList.isEmpty() || j >= totalAlumni) {
                alumniID[i] = "";
                alumniName[i] = "";
                alumniEmail[i] = "";
                alumniState[i] = "";
                alumniGraduationYearDiploma[i] = "";
                alumniGraduationYearBachelor[i] = "";
                alumniGraduationYearMaster[i] = "";
                alumniProfStatus[i] = "";
                alumniCurJob[i] = "";
                continue;
            }

            alumniID[i] = alumniList.get(j).getAlumniID();
            alumniName[i] = alumniList.get(j).getName();
            alumniEmail[i] = alumniList.get(j).getEmail();
            alumniState[i] = alumniList.get(j).getAlumniAddressState();
            alumniGraduationYearDiploma[i] = Integer.toString(alumniList.get(j).getAlumniGraduateYearDiploma());
            alumniGraduationYearBachelor[i] = Integer.toString(alumniList.get(j).getAlumniGraduateYearBachelor());
            alumniGraduationYearMaster[i] = Integer.toString(alumniList.get(j).getAlumniGraduateYearMaster());
            alumniProfStatus[i] = alumniList.get(j).getAlumniProfStatus();
            alumniCurJob[i] = alumniList.get(j).getAlumniCurJob();
        }

        request.setAttribute("totalAlumni", totalAlumni);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("TOTAL_ALUMNI_PER_PAGE", TOTAL_ALUMNI_PER_PAGE);

        request.setAttribute("alumniIDArray", alumniID);
        request.setAttribute("alumniNameArray", alumniName);
        request.setAttribute("alumniEmailArray", alumniEmail);
        request.setAttribute("alumniStateArray", alumniState);
        request.setAttribute("alumniGraduationYearDiplomaArray", alumniGraduationYearDiploma);
        request.setAttribute("alumniGraduationYearBachelorArray", alumniGraduationYearBachelor);
        request.setAttribute("alumniGraduationYearMasterArray", alumniGraduationYearMaster);
        request.setAttribute("alumniProfStatusArray", alumniProfStatus);
        request.setAttribute("alumniCurJobArray", alumniCurJob);
    }

    private void processViewAlumniList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        setContentForAlumniList(request);

        new Route().forwardPage(request, response, AlumniPageList.VIEW_ALUMNI_LIST_INFO_PAGE);
    }

    private void goToPage(int page) {
        if (totalPages == 0) {
            currentPage = 0;
            return;
        }
        currentPage = page;
    }

//    private void includePage(HttpServletRequest request, HttpServletResponse response, String path)
//            throws ServletException, IOException {
//        RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(path);
//        dispatcher.include(request, response);
//    }
    private boolean getAllAlumniInfoFromDatabase() {
        alumniList = new ArrayList<>();

        boolean status = false;
        try {
            Connection con = JDBCUtility.getCon();

            String statementQuery1 = AlumniSQLStatementList.SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_ALUMNI;

            Statement statement1 = con.createStatement();
            ResultSet result1 = statement1.executeQuery(statementQuery1);

            String statementQuery2 = AlumniSQLStatementList.SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_USER_WHERE_ROLE_IS_ALUMNI;

            Statement statement2 = con.createStatement();
            ResultSet result2 = statement2.executeQuery(statementQuery2);

            while (result1.next() && result2.next()) {
                Alumni alumni = new Alumni();

                alumni.setUserID(result2.getString("userID"));
                alumni.setName(result2.getString("name"));
                alumni.setPhoneNum(result2.getString("phone"));
                alumni.setEmail(result2.getString("email"));
                alumni.setRole(result2.getString("role"));
                alumni.setPassword(result2.getString("password"));
                
                Blob blob = result1.getBlob("alumniProfilePicture");
                 
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);                  
                }
                 
                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                alumni.setAlumniPersonalInfo(result1.getString("alumniID"), result1.getString("alumniProfStatus"),
                        result1.getInt("alumniProfStatusYearGained"), base64Image);

                alumni.setAlumniAddress(result1.getString("alumniAddress1"), result1.getString("alumniAddress2"),
                        result1.getString("alumniAddressCity"), result1.getString("alumniAddressPostCode"),
                        result1.getString("alumniAddressState"), result1.getString("alumniAddressCountry"));

                alumni.setAlumniEducationalInfoDiploma(result1.getInt("alumniGraduateYearDiploma"), result1.getInt("alumniStartStudyYearDiploma"),
                        result1.getString("alumniFieldOfSpecializationDiploma"), result1.getInt("alumniBatchDiploma"));

                alumni.setAlumniEducationalInfoBachelor(result1.getInt("alumniGraduateYearBachelor"), result1.getInt("alumniStartStudyYearBachelor"),
                        result1.getString("alumniFieldOfSpecializationBachelor"), result1.getInt("alumniBatchBachelor"));

                alumni.setAlumniEducationalInfoMaster(result1.getInt("alumniGraduateYearMaster"), result1.getInt("alumniStartStudyYearMaster"),
                        result1.getString("alumniFieldOfSpecializationMaster"), result1.getInt("alumniBatchMaster"));

                alumni.setAlumniEmploymentInfo(result1.getString("alumniPrevJob"), result1.getDouble("alumniPrevSalary"),
                        result1.getString("alumniCurJob"), result1.getDouble("alumniCurSalary"),
                        result1.getString("alumniPrevEmployer"), result1.getString("alumniCurEmployer"));

                alumni.setEmployerAddress(result1.getString("employerAddress1"), result1.getString("employerAddress2"),
                        result1.getString("employerAddressCity"), result1.getString("employerAddressPostCode"),
                        result1.getString("employerAddressState"), result1.getString("employerAddressCountry"));

                alumniList.add(alumni);
                status = true;
            }

            totalAlumni = alumniList.size();
            totalPages = totalAlumni / TOTAL_ALUMNI_PER_PAGE;

            int remain = totalAlumni % TOTAL_ALUMNI_PER_PAGE;
            if (remain > 0) {
                totalPages += 1;
            }

            return true;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(AlumniController.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        } 
        return status;
    }

    private ArrayList<Alumni> getAllAlumniInfoFromDatabaseForMobile() throws IOException {
        ArrayList<Alumni> alumniUserList = new ArrayList<>();

        try {
            Connection con = JDBCUtility.getCon();

            String statementQuery1 = AlumniSQLStatementList.SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_ALUMNI;

            Statement statement1 = con.createStatement();
            ResultSet result1 = statement1.executeQuery(statementQuery1);

            String statementQuery2 = AlumniSQLStatementList.SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_USER_WHERE_ROLE_IS_ALUMNI;

            Statement statement2 = con.createStatement();
            ResultSet result2 = statement2.executeQuery(statementQuery2);

            while (result1.next() && result2.next()) {
                Alumni alumni = new Alumni();
                
                alumni.setUserID(result2.getString("userID"));
                alumni.setName(result2.getString("name"));
                alumni.setPhoneNum(result2.getString("phone"));
                alumni.setEmail(result2.getString("email"));
                alumni.setRole(result2.getString("role"));
                alumni.setPassword(result2.getString("password"));
                
                Blob blob = result1.getBlob("alumniProfilePicture");
                 
                ByteArrayOutputStream outputStream;
                String base64Image;
                try (InputStream inputStream = blob.getBinaryStream()) {
                    outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {                  
                        outputStream.write(buffer, 0, bytesRead);
                    }   byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                }
                outputStream.close();
                
                alumni.setAlumniPersonalInfo(result1.getString("alumniID"), result1.getString("alumniProfStatus"),
                        result1.getInt("alumniProfStatusYearGained"), base64Image);

                alumni.setAlumniAddress(result1.getString("alumniAddress1"), result1.getString("alumniAddress2"),
                        result1.getString("alumniAddressCity"), result1.getString("alumniAddressPostCode"),
                        result1.getString("alumniAddressState"), result1.getString("alumniAddressCountry"));

                alumni.setAlumniEducationalInfoDiploma(result1.getInt("alumniGraduateYearDiploma"), result1.getInt("alumniStartStudyYearDiploma"),
                        result1.getString("alumniFieldOfSpecializationDiploma"), result1.getInt("alumniBatchDiploma"));

                alumni.setAlumniEducationalInfoBachelor(result1.getInt("alumniGraduateYearBachelor"), result1.getInt("alumniStartStudyYearBachelor"),
                        result1.getString("alumniFieldOfSpecializationBachelor"), result1.getInt("alumniBatchBachelor"));

                alumni.setAlumniEducationalInfoMaster(result1.getInt("alumniGraduateYearMaster"), result1.getInt("alumniStartStudyYearMaster"),
                        result1.getString("alumniFieldOfSpecializationMaster"), result1.getInt("alumniBatchMaster"));

                alumni.setAlumniEmploymentInfo(result1.getString("alumniPrevJob"), result1.getDouble("alumniPrevSalary"),
                        result1.getString("alumniCurJob"), result1.getDouble("alumniCurSalary"),
                        result1.getString("alumniPrevEmployer"), result1.getString("alumniCurEmployer"));

                alumni.setEmployerAddress(result1.getString("employerAddress1"), result1.getString("employerAddress2"),
                        result1.getString("employerAddressCity"), result1.getString("employerAddressPostCode"),
                        result1.getString("employerAddressState"), result1.getString("employerAddressCountry"));

                alumniUserList.add(alumni);
            }

            return alumniUserList;

        } catch (SQLException ex) {
            Logger.getLogger(AlumniController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private boolean saveUpdatedInfoIntoDatabase(HttpServletRequest request, HttpServletResponse response) {
        boolean status = false;
        try {
            String currentAlumniID = currentAlumni.getAlumniID();

            String updatedAlumniProfStatus = request.getParameter("updatedAlumniProfStatus");
            int updatedAlumniProfStatusGainedYear = Integer.parseInt(request.getParameter("updatedAlumniProfStatusGainedYear"));

            String updatedAlumniAddress1 = request.getParameter("updatedAlumniAddress1");
            String updatedAlumniAddress2 = request.getParameter("updatedAlumniAddress2");
            String updatedAlumniAddressCity = request.getParameter("updatedAlumniAddressCity");
            String updatedAlumniAddressPostCode = request.getParameter("updatedAlumniAddressPostCode");
            String updatedAlumniAddressState = request.getParameter("updatedAlumniAddressState");
            String updatedAlumniAddressCountry = request.getParameter("updatedAlumniAddressCountry");

            String updatedAlumniFieldOfSpecializationDiploma = request.getParameter("updatedAlumniFieldOfSpecializationDiploma");
            int updatedAlumniStartStudyDiploma = Integer.parseInt(request.getParameter("updatedAlumniStartStudyDiploma"));
            int updatedAlumniBatchDiploma = Integer.parseInt(request.getParameter("updatedAlumniBatchDiploma"));
            int updatedAlumniGraduateYearDiploma = Integer.parseInt(request.getParameter("updatedAlumniGraduateYearDiploma"));

            String updatedAlumniFieldOfSpecializationBachelor = request.getParameter("updatedAlumniFieldOfSpecializationBachelor");
            int updatedAlumniStartStudyBachelor = Integer.parseInt(request.getParameter("updatedAlumniStartStudyBachelor"));
            int updatedAlumniBatchBachelor = Integer.parseInt(request.getParameter("updatedAlumniBatchBachelor"));
            int updatedAlumniGraduateYearBachelor = Integer.parseInt(request.getParameter("updatedAlumniGraduateYearBachelor"));

            String updatedAlumniFieldOfSpecializationMaster = request.getParameter("updatedAlumniFieldOfSpecializationMaster");
            int updatedAlumniStartStudyMaster = Integer.parseInt(request.getParameter("updatedAlumniStartStudyMaster"));
            int updatedAlumniBatchMaster = Integer.parseInt(request.getParameter("updatedAlumniBatchMaster"));
            int updatedAlumniGraduateYearMaster = Integer.parseInt(request.getParameter("updatedAlumniGraduateYearMaster"));

            String updatedAlumniCurJob = request.getParameter("updatedAlumniCurJob");
            String updatedAlumniPrevJob = request.getParameter("updatedAlumniPrevJob");
            String updatedAlumniCurEmployer = request.getParameter("updatedAlumniCurEmployer");
            String updatedAlumniPrevEmployer = request.getParameter("updatedAlumniPrevEmployer");
            double updatedAlumniCurSalary = Double.parseDouble(request.getParameter("updatedAlumniCurSalary"));
            double updatedAlumniPrevSalary = Double.parseDouble(request.getParameter("updatedAlumniPrevSalary"));

            String updatedEmployerAddress1 = request.getParameter("updatedEmployerAddress1");
            String updatedEmployerAddress2 = request.getParameter("updatedEmployerAddress2");
            String updatedEmployerAddressCity = request.getParameter("updatedEmployerAddressCity");
            String updatedEmployerAddressPostCode = request.getParameter("updatedEmployerAddressPostCode");
            String updatedEmployerAddressState = request.getParameter("updatedEmployerAddressState");
            String updatedEmployerAddressCountry = request.getParameter("updatedEmployerAddressCountry");

            Connection con = JDBCUtility.getCon();

            String statementQuery = AlumniSQLStatementList.SQL_STATEMENT_UPDATE_SELECTED_ALUMNI_INFO;
            PreparedStatement statement = con.prepareStatement(statementQuery);

            statement.setString(1, updatedAlumniAddress1);
            statement.setString(2, updatedAlumniAddress2);
            statement.setString(3, updatedAlumniAddressCity);
            statement.setString(4, updatedAlumniAddressCountry);
            statement.setString(5, updatedAlumniAddressPostCode);
            statement.setString(6, updatedAlumniAddressState);
            statement.setInt(7, updatedAlumniBatchDiploma);
            statement.setInt(8, updatedAlumniBatchBachelor);
            statement.setInt(9, updatedAlumniBatchMaster);
            statement.setString(10, updatedAlumniCurEmployer);
            statement.setString(11, updatedAlumniCurJob);
            statement.setDouble(12, updatedAlumniCurSalary);
            statement.setInt(13, updatedAlumniStartStudyDiploma);
            statement.setInt(14, updatedAlumniStartStudyBachelor);
            statement.setInt(15, updatedAlumniStartStudyMaster);
            statement.setString(16, updatedAlumniFieldOfSpecializationDiploma);
            statement.setString(17, updatedAlumniFieldOfSpecializationBachelor);
            statement.setString(18, updatedAlumniFieldOfSpecializationMaster);
            statement.setInt(19, updatedAlumniGraduateYearDiploma);
            statement.setInt(20, updatedAlumniGraduateYearBachelor);
            statement.setInt(21, updatedAlumniGraduateYearMaster);
            statement.setString(22, updatedAlumniPrevEmployer);
            statement.setString(23, updatedAlumniPrevJob);
            statement.setDouble(24, updatedAlumniPrevSalary);
            statement.setString(25, updatedAlumniProfStatus);
            statement.setInt(26, updatedAlumniProfStatusGainedYear);
            statement.setString(27, updatedEmployerAddress1);
            statement.setString(28, updatedEmployerAddress2);
            statement.setString(29, updatedEmployerAddressCity);
            statement.setString(30, updatedEmployerAddressCountry);
            statement.setString(31, updatedEmployerAddressPostCode);
            statement.setString(32, updatedEmployerAddressState);
            statement.setString(33, currentAlumniID);

            int result = statement.executeUpdate();

            if (result == 0) {
                status = false;
            } else if (result > 0) {
                status = true;
            }

            if (status) {
                currentAlumni.setAlumniPersonalInfo(currentAlumniID, updatedAlumniProfStatus,
                        updatedAlumniProfStatusGainedYear, currentAlumni.getAlumniProfilePicture());

                currentAlumni.setAlumniAddress(updatedAlumniAddress1, updatedAlumniAddress2, updatedAlumniAddressCity,
                        updatedAlumniAddressPostCode, updatedAlumniAddressState, updatedAlumniAddressCountry);

                currentAlumni.setAlumniEducationalInfoDiploma(updatedAlumniGraduateYearDiploma, updatedAlumniStartStudyDiploma, updatedAlumniFieldOfSpecializationDiploma, updatedAlumniBatchDiploma);
                currentAlumni.setAlumniEducationalInfoBachelor(updatedAlumniGraduateYearBachelor, updatedAlumniStartStudyBachelor, updatedAlumniFieldOfSpecializationBachelor, updatedAlumniBatchBachelor);
                currentAlumni.setAlumniEducationalInfoMaster(updatedAlumniGraduateYearMaster, updatedAlumniStartStudyMaster, updatedAlumniFieldOfSpecializationMaster, updatedAlumniBatchMaster);

                currentAlumni.setAlumniEmploymentInfo(updatedAlumniPrevJob, updatedAlumniPrevSalary, updatedAlumniCurJob, updatedAlumniCurSalary,
                        updatedAlumniPrevEmployer, updatedAlumniCurEmployer);

                currentAlumni.setEmployerAddress(updatedEmployerAddress1, updatedEmployerAddress2, updatedEmployerAddressCity,
                        updatedEmployerAddressPostCode, updatedEmployerAddressState, updatedEmployerAddressCountry);

                for (int i = 0; i < totalAlumni; i++) {
                    if (alumniList.get(i).getAlumniID().equals(currentAlumniID)) {
                        alumniList.set(i, currentAlumni);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumniController.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        } 
        return status;
    }
  
    private boolean saveUpdatedProfilePictureIntoDatabase(byte[] imageBytes) {
        
        try (Connection connection = JDBCUtility.getCon()) {
            Blob imageBlob = connection.createBlob();
            imageBlob.setBytes(1, imageBytes);

            PreparedStatement statement = connection.prepareStatement(AlumniSQLStatementList.SQL_STATEMENT_UPDATE_SELECTED_ALUMNI_PROFILE_PICTURE);

            statement.setBlob(1, imageBlob);

            statement.setString(2, currentAlumni.getAlumniID());

            if(statement.executeUpdate() > 0) {
                JDBCUtility.jdbcConClose();
                return true;
            }
            JDBCUtility.jdbcConClose();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(AlumniController.class.getName()).log(Level.INFO, ex.getMessage());
        } 
        return false;
    }

    private boolean deleteAlumniAccountFromDatabase(HttpServletRequest request, HttpServletResponse response) {
        boolean status = false;
        Connection con;
        try {
            String currentAlumniID = currentAlumni.getAlumniID();
            con = JDBCUtility.getCon();

            String statementQuery1 = AlumniSQLStatementList.SQL_STATEMENT_DELETE_SELECTED_ALUMNI;
            String statementQuery2 = AlumniSQLStatementList.SQL_STATEMENT_DELETE_SELECTED_USER;

            PreparedStatement statement1 = con.prepareStatement(statementQuery1);
            PreparedStatement statement2 = con.prepareStatement(statementQuery2);

            statement1.setString(1, currentAlumniID);
            statement2.setString(1, currentAlumniID);

            int result1 = statement1.executeUpdate();
            int result2 = statement2.executeUpdate();

            if (result1 == 0 || result2 == 0) {
                status = false;
            } else if (result1 > 0 && result2 > 0) {
                status = true;
            }

            if (status) {
                for (int i = 0; i < totalAlumni; i++) {
                    if (alumniList.get(i).getAlumniID() == null ? currentAlumniID == null : alumniList.get(i).getAlumniID().equals(currentAlumniID)) {
                        alumniList.remove(i);
                        break;
                    }
                }
                currentAlumni = null;
                --totalAlumni;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumniController.class.getName()).log(Level.SEVERE, null, ex);
            status = false;
        }
        return status;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestType = request.getParameter("requestType");
        
        if(requestType != null && requestType.equals("requestImage")) {
            displaySelectedImage(response);
        }
        else {
            response.setContentType("text/html");
            try (PrintWriter out = response.getWriter()) {
                ArrayList<Alumni> alumni = getAllAlumniInfoFromDatabaseForMobile();
                Gson gson = new Gson();
                String jsonString = gson.toJson(alumni);

                out.println(jsonString);

                out.close();
            }
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
