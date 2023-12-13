//For opening and closing popup for product filter (ViewProduct page)) 
const popupFilter = document.querySelector('.popup-button');
const shop = document.querySelector('.shop');

popupFilter.addEventListener('click', () => {
    //Temporary make shop product content to back
    shop.style.zIndex = -1;
    
    //After popup closed then must add back shop's z-index, else not functionable
    document.querySelector('.close-popup').addEventListener('click', () => {
        shop.style.zIndex = 99;
    })
});

//Price range slider
(function() {
   var parent = document.querySelector(".price-range-wrapper");
   if(!parent) return;

   var rangeS = parent.querySelectorAll(".price-slider-range");
   var numberS = parent.querySelectorAll(".price-text-range");

    rangeS.forEach(function(el) {
        el.oninput = function() {
            var slide1 = parseFloat(rangeS[0].value);
            var slide2 = parseFloat(rangeS[1].value);
          
            if (slide1 > slide2) {                
              [slide1, slide2] = [slide2, slide1];
//               var tmp = slide2;
//               slide2 = slide1;
//               slide1 = tmp;
            }
          
            numberS[0].value = slide1;
            numberS[1].value = slide2;
        }
    });
    
    numberS.forEach(function(el) {
        el.oninput = function() {
           var number1 = parseFloat(numberS[0].value),
           number2 = parseFloat(numberS[1].value);
    	 		
           if (number1 > number2) {
               var tmp = number1;
               numberS[0].value = number2;
               numberS[1].value = tmp;
           }
     
           rangeS[0].value = number1;
           rangeS[1].value = number2
        }
    });
})();


//Product's Category Filter must have at least one check-boex has been checked
let chkboxs = document.querySelectorAll('input[type="checkbox"][name="chkCateg"]');
let msgToNoBoxChecked = "Hey, Sorry...\nAt least one checkbox must be checked before filtering!";

const isOneChecked = function(e) {
    e.preventDefault();
    let _this = this;
    let arrVal = Array.prototype
        .slice
        .call(document.querySelectorAll('input[type="checkbox"][name="chkCateg"]:checked'))
        .map(function(cur){ return cur.value });

    if(!arrVal.length){
        _this.checked = true;
        alert(msgToNoBoxChecked);
    }
};

for(let i = chkboxs.length; i--;) {
    chkboxs[i].addEventListener('change', isOneChecked, false);
}


