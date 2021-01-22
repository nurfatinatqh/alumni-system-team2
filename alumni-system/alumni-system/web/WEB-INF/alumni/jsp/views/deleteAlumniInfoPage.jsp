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
        <title>Delete Alumni</title>
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
            #overlay {
                position: fixed;
                display: block;
                width: 100%;
                height: 100%;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: rgba(0,0,0,0.5);
                z-index: 2;
            }

            .ovelay-content{
                position: absolute;
                top: 50%;
                left: 50%;
                font-size: 24px;
                background-color: #ccffff;
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                text-align: center;
                border-radius: 24px;
                transform: translate(-50%,-50%);
                -ms-transform: translate(-50%,-50%);
            }
        </style>
    </head>
    <body>
        <jsp:include page="../../../allModules/sideNavigationBar.jsp" />
        <jsp:include page="../components/bootstrap4.jsp" />
        
        <% String currentUserType = ((User)session.getAttribute("user")).getRole(); %>
        
        <div class="container-custom">
            <div class="jumbotron">
                <h1 class="display-4">Delete Profile Page</h1>
                       
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
            </div>
        </div>
                
        <form action="AlumniController" method="POST">     
            <div id="overlay">
                <div class="ovelay-content">
                    <div class="card-body">
                        <p class="card-text">Confirm Delete?</p>
                        <input type="hidden" name="requestType" value="confirmDeleteAlumniInfo">
                        <input type="submit" name="yes-btn" class="btn btn-danger" value="Yes">
                        <input type="submit" name="no-btn" class="btn btn-success" value="No">
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
