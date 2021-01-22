<%-- 
    Document   : graphAlumniStatus
    Created on : Jan 22, 2021, 7:25:41 PM
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
                int totalQualifiedArchitect = ((AlumniReport)request.getAttribute("alumniReport")).getTotalAlumniHaveProfessionalStatus();
                int totalNotQualifiedArchitect = ((AlumniReport)request.getAttribute("alumniReport")).getTotalAlumniDoNotHaveProfessionalStatus();
        %>
        <script type="text/javascript">

            // Load Charts and the corechart package.
            google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawAlumniProfessionalStatusChart);
            
            function drawAlumniProfessionalStatusChart() {
                // Create the data table for Sarah's pizza.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Alumni Professional Status');
                data.addColumn('number', 'number');
                data.addRows([
                    ['Qualified Architect', <%= totalQualifiedArchitect %>],
                    ['Non Qualified Architect', <%= totalNotQualifiedArchitect %>]
                ]);

                // Optional; add a title and set the width and height of the chart
                var options = {'title':'Alumni Status', 'width':600, 'height':400};

                // Display the chart inside the <div> element with id="piechart"
                var chart = new google.visualization.PieChart(document.getElementById('chart_alumni_status'));
                chart.draw(data, options);
            }
        </script>
        <%  } %>
        
        <div id="chart_alumni_status" style="border: 1px solid #ccc"></div>
    </body>
</html>
