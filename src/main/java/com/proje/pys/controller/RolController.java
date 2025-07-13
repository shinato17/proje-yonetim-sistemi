package com.proje.pys.controller;

import com.proje.pys.dto.RolDto;
import com.proje.pys.entity.Rol;
import com.proje.pys.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roller")
public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<Rol> tumRolleriGetir() {
        return rolService.tumRolleriGetir();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> rolGetir(@PathVariable Long id) {
        Rol rol = rolService.rolGetirById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + id));
        return ResponseEntity.ok(rol);
    }

    @PostMapping
    public Rol rolOlustur(@RequestBody RolDto rolDto) {
        Rol rol = new Rol();
        rol.setIsim(rolDto.getIsim());
        return rolService.rolOlustur(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> rolGuncelle(@PathVariable Long id, @RequestBody RolDto rolDto) {
        Rol rolDetaylari = new Rol();
        rolDetaylari.setIsim(rolDto.getIsim());
        Rol guncellenmisRol = rolService.rolGuncelle(id, rolDetaylari);
        return ResponseEntity.ok(guncellenmisRol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> rolSil(@PathVariable Long id) {
        rolService.rolSil(id);
        return ResponseEntity.noContent().build();
    }
}
