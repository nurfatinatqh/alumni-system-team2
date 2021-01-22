<%-- 
    Document   : AddUserPage
    Created on : Dec 31, 2020, 10:16:06 PM
    Author     : nurfa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.models.user.User" %>
<!DOCTYPE html>
<html>
    <head>
        <title>ADD USER</title>
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
  <header class="w3-container w3-padding-32 w3-center w3-white" id="home">
      <img src="https://brand.utm.my/files/2016/08/LOGO-UTM.png" style="width:30%">
      <br><p>FACULTY OF BUILT ENVIRONMENT, UNIVERSITI TEKNOLOGI MALAYSIA</p>
  </header>
  
  <div class="tablePosition">
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
            else if(phoneNum.length<10)
            { 
            alert("Invalid Phone Number length."); 
            return false; 
            } 
            else if(password.length<8)
            { 
            alert("Password must be at least 8 characters long."); 
            return false; 
            } 
            else if (password!=conpassword)
            { 
            alert("Confirm Password should match with the Password"); 
            return false; 
            } 
             else if (role==null || role=="")
             { 
             alert("Please choose your role"); 
             return false; 
             } 
         } 
        </script> 
      <form name="form" action="ManageUserController" method="post" onsubmit="return validate()">
        <table width="50%" border="5" cellspacing="5" cellpadding="5" align="center">
                    <tr>
                      <th width="30%" scope="row"><div align="left">NAME</div></th>
                      <td width="80%"><label>
                        <input type="text" name="name" id="name" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">EMAIL</div></th>
                      <td><label>
                        <input type="email" name="email" id="email" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">PHONE NUMBER</div></th>
                      <td><label>
                        <input type="text" name="phoneNum" id="phoneNum" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">PASSWORD</div></th>
                      <td><label>
                        <input type="password" name="password" id="password" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                      <th scope="row"><div align="left">CONFIRM PASSWORD</div></th>
                      <td><label>
                        <input type="password" name="password2" id="password2" size = "60%">
                      </label></td>
                    </tr>
                    <tr>
                        <th scope="row"><div align="left">ROLE</div></th>
                        <td align="left">
                        <input type="radio" id="role" name="role" value="ALUMNI">
                        <label for="role">ALUMNI</label>&nbsp;&nbsp;
                        <input type="radio" id="role" name="role" value="STAFF">
                        <label for="role">STAFF</label>&nbsp;&nbsp;
                        <input type="radio" id="role" name="role" value="ADMIN">
                        <label for="role">ADMIN</label><br>
                      </td>
                    </tr>
                    
                  </table>
                  <center>
                  <span style="color:red; font-weight: bold; align-content: center;">
                     <br><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%><br>
                  </span>
                 
                <label>
                    <input type="text" name="option" id="option" value = "submitNewUser" hidden>
                    <input type="submit" name="button" id="button" value="Submit">
                    <input type="reset" name="button2" id="button2" value="Reset">
                </label></center>
    </form></div></div>
  </div>

<!-- END PAGE CONTENT -->
</div>

</body>
</html>

