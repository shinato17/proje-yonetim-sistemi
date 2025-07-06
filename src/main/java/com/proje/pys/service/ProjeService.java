package com.proje.pys.service;

import com.proje.pys.entity.Proje;
import com.proje.pys.repository.ProjeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjeService {
    private final ProjeRepository projeRepository;

    public ProjeService(ProjeRepository projeRepository) {
        this.projeRepository = projeRepository;
    }

    // Tüm projeleri getir
    public List<Proje> tumProjeleriGetir() {
        return projeRepository.findAll();
    }

    // Yeni bir proje oluştur
    public Proje projeOlustur(Proje proje) {
        return projeRepository.save(proje);
    }

    // Projeyi sil
    public void projeSil(Long id) {
        projeRepository.deleteById(id);
    }

    // Projeyi güncelle
    public Proje projeGuncelle(Long id, Proje proje) {
        // Proje var mı kontrol et
        if (!projeRepository.existsById(id)) {
            throw new RuntimeException("Proje bulunamadı: " + id);
        }
        proje.setId(id); // Güncellenen proje ID'sini ayarla
        return projeRepository.save(proje); // Güncellenmiş projeyi kaydet
    }
}
