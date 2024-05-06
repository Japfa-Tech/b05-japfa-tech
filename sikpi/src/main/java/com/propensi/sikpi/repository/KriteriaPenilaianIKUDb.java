package com.propensi.sikpi.repository;

import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianIKU;
import com.propensi.sikpi.model.KriteriaPenilaianNorma;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KriteriaPenilaianIKUDb extends JpaRepository<KriteriaPenilaianIKU, Long> {

}
