package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaian;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BorangPenilaianDb extends JpaRepository<BorangPenilaian, Long>{
    BorangPenilaian findByIdBorangPenilaian(Long idBorangPenilaian);
}
