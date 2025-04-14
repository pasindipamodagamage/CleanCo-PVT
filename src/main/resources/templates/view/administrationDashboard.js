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
    $('.sidebar-link').click(function(e) {
    e.preventDefault();

    // Remove active class from all links and sections
    $('.sidebar-link').removeClass('active');
    $('.content-section').removeClass('active');

    // Add active class to clicked link
    $(this).addClass('active');

    // Show corresponding section
    $('#' + $(this).data('section')).addClass('active');
});

    // Category table row click
    $('#categoriesTable tbody').on('click', 'tr', function() {
    const id = $(this).data('id');
    const name = $(this).find('td:eq(1)').text();
    const description = $(this).find('td:eq(2)').text();
    const status = $(this).find('td:eq(3) .badge').hasClass('bg-success') ? 'active' : 'inactive';

    // Fill modal with data
    $('#categoryModalLabel').text('Edit Category');
    $('#categoryId').val(id);
    $('#categoryName').val(name);
    $('#categoryDescription').val(description);
    $(`#categoryStatus${status.charAt(0).toUpperCase() + status.slice(1)}`).prop('checked', true);

    // Show modal
    $('#categoryModal').modal('show');
});

    // Service table row click
    $('#servicesTable tbody').on('click', 'tr', function() {
    const id = $(this).data('id');
    const name = $(this).find('td:eq(1)').text();
    const category = $(this).find('td:eq(2)').text();
    const price = $(this).find('td:eq(3)').text().replace('$', '');
    const duration = $(this).find('td:eq(4)').text().replace(' hours', '').replace(' hour', '');
    const status = $(this).find('td:eq(5) .badge').hasClass('bg-success') ? 'active' : 'inactive';

    // Fill modal with data
    $('#serviceModalLabel').text('Edit Service');
    $('#serviceId').val(id);
    $('#serviceName').val(name);
    // Find category option by text
    $('#serviceCategory option').each(function() {
    if ($(this).text() === category) {
    $(this).prop('selected', true);
}
});
    $('#servicePrice').val(price);
    $('#serviceDuration').val(duration);
    $(`#serviceStatus${status.charAt(0).toUpperCase() + status.slice(1)}`).prop('checked', true);

    // Show modal
    $('#serviceModal').modal('show');
});

    // Reset modals when hidden
    $('#categoryModal').on('hidden.bs.modal', function () {
    $('#categoryModalLabel').text('Add Category');
    $('#categoryForm')[0].reset();
    $('#categoryId').val('');
});

    $('#serviceModal').on('hidden.bs.modal', function () {
    $('#serviceModalLabel').text('Add Service');
    $('#serviceForm')[0].reset();
    $('#serviceId').val('');
});

    $('#userModal').on('hidden.bs.modal', function () {
    $('#userModalLabel').text('Add User');
    $('#userForm')[0].reset();
});

    // Save category
    $('#saveCategoryBtn').click(function() {
    if (!$('#categoryForm')[0].checkValidity()) {
    $('#categoryForm')[0].reportValidity();
    return;
}

    const id = $('#categoryId').val();
    const name = $('#categoryName').val();
    const description = $('#categoryDescription').val();
    const status = $('input[name="categoryStatus"]:checked').val();

    if (id) {
    // Update existing category
    const row = $(`#categoriesTable tbody tr[data-id="${id}"]`);
    row.find('td:eq(1)').text(name);
    row.find('td:eq(2)').text(description);
    row.find('td:eq(3) .badge')
    .removeClass('bg-success bg-danger')
    .addClass(status === 'active' ? 'bg-success' : 'bg-danger')
    .text(status === 'active' ? 'Active' : 'Inactive');
} else {
    // Add new category
    const newId = 'CAT00' + ($('#categoriesTable tbody tr').length + 1);
    const newRow = `
                <tr data-id="${newId}">
                    <td>${newId}</td>
                    <td>${name}</td>
                    <td>${description}</td>
                    <td><span class="badge ${status === 'active' ? 'bg-success' : 'bg-danger'}">${status === 'active' ? 'Active' : 'Inactive'}</span></td>
                    <td>
                        <button class="btn btn-sm btn-info edit-btn" data-bs-toggle="modal" data-bs-target="#categoryModal">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger delete-btn">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
    $('#categoriesTable tbody').append(newRow);
}

    $('#categoryModal').modal('hide');
});

    // Save service
    $('#saveServiceBtn').click(function() {
    if (!$('#serviceForm')[0].checkValidity()) {
    $('#serviceForm')[0].reportValidity();
    return;
}

    const id = $('#serviceId').val();
    const name = $('#serviceName').val();
    const categoryId = $('#serviceCategory').val();
    const categoryText = $('#serviceCategory option:selected').text();
    const price = $('#servicePrice').val();
    const duration = $('#serviceDuration').val();
    const description = $('#serviceDescription').val();
    const status = $('input[name="serviceStatus"]:checked').val();

    if (id) {
    // Update existing service
    const row = $(`#servicesTable tbody tr[data-id="${id}"]`);
    row.find('td:eq(1)').text(name);
    row.find('td:eq(2)').text(categoryText);
    row.find('td:eq(3)').text('$' + price);
    row.find('td:eq(4)').text(duration + ' hours');
    row.find('td:eq(5) .badge')
    .removeClass('bg-success bg-danger')
    .addClass(status === 'active' ? 'bg-success' : 'bg-danger')
    .text(status === 'active' ? 'Active' : 'Inactive');
} else {
    // Add new service
    const newId = 'SRV00' + ($('#servicesTable tbody tr').length + 1);
    const newRow = `
                <tr data-id="${newId}">
                    <td>${newId}</td>
                    <td>${name}</td>
                    <td>${categoryText}</td>
                    <td>$${price}</td>
                    <td>${duration} hours</td>
                    <td><span class="badge ${status === 'active' ? 'bg-success' : 'bg-danger'}">${status === 'active' ? 'Active' : 'Inactive'}</span></td>
                    <td>
                        <button class="btn btn-sm btn-info edit-btn" data-bs-toggle="modal" data-bs-target="#serviceModal">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger delete-btn">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
            `;
    $('#servicesTable tbody').append(newRow);
}

    $('#serviceModal').modal('hide');
});

    // Delete category
    $(document).on('click', '#categoriesTable .delete-btn', function(e) {
    e.stopPropagation(); // Prevent row click event
    if (confirm('Are you sure you want to delete this category?')) {
    $(this).closest('tr').remove();
}
});

    // Delete service
    $(document).on('click', '#servicesTable .delete-btn', function(e) {
    e.stopPropagation(); // Prevent row click event
    if (confirm('Are you sure you want to delete this service?')) {
    $(this).closest('tr').remove();
}
});

    // View user
    $(document).on('click', '#usersTable .view-btn', function(e) {
    e.stopPropagation(); // Prevent row click event
    const row = $(this).closest('tr');
    const name = row.find('td:eq(1)').text();
    const email = row.find('td:eq(2)').text();
    const role = row.find('td:eq(3)').text();
    const status = row.find('td:eq(4) .badge').text();

    // Fill user view modal
    $('.user-detail-name').text(name);
    $('.user-detail-role').text(role);
    $('.user-detail-email').text(email);
    $('.user-detail-status')
    .removeClass('bg-success bg-danger')
    .addClass(status === 'Active' ? 'bg-success' : 'bg-danger')
    .text(status);

    // Show modal
    $('#userViewModal').modal('show');
});

    // Change user status
    $(document).on('click', '#usersTable .status-btn', function(e) {
    e.stopPropagation(); // Prevent row click event
    const row = $(this).closest('tr');
    const badge = row.find('td:eq(4) .badge');
    const isActive = badge.hasClass('bg-success');

    if (isActive) {
    if (confirm('Are you sure you want to deactivate this user?')) {
    badge.removeClass('bg-success').addClass('bg-danger').text('Inactive');
    $(this).find('i').removeClass('fa-ban').addClass('fa-check');
}
} else {
    if (confirm('Are you sure you want to activate this user?')) {
    badge.removeClass('bg-danger').addClass('bg-success').text('Active');
    $(this).find('i').removeClass('fa-check').addClass('fa-ban');
}
}
});

    // Save user
    $('#saveUserBtn').click(function() {
    if (!$('#userForm')[0].checkValidity()) {
    $('#userForm')[0].reportValidity();
    return;
}

    const firstName = $('#userFirstName').val();
    const lastName = $('#userLastName').val();
    const fullName = firstName + ' ' + lastName;
    const email = $('#userEmail').val();
    const role = $('#userRole option:selected').text();

    // Check if password and confirm password match
    const password = $('#userPassword').val();
    const confirmPassword = $('#userConfirmPassword').val();

    if (password !== confirmPassword) {
    alert('Passwords do not match!');
    return;
}

    // Add new user
    const newId = 'USR00' + ($('#usersTable tbody tr').length + 1);
    const newRow = `
            <tr data-id="${newId}">
                <td>${newId}</td>
                <td>${fullName}</td>
                <td>${email}</td>
                <td>${role}</td>
                <td><span class="badge bg-success">Active</span></td>
                <td>
                    <button class="btn btn-sm btn-info view-btn" data-bs-toggle="modal" data-bs-target="#userViewModal">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-warning status-btn">
                        <i class="fas fa-ban"></i>
                    </button>
                </td>
            </tr>
        `;
    $('#usersTable tbody').append(newRow);

    $('#userModal').modal('hide');
});

    // Handle appointment actions
    $(document).on('click', '#pendingAppointmentsTable .btn-success', function() {
    if (confirm('Are you sure you want to confirm this appointment?')) {
    const row = $(this).closest('tr').clone();
    $(this).closest('tr').remove();
    row.find('td:last-child').html(`
                <button class="btn btn-sm btn-success">Complete</button>
                <button class="btn btn-sm btn-danger">Cancel</button>
            `);
    $('#confirmedAppointmentsTable tbody').append(row);
}
});

    $(document).on('click', '#pendingAppointmentsTable .btn-danger', function() {
    if (confirm('Are you sure you want to reject this appointment?')) {
    const row = $(this).closest('tr').clone();
    $(this).closest('tr').remove();
    row.find('td:last-child').html(`
                <button class="btn btn-sm btn-info">View Details</button>
            `);
    $('#rejectedAppointmentsTable tbody').append(row);
}
});

    $(document).on('click', '#confirmedAppointmentsTable .btn-success', function() {
    if (confirm('Are you sure this appointment is completed?')) {
    const row = $(this).closest('tr').clone();
    $(this).closest('tr').remove();
    row.find('td:last-child').html(`
                <button class="btn btn-sm btn-info">View Details</button>
            `);
    $('#completedAppointmentsTable tbody').append(row);
}
});

    $(document).on('click', '#confirmedAppointmentsTable .btn-danger', function() {
    if (confirm('Are you sure you want to cancel this appointment?')) {
    const row = $(this).closest('tr').clone();
    $(this).closest('tr').remove();
    row.find('td:last-child').html(`
                <button class="btn btn-sm btn-info">View Details</button>
            `);
    $('#rejectedAppointmentsTable tbody').append(row);
}
});

    // Handle review actions
    $(document).on('click', '#reviews .btn-outline-danger', function() {
    if (confirm('Are you sure you want to hide this review?')) {
    $(this).closest('.card').fadeOut();
}
});

    $(document).on('click', '#reviews .btn-outline-primary', function() {
    const reviewerName = $(this).closest('.card-body').find('.card-title').text();
    alert('Replying to ' + reviewerName + '\'s review. This feature is under development.');
});

    // Handle mail actions
    $('.mail-list .list-group-item').click(function() {
    $('.mail-list .list-group-item').removeClass('active');
    $(this).addClass('active');
    // In a real app, you would load the email content here
});

    $('.card-footer .btn-primary').click(function() {
    alert('Reply feature is under development');
});

    $('.card-footer .btn-outline-secondary').click(function() {
    alert('Forward feature is under development');
});

    $('.card-footer .btn-outline-danger').click(function() {
    if (confirm('Are you sure you want to delete this message?')) {
    alert('Message deleted');
}
});

    // Complete booking action
    $(document).on('click', '#pendingBookingsTable .btn-success', function() {
    if (confirm('Are you sure this booking is completed?')) {
    $(this).closest('tr').find('td:eq(5) .badge')
    .removeClass('bg-warning')
    .addClass('bg-success')
    .text('Completed');
    $(this).prop('disabled', true).text('Completed');
}
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
