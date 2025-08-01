package com.proje.pys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.proje.pys.entity.Mesaj;

public interface MesajRepository extends JpaRepository<Mesaj, Long> {

    List<Mesaj> findByAliciIdOrderByTarihDesc(Long aliciId);

    List<Mesaj> findByGonderenIdOrderByTarihDesc(Long gonderenId);

    boolean existsByAliciIdAndOkunduFalse(Long aliciId);

    List<Mesaj> findByAliciIdAndOkunduFalse(Long aliciId);

    List<Mesaj> findByGonderenIdOrAliciId(Long gonderenId, Long aliciId);

    List<Mesaj> findByGonderenIdAndAliciIdOrGonderenIdAndAliciId(Long gonderenId1, Long aliciId1,
            Long gonderenId2, Long aliciId2);

    List<Mesaj> findByGonderenIdOrAliciIdOrderByTarihDesc(Long gonderenId, Long aliciId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Mesaj m WHERE (m.gonderen.id = :kullaniciId1 AND m.alici.id = :kullaniciId2) OR (m.gonderen.id = :kullaniciId2 AND m.alici.id = :kullaniciId1)")
    void deleteSohbetBetweenUsers(Long kullaniciId1, Long kullaniciId2);

}
