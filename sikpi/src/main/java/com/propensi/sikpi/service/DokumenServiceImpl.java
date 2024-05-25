package com.propensi.sikpi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.DokumenDb;

@Service
public class DokumenServiceImpl implements DokumenService {

    // Injeksi dependency DokumenDb
    @Autowired
    DokumenDb dokumenDb;

    // Menyimpan dokumen ke database
    @Override
    public void saveDokumen(Dokumen dokumen) {
        dokumenDb.save(dokumen);
    }

    // Mengambil semua dokumen dari database
    @Override
    public List<Dokumen> getAllDokumen() {
        return dokumenDb.findAll();
    }

    // Mengambil semua dokumen berdasarkan user ID
    @Override
    public List<Dokumen> getAllDokumenByUserId(UserModel idUser) {
        return dokumenDb.findAllByIdUser(idUser);
    }

    // Menghapus dokumen dari database
    @Override
    public void deleteDokumen(Dokumen dokumen) {
        dokumenDb.delete(dokumen);
    }

    // Mengambil dokumen berdasarkan ID dokumen
    @Override
    public Dokumen getDokumenById(Long idDokumen) {
        return dokumenDb.findById(idDokumen).orElse(null);
    }
}
