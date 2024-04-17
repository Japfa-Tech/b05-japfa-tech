package com.propensi.sikpi.service;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.UserModel;

import java.util.*;

public interface BorangPenilaianService {
    void handleConnection(Long idUser, Long idBorang, String borangType);

    void handleConnectionNorma(Long idUser, Long idEvaluatedUser, Long idBorang);

    BorangPenilaian getBorangById(Long idBorang);

    BorangPenilaianIKI getBorangPenilaianIKIByEvaluatedUser(Long evaluatedUser);

    BorangPenilaianIKU getBorangPenilaianIKUByEvaluatedUnit(Long evaluatedUnit);

    BorangPenilaianNorma getBorangPenilaianNormaByEvaluatedUser(Long evaluatedUser);

    KriteriaPenilaian getKriteriaPenilaianById(Long id);

    BorangPenilaianIKI getAcceptedStatus(Long idBorangPenilaian);

    BorangPenilaianIKI updateAcceptedStatus(boolean acceptedByEvaluator, Long idBorangPenilaian);

    List<BorangPenilaianIKI> filterIKIByUser(Long id);

    List<BorangPenilaianIKU> filterIKUByUnit(Long id);

    List<BorangPenilaianNorma> filterNormaByUnit(Long id);

    Long getTotalBorang(Long idBorang);

}
