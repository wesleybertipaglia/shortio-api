# Resources API Documentation

The Resources API allows users to manage resources, including creating, retrieving, updating, and deleting them. Authentication via a Bearer token is required for all actions.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [GET /resources](#get-resources)
  * [GET /resources/{resourceId}](#get-resource)
  * [POST /resources](#post-resources)
  * [PUT /resources/{resourceId}](#put-resources)
  * [DELETE /resources/{resourceId}](#delete-resources)

## Overview

The Resources API provides endpoints for managing resources in the system. Users can:

* **GET /resources** — List all resources.
* **GET /resources/{resourceId}** — Retrieve a specific resource's details.
* **POST /resources** — Create a new resource.
* **PUT /resources/{resourceId}** — Update an existing resource.
* **DELETE /resources/{resourceId}** — Delete a resource.

Authentication via a Bearer token is required for all actions.

## Endpoints

### GET `/resources`

Retrieves a list of resources.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Query Parameters**

| Parameter | Description                               |
| --------- | ----------------------------------------- |
| `page`    | Page number (default 0)                   |
| `size`    | Number of resources per page (default 10) |

**Response Example**

```json
[
  {
    "id": "68177737fb4d7f17eabe296f",
    "url": "dev.to"
  },
  {
    "id": "6817ab841bfa419468171496",
    "url": "github.com"
  }
]
```

### GET `/resources/{resourceId}`

Retrieves the details of a specific resource by its ID.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "id": "6817ab841bfa419468171496",
  "url": "github.com"
}
```

### POST `/resources`

Creates a new resource.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Request Body**

```json
{
  "url": "dev.to"
}
```

**Response Example**

```json
{
  "id": "68177737fb4d7f17eabe296f",
  "url": "dev.to"
}
```

### PUT `/resources/{resourceId}`

Updates an existing resource.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Request Body**

```json
{
  "url": "oracle.com"
}
```

**Response Example**

```json
{
  "id": "6817ab8d1bfa419468171498",
  "url": "oracle.com"
}
```

### DELETE `/resources/{resourceId}`

Deletes a resource.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**Response Example**

```json
{
  "message": "Resource deleted successfully"
}
```

## Authentication

All endpoints in this module require Bearer token authentication. The token should be included in the `Authorization` header.

Example:

```http
Authorization: Bearer {{token}}
```

## Error Handling

| Status Code | Error                 | Description                      |
| ----------- | --------------------- | -------------------------------- |
| 400         | Bad Request           | Invalid or missing resource data |
| 401         | Unauthorized          | Invalid or expired Bearer token  |
| 404         | Not Found             | Resource not found               |
| 500         | Internal Server Error | Unexpected server error          |
