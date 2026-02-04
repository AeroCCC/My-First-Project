<template>
  <div class="todo-page">
    <!-- 添加任务表单 -->
    <el-card class="form-card" shadow="hover">
      <h2>添加任务</h2>
      <el-form @submit.prevent="addTodo" label-width="100px">
        <el-form-item label="任务标题">
          <el-input v-model="newTodo.title" placeholder="请输入任务标题"></el-input>
        </el-form-item>

        <el-form-item label="分类">
          <el-select v-model="newTodo.category" placeholder="选择分类">
            <el-option label="工作" value="work"></el-option>
            <el-option label="学习" value="study"></el-option>
            <el-option label="生活" value="life"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="优先级">
          <el-radio-group v-model="newTodo.priority">
            <el-radio label="HIGH">高</el-radio>
            <el-radio label="MEDIUM">中</el-radio>
            <el-radio label="LOW">低</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="截止日期">
          <el-date-picker v-model="newTodo.dueDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addTodo">添加任务</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 任务列表 -->
    <el-card class="list-card" shadow="hover">
      <h2>任务列表</h2>
      <el-table :data="todos" style="width: 100%">
        <el-table-column prop="title" label="任务"></el-table-column>
        <el-table-column prop="category" label="分类"></el-table-column>
        <el-table-column prop="priority" label="优先级">
          <template #default="scope">
            <el-tag :type="scope.row.priority === 'HIGH' ? 'danger' : scope.row.priority === 'MEDIUM' ? 'warning' : 'success'">
              {{ scope.row.priority }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dueDate" label="截止日期"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="success" @click="completeTodo(scope.$index)">完成</el-button>
            <el-button size="small" type="danger" @click="deleteTodo(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { ElNotification } from 'element-plus'

// 模拟任务列表（后续可替换为后端 API）
const todos = ref([
  { title: '写报告', category: '工作', priority: 'HIGH', dueDate: '2025-11-30' },
  { title: '复习算法', category: '学习', priority: 'MEDIUM', dueDate: '2025-12-01' }
])

// 新任务表单
const newTodo = ref({
  title: '',
  category: '',
  priority: 'MEDIUM',
  dueDate: null
})

// 添加任务
const addTodo = () => {
  if (!newTodo.value.title) return
  todos.value.push({ ...newTodo.value })
  // TODO: 调用后端 API 保存任务
  newTodo.value.title = ''
  newTodo.value.category = ''
  newTodo.value.priority = 'MEDIUM'
  newTodo.value.dueDate = null
}

// 完成任务
const completeTodo = (index) => {
  todos.value.splice(index, 1)
  // TODO: 调用后端 API 更新任务状态
}

// 删除任务
const deleteTodo = (index) => {
  todos.value.splice(index, 1)
  // TODO: 调用后端 API 删除任务
}

// 优先级颜色
const priorityColor = (priority) => {
  if (priority === 'HIGH') return 'danger'
  if (priority === 'MEDIUM') return 'warning'
  return 'success'
}

//WebSocket 接收提醒消息
onMounted(() => {
  const socket = new SockJS('http://localhost:8080/ws')
  const stompClient = Stomp.over(socket)

  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/reminders', (message) => {
      ElNotification({
        title: '任务提醒',
        message: message.body, // 后端推送的完整提醒内容
        type: 'warning'
      })
    })
  })
})
</script>

<style scoped>
.todo-page {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 30px;
}
.form-card, .list-card {
  padding: 20px;
}
</style>

<!--<template>-->
<!--  <div class="todo-container">-->
<!--    <h1>Todo List</h1>-->

<!--    &lt;!&ndash; 添加任务 &ndash;&gt;-->
<!--    <div class="add-task">-->
<!--      <input v-model="newTask.title" placeholder="任务标题" />-->
<!--      <select v-model="newTask.priority">-->
<!--        <option value="HIGH">高</option>-->
<!--        <option value="MEDIUM">中</option>-->
<!--        <option value="LOW">低</option>-->
<!--      </select>-->
<!--      <input type="date" v-model="newTask.deadline" />-->
<!--      <button @click="addTask">添加</button>-->
<!--    </div>-->

<!--    &lt;!&ndash; 任务列表 &ndash;&gt;-->
<!--    <table class="task-table">-->
<!--      <thead>-->
<!--      <tr>-->
<!--        <th>任务</th>-->
<!--        <th>优先级</th>-->
<!--        <th>截止日期</th>-->
<!--        <th>操作</th>-->
<!--      </tr>-->
<!--      </thead>-->
<!--      <tbody>-->
<!--      <tr v-for="task in tasks" :key="task.id">-->
<!--        <td>{{ task.title }}</td>-->
<!--        <td>{{ task.priority }}</td>-->
<!--        <td>{{ task.deadline }}</td>-->
<!--        <td>-->
<!--          <button @click="deleteTask(task.id)">删除</button>-->
<!--        </td>-->
<!--      </tr>-->
<!--      </tbody>-->
<!--    </table>-->

<!--    &lt;!&ndash; WebSocket 消息 &ndash;&gt;-->
<!--    <div class="ws-message" v-if="wsMessage">-->
<!--      🔔 {{ wsMessage }}-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { ref, onMounted, onBeforeUnmount } from "vue";-->
<!--import axios from "axios";-->
<!--import SockJS from "sockjs-client";-->
<!--import Stomp from "stompjs";-->

<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--// 数据-->
<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--const tasks = ref([]);-->
<!--const wsMessage = ref("");-->

<!--const newTask = ref({-->
<!--  title: "",-->
<!--  priority: "MEDIUM",-->
<!--  deadline: "",-->
<!--});-->

<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--// 加载任务列表-->
<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--const loadTasks = async () => {-->
<!--  const res = await axios.get("http://localhost:8080/api/todo/list");-->
<!--  tasks.value = res.data;-->
<!--};-->

<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--// 添加任务-->
<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--const addTask = async () => {-->
<!--  if (!newTask.value.title) return;-->

<!--  await axios.post("http://localhost:8080/api/todo/add", newTask.value);-->
<!--  await loadTasks();-->

<!--  newTask.value = { title: "", priority: "MEDIUM", deadline: "" };-->
<!--};-->

<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--// 删除任务-->
<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--const deleteTask = async (id) => {-->
<!--  await axios.delete(`http://localhost:8080/api/todo/delete/${id}`);-->
<!--  await loadTasks();-->
<!--};-->

<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--// WebSocket（SockJS + STOMP）-->
<!--// -&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
<!--let stompClient = null;-->

<!--const connectWebSocket = () => {-->
<!--  const socket = new SockJS("http://localhost:8080/ws");-->
<!--  stompClient = Stomp.over(socket);-->

<!--  stompClient.connect({}, () => {-->
<!--    console.log("WebSocket 连接成功");-->

<!--    // 订阅后端推送的提醒消息-->
<!--    stompClient.subscribe("/topic/notify", (msg) => {-->
<!--      wsMessage.value = msg.body;-->

<!--      // 3 秒后自动消失-->
<!--      setTimeout(() => (wsMessage.value = ""), 3000);-->
<!--    });-->
<!--  });-->
<!--};-->

<!--onMounted(() => {-->
<!--  loadTasks();-->
<!--  connectWebSocket();-->
<!--});-->

<!--onBeforeUnmount(() => {-->
<!--  if (stompClient) stompClient.disconnect();-->
<!--});-->
<!--</script>-->

<!--<style scoped>-->
<!--.todo-container {-->
<!--  width: 600px;-->
<!--  margin: 40px auto;-->
<!--  font-family: Arial, sans-serif;-->
<!--}-->

<!--.add-task {-->
<!--  display: flex;-->
<!--  gap: 10px;-->
<!--  margin-bottom: 20px;-->
<!--}-->

<!--.task-table {-->
<!--  width: 100%;-->
<!--  border-collapse: collapse;-->
<!--}-->

<!--.task-table th,-->
<!--.task-table td {-->
<!--  border: 1px solid #ddd;-->
<!--  padding: 8px;-->
<!--}-->

<!--.ws-message {-->
<!--  margin-top: 20px;-->
<!--  padding: 10px;-->
<!--  background: #fff3cd;-->
<!--  border: 1px solid #ffeeba;-->
<!--  color: #856404;-->
<!--}-->
<!--</style>-->

