package org.hei.federationagribackend.dto;

import java.util.List;

public class CreateCollectivityDTO {
    private String location;
    private List<String> members;
    private Boolean federationApproval;
    private CreateCollectivityStructureDTO structure;
}
