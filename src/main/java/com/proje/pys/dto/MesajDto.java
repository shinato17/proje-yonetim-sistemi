package com.proje.pys.dto;

import java.time.LocalDateTime;

public class MesajDto {
    private Long id;
    private Long gonderenId;
    private String gonderenIsim;
    private Long aliciId;
    private String icerik;
    private LocalDateTime tarih;
    private boolean okundu;

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGonderenId() {
        return gonderenId;
    }

    public void setGonderenId(Long gonderenId) {
        this.gonderenId = gonderenId;
    }

    public String getGonderenIsim() {
        return gonderenIsim;
    }

    public void setGonderenIsim(String gonderenIsim) {
        this.gonderenIsim = gonderenIsim;
    }

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

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }

    public boolean isOkundu() {
        return okundu;
    }

    public void setOkundu(boolean okundu) {
        this.okundu = okundu;
    }
}
