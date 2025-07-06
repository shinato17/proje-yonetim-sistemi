package com.proje.pys.repository;

import com.proje.pys.entity.ProjeKullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjeKullaniciRepository extends JpaRepository<ProjeKullanici, Long> {
    List<ProjeKullanici> findByProjeId(Long projeId);
    List<ProjeKullanici> findByKullaniciId(Long kullaniciId);
    boolean existsByProjeIdAndKullaniciId(Long projeId, Long kullaniciId);
}
