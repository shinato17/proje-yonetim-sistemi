package com.proje.pys.controller;

import com.proje.pys.dto.ProjeResponse;
import com.proje.pys.entity.Proje;
import com.proje.pys.service.ProjeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projeler")
public class ProjeController {
    private final ProjeService projeService;

    public ProjeController(ProjeService projeService) {
        this.projeService = projeService;
    }

    @GetMapping
    public List<ProjeResponse> kullaniciyaGoreProjeler(Authentication authentication) {
        String eposta = authentication.getName();
        List<Proje> projeler = projeService.kullaniciyaGoreProjeleriGetir(eposta);

        return projeler.stream()
                .map(ProjeResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public Proje projeEkle(@RequestBody Proje proje) {
        return projeService.projeOlustur(proje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> projeSil(@PathVariable Long id) {
        projeService.projeSil(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proje> projeGuncelle(@PathVariable Long id, @RequestBody Proje proje) {
        if (!projeService.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 döndür
        }

        proje.setId(id); // ID'yi path'ten al
        Proje guncellenmis = projeService.projeOlustur(proje); // save işlemi
        return ResponseEntity.ok(guncellenmis);
    }
}
