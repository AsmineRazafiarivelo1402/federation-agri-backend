package org.hei.federationagribackend.controller;

import org.hei.federationagribackend.dto.MemberShipFeeDTO;
import org.hei.federationagribackend.service.MemberShipFeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class MemberShipFeeController {

    private final MemberShipFeeService service;

    public MemberShipFeeController(MemberShipFeeService service) {
        this.service = service;
    }

    @PostMapping("/{id}/membershipFees")
    public List<MemberShipFeeDTO> create(
            @PathVariable String id,
            @RequestBody List<MemberShipFeeDTO> dtos
    ) {
        return service.create(id, dtos);
    }

    @GetMapping("/{id}/membershipFees")
    public List<MemberShipFeeDTO> getAll(@PathVariable String id) {
        return service.getAll(id);
    }
}
