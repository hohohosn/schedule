package com.example.schedulepj.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ScheduleDto {

    // 일정 생성 요청 DTO
    public static class CreateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @Size(max = 1000, message = "내용은 1000자 이하여야 합니다.")
        private String content;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    // 일정 수정 요청 DTO
    public static class UpdateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        private String title;

        @Size(max = 1000, message = "내용은 1000자 이하여야 합니다.")
        private String content;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    // 일정 응답 DTO
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String username;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // 생성자
        public Response() {}

        public Response(Long id, String title, String content, String username,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}