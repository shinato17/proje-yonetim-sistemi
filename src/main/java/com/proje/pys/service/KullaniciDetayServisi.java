package com.proje.pys.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Rol;
import com.proje.pys.repository.KullaniciRepository;
import com.proje.pys.repository.RolRepository;

@Service
public class KullaniciDetayServisi implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public KullaniciDetayServisi(KullaniciRepository kullaniciRepository,
                                 RolRepository rolRepository,
                                 PasswordEncoder passwordEncoder) {
        this.kullaniciRepository = kullaniciRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String eposta) throws UsernameNotFoundException {
        Kullanici kullanici = kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + eposta));

        String rolAdi = "ROLE_" + kullanici.getRol().getIsim();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(rolAdi));

        return new User(kullanici.getEposta(), kullanici.getSifre(), authorities);
    }

    public Kullanici kullaniciOlustur(KullaniciDto kullaniciDto) {
        Kullanici kullanici = new Kullanici();
        kullanici.setEposta(kullaniciDto.getEposta());
        kullanici.setSifre(passwordEncoder.encode(kullaniciDto.getSifre()));
        kullanici.setIsim(kullaniciDto.getIsim());

        Rol rol = rolRepository.findById(kullaniciDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + kullaniciDto.getRolId()));
        kullanici.setRol(rol);

        return kullaniciRepository.save(kullanici);
    }
}
