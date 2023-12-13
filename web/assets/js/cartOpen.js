//  Cart
let cartIcon = document.querySelector(".cartIcon");
let cart = document.querySelector(".cart");
let closeCart = document.querySelector("#close-cart");

//When Open Cart
cartIcon.onclick = () => {
    cart.classList.add("active");
};

//When Close Cart
closeCart.onclick = () => {
    cart.classList.remove("active");
};

function stacking(success) {
    let msgToDisp;
    let message = document.getElementById("toast");
    message.className = "show";
    if(success) {
        msgToDisp = "You have already added this item to cart. Please select another...";
    } else {
        msgToDisp = "You've successfully added this item...";
    }
    document.getElementById("description").innerText = msgToDisp;
    setTimeout(function(){ 
        message.className = message.className.replace("show", ""); 
    }, 2000);
}

