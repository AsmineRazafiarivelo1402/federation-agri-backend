package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CreateMemberDTO;
import org.hei.federationagribackend.dto.MemberDTO;
import org.hei.federationagribackend.exception.BadRequestException;
import org.hei.federationagribackend.exception.NotFoundException;
import org.hei.federationagribackend.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateMemberDTO> request) {

        try {
            List<MemberDTO> response = memberService.createMembers(request);
            return ResponseEntity.status(201).body(response);

        } catch (BadRequestException e) {
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
