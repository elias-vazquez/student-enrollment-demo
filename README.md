# Student Enrollment Demo - SFWE 405/505

**Author**: Elias Vazquez (Preceptor)  
**Course**: SFWE 405/505 - Software Architecture and Design  
**Instructor**: Dr. Cerny  
**Date**: February 2026

---

## Project Overview

This project is a demo of a **Student Enrollment System** built using Spring Boot that showcases the three-layer Enterprise Application Architecture covered in Weeks 1-3 of the course. It's not a perfect system, but it's definitely a nice hands on experience for working with the in-class concepts.

### Development
This demo was created in VS Code but has all of the approrpaite parts to translate to Eclipse. That being said, if you are seeing errors about Java not being able to find symbols or methods, it's because those methods aren't actually written in the code and instead they are generated when **Maven runs and processes the Lombok** annotations correctly. Hence the Lombok in the dependencies of the pom file. Additionally, this course is focused on Architecture and Design so the HTML seen in the demo is 100% claude generated and looks way better than what I would have made, so shoutout to claude.

### Key Features Demonstrated

- **Form Submission** - Create new student enrollments with validation  
- **Table with Pagination** - Display enrollments with page navigation (5 per page)  
- **CRUD Operations** - Create and Delete enrollments  
- **Business Logic Validation** - Prevent duplicate enrollments  
- **Transaction Management** - @Transactional service methods  
- **Professional UI** - Bootstrap 5 responsive design

---

## How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps

1. **Extract the zip file**
```bash
cd student-enrollment-demo
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Open browser**
```
http://localhost:8080
```

5. **Stop the application**
Press `Ctrl+C` in terminal

---

## Demo Walkthrough

### Feature 1: View Enrollments with Pagination

**URL**: http://localhost:8080

- Table shows 5 enrollments per page
- Click page numbers to navigate (1, 2, 3...)
- Click "Next" or "Previous" buttons
- Total enrollment count displayed

### Feature 2: Create New Enrollment

1. Click **"+ New Enrollment"** button
2. Select a student from dropdown
3. Select a course from dropdown
4. Enter semester (e.g., "Spring 2024")
5. Click **"Create Enrollment"**
6. See success message
7. New enrollment appears in table

### Feature 3: Delete Enrollment

1. Click red **"Delete"** button on any row
2. Confirm the popup
3. See success message
4. Enrollment removed

### Feature 4: Business Validation

Try enrolling the same student in the same course twice:
- You'll see an error message preventing duplicates
- Demonstrates business logic validation

---

## Sample Data

### Students (5)
- John Doe
- Anakin Skywalker
- LeBron James
- Mario Mario
- Jermaine Cole

### Courses (5)
- SFWE405 - Software Architecture and Design
- UNIV301 - General Education Portfolio
- ENGR498B - Interdisciplinary Capstone
- SFWE409 - Cloud Computing Principles
- SFWE402 - Software DevSecOps

### Initial Enrollments
- 10 active enrollments (2 pages)

---

## Architecture Overview

### Persistence Layer (Week 1-2)
> Handles data storage and retrieval.
- **Entities**: Student, Course, Enrollment (JPA annotations) 
> Java classes mapped to database tables using @Entity. Represent the data model.
- **Repositories**: Spring Data JPA with pagination support
> Interfaces that provide CRUD operations without writing SQL. Spring Data JPA automatically implements these.
- **Database**: H2 in-memory
> Temporary database stored in memory. Perfect for demos since there is no installation needed and the data resets on restart.


### Business Layer (Week 3)
> Contains business logic and domain rules. Determines how data can be created, displayed, stored, and changed.
- **Service**: EnrollmentService with @Transactional
> Classes marked with @Service that orchestrate business operations. @Transactional ensures database consistency.
- **Business Rules**: Prevent duplicate enrollments
> Domain-specific logic that enforces real-world constraints. Such as a student can't enroll in the same course twice.
- **Validation**: Student/Course existence checks
> Ensures data integrity by verifying entities exist before operations. Throws exceptions for invalid data.

### Presentation Layer (Week 2)
> Handles user interaction and displays data. Receives input and shows results to users.
- **Controllers**: Spring MVC with @GetMapping/@PostMapping
> Classes marked with @Controller that handle HTTP requests. Route URLs to appropriate methods.
- **Views**: Thymeleaf templates with Bootstrap 5
> HTML templates that render dynamic content. Thymeleaf processes server-side data into HTML pages.
- **Forms**: HTML form submission
> User input collection through HTML forms. Submit data via POST requests to the controllers.
- **Pagination**: Spring Data Page/Pageable
> Splits large datasets into pages. Page holds data for one page. Pageable specifies page number and size.

---

## Project Structure

```
student-enrollment-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── enrollment/
│   │   │               ├── StudentEnrollmentDemoApplication.java  # Main application
│   │   │               ├── config/
│   │   │               │   └── DataLoader.java                    # Sample data loader
│   │   │               ├── controller/                            # Presentation Layer
│   │   │               │   ├── HomeController.java
│   │   │               │   └── EnrollmentController.java
│   │   │               ├── entity/                                # Persistence Layer
│   │   │               │   ├── Student.java
│   │   │               │   ├── Course.java
│   │   │               │   └── Enrollment.java
│   │   │               ├── repository/                            # Persistence Layer
│   │   │               │   ├── StudentRepository.java
│   │   │               │   ├── CourseRepository.java
│   │   │               │   └── EnrollmentRepository.java
│   │   │               └── service/                               # Business Layer
│   │   │                   └── EnrollmentService.java
│   │   └── resources/
│   │       ├── application.properties                             # Configuration
│   │       └── templates/                                         # UI Templates
│   │           ├── enrollment-list.html                           # Table + Pagination
│   │           └── enrollment-form.html                           # Form
│   └── test/
│       └── (test files can go here)
├── target/                                                        # Compiled classes (auto-generated)
├── pom.xml                                                        # Maven dependencies
└── README.md                                                      # Documentation
```

---

## Technologies Used

- **Spring Boot 3.2.2** - Framework
- **Spring Data JPA** - Persistence
- **Spring MVC** - Web layer
- **Thymeleaf** - Templates
- **H2 Database** - In-memory DB
- **Bootstrap 5** - UI styling
- **Lombok** - Reduce boilerplate
- **Maven** - Build tool

---

## Database Console (Optional)

If you would like to see the database more in-debth, feel free to checkout the H2 Console. This is a built-in database admin tool that comes with H2 database. It's just there to help developers verify that data is there and working. Pretty cool!!

**URL**: http://localhost:8080/h2-console

**Login Credentials**:
- JDBC URL: `jdbc:h2:mem:enrollmentdb`
- Username: `sa`
- Password: (blank)

View tables: STUDENTS, COURSES, ENROLLMENTS

**Example Test:**
1. **Run Program:** In terminal `mvn spring-boot:run`
2. **Access URL:** In browswer `http://localhost:8080/h2-console`
3. **Login:** Match to the above login credentials and select `connect`
4. **Select Data:** On the left sidebar, click "STUDENTS", you should see the SQL statement populate with: `SELECT * FROM STUDENTS`
5. **Run SQL Statement:** At the top, select the green `play` button to run the query.
6. **Results:** The `results table` at the bottom of the screen should populate.
---

## Course Concepts Demonstrated

### Week 1-2: Persistence & ORM
- JPA Entity annotations (@Entity, @Table, @Id)  
- Relationships (@ManyToOne)  
- Spring Data JPA repositories  
- Pagination with Pageable

### Week 3: Business Layer
- @Service annotation  
- @Transactional management  
- Business rule validation  
- Exception handling

### Week 2: Presentation & REST
- @Controller, @GetMapping, @PostMapping  
- Form submission handling  
- Model-View-Controller pattern  
- Request parameters

---

## Troubleshooting

**Port 8080 in use?**  
Change port in `application.properties`: `server.port=8081`

**Maven not found?**  
Install from: https://maven.apache.org/install.html

**Java not found?**  
Install Java 17 from: https://adoptium.net/

---

## Contact
If you have any questions, feel free to ask an AI for explainations or reach out to a human like me through the email below.
- **Email**: eliasvazquez@arizona.edu

---

**Created for SFWE 405/505 - University of Arizona**