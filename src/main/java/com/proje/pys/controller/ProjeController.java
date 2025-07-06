// ProjeController.java
package com.proje.pys.controller;

import com.proje.pys.entity.Proje;
import com.proje.pys.service.ProjeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projeler")
public class ProjeController {
    private final ProjeService projeService;

    public ProjeController(ProjeService projeService) {
        this.projeService = projeService;
    }

    @GetMapping
    public List<Proje> tumProjeler(Authentication authentication) {
        String eposta = authentication.getName();
        return projeService.kullaniciyaGoreProjeleriGetir(eposta);
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
        Proje guncellenmisProje = projeService.projeGuncelle(id, proje);
        return ResponseEntity.ok(guncellenmisProje);
    }
}
