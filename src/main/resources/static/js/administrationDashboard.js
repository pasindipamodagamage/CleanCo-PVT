const sideMenu = document.querySelector('aside');
const menuBtn = document.getElementById('menu-btn');
const closeBtn = document.getElementById('close-btn');

const darkMode = document.querySelector('.dark-mode');

menuBtn.addEventListener('click', () => {
    sideMenu.style.display = 'block';
});

closeBtn.addEventListener('click', () => {
    sideMenu.style.display = 'none';
});

darkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode-variables');
    darkMode.querySelector('span:nth-child(1)').classList.toggle('active');
    darkMode.querySelector('span:nth-child(2)').classList.toggle('active');
});

document.addEventListener("DOMContentLoaded", function () {
    const sidebarLinks = document.querySelectorAll(".sidebar a");

    function setActiveLink(clickedLink) {
        sidebarLinks.forEach(link => link.classList.remove("active"));
        clickedLink.classList.add("active");
    }

    sidebarLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            setActiveLink(link);
        });
    });

    const defaultLink = document.querySelector(".sidebar a.active");
    setActiveLink(defaultLink);
});

// Toggle sidebar visibility
menuBtn.addEventListener('click', () => {
    sideMenu.style.display = 'block'; // Ensures the side menu is shown
});

closeBtn.addEventListener('click', () => {
    sideMenu.style.display = 'none'; // Ensures the side menu is hidden
});

// Dark mode toggle
darkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
});
Selectors
// const sideMenu = document.querySelector('.sidebar'); // FIXED: Correctly selecting sidebar
// const menuBtn = document.getElementById('menu-btn');
// const closeBtn = document.getElementById('close-btn');
// const darkMode = document.getElementById('dark-mode');
//
// // Toggle sidebar visibility
// menuBtn.addEventListener('click', () => {
//     sideMenu.style.left = "0px"; // Show sidebar
// });
//
// closeBtn.addEventListener('click', () => {
//     sideMenu.style.left = "-250px"; // Hide sidebar
// });
//
// // Fix navigation issue
// document.addEventListener("DOMContentLoaded", function () {
//     const sidebarLinks = document.querySelectorAll(".sidebar a");
//
//     function setActiveLink(clickedLink) {
//         sidebarLinks.forEach(link => link.classList.remove("active"));
//         clickedLink.classList.add("active");
//     }
//
//     sidebarLinks.forEach(link => {
//         link.addEventListener("click", function () {
//             setActiveLink(link);  // Fix: Remove preventDefault to allow page navigation
//         });
//     });
//
//     const defaultLink = document.querySelector(".sidebar a.active");
//     if (defaultLink) {
//         setActiveLink(defaultLink);
//     }
// });
//
// // Dark Mode Toggle
// darkMode.addEventListener('click', () => {
//     document.body.classList.toggle('dark-mode-variables');
// });
