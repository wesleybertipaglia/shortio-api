# 🐕 shortio — Hackathon Submission for Permit.io

Welcome to **shortio**, my submission for the [Permit.io Hackathon](https://dev.to/challenges/permit_io). This project is built on a key principle:

> **Access control shouldn't be an afterthought — it should be built in.**

In an era where link sharing and resource access happen instantly, reliable, organization-aware permission management is essential to ensure that **only the right people access the right resources at the right time**.

## 📑 Table of Contents

* [Features](#features)
* [How It Works](#how-it-works)
* [Getting Started](#getting-started)
* [Documentation](#documentation)
* [Contributing](#contributing)
* [License](#license)

## 🌟 Features

* **Role-Based Access Control (RBAC)** with support for multiple user roles
* **Organization-aware resource permissions**, enabling scoped access
* **JWT-based authentication** for secure session handling
* **Seamless redirect flow** between backend and frontend
* **React frontend** for sign-in, sign-up, and user onboarding
* **Quarkus backend** with MongoDB and Panache for data persistence
* **Developer-friendly setup** with hot reload and simple local development

## 🔄 How It Works

### User Roles

* **Owner**: Full administrative access to the organization, users, and resources
* **Admin**: Can manage users and resources but cannot modify organization-level settings
* **Employee**: Restricted to viewing only the resources they have been granted access to

### Access Flow

1. A new user signs up and is assigned the `Owner` role for a newly created organization.
2. The owner creates resources and invites other users to join the organization.
3. A user accesses a resource using a short link (e.g., `http://localhost:8080/s/{resourceId}`).
4. If the user is not authenticated, they are redirected to the frontend for login or registration.
5. Upon authentication, the backend verifies:

   * Whether the user belongs to the same organization
   * Whether the user has permission to access the requested resource
6. If validation passes, the backend responds with the resource’s destination URL.
7. The frontend handles the final redirect to the resource.

## 🛠️ Getting Started

### Prerequisites

Before starting, ensure you have the following installed:

* Java 21+
* Node.js v18+
* Docker (optional, for MongoDB)
* `make` (used for backend automation)

### Setup

Clone both repositories:

```bash
git clone https://github.com/wesleybertipaglia/shortio-api.git
git clone https://github.com/wesleybertipaglia/shortio-app.git
```

#### Start the Backend

```bash
cd shortio-api
make dev
```

Backend runs at: `http://localhost:8080`

#### Start the Frontend

```bash
cd shortio-app
npm install
npm run dev
```

Frontend runs at: `http://localhost:5173`

## 📚 Documentation

API and module documentation can be found in the `docs` directory within the backend repository:

* **Authentication** → [/docs/auth](/docs/auth)
* **User Profiles** → [/docs/profile](/docs/profile)
* **Organizations** → [/docs/org](/docs/org)
* **Users** → [/docs/users](/docs/users)
* **Resources** → [/docs/resources](/docs/resources)
* **URL Shortener** → [/docs/shortner](/docs/shortner)

## 🤝 Contributing

Contributions are welcome and appreciated. If you have suggestions, improvements, or encounter bugs, feel free to open an issue or submit a pull request.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
