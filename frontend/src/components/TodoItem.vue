<!--<template>-->
<!--  <li class="item">-->
<!--    <label class="row">-->
<!--      <input type="checkbox" :checked="todo.completed" @change="$emit('toggle', todo)" />-->
<!--      <span :class="{ done: todo.completed }">{{ todo.title }}</span>-->
<!--    </label>-->
<!--    <button class="del" @click="$emit('remove', todo.id)">删除</button>-->
<!--  </li>-->
<!--</template>-->

<!--<script setup>-->
<!--defineProps({-->
<!--  todo: {-->
<!--    type: Object,-->
<!--    required: true-->
<!--  }-->
<!--})-->
<!--</script>-->

<!--<style>-->
<!--.item {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  justify-content: space-between;-->
<!--  padding: 8px 0;-->
<!--  border-bottom: 1px solid #eee;-->
<!--}-->
<!--.row {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  gap: 8px;-->
<!--}-->
<!--.done {-->
<!--  text-decoration: line-through;-->
<!--  color: #999;-->
<!--}-->
<!--.del {-->
<!--  background: #ff4d4f;-->
<!--  color: #fff;-->
<!--  border: none;-->
<!--  padding: 6px 10px;-->
<!--  cursor: pointer;-->
<!--}-->
<!--</style>-->

<template>
  <el-card class="todo-item" shadow="hover">
    <div class="title">{{ todo.title }}</div>
    <el-tag :type="priorityColor(todo.priority)">
      {{ todo.priority }}
    </el-tag>
    <div class="due-date">截止日期: {{ todo.dueDate }}</div>
  </el-card>
</template>

<script setup>
const props = defineProps({
  todo: Object
})

const priorityColor = (priority) => {
  if (priority === 'HIGH') return 'danger'
  if (priority === 'MEDIUM') return 'warning'
  return 'success'
}
</script>

<style scoped>
.todo-item {
  margin-bottom: 10px;
}
.title {
  font-weight: bold;
}
.due-date {
  font-size: 12px;
  color: #666;
}
</style>

