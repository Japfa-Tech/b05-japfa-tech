package com.propensi.sikpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.DokumenDb;

@Service
public class DokumenServiceImpl implements DokumenService {
    @Autowired
    DokumenDb dokumenDb;

    @Override
    public void handleDOC(Dokumen dokumen) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleDOC'");
    }

    @Override
    public void saveDokumen(Dokumen dokumen) {
        dokumenDb.save(dokumen);
    }

    @Override
    public List<Dokumen> getAllDokumen() {
        return dokumenDb.findAll();
    }

    @Override
    public List<Dokumen> getAllDokumenByUserId(UserModel idUser) {
        return dokumenDb.findAllByIdUser(idUser);
    }

    @Override
    public void deleteDokumen(Dokumen dokumen) {
        dokumenDb.delete(dokumen);
    }

    @Override
    public Dokumen getDokumenById(Long idDokumen) {
        if (dokumenDb.findById(idDokumen) != null) {
            return dokumenDb.findById(idDokumen).get();
        }
        return null;
    }

}
