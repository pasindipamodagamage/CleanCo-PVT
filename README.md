# 🧼 CleanCo - Home & Office Cleaning Service Booking System 🏠🧹

Welcome to **CleanCo**, a modern, role-based full-stack web application built to simplify and streamline **home and office cleaning service bookings**. 💻📅

> Empowering customers to book services effortlessly, while enabling administrators and employees to manage categories, services, and bookings effectively.

🎥 **Project Mockup Video**: [Watch the Demo on YouTube](https://www.youtube.com/playlist?list=PLxbZZ2kRAIyiHVPn1uDzi5KW9CWcIh3NS)
---

## 🚀 Features

✨ **For All Users:**
- 📋 Browse cleaning service categories
- 🛒 View services and total price in a categorized cart
- 📆 Book cleaning services with date and time selection
- 🔐 Secure registration & login with JWT authentication

👨‍💼 **For Employees & Admins:**
- 🗂️ Manage categories and services (Add, Update, Delete)
- ✅ Approve or ❌ Reject bookings
- 🔄 Toggle user active/inactive status

👑 **For Admins Only:**
- 🔐 Full control over user roles and platform access

---

## 🛠️ Tech Stack

**Backend**:  
- 🧬 Java + Spring Boot  
- 🔐 Spring Security + JWT  
- 🌐 RESTful APIs  
- 🗃️ MySQL  
- 🔄 ModelMapper & DTO architecture

**Frontend**:  
- 🧩 jQuery + AJAX  
- 🎨 Bootstrap  
- 🧑‍💻 HTML5 + CSS3  

---

## 🔐 Roles & Access Control

| Role         | Capabilities |
|--------------|--------------|
| 👤 User       | Register, Login, Book Services |
| 🧹 Employee   | Manage services, categories, bookings |
| 🛡️ Admin     | Full access to all features and user management |

All requests are secured via **JWT tokens** and filtered through a custom `JwtFilter` 🔐.

---

## 📁 Project Structure

```bash
CleanCo-PVT/
├── src/
│   ├── config/              # JWT, Security, App config
│   ├── controller/          # REST Controllers
│   ├── dto/                 # Data Transfer Objects
│   ├── entity/              # JPA Entities
│   ├── repo/                # Spring Data Repositories
│   ├── service/             # Business Logic Services
│   └── util/                # Utility classes like VarList, ResponseUtil
```

---

## 🔧 Installation & Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/pasindipamodagamage/CleanCo-PVT.git
   ```

2. **Import into IDE** (e.g., IntelliJ, Eclipse)

3. **Configure MySQL Database**

4. **Run Backend**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📝 Author

👩‍💻 **Nagoda Gamage Pasindi Pamoda**  
Undergraduate Software Engineer | Passionate about full-stack development 🚀  
📫 [GitHub](https://github.com/pasindipamodagamage)

---

## 💡 Future Enhancements

- 📱 Responsive mobile view
- 💳 Integrated payment gateway (e.g., PayHere)
- 📊 Admin dashboard with analytics
- 🔔 Email notifications for bookings

---

## 📄 License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and contribute! 🤝

---
