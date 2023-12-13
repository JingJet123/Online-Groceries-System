/* Index Page Category Dropdown  */
const btns = document.querySelectorAll('.categ-main');
const sortBtn = document.querySelectorAll('.sort-btn');
const dropMenus = document.querySelectorAll('.drop-menu');
const sortMenus = document.querySelectorAll('.sort-menu');
const dropBtn = document.querySelectorAll('.drop-btn');
const dropdownMenu = document.querySelectorAll('.dropdown-menu');

window.addEventListener("resize", function() {
    if (window.matchMedia("(max-width: 991px)").matches) {
        dropBtn.addEventListener('click', () => {
            dropdownMenu.classList.add(' .active');
            document.querySelector(dropdownMenu.dataset.target).classList.add(' .active');
        });
    } 
})

btns.forEach(btn => {
    btn.addEventListener('mousemove', () => {
        removeActive();
        btn.classList.add('active');
        document.querySelector(btn.dataset.target).classList.add('active');
    });
});

//sortBtn.addEventListener('mousemove', () => {
//    removeActive();
//    sortBtn.classList.add('active');
//    document.querySelector(sortBtn.dataset.target).classList.add('active');
//});

sortBtn.forEach(btn => {
    btn.addEventListener('mousemove', () => {
        removeActive();
        btn.classList.add('active');
        document.querySelector(btn.dataset.target).classList.add('active');
    });
});

const removeActive = () => {
    btns.forEach(btn => btn.classList.remove('active'));
    dropMenus.forEach(dropmenu => dropmenu.classList.remove('active'));
    sortMenus.forEach(sortmanu => sortmanu.classList.remove('active'));
};

window.onclick = (e) => {
    if (!e.target.matches('.categ-main') || !e.target.matches('.categ-sub') || !e.target.matches('.sort-btn')) {
        removeActive();
    } 
};


