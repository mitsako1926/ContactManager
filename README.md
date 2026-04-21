# 📇 Contact Manager (Java Swing Desktop App)

A modern desktop Contact Manager application built with Java (Swing), featuring a clean UI and full contact management functionality.

---


## 🚀 Features

- 👤 Add new contacts (stored in MySQL database)
- ✏️ Edit existing contacts with real-time updates
- 🔄 CRUD operations fully integrated with database (Create, Read, Update, Delete)
- ❌ Delete contacts with confirmation dialog
- ⭐ Mark contacts as favorite
- 🔍 Live search (real-time filtering from database)
- 🔽 Sort contacts:
  - By First Name
  - By Last Name
  - Favorites First
- 🖼️ Custom profile images:
  - Select image from your system
  - Automatic storage & reuse
- 📂 Built-in default avatars
- 🧠 Smart UI behavior (selection, refresh, state handling)

---


## 🖼️ UI Preview

<p align="center">
  <img src="https://github.com/user-attachments/assets/cf255603-298b-4eba-a908-68786dbc8ad9" width="45%" />
  <img src="https://github.com/user-attachments/assets/267a7615-a0db-4435-826d-710f8bdd6979" width="45%" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/f315750b-c73a-458d-a7f4-adf002736fa2" width="45%" />
  <img src="https://github.com/user-attachments/assets/23194aff-2e1c-409d-8c70-e865c0cff5d1" width="45%" />
</p>

---


## 🏗️ Architecture

The project follows a clean layered structure:
```bash
com.mitsako1926.contactManager
│
├── gui        # Swing UI
├── service    # Business logic
├── dao        # Database access
├── model      # Data model
├── db         # Database connection
└── Main.java
```

---


## 🖼️ Image Handling

- Built-in images loaded from:
  
```bash
resources/images/users/
```

- User images:
  - Selected via file dialog
  - Stored locally at:
```bash
C:\Users\<your-user>\ContactManager\user-images\
```

- Duplicate images are avoided using SHA-256 hashing.

---

## 🛠️ Technologies Used

- **Java 22**
- **Java Swing (AWT/Swing GUI)**
- **MySQL**
- **Wamp Server 3.3.5**
- **MVC-inspired architecture**
- **Eclipse IDE**
- **Git, GitHub, Visual Paradigm**

---

## ⚠️ Warning

- Make sure your database is running (via WAMP, XAMPP, or similar)
- Make sure you have Maven installed (run this at terminal: mvn --version)
- Import the provided SQL script into your database before running the application
- Ensure the application is connected to your local MySQL server (localhost)
- 
---

## SQL SCRIPT
[📥 Download SQL Script](https://github.com/user-attachments/files/26940063/SQL-script.sql)

Use the default MySQL configuration:

- **Host:** localhost  
- **User:** root  
- **Password:** *(empty)*  

> ⚠️ Note: The script creates the database and required tables automatically.

---

## 📌 Future Improvements

- Export/Import contacts (CSV / JSON)

- Add Filters to the search

- Dark mode 🌙

---

## 🎯 What I Learned

- 🧩 Designing a layered architecture (GUI → Service → DAO → Database)
- 🗄️ Integrating a Java application with a MySQL database
- 🔄 Implementing full CRUD operations (Create, Read, Update, Delete)
- 🖼️ Handling user-selected images and storing them efficiently
- 🏗️ Structuring a scalable and maintainable project
- 🧠 Understanding the interaction between frontend (Swing UI) and backend (database)

---

## 💾 Installation

Follow these steps to clone and run the calculator locally. This guide works for Linux, macOS, and Windows (via Git Bash or WSL).

### 1. Clone the repository

Make sure you have Git installed. Open your terminal or command prompt and run:

```bash
git clone https://github.com/mitsako1926/ContactManager.git

cd ContactManager
```

---

### 2. Open the project

You can open the project in:

- IntelliJ IDEA (recommended)
- Eclipse
- or any Java IDE

Make sure the `src` folder is marked as the source root if required by your IDE.

---

### 3. Run the application

---

#### Using an IDE:

Navigate to:

```bash
src/main/java/com/mitsako1926/contactManager/Main.java
```

and run the `Main` class.

---

#### Using the terminal:
Current directory ContactManager
```bash
mvn clean compile
mvn exec:java
```

---

## 🤝 Contributing

Feel free to fork the project and submit improvements.  
Contributions such as bug fixes, optimizations, or new features are welcome.

---

## 📜 License

This project is licensed under the **MIT License**.
