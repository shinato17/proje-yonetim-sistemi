package com.proje.pys.controller;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.service.KullaniciServisi;
import org.springframework.http.ResponseEntity;
import com.proje.pys.dto.KullaniciDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {
    private final KullaniciServisi kullaniciServisi;

    public KullaniciController(KullaniciServisi kullaniciServisi) {
        this.kullaniciServisi = kullaniciServisi;
    }

    // Tüm kullanıcıları getir
    @GetMapping
    public List<Kullanici> tumKullanicilar() {
        return kullaniciServisi.tumKullanicilariGetir();
    }

    // Belirli bir kullanıcıyı getir
    @GetMapping("/{id}")
    public Kullanici kullaniciGetir(@PathVariable Long id) {
        return kullaniciServisi.kullaniciGetir(id);
    }

    // Yeni kullanıcı oluştur
    @PostMapping
    public Kullanici kullaniciEkle(@RequestBody KullaniciDto kullaniciDto) {
        Kullanici kullanici = new Kullanici();
        kullanici.setIsim(kullaniciDto.getIsim());
        kullanici.setEposta(kullaniciDto.getEposta());
        kullanici.setSifre(kullaniciDto.getSifre());
        return kullaniciServisi.kullaniciOlustur(kullanici, kullaniciDto.getRolId());
    }

    // Kullanıcıyı güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGuncelle(@PathVariable Long id, @RequestBody Kullanici kullanici) {
        Kullanici guncellenmisKullanici = kullaniciServisi.kullaniciGuncelle(id, kullanici);
        return ResponseEntity.ok(guncellenmisKullanici);
    }

    // Kullanıcıyı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciServisi.kullaniciSil(id);
        return ResponseEntity.noContent().build();
    }
}
