import Redirect from "./redirect.js";

class SignUp {
    constructor() {
        this.redirect = new Redirect();
        document.addEventListener("DOMContentLoaded", () => this.init());
    }

    init() {
        const token = localStorage.getItem("token");

        if (token) {
            this.redirect.resolveShortUrl(token);
        } else {
            const form = document.getElementById("signup-form");
            if (form) {
                form.addEventListener("submit", (e) => this.handleSignUpSubmit(e));
            }
        }
    }

    getCredentials() {
        return {
            name: document.getElementById("name")?.value || "",
            email: document.getElementById("email")?.value || "",
            password: document.getElementById("password")?.value || ""
        };
    }

    async handleSignUpSubmit(event) {
        event.preventDefault();
        const credentials = this.getCredentials();
        await this.submitSignUp(credentials);
    }

    async submitSignUp(credentials) {
        try {
            const response = await fetch("/api/auth/signup", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(credentials)
            });

            if (!response.ok) {
                throw new Error("Failed to sign up. Please check your info.");
            }

            const data = await response.json();
            localStorage.setItem("token", data.token.content);

            await this.redirect.resolveShortUrl(data.token.content);

        } catch (error) {
            alert(error.message);
        }
    }
}

new SignUp();