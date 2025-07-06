package com.proje.pys.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "projeler")
public class Proje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isim", nullable = false) //
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

    // Getter ve Setter'lar (Lombok ile otomatik olarak oluşturuluyor)
    // Ancak setId metodunu manuel olarak ekleyebilirsiniz
    public void setId(Long id) {
        this.id = id;
    }
}
