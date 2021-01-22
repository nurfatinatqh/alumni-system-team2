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
            
            google.charts.setOnLoadCallback(drawNumberOfAlumniGraduatesWithBachelorPerYearChart);
            
            function drawNumberOfAlumniGraduatesWithBachelorPerYearChart(){
                var data = google.visualization.arrayToDataTable([
                   ["Year","Number of Alumni Graduated with Bachelor"],
                   <%
                        ArrayList<AlumniReport> alumniReports = (ArrayList<AlumniReport>)request.getAttribute("alumniReports");
                        for(int i = 0; i < alumniReports.size() - 1; i++) {
                   %>['<%= alumniReports.get(i).getAlumniGraduateYearBachelor() + "', " + alumniReports.get(i).getTotalAlumniGraduateYearBachelor() %>], <%
                        }
                        %>['<%= alumniReports.get(alumniReports.size() - 1).getAlumniGraduateYearBachelor() + "', " + alumniReports.get(alumniReports.size() - 1).getTotalAlumniGraduateYearBachelor() %>]<%
                   %>
                ]);

                var view = new google.visualization.DataView(data);
                view.setColumns([0,1]);

                var options = {
                    title: "Number of Alumni Graduate with Bachelor per year",
                    width: 600,
                    height: 400
                };
                var chart = new google.visualization.ColumnChart(document.getElementById("chart_bachelor"));
                chart.draw(view, options);
            }
        </script>
        <%  } %>
        <div id="chart_bachelor" style="border: 1px solid #ccc"></div>
    </body>
</html>
