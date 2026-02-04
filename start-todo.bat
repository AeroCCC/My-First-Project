@echo off
title SpringBoot + Vue Todo 项目启动器

echo ================================
echo   启动 Spring Boot 后端...
echo ================================
cd backend
start mvn spring-boot:run
cd ..

echo ================================
echo   启动 Vue 前端...
echo ================================
cd frontend
start npm run dev
cd ..

echo ================================
echo   前后端已启动！
echo   后端地址: http://localhost:8080/api/todos
echo   前端地址: http://localhost:5173
echo ================================

pause
