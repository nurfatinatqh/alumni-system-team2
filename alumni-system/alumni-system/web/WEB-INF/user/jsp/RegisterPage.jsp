<%-- 
    Document   : RegisterPage
    Created on : Dec 31, 2020, 10:19:08 PM
    Author     : nurfa
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>REGISTER ACCOUNT</title>

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
            .ridge {border-style: ridge;}
</style>
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
</head>
<body>
<div class="container"> 
<div class="center">
    <br><br><br><br><img src="assets\img\user\LOGO-UTM.png" style="width:400px;height:150px;">
    <h4><br>FACULTY OF BUILT ENVIRONMENT, UNIVERSITI TEKNOLOGI MALAYSIA</h4>
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
                        <label for="role">ALUMNI</label>
                        <input type="radio" id="role" name="role" value="STAFF">
                        <label for="role">STAFF</label><br>
                      </td>
                    </tr>
                    
                  </table>
                  <center>
                  <span style="color:red; font-weight: bold; align-content: center;">
                     <br><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%><br>
                  </span>
                  
                  <p>Already have an account? <a href="ManageUserController?option=viewLoginPage">Login here</a>.</p>
                <label>
                    <input type="text" name="option" id="option" value = "register" hidden>
                    <input type="submit" name="button" id="button" value="Submit">
                    <input type="reset" name="button2" id="button2" value="Reset">
                </label></center>
    </form></div></div>
</body>
</html>