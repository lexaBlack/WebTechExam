function handleImageUpload(event) {
    const input = event.target;
    const preview = document.getElementById('itemImagePreview');
    const placeholder = document.getElementById('imagePlaceholder');

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
            placeholder.style.display = 'none';
        };

        reader.readAsDataURL(input.files[0]);
    } else {
        // If no file is selected, show the placeholder icon
        preview.style.display = 'none';
        placeholder.style.display = 'block';
    }
}

function validateForm() {
    var errorMessage = document.getElementById('error-message');
    var phoneInput = document.querySelector('input[type="tel"]');
    var phonePattern = /^\d{10}$/;

    // Reset error message
    errorMessage.textContent = '';

    if (!phonePattern.test(phoneInput.value)) {
        errorMessage.textContent = "Please enter a valid 10-digit phone number.";
        return false;
    }

    // Add additional validation logic as needed

    // Simulate form submission success
    // Replace this with your actual form submission logic
    simulateFormSubmission();

    return false; // Prevents the form from actually submitting
}

function simulateFormSubmission() {
    var errorMessage = document.getElementById('error-message');
    errorMessage.textContent = "Form submitted successfully!";
}
