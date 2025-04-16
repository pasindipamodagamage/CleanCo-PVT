     document.addEventListener('DOMContentLoaded', function () {
    AOS.init();

    // Animate elements on scroll
    const animateElements = document.querySelectorAll('.service-card, .pricing-card');
    window.addEventListener('scroll', checkScroll);
    checkScroll();

    function checkScroll() {
    animateElements.forEach(element => {
    const elementTop = element.getBoundingClientRect().top;
    if (elementTop < window.innerHeight - 100) {
    element.style.opacity = '1';
    element.style.transform = 'translateY(0)';
}
});
}

    // Navbar scroll effect
    const navbar = document.querySelector(".navbar");
    window.addEventListener("scroll", () => {
    navbar.classList.toggle("scrolled", window.scrollY > 50);
});

    // Pulse animation for "Get Started" button
    const getStartedBtn = document.querySelector('.get-started-btn');
    if (getStartedBtn) {
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
}

    // Review toggle
    const viewAllBtn = document.getElementById('viewAllReviews');
    const reviewsContainer = document.querySelector('.reviews-container');
    if (viewAllBtn && reviewsContainer) {
    viewAllBtn.addEventListener('click', function () {
    reviewsContainer.classList.toggle('show-all-reviews');
    viewAllBtn.textContent = reviewsContainer.classList.contains('show-all-reviews')
    ? 'Show Less Reviews'
    : 'View All Reviews';

    if (reviewsContainer.classList.contains('show-all-reviews')) {
    document.getElementById('feedback').scrollIntoView({ behavior: 'smooth' });
}
});
}

    // Active nav-link styling
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
    link.addEventListener('click', function () {
    navLinks.forEach(nav => nav.classList.remove('active'));
    link.classList.add('active');
});
});

    // Load service cart details from backend
    loadServiceCartDetails();
});

    function loadServiceCartDetails() {
    $.ajax({
        url: 'http://localhost:8082/api/v1/service/serviceCartDetail',
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
            console.log("Received data:", response.data);
            if (response.code === 200 && response.data) {
                renderCartItems(response.data);
            } else {
                $('#cartServiceList').html('<p class="text-danger">No services available at the moment.</p>');
            }
        },
        error: function (xhr) {
            console.error("Failed to load service cart details:", xhr);
            $('#cartServiceList').html('<p class="text-danger">Failed to load services.</p>');
        }
    });
}

    function renderCartItems(data) {
    let html = '';
    data.forEach(item => {
    let servicesHtml = item.serviceNames.map(service =>
    `<li class="mb-2"><i class="fas fa-check check-icon"></i> ${service}</li>`
    ).join('');

    html += `
                <div class="col-md-4 mb-4" data-aos="fade-up">
                    <div class="card pricing-card h-100 shadow rounded-4">
                        <div class="card-body p-4">
                            <div class="text-muted mb-2 fw-semibold">${item.duration}</div>
                            <h5 class="card-title fw-bold">${item.categoryName}</h5>
                            <div class="price text-success">Rs. ${item.total.toFixed(2)}</div>
                            <ul class="list-unstyled mt-4">
                                ${servicesHtml}
                            </ul>
                            <a href="signIn.html" class="btn btn-outline-primary w-100 mt-3">Buy Plan</a>
                        </div>
                    </div>
                </div>`;
});

    $('#cartServiceList').html(html);
}
