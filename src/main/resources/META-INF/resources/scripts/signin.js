document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const redirect = getRedirectParameter();

    if (token && redirect) {
        resolveShortUrl(redirect, token);
    } else {
        const signinForm = document.getElementById("signin-form");
        if (signinForm) {
            signinForm.addEventListener("submit", handleSignInSubmit);
        }
    }
});

async function handleSignInSubmit(e) {
    e.preventDefault();
    const redirect = getRedirectParameter();
    const credentials = getCredentials();
    await submitSignIn(credentials, redirect);
}

function getRedirectParameter() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('redirect');
}

function getCredentials() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    return {
        email: emailInput ? emailInput.value : "",
        password: passwordInput ? passwordInput.value : ""
    };
}

async function submitSignIn(credentials, redirect) {
    const response = await fetch("/api/auth/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credentials)
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token.content);
        await resolveShortUrl(redirect, data.token.content);
    } else {
        alert("Invalid credentials");
    }
}

async function resolveShortUrl(redirect, token) {
    if (redirect) {
        const slugResponse = await fetch(`/s/${redirect}`, {
            method: "GET",
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (slugResponse.ok) {
            const result = await slugResponse.json();
            if (result && result.url) {
                window.alert("You will be redirected shortly...");
                window.location.href = result.url;
            } else {
                alert("Invalid response from short URL resolver");
            }
        } else {
            alert("Unable to resolve short URL");
        }
    }
}