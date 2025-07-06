package com.proje.pys.service;

import com.proje.pys.entity.ProjeDurumu;
import com.proje.pys.repository.ProjeDurumuRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjeDurumuService {
    private final ProjeDurumuRepository projeDurumuRepository;

    public ProjeDurumuService(ProjeDurumuRepository projeDurumuRepository) {
        this.projeDurumuRepository = projeDurumuRepository;
    }

    public List<ProjeDurumu> tumProjeDurumlariniGetir() {
        return projeDurumuRepository.findAll();
    }

    public Optional<ProjeDurumu> projeDurumuGetirById(Long id) {
        return projeDurumuRepository.findById(id);
    }

    public ProjeDurumu projeDurumuOlustur(ProjeDurumu projeDurumu) {
        return projeDurumuRepository.save(projeDurumu);
    }

    public ProjeDurumu projeDurumuGuncelle(Long id, ProjeDurumu projeDurumuDetaylari) {
        ProjeDurumu projeDurumu = projeDurumuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje Durumu bulunamadı: " + id));

        projeDurumu.setIsim(projeDurumuDetaylari.getIsim());
        return projeDurumuRepository.save(projeDurumu);
    }

    public void projeDurumuSil(Long id) {
        projeDurumuRepository.deleteById(id);
    }
}
