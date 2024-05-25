package com.propensi.sikpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.RaporDb;

@Service
public class RaporServiceImpl implements RaporService {

    // Injeksi dependency RaporDb
    @Autowired
    RaporDb raporDb;

    // Menyimpan rapor ke database
    @Override
    public void saveRapor(Rapor rapor) {
        raporDb.save(rapor);
    }

    // Mengambil rapor berdasarkan pengguna yang dinilai
    @Override
    public Rapor getRaporByEvaluatedUser(UserModel idUser) {
        return raporDb.findByEvaluatedUser(idUser).orElse(null);
    }

    // Mengambil semua rapor yang belum ditandatangani oleh SDM
    @Override
    public List<Rapor> getUnsignedBySDM() {
        return raporDb.findBySignPenyetujuFalse();
    }

    // Mengambil semua rapor yang belum ditandatangani oleh Kepala Bidang
    @Override
    public List<Rapor> getUnsignedByKepalaBidang() {
        return raporDb.findBySignPenilaiFalse();
    }
}
