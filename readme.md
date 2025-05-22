# 📇 Contact Management RESTful API

## 📌 Overview

Aplikasi ini adalah sebuah RESTful API yang dirancang untuk manajemen **Contact** secara efisien dan aman. API ini menyediakan fitur-fitur berikut:

- ✅ **User Registration & Authentication** menggunakan **JWT (JSON Web Token)** untuk memastikan hanya pengguna yang terautentikasi dapat mengakses resource.
- 📄 Operasi **CRUD** lengkap (Create, Read, Update, Delete) untuk data **Contact**.
- 🧪 **Unit Testing** untuk memastikan kestabilan dan keandalan fitur.
- 🗄️ Terintegrasi dengan **MySQL** sebagai sistem manajemen basis data.

API ini dibangun dengan pendekatan clean code dan modular.

---

## 👩‍💻 Tech Stack

- Java Spring Boot
- JWT
- Maven
- Mysql
- Lombok
- SpringBoot Starter Data JPA
- Spring Boot Starter Validation

---

## 🚀 Endpoints

### 🧑‍💻 User Endpoints
| Method | Endpoint           | Deskripsi               | Auth Required |
|--------|--------------------|-------------------------|---------------|
| POST   | `/api/users`       | Registrasi user baru    | ❌            |
| POST   | `/api/auth/login`  | Login dan dapatkan JWT  | ❌            |

### 📇 Contact Endpoints
| Method | Endpoint              | Deskripsi                     | Auth Required |
|--------|-----------------------|-------------------------------|---------------|
| GET    | `/api/contacts`       | Ambil semua contact           | ✅            |
| POST   | `/api/contacts`       | Tambah contact baru           | ✅            |
| GET    | `/api/contacts/{id}`  | Ambil detail contact by ID    | ✅            |
| PUT    | `/api/contacts/{id}`  | Update contact by ID          | ✅            |
| DELETE | `/api/contacts/{id}`  | Hapus contact by ID           | ✅            |

---

## 🔐 Security

- Menggunakan **JWT Authentication**
    - Setelah login, client akan menerima token JWT.
    - Token dikirim melalui header:
      ```
      Authorization: Bearer <token>
      ```

- Semua endpoint selain `POST /api/users` dan `POST /api/auth/login` memerlukan token yang valid.
- Validasi input menggunakan anotasi `@Valid`.

---

## 🚀 Cara Menjalankan Aplikasi

Ikuti langkah-langkah berikut untuk menjalankan aplikasi ini secara lokal:

### 1. 📦 Persyaratan Sistem

Pastikan kamu sudah menginstal:

- Java 17 atau lebih baru
- Maven
- MySQL Server
- Postman (opsional, untuk testing API)

### 2. ⚙️ Konfigurasi Database

Buat database MySQL dengan nama sesuai konfigurasi `user_managements`.

