package org.hei.federationagribackend.entity;

import java.util.ArrayList;
import java.util.List;

public class CollectivityEntity {
    private String id;
    private String location;
    private Boolean federationApproval;


    private List<MemberEntity> members = new ArrayList<>();

    private CollectivityStructure structure;
}
