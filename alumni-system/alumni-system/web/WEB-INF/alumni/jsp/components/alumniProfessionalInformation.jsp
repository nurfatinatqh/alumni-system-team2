<%-- 
    Document   : alumniProfessionalInformation
    Created on : Jan 19, 2021, 3:56:57 PM
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
                        <th colspan="2" style="text-align: center;">Professional Information</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th><label>Professional Status</label></th>
                        <td><input type="text" name="updatedAlumniProfStatus" class="form-control<%= styleInput %>" id="professionalStatus" value="<% out.print(request.getAttribute("profStatus")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Professional Status Gained (Year)</label></th>
                        <td><input type="text" name="updatedAlumniProfStatusGainedYear" class="form-control<%= styleInput %>" id="professionalStatusYearGained" value="<%= (Integer)request.getAttribute("profStatusYearGained") %>" <%= readOnly %>></td>
                    </tr>
                </tbody>
            </table>
        </div>
                
        <script>
            setInputFilter(document.getElementById("professionalStatusYearGained"), function(value) {
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
