package com.example.schedulepj.service;

import com.example.schedulepj.dto.ScheduleDto;
import com.example.schedulepj.entity.Schedule;
import com.example.schedulepj.entity.User;
import com.example.schedulepj.repository.ScheduleRepository;
import com.example.schedulepj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // 일정 생성
    public ScheduleDto.Response createSchedule(Long userId, ScheduleDto.CreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return convertToResponse(savedSchedule);
    }

    // 모든 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleDto.Response> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 특정 유저의 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleDto.Response> findSchedulesByUser(Long userId) {
        return scheduleRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 일정 상세 조회
    @Transactional(readOnly = true)
    public ScheduleDto.Response findSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        return convertToResponse(schedule);
    }

    // 일정 수정
    public ScheduleDto.Response updateSchedule(Long id, Long userId, ScheduleDto.UpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("일정을 수정할 권한이 없습니다.");
        }

        schedule.setTitle(request.getTitle());
        schedule.setContent(request.getContent());

        return convertToResponse(schedule);
    }

    // 일정 삭제
    public void deleteSchedule(Long id, Long userId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("일정을 삭제할 권한이 없습니다.");
        }

        scheduleRepository.deleteById(id);
    }

    // Entity -> DTO 변환
    private ScheduleDto.Response convertToResponse(Schedule schedule) {
        return new ScheduleDto.Response(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}