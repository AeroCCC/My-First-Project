package com.example.todo.config;

import com.example.todo.entity.Todo;
import com.example.todo.service.ReminderPublisher;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {



    //注入 TodoRepository，用于查询任务详情
    private final TodoRepository repo;

    //注入 ReminderPublisher，用于推送提醒消息
    private final ReminderPublisher publisher;

    //构造函数注入，确保 Spring 能正确装配依赖
    public RedisKeyExpirationListener(
            RedisMessageListenerContainer listenerContainer,
            TodoRepository repo,
            ReminderPublisher publisher
    ) {
        super(listenerContainer);
        this.repo = repo;
        this.publisher = publisher;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if (expiredKey.startsWith("reminder:")) {
            String[] parts = expiredKey.split(":");
            String type = parts[1]; // reminder 或 advance
            String taskId = parts[2];

            Todo todo = repo.findById(Long.valueOf(taskId)).orElse(null);
            if (todo != null) {
                if ("advance".equals(type)) {
                    publisher.sendReminder("任务 " + todo.getTitle() + " 将在30分钟后截止：" + todo.getDueDate());
                } else {
                    publisher.sendReminder("任务 " + todo.getTitle() + " 已到截止日期：" + todo.getDueDate());
                }
            }
        }
    }

}

