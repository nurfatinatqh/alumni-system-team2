<%-- 
    Document   : alumniEmploymentInformation
    Created on : Jan 19, 2021, 3:57:40 PM
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
                        <th colspan="4" style="text-align: center;">Employment Information</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th></th>
                        <th><label>Current</label></th>
                        <th><label>Previous</label></th>
                    </tr>
                    <tr>
                        <th><label>Job</label></th>
                        <td><input type="text" name="updatedAlumniCurJob" class="form-control<%= styleInput %>" value="<% out.print(request.getAttribute("curJob")); %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniPrevJob" class="form-control<%= styleInput %>" value="<% out.print(request.getAttribute("prevJob")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>    
                        <th><label>Employer</label></th>
                        <td><input type="text" name="updatedAlumniCurEmployer" class="form-control<%= styleInput %>" value="<% out.print(request.getAttribute("curEmployer")); %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniPrevEmployer" class="form-control<%= styleInput %>" value="<% out.print(request.getAttribute("prevEmployer")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Salary</label></th>
                        <td><input type="number" name="updatedAlumniCurSalary" class="form-control<%= styleInput %>" value="<% out.print(String.format("%.2f", (Double)request.getAttribute("curSalary"))); %>" <%= readOnly %>></td>
                        <td><input type="number" name="updatedAlumniPrevSalary" class="form-control<%= styleInput %>" value="<% out.print(String.format("%.2f", (Double)request.getAttribute("prevSalary"))); %>" <%= readOnly %>></td>
                    </tr>
                    <tr class="table-primary">
                        <th colspan="3" style="text-align: center;"><label>Current Employer Address</label></th>
                    </tr>
                    <tr>
                        <th><label>Address Line 1</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddress1" class="form-control<%= styleInput %>" id="employerAddress1" value="<% out.print(request.getAttribute("employerAddress1")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Address Line 2</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddress2" class="form-control<%= styleInput %>" id="employerAddress2" value="<% out.print(request.getAttribute("employerAddress2")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>City</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddressCity" class="form-control<%= styleInput %>" id="employerAddressCity" value="<% out.print(request.getAttribute("employerAddressCity")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Post Code</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddressPostCode" maxlength="5" class="form-control<%= styleInput %>" id="employerAddressPostCode" value="<% out.print(request.getAttribute("employerAddressPostCode")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>State</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddressState" class="form-control<%= styleInput %>" id="employerAddressState" value="<% out.print(request.getAttribute("employerAddressState")); %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Country</label></th>
                        <td colspan="2"><input type="text" name="updatedEmployerAddressCountry" class="form-control<%= styleInput %>" id="employerAddressCountry" value="<% out.print(request.getAttribute("employerAddressCountry")); %>" <%= readOnly %>></td>
                    </tr>
                </tbody>
            </table>
        </div>
                
        <script>
            setInputFilter(document.getElementById("employerAddressPostCode"), function(value) {
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
