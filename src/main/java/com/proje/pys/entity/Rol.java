package com.proje.pys.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roller")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isim", nullable = false, unique = true)
    private String isim;

    // Getter ve Setter'lar
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
