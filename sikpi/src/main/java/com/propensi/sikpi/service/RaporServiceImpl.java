package com.propensi.sikpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.RaporDb;

@Service
public class RaporServiceImpl implements RaporService {
    @Autowired
    RaporDb raporDb;

    @Override
    public void saveRapor(Rapor rapor) {
        raporDb.save(rapor);
    }

    @Override
    public Rapor getRaporByEvaluatedUser(UserModel idUser) {
        return raporDb.findByEvaluatedUser(idUser).get();
    }

    @Override
    public List<Rapor> getUnsignedBySDM() {
        return raporDb.findBySignPenyetujuFalse();
    }

    @Override
    public List<Rapor> getUnsignedByKepalaBidang() {
        return raporDb.findBySignPenilaiFalse();
    }

    // @Override
    // public Rapor getRaporByUserId(UserModel idUser) {
    // return raporDb.findAllByIdUser(idUser);
    // }

    // @Override
    // public Rapor getRaporById(Long idRapor) {
    // if (raporDb.findById(idRapor) != null) {
    // return raporDb.findById(idRapor).get();
    // }
    // return null;
    // }

}
