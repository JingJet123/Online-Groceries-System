// Create cookie
document.addEventListener('DOMContentLoaded', function () {
  const cookies = document.querySelector('.cookie-banner');
  const close = document.querySelector('.close');

  // Get value from localStorage when open the page
  const lS = localStorage.getItem('first-visit');

  // Add button 'click' event
  close.addEventListener('click', () => {
    // Set value to the localStorage
    localStorage.setItem('first-visit', false);
    // hide DOM element
    cookies.style.display = 'none';
  });

  // This does not check on the first visit to the page
  // If localStorage have value, hide DOM element
  if (lS) topDiv.style.display = 'none';
});