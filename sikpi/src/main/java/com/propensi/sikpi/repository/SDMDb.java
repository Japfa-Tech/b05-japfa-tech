package com.propensi.sikpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.SDM;

@Repository
public interface SDMDb extends JpaRepository<SDM, Long> {

}
