# 📇 Contact Management RESTful API

## 📌 Overview
Aplikasi ini merupakan RESTful API untuk manajemen **contact**, mendukung registrasi user dan operasi CRUD (Create, Read, Update, Delete) terhadap data contact. API dilengkapi dengan **JWT Authentication** dan **rate limiting** untuk keamanan dan performa.

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

## ⏳ Rate Limiting

- Tiap user/IP hanya dapat melakukan sejumlah request tertentu per menit.
- Jika melebihi batas, akan dibalas dengan:
