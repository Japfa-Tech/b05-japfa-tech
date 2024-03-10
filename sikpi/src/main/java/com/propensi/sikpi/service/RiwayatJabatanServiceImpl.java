package com.propensi.sikpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.RiwayatJabatanDb;

@Service
public class RiwayatJabatanServiceImpl implements RiwayatJabatanService{
    @Autowired
    RiwayatJabatanDb riwayatJabatanDb;

    @Override
    public void saveRiwayatJabatan(RiwayatJabatan riwayatJabatan) {
        riwayatJabatanDb.save(riwayatJabatan);
    }

    @Override
    public List<RiwayatJabatan> getAllRiwayatJabatan() {
        return riwayatJabatanDb.findAll();
    }

    @Override
    public List<RiwayatJabatan> getAllJabatanByUserId(UserModel idUser) {
        return riwayatJabatanDb.findAllByIdUser(idUser);
    }

    @Override
    public RiwayatJabatan getJabatanById(Long idRiwayatJabatan) {
        if (riwayatJabatanDb.findById(idRiwayatJabatan) != null) {
            return riwayatJabatanDb.findById(idRiwayatJabatan).get();
        }
        return null;
    }

    @Override
    public RiwayatJabatan updateRiwayatJabatan(RiwayatJabatan riwayatJabatan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRiwayatJabatan'");
    }

    @Override
    public void deleteRiwayatJabatan(RiwayatJabatan riwayatJabatan) {
        riwayatJabatanDb.delete(riwayatJabatan);
    }
}