# Store Management Tool

Spring Boot REST API project for managing users, products, categories, and price history with role-based access control.

---

## 🚀 Features
- User registration & role management (`USER` / `ADMIN`)
- CRUD for Products and Categories
- Price update with history tracking
- Role-based endpoint access
- Consistent JSON response via `ApiResponse`

---
## 🔑 Authentication

* Uses **Basic Auth**
* Default roles:

  * `USER` (normal access)
  * `ADMIN` (full access)
* Registration is public, but **granting roles** requires an `ADMIN`.

---

## 📋 API Endpoints

### 1. Auth

| Method | Endpoint                                | Description                                                    | Access |
| ------ | --------------------------------------- | -------------------------------------------------------------- | ------ |
| `POST` | `/auth/register`                        | Register new user (`{ "username": "...", "password": "..." }`) | Public |
| `POST` | `/auth/grantRole?username=...&role=...` | Grant role to user                                             | ADMIN  |
| `GET`  | `/auth/users`                           | List all users                                                 | ADMIN  |
| `GET`  | `/auth/roles`                           | List all roles                                                 | ADMIN  |

---

### 2. Categories

| Method   | Endpoint                      | Description            | Access     |
| -------- | ----------------------------- | ---------------------- | ---------- |
| `POST`   | `/categories`                 | Create new category    | ADMIN      |
| `GET`    | `/categories/{id}`            | Get category by ID     | USER/ADMIN |
| `GET`    | `/categories/all`             | List all categories    | USER/ADMIN |
| `PATCH`  | `/categories/{id}`            | JSON Patch a category  | ADMIN      |
| `PATCH`  | `/categories/{id}/inactivate` | Mark category inactive | ADMIN      |
| `DELETE` | `/categories/{id}`            | Delete category        | ADMIN      |

---

### 3. Products

| Method   | Endpoint                     | Description                            | Access     |
| -------- | ---------------------------- | -------------------------------------- | ---------- |
| `POST`   | `/products`                  | Create product (with category in body) | ADMIN      |
| `GET`    | `/products/{id}`             | Get product by ID                      | USER/ADMIN |
| `GET`    | `/products/all`              | List all products                      | USER/ADMIN |
| `PATCH`  | `/products/{id}`             | JSON Patch a product                   | ADMIN      |
| `DELETE` | `/products/{id}`             | Delete product                         | ADMIN      |
| `GET`    | `/products?categoryName=...` | Get products by category name          | USER/ADMIN |
| `GET`    | `/products?categoryId=...`   | Get products by category ID            | USER/ADMIN |

---

### 4. Price

| Method  | Endpoint                      | Description                            | Access |
| ------- | ----------------------------- | -------------------------------------- | ------ |
| `PATCH` | `/price/product/{id}`         | Update product price (records history) | ADMIN  |
| `GET`   | `/price/product/{id}/history` | Get product price history              | ADMIN  |

---

## 🛠️ Usage Flow (suggested order)

1. **Auth**

   * Register a `user`
   * Register an `admin`
   * Grant `ADMIN` role to the admin

2. **Categories**

   * Create a category → saves `{{categoryId}}`
   * (optional) Patch, Inactivate, Delete

3. **Products**

   * Create a product with category → saves `{{productId}}`
   * Get / List / Patch / Delete

4. **Price**

   * Update product price
   * Get product price history

---

## 📂 Postman Collection

Import: [Store Management Tool Postman Collection](https://github.com/user-attachments/files/22067958/Store.Management.Tool.postman_collection.json)

**Environment variables included:**

* `baseUrl` (default: `http://localhost:8080`)
* `userUsername`, `userPassword`
* `adminUsername`, `adminPassword`
* `productId`, `categoryId`

Postman scripts automatically set `{{productId}}` and `{{categoryId}}` when you create resources.

---


