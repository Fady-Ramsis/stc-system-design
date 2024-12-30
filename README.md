Here is a **README.md** file for your project:

---

# **STC System Design**

This project implements a system for managing spaces, folders, and files with user permissions. The system allows users
to create spaces, folders, and files while enforcing access control using `VIEW` and `EDIT` permissions. The project is
built with **Spring Boot**, uses **MSSQL** as the database, and is fully containerized with Docker and Docker Compose.

---

## **Features**

- **Space Management**:
    - Create a space with assigned permission groups.
- **Folder Management**:
    - Create folders under spaces, enforcing `EDIT` access permissions.
- **File Management**:
    - Upload files (binary data like PDFs) under folders, ensuring `EDIT` access permissions.
    - View and download file metadata and binary data.
- **Access Control**:
    - Users are assigned permissions (`VIEW` or `EDIT`) via permission groups.
    - Only users with `EDIT` access can create folders or files.
    - Users with `VIEW` access can only view metadata.

---

## **Technologies Used**

- **Backend**:
    - Spring Boot (3.3.4)
    - Spring Data JPA
    - MSSQL
- **Database**:
    - Microsoft SQL Server (MSSQL)
- **Containerization**:
    - Docker
    - Docker Compose

---

## **Project Structure**

```plaintext
project/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── controller/     # API Controllers
│   │   │   ├── service/        # Service Layer
│   │   │   ├── repository/     # JPA Repositories
│   │   │   └── entity/         # Entity Models
│   │   └── resources/
│   │       ├── application.properties  # Spring Boot configuration
│   │       └── db/
│   │           ├── Dockerfile           # Spring Boot container build
│   │           └── init.sql             # Database initialization
├── docker-compose.yml                   # Compose setup for app + db
├── pom.xml                               # Maven dependencies
└── README.md                             # Project Documentation
```

---

## **Getting Started**

### **Requirements**

- Docker
- Docker Compose
- Java 17+
- Maven

---

### **Setup and Run**

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/stc-system-design.git
   cd stc-system-design
   ```

2. **Build the Application**:
   Ensure the Spring Boot JAR file is generated:
   ```bash
   mvn clean package
   ```

3. **Start the Docker Services**:
   Bring up the application and database using Docker Compose:
   ```bash
   docker compose -f  docker-compose-mssql.yml  up --build -d
   docker exec -it mssql /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P 'ASD@12345' -Q "CREATE DATABASE [stc-assessment];"
   docker compose -f  docker-compose-app.yml --env-file environment_template up --build -d

   ```

4. **Access the Application**:
    - **API**: `http://localhost:8080`
    - **MSSQL Database**: Port `1433` (credentials in `docker-compose.yml`).

---

## **API Endpoints**

### **Create Space**

- **Endpoint**: `POST /api/spaces`
- **Query Parameters**:
    - `space_name`: Name of the space.
- **Example**:
  ```bash
  curl -X POST "http://localhost:8080/api/spaces?space_name=stc-assessments"
  ```

---

### **Create Folder**

- **Endpoint**: `POST /api/folders`
- **Query Parameters**:
    - `parent_space_id`: ID of the parent space.
    - `folder_name`: Name of the folder.
    - `user_email`: Email of the user creating the folder.
- **Example**:
  ```bash
  curl -X POST "http://localhost:8080/api/folders?parent_space_id=1&folder_name=backend&user_email=edit.user@example.com"
  ```

---

### **Create File**

- **Endpoint**: `POST /api/files`
- **Query Parameters**:
    - `folder_id`: ID of the parent folder.
    - `file_name`: Name of the file.
    - `user_email`: Email of the user uploading the file.
- **Body**: Binary file data (PDF, etc.).
- **Example (Using Postman)**:
    - Set `folder_id`, `file_name`, and `user_email` in query parameters.
    - Upload the file in the `binary` tab.

---

### **View File Metadata**

- **Endpoint**: `GET /api/items/{item_id}/metadata`
- **Query Parameters**:
    - `userEmail`: Email of the user viewing the metadata.
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/items/1/metadata?user_email=view.user@example.com"
  ```

---

### **Download File**

- **Endpoint**: `GET /api/files/{id}/download`
- **Query Parameters**:
    - `userEmail`: Email of the user downloading the file.
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/files/1/download?user_email=view.user@example.com" -o assessment.pdf
  ```

---

## **Database Schema**

### **Tables**

- **`permission_group`**: Manages groups with shared permissions.
- **`permission_`**: Links users to permissions (VIEW/EDIT).
- **`item_`**: Represents spaces, folders, and files with a parent-child hierarchy.
- **`file_`**: Stores binary data for files.

---



