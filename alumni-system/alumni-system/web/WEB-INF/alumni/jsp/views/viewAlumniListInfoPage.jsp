<%-- 
    Document   : viewAlumniListInfoPage
    Created on : Dec 27, 2020, 8:32:04 PM
    Author     : PC
--%>

<%@page import="com.models.user.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alumni List</title>
        
        <style>
            .btn-custom {
                border-radius: 30px; /* Curve of border corners */
                text-transform: uppercase; /* Make letters uppercase */
                color: white; /* Text color */
                outline: none;
                background: #0099cc; /* For browsers that do not support gradients */
                background: -webkit-linear-gradient(#e5f4f9, #b2e0ef, #7fcce5, #0089b7, #0099CC, #b2e0ef); /* For Safari 5.1 to 6.0 */
                background: -o-linear-gradient(#e5f4f9, #b2e0ef, #7fcce5, #0089b7, #0099CC, #b2e0ef); /* For Opera 11.1 to 12.0 */
                background: -moz-linear-gradient(#e5f4f9, #b2e0ef, #7fcce5, #0089b7, #0099CC, #b2e0ef); /* For Firefox 3.6 to 15 */
                background: linear-gradient(#e5f4f9, #b2e0ef, #7fcce5, #0089b7, #0099CC, #b2e0ef); /* Standard syntax */
            }
            
            .container-custom {
                display: block;
                margin-right: auto;
                margin-left: auto;
                width: 80%;
                padding: 50px 0 50px 150px;
            }
            
            .custom-shadow {
                box-shadow: 
                    rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, 
                    rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, 
                    rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;
            }
            
            .container-custom .search-container {
                padding-bottom: 5px;
            }
            
            .container-custom .filter-container {
                padding-bottom: 5px;
            }
            
            .container-custom .pagination-container {
                padding-top: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../../../allModules/sideNavigationBar.jsp" />
        <jsp:include page="../components/bootstrap4.jsp" />
        
        <% String currentUserType = ((User)session.getAttribute("user")).getRole(); %>
        
        <div class="container-custom">
            <div class="search-container">
                <%@include file="../components/search.jsp" %>
            </div>
        <%  if((Integer)request.getAttribute("totalPages") > 0) { %>
            <div class="filter-container">
                <%@include file="../components/filter.jsp" %>
            </div>
        <%  } %>
            <div class="table-responsive custom-shadow">
                <table class="table table-hover table-striped table-light" style="text-align:center;">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" rowspan="2" class="align-middle">No</th>
                            <th scope="col" rowspan="2" class="align-middle">Name</th>
                            <th scope="col" rowspan="2" class="align-middle">Email</th>
                            <th scope="col" rowspan="2" class="align-middle">Location (State)</th>
                            <th scope="col" colspan="3" class="align-middle">Graduation Year</th>
                            <th scope="col" rowspan="2" class="align-middle">Professional Status</th>
                            <th scope="col" rowspan="2" class="align-middle">Current Job</th>
                            <%  if(currentUserType.equalsIgnoreCase("admin")) { %>
                            <th scope="col" rowspan="2" class="align-middle">Manage</th>
                            <%  } %>
                        </tr>
                        <tr>
                            <th>Diploma</th>
                            <th>Bachelor</th>
                            <th>Master</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%  String[] alumniID = (String[])request.getAttribute("alumniIDArray"); %>
                        <%  String[] alumniName = (String[])request.getAttribute("alumniNameArray"); %>
                        <%  String[] alumniEmail = (String[])request.getAttribute("alumniEmailArray"); %>
                        <%  String[] alumniState = (String[])request.getAttribute("alumniStateArray"); %>
                        <%  String[] alumniGraduationYearDiploma = (String[])request.getAttribute("alumniGraduationYearDiplomaArray"); %>
                        <%  String[] alumniGraduationYearBachelor = (String[])request.getAttribute("alumniGraduationYearBachelorArray"); %>
                        <%  String[] alumniGraduationYearMaster = (String[])request.getAttribute("alumniGraduationYearMasterArray"); %>
                        <%  String[] alumniProfStatus = (String[])request.getAttribute("alumniProfStatusArray"); %>
                        <%  String[] alumniCurJob = (String[])request.getAttribute("alumniCurJobArray"); %>

                        <%  for(int i = 0; i < (Integer)request.getAttribute("TOTAL_ALUMNI_PER_PAGE"); i++) { %>
                        <%      if(alumniID[i].equals("") && i == 0) { %><td colspan="9" style="text-align: center;"><% out.print("No Alumni Found!"); break;} %></td>
                        <%      if(alumniID[i].equals("")) break;%>
                        <tr>
                            <th scope="row"><%= i+1+(Integer)request.getAttribute("TOTAL_ALUMNI_PER_PAGE") * (Integer)request.getAttribute("currentPage") - (Integer)request.getAttribute("TOTAL_ALUMNI_PER_PAGE") %></th>
                            <td><%= alumniName[i] %></td>
                            <td><%= alumniEmail[i] %></td>
                            <td><%= alumniState[i] %></td>
                            <td><%= alumniGraduationYearDiploma[i].equals("0")? "-" : alumniGraduationYearDiploma[i] %></td>
                            <td><%= alumniGraduationYearBachelor[i].equals("0")? "-" : alumniGraduationYearBachelor[i] %></td>
                            <td><%= alumniGraduationYearMaster[i].equals("0")? "-" : alumniGraduationYearMaster[i] %></td>
                            <td><%= alumniProfStatus[i] %></td>
                            <td><%= alumniCurJob[i] %></td>
                            <%  if(currentUserType.equalsIgnoreCase("admin")) { %>
                            <td>
                                <form action="AlumniController" method="POST">
                                    <input type="hidden" name="selectedAlumniID" value="<% out.print(alumniID[i]); %>">
                                    <input type="hidden" name="requestType" value="manageSelectedAlumniInfo">
                                    <input type="submit" class="btn-custom" value="go">
                                </form>
                            </td>
                            <%  } %>
                        </tr>
                        <%  } %>
                    </tbody>
                </table>
            </div>
        <%  if((Integer)request.getAttribute("totalPages") > 0) { %>
            <div class="pagination-container">
                <%@include file="../components/pagination.jsp" %>
            </div>
        <%  } %>
        </div>
    </body>
</html>
