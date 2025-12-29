# 🚀 Deployment Guide

This guide covers multiple deployment options for the Task Management Application.

## Table of Contents
- [Heroku Deployment](#heroku-deployment)
- [Railway Deployment](#railway-deployment)
- [AWS Deployment](#aws-deployment)
- [Render Deployment](#render-deployment)

---

## 🎯 Heroku Deployment

### Prerequisites
- Heroku account
- Heroku CLI installed
- Git repository

### Backend Deployment

1. **Create Heroku apps**
```bash
# Create backend app
heroku create task-management-backend

# Add MySQL addon
heroku addons:create jawsdb:kitefin -a task-management-backend
```

2. **Set environment variables**
```bash
# Get database URL
heroku config:get JAWSDB_URL -a task-management-backend

# Set JWT secret
heroku config:set JWT_SECRET=VGhpc0lzQVZlcnlTZWN1cmVTZWNyZXRLZXk= -a task-management-backend

# Set CORS origins
heroku config:set CORS_ORIGINS=https://your-frontend-url.herokuapp.com -a task-management-backend
```

3. **Create Procfile in backend directory**
```bash
web: java -Dserver.port=$PORT -jar target/task-management-1.0.0.jar
```

4. **Deploy backend**
```bash
cd backend
git init
heroku git:remote -a task-management-backend
git add .
git commit -m "Deploy backend"
git push heroku main
```

### Frontend Deployment

1. **Create frontend app**
```bash
heroku create task-management-frontend --buildpack mars/create-react-app
```

2. **Set environment variables**
```bash
heroku config:set VITE_API_URL=https://task-management-backend.herokuapp.com/api -a task-management-frontend
```

3. **Deploy frontend**
```bash
cd frontend
git init
heroku git:remote -a task-management-frontend
git add .
git commit -m "Deploy frontend"
git push heroku main
```

---

## 🚂 Railway Deployment

### Backend Deployment

1. **Create new project on Railway**
    - Go to [railway.app](https://railway.app)
    - Click "New Project"
    - Choose "Deploy from GitHub repo"

2. **Add MySQL Database**
    - Click "New" → "Database" → "Add MySQL"

3. **Configure environment variables**
```
DB_URL=mysql://user:password@host:port/database
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=VGhpc0lzQVZlcnlTZWN1cmVTZWNyZXRLZXk=
JWT_EXPIRATION=1209600000
CORS_ORIGINS=https://your-frontend-url.railway.app
```

4. **Add build command** (Settings → Build Command)
```bash
cd backend && mvn clean package -DskipTests
```

5. **Add start command** (Settings → Start Command)
```bash
cd backend && java -jar target/task-management-1.0.0.jar
```

### Frontend Deployment

1. **Create new service**
    - Add another service to your project
    - Connect your GitHub repo

2. **Configure environment variables**
```
VITE_API_URL=https://your-backend-url.railway.app/api
```

3. **Build and start commands will be auto-detected from package.json**

---

## ☁️ AWS Deployment

### Option 1: AWS Elastic Beanstalk

#### Backend

1. **Install EB CLI**
```bash
pip install awsebcli
```

2. **Initialize EB application**
```bash
cd backend
eb init -p java-17 task-management-backend
```

3. **Create environment**
```bash
eb create task-management-env
```

4. **Configure RDS**
```bash
# Add RDS MySQL database
eb create database
```

5. **Set environment variables**
```bash
eb setenv JWT_SECRET=VGhpc0lzQVZlcnlTZWN1cmVTZWNyZXRLZXk=
eb setenv CORS_ORIGINS=https://your-frontend-url
```

6. **Deploy**
```bash
mvn clean package
eb deploy
```

#### Frontend

1. **Build the frontend**
```bash
cd frontend
npm run build
```

2. **Deploy to S3 + CloudFront**
```bash
aws s3 sync dist/ s3://your-bucket-name
aws cloudfront create-invalidation --distribution-id YOUR_DIST_ID --paths "/*"
```

### Option 2: AWS ECS (Docker)

1. **Push images to ECR**
```bash
# Authenticate
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin your-account-id.dkr.ecr.us-east-1.amazonaws.com

# Build and push backend
cd backend
docker build -t task-backend .
docker tag task-backend:latest your-account-id.dkr.ecr.us-east-1.amazonaws.com/task-backend:latest
docker push your-account-id.dkr.ecr.us-east-1.amazonaws.com/task-backend:latest

# Build and push frontend
cd frontend
docker build -t task-frontend .
docker tag task-frontend:latest your-account-id.dkr.ecr.us-east-1.amazonaws.com/task-frontend:latest
docker push your-account-id.dkr.ecr.us-east-1.amazonaws.com/task-frontend:latest
```

2. **Create ECS cluster and service**
```bash
aws ecs create-cluster --cluster-name task-management-cluster
```

3. **Create RDS MySQL instance**
```bash
aws rds create-db-instance \
  --db-instance-identifier task-db \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --master-username admin \
  --master-user-password YourPassword123
```

4. **Create task definitions and services** (use AWS Console or CloudFormation)

---

## 🎨 Render Deployment

### Backend Deployment

1. **Create Web Service**
    - Go to [render.com](https://render.com)
    - New → Web Service
    - Connect your GitHub repo
    - Select `backend` directory

2. **Configure settings**
```
Name: task-management-backend
Build Command: mvn clean package -DskipTests
Start Command: java -jar target/task-management-1.0.0.jar
```

3. **Add MySQL Database**
    - New → PostgreSQL (or use external MySQL)

4. **Set environment variables**
```
DB_URL=jdbc:mysql://host:port/database
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=VGhpc0lzQVZlcnlTZWN1cmVTZWNyZXRLZXk=
CORS_ORIGINS=https://your-frontend-url.onrender.com
```

### Frontend Deployment

1. **Create Static Site**
    - New → Static Site
    - Connect your GitHub repo
    - Select `frontend` directory

2. **Configure settings**
```
Build Command: npm run build
Publish Directory: dist
```

3. **Add environment variable**
```
VITE_API_URL=https://task-management-backend.onrender.com/api
```

---

## 🔧 Post-Deployment Checklist

- [ ] Test user registration
- [ ] Test user login
- [ ] Test task CRUD operations
- [ ] Verify JWT token expiration
- [ ] Check CORS configuration
- [ ] Test pagination
- [ ] Verify error handling
- [ ] Check mobile responsiveness
- [ ] Test with different browsers
- [ ] Verify SSL certificate
- [ ] Set up monitoring (optional)
- [ ] Configure backup strategy
- [ ] Update README with live URLs

---

## 📊 Monitoring & Maintenance

### Logging

**Backend Logs**
```bash
# Heroku
heroku logs --tail -a task-management-backend

# Railway
railway logs

# AWS
aws logs tail /aws/elasticbeanstalk/task-management-env/var/log/web.stdout.log --follow
```

### Database Backup

**Heroku JawsDB**
```bash
heroku config:get JAWSDB_URL -a task-management-backend
mysqldump -h host -u user -p database > backup.sql
```

**AWS RDS**
```bash
aws rds create-db-snapshot \
  --db-instance-identifier task-db \
  --db-snapshot-identifier task-db-snapshot-$(date +%Y%m%d)
```

---

## 🆘 Troubleshooting

### Common Issues

**Issue**: Application crashes on startup
```bash
Solution: Check logs for database connection errors
Verify environment variables are set correctly
```

**Issue**: CORS errors in frontend
```bash
Solution: Update CORS_ORIGINS environment variable
Ensure frontend URL is included
```

**Issue**: Database connection timeout
```bash
Solution: Check security group rules
Verify database is publicly accessible (if needed)
Update database URL format
```

**Issue**: JWT token issues
```bash
Solution: Verify JWT_SECRET is base64 encoded
Check token expiration time
Ensure consistent secret across deployments
```

---

## 💰 Cost Estimation

### Free Tier Options

| Platform | Backend | Database | Frontend | Total |
|----------|---------|----------|----------|-------|
| Heroku | Free (Eco) | $5/mo | Free | $5/mo |
| Railway | $5/mo | $5/mo | $5/mo | $15/mo |
| Render | Free | Free | Free | Free |
| AWS | Free tier | Free tier | Free tier | ~$0-5/mo |

### Production Ready

| Platform | Backend | Database | Frontend | Total |
|----------|---------|----------|----------|-------|
| Heroku | $25/mo | $15/mo | $25/mo | $65/mo |
| Railway | $20/mo | $15/mo | $10/mo | $45/mo |
| AWS | $30/mo | $15/mo | $5/mo | $50/mo |

---

## 🔐 Security Best Practices

1. **Use environment variables** for all sensitive data
2. **Enable HTTPS** on all endpoints
3. **Set strong JWT secret** (minimum 256 bits)
4. **Configure proper CORS** origins
5. **Use database connection pooling**
6. **Enable rate limiting** (optional)
7. **Regular security updates**
8. **Database backups**
9. **Monitor application logs**
10. **Use secrets management** (AWS Secrets Manager, etc.)

---

## 📱 Example Live URLs

After deployment, your application will be accessible at:

```
Frontend: https://task-management-frontend.onrender.com
Backend API: https://task-management-backend.onrender.com/api
Swagger UI: https://task-management-backend.onrender.com/swagger-ui.html
```

Update these URLs in your README.md!

---

**Need help?** Open an issue on GitHub or contact support.