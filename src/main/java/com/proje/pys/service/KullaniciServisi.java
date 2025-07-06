package com.proje.pys.service;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KullaniciServisi {
    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;

    public KullaniciServisi(KullaniciRepository kullaniciRepository, RolRepository rolRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
    }

    // Tüm kullanıcıları getir
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    // Belirli bir kullanıcıyı getir
    public Kullanici kullaniciGetir(Long id) {
        return kullaniciRepository.findById(id).orElse(null);
    }

    // Yeni kullanıcı oluştur
    public Kullanici kullaniciOlustur(KullaniciDto kullaniciDto) {
        Kullanici kullanici = new Kullanici();
        kullanici.setIsim(kullaniciDto.getIsim());
        kullanici.setEposta(kullaniciDto.getEposta());
        kullanici.setSifre(kullaniciDto.getSifre());

        Rol rol = rolRepository.findById(kullaniciDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
        kullanici.setRol(rol);

        return kullaniciRepository.save(kullanici);
    }

    // Kullanıcıyı güncelle
    public Kullanici kullaniciGuncelle(Long id, KullaniciDto kullaniciDto) {
        Kullanici mevcutKullanici = kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));

        mevcutKullanici.setIsim(kullaniciDto.getIsim());
        mevcutKullanici.setEposta(kullaniciDto.getEposta());

        // Şifre değişiyorsa
        if (kullaniciDto.getSifre() != null && !kullaniciDto.getSifre().isEmpty()) {
            mevcutKullanici.setSifre(kullaniciDto.getSifre());
        }

        // Rol güncelleniyorsa
        if (kullaniciDto.getRolId() != null) {
            Rol rol = rolRepository.findById(kullaniciDto.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
            mevcutKullanici.setRol(rol);
        }

        return kullaniciRepository.save(mevcutKullanici);
    }

    // Kullanıcıyı sil
    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }
}
