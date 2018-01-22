package com.wode.factory.model;

public class EnterpriseStructure {
    private Long id;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 关联的企业id
     */
    private Long relatedEntId;

    /**
     * 类型  1:表示母企业，2:表示子企业，3:表示友盟企业
     */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getRelatedEntId() {
        return relatedEntId;
    }

    public void setRelatedEntId(Long relatedEntId) {
        this.relatedEntId = relatedEntId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}