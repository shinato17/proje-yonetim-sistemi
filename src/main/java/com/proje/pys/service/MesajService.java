package com.proje.pys.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Mesaj;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.MesajRepository;

@Service
public class MesajService {

    @Autowired
    private MesajRepository mesajRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    public Mesaj mesajGonder(Long gonderenId, Long aliciId, String icerik) {
        Kullanici gonderen = kullaniciRepository.findById(gonderenId)
                .orElseThrow(() -> new IllegalArgumentException("Gönderen kullanıcı bulunamadı"));
        Kullanici alici = kullaniciRepository.findById(aliciId)
                .orElseThrow(() -> new IllegalArgumentException("Alıcı kullanıcı bulunamadı"));

        Mesaj mesaj = new Mesaj();
        mesaj.setGonderen(gonderen);
        mesaj.setAlici(alici);
        mesaj.setIcerik(icerik);
        mesaj.setTarih(LocalDateTime.now());
        mesaj.setOkundu(false);

        return mesajRepository.save(mesaj);
    }

    public List<Mesaj> gelenMesajlar(Long kullaniciId) {
        return mesajRepository.findByAliciIdOrderByTarihDesc(kullaniciId);
    }

    public List<Mesaj> gidenMesajlar(Long kullaniciId) {
        return mesajRepository.findByGonderenIdOrderByTarihDesc(kullaniciId);
    }

    public boolean kullaniciIcinYeniMesajVarMi(Long kullaniciId) {
        return mesajRepository.existsByAliciIdAndOkunduFalse(kullaniciId);
    }

    @Transactional
    public void kullaniciMesajlariniOkunduIsaretle(Long kullaniciId) {
        List<Mesaj> okunmamislar = mesajRepository.findByAliciIdAndOkunduFalse(kullaniciId);
        for (Mesaj mesaj : okunmamislar) {
            mesaj.setOkundu(true);
        }
        mesajRepository.saveAll(okunmamislar);
    }

    @Transactional
    public void sohbetSil(Long kullaniciId1, Long kullaniciId2) {
        mesajRepository.deleteSohbetBetweenUsers(kullaniciId1, kullaniciId2);
    }

    public List<Kullanici> sohbetEdilenKullanicilar(Long kullaniciId) {
        List<Mesaj> mesajlar = mesajRepository.findByGonderenIdOrAliciId(kullaniciId, kullaniciId);
        Set<Long> kullaniciIds = new HashSet<>();

        for (Mesaj m : mesajlar) {
            if (m.getGonderen().getId().equals(kullaniciId)) {
                kullaniciIds.add(m.getAlici().getId());
            } else {
                kullaniciIds.add(m.getGonderen().getId());
            }
        }

        kullaniciIds.remove(kullaniciId);

        return kullaniciIds.stream()
                .map(id -> kullaniciRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Mesaj> ikiKullaniciArasindakiMesajlar(Long kullaniciId1, Long kullaniciId2) {
        return mesajRepository.findByGonderenIdAndAliciIdOrGonderenIdAndAliciId(
                kullaniciId1, kullaniciId2, kullaniciId2, kullaniciId1);
    }

    // Kullanıcının tüm mesajlarını (gelen + giden) getirir
    public List<Mesaj> kullaniciMesajlari(Long kullaniciId) {
        return mesajRepository.findByGonderenIdOrAliciIdOrderByTarihDesc(kullaniciId, kullaniciId);
    }

    // Mesaj silme (opsiyonel)
    public boolean mesajSil(Long mesajId, Long kullaniciId) {
        return mesajRepository.findById(mesajId).map(mesaj -> {
            boolean yetkili = mesaj.getGonderen().getId().equals(kullaniciId) || mesaj.getAlici().getId().equals(kullaniciId);
            if (yetkili) {
                mesajRepository.deleteById(mesajId);
                return true;
            }
            return false;
        }).orElse(false);
    }
}
