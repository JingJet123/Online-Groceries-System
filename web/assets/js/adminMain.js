//====================================================
// Select subcategory only if category is selected
//====================================================
//var categoryObject = {
//    "Beverages":
//            {"Alcohol": [], "Coffee Tea": [], "Juice": [], "Soft Drinks": [], "Water": []},
//    "CAF Products":
//            {"Dairy": [], "Frozen": [], "Ice Cream": []},
//    "Fresh Produce":
//            {"Eggs": [], "Fruits": [], "Meat": [], "Seafood": [], "Vegetables": []},
//    "Household":
//            {"Home Cleaning": [], "Kitchen Utensils": [], "Laundry": [], "Paper Products": []},
//    "Packeged Food":
//            {"Canned Goods": [], "Chip and Biscuits": [], "Chocolate and Candies": [], "Dried Nuts": [], "Instant Food": []},
//    "Pantry & Ingredient":
//            {"Grains": [], "Noodles": [], "Oil": [], "Sauce Dressing": [], "Spice Seasoning": []},
//    "Personal Care":
//            {"Body Care": [], "Hair Care": [], "Oral Care": [], "Sanitary": [], "Skin Care": []}
//}
//
//window.onload = function () {
//    let categorySel = document.getElementById("categSelect");
//    let subcategorySel = document.getElementById("subCategSelect");
//    for (let x in categoryObject) {
//        categorySel.options[categorySel.options.length] = new Option(x, x);
//    }
//
//    categorySel.onchange = function () {
//        //empty Chapters- and Topics- dropdowns
//        subcategorySel.length = 1;
//        //display correct values
//        for (let y in categoryObject[this.value]) {
//            subcategorySel.options[subcategorySel.options.length] = new Option(y, y);
//        }
//    }
//    
//    let categorySel2 = document.getElementById("categSelect2");
//    let subcategorySel2 = document.getElementById("subCategSelect2");
//    for (let x in categoryObject) {
//        categorySel2.options[categorySel2.options.length] = new Option(x, x);
//    }
//
//    categorySel2.onchange = function () {
//        //empty Chapters- and Topics- dropdowns
//        subcategorySel2.length = 1;
//        //display correct values
//        for (let y in categoryObject[this.value]) {
//            subcategorySel2.options[subcategorySel2.options.length] = new Option(y, y);
//        }
//    }
//};

//==========================================
//  For opening and closing popup for CRUD
//==========================================
const popupAdd = document.querySelector('.addBtn');
const sidebar = document.querySelector('.sidebar');

popupAdd.addEventListener('click', () => {
    //Temporary make all content to back
    sidebar.style.zIndex = -1;
    
    //After popup closed then must add back z-index, else not functionable
    document.querySelector('.close-popup').addEventListener('click', () => {
        sidebar.style.zIndex = 99;
    });
});



