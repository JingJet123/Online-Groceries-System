/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var preLoader = document.getElementById('pre-loader');

function endPreLoading() {
    preLoader.style.display = 'none';
}

//To refresh page everytime a browser back-button is clicked
(function () {
    window.onpageshow = function(event) {
        if (event.persisted) {
            window.location.reload();
        }
    };
})();

