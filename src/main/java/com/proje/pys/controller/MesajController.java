package com.proje.pys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proje.pys.dto.KullaniciDto;
import com.proje.pys.dto.MesajDto;
import com.proje.pys.dto.MesajGonderRequest;
import com.proje.pys.entity.Kullanici;
import com.proje.pys.entity.Mesaj;
import com.proje.pys.service.KullaniciServisi;
import com.proje.pys.service.MesajService;

@RestController
@RequestMapping("/api/mesajlar")
public class MesajController {

    private static final Logger logger = LoggerFactory.getLogger(MesajController.class);

    @Autowired
    private MesajService mesajServisi;

    @Autowired
    private KullaniciServisi kullaniciServisi;

    @PostMapping("/gonder")
    public ResponseEntity<String> mesajGonder(@AuthenticationPrincipal User user,
                                              @RequestBody MesajGonderRequest mesajGonderRequest) {
        try {
            logger.info("mesajGonder çağrıldı. Kullanıcı: {}, Alıcı ID: {}, İçerik: {}",
                    user != null ? user.getUsername() : "NULL",
                    mesajGonderRequest.getAliciId(),
                    mesajGonderRequest.getIcerik());

            if (user == null) {
                logger.error("Authentication user is null");
                return ResponseEntity.status(401).body("Kullanıcı kimlik doğrulaması gerekli.");
            }

            Kullanici gonderen = kullaniciServisi.findByEposta(user.getUsername());
            if (gonderen == null) {
                logger.error("Gönderen kullanıcı bulunamadı: {}", user.getUsername());
                return ResponseEntity.badRequest().body("Gönderen kullanıcı bulunamadı.");
            }

            Kullanici alici = kullaniciServisi.kullaniciGetir(mesajGonderRequest.getAliciId());
            if (alici == null) {
                logger.error("Alıcı kullanıcı bulunamadı: {}", mesajGonderRequest.getAliciId());
                return ResponseEntity.badRequest().body("Alıcı kullanıcı bulunamadı.");
            }

            mesajServisi.mesajGonder(gonderen.getId(), alici.getId(), mesajGonderRequest.getIcerik());

            logger.info("Mesaj başarıyla gönderildi. Gönderen: {}, Alıcı: {}", gonderen.getId(), alici.getId());
            return ResponseEntity.ok("Mesaj başarıyla gönderildi.");
            
        } catch (Exception e) {
            logger.error("Mesaj gönderme sırasında hata: ", e);
            return ResponseEntity.status(500).body("Sunucu hatası: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MesajDto>> kullaniciMesajlari(@AuthenticationPrincipal User user) {
        try {
            logger.info("kullaniciMesajlari çağrıldı. User: {}", user != null ? user.getUsername() : "NULL");
            
            if (user == null) {
                logger.error("Authentication user is null");
                return ResponseEntity.status(401).build();
            }

            Kullanici kullanici = kullaniciServisi.findByEposta(user.getUsername());
            if (kullanici == null) {
                logger.error("Kullanıcı bulunamadı: {}", user.getUsername());
                return ResponseEntity.badRequest().build();
            }

            List<Mesaj> mesajlar = mesajServisi.kullaniciMesajlari(kullanici.getId());

            List<MesajDto> dtolar = mesajlar.stream().map(m -> {
                MesajDto dto = new MesajDto();
                dto.setId(m.getId());
                dto.setGonderenId(m.getGonderen().getId());
                dto.setGonderenIsim(m.getGonderen().getIsim());
                dto.setAliciId(m.getAlici().getId());
                dto.setIcerik(m.getIcerik());
                dto.setTarih(m.getTarih());
                dto.setOkundu(m.isOkundu());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(dtolar);
            
        } catch (Exception e) {
            logger.error("Kullanıcı mesajları getirme sırasında hata: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Transactional
    @GetMapping("/sohbetler")
    public ResponseEntity<List<KullaniciDto>> sohbetEdilenKullanicilar(@AuthenticationPrincipal User user) {
        try {
            // Detaylı log ekleyelim
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logger.info("sohbetEdilenKullanicilar çağrıldı");
            logger.info("Authentication: {}", auth != null ? auth.toString() : "NULL");
            logger.info("User: {}", user != null ? user.getUsername() : "NULL");
            logger.info("Principal type: {}", auth != null && auth.getPrincipal() != null ? auth.getPrincipal().getClass().getName() : "NULL");

            if (user == null) {
                logger.error("Authentication user is null");
                return ResponseEntity.status(401).build();
            }

            logger.info("Kullanıcı email ile aranıyor: {}", user.getUsername());
            Kullanici kullanici = kullaniciServisi.findByEposta(user.getUsername());

            if (kullanici == null) {
                logger.error("Kullanıcı bulunamadı email ile: {}", user.getUsername());
                return ResponseEntity.badRequest().build();
            }

            logger.info("Kullanıcı bulundu: ID={}, İsim={}", kullanici.getId(), kullanici.getIsim());

            List<Kullanici> sohbetEdilenler = mesajServisi.sohbetEdilenKullanicilar(kullanici.getId());
            logger.info("Sohbet edilen kullanıcı sayısı: {}", sohbetEdilenler.size());

            List<KullaniciDto> dtolar = sohbetEdilenler.stream()
                    .map(k -> {
                        KullaniciDto dto = new KullaniciDto();
                        dto.setId(k.getId());
                        dto.setIsim(k.getIsim());
                        dto.setEposta(k.getEposta());
                        return dto;
                    })
                    .collect(Collectors.toList());

            logger.info("DTO listesi oluşturuldu, boyut: {}", dtolar.size());
            return ResponseEntity.ok(dtolar);

        } catch (Exception e) {
            logger.error("Sohbet edilen kullanıcıları getirme sırasında hata: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/sohbet")
    public ResponseEntity<List<MesajDto>> ikiKullaniciArasindakiMesajlar(@AuthenticationPrincipal User user,
                                                                         @RequestParam Long diger) {
        try {
            logger.info("ikiKullaniciArasindakiMesajlar çağrıldı. User: {}, Diger: {}", 
                       user != null ? user.getUsername() : "NULL", diger);
            
            if (user == null) {
                logger.error("Authentication user is null");
                return ResponseEntity.status(401).build();
            }

            Kullanici kullanici = kullaniciServisi.findByEposta(user.getUsername());
            if (kullanici == null) {
                logger.error("Kullanıcı bulunamadı: {}", user.getUsername());
                return ResponseEntity.badRequest().build();
            }

            List<Mesaj> mesajlar = mesajServisi.ikiKullaniciArasindakiMesajlar(kullanici.getId(), diger);

            List<MesajDto> dtolar = mesajlar.stream().map(m -> {
                MesajDto dto = new MesajDto();
                dto.setId(m.getId());
                dto.setGonderenId(m.getGonderen().getId());
                dto.setGonderenIsim(m.getGonderen().getIsim());
                dto.setAliciId(m.getAlici().getId());
                dto.setIcerik(m.getIcerik());
                dto.setTarih(m.getTarih());
                dto.setOkundu(m.isOkundu());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(dtolar);
            
        } catch (Exception e) {
            logger.error("İki kullanıcı arasındaki mesajları getirme sırasında hata: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Transactional
    @DeleteMapping("/sohbet-sil")
    public ResponseEntity<?> sohbetSil(@AuthenticationPrincipal User user, @RequestParam Long digerKullaniciId) {
        try {
            logger.info("sohbetSil çağrıldı. User: {}, Diger: {}", 
                       user != null ? user.getUsername() : "NULL", digerKullaniciId);
            
            if (user == null) {
                logger.error("Authentication user is null");
                return ResponseEntity.status(401).body("Kullanıcı kimlik doğrulaması gerekli.");
            }

            Kullanici kullanici = kullaniciServisi.findByEposta(user.getUsername());
            if (kullanici == null) {
                logger.error("Kullanıcı bulunamadı: {}", user.getUsername());
                return ResponseEntity.badRequest().body("Kullanıcı bulunamadı.");
            }

            mesajServisi.sohbetSil(kullanici.getId(), digerKullaniciId);

            return ResponseEntity.ok("Sohbet başarıyla silindi.");
            
        } catch (Exception e) {
            logger.error("Sohbet silme sırasında hata: ", e);
            return ResponseEntity.status(500).body("Sunucu hatası: " + e.getMessage());
        }
    }
}