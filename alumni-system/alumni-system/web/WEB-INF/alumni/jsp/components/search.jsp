<%-- 
    Document   : search
    Created on : Dec 28, 2020, 12:28:33 PM
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
        <div class="search-container">
            <% String selectedSearchReq = ""; %>
            <% if(request.getAttribute("selectedSearchReq") != null) selectedSearchReq = (String)request.getAttribute("selectedSearchReq"); %>
            <div class="row">
                <div class="col">
                    <form class="form-inline" action="AlumniController" method="POST">
                        <input type="hidden" name="requestType" value="searchAlumni">
                        <input class="form-control mr-sm-2" type="search" name="searchInfo" placeholder="Search" aria-label="Search" required>
                        <input type="submit" class="btn btn-outline-success my-2 my-sm-0" name="searchBtn" value="Search" />
                        <div class="form-check form-check-inline" style="margin-left: 10px;">
                            <input class="form-check-input" type="radio" name="searchReq" id="searchByName" value="searchByName" <% if(selectedSearchReq.equalsIgnoreCase("searchByName") || selectedSearchReq.equals("")) out.print("checked"); %>>
                            <label class="form-check-label" for="searchByName">Name</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="searchReq" id="searchByLocationInState" value="searchByLocationInState" <% if(selectedSearchReq.equalsIgnoreCase("searchByLocationInState")) out.print("checked"); %>>
                            <label class="form-check-label" for="searchByLocationInState">Location (State)</label>
                        </div>
                    </form> 
                </div>
                <div class="col" style="text-align: right">
                    <form action="AlumniController" method="POST">
                        <input type="hidden" name="requestType" value="searchAlumni">
                        <input type="submit" class="btn btn-outline-success my-2 my-sm-0" name="resetBtn" value="Refresh" />
                    </form>
                </div>
            </div> 
        </div>
    </body>
</html>
