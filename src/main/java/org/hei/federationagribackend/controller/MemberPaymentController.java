package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.CreateMemberPaymentDto;
import org.hei.federationagribackend.entity.MemberPayment;
import org.hei.federationagribackend.repository.MemberPaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/members")
public class MemberPaymentController {
    private final MemberPaymentRepository memberPaymentRepository;

    public MemberPaymentController(MemberPaymentRepository memberPaymentRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
    }

    @PostMapping("/{id}/payments")
    public List<MemberPayment> createPayments(
            @PathVariable("id") String memberId,
            @RequestBody List<CreateMemberPaymentDto> dtos) {
        System.out.println("Nombre de paiements reçus : " + dtos.size());
        List<MemberPayment> createdPayments = new ArrayList<>();

        for (CreateMemberPaymentDto dto : dtos) {
            String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);

            MemberPayment payment = new MemberPayment(paymentId, memberId, dto);

            try {
                memberPaymentRepository.insertPayment(payment);
                createdPayments.add(payment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return createdPayments;
    }
}
