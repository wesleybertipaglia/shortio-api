class SignIn {
    constructor() {
        this.token = localStorage.getItem("token");
        this.redirectParam = this.getRedirectParameter();
        document.addEventListener("DOMContentLoaded", () => this.init());
    }

    init() {
        if (this.token && this.redirectParam) {
            this.resolveShortUrl(this.redirectParam, this.token);
        } else {
            const form = document.getElementById("signin-form");
            if (form) {
                form.addEventListener("submit", (e) => this.handleSignInSubmit(e));
            }
        }
    }

    getRedirectParameter() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('redirect');
    }

    getCredentials() {
        return {
            email: document.getElementById("email")?.value || "",
            password: document.getElementById("password")?.value || ""
        };
    }

    async handleSignInSubmit(event) {
        event.preventDefault();
        const credentials = this.getCredentials();
        await this.submitSignIn(credentials, this.redirectParam);
    }

    async submitSignIn(credentials, redirect) {
        try {
            const response = await fetch("/api/auth/signin", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(credentials)
            });

            if (!response.ok) {
                throw new Error("Invalid credentials");
            }

            const data = await response.json();
            localStorage.setItem("token", data.token.content);
            await this.resolveShortUrl(redirect, data.token.content);
        } catch (error) {
            alert(error.message);
        }
    }

    async resolveShortUrl(redirect, token) {
        if (!redirect) return;

        try {
            const response = await fetch(`/s/${redirect}`, {
                method: "GET",
                headers: { "Authorization": `Bearer ${token}` }
            });

            if (!response.ok) {
                if (response.status === 401) {
                    alert("Session expired. Please sign in again.");
                    localStorage.removeItem("token");
                    return;
                }
                else {
                    alert("Failed to resolve short URL");
                    return;
                }

            }

            const result = await response.json();

            if (result?.url) {
                alert("You will be redirected shortly...");
                window.location.href = result.url;
            } else {
                alert("Invalid response from short URL resolver");
            }
        } catch (error) {
            alert(error.message);
        }
    }
}

new SignIn();