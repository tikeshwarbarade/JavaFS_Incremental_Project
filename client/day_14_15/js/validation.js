function login() {
    const usernameElement = document.getElementById("loginUsername");
    const passwordElement = document.getElementById("loginPassword");

    const username = usernameElement ? usernameElement.value.trim() : "";
    const password = passwordElement ? passwordElement.value.trim() : "";

    // Validate login username
    if (username === "") {
        alert("Username should not be empty");
        return;
    }

    // Validate login password
    if (password === "") {
        alert("Password should not be empty");
        return;
    }

    // Exact console log format expected by test
    console.log(`Login clicked. Username: ${username}, Password: ${password}`);
}

function register() {
    const nameElement = document.getElementById("registerName");
    const emailElement = document.getElementById("registerEmail");
    const usernameElement = document.getElementById("registerUsername");
    const passwordElement = document.getElementById("registerPassword");

    const name = nameElement ? nameElement.value.trim() : "";
    const email = emailElement ? emailElement.value.trim() : "";
    const username = usernameElement ? usernameElement.value.trim() : "";
    const password = passwordElement ? passwordElement.value.trim() : "";

    // Every field is required
    if (name === "" || email === "" || username === "" || password === "") {
        alert("All fields are required");
        return;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Please enter a valid email address");
        return;
    }

    // Validate username (no special characters)
    const usernameRegex = /^[a-zA-Z0-9]+$/;
    if (!usernameRegex.test(username)) {
        alert("Username should not contain special characters");
        return;
    }

    // Validate password
    // At least 8 characters, one capital letter, and one numeric
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordRegex.test(password)) {
        alert("Password must be at least 8 characters long and contain at least one capital letter and one number");
        return;
    }

    // Exact console log format expected by test
    console.log(`Register clicked. Name: ${name}, Email: ${email}, Username: ${username}, Password: ${password}`);
}

module.exports = { login, register };
