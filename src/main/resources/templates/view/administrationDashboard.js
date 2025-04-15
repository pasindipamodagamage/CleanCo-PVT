    $(document).ready(function() {
        // Initialize DataTables
        $('#pendingBookingsTable').DataTable();
        $('#categoriesTable').DataTable();
        $('#servicesTable').DataTable();
        $('#usersTable').DataTable();
        $('#pendingAppointmentsTable').DataTable();
        $('#confirmedAppointmentsTable').DataTable();
        $('#completedAppointmentsTable').DataTable();
        $('#rejectedAppointmentsTable').DataTable();

        // Sidebar navigation
        $('.sidebar-link').click(function (e) {
            e.preventDefault();

            // Remove active class from all links and sections
            $('.sidebar-link').removeClass('active');
            $('.content-section').removeClass('active');

            // Add active class to clicked link
            $(this).addClass('active');

            // Show corresponding section
            $('#' + $(this).data('section')).addClass('active');
        });

        // Reset modals when hidden

        $('#userModal').on('hidden.bs.modal', function () {
            $('#userModalLabel').text('Add User');
            $('#userForm')[0].reset();
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

    // category
    $(document).ready(function () {
        // Check if the user is logged in and has the correct role
        function checkUserRole() {
            const token = localStorage.getItem("authToken");

            if (!token) {
                alert("You are not logged in.");
                window.location.href = "signIn.html";  // Redirect to login page if no token
                return;
            }

            const headers = {
                "Authorization": "Bearer " + token
            };

            $.ajax({
                url: "http://localhost:8082/api/v1/user/getRole",
                type: "GET",
                headers: headers,
                success: function (response) {
                    const userRole = response.data;
                    if (userRole !== 'Employee' && userRole !== 'Administrator') {
                        alert("You do not have permission to manage categories.");
                        $('#manage-categories').hide();
                    }
                },
                error: function () {
                    alert("Failed to fetch user role.");
                }
            });

        }

        // Load categories initially
        loadCategories();

        // Save category button click
        $('#saveCategoryBtn').click(function () {
            if (!$('#categoryForm')[0].checkValidity()) {
                $('#categoryForm')[0].reportValidity();
                return;
            }

            const id = $('#categoryId').val();
            const name = $('#categoryName').val();
            const description = $('#categoryDescription').val();
            const duration = $('#categoryDuration').val();

            const token = localStorage.getItem("authToken");
            const headers = {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            };

            const categoryData = {
                id: id || null,
                name: name,
                description: description,
                duration: duration
            };

            if (id) {
                // Update category
                $.ajax({
                    url: `http://localhost:8082/api/v1/category/updateCategory/${id}`,
                    type: 'PUT',
                    headers: headers,
                    data: JSON.stringify(categoryData),
                    success: function () {
                        loadCategories(); // Reload list
                        $('#categoryModal').modal('hide');
                    },
                    error: function (err) {
                        alert("Update failed: " + err.responseText);
                    }
                });
            } else {
                // Add new category
                $.ajax({
                    url: "http://localhost:8082/api/v1/category/saveCategory",
                    type: 'POST',
                    headers: headers,
                    data: JSON.stringify(categoryData),
                    success: function () {
                        loadCategories(); // Reload list
                        $('#categoryModal').modal('hide');
                    },
                    error: function (err) {
                        alert("Add failed: " + err.responseText);
                    }
                });
            }
        });

        // Reset modal when hidden
        $('#categoryModal').on('hidden.bs.modal', function () {
            $('#categoryModalLabel').text('Add Category');
            $('#categoryForm')[0].reset();
            $('#categoryId').val('');
        });

        // Edit category button click
        $('#categoriesTable').on('click', '.edit-btn', function () {
            const row = $(this).closest('tr');
            const id = row.data('id');
            const name = row.find('td:eq(1)').text();
            const description = row.find('td:eq(2)').text();
            const duration = row.find('td:eq(3)').text();

            $('#categoryModalLabel').text('Edit Category');
            $('#categoryId').val(id);
            $('#categoryName').val(name);
            $('#categoryDescription').val(description);
            $('#categoryDuration').val(duration);

            $('#categoryModal').modal('show');
        });

        // Delete category button click
        $('#categoriesTable').on('click', '.delete-btn', function (e) {
            e.stopPropagation();
            const row = $(this).closest('tr');
            const id = row.data('id');
            if (confirm('Are you sure you want to delete this category?')) {
                const token = localStorage.getItem("authToken");
                $.ajax({
                    url: `http://localhost:8082/api/v1/category/removeCategory/${id}`,
                    type: 'DELETE',
                    headers: {
                        "Authorization": "Bearer " + token
                    },
                    success: function () {
                        loadCategories();
                    },
                    error: function () {
                        alert("Delete failed.");
                    }
                });
            }
        });

        // Function to load categories into the table
        function loadCategories() {
            const token = localStorage.getItem("authToken");
            if (!token) {
                alert("You are not logged in.");
                window.location.href = "signIn.html";  // Redirect to login page
                return;
            }

            $.ajax({
                url: "http://localhost:8082/api/v1/category/getAllCategory",
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                },
                success: function (categories) {
                    let tbody = '';
                    categories.forEach(category => {
                        tbody += `
                    <tr data-id="${category.id}">
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                        <td>${category.duration}</td>
                        <td>
                            <button class="btn btn-sm btn-info edit-btn"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-danger delete-btn"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `;
                    });
                    $('#categoriesTable tbody').html(tbody);
                },
                error: function () {
                    alert("Failed to load categories");
                }
            });
        }

        // Call the function to check user role on page load
        checkUserRole();
    });

    // service
    $(document).ready(function () {

        function checkUserRole() {
            const token = localStorage.getItem("authToken");

            if (!token) {
                alert("You are not logged in.");
                window.location.href = "signIn.html";
                return;
            }

            const headers = {
                "Authorization": "Bearer " + token
            };

            $.ajax({
                url: "http://localhost:8082/api/v1/user/getRole",
                type: "GET",
                headers: headers,
                success: function (response) {
                    const userRole = response.data;
                    if (userRole !== 'Employee' && userRole !== 'Administrator') {
                        alert("You do not have permission to manage services.");
                        $('#manage-services').hide();  // Hide management section if no permission
                    }
                },
                error: function () {
                    alert("Failed to fetch user role.");
                }
            });
        }

        function loadCategoryDropdown(selectedNames = []) {
            const token = localStorage.getItem("authToken");

            $.ajax({
                url: "http://localhost:8082/api/v1/category/getCategoryNames",
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                },
                success: function (categories) {
                    const select = $('#serviceCategory');
                    select.empty();

                    categories.forEach(category => {
                        const option = $('<option>', {
                            value: category.id,
                            text: category.name
                        });

                        if (selectedNames.includes(category.name)) {
                            option.prop('selected', true);
                        }

                        select.append(option);
                    });
                },
                error: function () {
                    alert("Failed to load categories for service.");
                }
            });
        }

        $('#serviceModal').on('show.bs.modal', function () {
            loadCategoryDropdown();
        });

        loadServices();

        $('#saveServiceBtn').click(function () {
            if (!$('#serviceForm')[0].checkValidity()) {
                $('#serviceForm')[0].reportValidity();
                return;
            }

            const token = localStorage.getItem("authToken");

            const headers = {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            };

            const categoryIds = $('#serviceCategory').val(); // <-- returns array of selected category IDs

            const serviceData = {
                id: $('#serviceId').val() || null,
                serviceName: $('#serviceName').val(),
                description: $('#serviceDescription').val(),
                unitPrice: $('#servicePrice').val(),
                categoryIdList: categoryIds
            };

            $.ajax({
                url: "http://localhost:8082/api/v1/service/saveService",
                type: "POST",
                headers: headers,
                data: JSON.stringify(serviceData),
                success: function () {
                    $('#serviceModal').modal('hide');
                    loadServices(); // Reload services after saving
                },
                error: function (err) {
                    alert("Failed to save service: " + err.responseText);
                }
            });
        });

        $('#serviceModal').on('hidden.bs.modal', function () {
            $('#serviceModalLabel').text('Add Service');
            $('#serviceForm')[0].reset();
            $('#serviceId').val('');
            $('#serviceCategory').empty();
        });

        $('#servicesTable').on('click', '.edit-btn', function () {
            const row = $(this).closest('tr');
            const id = row.data('id');
            const name = row.find('td:eq(1)').text();
            const categoryNames = row.find('td:eq(2)').text().split(', ').map(s => s.trim());
            const price = row.find('td:eq(3)').text().replace('$', '');
            const description = row.find('td:eq(4)').text();

            $('#serviceModalLabel').text('Edit Service');
            $('#serviceId').val(id);
            $('#serviceName').val(name);
            $('#servicePrice').val(price);
            $('#serviceDescription').val(description);

            // Load dropdown with selected options
            loadCategoryDropdown(categoryNames);

            $('#serviceModal').modal('show');
        });

        $('#servicesTable').on('click', '.delete-btn', function (e) {
            e.stopPropagation();
            const row = $(this).closest('tr');
            const id = row.data('id');

            if (confirm('Are you sure you want to delete this service?')) {
                const token = localStorage.getItem("authToken");

                $.ajax({
                    url: `http://localhost:8082/api/v1/service/removeService/${id}`,
                    type: 'DELETE',
                    headers: {
                        "Authorization": "Bearer " + token
                    },
                    success: function () {
                        loadServices();
                    },
                    error: function () {
                        alert("Failed to delete service.");
                    }
                });
            }
        });

        function loadServices() {
            const token = localStorage.getItem("authToken");
            if (!token) {
                alert("You are not logged in.");
                window.location.href = "signIn.html";
                return;
            }

            $.ajax({
                url: "http://localhost:8082/api/v1/service/getAllServices",
                type: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                },
                success: function (services) {
                    let tbody = '';
                    if (services && services.length > 0) {
                        services.forEach(service => {
                            tbody += `
                        <tr data-id="${service.id}">
                            <td>${service.id}</td>
                            <td>${service.serviceName}</td>
                            <td>${(service.categoryNames || []).join(', ')}</td>
                            <td>$${service.unitPrice}</td>
                            <td>${service.description}</td>
                            <td>
                                <button class="btn btn-sm btn-info edit-btn"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-sm btn-danger delete-btn"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    `;
                        });
                    } else {
                        tbody = '<tr><td colspan="6" class="text-center">No services available</td></tr>';
                    }
                    $('#servicesTable tbody').html(tbody);
                },
                error: function () {
                    alert("Failed to load services.");
                }
            });
        }


        checkUserRole();
    });

//     user update
    $(document).ready(function () {

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
                    "Authorization": "Bearer " + token
                },
                success: function (response) {
                    const userRole = response.data;
                    if (userRole !== 'Employee' && userRole !== 'Administrator') {
                        alert("You do not have permission to edit your profile.");
                        $('#my-profile').hide();
                    }
                },
                error: function () {
                    alert("Failed to fetch user role.");
                }
            });
        }

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
                    console.log("User Profile Data:", response);

                    if (response && response.data) {
                        const user = response.data;

                        // Basic fields
                        $('#firstName').val(user.name?.firstName || '');
                        $('#lastName').val(user.name?.lastName || '');
                        $('#nicNumber').val(user.nicNumber || '');
                        $('#primaryContact').val(user.primaryContact || '');
                        $('#secondaryContact').val(user.secondaryContact || '');
                        $('#email').val(user.email || '');

                        // Optional: You can add address fields here if you include them in the form

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

            const firstName = $('#firstName').val();
            const lastName = $('#lastName').val();
            const nicNumber = $('#nicNumber').val();
            const primaryContact = $('#primaryContact').val();
            const secondaryContact = $('#secondaryContact').val();
            const email = $('#email').val();

            const token = localStorage.getItem("authToken");

            const updatedUserData = {
                name: {
                    firstName: firstName,
                    lastName: lastName
                },
                nicNumber: nicNumber,
                primaryContact: primaryContact,
                secondaryContact: secondaryContact,
                email: email
            };

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
                    loadUserProfile();
                },
                error: function (err) {
                    console.error("Profile update failed:", err);
                    alert("Profile update failed: " + (err.responseJSON?.message || err.responseText));
                }
            });
        });
        checkUserRole();
        loadUserProfile();
    });