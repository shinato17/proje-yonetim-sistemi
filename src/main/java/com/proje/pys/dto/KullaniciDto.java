package com.proje.pys.dto;

public class KullaniciDto {
    private String isim;
    private String eposta;
    private String sifre;
    private Long rolId;

    public String getIsim() { return isim; }
    public void setIsim(String isim) { this.isim = isim; }
    public String getEposta() { return eposta; }
    public void setEposta(String eposta) { this.eposta = eposta; }
    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }
    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
}