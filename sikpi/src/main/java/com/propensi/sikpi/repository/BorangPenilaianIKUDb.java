package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BorangPenilaianIKUDb extends JpaRepository<BorangPenilaianIKU, Long> {
    Optional<BorangPenilaianIKU> findByEvaluatedUnit(Long evaluatedUnit);
    BorangPenilaianIKU findByIdBorangPenilaian(Long idBorangPenilaian);
    List<BorangPenilaianIKU> findByIdTemplate(Long idTemplate);
    List<BorangPenilaianIKU> findByEvaluator(Long evaluator);
    List<BorangPenilaianIKU> findByEvaluatorAndIsDeletedNot(Long idEvaluator, Boolean status);
    List<BorangPenilaianIKU> findByEvaluatedUnitAndIsDeletedNot(Long idEvaluated, Boolean status);

    // List<BorangPenilaianIKU> findByEvaluatedUnitId(Long evaluatedUnit);
}
