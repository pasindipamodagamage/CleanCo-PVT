document.addEventListener('DOMContentLoaded', function () {
    const sections = {
        dashboardLink: 'dashboardContent',
        bookingsLink: 'bookingsContent',
        newBookingLink: 'newBookingContent',
        paymentsLink: 'paymentsContent',
        feedbackLink: 'feedbackContent',
        updateProfileBtn: 'updateProfileContent'
    };

    function showSection(sectionId) {
        document.querySelectorAll('.main-content').forEach(section => {
            section.classList.add('d-none');
        });
        document.getElementById(sectionId)?.classList.remove('d-none');
    }

    Object.entries(sections).forEach(([linkId, sectionId]) => {
        document.getElementById(linkId)?.addEventListener('click', function (e) {
            e.preventDefault();
            showSection(sectionId);

            document.querySelectorAll('.sidebar-link').forEach(link => {
                link.classList.remove('active');
            });
            if (linkId !== 'updateProfileBtn') {
                this.classList.add('active');
            }
        });
    });

    showSection('dashboardContent');
    document.getElementById('dashboardLink')?.classList.add('active');

    document.getElementById('newBookingBtn')?.addEventListener('click', function (e) {
        e.preventDefault();
        showSection('newBookingContent');
    });

    document.getElementById('cancelBookingBtn')?.addEventListener('click', function () {
        showSection('bookingsContent');
    });

    document.getElementById('cancelProfileUpdateBtn')?.addEventListener('click', function () {
        showSection('dashboardContent');
    });

    document.querySelectorAll('.cancel-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const row = this.closest('tr');
            const statusCell = row.querySelector('td:nth-child(3)');
            statusCell.innerHTML = '<span class="badge bg-danger">Cancelled</span>';
            alert('Booking has been cancelled.');
        });
    });

    document.querySelectorAll('.pay-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            showSection('paymentsContent');
        });
    });

    document.querySelectorAll('.feedback-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            showSection('feedbackContent');
        });
    });

    const stars = document.querySelectorAll('.rating-stars i');
    stars.forEach(star => {
        star.addEventListener('click', function () {
            const rating = parseInt(this.getAttribute('data-rating'));
            const ratingInput = document.getElementById('rating');
            if (ratingInput) ratingInput.value = rating;

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

    const bookingDateInput = document.getElementById('bookingDate');
    if (bookingDateInput) {
        bookingDateInput.min = new Date().toISOString().split('T')[0];
    }

    document.querySelectorAll('.feedback-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            showSection('feedbackContent');
        });
    });


    document.getElementById('feedbackForm')?.addEventListener('submit', function (e) {
        e.preventDefault();
        alert('Thank you for your feedback!');
        this.reset();
        stars.forEach(star => {
            star.classList.remove('fas', 'active');
            star.classList.add('far');
        });
    });

    document.getElementById('profileForm')?.addEventListener('submit', function (e) {
        e.preventDefault();
        alert('Profile updated successfully!');
        showSection('dashboardContent');
    });

    document.getElementById('creditCardForm')?.addEventListener('submit', function (e) {
        e.preventDefault();
        alert('Payment method added successfully!');
        this.reset();
    });

    // Logout
    document.getElementById("logoutBtn")?.addEventListener("click", function () {
        fetch('http://localhost:8082/api/v1/user/logout', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                localStorage.removeItem('authToken');
                sessionStorage.removeItem('authToken');
                localStorage.removeItem('userId');
                sessionStorage.removeItem('userId');
                window.location.href = 'index.html';
            })
            .catch(err => {
                console.error('Logout error:', err);
            });
    });

});

function checkUserRole() {
    const token = localStorage.getItem("authToken");
    if (!token) {
        alert("You are not logged in.");
        window.location.href = "signIn.html";
        return;
    }

    $.ajax({
        url: "http://localhost:8082/api/v1/user/getRole",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        success: function (response) {
            const userRole = response.data;
            if (!['Employee', 'Administrator', 'Customer'].includes(userRole)) {
                alert("You do not have permission to edit your profile.");
                $('#my-profile').hide();
            }
        },
        error: function () {
            alert("Failed to fetch user role.");
        }
    });
}

// updateUser
$(document).ready(function () {
    let currentUserData = {};

    function loadUserProfile() {
        const token = localStorage.getItem("authToken");
        if (!token) {
            alert("You are not logged in.");
            window.location.href = "signIn.html";
            return;
        }

        $.ajax({
            url: "http://localhost:8082/api/v1/user/me",
            type: "GET",
            headers: {
                "Authorization": "Bearer " + token
            },
            success: function (response) {
                if (response && response.data) {
                    currentUserData = response.data;

                    // Set input fields
                    $('#firstName').val(currentUserData.name?.firstName || '');
                    $('#lastName').val(currentUserData.name?.lastName || '');
                    $('#nicNumber').val(currentUserData.nicNumber || '');
                    $('#primaryContact').val(currentUserData.primaryContact || '');
                    $('#secondaryContact').val(currentUserData.secondaryContact || '');
                    $('#email').val(currentUserData.email || '');

                    $('#locationNumber').val(currentUserData.address?.locationNumber || '');
                    $('#street').val(currentUserData.address?.street || '');
                    $('#city').val(currentUserData.address?.city || '');
                    $('#district').val(currentUserData.address?.district || '');

                    // Set avatar
                    const profilePicUrl = currentUserData.profilePic || '/static/assets/user.png';
                    $('#user-avatar').attr('src', profilePicUrl);
                    $('#profilePicPreview').attr('src', profilePicUrl);

                    // Set user name and role
                    $('#user-name').text(`${currentUserData.name?.firstName || ''} ${currentUserData.name?.lastName || ''}`);
                    $('#user-role').text(currentUserData.role || 'User');
                } else {
                    console.error("Invalid response data format");
                    alert("Failed to load profile data.");
                }
            },
            error: function (err) {
                console.error("Error fetching profile data:", err);
                alert("Failed to load profile data.");
            }
        });
    }

    $('#updateProfileForm').submit(function (e) {
        e.preventDefault();

        const token = localStorage.getItem("authToken");
        if (!token) {
            alert("Session expired. Please log in again.");
            return;
        }

        const updatedUserData = {
            name: {
                firstName: $('#firstName').val().trim() || currentUserData.name?.firstName,
                lastName: $('#lastName').val().trim() || currentUserData.name?.lastName
            },
            nicNumber: $('#nicNumber').val().trim() || currentUserData.nicNumber,
            primaryContact: $('#primaryContact').val().trim() || currentUserData.primaryContact,
            secondaryContact: $('#secondaryContact').val().trim() || currentUserData.secondaryContact,
            email: $('#email').val().trim() || currentUserData.email,
            address: {
                locationNumber: $('#locationNumber').val().trim() || currentUserData.address?.locationNumber,
                street: $('#street').val().trim() || currentUserData.address?.street,
                city: $('#city').val().trim() || currentUserData.address?.city,
                district: $('#district').val().trim() || currentUserData.address?.district
            }
        };

        const newPassword = $('#password').val().trim();
        const confirmPassword = $('#confirmPassword').val().trim();

        if (newPassword) {
            if (newPassword === confirmPassword) {
                updatedUserData.password = newPassword;
            } else {
                alert("New passwords do not match.");
                return;
            }
        }

        $.ajax({
            url: "http://localhost:8082/api/v1/user/updateProfile",
            type: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            data: JSON.stringify(updatedUserData),
            success: function () {
                alert("Profile updated successfully.");
                $('#password').val('');
                $('#confirmPassword').val('');
                $('#currentPassword').val('');
                loadUserProfile();
            },
            error: function (err) {
                console.error("Profile update failed:", err);
                const errorMessage = err.responseJSON?.message || err.responseText || "Unknown error.";
                alert("Profile update failed: " + errorMessage);
            }
        });
    });

    // Load profile and validate role
    if (typeof checkUserRole === "function") {
        checkUserRole();
    }
    loadUserProfile();
});

$(document).ready(function () {

    function loadServiceCartDetails() {
        const token = localStorage.getItem("authToken");

        $.ajax({
            url: 'http://localhost:8082/api/v1/service/serviceCartDetail',
            type: 'GET',
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            success: function (response) {
                console.log("Received data:", response.data);
                if (response.code === 200 && response.data) {
                    renderCartItems(response.data);
                    $('#cartServiceList').removeAttr('hidden');
                } else {
                    $('#cartServiceList').html('<p class="text-danger">No services available at the moment.</p>');
                    $('#cartServiceList').removeAttr('hidden');
                }
            },
            error: function (xhr) {
                console.error("Failed to load service cart details:", xhr);
                $('#cartServiceList').html('<p class="text-danger">Failed to load services.</p>');
                $('#cartServiceList').removeAttr('hidden'); // Ensure it is shown even if there's an error
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
                            <h5 class="card-title fw-bold" style="font-size: 2rem; margin-right: 2px">${item.categoryName} /<span style="font-size: 0.8rem">${item.duration}</span> </h5>
                            <div class="text-muted mb-2" hidden="hidden">Category ID: ${item.id}</div>
                            <div class="price text-success">Rs. ${item.total.toFixed(2)}</div>
                            <ul class="list-unstyled mt-4">
                                ${servicesHtml}
                            </ul>
                            <a href="#" class="btn btn-outline-primary w-100 mt-3 buy-plan-btn" data-category-id="${item.id}" data-category-name="${item.categoryName}">Buy Plan</a>
                        </div>
                    </div>
                </div>`;
        });

        $('#cartServiceList').html(html);
    }

    let selectedCategoryId = null;

    $(document).on('click', '.buy-plan-btn', function () {
        const categoryId = $(this).data('category-id');
        const categoryName = $(this).data('category-name');
        $('#bookingCategoryId').val(categoryId);
        $('#bookingModalLabel').text(`Book: ${categoryName}`);
        $('#bookingModal').modal('show');
    });

    $('#bookingForm').on('submit', function (e) {
        e.preventDefault();

        const token = localStorage.getItem("authToken");
        const categoryId = $('#bookingCategoryId').val();
        const bookingDate = $('#bookingDate').val();

        if (!categoryId || !bookingDate) {
            alert("Please fill in all fields.");
            return;
        }

        const userId = localStorage.getItem("userId");
        const currentTime = new Date().toISOString().slice(11, 19);

        const bookingPayload = {
            userId: userId,
            categoryId: categoryId,
            bookingDate: bookingDate,
            bookingTime: currentTime,
            bookingStatus: "PENDING"
        };


        $.ajax({
            method: 'POST',
            url: 'http://localhost:8082/api/v1/booking/addBooking',
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            data: JSON.stringify(bookingPayload),
            success: function (res) {
                alert(res.message);
                $('#bookingModal').modal('hide');
                $('#bookingForm')[0].reset();
            },
            error: function (err) {
                console.error(err);
                alert("Booking failed.");
            }
        });
    });

    checkUserRole();
    loadServiceCartDetails();
});

$(document).ready(function () {
    const userId = localStorage.getItem("userId"); // Get userId from localStorage

    $.ajax({
        url: `http://localhost:8082/api/v1/booking/getBookingsForUser?userId=${userId}`, // Pass userId as query parameter
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem("authToken")
        },
        success: function (data) {
            const tbody = $('#recentBookingsBody');
            tbody.empty();

            data.forEach(booking => {
                const statusBadge = getStatusBadge(booking.bookingStatus);
                const row = `
                    <tr>
                        <td>${booking.bookingDate}</td>
                        <td>${booking.categoryName}</td>
                        <td>${statusBadge}</td>
                        <td>
                            <button class="btn btn-sm btn-outline-primary me-1 feedback-btn">Feedback</button>
                            <button class="btn btn-sm btn-outline-success me-1 pay-btn">Pay</button>
                            <button class="btn btn-sm btn-outline-danger cancel-btn">Cancel</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function () {
            alert("Failed to load bookings");
        }
    });

    function getStatusBadge(status) {
        let className = "bg-secondary";
        if (status === "COMPLETED") className = "bg-success";
        else if (status === "PENDING") className = "bg-warning";
        else if (status === "CANCELLED" || status === "REJECTED") className = "bg-danger";
        else if (status === "CONFIRMED") className = "bg-primary";

        return `<span class="badge ${className}">${status.charAt(0) + status.slice(1).toLowerCase()}</span>`;
    }
});

// $(document).ready(function () {
//     $.ajax({
//         url: 'http://localhost:8082/api/v1/booking/getBookingTableData',
//         method: 'GET',
//         headers: {
//             'Authorization': 'Bearer ' + localStorage.getItem("authToken")
//         },
//         success: function (data) {
//             const tbody = $('#recentBookingsBody');
//             tbody.empty();
//
//             data.forEach(booking => {
//                 const statusBadge = getStatusBadge(booking.bookingStatus);
//                 const row = `
//                         <tr>
//                             <td>${booking.bookingDate}</td>
//                             <td>${booking.categoryName}</td>
//                             <td>${statusBadge}</td>
//                             <td>
//                                 <button class="btn btn-sm btn-outline-primary me-1 feedback-btn">Feedback</button>
//                                 <button class="btn btn-sm btn-outline-success me-1 pay-btn">Pay</button>
//                                 <button class="btn btn-sm btn-outline-danger cancel-btn">Cancel</button>
//                             </td>
//                         </tr>
//                     `;
//                 tbody.append(row);
//             });
//         },
//         error: function () {
//             alert("Failed to load bookings");
//         }
//     });
//
//     function getStatusBadge(status) {
//         let className = "bg-secondary";
//         if (status === "COMPLETED") className = "bg-success";
//         else if (status === "PENDING") className = "bg-warning";
//         else if (status === "CANCELLED" || status === "REJECTED") className = "bg-danger";
//         else if (status === "CONFIRMED") className = "bg-primary";
//
//         return `<span class="badge ${className}">${status.charAt(0) + status.slice(1).toLowerCase()}</span>`;
//     }
// });