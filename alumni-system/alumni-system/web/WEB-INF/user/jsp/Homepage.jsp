<%-- 
    Document   : Homepage
    Created on : Dec 31, 2020, 10:17:14 PM
    Author     : nurfa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.models.user.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>HOMEPAGE</title>
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
      <br><br><br><br><br><img src="https://brand.utm.my/files/2016/08/LOGO-UTM.png" style="width:30%">
    <br><p>FACULTY OF BUILT ENVIRONMENT, UNIVERSITI TEKNOLOGI MALAYSIA</p><br>
  </header>
  
  <jsp:useBean id="user" type="com.models.user.User" scope="session" />
  
  <div class="w3-container w3-padding-25 w3-center w3-white">
      <table width="60%" border="5" cellspacing="5" cellpadding="5" align="center">
                    <tr>
                      <th scope="row"><div align="left">USER ID</div></th>
                      <td><label><div align="left"><jsp:getProperty name="user" property="userID"/></div></label></td>
                    </tr>
                    <tr>
                      <th width="30%" scope="row"><div align="left">NAME</div></th>
                      <td width="80%"><label><div align="left"><jsp:getProperty name="user" property="name"/></div></label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">EMAIL</div></th>
                      <td><label><div align="left"><jsp:getProperty name="user" property="email"/></div></label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">PHONE NUMBER</div></th>
                      <td><label><div align="left"><jsp:getProperty name="user" property="phoneNum"/></div></label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">ROLE</div></th>
                      <td><label><div align="left"><jsp:getProperty name="user" property="role"/></div></label></td>
                    </tr>
       </table>  
  </div>

<!-- END PAGE CONTENT -->
</div>

</body>
</html>

