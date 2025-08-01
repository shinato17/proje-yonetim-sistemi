package com.proje.pys.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Mesaj;
import com.proje.pys.entity.ProjeKullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.MesajRepository;
import com.proje.pys.repository.ProjeKullaniciRepository;
import com.proje.pys.repository.RolRepository;

@Service
public class KullaniciServisi {

    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;
    private final ProjeKullaniciRepository projeKullaniciRepository;
    private final MesajRepository mesajRepository;

    public KullaniciServisi(KullaniciRepository kullaniciRepository,
                            RolRepository rolRepository,
                            ProjeKullaniciRepository projeKullaniciRepository,
                            MesajRepository mesajRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
        this.projeKullaniciRepository = projeKullaniciRepository;
        this.mesajRepository = mesajRepository;
    }

    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Kullanici kullaniciGetir(Long id) {
        return kullaniciRepository.findById(id).orElse(null);
    }

    public Kullanici kullaniciOlustur(KullaniciDto kullaniciDto) {
        if (kullaniciRepository.findByEposta(kullaniciDto.getEposta()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bu e-posta adresine sahip bir kullanıcı zaten mevcut.");
        }

        Kullanici kullanici = new Kullanici();
        kullanici.setIsim(kullaniciDto.getIsim());
        kullanici.setEposta(kullaniciDto.getEposta());
        kullanici.setSifre(kullaniciDto.getSifre());

        Rol rol = rolRepository.findById(kullaniciDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
        kullanici.setRol(rol);

        return kullaniciRepository.save(kullanici);
    }

    public Kullanici kullaniciGuncelle(Long id, KullaniciDto kullaniciDto) {
        Kullanici mevcutKullanici = kullaniciRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));

        mevcutKullanici.setIsim(kullaniciDto.getIsim());
        mevcutKullanici.setEposta(kullaniciDto.getEposta());

        if (kullaniciDto.getSifre() != null && !kullaniciDto.getSifre().isEmpty()) {
            mevcutKullanici.setSifre(kullaniciDto.getSifre());
        }

        if (kullaniciDto.getRolId() != null) {
            Rol yeniRol = rolRepository.findById(kullaniciDto.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
            mevcutKullanici.setRol(yeniRol);

            // ProjeKullanici ilişkilerinde rolü de güncelle
            List<ProjeKullanici> iliskiler = projeKullaniciRepository.findByKullaniciId(id);
            for (ProjeKullanici iliski : iliskiler) {
                iliski.setRol(yeniRol);
                projeKullaniciRepository.save(iliski);
            }
        }

        return kullaniciRepository.save(mevcutKullanici);
    }

    public void kullaniciSil(Long id) {
        // Proje ilişkilerini sil
        projeKullaniciRepository.findByKullaniciId(id)
                .forEach(iliski -> projeKullaniciRepository.deleteById(iliski.getId()));

        // Mesaj ilişkilerini sil (gönderen veya alıcı olan tüm mesajlar)
        List<Mesaj> mesajlar = mesajRepository.findByGonderenIdOrAliciId(id, id);
        mesajRepository.deleteAll(mesajlar);

        // Kullanıcıyı sil
        kullaniciRepository.deleteById(id);
    }

    public Kullanici findByEposta(String eposta) {
        return kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + eposta));
    }
}
