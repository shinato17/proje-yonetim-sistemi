package com.proje.pys.dto;

import com.proje.pys.entity.Proje;
import java.time.LocalDateTime;

public class ProjeResponse {
    private Long id;
    private String isim;
    private String aciklama;
    private String durum;
    private LocalDateTime olusturmaTarihi;

    public ProjeResponse(Proje proje) {
        this.id = proje.getId();
        this.isim = proje.getIsim();
        this.aciklama = proje.getAciklama();
        this.durum = (proje.getDurum() != null) ? proje.getDurum().getIsim() : "Belirtilmemiş";
        this.olusturmaTarihi = proje.getOlusturmaTarihi();
    }

    public Long getId() { return id; }
    public String getIsim() { return isim; }
    public String getAciklama() { return aciklama; }
    public String getDurum() { return durum; }
    public LocalDateTime getOlusturmaTarihi() { return olusturmaTarihi; }
}
