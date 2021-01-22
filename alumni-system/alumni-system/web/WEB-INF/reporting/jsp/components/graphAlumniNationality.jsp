<%-- 
    Document   : graphAlumniNationality
    Created on : Jan 22, 2021, 7:35:25 PM
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
                int totalMalaysian = ((AlumniReport)request.getAttribute("alumniReport")).getTotalMalaysianAlumni();
                int totalNonMalaysian = ((AlumniReport)request.getAttribute("alumniReport")).getTotalNonMalaysianAlumni();
        %>
        <script type="text/javascript">

            // Load Charts and the corechart package.
            google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawAlumniNationalityChart);
            
            function drawAlumniNationalityChart() {
                // Create the data table for Sarah's pizza.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Nationality');
                data.addColumn('number', 'number');
                data.addRows([
                    ['Local', <%= totalMalaysian %>],
                    ['International', <%= totalNonMalaysian %>]
                ]);

                // Optional; add a title and set the width and height of the chart
                var options = {'title':'Alumni Nationality', 'width':600, 'height':400};

                // Display the chart inside the <div> element with id="piechart"
                var chart = new google.visualization.PieChart(document.getElementById('chart_nationality'));
                chart.draw(data, options);
            }
        </script>
        <%  } %>
        
        <div id="chart_nationality" style="border: 1px solid #ccc"></div>
    </body>
</html>
