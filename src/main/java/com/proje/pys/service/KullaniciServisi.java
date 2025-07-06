package com.proje.pys.service;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol; // Rol sınıfını ekleyin
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository; // RolRepository'yi ekleyin
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KullaniciServisi {
    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository; // RolRepository'yi tanımlayın

    public KullaniciServisi(KullaniciRepository kullaniciRepository, RolRepository rolRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository; // RolRepository'yi başlatın
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
    public Kullanici kullaniciOlustur(Kullanici kullanici, Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + rolId));
        kullanici.setRol(rol); // Rolü ayarlayın
        return kullaniciRepository.save(kullanici);
    }

    // Kullanıcıyı güncelle
    public Kullanici kullaniciGuncelle(Long id, Kullanici kullanici) {
        if (!kullaniciRepository.existsById(id)) {
            throw new RuntimeException("Kullanıcı bulunamadı: " + id);
        }
        kullanici.setId(id);
        return kullaniciRepository.save(kullanici);
    }

    // Kullanıcıyı sil
    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }
}
