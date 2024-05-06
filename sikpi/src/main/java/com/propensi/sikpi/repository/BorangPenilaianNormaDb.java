package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianNorma;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BorangPenilaianNormaDb extends JpaRepository<BorangPenilaianNorma, Long> {
    Optional<BorangPenilaianNorma> findByEvaluatedUser(Long evaluatedUser);
    List<BorangPenilaianNorma> findByIdTemplate(Long idTemplate);
    List<BorangPenilaianNorma> findByEvaluator(Long evaluator);
    List<BorangPenilaianNorma> findByEvaluatorAndIsDeletedNot(Long idEvaluated, Boolean status);
    List<BorangPenilaianNorma> findByEvaluatedUserAndIsDeletedNot(Long idEvaluated, Boolean status);
    // List<BorangPenilaianNorma> findByEvaluatedUserId(Long evaluatedUser);
}
