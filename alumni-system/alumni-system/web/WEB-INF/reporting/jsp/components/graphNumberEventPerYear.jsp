<%-- 
    Document   : graphNumberEventPerYear
    Created on : Jan 22, 2021, 7:26:06 PM
    Author     : ainal farhan
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.models.reporting.EventReport"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    </head>
    <body>
        <% if(request.getAttribute("eventReports") != null) { %>
        <script type="text/javascript">

            // Load Charts and the corechart package.
            google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawNumberOfEventPerYearChart);
            
            //Number of event by year
            function drawNumberOfEventPerYearChart() {

                // Create the data table for Anthony's pizza.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Year');
                data.addColumn('number', 'Number of event');
                data.addRows([
                    <%
                        ArrayList<EventReport> eventReports = (ArrayList<EventReport>)request.getAttribute("eventReports");
                        for(int i = 0; i < eventReports.size() - 1; i++) {
                    %>['<%= eventReports.get(i).getEventYear() + "', " + eventReports.get(i).getNumberOfEventPerYear()%>], <%
                        }
                        %>['<%= eventReports.get(eventReports.size() - 1).getEventYear() + "', " + eventReports.get(eventReports.size() - 1).getNumberOfEventPerYear() %>]<%
                    %>
                ]);

                // Set options for Anthony's pie chart.
                var options = {title:'Number of event per year',
                               width: 600,
                               height:400};

                // Instantiate and draw the chart for Anthony's pizza.
                var chart = new google.visualization.BarChart(document.getElementById('chart_number_event'));
                chart.draw(data, options);
            }
        </script>
        <%  } %>
        
        <div id="chart_number_event" style="border: 1px solid #ccc"></div>
    </body>
</html>
