    AOS.init();

    document.addEventListener('DOMContentLoaded', function() {
    const animateElements = document.querySelectorAll('.service-card, .pricing-card');

    window.addEventListener('scroll', checkScroll);

    function checkScroll() {
    animateElements.forEach(element => {
    const elementTop = element.getBoundingClientRect().top;
    if (elementTop < window.innerHeight - 100) {
    element.style.opacity = '1';
    element.style.transform = 'translateY(0)';
}
});
}
   checkScroll();
});

        document.addEventListener("DOMContentLoaded", function () {
        const navbar = document.querySelector(".navbar");

        window.addEventListener("scroll", function () {
        if (window.scrollY > 50) {
        navbar.classList.add("scrolled");
    } else {
        navbar.classList.remove("scrolled");
    }
    });
    });

    document.addEventListener('DOMContentLoaded', function() {
    const getStartedBtn = document.querySelector('.get-started-btn');

    setTimeout(() => {
    getStartedBtn.style.animation = 'pulse 1.5s infinite';
}, 1000);

    getStartedBtn.addEventListener('mouseenter', () => {
    getStartedBtn.style.animation = 'none';
});

    getStartedBtn.addEventListener('mouseleave', () => {
    setTimeout(() => {
    getStartedBtn.style.animation = 'pulse 1.5s infinite';
}, 500);
});
});
    document.addEventListener('DOMContentLoaded', function() {
    const viewAllBtn = document.getElementById('viewAllReviews');
    const reviewsContainer = document.querySelector('.reviews-container');

    viewAllBtn.addEventListener('click', function() {
    reviewsContainer.classList.toggle('show-all-reviews');

    if (reviewsContainer.classList.contains('show-all-reviews')) {
    viewAllBtn.textContent = 'Show Less Reviews';
} else {
    viewAllBtn.textContent = 'View All Reviews';
}

    if (reviewsContainer.classList.contains('show-all-reviews')) {
    document.getElementById('feedback').scrollIntoView({ behavior: 'smooth' });
}
});
});
    document.addEventListener("DOMContentLoaded", function() {
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', function() {
                navLinks.forEach(nav => nav.classList.remove('active'));
                link.classList.add('active');
            });
        });
    });