document.addEventListener('DOMContentLoaded', function() {
    // Navigation between sections
    const sections = {
        dashboardLink: 'dashboardContent',
        bookingsLink: 'bookingsContent',
        newBookingLink: 'newBookingContent',
        paymentsLink: 'paymentsContent',
        feedbackLink: 'feedbackContent',
        updateProfileBtn: 'updateProfileContent'
    };

    // Hide all sections and show the selected one
    function showSection(sectionId) {
        document.querySelectorAll('.main-content').forEach(section => {
            section.classList.add('d-none');
        });
        document.getElementById(sectionId).classList.remove('d-none');
    }

    // Add click event listeners to navigation links
    Object.entries(sections).forEach(([linkId, sectionId]) => {
        document.getElementById(linkId)?.addEventListener('click', function(e) {
            e.preventDefault();
            showSection(sectionId);

            // Update active state in sidebar
            document.querySelectorAll('.sidebar-link').forEach(link => {
                link.classList.remove('active');
            });
            if (linkId !== 'updateProfileBtn') {
                this.classList.add('active');
            }
        });
    });

    // Set default section
    showSection('dashboardContent');
    document.getElementById('dashboardLink').classList.add('active');

    // New Booking button in bookings section
    document.getElementById('newBookingBtn')?.addEventListener('click', function(e) {
        e.preventDefault();
        showSection('newBookingContent');
    });

    // Cancel Booking button
    document.getElementById('cancelBookingBtn')?.addEventListener('click', function() {
        showSection('bookingsContent');
    });

    // Cancel Profile Update button
    document.getElementById('cancelProfileUpdateBtn')?.addEventListener('click', function() {
        showSection('dashboardContent');
    });

    // Star rating functionality
    const stars = document.querySelectorAll('.rating-stars i');
    stars.forEach(star => {
        star.addEventListener('click', function() {
            const rating = parseInt(this.getAttribute('data-rating'));
            document.getElementById('rating').value = rating;

            stars.forEach((s, index) => {
                if (index < rating) {
                    s.classList.remove('far');
                    s.classList.add('fas', 'active');
                } else {
                    s.classList.remove('fas', 'active');
                    s.classList.add('far');
                }
            });
        });
    });

    // Set minimum date for booking to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('bookingDate').min = today;

    // Form submissions (would be connected to backend in real implementation)
    document.getElementById('bookingForm')?.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Booking submitted! Redirecting to payment...');
        // In a real app, would process payment here
    });

    document.getElementById('feedbackForm')?.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Thank you for your feedback!');
        this.reset();
        stars.forEach(star => {
            star.classList.remove('fas', 'active');
            star.classList.add('far');
        });
    });

    document.getElementById('profileForm')?.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Profile updated successfully!');
        showSection('dashboardContent');
    });

    document.getElementById('creditCardForm')?.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Payment method added successfully!');
        this.reset();
    });
});

// logout
document.getElementById("logoutBtn").addEventListener("click", function () {
    fetch('http://localhost:8082/api/v1/user/logout', {
        method: 'POST',
        credentials: 'include', // This sends cookies/session
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            localStorage.removeItem('authToken');
            sessionStorage.removeItem('authToken');
            window.location.href = 'index.html';
        })
        .catch(err => {
            console.error('Logout error:', err);
        });
});

// login data
document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("authToken"); // Or sessionStorage.getItem

    if (token) {
        fetch("http://localhost:8082/api/v1/user/me", {
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(response => {
                if (!response.ok) throw new Error("Failed to fetch user data");
                return response.json();
            })
            .then(data => {
                const user = data.data;

                // Set avatar
                const avatar = document.getElementById("user-avatar");
                avatar.src = user.profilePic ? user.profilePic : "static/assets/user.jpeg";

                // Set full name
                const name = document.getElementById("user-name");
                const fullName = user.name.firstName + " " + user.name.lastName;
                name.textContent = fullName;

                // Set role
                const role = document.getElementById("user-role");
                role.textContent = user.role.charAt(0).toUpperCase() + user.role.slice(1).toLowerCase();
            })
            .catch(error => {
                console.error("Error loading user profile:", error);
            });
    }
});


