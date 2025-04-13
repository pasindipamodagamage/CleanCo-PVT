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

document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    alert("Login successful!");
});

document.querySelector(".btn-google").addEventListener("click", function() {
    alert("Continuing with Google...");
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

                    if (isActive === 1 || isActive === true) {
                        if (rememberMe) {
                            localStorage.setItem('authToken', token);
                        } else {
                            localStorage.removeItem('authToken');
                            sessionStorage.setItem('authToken', token);
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
                                if (userRole === 'Employee' || userRole === 'Administrator') {
                                    window.location.href = 'administrationDashboard.html';
                                } else if (userRole === 'Customer') {
                                    window.location.href = 'customerDashboard.html';
                                } else {
                                    window.location.href = 'index.html';
                                }
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Account Inactive',
                            text: 'Your account is currently inactive. Please contact support.',
                            confirmButtonText: 'Ok'
                        });
                    }
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Login Failed',
                        text: response.message || 'Invalid credentials. Please try again.',
                        confirmButtonText: 'Ok'
                    });
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

                Swal.fire({
                    icon: 'error',
                    title: 'Login Error',
                    text: errorMessage,
                    confirmButtonText: 'Ok'
                });
            }
        });
    });

    checkExistingToken();

    function checkExistingToken() {
        const token = localStorage.getItem('authToken') || sessionStorage.getItem('authToken');
        if (token) {
            try {
                const decodedToken = jwt_decode(token);
                const userRole = decodedToken.role;

                if (userRole === 'Employee' || userRole === 'Administrator') {
                    window.location.href = 'administrationDashboard.html';
                } else if (userRole === 'Customer') {
                    window.location.href = 'customerDashboard.html';
                }
            } catch (e) {
                localStorage.removeItem('authToken');
                sessionStorage.removeItem('authToken');
            }
        }
    }
});