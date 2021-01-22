<%-- 
    Document   : alumniEducationalInformation
    Created on : Jan 19, 2021, 3:57:29 PM
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
                <thead class="thead-dark" style="text-align: center;">
                    <tr>
                        <th colspan="5">Educational Information</th>
                    </tr>
                    <tr>
                        <th></th>
                        <th>Diploma</th>
                        <th>Bachelor</th>
                        <th>Master</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th><label>Specialization</label></th>
                        <td><input type="text" name="updatedAlumniFieldOfSpecializationDiploma" class="form-control<%= styleInput %>" value="<%= request.getAttribute("fieldOfSpecializationDiploma") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniFieldOfSpecializationBachelor" class="form-control<%= styleInput %>" value="<%= request.getAttribute("fieldOfSpecializationBachelor") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniFieldOfSpecializationMaster" class="form-control<%= styleInput %>" value="<%= request.getAttribute("fieldOfSpecializationMaster") %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Batch</label></th>
                        <td><input type="text" name="updatedAlumniBatchDiploma" id="batchDip" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("batchDiploma") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniBatchBachelor" id="batchBac" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("batchBachelor") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniBatchMaster" id="batchMast" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("batchMaster") %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Start in (Year)</label></th>
                        <td><input type="text" name="updatedAlumniStartStudyDiploma" id="startDip" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("startStudyYearDiploma") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniStartStudyBachelor" id="startBac" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("startStudyYearBachelor") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniStartStudyMaster" id="startMast" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("startStudyYearMaster") %>" <%= readOnly %>></td>
                    </tr>
                    <tr>
                        <th><label>Graduate in (Year)</label></th>
                        <td><input type="text" name="updatedAlumniGraduateYearDiploma" id="graduateDip" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("graduateYearDiploma") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniGraduateYearBachelor" id="graduateBac" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("graduateYearBachelor") %>" <%= readOnly %>></td>
                        <td><input type="text" name="updatedAlumniGraduateYearMaster" id="graduateMast" class="form-control<%= styleInput %>" value="<%= (Integer)request.getAttribute("graduateYearMaster") %>" <%= readOnly %>></td>
                    </tr>
                </tbody>
            </table>
        </div>
                
        <script>
            setInputFilter(document.getElementById("batchDip"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("batchBac"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("batchMast"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            
            setInputFilter(document.getElementById("startDip"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("startBac"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("startMast"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            
            setInputFilter(document.getElementById("graduateDip"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("graduateBac"), function(value) {
                return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
            });
            setInputFilter(document.getElementById("graduateMast"), function(value) {
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
