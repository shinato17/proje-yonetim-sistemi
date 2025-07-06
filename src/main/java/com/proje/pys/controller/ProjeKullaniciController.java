package com.proje.pys.controller;

import com.proje.pys.entity.ProjeKullanici;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.service.ProjeKullaniciService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proje-kullanicilari")
public class ProjeKullaniciController {

    private final ProjeKullaniciService projeKullaniciService;

    public ProjeKullaniciController(ProjeKullaniciService projeKullaniciService) {
        this.projeKullaniciService = projeKullaniciService;
    }

    @GetMapping
    public List<ProjeKullanici> getAllProjeKullanicilari() {
        return projeKullaniciService.tumProjeKullanicilariniGetir();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjeKullanici> getProjeKullaniciById(@PathVariable Long id) {
        ProjeKullanici projeKullanici = projeKullaniciService.projeKullaniciGetirById(id)
                .orElseThrow(() -> new RuntimeException("Proje Kullanıcı ilişkisi bulunamadı: " + id));
        return ResponseEntity.ok(projeKullanici);
    }

    @PostMapping("/ata")
    public ProjeKullanici assignUserToProject(@RequestBody Map<String, Long> payload) {
        Long projeId = payload.get("projeId");
        Long kullaniciId = payload.get("kullaniciId");
        Long rolId = payload.get("rolId");

        if (projeId == null || kullaniciId == null || rolId == null) {
            throw new IllegalArgumentException("projeId, kullaniciId ve rolId boş olamaz.");
        }

        LocalDateTime atanmaTarihi = LocalDateTime.now();
        return projeKullaniciService.projeKullaniciAta(projeId, kullaniciId, rolId, atanmaTarihi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjeKullanici(@PathVariable Long id) {
        projeKullaniciService.projeKullaniciSil(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proje/{projeId}")
    public List<ProjeKullanici> getKullanicilarByProjeId(@PathVariable Long projeId) {
        return projeKullaniciService.getKullanicilarByProjeId(projeId);
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public List<ProjeKullanici> getProjelerByKullaniciId(@PathVariable Long kullaniciId, Authentication authentication) {
        Kullanici girisYapan = projeKullaniciService.getKullaniciByEposta(authentication.getName());

        if (!girisYapan.getId().equals(kullaniciId) && !girisYapan.getRol().getIsim().equals("Yönetici")) {
            throw new RuntimeException("Buna yetkiniz yok.");
        }

        return projeKullaniciService.getProjelerByKullaniciId(kullaniciId);
    }
}
