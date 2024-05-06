package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface BorangPenilaianDb extends JpaRepository<BorangPenilaian, Long>{
    BorangPenilaian findByIdBorangPenilaian(Long idBorangPenilaian);
    List<BorangPenilaian> findByIdTemplate(Long idTemplate);
}
