package com.proje.pys.service;

import com.proje.pys.entity.Proje;
import com.proje.pys.entity.ProjeKullanici;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.repository.ProjeRepository;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.ProjeKullaniciRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjeService {
    private final ProjeRepository projeRepository;
    private final KullaniciRepository kullaniciRepository;
    private final ProjeKullaniciRepository projeKullaniciRepository;

    public ProjeService(ProjeRepository projeRepository,
                        KullaniciRepository kullaniciRepository,
                        ProjeKullaniciRepository projeKullaniciRepository) {
        this.projeRepository = projeRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.projeKullaniciRepository = projeKullaniciRepository;
    }

    public List<Proje> tumProjeleriGetir() {
        return projeRepository.findAll();
    }

    public Proje projeOlustur(Proje proje) {
        return projeRepository.save(proje);
    }

    public void projeSil(Long id) {
        projeRepository.deleteById(id);
    }

    public Proje projeGuncelle(Long id, Proje guncelProje) {
        Proje mevcut = projeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı: " + id));

        mevcut.setIsim(guncelProje.getIsim());
        mevcut.setAciklama(guncelProje.getAciklama());
        mevcut.setDurum(guncelProje.getDurum()); // varsa

        return projeRepository.save(mevcut);
    }

    public List<Proje> kullaniciyaGoreProjeleriGetir(String eposta) {
        Kullanici kullanici = kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (kullanici.getRol().getIsim().equals("Yönetici")) {
            return projeRepository.findAll();
        }

        List<ProjeKullanici> iliskiler = projeKullaniciRepository.findByKullaniciId(kullanici.getId());
        return iliskiler.stream()
                .map(ProjeKullanici::getProje)
                .distinct()
                .toList();
    }

    public boolean existsById(Long id) {
        return projeRepository.existsById(id);
    }
}
