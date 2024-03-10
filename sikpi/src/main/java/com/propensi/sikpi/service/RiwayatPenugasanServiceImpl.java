package com.propensi.sikpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.RiwayatPenugasanDb;

@Service
public class RiwayatPenugasanServiceImpl implements RiwayatPenugasanService{
    @Autowired
    RiwayatPenugasanDb riwayatPenugasanDb;

    @Override
    public void saveRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan) {
        riwayatPenugasanDb.save(riwayatPenugasan);
    }

    @Override
    public List<RiwayatPenugasan> getAllRiwayatPenugasan() {
        return riwayatPenugasanDb.findAll();
    }

    @Override
    public List<RiwayatPenugasan> getAllPenugasanByUserId(UserModel idUser) {
        return riwayatPenugasanDb.findAllByIdUser(idUser);
    }

    @Override
    public RiwayatPenugasan getPenugasanById(Long idRiwayatPenugasan) {
        if (riwayatPenugasanDb.findById(idRiwayatPenugasan) != null) {
            return riwayatPenugasanDb.findById(idRiwayatPenugasan).get();
        }
        return null;
    }

    @Override
    public RiwayatPenugasan updateRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRiwayatJabatan'");
    }

    @Override
    public void deleteRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan) {
        riwayatPenugasanDb.delete(riwayatPenugasan);
    }
}