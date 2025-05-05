# Users API Documentation

The Users API allows administrators to manage users within the system. It includes functionality for retrieving, creating, updating, and deleting user profiles. Authentication is required for all actions via a Bearer token.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [GET /users](#get-users)
  * [GET /users/{userId}](#get-user)
  * [POST /users](#post-users)
  * [PUT /users/{userId}](#put-users)
  * [DELETE /users/{userId}](#delete-users)

## Overview

This module provides the following functionalities for managing users:

* **GET /users** — List all users.
* **GET /users/{userId}** — Retrieve a specific user's details.
* **POST /users** — Create a new user.
* **PUT /users/{userId}** — Update a user's details.
* **DELETE /users/{userId}** — Delete a user.

Authentication is required for all actions via a Bearer token.

## Endpoints

### GET `/users`

Retrieves a list of all users.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
[
  {
    "id": "68177445fb4d7f17eabe296c",
    "name": "Lucia",
    "email": "lucia@email.com",
    "role": "USER"
  },
  {
    "id": "2f8e67a54f7d3b56a14d6890",
    "name": "Wesley Bertipaglia",
    "email": "wesley@email.com",
    "role": "ADMIN"
  }
]
```

### GET `/users/{userId}`

Retrieves a specific user's details.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "id": "68177445fb4d7f17eabe296c",
  "name": "Lucia",
  "email": "lucia@email.com",
  "role": "USER"
}
```

### POST `/users`

Creates a new user in the system.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Request Body**

```json
{
  "email": "lucia@email.com",
  "name": "lucia",
  "password": "12345678"
}
```

**Response Example**

```json
{
  "id": "68177445fb4d7f17eabe296c",
  "name": "Lucia",
  "email": "lucia@email.com",
  "role": "USER"
}
```

### PUT `/users/{userId}`

Updates a user's details.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Request Body**

```json
{
  "name": "Maria Julia"
}
```

**Response Example**

```json
{
  "id": "68177445fb4d7f17eabe296c",
  "name": "Maria Julia",
  "email": "lucia@email.com",
  "role": "USER"
}
```

### DELETE `/users/{userId}`

Deletes a user from the system.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "message": "User deleted successfully"
}
```

## Authentication

All endpoints in this module require Bearer token authentication. The token should be included in the `Authorization` header.

Example:

```http
Authorization: Bearer {{token}}
```

## Error Handling

| Status Code | Error                 | Description                     |
| ----------- | --------------------- | ------------------------------- |
| 400         | Bad Request           | Invalid or missing user data    |
| 401         | Unauthorized          | Invalid or expired Bearer token |
| 404         | Not Found             | User not found                  |
| 500         | Internal Server Error | Unexpected server error         |
