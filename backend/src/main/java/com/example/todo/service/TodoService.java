package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;
    private final RedisTemplate<String, Object> redis;

    private static final String CACHE_KEY = "todos";

    public TodoService(TodoRepository repo, RedisTemplate<String, Object> redis) {
        this.repo = repo;
        this.redis = redis;
    }

    // 获取所有任务（优先从 Redis 缓存中读取）
    public List<Todo> findAll() {
        List<Todo> cached = (List<Todo>) redis.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return cached;
        }

        List<Todo> todos = repo.findAll();
        redis.opsForValue().set(CACHE_KEY, todos, Duration.ofMinutes(10)); // 缓存 10 分钟
        return todos;
    }

    // 新增或更新任务（保存后清除缓存）
    public Todo save(Todo todo) {
        Todo saved = repo.save(todo);
        redis.delete(CACHE_KEY); // 数据变更后清除缓存

        // 如果有截止日期，设置提醒
        if (saved.getDueDate() != null) {
            long seconds = Duration.between(LocalDateTime.now(), saved.getDueDate().atStartOfDay()).getSeconds();
            if (seconds > 0) {
                String reminderValue = saved.getTitle() + "|" + saved.getDueDate();
                redis.opsForValue().set("reminder:" + saved.getId(), reminderValue, Duration.ofSeconds(seconds));

                long advanceSeconds = seconds - 1800; // 30分钟 = 1800秒
                if (advanceSeconds > 0) {
                    redis.opsForValue().set("reminder:advance:" + saved.getId(), reminderValue, Duration.ofSeconds(advanceSeconds));
                }
            }
        }



        return saved;
    }

    // 删除任务（删除后清除缓存）
    public void delete(Long id) {
        repo.deleteById(id);
        redis.delete(CACHE_KEY);
    }
}



