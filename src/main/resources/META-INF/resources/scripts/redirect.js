class Redirect {
    getRedirectParameter() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('redirect');
    }

    async resolveShortUrl(token) {
        const redirectParam = this.getRedirectParameter();
        if (!redirectParam) {
            alert("No redirect parameter provided. You will remain on this page.");
            return;
        }

        try {
            const response = await fetch(`/s/${redirectParam}`, {
                method: "GET",
                headers: { "Authorization": `Bearer ${token}` }
            });

            if (!response.ok) {
                if (response.status === 401) {
                    alert("Session expired. Please sign in again.");
                    localStorage.removeItem("token");
                    return;
                } else {
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

export default Redirect;