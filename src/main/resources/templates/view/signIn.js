document.addEventListener("DOMContentLoaded", function() {
    const brandText = document.querySelector(".brand-name");
    const text = "Let's in...";
    let index = 0;

    function typeEffect() {
        if (index < text.length) {
            brandText.innerHTML += text.charAt(index);
            index++;
            setTimeout(typeEffect, 150);
        } else {
            brandText.classList.add("animate__animated", "animate__fadeIn");
        }
    }

    brandText.innerHTML = "";
    typeEffect();
});

$(document).ready(function () {
    $('#loginForm').on('submit', function (event) {
        event.preventDefault();

        const formData = {
            email: $('#email').val(),
            password: $('#password').val()
        };

        const rememberMe = $('#rememberPassword').is(':checked');

        $.ajax({
            url: 'http://localhost:8082/api/v1/auth/authenticate',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(formData),
            success: function (response) {
                if ((response.code === 200 || response.code === 201) && response.data) {
                    const token = response.data.token;
                    const isActive = response.data.active;
                    const userId = response.data.id;  // This is where we extract the id

                    if (isActive === 1 || isActive === true) {
                        if (rememberMe) {
                            localStorage.setItem('authToken', token);
                            localStorage.setItem('userId', userId);  // Now we are using userId correctly
                        } else {
                            sessionStorage.setItem('authToken', token);
                            sessionStorage.setItem('userId', userId);  // Now we are using userId correctly
                        }

                        const decodedToken = jwt_decode(token);
                        const userRole = decodedToken.role;

                        Swal.fire({
                            icon: 'success',
                            title: 'Login Successful',
                            text: 'You have successfully logged in.',
                            confirmButtonText: 'Ok'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                redirectBasedOnRole(userRole);
                            }
                        });
                    } else {
                        showError('Account Inactive', 'Your account is currently inactive. Please contact support.', 'warning');
                    }
                } else {
                    showError('Login Failed', response.message || 'Invalid credentials. Please try again.', 'error');
                }
            },
            error: function (xhr, status, error) {
                let errorMessage = 'An unknown error occurred.';
                if (xhr.status === 0) {
                    errorMessage = 'Unable to connect to the server. Please check your network.';
                } else if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage = xhr.responseJSON.message;
                } else if (xhr.status === 401) {
                    errorMessage = 'Invalid credentials. Please try again.';
                }
                showError('Login Error', errorMessage, 'error');
            }
        });
    });

    checkExistingToken();

    document.querySelector(".btn-google").addEventListener("click", function() {
        // Implement Google Sign In logic here
        alert("Google Sign In will be implemented here");
    });

    function redirectBasedOnRole(role) {
        if (role === 'Employee' || role === 'Administrator') {
            window.location.href = 'administrationDashboard.html';
        } else if (role === 'Customer') {
            window.location.href = 'customerDashboard.html';
        } else {
            window.location.href = 'index.html';
        }
    }

    function showError(title, text, icon) {
        Swal.fire({
            icon: icon,
            title: title,
            text: text,
            confirmButtonText: 'Ok'
        });
    }

    function checkExistingToken() {
        // Check both localStorage and sessionStorage for token
        const token = localStorage.getItem('authToken') || sessionStorage.getItem('authToken');

        if (token) {
            try {
                const decodedToken = jwt_decode(token);
                redirectBasedOnRole(decodedToken.role);
            } catch (e) {
                // If token is invalid, clear it
                localStorage.removeItem('authToken');
                sessionStorage.removeItem('authToken');
            }
        }
    }
});