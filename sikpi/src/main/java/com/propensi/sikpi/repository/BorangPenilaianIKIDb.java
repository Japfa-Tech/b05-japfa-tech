package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaianIKI;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BorangPenilaianIKIDb extends JpaRepository<BorangPenilaianIKI, Long> {
    Optional<BorangPenilaianIKI> findByEvaluatedUser(Long evaluatedUser);
    BorangPenilaianIKI findByIdBorangPenilaian(Long idBorangPenilaian);
    List<BorangPenilaianIKI> findByIdTemplate(Long idTemplate);

    // List<BorangPenilaianIKI> findByEvaluatedUser(Long evaluatedUser);
}
