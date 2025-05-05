# URL Shortener API Documentation

The URL Shortener API allows users to manage shortened URLs. It includes functionality for redirecting to the original URL based on the shortened URL identifier. Authentication via a Bearer token is required.

## Table of Contents

* [Overview](#overview)
* [Endpoints](#endpoints)

  * [GET /s/{slug}](#get-redirect)

## Overview

This module provides the following functionality:

* **GET /s/{slug}** — Redirects to the original URL based on the shortened URL identifier.

Authentication via Bearer token is required for this operation.

## Endpoints

### GET `/s/{slug}`

Redirects to the original URL based on the shortened URL identifier.

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Content-Type  | `application/json` |
| Authorization | `Bearer {{token}}` |

**Authentication Required:** Yes (Bearer token)

**URL Parameters**

| Parameter     | Description                                |
| ------------- | ------------------------------------------ |
| `slug` | The unique identifier of the shortened URL |

**Request Example**

```http
GET {{url_base}}/s/77ae196b
```

**Response Example**

This request will redirect the user to the original URL (based on the shortened identifier `77ae196b`).

## Authentication

This endpoint requires Bearer token authentication. The token should be included in the `Authorization` header.

Example:

```http
Authorization: Bearer {{token}}
```

## Error Handling

| Status Code | Error                 | Description                     |
| ----------- | --------------------- | ------------------------------- |
| 401         | Unauthorized          | Invalid or expired Bearer token |
| 404         | Not Found             | Shortened URL not found         |
| 500         | Internal Server Error | Unexpected server error         |
