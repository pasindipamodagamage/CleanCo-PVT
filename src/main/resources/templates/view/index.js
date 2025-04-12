    // Initialize AOS animation
    AOS.init();

    // Additional animations
    document.addEventListener('DOMContentLoaded', function() {
    // Add scroll-triggered animations
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

    // Initial check
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

    // Initial animation
    setTimeout(() => {
    getStartedBtn.style.animation = 'pulse 1.5s infinite';
}, 1000);

    // Stop animation on hover and restart on mouse leave
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
    // Toggle the show-all-reviews class on the container
    reviewsContainer.classList.toggle('show-all-reviews');

    // Change the button text based on state
    if (reviewsContainer.classList.contains('show-all-reviews')) {
    viewAllBtn.textContent = 'Show Less Reviews';
} else {
    viewAllBtn.textContent = 'View All Reviews';
}

    // Scroll to the reviews section if showing all
    if (reviewsContainer.classList.contains('show-all-reviews')) {
    document.getElementById('feedback').scrollIntoView({ behavior: 'smooth' });
}
});
});
    // Example of a simple script to toggle navbar active class on page load
    document.addEventListener("DOMContentLoaded", function() {
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', function() {
                navLinks.forEach(nav => nav.classList.remove('active'));
                link.classList.add('active');
            });
        });
    });


    {/*<script>(*/}
    {/*    function(){*/}
    {/*        emailjs.init("8SrU-EPT-B1aWBrC8");*/}
    {/*    })();*/}

    {/*    document.addEventListener('DOMContentLoaded', function () {*/}
    {/*        emailjs.init("8SrU-EPT-B1aWBrC8");*/}
    {/*    });*/}

    {/*    document.getElementById('contactForm').addEventListener('submit', function(e) {*/}
    {/*        e.preventDefault();*/}

    {/*        const submitBtn = document.getElementById('submitBtn');*/}
    {/*        const submitText = document.getElementById('submitText');*/}
    {/*        const submitSpinner = document.getElementById('submitSpinner');*/}
    {/*        const formSuccess = document.getElementById('formSuccess');*/}
    {/*        const formError = document.getElementById('formError');*/}

    {/*        // UI feedback*/}
    {/*        submitBtn.disabled = true;*/}
    {/*        submitText.textContent = 'Sending...';*/}
    {/*        submitSpinner.classList.remove('d-none');*/}

    {/*        const formData = {*/}
    {/*        name: this.name.value,*/}
    {/*        email: this.email.value,*/}
    {/*        phone: this.phone.value,*/}
    {/*        message: this.message.value*/}
    {/*    };*/}

    {/*        emailjs.send("service_l8t6g1q", "template_7u7bzpc", formData)*/}
    {/*        .then(function(response) {*/}
    {/*        formSuccess.classList.remove('d-none');*/}
    {/*        formError.classList.add('d-none');*/}
    {/*        submitText.textContent = 'Send Message';*/}
    {/*        submitSpinner.classList.add('d-none');*/}
    {/*        submitBtn.disabled = false;*/}
    {/*        document.getElementById('contactForm').reset();*/}
    {/*    }, function(error) {*/}
    {/*        formError.classList.remove('d-none');*/}
    {/*        formSuccess.classList.add('d-none');*/}
    {/*        submitText.textContent = 'Send Message';*/}
    {/*        submitSpinner.classList.add('d-none');*/}
    {/*        submitBtn.disabled = false;*/}
    {/*    });*/}
    {/*    });*/}
    {/*</script>*/}
