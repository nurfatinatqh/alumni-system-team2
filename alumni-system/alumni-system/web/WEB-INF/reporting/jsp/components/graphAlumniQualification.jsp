<%-- 
    Document   : graphAlumniQualification
    Created on : Jan 22, 2021, 7:25:27 PM
    Author     : ainal farhan
--%>

<%@page import="com.models.reporting.AlumniReport"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    </head>
    <body>
        <%  if(request.getAttribute("alumniReport") != null) { %>
        <%      
                int totalDiploma = ((AlumniReport)request.getAttribute("alumniReport")).getTotalAlumniGraduateYearDiploma();
                int totalBachelor = ((AlumniReport)request.getAttribute("alumniReport")).getTotalAlumniGraduateYearBachelor();
                int totalMaster = ((AlumniReport)request.getAttribute("alumniReport")).getTotalAlumniGraduateYearMaster();
        %>
        <script type="text/javascript">

            // Load Charts and the corechart package.
            google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawAlumniQualificationChart);
            
            //Number of alumni based on qualification
            function drawAlumniQualificationChart() {

                // Create the data table for Sarah's pizza.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Qualification');
                data.addColumn('number', 'number');
                data.addRows([
                    ['Bachelor', <%= totalDiploma %>],
                    ['Diploma', <%= totalBachelor %>],
                    ['Master', <%= totalMaster %>],
                ]);

                // Set options for Sarah's pie chart.
                var options = {title:'Alumni Qualification',
                               width: 600,
                               height:400,
                                pieHole: 0.4};

                // Instantiate and draw the chart for Sarah's pizza.
                var chart = new google.visualization.PieChart(document.getElementById('chart_alumni_qualification'));
                chart.draw(data, options);
            }
        </script>
        <%  } %>
        
        <div id="chart_alumni_qualification" style="border: 1px solid #ccc"></div>
    </body>
</html>
