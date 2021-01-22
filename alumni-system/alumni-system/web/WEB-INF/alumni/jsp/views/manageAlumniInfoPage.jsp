<%-- 
    Document   : manageInfoPage
    Created on : Dec 27, 2020, 8:30:37 PM
    Author     : PC
--%>

<%@page import="com.models.user.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Alumni</title>
        <style>
            .custom-shadow {
                box-shadow: 
                    rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, 
                    rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, 
                    rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;
            }
            .center {
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .table-custom {
                width: 100%;
            }
            .container-custom {
                display: block;
                margin-right: auto;
                margin-left: auto;
                width: 80%;
                padding: 50px 0 50px 150px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../../../allModules/sideNavigationBar.jsp" />
        <jsp:include page="../components/bootstrap4.jsp" />
        
        <% 
            String currentUserType = ((User)session.getAttribute("user")).getRole(); 
        %>
        
        <div class="container-custom">
            <div class="jumbotron">
                <h1 class="display-4">View Profile Page</h1>
                
                <div class="form-group row">
                    <div class="col center">
                        <img src="AlumniController?requestType=requestImage" alt="profile picture" class="mx-auto d-block" width="200" height="200" style="margin-top:10px;margin-bottom:10px;border-radius: 50%;">
                    </div>
                    <div class="col">
                        <div class="form-group custom-shadow">
                            <jsp:include page="../components/alumniPersonalInformation.jsp" />
                        </div>
                        <div class="form-group custom-shadow">
                            <jsp:include page="../components/alumniProfessionalInformation.jsp" />
                        </div>
                    </div>
                </div>

                <div class="form-group custom-shadow">
                    <jsp:include page="../components/alumniMailingAddress.jsp" />
                </div>

                <div class="form-group custom-shadow">
                    <jsp:include page="../components/alumniEducationalInformation.jsp" />
                </div>

                <div class="form-group custom-shadow">
                    <jsp:include page="../components/alumniEmploymentInformation.jsp" />
                </div>

                <hr class="my-4">
                
                <form action="AlumniController" method="POST">    
                    <div class="form-group center">
                        <input type="hidden" name="requestType" value="deleteOrUpdateAlumniInfo">
                        <input type="submit" name="update-btn" class="btn btn-success" value="Update">
                        <%  if(currentUserType.equalsIgnoreCase("staff") || currentUserType.equalsIgnoreCase("admin")) { %>
                        <input type="submit" name="delete-btn" class="btn btn-danger" value="Delete">
                        <%  } %>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
