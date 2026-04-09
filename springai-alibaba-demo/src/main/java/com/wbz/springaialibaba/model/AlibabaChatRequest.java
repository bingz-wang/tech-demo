package com.wbz.springaialibaba.model;

import jakarta.validation.constraints.NotBlank;

public record AlibabaChatRequest(
        @NotBlank(message = "question 不能为空")
        String question
) {
}
