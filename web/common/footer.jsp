<%-- 
    Document   : footer
    Created on : Mar 6, 2022, 11:57:33 PM
    Author     : Chuah Shee Yeap
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<footer class="default-padding footer-padding bg-footer">
    <div class="container middle-content-l">
        <div class="row footer-bottom">
            <div class="col-lg-2 col-md-12 col-sm-2">
                <div class="single-footer-widget">
                    <a href="#" class="AG_logo title">
                        <img src="<%= request.getParameter("faviconImgSrc")%>" alt="logo"  width="120px">
                    </a>
                    <p style="font-size: small;">At yout convenience.</p>
                </div>
            </div>
            <div class="col-lg-6 col-md-11 col-sm-12">
                <div class="single-footer-widget pl-5">
                    <h4 class="title">Resources</h4>
                    <div class="row">
                        <div class="col-lg-6">
                            <ul class="footer-resources">
                                <li><a href="">About Us</a></li>
                                <li><a href="">Contact Us</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-6">
                            <ul class="footer-resources">
                                <li><a href="">Policy Privacy</a></li>
                                <li><a href="">Terms and Condition</a></li>
                                <li><a href="">FAQ</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-12 col-sm-12">
                <div class="single-footer-widget community-area pl-5">
                    <h4 class="title">Join Our Community</h4>
                    <ul class="footer-links-list">
                        <li><a target="_blank" href="https://discord.gg/BND3amjwJs" class="cs">
                                <span class="icon icon-discord"></span></a></li>
                        <li><a target="_blank" href="https://twitter.com/AGMarket4" class="cs">
                                <span class="icon icon-twitter"></span></a></li>
                        <li><a target="_blank" href="https://t.me/+q2CLxJhj1zFmYWFl" class="cs">
                                <span class="icon icon-telegram"></span></a></li>
                        <li><a target="_blank" href="https://www.facebook.com/AG-Market-104424522196094" class="cs">
                                <span class="icon1 icon-facebook"></span></a></li>
                        <!-- <span class="icon1 icon1-github"></span> -->
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer-bottom-area">
            <p><i class='bx bx-copyright'></i>
                &#9830;&nbsp; Tel-to: <a href="tel:+60123032688">012-3032688</a> 
                &nbsp;&#9830;&nbsp; Email-to: <a href="mailto:agmart1314@gmail.com">agmart1314@gmail.com</a> &nbsp;&#9830;
                <br/>No.999 Jalan Bintang, Bukit Sri Bintang, Kuala Lumpur, Federal Territory of Kuala Lumpur.
                <br/>This is Copyright © 2022 <strong>AG Market</strong>. All Rights Will Be Reserved.</p>

        </div>
    </div>
</footer>
