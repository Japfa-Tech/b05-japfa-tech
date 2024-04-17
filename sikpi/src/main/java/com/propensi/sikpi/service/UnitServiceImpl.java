package com.propensi.sikpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
import com.propensi.sikpi.DTO.request.LoginFormDTO;
import com.propensi.sikpi.DTO.request.LoginJwtRequestDTO;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.security.jwt.JwtUtils;

import javassist.NotFoundException;

import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.repository.DokumenDb;
import com.propensi.sikpi.repository.KaryawanDb;
import com.propensi.sikpi.repository.KepalaUnitDb;
import com.propensi.sikpi.repository.UnitDb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDb unitDb;

    @Autowired
    private KaryawanDb karyawanDb;

    @Autowired
    private KepalaUnitDb kepalaUnitDb;

    @Override
    public List<Unit> getAllUnits() {
        return unitDb.findAll();
    }

    @Override
    public Unit getUnitByUserId(Long userId) throws NotFoundException {
        Optional<Karyawan> karyawanOptional = karyawanDb.findById(userId);

        // Check if the user exists
        if (karyawanOptional.isPresent()) {
            Karyawan karyawan = karyawanOptional.get();

            // Retrieve the unit associated with the user
            return karyawan.getUnit();
        } else {
            // Handle case where user does not exist
            throw new NotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public Unit getUnitByKepalaUnitId(Long kepalaUnitId) throws NotFoundException {
        // Retrieve the KepalaUnit entity by ID
        Optional<KepalaUnit> kepalaUnitOptional = kepalaUnitDb.findById(kepalaUnitId);

        // Check if the KepalaUnit exists
        if (kepalaUnitOptional.isPresent()) {
            KepalaUnit kepalaUnit = kepalaUnitOptional.get();

            // Retrieve the unit associated with the KepalaUnit
            return unitDb.findById(kepalaUnit.getIdUnit())
                    .orElseThrow(() -> new NotFoundException("Unit not found for KepalaUnit ID: " + kepalaUnitId));
        } else {
            // Handle case where KepalaUnit does not exist
            throw new NotFoundException("KepalaUnit not found with ID: " + kepalaUnitId);
        }
    }

    @Override
    public Long getUnitIdForUser(Long userId) {
        Optional<Karyawan> karyawanOptional = karyawanDb.findById(userId);
        if (karyawanOptional.isPresent()) {
            return karyawanOptional.get().getUnit().getId();
        }

        Optional<KepalaUnit> kepalaUnitOptional = kepalaUnitDb.findById(userId);
        if (kepalaUnitOptional.isPresent()) {
            return kepalaUnitOptional.get().getIdUnit();
        }

        // Optional<Manajer> manajerOptional = manajerDb.findById(userId);
        // if (manajerOptional.isPresent()) {
        // return manajerOptional.get().getIdUnit();
        // }

        // Handle case when user is not found or doesn't belong to any of the specified
        // roles
        return null;
    }
}
