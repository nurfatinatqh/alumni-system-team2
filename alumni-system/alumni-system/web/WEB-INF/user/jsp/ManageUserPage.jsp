<%-- 
    Document   : ManageUserPage
    Created on : Dec 31, 2020, 10:18:29 PM
    Author     : nurfa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.models.user.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>MANAGE USER</title>
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
    <br><br><br><br><br>
  <div class="w3-container w3-padding-30 w3-center w3-white">
      <table cellspacing="5" cellpadding="5" align="center">
                    <tr>
                      <th scope="row"><div align="center"><a href="ManageUserController?option=addUser"><img src="https://cdn0.iconfinder.com/data/icons/user-collection-4/512/add_user-512.png" style="width:70%;"></a></div></th>
                      <th scope="row"><div align="center"><a href="ManageUserController?option=display"><img src="https://icons-for-free.com/iconfiles/png/512/delete+user+user+icon-1320196241186328120.png" style="width:70%;"></a></div></th>
                    <tr>
                        <th scope="row"><div align="center">ADD USER</div></th>
                        <th scope="row"><div align="center">REMOVE USER</div></th>
                    </tr>  
      </table>
  </div>

<!-- END PAGE CONTENT -->
</div>

</body>
</html>

