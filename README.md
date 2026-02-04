🚀 全栈 TodoList 管理系统

这是一个基于 **前后端分离** 架构开发的待办事项管理系统。项目集成了前端交互、后端 API 以及一键启动脚本，旨在提供流畅的任务管理体验。

🛠 技术栈

### 前端 (Frontend)
* **框架**: Vue 3 (Vite)
* **UI 组件库**: Element Plus
* **通信**: Axios

### 后端 (Backend)
* **环境**: Node.js / Java (Maven)
* **架构**: RESTful API 规范

---

✨ 项目亮点

* **前后端分离**: 结构清晰，前端负责展现，后端负责业务逻辑。
* **一键启动**: 根目录包含 `start-todo.bat` 脚本，支持在 Windows 环境下一键运行。
* **工程化规范**: 包含完整的 `.gitignore` 配置，自动忽略冗余的 `node_modules` 和 `target` 编译文件。

---

📂 目录结构说明

```text
.
├── frontend/           # 前端 Vue 项目源码
├── backend/            # 后端项目源码
├── start-todo.bat      # Windows 一键启动脚本
└── .gitignore


🚀 快速开始
1. 环境准备
确保你的电脑已安装：

Node.js (建议 v16+)

JDK/Maven (如果后端是 Java)

2. 运行项目
在项目根目录下，直接双击运行：

Bash
./start-todo.bat
或者手动进入目录启动：

Bash
# 启动前端
cd frontend && npm install && npm run dev

# 启动后端
cd backend && npm install && npm start


