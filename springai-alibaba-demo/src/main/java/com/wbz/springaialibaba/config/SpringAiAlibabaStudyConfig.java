package com.wbz.springaialibaba.config;

import com.wbz.springaialibaba.service.AlibabaAiStudyService;
import com.wbz.springaialibaba.service.DashScopeChatClientAlibabaAiStudyService;
import com.wbz.springaialibaba.service.LocalStubAlibabaAiStudyService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiAlibabaStudyConfig {

    @Bean
    @ConditionalOnBean(ChatClient.Builder.class)
    ChatClient alibabaLearningChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        你是一名 Spring AI Alibaba 学习教练。
                        请优先使用中文回答。
                        回答顺序优先采用：概念 -> 代码 -> 场景 -> 注意事项。
                        说明时请尽量突出它和 Spring AI 的关系。
                        """)
                .build();
    }

    @Bean
    @ConditionalOnBean(ChatClient.class)
    AlibabaAiStudyService dashScopeStudyService(ChatClient alibabaLearningChatClient) {
        return new DashScopeChatClientAlibabaAiStudyService(alibabaLearningChatClient);
    }

    @Bean
    @ConditionalOnMissingBean(AlibabaAiStudyService.class)
    AlibabaAiStudyService localStubStudyService() {
        return new LocalStubAlibabaAiStudyService();
    }
}
