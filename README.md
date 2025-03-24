# eKIDS Java PRO EE Spring 2025 – FeedbackApp

Welcome to the **eKIDS Java PRO EE Spring 2025** open-source Java project!  
This is part of the **eKIDS charity initiative**, helping children and young learners develop professional-grade coding skills in Java Enterprise Edition (Jakarta EE).

## 🌱 Project Overview

This repository contains a full example of a Java EE web application called **FeedbackApp**, developed during the eKIDS Java PRO EE Spring 2025 course.

It demonstrates:
- Modern Servlet development using `@WebServlet` (Java-based configuration)
- Full **MVC architecture**: controller, service, model, and JSP views
- Usage of **JSP + JSTL** (Java Standard Tag Library)
- **GET and POST** HTTP method handling
- Session storage of user-submitted feedback
- Simple server-side validation
- JSTL control structures (`c:if`, `c:out`) and EL expressions (`${}`)

The app is perfect for beginners who want to understand Java web development without external frameworks.

## 📦 What’s Inside?

- `FeedbackApp/`
    - `controller/` – Java Servlets (business logic controllers)
    - `model/` – Plain Java objects (user feedback structure)
    - `service/` – Business logic / message generation
    - `webapp/`
        - `index.jsp` – Welcome page (default landing page)
        - `form.jsp` – Form for entering feedback
        - `result.jsp` – Result display page

## 🚀 How to Run

1. Install **Java 17+**
2. Install **Apache Tomcat 10+**
3. Clone this repository
4. Import into **IntelliJ IDEA** (or your preferred IDE)
5. Build and deploy `FeedbackApp` to your Tomcat `webapps` directory
6. Visit [http://localhost:8080/FeedbackApp](http://localhost:8080/FeedbackApp)

## 👨‍🏫 Educational Value

This project is intentionally lightweight, **framework-free**, and uses only native Java EE tools to help students fully understand the HTTP lifecycle, servlet mapping, request processing, session state, and dynamic HTML generation with JSP.

It’s part of a broader mission to empower students aged 12–17 with real-world programming tools, preparing them for further Spring, Hibernate, or RESTful backend development.

## ❤️ About eKIDS

**eKIDS** is a non-profit educational initiative focused on teaching programming and digital thinking to children and teenagers.

This project was developed by students during the **Spring 2025 edition** of the **Java PRO EE** track, guided by mentors from the IT industry.

## 📄 License

This project is distributed under the **MIT License** — feel free to use, remix, and share with attribution.