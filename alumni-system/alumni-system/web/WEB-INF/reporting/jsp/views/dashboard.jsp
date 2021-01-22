<%-- 
    Document   : dashboard
    Created on : Dec 28, 2020, 2:37:40 PM
    Author     : hafizul
--%>

<%@page import="com.models.user.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
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
            .container-custom {
                display: block;
                margin-right: auto;
                margin-left: auto;
                width: 80%;
                padding: 50px 0 50px 150px;
            }
        </style>
    </script>
    </head>
        <body>
            <%  if(session.getAttribute("user") != null) { %>
            
            <jsp:include page="../../../allModules/sideNavigationBar.jsp" />
            
            <%  String userRole = ((User)session.getAttribute("user")).getRole(); %>
            <div class="container-custom">
                <div class="table-responsive">
                    <table class="columns center">
                        <tr>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphNumberAlumniGraduatePerYearDiploma.jsp" /></div></td>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphNumberAlumniGraduatePerYearBachelor.jsp" /></div></td>
                        </tr>
                    </table>
                    <table class="columns center">
                        <tr>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphNumberAlumniGraduatePerYearMaster.jsp" /></div></td>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphAlumniQualification.jsp" /></div></td>
                        </tr>
                    </table>
                    <table class="columns center">
                        <tr>
                            <% if(!userRole.equalsIgnoreCase("alumni")) { %>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphAlumniNationality.jsp" /></div></td>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphAlumniSponsorship.jsp" /></div></td>
                            <%  } %>
                        </tr>
                    </table>
                    <table class="columns center">
                        <tr>
                            <% if(!userRole.equalsIgnoreCase("alumni")) { %>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphAlumniStatus.jsp" /></div></td>
                            <%  } %>
                            <td><div class="custom-shadow"><jsp:include page="../components/graphNumberEventPerYear.jsp" /></div></td>
                        </tr>
                    </table>
                </div>
            </div>
                
            <%  } %>
      </body>
</html>


</html>