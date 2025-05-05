# Profile API Documentation

The Profile API allows users to view, update, and delete their profile information. All requests require authentication via a bearer token.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [GET /profile](#get-profile)
  * [PUT /profile](#put-profile)
  * [DELETE /profile](#delete-profile)

## Overview

This module provides access to the authenticated user's profile. It includes the ability to:

* **GET /profile** — Retrieve the user's profile information.
* **PUT /profile** — Update the user's profile information.
* **DELETE /profile** — Delete the user's profile.

Each of these actions requires an authenticated request using a Bearer token.

## Endpoints

### GET `/profile`

Retrieves the user's profile information.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "id": "68180b9bdeedfdcc5db3fe80",
  "name": "jhoe",
  "email": "jhoe@email.com",
  "role": "OWNER"
}
```

### PUT `/profile`

Updates the user's profile information.

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
  "id": "68180b9bdeedfdcc5db3fe80",
  "name": "Maria Julia",
  "email": "MARIA@email.com",
  "role": "OWNER"
}
```

### DELETE `/profile`

Deletes the user's profile.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "message": "Profile deleted successfully"
}
```

## Authentication

All endpoints in this module require Bearer token authentication. The token should be included in the `Authorization` header.

Example:

```http
Authorization: Bearer {{token}}
```

## Error Handling

| Status Code | Error                 | Description                                                                 |
| ----------- | --------------------- | --------------------------------------------------------------------------- |
| 400         | Bad Request           | Invalid or missing profile data                                             |
| 401         | Unauthorized          | Invalid or expired Bearer token                                             |
| 404         | Not Found             | Profile not found (e.g., trying to delete or update a non-existent profile) |
| 500         | Internal Server Error | Unexpected server error                                                     |
