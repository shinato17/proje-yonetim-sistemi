package com.proje.pys.service;

import com.proje.pys.entity.Kullanici;
import com.proje.pys.repository.KullaniciRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KullaniciDetayServisi implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;

    public KullaniciDetayServisi(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eposta) throws UsernameNotFoundException {
        Kullanici kullanici = kullaniciRepository.findByEposta(eposta)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanici bulunamadi: " + eposta));

        return User.builder()
                .username(kullanici.getEposta())
                .password(kullanici.getSifre())
                .roles(kullanici.getRol().getIsim())
                .build();
    }
}
