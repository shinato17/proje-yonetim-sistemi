package com.proje.pys.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "proje_durumlari")
public class ProjeDurumu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isim", nullable = false, unique = true)
    private String isim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
