# Monolithic Web Application with Angular 17 and Spring Boot

*A secure, full-stack application featuring authentication (JWT + Google SSO) and Zoom meeting integration*

![Tech Stack](https://img.shields.io/badge/Angular-17-dd0031?logo=angular)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-6db33f?logo=spring)
![Java](https://img.shields.io/badge/Java-21-007396?logo=java)

## ✨ Features

### 🔐 Authentication
| Feature | Description |
|---------|-------------|
| **JWT Login** | Secure username/password authentication with JWT tokens |
| **Google SSO** | One-click login using Google OAuth 2.0 |
| **Route Protection** | Angular guards for secure navigation |

### 🎥 Zoom Integration
| Feature | Description |
|---------|-------------|
| **Meeting Creation** | Create Zoom meetings |
| **Meeting Details** | View meeting information |
| **Join Sessions** | Join existing meetings |

## 🛠 Technology Stack
### Frontend
![Angular](https://img.shields.io/badge/-Angular_17-DD0031?logo=angular&logoColor=white)
![Bootstrap](https://img.shields.io/badge/-Bootstrap_5-7952B3?logo=bootstrap&logoColor=white)
![Toastr](https://img.shields.io/badge/-ngx--toastr-FFE484?logo=angular&logoColor=black)

### Backend
![Spring Boot](https://img.shields.io/badge/-Spring_Boot_3.4.5-6DB33F?logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/-Java_21-007396?logo=java&logoColor=white)
![Spring Security](https://img.shields.io/badge/-Spring_Security-6DB33F?logo=springsecurity&logoColor=white)
![OpenFeign](https://img.shields.io/badge/-Spring_Cloud_OpenFeign-6DB33F?logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/-Java_JWT-000000?logo=jsonwebtokens&logoColor=white)
![H2](https://img.shields.io/badge/-H2_Database-4479A1?logo=h2&logoColor=white)

### Prerequisites
- Node.js v18+
- Java JDK 21
- Angular CLI v17
- [Zoom Developer Account](https://marketplace.zoom.us/)
- [Google Cloud Project](https://console.cloud.google.com/)


## 🚀 Setup Instructions

### 1. Repository Clone

```bash
# Clone the repository
git clone https://github.com/laithbhais/angular-spring-monolithic-app.git
```

### 2. Frontend Setup
```bash
# Navigate to frontend directory
cd angular-spring-monolithic-app/frontend

# Install dependencies
npm install
```


### 1. Backend Setup
```bash
# Navigate to backend directory 
cd angular-spring-monolithic-app/backend

# Install dependencies
mvn clean install
```

### 2. Google OAuth Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Navigate to "APIs & Services" > "Credentials"
4. Create OAuth 2.0 Client ID for Web Application
5. Add authorized redirect URIs:
   - `http://localhost:8080/login/oauth2/code/google`
6. Copy Client ID and Secret to backend properties

### 3. Zoom API Setup
1. Go to [Zoom Marketplace](https://marketplace.zoom.us/)
2. Create a OAuth App
3. Copy these credentials to your backend properties:
   - `zoom.account-id`
   - `zoom.client-id`
   - `zoom.client-secret`
4. Enable these permissions:
   - Meeting:Write (create meetings)
   - Meeting:Read (list meetings)
