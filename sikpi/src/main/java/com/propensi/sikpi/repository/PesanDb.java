package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.Pesan;
import com.propensi.sikpi.model.TemplatePenilaian;

import jakarta.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface PesanDb extends JpaRepository<Pesan, Long>{
    List<Pesan> findByTemplatePenilaian(TemplatePenilaian templatePenilaian);
}
