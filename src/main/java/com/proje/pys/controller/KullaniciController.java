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

    @GetMapping
    public ResponseEntity<List<Kullanici>> tumKullanicilar() {
        return ResponseEntity.ok(kullaniciServisi.tumKullanicilariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGetir(@PathVariable Long id) {
        Kullanici kullanici = kullaniciServisi.kullaniciGetir(id);
        if (kullanici == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(kullanici);
    }

    @PostMapping
    public ResponseEntity<Kullanici> kullaniciEkle(@RequestBody KullaniciDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(kullaniciServisi.kullaniciOlustur(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGuncelle(@PathVariable Long id, @RequestBody KullaniciDto dto) {
        return ResponseEntity.ok(kullaniciServisi.kullaniciGuncelle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciServisi.kullaniciSil(id);
        return ResponseEntity.noContent().build();
    }
}
