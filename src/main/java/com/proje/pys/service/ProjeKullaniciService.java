package com.proje.pys.service;

import com.proje.pys.entity.Proje;
import com.proje.pys.entity.ProjeKullanici;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.ProjeKullaniciRepository;
import com.proje.pys.repository.ProjeRepository;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjeKullaniciService {
    private final ProjeKullaniciRepository projeKullaniciRepository;
    private final ProjeRepository projeRepository;
    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository; // Rol ataması için

    public ProjeKullaniciService(ProjeKullaniciRepository projeKullaniciRepository,
                                 ProjeRepository projeRepository,
                                 KullaniciRepository kullaniciRepository,
                                 RolRepository rolRepository) {
        this.projeKullaniciRepository = projeKullaniciRepository;
        this.projeRepository = projeRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
    }

    public List<ProjeKullanici> tumProjeKullanicilariniGetir() {
        return projeKullaniciRepository.findAll();
    }

    public Optional<ProjeKullanici> projeKullaniciGetirById(Long id) {
        return projeKullaniciRepository.findById(id);
    }

    public ProjeKullanici projeKullaniciAta(Long projeId, Long kullaniciId, Long rolId, LocalDateTime atanmaTarihi) {
        Proje proje = projeRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı: " + projeId));
        Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + kullaniciId));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + rolId));

        if (projeKullaniciRepository.existsByProjeIdAndKullaniciId(projeId, kullaniciId)) {
            throw new RuntimeException("Bu kullanıcı zaten bu projeye atanmış.");
        }

        ProjeKullanici projeKullanici = new ProjeKullanici();
        projeKullanici.setProje(proje);
        projeKullanici.setKullanici(kullanici);
        projeKullanici.setRol(rol);
        projeKullanici.setAtanmaTarihi(atanmaTarihi); // Atanma tarihi dışarıdan alınıyor

        return projeKullaniciRepository.save(projeKullanici);
    }

    public void projeKullaniciSil(Long id) {
        projeKullaniciRepository.deleteById(id);
    }

    public List<ProjeKullanici> getKullanicilarByProjeId(Long projeId) {
        return projeKullaniciRepository.findByProjeId(projeId);
    }

    public List<ProjeKullanici> getProjelerByKullaniciId(Long kullaniciId) {
        return projeKullaniciRepository.findByKullaniciId(kullaniciId);
    }
}
