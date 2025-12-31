# ✅ Task Management App

**Task Management App** is a simple yet powerful full-stack application that helps you organize your daily tasks with ease. Create, update, and track your tasks — whether they're pending, in progress, or done — all in one place with a clean and intuitive interface.

I'm **Abdulrahman Gamal**, and I built this project as part of my journey to become a Junior Software Engineer. It's designed with real-world engineering practices in mind — secure authentication, RESTful API design, and a responsive user experience. 💻

---

## 💡 Project Idea

The idea behind this project is to build a task management system that's both **functional** and **secure**. Users can register, log in, and manage their personal tasks with JWT-based authentication ensuring that each user only sees their own data.

The backend is built with **Spring Boot** following clean architecture principles, and the frontend uses **React** with **Tailwind CSS** for a modern, responsive UI. I focused on building a solid foundation with proper security, pagination support, and Docker deployment.

---

## ScreenShoots
<img width="641" height="750" alt="Screenshot 2025-12-29 035159" src="https://github.com/user-attachments/assets/c97ff88b-bff1-47c1-88f0-60e48fbfd3a8" />
<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/698d978f-3b68-4bde-8275-3b6ba8dd603e" />
<img width="1920" height="1020" alt="Screenshot 2025-12-29 051404" src="https://github.com/user-attachments/assets/de0b675d-8745-4fa1-bb04-f1782293f167" />
<img width="945" height="765" alt="Screenshot 2025-12-29 034752" src="https://github.com/user-attachments/assets/9d9e48c9-0dcb-4a66-b617-9f16ef07891c" />
<img width="1603" height="729" alt="Screenshot 2025-12-29 035051" src="https://github.com/user-attachments/assets/2f5f945c-a7f6-4fa3-896b-231fc8610b91" />
<img width="1393" height="292" alt="Screenshot 2025-12-29 034924" src="https://github.com/user-attachments/assets/37ffe214-cf9a-4487-9d70-a0afbe3afc39" />

### Testing

<img width="1920" height="1080" alt="Screenshot 2025-12-31 183559" src="https://github.com/user-attachments/assets/1615355a-dfdb-43ae-8414-b388d460223b" />
<img width="1920" height="1080" alt="Screenshot 2025-12-31 183533" src="https://github.com/user-attachments/assets/c6063b01-6fd7-4af7-a936-6fd06d3fb4d1" />
<img width="1920" height="1080" alt="Screenshot 2025-12-31 183545" src="https://github.com/user-attachments/assets/f8e57fc2-957e-4ed1-9fe9-76add3284b70" />



## ⚙️ Tech Stack

**Backend:**
- Java 25
- Spring Boot 4.0.1
- Spring Security + JWT
- Spring Data JPA
- MySQL 8

**Frontend:**
- React 18
- Vite
- Tailwind CSS
- Lucide React (icons)

**DevOps:**
- Docker & Docker Compose
- Maven

---

## 🔐 Security & Features

I focused heavily on building secure and scalable features:

- **JWT Authentication** - Secure token-based authentication with password encryption using BCrypt
- **User Isolation** - Each user can only access their own tasks (enforced at the database level)
- **Protected Endpoints** - All task operations require valid authentication
- **Pagination Support** - Efficient data loading with paginated responses
- **RESTful API Design** - Clean and consistent API structure

These aren't just buzzwords — the implementation follows Spring Security best practices with proper configuration and authentication providers.

---

## 🚀 How to Run the Project

### Option 1: Using Docker (Recommended)

1. **Clone the repository:**
```bash
git clone <your-repo-url>
cd task-management-app
```

2. **Start all services:**
```bash
docker-compose up --build
```

3. **Access the app:**
- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080/api`
- MySQL: `localhost:3306`

That's it! Docker handles everything for you. 🐳

---

### Option 2: Local Development

**Backend:**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

**Database Setup:**
```sql
CREATE DATABASE taskdb;
CREATE USER 'taskuser'@'localhost' IDENTIFIED BY 'taskpassword';
GRANT ALL PRIVILEGES ON taskdb.* TO 'taskuser'@'localhost';
```

---

## 📱 API Overview

### Authentication
```http
POST /api/auth/register    # Create new account
POST /api/auth/login       # Login and get JWT token
```

### Tasks (Authenticated)
```http
GET    /api/tasks          # Get all tasks (paginated)
POST   /api/tasks          # Create new task
PUT    /api/tasks/{id}     # Update task
DELETE /api/tasks/{id}     # Delete task
```

**Example Request:**
```json
{
  "title": "Complete project documentation",
  "description": "Write comprehensive README",
  "status": "in_progress"
}
```

---

## 🎯 What Makes This Project Different?

This isn't just another CRUD app. Here's what I focused on:

✅ **Real Backend Engineering** - Proper service layer, DTOs, and separation of concerns  
✅ **Security First** - JWT implementation with proper filter chains and authentication providers  
✅ **Docker Ready** - Multi-stage builds and production-ready containers  
✅ **Clean Code** - Following Spring Boot best practices and SOLID principles  
✅ **User Experience** - Responsive design with loading states and error handling

---

## 📊 Upcoming Features

- 🔍 Task search and filtering
- 🏷️ Task categories and tags
- 📅 Due dates and reminders
- 🌙 Dark mode
- 📊 Analytics dashboard
- 📤 Export tasks to PDF

---

## 💫 A Few Words from Me

For me, this project is more than just code — it's a demonstration of my passion for backend development and building meaningful applications. I challenged myself to implement proper authentication, secure endpoints, and create a system that's ready to scale.

I'm currently working on adding more features and improving the analytics side. If you have any questions, feedback, or just want to discuss development, feel free to reach out!

---

## 🤝 Contributing & Feedback

Want to contribute or share ideas? I'd love to hear from you! 💜

- 🐛 **Found a bug?** → Open an [Issue](your-github-issues-link)
- 💡 **Have a feature idea?** → Start a [Discussion](your-github-discussions-link)
- 🔨 **Want to contribute?** → Fork the repo and submit a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

**© 2025 Abdulrahman Gamal. All rights reserved.**

---

## 📬 Contact

**Abdulrahman Gamal**  
Junior Software Engineer Candidate

Feel free to connect with me for any questions or opportunities!

---

*Built with ❤️ using Spring Boot & React*
