<%-- 
    Document   : personal information
    Created on : Jan 19, 2021, 3:56:23 PM
    Author     : ainal farhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th colspan="2" style="text-align: center;">Personal Information</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th><label>Alumni ID</label></th>
                        <td><input type="text" class="form-control-plaintext" value="<% out.print(request.getAttribute("id")); %>" readonly></td>
                    </tr>
                    <tr>
                        <th><label>Name</label></th>
                        <td><input type="text" class="form-control-plaintext" id="name" value="<% out.print(request.getAttribute("name")); %>" readonly></td>
                    </tr>
                    <tr>
                        <th><label>Phone Number</label></th>
                        <td><input type="text" class="form-control-plaintext" id="phone-number" value="<% out.print(request.getAttribute("phoneNumber")); %>" readonly></td>
                    </tr>
                    <tr>
                        <th><label>Email</label></th>
                        <td><input type="email" class="form-control-plaintext" value="<% out.print(request.getAttribute("email")); %>" readonly></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
