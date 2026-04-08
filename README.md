# Ecommerce Website

![Ecommerce Cover](https://images.unsplash.com/photo-1481437156560-3205f6a55735?w=1200&auto=format&fit=crop)

A full-stack ecommerce app built with Angular 16 and Spring Boot.

## Project structure

- **login-app/** – Angular frontend (login page, auth service)
- **springboot-backend/** – Spring Boot REST API (login endpoint, CORS enabled)

## Quick start

### 1. Backend (Spring Boot)

```bash
cd springboot-backend
./mvnw spring-boot:run
```

Or with Maven installed:

```bash
cd springboot-backend
mvn spring-boot:run
```

Backend runs at **http://localhost:8080**.  
Login API: `POST http://localhost:8080/api/auth/login`

**Demo credentials:**
- **admin** / **admin** → success  
- Any username with password **password** → success  
- Anything else → "Invalid username or password"

### 2. Frontend (Angular)

```bash
cd login-app
npm install
npm start
```

Frontend runs at **http://localhost:4200**.  
Open the app and use the demo credentials above.

## API

**POST** `/api/auth/login`

Request body:
```json
{
  "username": "admin",
  "password": "admin"
}
```

Response (success):
```json
{
  "success": true,
  "message": "Login successful!",
  "token": "demo-token-admin"
}
```

Response (failure):
```json
{
  "success": false,
  "message": "Invalid username or password."
}
```

## Requirements

- Node.js 20 + npm (for Angular)
- Java 17 + Maven (for Spring Boot)
