<%-- 
    Document   : graphAlumniSponsorship
    Created on : Jan 22, 2021, 7:35:04 PM
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
            
            google.charts.setOnLoadCallback(drawSponsorshipPerYearChart);
            
            function drawSponsorshipPerYearChart(){
                var data = google.visualization.arrayToDataTable([
                    ["Year","Total Sponsorship"],
                    <%
                        ArrayList<EventReport> eventReports = (ArrayList<EventReport>)request.getAttribute("eventReports");
                        for(int i = 0; i < eventReports.size() - 1; i++) {
                    %>['<%= eventReports.get(i).getEventSponsorYear()+ "', " + eventReports.get(i).getTotalSponsorAmountPerYear()%>], <%
                        }
                        %>['<%= eventReports.get(eventReports.size() - 1).getEventSponsorYear() + "', " + eventReports.get(eventReports.size() - 1).getTotalSponsorAmountPerYear() %>]<%
                    %>
                ]);

                var view = new google.visualization.DataView(data);
                view.setColumns([0,1]);

                var options = {
                    title: "Sponsorship per year",
                    width: 600,
                    height: 400
                };
                var chart = new google.visualization.ColumnChart(document.getElementById("chart_event_sponsor"));
                chart.draw(view, options);
            }
        </script>
        <%  } %>
        
        <div id="chart_event_sponsor" style="border: 1px solid #ccc"></div>
    </body>
</html>

