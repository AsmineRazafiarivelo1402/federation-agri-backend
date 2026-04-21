package org.hei.federationagribackend.dto;

import java.util.List;

public class CollectivityDTO {
    private String id;
    private String location;
    private Boolean federationApproval;

    private List<MemberDTO> members;
    private CollectivityStructureDTO structure;

}
