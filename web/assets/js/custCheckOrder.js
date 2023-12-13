/*===== LINK ACTIVE  =====*/
const statusLinkColor = document.querySelectorAll(".status-btn");
function statusColorLink() {
  statusLinkColor.forEach((l) => l.classList.remove("btn-active"));
  this.classList.add("btn-active");
}
statusLinkColor.forEach((l) => l.addEventListener("click", statusColorLink));
