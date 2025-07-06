package com.proje.pys.controller;

import com.proje.pys.entity.ProjeDurumu;
import com.proje.pys.service.ProjeDurumuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proje-durumlari")
public class ProjeDurumuController {
    private final ProjeDurumuService projeDurumuService;

    public ProjeDurumuController(ProjeDurumuService projeDurumuService) {
        this.projeDurumuService = projeDurumuService;
    }

    @GetMapping
    public List<ProjeDurumu> getAllProjeDurumlari() {
        return projeDurumuService.tumProjeDurumlariniGetir();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjeDurumu> getProjeDurumuById(@PathVariable Long id) {
        ProjeDurumu projeDurumu = projeDurumuService.projeDurumuGetirById(id)
                .orElseThrow(() -> new RuntimeException("Proje Durumu bulunamadı: " + id));
        return ResponseEntity.ok(projeDurumu);
    }

    @PostMapping
    public ProjeDurumu createProjeDurumu(@RequestBody ProjeDurumu projeDurumu) {
        return projeDurumuService.projeDurumuOlustur(projeDurumu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjeDurumu> updateProjeDurumu(@PathVariable Long id, @RequestBody ProjeDurumu projeDurumuDetaylari) {
        ProjeDurumu updatedProjeDurumu = projeDurumuService.projeDurumuGuncelle(id, projeDurumuDetaylari);
        return ResponseEntity.ok(updatedProjeDurumu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjeDurumu(@PathVariable Long id) {
        projeDurumuService.projeDurumuSil(id);
        return ResponseEntity.noContent().build();
    }
}
