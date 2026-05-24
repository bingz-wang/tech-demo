package com.wbz.activiti.config;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig {

    private static final Logger log = LoggerFactory.getLogger(ActivitiConfig.class);

    /**
     * 启动时自动部署 BPMN 流程定义，并打印已部署的流程信息
     */
    @Bean
    CommandLineRunner deployProcessDefinition(RepositoryService repositoryService) {
        return args -> {
            long count = repositoryService.createProcessDefinitionQuery().count();
            log.info("已部署的流程定义数量: {}", count);

            repositoryService.createProcessDefinitionQuery().list()
                    .forEach(pd -> log.info("流程定义 — ID: {}, Key: {}, Name: {}, Version: {}",
                            pd.getId(), pd.getKey(), pd.getName(), pd.getVersion()));
        };
    }
}
