package com.aberturaavisoservice.aberturasinistro.model;


import jakarta.validation.constraints.NotBlank;

public class DadosInformados {
    @NotBlank(message = "O Campo claimsID é obrigatório")
    public String claimsID;
    public String name;
    @NotBlank(message = "O Campo id é obrigatório")
    public String id;
    public String policyNumber;
    public String documentNumber;

    public String getClaimsID() {
        return claimsID;
    }

    public void setClaimsID(String claimsID) {
        this.claimsID = claimsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "claimsID = " + this.claimsID
                + ", documentNumber=" + this.documentNumber
                + ", id=" + this.id
                + ", name=" + this.name
                + ", policyNumber=" + this.policyNumber;
    }
}
