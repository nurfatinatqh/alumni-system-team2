<%-- 
    Document   : view4
    Created on : Dec 27, 2020, 1:35:35 AM
    Author     : Lenovo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.models.user.User"%>
<%@page import="java.util.ArrayList"%>

<%
    ArrayList users = (ArrayList)session.getAttribute("users");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DELETE USER</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
table {
  max-width: 100%;
}

tr:nth-child(odd) {
  background-color: #eee;
}

th {
  background-color: #555;
  color: #fff;
}

th,
td {
  text-align: left;
  padding: 0.5em 1em;
}

td::before {
  display: none;
}

@media screen and (max-width: 680px) {
 table {
   border: 0;
   display: block;
   box-shadow: none;
 }

 thead {
   position: absolute;
   opacity: 0;
 }
  
 tbody {
   display: block;
   width: 100%;
   min-width: 19em;
   max-width: 25em;
 }

 tr {
   border-top: 2px solid #3c3c3b;
   border-bottom: 1px solid #3c3c3b;
   display: grid;
   grid-template-columns: max-content auto;
   margin-bottom: 1em;
 }

 td {
   display: contents;
 }

 td::before {
   display: inline-block;
   font-weight: bold;
   padding: 0.5em;
   border-bottom: 1px solid;
 }
  
  td span {
    padding: 0.5em;
    border-bottom: 1px solid;
  }

 td:last-child {
   border-bottom: 0;
 }
}
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
            table
            {
                width : 90%; 
                height : 10%;  
                
                margin-left: 60px;
                	
                padding:2%; 
                float: right;
                right:10px;
               
                text-align:left;
                position:relative;
               
            }
        </style>
    </head>
<body class="w3-white">
<script>
    var a = "<%= request.getParameter("status") %>";
    if (a === "true") {
        var answer = window.confirm("Delete Confirmation");
        if (answer) {
            location.replace("ManageUserController?option=confirmDelete&id=<%= request.getParameter("name") %>");
        }
        else {
            //some code
        }
    }
</script>
                     
    <jsp:include page="../../allModules/sideNavigationBar.jsp" />

  <header class="w3-container w3-padding-32 w3-center w3-white">
  </header>
<div style="padding-left:150px;">
        <table>
            <thead>
               <tr >
                   <th>USER ID</th>
                    <th>NAME</th>
                    <th>EMAIL</th>
                    <th>PHONE NUMBER</th>
                    <th>ROLE</th>
                    <th>DELETE</th>
                </tr>
            </thead>
             <tbody>
                <% 
                int index=0;
                for(int i=0;i<users.size();i++){
                 
                    
                    User mp =(User)users.get(i);
                    index++;
                %>
                    
		<tr >
                     <td><%= mp.getUserID() %></td>
                     <td><%= mp.getName() %></td>
                     <td><%= mp.getEmail()%></td>
                     <td><%= mp.getPhoneNum()%></td> 
                     <td><%= mp.getRole()%></td> 
                     <td><center><a href="ManageUserController?option=delete&id=<%= mp.getUserID() %>">DELETE</a></center></td>
                </tr>
                <% 
                }
                %>
            </tbody>
        </table>
</div>
    </body>
</html>
