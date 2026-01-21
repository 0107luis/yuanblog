package com.yuan.yuanblog.mq;

import com.yuan.yuanblog.common.config.RabbitMQConfig;
import com.yuan.yuanblog.es.BlogDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = RabbitMQConfig.ES_QUEUE)
public class MQMessageHandler {

    @Autowired
    private BlogDocumentService blogDocumentService;

    @RabbitHandler
    public void handler(BlogMQIndexMessage message) {
        log.info("mq 收到一条消息--------> {} ", message.toString());
        switch (message.getType()) {
            case BlogMQIndexMessage.CREATE_OR_UPDATE:
                blogDocumentService.createOrUpdateIndex(message);
                break;
            case BlogMQIndexMessage.DELETE:
                blogDocumentService.deleteIndex(message);
                break;
            default:
                log.info("没找到对应的消息类型，请注意！！-----> {}", message.toString());
                break;
        }
    }
}
