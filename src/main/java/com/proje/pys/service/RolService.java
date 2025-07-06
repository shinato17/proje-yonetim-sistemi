package com.proje.pys.service;

import com.proje.pys.entity.Rol;
import com.proje.pys.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> tumRolleriGetir() {
        return rolRepository.findAll();
    }

    public Optional<Rol> rolGetirById(Long id) {
        return rolRepository.findById(id);
    }

    public Rol rolOlustur(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol rolGuncelle(Long id, Rol rolDetaylari) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + id));

        rol.setIsim(rolDetaylari.getIsim());
        return rolRepository.save(rol);
    }

    public void rolSil(Long id) {
        rolRepository.deleteById(id);
    }
}
