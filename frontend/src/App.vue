<!--<template>-->
<!--  <div class="container">-->
<!--    <h1>Todo List</h1>-->

<!--    &lt;!&ndash; 输入框和添加按钮 &ndash;&gt;-->
<!--    <div class="add">-->
<!--      <input-->
<!--        v-model="newTodo"-->
<!--        @keyup.enter="addTodo"-->
<!--        placeholder="输入任务..."-->
<!--      />-->
<!--      <button @click="addTodo">添加</button>-->
<!--    </div>-->

<!--    &lt;!&ndash; 任务列表 &ndash;&gt;-->
<!--    <ul class="list">-->
<!--      <TodoItem-->
<!--        v-for="todo in todos"-->
<!--        :key="todo.id"-->
<!--        :todo="todo"-->
<!--        @toggle="toggleTodo"-->
<!--        @remove="deleteTodo"-->
<!--      />-->
<!--    </ul>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { ref, onMounted } from 'vue'-->
<!--import axios from 'axios'-->
<!--import TodoItem from './components/TodoItem.vue'-->

<!--// 数据-->
<!--const newTodo = ref('')-->
<!--const todos = ref([])-->

<!--// 获取任务列表-->
<!--const fetchTodos = async () => {-->
<!--  const res = await axios.get('/api/todos')-->
<!--  todos.value = res.data-->
<!--}-->

<!--// 添加任务-->
<!--const addTodo = async () => {-->
<!--  const title = newTodo.value.trim()-->
<!--  if (!title) return-->
<!--  const res = await axios.post('/api/todos', { title, completed: false })-->
<!--  todos.value.push(res.data)-->
<!--  newTodo.value = ''-->
<!--}-->

<!--// 切换完成状态-->
<!--const toggleTodo = async (todo) => {-->
<!--  const updated = { ...todo, completed: !todo.completed }-->
<!--  await axios.put(`/api/todos/${todo.id}`, updated)-->
<!--  todo.completed = updated.completed-->
<!--}-->

<!--// 删除任务-->
<!--const deleteTodo = async (id) => {-->
<!--  await axios.delete(`/api/todos/${id}`)-->
<!--  todos.value = todos.value.filter(t => t.id !== id)-->
<!--}-->

<!--// 页面加载时获取数据-->
<!--onMounted(fetchTodos)-->
<!--</script>-->

<!--<style>-->
<!--.container {-->
<!--  max-width: 640px;-->
<!--  margin: 40px auto;-->
<!--  font-family: system-ui, sans-serif;-->
<!--}-->
<!--.add {-->
<!--  display: flex;-->
<!--  gap: 8px;-->
<!--  margin-bottom: 16px;-->
<!--}-->
<!--.add input {-->
<!--  flex: 1;-->
<!--  padding: 8px;-->
<!--}-->
<!--.add button {-->
<!--  padding: 8px 12px;-->
<!--}-->
<!--.list {-->
<!--  list-style: none;-->
<!--  padding: 0;-->
<!--  margin: 0;-->
<!--}-->
<!--</style>-->

<template>
  <div id="app">
    <TodoPage />
  </div>
</template>

<script setup>
import TodoPage from './views/TodoPage.vue'
</script>

<style>
#app {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f5f7fa;
  min-height: 100vh;
  padding: 20px;
}
</style>

