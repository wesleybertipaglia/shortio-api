# Organization API Documentation

The Organization API allows users to retrieve and update organization details. Authentication via a Bearer token is required for accessing and modifying organization data.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [GET /org](#get-org)
  * [PUT /org](#put-org)

## Overview

This module provides access to the authenticated user's organization. It includes the ability to:

* **GET /org** — Retrieve organization information.
* **PUT /org** — Update organization information.

Authentication is required for both actions via a Bearer token.

## Endpoints

### GET `/org`

Retrieves the organization's information.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "id": "68180b9bdeedfdcc5db3135",
  "name": "Tech Solutions",
  "slug": "tech-solutions",
  "created_at": "2025-05-01T10:00:00Z"
}
```

### PUT `/org`

Updates the organization's details.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Request Body**

```json
{
  "name": "Computers SA",
  "slug": "computers-sa"
}
```

**Response Example**

```json
{
  "id": "68180b9bdeedfdcc5db3135",
  "name": "Computers SA",
  "slug": "computers-sa",
  "created_at": "2025-05-01T10:00:00Z"
}
```

## Authentication

All endpoints in this module require Bearer token authentication. The token should be included in the `Authorization` header.

Example:

```http
Authorization: Bearer {{token}}
```

## Error Handling

| Status Code | Error                 | Description                          |
| ----------- | --------------------- | ------------------------------------ |
| 400         | Bad Request           | Invalid or missing organization data |
| 401         | Unauthorized          | Invalid or expired Bearer token      |
| 404         | Not Found             | Organization not found               |
| 500         | Internal Server Error | Unexpected server error              |
