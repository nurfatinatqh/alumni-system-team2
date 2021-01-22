/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.user;

import static com.controllers.alumni.singleton.AlumniPageList.ALUMNI_DEFAULT_PROFILE_PICTURE;
import com.models.user.factoryMethod.Creator;
import com.models.user.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.jdbc.utility.JDBCUtility;
import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;


@WebServlet(name = "ManageUserController", urlPatterns = {"/ManageUserController"})
public class ManageUserController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        
        String option = request.getParameter("option");
        if (option==null) {
            option = "";
        }
        
        switch (option) {
        //On success, you can display a message to user on Home page
            case "register":
                {
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phoneNum = request.getParameter("phoneNum");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");
                    User userRegistered;
                    
                    userRegistered = submitData(request, name, email, password, role, phoneNum, option);
                    
                    if (userRegistered!=null){
                        session.setAttribute("user", userRegistered);
                        sendReturnMessage("SUCCESS", request, response);
                    }
                    else {
                        sendReturnMessage("USER ALREADY EXIST", request, response);
                    }
                    break; 
                }
        //On success, you can display a message to user on Home page
            case "login":
                {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String status = "";
                    try {
                        ResultSet rs = submitLoginInfo(email, password);

                        if (rs != null && rs.first()==true) {
                            rs.previous();
                            while (rs.next())
                            {
                                String name= rs.getString("name");
                                email= rs.getString("email");
                                String phoneNum = rs.getString("phone");
                                String role = rs.getString("role");
                                password = rs.getString("password");
                                String userID = rs.getString("userID");
                                Creator c = new Creator();
                                User u = c.FactoryMethod(role);
                                u.setUserDetails(userID, name, email, password, role, phoneNum);
                                
                                session.setAttribute("user", u);
                            }
                            sendReturnMessage("SUCCESS", request, response);
                        }
                        else {
                            sendReturnMessage("INVALID ACCOUNT", request, response);
                        }
                    } 
                    catch (SQLException ex) {
                        sendReturnMessage("ERROR CONNECTION", request, response);
                    }
                    break;
                }
            case "editProfile":
                {
                    sendEditRequest(response, request);
                    break;
                }
            case "viewHomepage":
                {
                    if(session.getAttribute("user") != null) {
                        request.getRequestDispatcher("WEB-INF/user/jsp/Homepage.jsp").forward(request, response);
                    }
                    break;
                }
            case "viewManageUserPage":
                {
                    if(session.getAttribute("user") != null) {
                        request.getRequestDispatcher("WEB-INF/user/jsp/ManageUserPage.jsp").forward(request, response);
                    }
                    break;
                }
            case "viewLoginPage":
                {
                    request.getRequestDispatcher("WEB-INF/user/jsp/LoginPage.jsp").forward(request, response);
                    break;
                }
            case "viewRegisterPage":
                {
                    request.getRequestDispatcher("WEB-INF/user/jsp/RegisterPage.jsp").forward(request, response);
                    break;
                }
            case "viewDeleteUserPage":
                {
                    if(session.getAttribute("user") != null) {
                        if(request.getParameter("status") != null && request.getParameter("status").length() > 0 && request.getParameter("name") != null && request.getParameter("name").length() > 0)
                            request.getRequestDispatcher("WEB-INF/user/jsp/DeleteUserPage.jsp?status="+ request.getParameter("status") +"&name=" + request.getParameter("name")).forward(request, response);
                        else
                            request.getRequestDispatcher("WEB-INF/user/jsp/DeleteUserPage.jsp").forward(request, response);
                    }
                    break;
                }
            case "viewEditProfilePage":
                {
                    if(session.getAttribute("user") != null) {
                        request.getRequestDispatcher("WEB-INF/user/jsp/EditProfilePage.jsp").forward(request, response);
                    }
                    break;
                }
            case "viewAddUserPage":
                {
                    if(session.getAttribute("user") != null) {
                        request.getRequestDispatcher("WEB-INF/user/jsp/AddUserPage.jsp").forward(request, response);
                    }
                    break;
                }
            case "submitEditData":
                {
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phoneNum = request.getParameter("phoneNum");
                    String password = request.getParameter("password");
                    String password2 = request.getParameter("password2");
                    String role = request.getParameter("role");
                    String userID = request.getParameter("userID");
                    User u;
                    
                        u = submitData(request, name, email, password, role, phoneNum, option);
                    
                        if (u!=null){
                            User a =(User)session.getAttribute("user");  
                            a.setUserDetails(userID, name, email, password, role, phoneNum);
                            session.setAttribute("user", a);
                            sendReturnMessage("UPDATE SUCCESS", request, response);
                        }
                        else
                            sendReturnMessage("UPDATE SUCCESS", request, response);
                    break;
                }
            case "addUser":
                {
                    sendRequest(option, response, request);
                    break;
                }
            case "submitNewUser":
                {
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String phoneNum = request.getParameter("phoneNum");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");
                    User status;
                    option = "register";
                    status = submitData(request, name, email, password, role, phoneNum, option);
                    
                    if (status!=null){
                        sendReturnMessage("USER ADDED SUCCESSFULLY", request, response);
                    }
                    else {
                        sendReturnMessage("USER ADDED FAILED", request, response);
                    }
                    break; 
                }
            case "display":
                {
                
                 ArrayList users = new ArrayList();
                 ResultSet rs = getAllUser();
                try{
                 while (rs.next()) 
                {
                    String name= rs.getString("name");
                    String email= rs.getString("email");
                    String phoneNum = rs.getString("phone");
                    String role = rs.getString("role");
                    String id= rs.getString("userID");
    
                    //create beans
                    Creator c = new Creator();
                    User user = c.FactoryMethod(role);
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhoneNum(phoneNum);
                    user.setRole(role);
                    user.setUserID(id);
                    
                    users.add(user);
             
                    session.setAttribute("users", users);

                } sendReturnMessage("SUCCESS1", request, response);
                 }
                 catch (SQLException ex) {
                        sendReturnMessage("DELETE ERROR", request, response);
                    }
                break;
                
                }
                
                case "delete":
            {
                String strId = request.getParameter("id");

                requestDelete(strId, request, response);
                break;
            }
            case "confirmDelete":
            {
                String userID = request.getParameter("id");
                boolean status= deleteUser(userID);
                if(status) {
                    sendReturnMessage("DELETE SUCCESS", request, response);
                }
                else {
                    sendReturnMessage("DELETE ERROR", request, response);
                }
                break;
            }
            case "logout":
                {
                    session.invalidate();
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('User logout - session terminated!');");
                        out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewLoginPage'");
                        out.println("</script>");
                    }   break;
                }
            default:
                break;
        }
    }
    
    public void requestDelete(String name, HttpServletRequest request, HttpServletResponse response){
        try (PrintWriter out = response.getWriter()) {
            out.println("<script>");
            out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewDeleteUserPage&status=true&name=" + name + "'");
            out.println("</script>");
        } 
        catch (IOException ex) {
            Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    public void sendEditRequest(HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<script>");
            out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewEditProfilePage'");
            out.println("</script>");
        }   
    }

	
    public void sendRequest(String option, HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        if (option.equals("addUser")) {
            try (PrintWriter out = response.getWriter()) {
            out.println("<script>");
            out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewAddUserPage'");
            out.println("</script>");
            }   
        }
        else if (option.equals("deleteUser")){
            
        }
    }

    public void sendReturnMessage(String text, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        switch (text) {
            case "SUCCESS":
            {
                request.getRequestDispatcher("/WEB-INF/user/jsp/Homepage.jsp").forward(request, response);
                break; 
            }
             case "SUCCESS1":
                    {
                        request.getRequestDispatcher("/WEB-INF/user/jsp/DeleteUserPage.jsp").forward(request, response);
                        break; 
                    }
            case "USER ALREADY EXIST":
            {
                request.setAttribute("errMessage", text);
                request.getRequestDispatcher("/WEB-INF/user/jsp/RegisterPage.jsp").forward(request, response);
                break;
            }
            case "USER ADDED SUCCESSFULLY":
            {
                try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('USER ADDED SUCCESSFULLY!');");
                        out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewAddUserPage'");
                        out.println("</script>");
                } 
                break; 
            }
            case "USER ADDED FAILED":
            {
                try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('USER ADDED FAILED!');");
                        out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewAddUserPage'");
                        out.println("</script>");
                } 
                break;
            }
            case "INVALID ACCOUNT":
            {
                request.setAttribute("errMessage", text);
                request.getRequestDispatcher("/WEB-INF/user/jsp/LoginPage.jsp").forward(request, response);
                break;
            }
            case "ERROR CONNECTION":
            {
                request.setAttribute("errMessage", text);
                request.getRequestDispatcher("/WEB-INF/user/jsp/LoginPage.jsp").forward(request, response);
                break;
            }
            case "UPDATE ERROR":
            {
                try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('UPDATE ERROR!');");
                        out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewEditProfilePage'");
                        out.println("</script>");
                } 
                break;
            }
            case "UPDATE SUCCESS":
            {
                try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('UPDATE SUCCESS!');");
                        out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewEditProfilePage'");
                        
                        out.println("</script>");
                } 
                break;
            }
             case "DELETE SUCCESS":
            {
                request.getRequestDispatcher("ManageUserController?option=display").forward(request, response);
                break;
            }
             case "DELETE ERROR":
            {
                try (PrintWriter out = response.getWriter()) {
                        out.println("<script>");
                        out.println("    alert('DELETE ERROR!');");
                       out.println("    window.location = '" + request.getContextPath() + "/ManageUserController?option=viewDeleteUserPage'");
                        out.println("</script>");
                } 
                break;
            }
            default:
                    doGet(request, response);
        }
    }
    
    public User submitData(HttpServletRequest request, String name, String email, String password, String role, String phoneNum, String option) 
            throws ServletException, IOException
    {
        int i = validateData(name, email, password, role, phoneNum, option);
        Creator c = new Creator();
        User u = c.FactoryMethod(role);
        if (i!=0) {
            if (option.equals("register")){
                try
                {
                    int id=0;
                    Connection con = null;
                    PreparedStatement preparedStatement = null;
                    con = JDBCUtility.getCon();
                    String query = "SELECT * FROM users where email = ?"; //Insert user details into the table 'USERS'
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, email);
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        id = rs.getInt("id");
                    }
                    u.createUser(id, name, email, password, role, phoneNum);
                    query = "UPDATE users SET userID = ? WHERE email=?";
                    preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
                    preparedStatement.setString(1, u.getUserID());
                    preparedStatement.setString(2, email);
                    i = preparedStatement.executeUpdate();
                    
                    if("ALUMNI".equals(u.getRole())){
                        query = "insert into alumni(alumniID, alumniProfilePicture) values (?, ?)";
                        preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
                        preparedStatement.setString(1, u.getUserID());
                        preparedStatement.setBlob(2, getDefaultImageInBlob(request));
                        i = preparedStatement.executeUpdate();
                    }
                    return u;
                }
                catch(SQLException e)
                {
                    return null;
                } 
            }
            else {
                u = c.FactoryMethod(role);
//                u.setUserDetails(name, email, password, role, phoneNum);
                return u;
            }
        }
        else{
            return null;
        }
    }

    public ResultSet submitLoginInfo(String email, String password){
        ResultSet rs = verifyUser(email, password);
        return rs;
    }

    public void submitRequest(boolean choice){

    }
        
    public int validateData(String name, String email, String password, String role, String phoneNum, String option)
    {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        con = JDBCUtility.getCon();
        int i=0;
            
        try
        {
            if (option.equals("register")){
                String query = "insert into users(name,phone,email,role,password) values (?,?,?,?,?)"; //Insert user details into the table 'USERS'
                preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNum);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, role);
                preparedStatement.setString(5, password);
                i= preparedStatement.executeUpdate();
                return i;
            }
            else {
                String query = "UPDATE users SET name = ?, phone = ?, email = ?, password = ? WHERE email=?";
                preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNum);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, email);
                i= preparedStatement.executeUpdate();
                return i;
            }
        }
        catch(SQLException e)
        {
            i=0;
            return i;
        }  
    }

    public ResultSet verifyUser(String email, String password)
    {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs;
        try
        {
            con = JDBCUtility.getCon();
            String query = "SELECT * FROM users where email = ? AND password = ?"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
             
            rs = preparedStatement.executeQuery();
        }
        catch(SQLException e)
        {
            rs = null;
        }  
        return rs;
    }
    
      public ResultSet getAllUser(){
               
                Connection con = null;
                PreparedStatement preparedStatement = null;
                String sqlQuery = "SELECT * FROM users";
                ResultSet rs ;
               
                
            try
            {
                con = JDBCUtility.getCon();
                preparedStatement = con.prepareStatement(sqlQuery);
               
                rs = preparedStatement.executeQuery();
  
            }
   
            catch (java.lang.Exception ex)
            {
                rs=null;
            }
             return rs;
        }
        
        public boolean deleteUser(String id){
                Connection con = null;
                PreparedStatement preparedStatementSelect1 = null;
                PreparedStatement preparedStatementSelect2 = null;
                 
                String sqlDeleteAlumniById = "DELETE FROM alumni" +
                                      " WHERE alumniID = ?";
                String sqlDeleteStudentById = "DELETE FROM users" +
                                      " WHERE userID = ?";
                boolean test=false;
               
            try
            {
                con = JDBCUtility.getCon();
                
                if(id.toUpperCase().contains("ALU")) {
                    preparedStatementSelect1  = con.prepareStatement(sqlDeleteAlumniById);
                    preparedStatementSelect1.setString(1, id); 
                                        
                    if (preparedStatementSelect1.executeUpdate() != 0){
                        test = true;
                    }
                }
                preparedStatementSelect2  = con.prepareStatement(sqlDeleteStudentById);
            
                preparedStatementSelect2.setString(1, id);  
                         
                if (preparedStatementSelect2.executeUpdate() != 0) {
                    test = true;
                }  
            }
   
            catch (java.lang.Exception ex)
            {
               test = false;
            }
             return test;
        }
        
        private Blob getDefaultImageInBlob(HttpServletRequest request) 
            throws ServletException, IOException {
//            final String path = request.getContextPath() + "/assets/img/alumni/profile/default.png";
//            final String path = request.getScheme() + "://" + request.getServerName() + "/assets/img/alumni/profile/default.png";
//            final String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/assets/img/alumni/profile/default.png";

//            File defaultPicture = null;
            Blob imageBlob = null;

            try {
//                defaultPicture = new File(path);
                byte[] fileContent;
                fileContent = java.util.Base64.getDecoder().decode(ALUMNI_DEFAULT_PROFILE_PICTURE);
                imageBlob = JDBCUtility.getCon().createBlob();
                imageBlob.setBytes(1, fileContent);
            } 
            catch(SQLException ex) {
                Logger.getLogger(ManageUserController.class.getName()).log(Level.INFO, ex.getMessage());
            }

            return imageBlob;
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
        
    private ArrayList<User> getInfoFromDatabase() {
        ArrayList<User> userList = new ArrayList<>();
        
        try {
            Connection con = null;
            PreparedStatement preparedStatement = null;
            con = JDBCUtility.getCon();
            
            String statementQuery1 = "SELECT * FROM users";
            
            Statement statement1 = con.createStatement();
            ResultSet result1 = statement1.executeQuery(statementQuery1);
            
            while(result1.next()) {
                String role = result1.getString("role");
                Creator c = new Creator();
                User user = c.FactoryMethod(role);
                
                user.setUserID(result1.getString("userID"));
                user.setName(result1.getString("name"));
                user.setEmail(result1.getString("email"));
                user.setPassword(result1.getString("password"));
                user.setPhoneNum(result1.getString("phone"));
                user.setRole(result1.getString("role"));
                
                userList.add(user);
            }
            
            return userList;
            
        } catch (SQLException ex) {
            Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("option") != null && request.getParameter("option").equals("mobile-api")) {
            response.setContentType("text/html");
            try (PrintWriter out = response.getWriter()){

                ArrayList<User> user = getInfoFromDatabase();
                Gson gson = new Gson();
                String jsonString = gson.toJson(user);

                out.println(jsonString);

                out.close();
            }
        }
        else {
            processRequest(request, response);  
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