<%-- 
    Document   : alumniMailingAddress
    Created on : Jan 19, 2021, 3:57:08 PM
    Author     : ainal farhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <% 
            String manageAlumniProcess = (String)request.getAttribute("manageAlumniProcess");
            String readOnly = "";
            String styleInput = "";
            if(manageAlumniProcess.equalsIgnoreCase("view")) {
                readOnly = "readonly";
                styleInput = "-plaintext";
            }
            else if(manageAlumniProcess.equalsIgnoreCase("update")) {
                readOnly = "";
                styleInput = "";
            }
            else if(manageAlumniProcess.equalsIgnoreCase("delete")) {
                styleInput = "-plaintext";
                readOnly = "readonly";
            }
        %>
        
        <div class="table-responsive">
            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th colspan="2" style="text-align: center;">Mailing Address</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th><label>Address Line 1</label></th>
                        <td><input type="text" name="updatedAlumniAddress1" class="form-control<%= styleInput %>" id="alumniAddress1" value="<% out.print(request.getAttribute("alumniAddress1")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Address Line 2</label></th>
                        <td><input type="text" name="updatedAlumniAddress2" class="form-control<%= styleInput %>" id="alumniAddress2" value="<% out.print(request.getAttribute("alumniAddress2")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>City</label></th>
                        <td><input type="text" name="updatedAlumniAddressCity" class="form-control<%= styleInput %>" id="alumniAddressCity" value="<% out.print(request.getAttribute("alumniAddressCity")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Post Code</label></th>
                        <td><input type="text" name="updatedAlumniAddressPostCode" maxlength="5" class="form-control<%= styleInput %>" id="alumniAddressPostCode" value="<% out.print(request.getAttribute("alumniAddressPostCode")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>State</label></th>
                        <td><input type="text" name="updatedAlumniAddressState" class="form-control<%= styleInput %>" id="alumniAddressState" value="<% out.print(request.getAttribute("alumniAddressState")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Country</label></th>
                        <td><input type="text" name="updatedAlumniAddressCountry" class="form-control<%= styleInput %>" id="alumniAddressCountry" value="<% out.print(request.getAttribute("alumniAddressCountry")); %>" <%= readOnly %>></td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <script>
            setInputFilter(document.getElementById("alumniAddressPostCode"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            function setInputFilter(textbox, inputFilter) {
                ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
                    textbox.addEventListener(event, function() {
                        if (inputFilter(this.value)) {
                            this.oldValue = this.value;
                            this.oldSelectionStart = this.selectionStart;
                            this.oldSelectionEnd = this.selectionEnd;
                        } else if (this.hasOwnProperty("oldValue")) {
                            this.value = this.oldValue;
                            this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
                        } else {
                            this.value = "";
                        }
                    });
                });
            }
        </script>
    </body>
</html>
