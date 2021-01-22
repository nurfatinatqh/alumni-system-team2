<%-- 
    Document   : EditProfilePage
    Created on : Dec 31, 2020, 10:16:46 PM
    Author     : nurfa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.models.user.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>EDIT PROFILE</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <style>
            body, h1,h2,h3,h4,h5,h6 {font-family: "Montserrat", sans-serif}
            .w3-row-padding img {margin-bottom: 12px}
            /* Set the width of the sidebar to 120px */
            .w3-sidebar {width: 120px;background: #333399;}
            /* Add a left margin to the "page content" that matches the width of the sidebar (120px) */
            #main {margin-left: 120px}
            /* Remove margins from "page content" on small screens */
            @media only screen and (max-width: 600px) {#main {margin-left: 0}}
            #outer
            {
                width:100%;
                text-align: center;
            }
            .inner
            {
                display: inline-block;
            }
        </style>
    </head>
    
<body class="w3-white">
    <jsp:include page="../../allModules/sideNavigationBar.jsp" />

<!-- Page Content -->
<div class="w3-padding-large" id="main">
  <!-- Header/Home -->
  <header class="w3-container w3-padding-29 w3-center w3-white" id="home">
    <br><img src="https://brand.utm.my/files/2016/08/LOGO-UTM.png" style="width:30%">
    <br><p>FACULTY OF BUILT ENVIRONMENT, UNIVERSITI TEKNOLOGI MALAYSIA</p><br>
  </header>
  
  <div class="w3-container w3-padding-25 w3-center w3-white">
      <script>
       function validate()
        { 
             var name = document.form.name.value;
             var email = document.form.email.value;
             var phoneNum = document.form.phoneNum.value; 
             var password = document.form.password.value;
             var conpassword= document.form.password2.value;
             var role= document.form.role.value;

             if (name==null || name=="")
             { 
             alert("Name can't be blank"); 
             return false; 
             }
             else if (email==null || email=="")
             { 
             alert("Email can't be blank"); 
             return false; 
             }
             else if (phoneNum==null || phoneNum=="")
             { 
             alert("Phone Number can't be blank"); 
             return false; 
             }
             else if(password.length<8)
             { 
             alert("Password must be at least 6 characters long."); 
             return false; 
             } 
             else if (password!=conpassword)
             { 
             alert("Confirm Password should match with the Password"); 
             return false; 
             } 
         } 
        </script> 
        <jsp:useBean id="user" type="com.models.user.User" scope="session" />
      <form name="form" action="ManageUserController" method="post" onsubmit="return validate()">
      <table width="60%" border="5" cellspacing="5" cellpadding="5" align="center">
                    <tr>
                      <th scope="row"><div align="left">USER ID</div></th>
                      <td><label>
                        <input type="text" disabled value ="<jsp:getProperty name="user" property="userID"/>" size = "60%">
                        <input type="text" name="userID" id="userID" value = "<jsp:getProperty name="user" property="userID"/>" hidden>
                      </label></td>
                    </tr>
                    <tr>
                      <th width="30%" scope="row"><div align="left">NAME</div></th>
                      <td width="80%"><label>
                        <input type="text" name="name" id="name" value ="<jsp:getProperty name="user" property="name"/>" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">EMAIL</div></th>
                      <td><label>
                        <input type="email" name="email" id="email" value ="<jsp:getProperty name="user" property="email"/>" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">PHONE NUMBER</div></th>
                      <td><label>
                        <input type="text" name="phoneNum" id="phoneNum" value ="<jsp:getProperty name="user" property="phoneNum"/>" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">ROLE</div></th>
                      <td><label>
                        <input type="text" disabled value ="<jsp:getProperty name="user" property="role"/>" size = "60%">
                        <input type="text" name="role" id="role" value = "<jsp:getProperty name="user" property="role"/>" hidden>
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">PASSWORD</div></th>
                      <td><label>
                        <input type="password" name="password" id="password" value ="<jsp:getProperty name="user" property="password"/>" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">CONFIRM PASSWORD</div></th>
                      <td><label>
                        <input type="password" name="password2" id="password2" value ="<jsp:getProperty name="user" property="password"/>" size = "60%">
                      </label></td>
                    </tr>
       </table>  
            <span style="color:red; font-weight: bold; align-content: center;">
                     <br><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%><br>
            </span><br>
            <input type="text" name="option" id="option" value = "submitEditData" hidden>
            <input type="submit" name="button" id="button" value="Submit">
      </form>
  </div>

<!-- END PAGE CONTENT -->
</div>

</body>
</html>

