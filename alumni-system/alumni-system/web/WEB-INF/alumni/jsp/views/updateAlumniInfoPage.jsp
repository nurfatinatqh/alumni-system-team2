<%-- 
    Document   : updateAlumniInfoPage
    Created on : Dec 27, 2020, 8:30:37 PM
    Author     : PC
--%>

<%@page import="com.controllers.alumni.singleton.AlumniPageList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Alumni</title>
        <style>
            .custom-shadow {
                box-shadow: 
                    rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, 
                    rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, 
                    rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;
            }
            .center {
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .table-custom {
                width: 100%;
            }
            .container-custom {
                display: block;
                margin-right: auto;
                margin-left: auto;
                width: 80%;
                padding: 50px 0 50px 150px;
            }
            
            #overlay {
                position: fixed;
                display: nonne;
                width: 100%;
                height: 100%;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: rgba(0,0,0,0.5);
                z-index: 2;
            }

            .ovelay-content{
                position: absolute;
                top: 50%;
                left: 50%;
                font-size: 24px;
                background-color: #ccffff;
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                text-align: center;
                border-radius: 24px;
                transform: translate(-50%,-50%);
                -ms-transform: translate(-50%,-50%);
            }
        </style>
    </head>
    <body>
        <jsp:include page="../../../allModules/sideNavigationBar.jsp" />
        <jsp:include page="../components/bootstrap4.jsp" />
        
        <div class="container-custom">
            <div class="jumbotron">
                <h1 class="display-4">Edit Profile Page</h1>
                <form action="AlumniController" method="POST" onSubmit="return checkEitherImageIsSelected()">
                    <img id="profilePicture" src="AlumniController?requestType=requestImage" alt="profile picture" class="mx-auto d-block custom-shadow" width="200" height="200" style="margin-top:10px;margin-bottom:10px;border-radius: 50%;">
                    <span id="fileSelected"> Selected Image: None</span>

                    <input type="file" name="selectedImage" class="form-control-file" accept="image/*" id="fileName" accept=".jpg,.jpeg,.png" onchange="validateFileType(event)" style="display:none;">
                    <label for="fileName" class="btn btn-primary">Browse</label>
                    
                    <input type="hidden" id="uploadedFileName" name="uploadedFileName" value="" required>
                    <input type="hidden" name="requestType" value="updateAlumniProfilePicture">
                    <input type="hidden" id="fileContent" name="fileContentBase64" value="" required>

                    <input type="submit" class="btn btn-primary" id="uploadButton" value="Upload" name="upload-btn" style="margin-bottom: 8px;">
                    
                    <%  if(!((String)request.getAttribute("profilePicture")).equals(AlumniPageList.ALUMNI_DEFAULT_PROFILE_PICTURE)) { %>
                    <input type="submit" class="btn btn-primary" value="Default" name="remove-btn" style="margin-bottom: 8px;" onClick="toDefault()">
                    <%  } %>
                </form>

                <script type="text/javascript">                    
                    var statusUpload = false;

                    function validateFileType(event){
                        var fileName = document.getElementById("fileName").value;
                        
                        fileName = fileName.replace(/.*[\/\\]/, '');
                        
                        var idxDot = fileName.lastIndexOf(".") + 1;
                        
                        var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
                        
                        if (extFile==="jpg" || extFile==="jpeg" || extFile==="png"){
                            document.getElementById('fileSelected').innerHTML = " Selected Image: " + fileName;
                            document.getElementById('uploadedFileName').value = fileName;
                            
                            // If file size > 500kB, resize such that width <= 1000, quality = 0.9
                            reduceFileSize(event.target.files[0], 500*1024, 1000, Infinity, 0.9, blob => {
                                let body = new FormData();
                                body.set('file', blob, blob.name || "file.jpg");
                                getBase64(body.get('file'))
                                    .then(data => { 
                                            var base64result = data.split(',')[1];
                                            document.getElementById("fileContent").value = base64result;
                                            var output = document.getElementById('profilePicture');
                                            output.src = data;
                                            statusUpload = true;
                                        }
                                    ).catch(err => {
                                        statusUpload = false;
                                        console.log(err);
                                    });
                            });
                        }else{
                            document.getElementById('fileSelected').innerHTML = " Selected Image: None";
                            alert("Only jpg/jpeg and png files are allowed!");
                        }   
                    }
                    
                    function getBase64(file) {
                        return new Promise((resolve, reject) => {
                            const reader = new FileReader();
                            reader.readAsDataURL(file);
                            reader.onload = () => resolve(reader.result);
                            reader.onerror = error => reject(error);
                        });
                    }                        

                    function toDefault() {
                        statusUpload = true;
                    }

                    function checkEitherImageIsSelected() {
                        if(statusUpload === null || statusUpload === undefined) {
                            return false;
                        }
                        
                        return statusUpload;
                    }
                    
                    // From https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/toBlob, needed for Safari:
                    if (!HTMLCanvasElement.prototype.toBlob) {
                        Object.defineProperty(HTMLCanvasElement.prototype, 'toBlob', {
                            value: function(callback, type, quality) {

                                var binStr = atob(this.toDataURL(type, quality).split(',')[1]),
                                    len = binStr.length,
                                    arr = new Uint8Array(len);

                                for (var i = 0; i < len; i++) {
                                    arr[i] = binStr.charCodeAt(i);
                                }

                                callback(new Blob([arr], {type: type || 'image/png'}));
                            }
                        });
                    }

                    window.URL = window.URL || window.webkitURL;

                    // Modified from https://stackoverflow.com/a/32490603, cc by-sa 3.0
                    // -2 = not jpeg, -1 = no data, 1..8 = orientations
                    function getExifOrientation(file, callback) {
                        // Suggestion from http://code.flickr.net/2012/06/01/parsing-exif-client-side-using-javascript-2/:
                        if (file.slice) {
                            file = file.slice(0, 131072);
                        } else if (file.webkitSlice) {
                            file = file.webkitSlice(0, 131072);
                        }

                        var reader = new FileReader();
                        reader.onload = function(e) {
                            var view = new DataView(e.target.result);
                            if (view.getUint16(0, false) != 0xFFD8) {
                                callback(-2);
                                return;
                            }
                            var length = view.byteLength, offset = 2;
                            while (offset < length) {
                                var marker = view.getUint16(offset, false);
                                offset += 2;
                                if (marker == 0xFFE1) {
                                    if (view.getUint32(offset += 2, false) != 0x45786966) {
                                        callback(-1);
                                        return;
                                    }
                                    var little = view.getUint16(offset += 6, false) == 0x4949;
                                    offset += view.getUint32(offset + 4, little);
                                    var tags = view.getUint16(offset, little);
                                    offset += 2;
                                    for (var i = 0; i < tags; i++)
                                        if (view.getUint16(offset + (i * 12), little) == 0x0112) {
                                            callback(view.getUint16(offset + (i * 12) + 8, little));
                                            return;
                                        }
                                }
                                else if ((marker & 0xFF00) != 0xFF00) break;
                                else offset += view.getUint16(offset, false);
                            }
                            callback(-1);
                        };
                        reader.readAsArrayBuffer(file);
                    }

                    // Derived from https://stackoverflow.com/a/40867559, cc by-sa
                    function imgToCanvasWithOrientation(img, rawWidth, rawHeight, orientation) {
                        var canvas = document.createElement('canvas');
                        if (orientation > 4) {
                            canvas.width = rawHeight;
                            canvas.height = rawWidth;
                        } else {
                            canvas.width = rawWidth;
                            canvas.height = rawHeight;
                        }

                        if (orientation > 1) {
                            console.log("EXIF orientation = " + orientation + ", rotating picture");
                        }

                        var ctx = canvas.getContext('2d');
                        switch (orientation) {
                            case 2: ctx.transform(-1, 0, 0, 1, rawWidth, 0); break;
                            case 3: ctx.transform(-1, 0, 0, -1, rawWidth, rawHeight); break;
                            case 4: ctx.transform(1, 0, 0, -1, 0, rawHeight); break;
                            case 5: ctx.transform(0, 1, 1, 0, 0, 0); break;
                            case 6: ctx.transform(0, 1, -1, 0, rawHeight, 0); break;
                            case 7: ctx.transform(0, -1, -1, 0, rawHeight, rawWidth); break;
                            case 8: ctx.transform(0, -1, 1, 0, 0, rawWidth); break;
                        }
                        ctx.drawImage(img, 0, 0, rawWidth, rawHeight);
                        return canvas;
                    }

                    function reduceFileSize(file, acceptFileSize, maxWidth, maxHeight, quality, callback) {
                        if (file.size <= acceptFileSize) {
                            callback(file);
                            return;
                        }
                        var img = new Image();
                        img.onerror = function() {
                            URL.revokeObjectURL(this.src);
                            callback(file);
                        };
                        img.onload = function() {
                            URL.revokeObjectURL(this.src);
                            getExifOrientation(file, function(orientation) {
                                var w = img.width, h = img.height;
                                var scale = (orientation > 4 ?
                                    Math.min(maxHeight / w, maxWidth / h, 1) :
                                    Math.min(maxWidth / w, maxHeight / h, 1));
                                h = Math.round(h * scale);
                                w = Math.round(w * scale);

                                var canvas = imgToCanvasWithOrientation(img, w, h, orientation);
                                canvas.toBlob(function(blob) {
                                    console.log("Resized image to " + w + "x" + h + ", " + (blob.size >> 10) + "kB");
                                    callback(blob);
                                }, 'image/jpeg', quality);
                            });
                        };
                        img.src = URL.createObjectURL(file);
                    }
                </script>

                <form action="AlumniController" method="POST">   
                    <div class="form-group row">
                        <div class="col">
                            <div class="form-group custom-shadow">
                                <jsp:include page="../components/alumniPersonalInformation.jsp" />
                            </div>

                            <div class="form-group custom-shadow">
                                <jsp:include page="../components/alumniProfessionalInformation.jsp" />
                            </div>
                        </div>
                        <div class="col center">
                            <div class="form-group custom-shadow table-custom">
                                <jsp:include page="../components/alumniMailingAddress.jsp" />
                            </div>
                        </div>
                    </div>

                    <div class="form-group custom-shadow">
                        <jsp:include page="../components/alumniEducationalInformation.jsp" />
                    </div>

                    <div class="form-group custom-shadow">
                        <jsp:include page="../components/alumniEmploymentInformation.jsp" />
                    </div>
                    
                    <hr class="my-4">
                    
                    <div class="form-group center">
                        <input type="hidden" name="requestType" value="updateAlumniInfo">
                        <button type="button" style="width:100px;" class="btn btn-success custom-shadow" onclick="updateConfirmation()">Save</button>
                    </div>
                    
                    <div id="overlay">
                        <div class="ovelay-content">
                            <div class="card-body">
                                <p class="card-text">Update the information?</p>
                                <input type="hidden" name="requestType" value="confirmDeleteAlumniInfo">
                                <input type="submit" name="save-btn" class="btn btn-danger" value="Yes">
                                <button type="button" name="no-btn" class="btn btn-success" onclick="noIsSelected()">No</button>
                            </div>
                        </div>
                    </div>
                </form>
                    
                <form action="AlumniController" method="POST">
                    <div class="form-group center">
                        <input type="hidden" name="requestType" value="updateAlumniInfo">
                        <input type="submit" style="width:100px;" name="cancel-btn" class="btn btn-primary custom-shadow" value="Cancel">
                    </div>
                </form>
            </div>
        </div>
        <script>
            document.getElementById('overlay').style.display = "none";
            
            function updateConfirmation() {
                document.getElementById('overlay').style.display = "block";
            }
            
            function noIsSelected() {
                document.getElementById('overlay').style.display = "none";
            }
        </script>
    </body>
</html>
