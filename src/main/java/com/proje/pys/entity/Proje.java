package com.proje.pys.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projeler")
public class Proje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isim", nullable = false)
    private String isim;

    @Column(name = "aciklama")
    private String aciklama;

    @ManyToOne
    @JoinColumn(name = "durum_id", nullable = true)
    private ProjeDurumu durum;

    @Column(name = "olusturma_tarihi", nullable = false, updatable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();
    }

    // === GETTER'lar ===
    public Long getId() {
        return id;
    }

    public String getIsim() {
        return isim;
    }

    public String getAciklama() {
        return aciklama;
    }

    public ProjeDurumu getDurum() {
        return durum;
    }

    public LocalDateTime getOlusturmaTarihi() {
        return olusturmaTarihi;
    }

    // === SETTER'lar ===
    public void setId(Long id) {
        this.id = id;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public void setDurum(ProjeDurumu durum) {
        this.durum = durum;
    }

    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi) {
        this.olusturmaTarihi = olusturmaTarihi;
    }
}
