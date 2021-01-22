<%-- 
    Document   : graphNumberAlumniGraduatePerYear
    Created on : Jan 22, 2021, 7:25:10 PM
    Author     : ainal farhan
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.models.reporting.AlumniReport"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    </head>
    <body>
        <% if(request.getAttribute("alumniReports") != null) { %>
        <script type="text/javascript">

            // Load Charts and the corechart package.
            google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawNumberOfAlumniGraduatesWithDiplomaPerYearChart);
            
            function drawNumberOfAlumniGraduatesWithDiplomaPerYearChart(){
                var data = google.visualization.arrayToDataTable([
                   ["Year","Number of Alumni Graduated with Diploma"],
                   <%
                        ArrayList<AlumniReport> alumniReports = (ArrayList<AlumniReport>)request.getAttribute("alumniReports");
                        for(int i = 0; i < alumniReports.size() - 1; i++) {
                   %>['<%= alumniReports.get(i).getAlumniGraduateYearDiploma() + "', " + alumniReports.get(i).getTotalAlumniGraduateYearDiploma()%>], <%
                        }
                        %>['<%= alumniReports.get(alumniReports.size() - 1).getAlumniGraduateYearDiploma() + "', " + alumniReports.get(alumniReports.size() - 1).getTotalAlumniGraduateYearDiploma() %>]<%
                   %>
                ]);

                var view = new google.visualization.DataView(data);
                view.setColumns([0,1]);

                var options = {
                    title: "Number of Alumni Graduate with Diploma per year",
                    width: 600,
                    height: 400
                };
                var chart = new google.visualization.ColumnChart(document.getElementById("chart_diploma"));
                chart.draw(view, options);
            }
        </script>
        <%  } %>
        <div id="chart_diploma" style="border: 1px solid #ccc"></div>
    </body>
</html>
