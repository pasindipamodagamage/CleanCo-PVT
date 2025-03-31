    document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('signupForm');
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');

    // Error message elements
    const firstNameError = document.getElementById('firstNameError');
    const lastNameError = document.getElementById('lastNameError');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');

    // Validation functions
    function showError(errorElement, show = true) {
    errorElement.style.display = show ? 'block' : 'none';
}

    function validateFirstName() {
    const isValid = firstName.value.trim() !== '';
    showError(firstNameError, !isValid);
    return isValid;
}

    function validateLastName() {
    const isValid = lastName.value.trim() !== '';
    showError(lastNameError, !isValid);
    return isValid;
}

    function validateEmail() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isValid = emailRegex.test(email.value);
    showError(emailError, !isValid);
    return isValid;
}

    function validatePassword() {
    const isValid = password.value.length >= 8;
    showError(passwordError, !isValid);
    return isValid;
}

    function validateConfirmPassword() {
    const isValid = password.value === confirmPassword.value;
    showError(confirmPasswordError, !isValid);
    return isValid;
}

    // Add event listeners for real-time validation
    firstName.addEventListener('input', validateFirstName);
    lastName.addEventListener('input', validateLastName);
    email.addEventListener('input', validateEmail);
    password.addEventListener('input', validatePassword);
    confirmPassword.addEventListener('input', validateConfirmPassword);

    // Form submission handler
    form.addEventListener('submit', function(e) {
    e.preventDefault();

    // Validate all fields
    const isFirstNameValid = validateFirstName();
    const isLastNameValid = validateLastName();
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    const isConfirmPasswordValid = validateConfirmPassword();

    // If all validations pass, submit the form
    if (isFirstNameValid && isLastNameValid && isEmailValid &&
    isPasswordValid && isConfirmPasswordValid) {
    // Here you would typically send the form data to your backend
    alert('Form submitted successfully!');
    // Example:
    // const formData = new FormData(form);
    // fetch('/signup', { method: 'POST', body: formData })
    //     .then(response => response.json())
    //     .then(data => {
    //         // Handle successful signup
    //     })
    //     .catch(error => {
    //         // Handle signup error
    //     });
}
});

    // Password visibility toggle
    function togglePasswordVisibility(inputId) {
    const passwordInput = document.getElementById(inputId);
    const icon = passwordInput.nextElementSibling;

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

    // Expose toggle function globally
    window.togglePasswordVisibility = togglePasswordVisibility;
});