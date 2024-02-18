package com.propensi.sikpi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.propensi.sikpi.model.Aktivitas;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface AktivitasDb extends JpaRepository<Aktivitas, UUID> {
    
}
