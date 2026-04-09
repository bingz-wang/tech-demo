package com.wbz.springai.config;

import com.wbz.springai.service.AiStudyService;
import com.wbz.springai.service.LocalStubAiStudyService;
import com.wbz.springai.service.OpenAiChatClientAiStudyService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiStudyConfig {

    @Bean
    @ConditionalOnBean(ChatClient.Builder.class)
    ChatClient learningChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        你是一名 Spring AI 学习教练。
                        请优先使用中文回答。
                        回答顺序优先采用：概念 -> 代码 -> 场景 -> 注意事项。
                        示例尽量贴近 Spring Boot 实战。
                        """)
                .build();
    }

    @Bean
    @ConditionalOnBean(ChatClient.class)
    AiStudyService openAiStudyService(ChatClient learningChatClient) {
        return new OpenAiChatClientAiStudyService(learningChatClient);
    }

    @Bean
    @ConditionalOnMissingBean(AiStudyService.class)
    AiStudyService localStubAiStudyService() {
        return new LocalStubAiStudyService();
    }
}
