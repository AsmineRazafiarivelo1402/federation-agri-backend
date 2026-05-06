package org.hei.federationagribackend.service;

import org.hei.federationagribackend.dto.ActivityMemberAttendanceDTO;
import org.hei.federationagribackend.dto.CreateActivityMemberAttendanceDTO;
import org.hei.federationagribackend.dto.MemberDescriptionDTO;
import org.hei.federationagribackend.exception.BadRequestException;
import org.hei.federationagribackend.exception.NotFoundException;
import org.hei.federationagribackend.repository.AttendanceRepository;
import org.hei.federationagribackend.repository.CollectivityRepository;
import org.hei.federationagribackend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             CollectivityRepository collectivityRepository,
                             MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }
    public List<ActivityMemberAttendanceDTO> getAttendance(String collectivityId, String activityId) {

        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found");
        }

        if (!attendanceRepository.existsActivityById(activityId)) {
            throw new NotFoundException("Activity not found");
        }

        return attendanceRepository.findAllByActivityId(activityId);
    }



    public List<ActivityMemberAttendanceDTO> createAttendance(
            String collectivityId,
            String activityId,
            List<CreateActivityMemberAttendanceDTO> dtos) {

        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found");
        }

        if (!attendanceRepository.existsActivityById(activityId)) {
            throw new NotFoundException("Activity not found");
        }

        List<ActivityMemberAttendanceDTO> result = new ArrayList<>();

        for (CreateActivityMemberAttendanceDTO dto : dtos) {

            String existingStatus = attendanceRepository.findAttendanceStatus(activityId, dto.getMemberIdentifier());

            if (existingStatus != null) {
                if (existingStatus.equals("ATTENDED") || existingStatus.equals("MISSING")) {
                    throw new BadRequestException("Attendance already confirmed for member: " + dto.getMemberIdentifier());
                }
                attendanceRepository.update(activityId, dto.getMemberIdentifier(), dto.getAttendanceStatus());
            } else {
                String id = UUID.randomUUID().toString();
                attendanceRepository.save(id, activityId, dto.getMemberIdentifier(), dto.getAttendanceStatus());
            }

            ActivityMemberAttendanceDTO response = new ActivityMemberAttendanceDTO();
            response.setAttendanceStatus(dto.getAttendanceStatus());

            var member = memberRepository.findById(dto.getMemberIdentifier());
            if (member != null) {
                MemberDescriptionDTO memberDesc = new MemberDescriptionDTO();
                memberDesc.setId(member.getId());
                memberDesc.setFirstName(member.getFirstName());
                memberDesc.setLastName(member.getLastName());
                memberDesc.setEmail(member.getEmail());
                memberDesc.setOccupation(member.getOccupation() != null ? member.getOccupation().name() : null);
                response.setMemberDescription(memberDesc);
            }

            result.add(response);
        }

        return result;
    }
}