package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.ActivityMemberAttendanceDTO;
import org.hei.federationagribackend.dto.CreateActivityMemberAttendanceDTO;
import org.hei.federationagribackend.exception.BadRequestException;
import org.hei.federationagribackend.exception.NotFoundException;
import org.hei.federationagribackend.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.hei.federationagribackend.exception.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities/{activityId}/attendance")

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<Object> createAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendanceDTO> dtos) {

        try {
            List<ActivityMemberAttendanceDTO> result = attendanceService.createAttendance(id, activityId, dtos);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }
}
