package org.hei.federationagribackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hei.federationagribackend.dto.CreateMemberPaymentDto;
import org.hei.federationagribackend.entity.MemberPayment;
import org.hei.federationagribackend.repository.MemberPaymentRepository;
import org.hei.federationagribackend.security.ApiKeyValidator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/members")
public class MemberPaymentController {
    private final MemberPaymentRepository memberPaymentRepository;
    private final ApiKeyValidator apiKeyValidator;
    public MemberPaymentController(MemberPaymentRepository memberPaymentRepository, ApiKeyValidator apiKeyValidator) {
        this.memberPaymentRepository = memberPaymentRepository;
        this.apiKeyValidator = apiKeyValidator;
    }

    @PostMapping("/{id}/payments")
    public List<MemberPayment> createPayments(
            @PathVariable("id") String memberId,
            @RequestBody List<CreateMemberPaymentDto> dtos,
            HttpServletRequest request) {
        apiKeyValidator.validate(request);
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
