package com.yuan.yuanblog.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.yuanblog.common.config.RabbitMQConfig;
import com.yuan.yuanblog.es.BlogDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RabbitListener(queues = RabbitMQConfig.ES_QUEUE)
public class MQMessageHandler {

    @Autowired
    private BlogDocumentService blogDocumentService;

    @RabbitHandler
    public void handler(Object message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.convertValue(message, Map.class);
            
            Long blogId = ((Number) map.get("blogId")).longValue();
            String type = (String) map.get("type");
            
            BlogMQIndexMessage mqMessage = new BlogMQIndexMessage(blogId, type);
            log.info("mq 收到一条消息--------> {} ", mqMessage.toString());
            
            switch (type) {
                case BlogMQIndexMessage.CREATE_OR_UPDATE:
                    blogDocumentService.createOrUpdateIndex(mqMessage);
                    break;
                case BlogMQIndexMessage.DELETE:
                    blogDocumentService.deleteIndex(mqMessage);
                    break;
                default:
                    log.info("没找到对应的消息类型，请注意！！-----> {}", mqMessage.toString());
                    break;
            }
        } catch (Exception e) {
            log.error("处理MQ消息失败: {}", e.getMessage());
        }
    }
}
