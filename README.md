# Retail Inventory Management System

A comprehensive, enterprise-grade backend solution designed to streamline retail operations. This application provides robust management for inventory, products, employees, vendors, and multi-location logistics, built with modern Java technologies and best practices.

## 🚀 Overview

The Retail Inventory Management System is a RESTful API designed to serve as the backbone for retail businesses. It handles the complex lifecycle of retail goods—from purchasing and receiving to inventory tracking and inter-location transfers. Additionally, it includes a secure and granular employee management system with role-based access control.

## ✨ Key Features

### 📦 Inventory & Supply Chain
- **Product Management:** Create and manage products with support for categories, brands, and variations.
- **Purchase Orders:** Generate and track purchase orders for vendors.
- **Receiving Vouchers:** Verify and process incoming shipments against purchase orders.
- **Inventory Tracking:** Real-time stock monitoring across multiple locations.
- **Stock Transfers:** Manage the movement of goods between warehouses and retail outlets.

### 🏢 Organization Management
- **Multi-Location Support:** distinct handling for Retail Locations and Warehouse Locations.
- **Vendor Management:** Maintain detailed vendor profiles and purchase history.

### 👥 Employee & Security
- **Advanced Authentication:** Dual support for traditional Email/Password login and OAuth2 (Google, GitHub).
- **Role-Based Access Control (RBAC):** Granular permissions system with defined Roles.
- **Employee Hierarchy:** Model reporting lines and organizational structure.
- **Session Management:** Scalable, distributed session handling via Redis.

## 🛠️ Technology Stack

- **Core:** Java 22, Spring Boot 3
- **Database:** PostgreSQL (Persistence via Spring Data JPA)
- **Caching & Sessions:** Redis
- **Security:** Spring Security (OAuth2 Client + JWT/Session)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose

## ⚙️ Getting Started

### Prerequisites
- **Docker** and **Docker Compose**
- **Java 22** (for local development)
- **Maven** (Wrapper provided)

### 🔧 Installation & Configuration

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd retail-inventory-management
    ```

2.  **Configure Environment:**
    Copy the example configuration file to create your local configuration.
    ```bash
    cp src/main/resources/application.properties.example src/main/resources/application.properties
    ```
    Open `application.properties` and update the following fields as necessary (specifically OAuth2 credentials and Mail settings):
    ```properties
    # Database (Defaults configured for Docker)
    spring.datasource.url=jdbc:postgresql://db:5432/retail_db
    spring.datasource.username=postgres
    spring.datasource.password=password

    # OAuth2 (Required for Social Login)
    spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
    spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_SECRET

    # Mail Configuration (For emails)
    spring.mail.host=smtp.example.com
    # ... other mail settings
    ```

### 🐳 Running with Docker

The project includes convenience scripts to manage the Docker environment. Ensure they are executable:

```bash
chmod +x *.sh
```

**Option 1: Fresh Start (Recommended for initial setup)**
This script tears down existing containers, removes volumes (resets DB), and starts everything fresh.
```bash
./reset-dev.sh
```

**Option 2: Restart Services**
Use this to rebuild the application (Maven) and restart containers without wiping the database volumes.
```bash
./restart-services.sh
```

**Option 3: Manual Start**
```bash
# Build the application
./mvnw clean install -DskipTests

# Start containers
docker compose -f docker-compose-dev.yml up -d
```

## 📖 API Documentation

Once the application is running, you can explore the interactive API documentation:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🧪 Development Notes

- **Database Initialization:** The application uses `init-data.sql` and `fresh-dump.sql` to seed the database with initial data for testing and development.
- **Logging:** configured to provide detailed debug info for the application package (`dev.austinbarnes.retail`) and standard info for Spring.

