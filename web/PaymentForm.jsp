<%-- Document : processPayment Created on : Mar 28, 2022, 8:48:51 PM Author :
Joey Kok Yen Ni --%> 
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="entity.*"%>
<%@page errorPage="errorPage.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" class="entity.Order" scope="session"></jsp:useBean>

<%
    Payment payment = new Payment();
    payment.setOrderId(order);
%>

<!DOCTYPE html>
<html>
    <head>
        <!-- ========== Meta Tags ========== -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <!-- ========== Page Title ========== -->
        <title>AG Market</title>

        <!-- ========== Favicon Icon ========== -->
        <link
            rel="shortcut icon"
            href="assets/img/AGLogo/favicon.png"
            type="image/x-icon"
            />
        <link
            href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css"
            rel="stylesheet"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
            />

        <!-- ========== Start Stylesheet ========== -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="assets/css/bootsnav.css" rel="stylesheet" />
        <link href="assets/css/responsive.css" rel="stylesheet" />
        <link href="style.css" rel="stylesheet" />
        <link href="layout.css" rel="stylesheet" />

        <link href="assets/css/paymentForm.css" rel="stylesheet" />

        <!-- ========== End Stylesheet ========== -->


    </head>
    <body onload="endPreLoading()">
        <!-- Preloader Start -->
        <jsp:include page="common/preloader.jsp">
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png" />
        </jsp:include>
        <!-- Preloader Ends -->

        <jsp:include page="common/header.jsp">
            <jsp:param name="loginPath" value="login.jsp" />
            <jsp:param name="registerPath" value="register.jsp" />
            <jsp:param name="logoImgSrc" value="assets/img/AGLogo/Logo2.png" />
            <jsp:param name="indexPath" value="index.jsp" />
        </jsp:include>

        <!-- Any new contents place here -->
        <div class="payment_wrapper_top"><h2>Payment Form</h2></div>
        <div style="background-color: #ffffcc">
            <div class="payment_wrapper row" >

                <div class="col-4">
                    <div class="payment_wrapper_brief ">
                        <div class="order-amount">       <!--Get amount from order-->
                            <h4>Order Amount: RM <%= String.format("%.2f", Double.parseDouble(request.getParameter("oriOrderTotal")))%></h4>   <!--Get amount from order-->
                            <h4>Discount: RM <%= String.format("%.2f", Double.parseDouble(request.getParameter("discount")))%></h4>       <!--Get amount from order-->
                        </div>
                        <h4>Total Amount:</h4>
                        <div class="input_group">
                            <div class="input_box">
                                <h3>RM <%= String.format("%.2f", order.getTotalAmount())%></h3> <!--Get amount from order (Calculate)-->
                            </div>

                        </div>
                        <div class="input_group">
                            <div class="input_box update-btn" style="width: 100%;" >
                                <a href="OrderCheckOut.jsp"><h5>Back To Order Summary</h5></a>
                            </div>
                        </div>

                    </div>
                    <div class="preload">
                        <div class="creditcard">
                            <div class="front">
                                <div id="ccsingle"></div>
                                <svg version="1.1" id="cardfront" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                     x="0px" y="0px" viewBox="0 0 750 471" style="enable-background:new 0 0 750 471;" xml:space="preserve">
                                <g id="Front">
                                <g id="CardBackground">
                                <g id="Page-1_1_">
                                <g id="amex_1_">
                                <path id="Rectangle-1_1_" class="lightcolor grey" d="M40,0h670c22.1,0,40,17.9,40,40v391c0,22.1-17.9,40-40,40H40c-22.1,0-40-17.9-40-40V40
                                      C0,17.9,17.9,0,40,0z" />
                                </g>
                                </g>
                                <path class="darkcolor greydark" d="M750,431V193.2c-217.6-57.5-556.4-13.5-750,24.9V431c0,22.1,17.9,40,40,40h670C732.1,471,750,453.1,750,431z" />
                                </g>
                                <text transform="matrix(1 0 0 1 60.106 295.0121)" id="svgnumber" class="st2 st3 st4">0123 4567 8910 1112</text>
                                <text transform="matrix(1 0 0 1 54.1064 428.1723)" id="svgname" class="st2 st5 st6">AG Market</text>
                                <text transform="matrix(1 0 0 1 54.1074 389.8793)" class="st7 st5 st8">cardholder name</text>
                                <text transform="matrix(1 0 0 1 479.7754 388.8793)" class="st7 st5 st8">expiration</text>
                                <text transform="matrix(1 0 0 1 65.1054 241.5)" class="st7 st5 st8">card number</text>
                                <g>
                                <text transform="matrix(1 0 0 1 574.4219 433.8095)" id="svgexpire" class="st2 st5 st9">01/23</text>
                                <text transform="matrix(1 0 0 1 479.3848 417.0097)" class="st2 st10 st11">VALID</text>
                                <text transform="matrix(1 0 0 1 479.3848 435.6762)" class="st2 st10 st11">THRU</text>
                                <polygon class="st2" points="554.5,421 540.4,414.2 540.4,427.9 		" />
                                </g>
                                <g id="cchip">
                                <g>
                                <path class="st2" d="M168.1,143.6H82.9c-10.2,0-18.5-8.3-18.5-18.5V74.9c0-10.2,8.3-18.5,18.5-18.5h85.3
                                      c10.2,0,18.5,8.3,18.5,18.5v50.2C186.6,135.3,178.3,143.6,168.1,143.6z" />
                                </g>
                                <g>
                                <g>
                                <rect x="82" y="70" class="st12" width="1.5" height="60" />
                                </g>
                                <g>
                                <rect x="167.4" y="70" class="st12" width="1.5" height="60" />
                                </g>
                                <g>
                                <path class="st12" d="M125.5,130.8c-10.2,0-18.5-8.3-18.5-18.5c0-4.6,1.7-8.9,4.7-12.3c-3-3.4-4.7-7.7-4.7-12.3
                                      c0-10.2,8.3-18.5,18.5-18.5s18.5,8.3,18.5,18.5c0,4.6-1.7,8.9-4.7,12.3c3,3.4,4.7,7.7,4.7,12.3
                                      C143.9,122.5,135.7,130.8,125.5,130.8z M125.5,70.8c-9.3,0-16.9,7.6-16.9,16.9c0,4.4,1.7,8.6,4.8,11.8l0.5,0.5l-0.5,0.5
                                      c-3.1,3.2-4.8,7.4-4.8,11.8c0,9.3,7.6,16.9,16.9,16.9s16.9-7.6,16.9-16.9c0-4.4-1.7-8.6-4.8-11.8l-0.5-0.5l0.5-0.5
                                      c3.1-3.2,4.8-7.4,4.8-11.8C142.4,78.4,134.8,70.8,125.5,70.8z" />
                                </g>
                                <g>
                                <rect x="82.8" y="82.1" class="st12" width="25.8" height="1.5" />
                                </g>
                                <g>
                                <rect x="82.8" y="117.9" class="st12" width="26.1" height="1.5" />
                                </g>
                                <g>
                                <rect x="142.4" y="82.1" class="st12" width="25.8" height="1.5" />
                                </g>
                                <g>
                                <rect x="142" y="117.9" class="st12" width="26.2" height="1.5" />
                                </g>
                                </g>
                                </g>
                                </g>
                                <g id="Back">
                                </g>
                                </svg>
                            </div>
                            <div class="back">
                                <svg version="1.1" id="cardback" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                     x="0px" y="0px" viewBox="0 0 750 471" style="enable-background:new 0 0 750 471;" xml:space="preserve">
                                <g id="Front">
                                <line class="st0" x1="35.3" y1="10.4" x2="36.7" y2="11" />
                                </g>
                                <g id="Back">
                                <g id="Page-1_2_">
                                <g id="amex_2_">
                                <path id="Rectangle-1_2_" class="darkcolor greydark" d="M40,0h670c22.1,0,40,17.9,40,40v391c0,22.1-17.9,40-40,40H40c-22.1,0-40-17.9-40-40V40
                                      C0,17.9,17.9,0,40,0z" />
                                </g>
                                </g>
                                <rect y="61.6" class="st2" width="750" height="78" />
                                <g>
                                <path class="st3" d="M701.1,249.1H48.9c-3.3,0-6-2.7-6-6v-52.5c0-3.3,2.7-6,6-6h652.1c3.3,0,6,2.7,6,6v52.5
                                      C707.1,246.4,704.4,249.1,701.1,249.1z" />
                                <rect x="42.9" y="198.6" class="st4" width="664.1" height="10.5" />
                                <rect x="42.9" y="224.5" class="st4" width="664.1" height="10.5" />
                                <path class="st5" d="M701.1,184.6H618h-8h-10v64.5h10h8h83.1c3.3,0,6-2.7,6-6v-52.5C707.1,187.3,704.4,184.6,701.1,184.6z" />
                                </g>
                                <text transform="matrix(1 0 0 1 621.999 227.2734)" id="svgsecurity" class="st6 st7">985</text>
                                <g class="st8">
                                <text transform="matrix(1 0 0 1 518.083 280.0879)" class="st9 st6 st10">security code</text>
                                </g>
                                <rect x="58.1" y="378.6" class="st11" width="375.5" height="13.5" />
                                <rect x="58.1" y="405.6" class="st11" width="421.7" height="13.5" />
                                <text transform="matrix(1 0 0 1 59.5073 228.6099)" id="svgnameback" class="st12 st13">AG Market</text>
                                </g>
                                </svg>
                            </div>
                        </div>    

                    </div>
                </div>


                <div class="col-8">
                    <form action="AddToOrder" method="post" onSubmit="return confirm('Confirm to Pay RM<%=String.format("%.2f", order.getTotalAmount())%> and Place Order?');">
                        <h2>Shipping</h2>
                        <h4>Delivery Address</h4>
                        <div class="input_group">
                            <div class="input_box">
                                <input type="text" name="address" placeholder="Address" required class="name" />
                                <i class="fa fa-map-marker icon" aria-hidden="true"></i>
                            </div>
                        </div>
                        <div class="input_group">
                            <div class="input_box">
                                <input
                                    type="text"
                                    name="postcode"
                                    placeholder="Postcode"
                                    required
                                    class="name"
                                    />
                                <i class="fa fa-institution icon"></i>
                            </div>
                            <div class="input_box">
                                <input
                                    type="text"
                                    name="city"
                                    placeholder="City"
                                    required
                                    class="name"
                                    />
                                <i class="fa fa-institution icon"></i>
                            </div>
                        </div>


                        <!--Payment Details Start-->
                        <h2>Payment Details</h2>
                        <div class="input_group">
                            <div class="input_box">
                                <h4>Payment Type</h4>
                                <input type="radio" name="pay" value="Credit/Debit Card" class="radio" id="bc1" onclick="EnableDisableTextBox()" required="required"/>
                                <label for="bc1"
                                       ><span>
                                        <i class="fa fa-credit-card"></i>&nbsp Credit/Debit Card</span
                                    ></label
                                >
                                <input type="radio" name="pay" value="COD" class="radio" id="bc2" onclick="EnableDisableTextBox()"  />
                                <label for="bc2">
                                    <span> <i class="fa fa-money"></i>&nbsp COD</span></label
                                >
                            </div>
                        </div>
                        <div class="input_group">
                            <div class="input_box">
                                <input
                                    id="cardNum"
                                    name="cardNum"
                                    class="name"
                                    placeholder="Card Number 4111 2222 3333 4444"
                                    pattern="[45]{1}[\d]{3} [\d]{4} [\d]{4} [\d]{3,4}"
                                    required
                                    disabled="disabled"
                                    onkeydown="inputCardNum(this.value)"
                                    />
                                <i class="fa fa-credit-card icon"></i>
                            </div>
                        </div>
                        <div class="input_group">
                            <div class="input_box">
                                <input
                                    id="cardCVC"
                                    name="cardCVC"
                                    class="name"
                                    placeholder="Card CVC 632"
                                    pattern="^[0-9]{3}$"
                                    required
                                    disabled="disabled"
                                    />
                                <i class="fa fa-user icon"></i>
                            </div>
                        </div>
                        <div class="input_group">
                            <div class="input_box">
                                <div class="input_box">
                                    <input
                                        id="expMonth"
                                        type="number"
                                        name="expMonth"
                                        placeholder="Exp Month"
                                        min="<%=Calendar.getInstance().get(Calendar.MONTH) + 2%>"
                                        max="<%=Calendar.DECEMBER + 1%>"
                                        required
                                        class="name"
                                        disabled="disabled"
                                        />
                                    <i class="fa fa-calendar icon" aria-hidden="true"></i>
                                </div>
                            </div>
                            <div class="input_box">
                                <input id="expYear" type="number" min="<%=Calendar.getInstance().get(Calendar.YEAR)%>" max="<%=Calendar.getInstance().get(Calendar.YEAR) + 3%>" placeholder="Exp Year" required disabled="disabled" class="name" />
                                <i class="fa fa-calendar-o icon" aria-hidden="true"></i>
                            </div>
                        </div>
                        <!--Payment Details End-->
                        <div class="row justify-content-end">
                            <div class="input_group" style="margin: 10px;width: 30%;">
                                <div class="input_box">
                                    <button class="update-btn" type="submit">
                                        <h3 style="margin-bottom: 0; color: beige">PAY NOW</h3>
                                    </button>
                                </div>
                            </div>
                            <div class="input_group" style="margin: 10px; width: 30%; ">
                                <div class="input_box">
                                    <button class="reset-btn" type="reset">
                                        <h3 style="margin-bottom: 0; color: beige">RESET</h3>
                                    </button>
                                </div>
                            </div>
                        </div>


                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="common/alertPop.jsp">
            <jsp:param name="alert" value="<%= session.getAttribute("alert")%>" />
            <jsp:param name="alertMsg" value="<%= session.getAttribute("alertMsg")%>" />
            <jsp:param name="alertType" value="<%= session.getAttribute("alertType")%>" />
        </jsp:include>

        <jsp:include page="common/footer.jsp" >
            <jsp:param name="faviconImgSrc" value="assets/img/AGLogo/favicon.png"/>
        </jsp:include>

        <!-- jQuery Frameworks
        ============================================= -->
        <!-- Custom bootstrap -->
        <script src="assets/js/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootsnav.js"></script>
        <script src="assets/js/main.js"></script>

        <!-- Pre-loading -->
        <script src="assets/js/preloader.js"></script>

        <!--View Cart ======= Disable View Cart For Payment Page =======--> 
        <!--<script src="assets/js/cartOpen.js"></script>-->

        <!-- Accept Cookies 
        (Note: If got time need to make in java code)
               Need find ways to click only one time if a user has clicked before -->
        <!--<script src="assets/js/acceptDrop.js"></script>-->

        <!-- End jQuery Frameworks
        ============================================= -->

        <script type="text/javascript">
                                        function inputCardNum(val) {
                                            var cardNum = document.getElementById('cardNum');
                                            var key = event.keyCode || event.charCode;
                                            if (key == 8 || key == 46) {
                                            } else {
                                                var input = val.replace(/[\s]/g, '');
                                                if (input.length !== 0 && input.length % 4 === 0) {
                                                    cardNum.value += " ";
                                                }
                                            }
                                        }

                                        // CREDIT CARD IMAGE JS
                                        document.querySelector('.preload').classList.remove('preload');
                                        document.querySelector('.creditcard').addEventListener('click', function () {
                                            if (this.classList.contains('flipped')) {
                                                this.classList.remove('flipped');
                                            } else {
                                                this.classList.add('flipped');
                                            }
                                        });

                                        function EnableDisableTextBox() {
                                            var bc1 = document.getElementById("bc1");
                                            var expYear = document.getElementById("expYear");
                                            var expMonth = document.getElementById("expMonth");
                                            var cardCVC = document.getElementById("cardCVC");
                                            var cardNum = document.getElementById("cardNum");
                                            expYear.disabled = bc1.checked ? false : true;
                                            expMonth.disabled = bc1.checked ? false : true;
                                            cardCVC.disabled = bc1.checked ? false : true;
                                            cardNum.disabled = bc1.checked ? false : true;
                                            if (!cardNum.disabled && !cardCVC.disabled && !expMonth.disabled && !expYear.disabled) {
                                                cardNum.style.backgroundColor = "white";
                                                cardCVC.style.backgroundColor = "white";
                                                expMonth.style.backgroundColor = "white";
                                                expYear.style.backgroundColor = "white";
                                                cardNum.focus();
                                            } else {
                                                cardNum.style.backgroundColor = "lightgrey";
                                                cardCVC.style.backgroundColor = "lightgrey";
                                                expMonth.style.backgroundColor = "lightgrey";
                                                expYear.style.backgroundColor = "lightgrey";
                                            }
                                        }
        </script>

    </body>
</html>
