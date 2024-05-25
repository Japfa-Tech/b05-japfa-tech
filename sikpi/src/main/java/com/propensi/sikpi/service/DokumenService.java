package com.propensi.sikpi.service;

import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.UserModel;
import java.util.*;

public interface DokumenService {

    void saveDokumen(Dokumen dokumen);

    List<Dokumen> getAllDokumen();

    List<Dokumen> getAllDokumenByUserId(UserModel idUser);

    Dokumen getDokumenById(Long idDokumen);

    void deleteDokumen(Dokumen dokumen);
}
