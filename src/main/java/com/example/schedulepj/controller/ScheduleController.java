package com.example.schedulepj.controller;

import com.example.schedulepj.dto.ScheduleDto;
import com.example.schedulepj.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleDto.Response> createSchedule(@RequestBody ScheduleDto.CreateRequest request,
                                                               HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            ScheduleDto.Response response = scheduleService.createSchedule(userId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 모든 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleDto.Response>> getAllSchedules() {
        List<ScheduleDto.Response> schedules = scheduleService.findAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // 내 일정 조회
    @GetMapping("/my")
    public ResponseEntity<List<ScheduleDto.Response>> getMySchedules(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        List<ScheduleDto.Response> schedules = scheduleService.findSchedulesByUser(userId);
        return ResponseEntity.ok(schedules);
    }

    // 일정 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> getSchedule(@PathVariable Long id) {
        try {
            ScheduleDto.Response schedule = scheduleService.findSchedule(id);
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> updateSchedule(@PathVariable Long id,
                                                               @RequestBody ScheduleDto.UpdateRequest request,
                                                               HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            ScheduleDto.Response schedule = scheduleService.updateSchedule(id, userId, request);
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            scheduleService.deleteSchedule(id, userId);
            return ResponseEntity.ok("일정 삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}