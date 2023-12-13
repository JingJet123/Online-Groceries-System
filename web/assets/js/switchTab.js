var tabs = document.querySelectorAll(".nav-link");
var contents = document.querySelectorAll(".tab-pane");

function showContent(hashContents) {
  
  if (hashContents !== undefined) {
    location.hash = "#" + hashContents;
  }

  // tabs.length must be same with contents.length
  for (var i = 0; i < tabs.length; i++) {
    tabs[i].classList.remove("active");
    contents[i].classList.remove("active", "show");

    if (window.location.hash === "#" + tabs[i].id) {
      tabs[i].classList.add("active");
      contents[i].classList.add("active", "show");
    }
  }
}


