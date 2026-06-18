# 🐳 Docker Setup Guide

A complete Docker containerization guide for the Ecommerce Spring Boot application with PostgreSQL database.

---

## 📋 Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Common Commands](#common-commands)
- [Troubleshooting](#troubleshooting)
- [Architecture](#architecture)

---

## 📦 Prerequisites

Ensure you have installed:

- **Docker Desktop** ([Download](https://www.docker.com/products/docker-desktop)) or Docker Engine
- **Docker Compose** (included with Docker Desktop)
- **Git** (to clone the repository)

**Verify Installation:**
```bash
docker --version
docker-compose --version
```

---

## 🏗️ Project Structure

```
ecommerce/
├── Dockerfile                 # Multi-stage build for Spring Boot app
├── docker-compose.yml         # Orchestrates app + PostgreSQL
├── .dockerignore             # Files excluded from Docker build
├── .env.example              # Environment variables template
├── pom.xml                   # Maven configuration
├── src/                      # Application source code
├── target/                   # Compiled artifacts
└── DOCKER_README.md          # This file
```

---

## 🚀 Quick Start

### 1️⃣ Clone & Navigate
```bash
cd /Users/ankitkumar/Documents/Ecommerce
```

### 2️⃣ Create Environment File (Optional)
```bash
cp .env.example .env
```
Edit `.env` if you want custom database credentials.

### 3️⃣ Build & Start Services
```bash
docker-compose up --build
```

Wait for logs showing:
```
app-1  | Started EcomApplicationApplication in X seconds (JVM running for Y seconds)
```

### 4️⃣ Verify Everything Works
```bash
# Check running containers
docker-compose ps

# Test the application
curl http://localhost:8080/actuator/health
```

✅ You should see: `{"status":"UP"}`

---

## ⚙️ Configuration

### Environment Variables

Edit your `.env` file or modify `docker-compose.yml`:

| Variable | Default | Description |
|----------|---------|-------------|
| `POSTGRES_USER` | `ecom_user` | Database username |
| `POSTGRES_PASSWORD` | `ecom_password` | Database password |
| `POSTGRES_DB` | `ecommerce` | Database name |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Auto schema update (create/update/validate/none) |

### Database Connection Details

| Component | Host | Port | Default |
|-----------|------|------|---------|
| Application | `localhost` | `8080` | http://localhost:8080 |
| PostgreSQL | `localhost` | `5432` | jdbc:postgresql://localhost:5432/ecommerce |

**From within Docker:**
- App connects to DB using hostname: `db` (internal Docker network)
- External access uses `localhost:5432`

---

## 📝 Common Commands

### Start & Stop

```bash
# Start in foreground (see logs)
docker-compose up

# Start in background
docker-compose up -d

# Stop services
docker-compose down

# Stop and remove volumes (⚠️ deletes database)
docker-compose down -v
```

### Logs & Monitoring

```bash
# View all logs
docker-compose logs

# Follow application logs
docker-compose logs -f app

# Follow database logs
docker-compose logs -f db

# View logs from the last hour
docker-compose logs --since 1h
```

### Container Management

```bash
# List running containers
docker-compose ps

# Execute command in app container
docker-compose exec app bash

# Execute SQL command in database
docker-compose exec db psql -U ecom_user -d ecommerce -c "SELECT * FROM users;"

# View container resource usage
docker stats
```

### Build & Rebuild

```bash
# Rebuild images (after code changes)
docker-compose build

# Rebuild and start without cache
docker-compose build --no-cache
docker-compose up
```

### Cleanup

```bash
# Remove stopped containers
docker-compose down

# Remove unused images
docker image prune

# Remove all unused resources
docker system prune -a
```

---

## 🔧 Troubleshooting

### Issue: Port 8080 already in use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change port in docker-compose.yml (left side of colon)
# ports:
#   - "8081:8080"
```

### Issue: Port 5432 already in use
```bash
# Find process using port 5432
lsof -i :5432

# Or use different port in docker-compose.yml
# ports:
#   - "5433:5432"
```

### Issue: Database connection refused
```bash
# Check database health
docker-compose ps

# View database logs
docker-compose logs db

# Restart database
docker-compose restart db
```

### Issue: Application crashes on startup
```bash
# View detailed logs
docker-compose logs app

# Check environment variables
docker-compose config

# Rebuild from scratch
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

### Issue: Volume permission errors
```bash
# Check volume ownership
docker volume ls
docker volume inspect ecommerce_postgres_data

# Remove and recreate volume
docker-compose down -v
docker-compose up
```

---

## 🏛️ Architecture

```
┌─────────────────────────────────────────────────────┐
│         Your Local Machine (Host)                    │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │         Docker Network: ecommerce-network    │  │
│  │                                               │  │
│  │  ┌─────────────────┐    ┌──────────────────┐ │  │
│  │  │   Spring Boot   │    │   PostgreSQL 16  │ │  │
│  │  │   Application   │◄──►│   Database       │ │  │
│  │  │                 │    │                  │ │  │
│  │  │  Container: app │    │ Container: db    │ │  │
│  │  │  Port: 8080     │    │ Port: 5432       │ │  │
│  │  └─────────────────┘    └──────────────────┘ │  │
│  │           ▲                      ▲             │  │
│  └───────────┼──────────────────────┼─────────────┘  │
│              │                      │                 │
│              │                      │                 │
│    localhost:8080          localhost:5432            │
│    (Application)         (Database Access)           │
└─────────────────────────────────────────────────────┘
```

### Network Flow

1. **Client Request** → `localhost:8080` (exposed port)
2. **Docker Port Mapping** → Container port `8080`
3. **App Container** → Connects to `db:5432` (internal Docker DNS)
4. **Database Container** → Listens on port `5432`
5. **Data Persistence** → Stored in `postgres_data` volume

---

## 📊 Health Checks

The setup includes automated health checks:

- **Database Health**: Checks PostgreSQL availability every 10 seconds
- **Startup Dependency**: Application waits for database to be healthy before starting
- **Application Health**: Spring Boot Actuator endpoint at `/actuator/health`

---

## 🔐 Security Notes

⚠️ **For Production:**
- Change default database credentials in `.env`
- Use strong passwords
- Enable SSL/TLS for database connections
- Use environment-specific configurations
- Never commit `.env` files with real credentials

**Example Production Setup:**
```bash
# .env (production)
POSTGRES_USER=secure_user
POSTGRES_PASSWORD=$(openssl rand -base64 32)
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
```

---

## 📚 Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Reference](https://docs.docker.com/compose/reference/)
- [Spring Boot Docker Guide](https://spring.io/guides/topical/spring-boot-docker/)
- [PostgreSQL Docker Hub](https://hub.docker.com/_/postgres)

---

## ✨ Tips & Best Practices

✅ **Do:**
- Use `.dockerignore` to reduce build context
- Pin image versions (not `latest`)
- Use health checks for critical services
- Store sensitive data in `.env` files
- Use named volumes for persistent data

❌ **Don't:**
- Run containers as root (when possible)
- Use `COPY` without `.dockerignore`
- Mount large directories
- Leave containers running unused
- Commit `.env` files to Git

---

## 🆘 Need Help?

1. Check logs: `docker-compose logs app`
2. Verify containers: `docker-compose ps`
3. Test connectivity: `docker-compose exec app curl db:5432`
4. Read Docker documentation: https://docs.docker.com/

---

**Happy containerizing! 🚀**

*Last Updated: June 2026*
