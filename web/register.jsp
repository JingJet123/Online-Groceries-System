<%-- 
    Document   : register
    Created on : Mar 7, 2022, 1:30:40 AM
    Author     : Lee Jing Jet
--%>

<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AG Register</title>
        <link href="style.css" rel="stylesheet" />
        <link rel="shortcut icon" href="assets/img/AGLogo/favicon.png" type="image/x-icon">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="assets/css/register.css" rel="stylesheet" />

    </head>
    <body class="bg-theme-small">

        <!-- Start Breadcrumb 
        ============================================= -->
        <div class="register-area">
            <div class="container default-padding-top">
                <div class="row">
                    <!--                <div class="col-lg-6 offset-lg-4">-->
                    <div class="col-lg-8 col-md-8 col-sm-8">
                        <div class="register-box">
                            <div class="register">
                                <div class="content">
                                    <a href="index.jsp"><img src="assets/img/AGLogo/Logo4.png" alt="Logo" ></a>
                                    <form method="post" action="AddCustomer?index.jsp">
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="name" placeholder="Name*" type="text" 
                                                           pattern="[A-Za-z\s]+[.]?[/]?[A-Za-z\s]*" title="This is not a valid name" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="username" placeholder="Username*" type="text" 
                                                           pattern="[A-Za-z\d]{5,}" 
                                                           title="Username must be at least 5 characters and should contain alphabets and digits only" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="email" placeholder="Email*" type="email" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="contact" placeholder="Contact Number*" type="text" 
                                                           pattern="\d{3}[-]\d{7,8}" title="This is not a valid contact number" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="address" placeholder="Address*" type="text" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="password" placeholder="Password*" id="password" type="password" 
                                                           pattern="(?=.*[~!@#$%^&*()_+`\-=;':,./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\S{8,16}" required>
                                                    <span class="eye-icon"><i id="toggler1" class="fa fa-fw fa-eye-slash"></i></span>
                                                    <div class="errorMsgArea pass-require">
                                                        <span class="text-secondary">
                                                            Password requirements: <br/>
                                                            - Length: 8 - 16 characters <br/>
                                                            - At least one uppercase and lowercase <br/>
                                                            - At least one special character <br/>
                                                            - At least one digit
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <input class="form-control" name="passwordConfirm" placeholder="Confirm Password*" id="password2" 
                                                           onchange="isPasswordValid()" type="password" required>
                                                    <span class="eye-icon"><i id="toggler2" class="fa fa-fw fa-eye-slash"></i></span>
                                                    <div class="errorMsgArea">
                                                        <span id="error-message">Confirm Password does not match with the Password</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" id="invalidCheck" required>
                                                        <label class="form-check-label" for="invalidCheck">Agree to Terms and Conditions and Privacy Policy</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="row btn-wrap">
                                                <button type="submit" id="submitBtn" class="register-btn">Register</button>
                                                <button type="reset" class="register-btn">Reset</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="login">
                                        <p>Already have an account? <a href="login.jsp">Login now</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Breadcrumb -->
        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>
    </body>
    <script>
        function isPasswordValid() {
            var pass = document.getElementById('password').value;
            var confPass = document.getElementById('password2').value;
            var errorMsg = document.getElementById('error-message');
            var submitBtn = document.getElementById('submitBtn');

            if (pass !== confPass) {
                errorMsg.style.display = "block";
                submitBtn.disabled = true;
            } else {
                errorMsg.style.display = "none";
                submitBtn.disabled = false;
            }
        }

    </script>
    <script src="assets/js/togglePass.js"></script>
    <script src="https://kit.fontawesome.com/c6f1f55876.js" crossorigin="anonymous"></script>
</html>
