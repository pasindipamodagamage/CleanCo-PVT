    $(document).ready(function () {
    $('#signupForm').on('submit', function (e) {
        e.preventDefault();

        var formData = {
            name:{
                firstName: $('#firstName').val(),
                lastName: $('#lastName').val(),
            },
            email: $('#email').val(),
            password: $('#password').val(),
            primaryContact: $('#contact-1').val(),
            address: {
                locationNumber: $('#locationNumber').val(),
                street: $('#street').val(),
                city: $('#city').val(),
                district: $('#district').val()
            },
            profilePic: "/static/assets/user.png",
            nicNumber: "",
            role: "Customer",
            active: true,
            secondaryContact: ""
        };

        // console.log("Sending data:", formData);

        $.ajax({
            url: 'http://localhost:8082/api/v1/user/registerUser',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response, textStatus, xhr) {
                console.log("Response received:", response, "Status Code:", xhr.status);

                if (response.code === 201) {
                    Swal.fire({
                        icon: 'success',
                        title: 'User Registered Successfully!',
                        text: 'You have successfully created an account.',
                        confirmButtonText: 'Ok'
                    }).then(() => {
                        window.location.href = 'signIn.html';
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Registration Failed',
                        text: response.message || 'Something went wrong. Please try again.',
                        confirmButtonText: 'Ok'
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error('AJAX Error:', error);

                Swal.fire({
                    icon: 'error',
                    title: 'An Error Occurred',
                    text: 'Please try again later.',
                    confirmButtonText: 'Ok'
                });
            }
        });
    });
});