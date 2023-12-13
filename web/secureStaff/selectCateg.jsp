<%@page import="entity.Category"%>
<%@page import="entity.SubCategory"%>
<%@page import="java.util.List"%>
<%
    //Getting all Category records
    List<Category> categList = null;
    if ((List<Category>) session.getAttribute("categList") != null) {
        categList = (List<Category>) session.getAttribute("categList");
    } else {
        categList = (List<Category>) application.getAttribute("categList");
    }

    //Getting all SubCategory records in respect to that category
    List<SubCategory> getSubCategList = null;
    if ((List<SubCategory>) session.getAttribute("getSubCategList") != null) {
        getSubCategList = (List<SubCategory>) session.getAttribute("getSubCategList");
    } else {
        getSubCategList = (List<SubCategory>) application.getAttribute("subCategList");
    }

%>

<script>
var categoryObject = {
   <% for(Category categ: categList) { %>
        "<%=categ.getCategoryName()%>": {
           <% for(SubCategory subCateg: getSubCategList) { 
                 if(subCateg.getCategoryId().getCategoryId().equals(categ.getCategoryId())) { %>    
                    "<%=subCateg.getSubCategoryName() %>": [],
           <% } } %>
        },    
     <% } %>
}; 

window.onload = function () {
    let categorySel = document.getElementById("categSelect");
    let subcategorySel = document.getElementById("subCategSelect");
    for (let x in categoryObject) {
        categorySel.options[categorySel.options.length] = new Option(x, x);
    }

    categorySel.onchange = function () {
        //empty Chapters- and Topics- dropdowns
        subcategorySel.length = 1;
        //display correct values
        for (let y in categoryObject[this.value]) {
            subcategorySel.options[subcategorySel.options.length] = new Option(y, y);
        }
    }
    
    let categorySel2 = document.getElementById("categSelect2");
    let subcategorySel2 = document.getElementById("subCategSelect2");
    for (let x in categoryObject) {
        categorySel2.options[categorySel2.options.length] = new Option(x, x);
    }

    categorySel2.onchange = function () {
        //empty Chapters- and Topics- dropdowns
        subcategorySel2.length = 1;
        //display correct values
        for (let y in categoryObject[this.value]) {
            subcategorySel2.options[subcategorySel2.options.length] = new Option(y, y);
        }
    }
};

    
</script>