package com.propensi.sikpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.SDM;

@Repository
public interface SDMDb extends JpaRepository<SDM, Long> {
    @Query("SELECT s.id FROM SDM s")
    List<Long> findAllIds();
}
