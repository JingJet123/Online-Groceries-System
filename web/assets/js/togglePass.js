var password1 = document.getElementById('password');
var toggler1 = document.getElementById('toggler1');
showHidePassword = () => {
    if (password1.type === 'password') {
        password1.setAttribute('type', 'text');
        toggler1.classList.remove('fa-eye-slash');
        toggler1.classList.add('fa-eye');
    } else {
        toggler1.classList.add('fa-eye-slash');
        toggler1.classList.remove('fa-eye');
        password1.setAttribute('type', 'password');
    }
};

var password2 = document.getElementById('password2');
var toggler2 = document.getElementById('toggler2');
showHidePassword2 = () => {
    if (password2.type === 'password') {
        password2.setAttribute('type', 'text');
        toggler2.classList.remove('fa-eye-slash');
        toggler2.classList.add('fa-eye');
    } else {
        toggler2.classList.add('fa-eye-slash');
        toggler2.classList.remove('fa-eye');
        password2.setAttribute('type', 'password');
    }
};

toggler1.addEventListener('click', showHidePassword);
toggler2.addEventListener('click', showHidePassword2);


