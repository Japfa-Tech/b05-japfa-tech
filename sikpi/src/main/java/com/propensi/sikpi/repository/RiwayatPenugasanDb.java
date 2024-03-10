package com.propensi.sikpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.UserModel;

@Repository
@Transactional
public interface RiwayatPenugasanDb extends JpaRepository<RiwayatPenugasan, Long>{
    List<RiwayatPenugasan> findAllByIdUser(UserModel idUser);
}
