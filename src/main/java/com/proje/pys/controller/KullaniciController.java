package com.proje.pys.controller;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.service.KullaniciServisi;
import com.proje.pys.dto.KullaniciDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Kullanici>> tumKullanicilar() {
        List<Kullanici> kullanicilar = kullaniciServisi.tumKullanicilariGetir();
        return ResponseEntity.ok(kullanicilar);
    }

    // Belirli bir kullanıcıyı getir
    @GetMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGetir(@PathVariable Long id) {
        Kullanici kullanici = kullaniciServisi.kullaniciGetir(id);
        if (kullanici == null) {
            return ResponseEntity.notFound().build(); // Kullanıcı bulunamazsa 404 döner
        }
        return ResponseEntity.ok(kullanici);
    }

    // Yeni kullanıcı oluştur
    @PostMapping
    public ResponseEntity<Kullanici> kullaniciEkle(@RequestBody KullaniciDto kullaniciDto) {
        Kullanici yeniKullanici = kullaniciServisi.kullaniciOlustur(kullaniciDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(yeniKullanici);
    }

    // Kullanıcıyı güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGuncelle(@PathVariable Long id, @RequestBody KullaniciDto kullaniciDto) {
        Kullanici guncellenmisKullanici = kullaniciServisi.kullaniciGuncelle(id, kullaniciDto);
        return ResponseEntity.ok(guncellenmisKullanici);
    }

    // Kullanıcıyı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciServisi.kullaniciSil(id);
        return ResponseEntity.noContent().build();
    }
}
