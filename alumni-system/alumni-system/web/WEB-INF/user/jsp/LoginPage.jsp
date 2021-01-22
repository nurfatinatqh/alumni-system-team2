<%-- 
    Document   : LoginPage
    Created on : Dec 31, 2020, 10:18:05 PM
    Author     : nurfa
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOGIN ACCOUNT</title>

<style type="text/css">
            body{ 
                font: 14px sans-serif; 
                background-image: url('');
                background-repeat: no-repeat;
                background-attachment: fixed;  
                background-size: cover;
                }
            .wrapper{ width: 350px; padding: 20px; }
            .container {
                position: relative;
            }
            .center {
                position: absolute;
                top: 50%;
                width: 100%;
                text-align: center;
                font-size: 14px;
            }
</style>
</head>
<body>
<div class="container"> 
<div class="center">
    <br><br><br><br><img src="assets\img\user\LOGO-UTM.png" style="width:400px;height:150px;">
    <h4><br>FACULTY OF BUILT ENVIRONMENT, UNIVERSITI TEKNOLOGI MALAYSIA</h4>
    <form name="form" action="ManageUserController" method="post">
        <table width="30%" border="5" cellspacing="5" cellpadding="5" align="center">
                    <tr>
                      <th scope="row"><div align="left">EMAIL</div></th>
                      <td><label>
                        <input type="text" name="email" id="email" required size = "30%">
                      </label></td>
                    <tr>
                      <th scope="row"><div align="left">PASSWORD</div></th>
                      <td><label>
                        <input type="password" name="password" id="password" required size = "30%">
                      </label></td>
                    </tr>  
         </table>
                  <center>
                  <span style="color:red; font-weight: bold; align-content: center;">
                     <br><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%><br>
                  </span>
                  
                  <p>Don't have an account? <a href="ManageUserController?option=viewRegisterPage">Sign up now</a>.</p>
                <label>
                    <input type="text" name="option" id="option" value = "login" hidden>
                    <input type="submit" name="button" id="button" value="Login">
                </label></center>
    </form></div></div>
</body>
</html>