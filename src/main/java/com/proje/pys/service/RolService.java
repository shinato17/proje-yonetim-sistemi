package com.proje.pys.service;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    private final RolRepository rolRepository;
    private final KullaniciRepository kullaniciRepository;

    public RolService(RolRepository rolRepository, KullaniciRepository kullaniciRepository) {
        this.rolRepository = rolRepository;
        this.kullaniciRepository = kullaniciRepository;
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

        if (rol.getIsim().equalsIgnoreCase("Yönetici") || rol.getIsim().equalsIgnoreCase("Çalışan")) {
            throw new RuntimeException("Bu rol güncellenemez.");
        }

        rol.setIsim(rolDetaylari.getIsim());
        return rolRepository.save(rol);
    }

    public void rolSil(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + id));

        if (rol.getIsim().equalsIgnoreCase("Yönetici") || rol.getIsim().equalsIgnoreCase("Çalışan")) {
            throw new RuntimeException("Bu rol silinemez.");
        }

        List<Kullanici> bagliKullanicilar = kullaniciRepository.findAll().stream()
                .filter(k -> k.getRol().getId().equals(id))
                .toList();

        if (!bagliKullanicilar.isEmpty()) {
            String isimler = bagliKullanicilar.stream()
                    .map(Kullanici::getIsim)
                    .toList()
                    .toString();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bu rolde " + isimler + " kullanıcı(ları) atalı. Rol silinemiyor.");
        }

        rolRepository.deleteById(id);
    }
}
