# 🐕 shortio — Hackathon Submission for Permit.io

Welcome to **shortio**, a secure, organization-aware URL shortener developed for the [Permit.io Hackathon](https://dev.to/challenges/permit_io).

In today’s fast-moving digital world, link sharing is instantaneous—but access control often isn't. **shortio** is built around a core belief:

> **Access control shouldn't be an afterthought — it should be built in.**

By integrating Permit.io, shortio ensures that shared links are only accessible by the right people, within the right organization, at the right time.

## 📑 Table of Contents

* [Features](#features)
* [How It Works](#how-it-works)
* [Getting Started](#getting-started)
* [Documentation](#documentation)
* [Contributing](#contributing)
* [License](#license)

## 🌟 Features

* **Role-Based Access Control (RBAC)** supporting `owner`, `admin`, and `employee` roles
* **Organization-scoped resource permissions** for fine-grained access
* **JWT-based authentication** for secure session management
* **Seamless frontend–backend integration** with automatic login redirection
* **Modern tech stack**: Quarkus, Quarkus Qute, MongoDB, and Panache ORM
* **Developer-friendly setup**: hot reload, simple environment configuration, and Docker support

## 🔄 How It Works

### User Roles

* **Owner**: Full access to organization settings, users, and resources
* **Admin**: Can manage users and resources but cannot alter organization-level settings
* **Employee**: Can only view resources they've been granted access to

### Access Flow

1. A new user signs up and becomes the `Owner` of a new organization.
2. The owner creates resources and invites team members.
3. A user accesses a resource via a short link (e.g., `http://localhost:8080/s/{resourceId}`).
4. If unauthenticated, the user is redirected to sign in or sign up.
5. Upon authentication, the backend validates:

   * Whether the user belongs to the same organization
   * Whether the user has permission to access the resource
6. If validation passes, the backend returns the resource's destination URL.
7. The frontend then redirects the user to the target destination.

## 🛠️ Getting Started

### Prerequisites

Make sure the following tools are installed:

* Java 21+
* Docker (optional, used for MongoDB)
* `make` (used for backend automation tasks)

### Setup Instructions

Clone the repository:

```bash
git clone https://github.com/wesleybertipaglia/shortio-api.git
```

#### Configure Permit.io

1. Sign up at [Permit.io](https://app.permit.io/)

2. In the [Policy Editor](https://app.permit.io/policy-editor), create the following resources:

| Type     | Key      |
| -------- | -------- |
| user     | user     |
| org      | org      |
| resource | resource |

3. Define the following roles and permissions:

| Role     | Resource | Create | Read | Update | Delete |
| -------- | -------- | ------ | ---- | ------ | ------ |
| owner    | ✅        | ✅      | ✅    | ✅      | ✅      |
| admin    | ✅        | ✅      | ✅    | ✅      | ✅      |
| employee | ✅        |        | ✅    |        |        |

4. Copy your API key from the [API Keys page](https://app.permit.io/settings/api-keys).

5. Duplicate the example environment file and set your Permit API key:

```bash
cp -r .env.example .env
```

Update `.env`:

```env
PERMIT_API_KEY=your_api_key_here
```

### Running the api

```bash
cd shortio-api
make dev
```

* Backend URL: [localhost:8080](http://localhost:8080)

* Swagger UI: [localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)

## 📚 Documentation

Detailed module and API documentation is available in the backend repo under the `docs` directory:

* [Authentication](./docs/auth.md)
* [User Profiles](./docs/profile.md)
* [Organizations](./docs/org.md)
* [Users](./docs/users.md)
* [Resources](./docs/resources.md)
* [URL Shortener](./docs/shortner.md)

> You can also access the [swagger ui](http://localhost:8080/q/swagger-ui/).

## 🤝 Contributing

Contributions, suggestions, and issue reports are welcome! Please open an issue or submit a pull request if you’d like to help improve **shortio**.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
