# Auth API Documentation

This API provides endpoints for user registration and authentication using JSON Web Tokens (JWT). Authenticated users receive a JWT token to access protected routes via the `Authorization` header.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [POST /auth/signup](#post-authsignup)
  * [POST /auth/signin](#post-authsignin)
* [Error Handling](#error-handling)

## Overview

The Auth API allows users to register and log in using their email and password. On success, both endpoints return a JWT token along with user details.

**Available Endpoints:**

* `POST /auth/signup` — Register a new user and receive a JWT.
* `POST /auth/signin` — Authenticate an existing user and receive a JWT.

## Endpoints

### POST `/auth/signup`

Registers a new user and returns a JWT token along with user information.

**Headers**

| Key          | Value              |
| ------------ | ------------------ |
| Content-Type | `application/json` |

**Authentication Required:** No

**Request Body**

```json
{
  "email": "hanna@email.com",
  "name": "hanna",
  "password": "12345678"
}
```

**Response**

```json
{
  "token": {
    "type": "Bearer",
    "content": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9...",
    "expiration": "2025-05-06T00:51:40.842099063Z"
  },
  "user": {
    "id": "68180b9bdeedfdcc5db3fe80",
    "name": "hanna",
    "email": "hanna@email.com",
    "role": "OWNER"
  }
}
```

### POST `/auth/signin`

Authenticates a user and returns a JWT token along with user information.

**Headers**

| Key          | Value              |
| ------------ | ------------------ |
| Content-Type | `application/json` |

**Authentication Required:** No

**Request Body**

```json
{
  "email": "hanna@email.com",
  "password": "12345678"
}
```

**Response**

```json
{
  "token": {
    "type": "Bearer",
    "content": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9...",
    "expiration": "2025-05-06T00:51:40.842099063Z"
  },
  "user": {
    "id": "68180b9bdeedfdcc5db3fe80",
    "name": "hanna",
    "email": "hanna@email.com",
    "role": "OWNER"
  }
}
```

## Error Handling

| Status Code | Error                 | Description                                |
| ----------- | --------------------- | ------------------------------------------ |
| 400         | Bad Request           | Missing or invalid signup/signin data      |
| 401         | Unauthorized          | Invalid email or password                  |
| 409         | Conflict              | Email already registered (during signup)   |
| 500         | Internal Server Error | An unexpected error occurred on the server |
