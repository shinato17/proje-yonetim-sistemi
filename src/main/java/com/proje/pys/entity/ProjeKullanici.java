package com.proje.pys.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "proje_kullanicilari")
public class ProjeKullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Proje proje;

    @ManyToOne
    private Kullanici kullanici;

    @ManyToOne
    private Rol rol;

    private LocalDateTime atanmaTarihi;

    public ProjeKullanici() {
    }

    public ProjeKullanici(Proje proje, Kullanici kullanici, Rol rol, LocalDateTime atanmaTarihi) {
        this.proje = proje;
        this.kullanici = kullanici;
        this.rol = rol;
        this.atanmaTarihi = atanmaTarihi;
    }

    public void setProje(Proje proje) {
        this.proje = proje;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setAtanmaTarihi(LocalDateTime atanmaTarihi) {
        this.atanmaTarihi = atanmaTarihi;
    }

    public Long getId() {
        return id;
    }

    public Proje getProje() {
        return proje;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public Rol getRol() {
        return rol;
    }

    public LocalDateTime getAtanmaTarihi() {
        return atanmaTarihi;
    }
}
