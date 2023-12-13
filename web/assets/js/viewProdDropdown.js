/* ViewProduct Page Category Dropdown  */
$('.sideCategBtn').click(function(){
    $(this).toggleClass("click");
    $('#cover').addClass("active");
    $('.sideCategBar').toggleClass("showSide");
//    $(document).on('click', function(e) {
//        if (!$(".sideCategPanel").is(e.target) && $(".sideCategPanel").has(e.target).length == 0) {
//            $('.sideCategPanel').fadeOut('slow');
//        }
//    });

});

//$(window).click(function(e) {
//    if (!e.target.matches('.sideCategBar')) {
//        $('.sideCategBar').removeClass('showSide');
//    }  
//});

$('.sideCateg1').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow1').toggleClass("rotate");

    $('.sideSubCategList1').toggleClass("show1");
    $(".sideSubCategList1 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
 });

$('.sideCateg2').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow2').toggleClass("rotate");

    $('.sideSubCategList2').toggleClass("show2");
    $(".sideSubCategList2 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
});

$('.sideCateg3').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow3').toggleClass("rotate");

    $('.sideSubCategList3').toggleClass("show3");
    $(".sideSubCategList3 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
 });

$('.sideCateg4').click(function() {
    $(this).toggleClass("show");             
    $('.sideArrow4').toggleClass("rotate");

    $('.sideSubCategList4').toggleClass("show4");
    $(".sideSubCategList4 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
});

$('.sideCateg5').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow5').toggleClass("rotate");

    $('.sideSubCategList5').toggleClass("show5");
    $(".sideSubCategList5 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
 });

$('.sideCateg6').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow6').toggleClass("rotate");

    $('.sideSubCategList6').toggleClass("show6");
    $(".sideSubCategList6 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
});

$('.sideCateg7').click(function() {
    $(this).toggleClass("show");
    $('.sideArrow7').toggleClass("rotate");

    $('.sideSubCategList7').toggleClass("show7");
    $(".sideSubCategList7 li a").each(function(){
        $(this).mousemove(function() {
           $(this).addClass("showSub"); 
        });

        $(this).mouseleave(function() {
           $(this).removeClass("showSub"); 
        });
    });
 });

$('div ul li').click(function() {
    $(this).addClass("active").siblings().removeClass("active");
});     



//$('.pagination-inner a').on('click', function() {
//    $(this).siblings().removeClass('pagination-active');
//    $(this).addClass('pagination-active');
//})
