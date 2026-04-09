package com.wbz.springaialibaba.service;

import com.wbz.springaialibaba.model.AlibabaChatResponse;
import com.wbz.springaialibaba.model.AlibabaLearningGuideResponse;

import java.util.List;

public class LocalStubAlibabaAiStudyService implements AlibabaAiStudyService {

    @Override
    public AlibabaLearningGuideResponse learningGuide() {
        return new AlibabaLearningGuideResponse(
                "springai-alibaba-demo",
                "stub",
                "local-study",
                "当前未启用真实 DashScope 模型，这个模块先帮助你理解 Spring AI Alibaba 的基础接入方式。",
                List.of(
                        "理解 Spring AI Alibaba 和 Spring AI 的关系",
                        "认识 DashScope starter 的接入方式",
                        "学习 ChatClient 在 Alibaba 生态里的基本用法",
                        "理解如何通过 profile 在本地学习模式和真实模型模式之间切换"
                ),
                List.of(
                        "先请求 GET /api/springai-alibaba/guide 查看学习地图",
                        "再调用 POST /api/springai-alibaba/chat 提问：Spring AI Alibaba 是什么",
                        "接着调用 POST /api/springai-alibaba/compare 对比它与 Spring AI 的关系",
                        "最后配置 AI_DASHSCOPE_API_KEY 并启用 dashscope profile 切到真实模型"
                )
        );
    }

    @Override
    public AlibabaChatResponse answer(String question) {
        return new AlibabaChatResponse(
                "stub",
                "local-study",
                """
                        这是 Spring AI Alibaba 本地学习模式下的示例回答。

                        你的问题是：%s

                        你可以先抓住这几个点：
                        1. Spring AI Alibaba 建立在 Spring AI 的基础抽象之上。
                        2. 入门阶段仍然可以先从 ChatClient、Prompt、System Prompt 这些基础能力学起。
                        3. DashScope 是它常见的模型接入方式之一，比较适合在国内学习。
                        4. 真正切到模型时，再配置 API Key 和模型参数。
                        """.formatted(question)
        );
    }

    @Override
    public AlibabaChatResponse compareWithSpringAi(String question) {
        return new AlibabaChatResponse(
                "stub-compare",
                "local-study",
                """
                        问题：%s

                        对比理解：
                        1. Spring AI 更像基础抽象层，聚焦模型调用、提示词和通用 AI 开发方式。
                        2. Spring AI Alibaba 在此基础上继续强化了阿里云和 Agent 生态相关能力。
                        3. 对你这个 demo 来说，最好的学习路径是先对照 `springai-demo` 看相同点，再看这个模块新增的 Alibaba 入口。
                        """.formatted(question)
        );
    }
}
