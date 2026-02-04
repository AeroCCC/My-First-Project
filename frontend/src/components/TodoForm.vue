<template>
  <el-form @submit.prevent="handleSubmit" label-width="100px">
    <el-form-item label="任务标题">
      <el-input v-model="todo.title" placeholder="请输入任务标题"></el-input>
    </el-form-item>

    <el-form-item label="分类">
      <el-select v-model="todo.category" placeholder="选择分类">
        <el-option label="工作" value="work"></el-option>
        <el-option label="学习" value="study"></el-option>
        <el-option label="生活" value="life"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="优先级">
      <el-radio-group v-model="todo.priority">
        <el-radio label="HIGH">高</el-radio>
        <el-radio label="MEDIUM">中</el-radio>
        <el-radio label="LOW">低</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="截止日期">
      <el-date-picker v-model="todo.dueDate" type="date" placeholder="选择日期"></el-date-picker>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="handleSubmit">添加任务</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue'

const todo = reactive({
  title: '',
  category: '',
  priority: 'MEDIUM',
  dueDate: null
})

const handleSubmit = () => {
  console.log('提交任务:', todo)
  // TODO: 调用后端 API 保存任务
}
</script>
