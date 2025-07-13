package com.proje.pys.service;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KullaniciDetayServisi implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;

    public KullaniciDetayServisi(KullaniciRepository kullaniciRepository, RolRepository rolRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eposta) throws UsernameNotFoundException {
        Kullanici kullanici = kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanici bulunamadi: " + eposta));

        return User.builder()
                .username(kullanici.getEposta())
                .password(kullanici.getSifre())
                .authorities(kullanici.getRol().getIsim())
                .build();
    }

    public Kullanici kullaniciOlustur(KullaniciDto kullaniciDto) {
        Kullanici kullanici = new Kullanici();
        kullanici.setEposta(kullaniciDto.getEposta());
        kullanici.setSifre(kullaniciDto.getSifre());

        if (kullaniciDto.getIsim() != null) {
            kullanici.setIsim(kullaniciDto.getIsim());
        }

        Rol rol = rolRepository.findById(kullaniciDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
        kullanici.setRol(rol);

        return kullaniciRepository.save(kullanici);
    }
}
