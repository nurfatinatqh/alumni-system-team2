<%-- 
    Document   : filter
    Created on : Dec 28, 2020, 12:39:16 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    
    </head>
    <body>
        <div class="filter-container">
            <% String filterSelected = ""; %>
            <% if(request.getAttribute("filterReq") != null) filterSelected = (String)request.getAttribute("filterReq"); %>
            <form class="form-inline" action="AlumniController" method="POST">
                <input type="hidden" name="requestType" value="filterAlumniInfo">
                <input type="submit" value="Filter" class="btn btn-primary">
                
                <div class="form-check form-check-inline" style="margin-left: 20px;">
                    <input class="form-check-input" type="radio" name="filterReq" id="orderByNameAtoZ" value="orderByNameAtoZ" <% if(filterSelected.equalsIgnoreCase("orderByNameAtoZ") || filterSelected.equals("")) out.print("checked"); %>>
                    <label class="form-check-label" for="orderByNameAtoZ">Order By Name (Ascending)</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="filterReq" id="orderByNameZtoA" value="orderByNameZtoA" <% if(filterSelected.equalsIgnoreCase("orderByNameZtoA")) out.print("checked"); %>>
                    <label class="form-check-label" for="orderByNameZtoA">Order By Name (Descending)</label>
                </div>
            </form>
        </div>
    </body>
</html>
