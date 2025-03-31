// Toggle between forms
function showLoginForm() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('signupForm').style.display = 'none';
    document.getElementById('adminSignupForm').style.display = 'none';
}

function showSignupForm() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('signupForm').style.display = 'block';
    document.getElementById('adminSignupForm').style.display = 'none';
}

// Preview profile image
function previewImage(input, previewId) {
    const preview = document.getElementById(previewId);
    const file = input.files[0];

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
}

// Show success message
function showSuccessMessage(message) {
    const successMessage = document.createElement('div');
    successMessage.className = 'success-message';
    successMessage.innerHTML = `
        <div style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); 
                  background-color: #4caf50; color: white; padding: 15px 25px; 
                  border-radius: 4px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); 
                  z-index: 1000; text-align: center;">
            <i class="fas fa-check-circle" style="margin-right: 10px;"></i>
            ${message}
        </div>
    `;
    document.body.appendChild(successMessage);

    setTimeout(() => {
        document.body.removeChild(successMessage);
    }, 3000);
}

// Toggle password visibility
document.addEventListener('DOMContentLoaded', function() {
    const toggleButtons = document.querySelectorAll('.toggle-password');

    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {
            const targetId = this.getAttribute('data-target');
            const passwordInput = document.getElementById(targetId);

            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                this.classList.remove('fa-eye');
                this.classList.add('fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                this.classList.remove('fa-eye-slash');
                this.classList.add('fa-eye');
            }
        });
    });
});

// Validation for login form
document.getElementById('login').addEventListener('submit', function(e) {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    let isValid = true;

    if (!validateEmail(email)) {
        document.getElementById('loginEmailError').style.display = 'block';
        isValid = false;
    } else {
        document.getElementById('loginEmailError').style.display = 'none';
    }

    if (password.length < 6) {
        document.getElementById('loginPasswordError').style.display = 'block';
        isValid = false;
    } else {
        document.getElementById('loginPasswordError').style.display = 'none';
    }

    if (isValid) {
        showSuccessMessage('Login successful! Redirecting...');
        setTimeout(() => {
            window.location.href = "index.html";
        }, 1500);
    }
});

// Auto login on page load
window.onload = function() {
    const token = localStorage.getItem('authToken');
    if (token) {
        fetch('http://yourserverurl/validate-token', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    const userRole = data.user.role; // Assuming you return role in the response
                    if (userRole === 'customer') {
                        window.location.href = 'index.html'; // Redirect to customer home
                    } else if (userRole === 'admin' || userRole === 'employee') {
                        window.location.href = 'adminDashboard.html'; // Redirect to admin dashboard
                    }
                } else {
                    localStorage.removeItem('authToken');
                }
            })
            .catch(error => {
                console.error('Error during token validation:', error);
                localStorage.removeItem('authToken');
            });
    }
};

// Handle login form submission
document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const rememberMe = document.getElementById("rememberMe").checked;

    const loginData = {
        email: email,
        password: password
    };

    fetch('http://localhost:8082/api/v1/user/loginUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) { // Login success
                if (rememberMe) {
                    localStorage.setItem('authToken', data.data.token);
                }

                const userRole = data.data.role;
                if (userRole === 'customer') {
                    window.location.href = 'customerDashboard.html'; // Customer homepage
                } else if (userRole === 'admin' || userRole === 'employee') {
                    window.location.href = 'adminDashboard.html'; // Admin dashboard
                }
            } else {
                alert("Login failed: " + data.message);
            }
        })
        .catch(error => {
            console.error('Error during login:', error);
            alert('An error occurred. Please try again later.');
        });
});

// Helper validation functions
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function togglePasswordVisibility() {
    const passwordInput = document.getElementById('loginPassword');
    const icon = document.querySelector('.toggle-password');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// Optional: Additional validation logic can go here

// // Toggle between forms
// function showLoginForm() {
//     document.getElementById('loginForm').style.display = 'block';
//     document.getElementById('signupForm').style.display = 'none';
//     document.getElementById('adminSignupForm').style.display = 'none';
// }
//
// function showSignupForm() {
//     document.getElementById('loginForm').style.display = 'none';
//     document.getElementById('signupForm').style.display = 'block';
//     document.getElementById('adminSignupForm').style.display = 'none';
// }
//
// // // Switch between customer and admin tabs
// // function switchTab(type) {
// //     if (type === 'customer') {
// //         document.getElementById('signupForm').style.display = 'block';
// //         document.getElementById('adminSignupForm').style.display = 'none';
// //
// //         // Update tab styles
// //         const customerTabs = document.querySelectorAll('#signupForm .tab');
// //         customerTabs[0].classList.add('active');
// //         customerTabs[1].classList.remove('active');
// //
// //         const adminTabs = document.querySelectorAll('#adminSignupForm .tab');
// //         adminTabs[0].classList.remove('active');
// //         adminTabs[1].classList.add('active');
// //     } else {
// //         document.getElementById('signupForm').style.display = 'none';
// //         document.getElementById('adminSignupForm').style.display = 'block';
// //
// //         // Update tab styles
// //         const customerTabs = document.querySelectorAll('#signupForm .tab');
// //         customerTabs[0].classList.remove('active');
// //         customerTabs[1].classList.add('active');
// //
// //         const adminTabs = document.querySelectorAll('#adminSignupForm .tab');
// //         adminTabs[0].classList.add('active');
// //         adminTabs[1].classList.remove('active');
// //     }
// // }
//
// // Preview profile image
// function previewImage(input, previewId) {
//     const preview = document.getElementById(previewId);
//     const file = input.files[0];
//
//     if (file) {
//         const reader = new FileReader();
//         reader.onload = function(e) {
//             preview.src = e.target.result;
//         }
//         reader.readAsDataURL(file);
//     }
// }
//
// // Show success message
// function showSuccessMessage(message) {
//     // Create a success message element
//     const successMessage = document.createElement('div');
//     successMessage.className = 'success-message';
//     successMessage.innerHTML = `
//         <div style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%);
//                   background-color: #4caf50; color: white; padding: 15px 25px;
//                   border-radius: 4px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);
//                   z-index: 1000; text-align: center;">
//             <i class="fas fa-check-circle" style="margin-right: 10px;"></i>
//             ${message}
//         </div>
//     `;
//
//     document.body.appendChild(successMessage);
//
//     // Remove after 3 seconds
//     setTimeout(() => {
//         document.body.removeChild(successMessage);
//     }, 3000);
// }
//
// // Toggle password visibility
// document.addEventListener('DOMContentLoaded', function() {
//     const toggleButtons = document.querySelectorAll('.toggle-password');
//
//     toggleButtons.forEach(button => {
//         button.addEventListener('click', function() {
//             const targetId = this.getAttribute('data-target');
//             const passwordInput = document.getElementById(targetId);
//
//             if (passwordInput.type === 'password') {
//                 passwordInput.type = 'text';
//                 this.classList.remove('fa-eye');
//                 this.classList.add('fa-eye-slash');
//             } else {
//                 passwordInput.type = 'password';
//                 this.classList.remove('fa-eye-slash');
//                 this.classList.add('fa-eye');
//             }
//         });
//     });
// });
//
// // Validation for login form
// document.getElementById('login').addEventListener('submit', function(e) {
//     e.preventDefault();
//
//     const email = document.getElementById('loginEmail').value;
//     const password = document.getElementById('loginPassword').value;
//     let isValid = true;
//
//     // Email validation
//     if (!validateEmail(email)) {
//         document.getElementById('loginEmailError').style.display = 'block';
//         isValid = false;
//     } else {
//         document.getElementById('loginEmailError').style.display = 'none';
//     }
//
//     // Password validation
//     if (password.length < 6) {
//         document.getElementById('loginPasswordError').style.display = 'block';
//         isValid = false;
//     } else {
//         document.getElementById('loginPasswordError').style.display = 'none';
//     }
//
//     if (isValid) {
//         showSuccessMessage('Login successful! Redirecting...');
//         // Here you would normally handle the actual login process
//         setTimeout(() => {
//             // Redirect to dashboard or home page
//             // window.location.href = "dashboard.html";
//         }, 1500);
//     }
// });
//
// // Validation for customer signup form
// // document.getElementById('customerSignup').addEventListener('submit', function(e) {
// //     e.preventDefault();
// //
// //     const nic = document.getElementById('customerNic').value;
// //     const email = document.getElementById('customerEmail').value;
// //     const contact1 = document.getElementById('customerContact1').value;
// //     const contact2 = document.getElementById('customerContact2').value;
// //     const username = document.getElementById('customerUsername').value;
// //     const password = document.getElementById('customerPassword').value;
// //     const confirmPassword = document.getElementById('customerConfirmPassword').value;
// //
// //     let isValid = true;
// //
// //     // NIC validation
// //     if (!validateNIC(nic)) {
// //         document.getElementById('customerNicError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerNicError').style.display = 'none';
// //     }
// //
// //     // Email validation
// //     if (!validateEmail(email)) {
// //         document.getElementById('customerEmailError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerEmailError').style.display = 'none';
// //     }
// //
// //     // Contact validation
// //     if (!validateContact(contact1)) {
// //         document.getElementById('customerContact1Error').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerContact1Error').style.display = 'none';
// //     }
// //
// //     if (!validateContact(contact2)) {
// //         document.getElementById('customerContact2Error').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerContact2Error').style.display = 'none';
// //     }
// //
// //     // Username validation
// //     if (username.length < 3 || username.length > 20) {
// //         document.getElementById('customerUsernameError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerUsernameError').style.display = 'none';
// //     }
// //
// //     // Password validation
// //     if (password.length < 6) {
// //         document.getElementById('customerPasswordError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerPasswordError').style.display = 'none';
// //     }
// //
// //     // Confirm password validation
// //     if (password !== confirmPassword) {
// //         document.getElementById('customerConfirmPasswordError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('customerConfirmPasswordError').style.display = 'none';
// //     }
// //
// //     if (isValid) {
// //         showSuccessMessage('Customer account created successfully! Redirecting to login...');
// //         // Here you would normally handle the actual signup process
// //         setTimeout(() => {
// //             showLoginForm();
// //         }, 1500);
// //     }
// // });
// //
// // // Validation for admin signup form
// // document.getElementById('adminSignup').addEventListener('submit', function(e) {
// //     e.preventDefault();
// //
// //     const nic = document.getElementById('adminNic').value;
// //     const email = document.getElementById('adminEmail').value;
// //     const contact = document.getElementById('adminContact').value;
// //     const username = document.getElementById('adminUsername').value;
// //     const password = document.getElementById('adminPassword').value;
// //     const confirmPassword = document.getElementById('adminConfirmPassword').value;
// //
// //     let isValid = true;
// //
// //     // NIC validation
// //     if (!validateNIC(nic)) {
// //         document.getElementById('adminNicError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminNicError').style.display = 'none';
// //     }
// //
// //     // Email validation
// //     if (!validateEmail(email)) {
// //         document.getElementById('adminEmailError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminEmailError').style.display = 'none';
// //     }
// //
// //     // Contact validation
// //     if (!validateContact(contact)) {
// //         document.getElementById('adminContactError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminContactError').style.display = 'none';
// //     }
// //
// //     // Username validation
// //     if (username.length < 3 || username.length > 20) {
// //         document.getElementById('adminUsernameError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminUsernameError').style.display = 'none';
// //     }
// //
// //     // Password validation
// //     if (password.length < 6) {
// //         document.getElementById('adminPasswordError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminPasswordError').style.display = 'none';
// //     }
// //
// //     // Confirm password validation
// //     if (password !== confirmPassword) {
// //         document.getElementById('adminConfirmPasswordError').style.display = 'block';
// //         isValid = false;
// //     } else {
// //         document.getElementById('adminConfirmPasswordError').style.display = 'none';
// //     }
// //
// //     if (isValid) {
// //         showSuccessMessage('Admin account created successfully! Redirecting to login...');
// //         // Here you would normally handle the actual signup process
// //         setTimeout(() => {
// //             showLoginForm();
// //         }, 1500);
// //     }
// // });
//
// // Add input focus effects
// document.addEventListener('DOMContentLoaded', function() {
//     const formInputs = document.querySelectorAll('input, select');
//
//     formInputs.forEach(input => {
//         // Add focus effect
//         input.addEventListener('focus', function() {
//             this.parentElement.classList.add('input-focus');
//
//             // Find the icon in the parent element
//             const icon = this.parentElement.querySelector('i:not(.toggle-password)');
//             if (icon) {
//                 icon.style.color = 'var(--primary)';
//             }
//         });
//
//         // Remove focus effect
//         input.addEventListener('blur', function() {
//             this.parentElement.classList.remove('input-focus');
//
//             // Reset icon color
//             const icon = this.parentElement.querySelector('i:not(.toggle-password)');
//             if (icon) {
//                 icon.style.color = 'var(--text-light)';
//             }
//         });
//     });
// });
//
// // Helper validation functions
// // function validateEmail(email) {
// //     const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
// //     return re.test(String(email).toLowerCase());
// // }
// //
// // function validateNIC(nic) {
// //     const re = /^(\d{9}[Vv])|(\d{12})$/;
// //     return re.test(nic);
// // }
// //
// // function validateContact(contact) {
// //     const re = /^\d{10}$/;
// //     return re.test(contact);
// // }
//
// document.getElementById("loginForm").addEventListener("submit", function (event) {
//     event.preventDefault(); // Prevent form submission
//
//     // Get form data
//     const email = document.getElementById("email").value;
//     const password = document.getElementById("password").value;
//     const rememberMe = document.getElementById("rememberMe").checked;
//
//     // Prepare data for AJAX request
//     const loginData = {
//         email: email,
//         password: password
//     };
//
//     // Send AJAX request
//     fetch('http://localhost:8082/api/v1/user/loginUser', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(loginData)
//     })
//         .then(response => response.json())
//         .then(data => {
//             if (data.code === 200) { // Login success
//                 console.log("Login successful!");
//
//                 // If remember me is checked, store the token in localStorage
//                 if (rememberMe) {
//                     localStorage.setItem('authToken', data.data.token);
//                     console.log("Token saved to localStorage.");
//                 }
//
//                 // Optionally redirect to dashboard or home page
//                 window.location.href = '/dashboard'; // Adjust the URL based on your routing setup
//             } else {
//                 alert("Login failed: " + data.message);
//             }
//         })
//         .catch(error => {
//             console.error('Error during login:', error);
//             alert('An error occurred. Please try again later.');
//         });
// });
//
// // auto logging
// window.onload = function() {
//     const token = localStorage.getItem('authToken');
//     if (token) {
//         // You can validate the token with your backend or check its expiry
//         fetch('http://yourserverurl/validate-token', {
//             method: 'POST',
//             headers: {
//                 'Authorization': 'Bearer ' + token
//             }
//         })
//             .then(response => response.json())
//             .then(data => {
//                 if (data.code === 200) {
//                     // Token is valid, redirect user to dashboard or home
//                     window.location.href = '/dashboard';
//                 } else {
//                     // Token is invalid, clear from localStorage and prompt user to log in again
//                     localStorage.removeItem('authToken');
//                 }
//             })
//             .catch(error => {
//                 console.error('Error during token validation:', error);
//                 localStorage.removeItem('authToken');
//             });
//     }
// };
//
