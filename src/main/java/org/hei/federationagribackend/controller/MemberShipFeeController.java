package org.hei.federationagribackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hei.federationagribackend.dto.MemberShipFeeDTO;
import org.hei.federationagribackend.security.ApiKeyValidator;
import org.hei.federationagribackend.service.MemberShipFeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class MemberShipFeeController {

    private final MemberShipFeeService service;
    private final ApiKeyValidator apiKeyValidator;
    public MemberShipFeeController(MemberShipFeeService service, ApiKeyValidator apiKeyValidator) {
        this.service = service;
        this.apiKeyValidator = apiKeyValidator;
    }

    @PostMapping("/{id}/membershipFees")
    public List<MemberShipFeeDTO> create(
            @PathVariable String id,
            @RequestBody List<MemberShipFeeDTO> dtos,
            HttpServletRequest request
    ) {
        apiKeyValidator.validate(request);
        return service.create(id, dtos);
    }

    @GetMapping("/{id}/membershipFees")
    public List<MemberShipFeeDTO> getAll(@PathVariable String id, HttpServletRequest request) {
        apiKeyValidator.validate(request);
        return service.getAll(id);
    }
}
