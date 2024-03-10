package com.propensi.sikpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.UserModel;

@Repository
@Transactional
public interface RiwayatJabatanDb extends JpaRepository<RiwayatJabatan, Long>{
    List<RiwayatJabatan> findAllByIdUser(UserModel idUser);
}
