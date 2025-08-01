package com.proje.pys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proje.pys.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByIsim(String isim);
}