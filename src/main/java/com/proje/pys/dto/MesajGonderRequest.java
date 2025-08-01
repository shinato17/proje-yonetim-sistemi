package com.proje.pys.dto;

public class MesajGonderRequest {
    private Long aliciId;
    private String icerik;

    // Getter ve Setter metodları
    public Long getAliciId() {
        return aliciId;
    }

    public void setAliciId(Long aliciId) {
        this.aliciId = aliciId;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }
}