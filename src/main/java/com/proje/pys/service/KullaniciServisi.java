package com.proje.pys.service;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.entity.ProjeKullanici;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.ProjeKullaniciRepository;
import com.proje.pys.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class KullaniciServisi {
    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;
    private final ProjeKullaniciRepository projeKullaniciRepository;

    public KullaniciServisi(KullaniciRepository kullaniciRepository,
                            RolRepository rolRepository,
                            ProjeKullaniciRepository projeKullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
        this.projeKullaniciRepository = projeKullaniciRepository;
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

            List<ProjeKullanici> iliskiler = projeKullaniciRepository.findByKullaniciId(id);
            for (ProjeKullanici iliski : iliskiler) {
                iliski.setRol(yeniRol);
                projeKullaniciRepository.save(iliski);
            }
        }

        return kullaniciRepository.save(mevcutKullanici);
    }

    public void kullaniciSil(Long id) {
        projeKullaniciRepository.findByKullaniciId(id)
                .forEach(iliski -> projeKullaniciRepository.deleteById(iliski.getId()));
        kullaniciRepository.deleteById(id);
    }
}
