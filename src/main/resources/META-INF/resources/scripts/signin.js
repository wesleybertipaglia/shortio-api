import Redirect from "./redirect.js";

class SignIn {
    constructor() {
        this.redirect = new Redirect();
        document.addEventListener("DOMContentLoaded", () => this.init());
    }

    init() {
        const token = localStorage.getItem("token");

        if (token) {
            this.redirect.resolveShortUrl(token);
        } else {
            const form = document.getElementById("signin-form");
            if (form) {
                form.addEventListener("submit", (e) => this.handleSignInSubmit(e));
            }
        }
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
        await this.submitSignIn(credentials);
    }

    async submitSignIn(credentials) {
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

            await this.redirect.resolveShortUrl(data.token.content);

        } catch (error) {
            alert(error.message);
        }
    }
}

new SignIn();