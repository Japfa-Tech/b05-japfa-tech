package com.propensi.sikpi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.IndeksKinerjaIndividu;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresIKI;
import com.propensi.sikpi.model.KriteriaScoresIKU;
import com.propensi.sikpi.model.KriteriaScoresNorma;
import com.propensi.sikpi.model.Manajer;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.SDM;
import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.BorangPenilaianDb;
import com.propensi.sikpi.repository.BorangPenilaianIKIDb;
import com.propensi.sikpi.repository.BorangPenilaianIKUDb;
import com.propensi.sikpi.repository.BorangPenilaianNormaDb;
import com.propensi.sikpi.repository.DokumenDb;
import com.propensi.sikpi.repository.IndeksKinerjaIndividuDb;
import com.propensi.sikpi.repository.KriteriaPenilaianDb;
import com.propensi.sikpi.repository.NormaDb;
import com.propensi.sikpi.repository.SDMDb;
import com.propensi.sikpi.repository.UnitDb;
import com.propensi.sikpi.repository.UserDb;

@Service
public class BorangPenilaianServiceImpl implements BorangPenilaianService {

    @Autowired
    private BorangPenilaianDb borangPenilaianDb;

    @Autowired
    private BorangPenilaianIKIDb borangPenilaianIKIDb;

    @Autowired
    private BorangPenilaianIKUDb borangPenilaianIKUDb;

    @Autowired
    private BorangPenilaianNormaDb borangPenilaianNormaDb;

    @Autowired
    private KriteriaPenilaianDb kriteriaPenilaianDb;

    @Autowired
    private UserDb userDb;

    @Autowired
    private SDMDb sdmDb;

    @Autowired
    private IndeksKinerjaIndividuDb ikiDb;

    @Autowired
    private UnitDb unitDb;

    @Autowired
    private NormaDb normaDb;

    public void handleConnection(Long idUser, Long idBorang, String borangType) {
        UserModel user = userDb.findById(idUser).get();

        if (borangType.equals("IKI")) {
            BorangPenilaianIKI borang = borangPenilaianIKIDb.findById(idBorang).get();
            IndeksKinerjaIndividu templateIki = ikiDb.findByIdTemplatePenilaian(borang.getIdTemplate());

            List<Long> listAkses = borang.getListAkses();
            List<SDM> listSDM = sdmDb.findAll();

            UserModel evaluatedUser = userDb.findById(templateIki.getEvaluatedUser()).get();
            for (SDM sdm : listSDM) {
                listAkses.add(sdm.getId());
            }
            if (user instanceof Karyawan) {
                Karyawan karyawan = (Karyawan) user;

                Long kepalaUnitId = karyawan.getIdKepalaUnit();
                KepalaUnit kepalaUnit = (KepalaUnit) userDb.findById(kepalaUnitId).get();

                Long manajerId = kepalaUnit.getIdManajer();

                listAkses.add(karyawan.getId());

                borang.setEvaluatedUser(karyawan.getId());
                borang.setEvaluator(kepalaUnitId);

                if (manajerId != null) {
                    listAkses.add(manajerId);
                }
                if (kepalaUnitId != null) {
                    listAkses.add(kepalaUnitId);
                }
            } 
            if (user instanceof KepalaUnit && evaluatedUser instanceof Karyawan) {
                // Handle logic for KepalaUnit
                KepalaUnit kepalaUnit = (KepalaUnit) user;

                Long manajerId = kepalaUnit.getIdManajer();
                Long evaluatedId = templateIki.getEvaluatedUser();

                borang.setEvaluatedUser(evaluatedId);
                borang.setEvaluator(kepalaUnit.getId());

                listAkses.add(evaluatedId);
                listAkses.add(kepalaUnit.getId());
                listAkses.add(manajerId);
            }

            if (user instanceof KepalaUnit && evaluatedUser instanceof KepalaUnit) {
                // Handle logic for KepalaUnit
                KepalaUnit kepalaUnit = (KepalaUnit) user;

                Long manajerId = kepalaUnit.getIdManajer();
                Long evaluatedId = templateIki.getEvaluatedUser();

                borang.setEvaluatedUser(evaluatedId);
                borang.setEvaluator(manajerId);

                listAkses.add(evaluatedId);
                listAkses.add(kepalaUnit.getId());
                listAkses.add(manajerId);

            } else if (user instanceof Manajer) {
                // Handle logic for Manajer
                Manajer manajer = (Manajer) user;
                Long evaluatedId = templateIki.getEvaluatedUser();

                borang.setEvaluatedUser(evaluatedId);
                borang.setEvaluator(manajer.getId());
                listAkses.add(manajer.getId());
                listAkses.add(evaluatedId);
            }
            borangPenilaianIKIDb.save(borang);

        } else if (borangType.equals("IKU")) {
            // todo
            BorangPenilaianIKU borang = borangPenilaianIKUDb.findById(idBorang).get();
            List<Long> listAkses = borang.getListAkses();

            KepalaUnit kepalaUnit = (KepalaUnit) user;

            Long manajerId = kepalaUnit.getIdManajer();
            Unit unit = kepalaUnit.getUnitKu();
            List<Karyawan> users = unit.getUsers();
            List<SDM> listSDM = sdmDb.findAll();

            borang.setEvaluatedUnit(kepalaUnit.getUnitKu().getId());
            borang.setEvaluator(kepalaUnit.getId());

            listAkses.add(kepalaUnit.getId());
            listAkses.add(manajerId);
            for (Karyawan u : users) {
                listAkses.add(u.getId());
            }

            for (SDM sdm : listSDM) {
                listAkses.add(sdm.getId());
            }

            borangPenilaianIKUDb.save(borang);

        }
    }

    public void handleConnectionNorma(Long idUser, Long idEvaluatedUser, Long idBorang) {
        UserModel user = userDb.findById(idUser).get();
        BorangPenilaianNorma borang = borangPenilaianNormaDb.findById(idBorang).get();
        List<Long> sdmList = sdmDb.findAllIds();
        // SDM sdm = (SDM) userDb.findById(idSDM).get();

        List<Long> listAkses = borang.getListAkses();

        listAkses.addAll(sdmList);

        if (user instanceof Karyawan) {
            Karyawan karyawan = (Karyawan) user;

            Long kepalaUnitId = karyawan.getIdKepalaUnit();
            KepalaUnit kepalaUnit = (KepalaUnit) userDb.findById(kepalaUnitId).get();

            Long manajerId = kepalaUnit.getIdManajer();

            listAkses.add(karyawan.getId());

            if (manajerId != null) {
                listAkses.add(manajerId);
            }
            if (kepalaUnitId != null) {
                listAkses.add(kepalaUnitId);
            }
        } else if (user instanceof KepalaUnit) {
            // Handle logic for KepalaUnit
            KepalaUnit kepalaUnit = (KepalaUnit) user;

            Long manajerId = kepalaUnit.getIdManajer();
            // Long evaluatedId = templateNorma.getEvaluatedUser();

            borang.setEvaluatedUser(idEvaluatedUser);
            borang.setEvaluator(kepalaUnit.getId());

            listAkses.add(idEvaluatedUser);
            listAkses.add(kepalaUnit.getId());
            listAkses.add(manajerId);

        } else if (user instanceof Manajer) {
            // Handle logic for Manajer
            Manajer manajer = (Manajer) user;
            listAkses.add(manajer.getId());
        }
        borang.setEvaluatedUser(idEvaluatedUser);
        borangPenilaianNormaDb.save(borang);

    }

    public BorangPenilaian getBorangById(Long idBorang) {
        return borangPenilaianDb.findById(idBorang).get();
    }

    @Override
    public BorangPenilaianIKI getBorangPenilaianIKIByEvaluatedUser(Long evaluatedUser) {
        List<BorangPenilaianIKI> borangList = borangPenilaianIKIDb.findByEvaluatedUserAndIsDeletedNot(evaluatedUser, true);
        if (!borangList.isEmpty()) {
            return borangList.get(0);
        }
        return null; // Or handle this case according to your application logic
    }

    @Override
    public KriteriaPenilaian getKriteriaPenilaianById(Long id) {
        return kriteriaPenilaianDb.findById(id).get();
    }

    @Override
    public BorangPenilaianIKI getAcceptedStatus(Long idBorangPenilaian) {
        return borangPenilaianIKIDb.findByIdBorangPenilaian(idBorangPenilaian);
    }

    @Override
    public BorangPenilaianIKI updateAcceptedStatus(boolean acceptedByEvaluator, Long idBorangPenilaian) {
        BorangPenilaianIKI borang = borangPenilaianIKIDb.findById(idBorangPenilaian).orElse(new BorangPenilaianIKI());
        borang.setAcceptedByEvaluator(acceptedByEvaluator);
        borangPenilaianIKIDb.save(borang);
        return borang;
    }

    public BorangPenilaianIKU getBorangPenilaianIKUByEvaluatedUnit(Long evaluatedUnit) {
        List<BorangPenilaianIKU> borangList = borangPenilaianIKUDb.findByEvaluatedUnitAndIsDeletedNot(evaluatedUnit, true);
        if (!borangList.isEmpty()) {
            return borangList.get(0);
        }
        return null; // Or handle this case according to your application logic
    }

    @Override
    public BorangPenilaianNorma getBorangPenilaianNormaByEvaluatedUser(Long evaluatedUser) {
        List<BorangPenilaianNorma> borangList = borangPenilaianNormaDb.findByEvaluatedUserAndIsDeletedNot(evaluatedUser, true);
        if (!borangList.isEmpty()) {
            return borangList.get(0);
        }
        return null; // Or handle this case according to your application logic
    }

    @Override
    public List<BorangPenilaianIKI> filterIKIByUser(Long id) {
        // TODO Auto-generated method stub
        List<BorangPenilaianIKI> allIki = borangPenilaianIKIDb.findAll();
        List<BorangPenilaianIKI> filteredIki = new ArrayList<>();

        for (BorangPenilaianIKI iki : allIki) {
            if (id == iki.getEvaluatedUser() && !iki.getStatus().equals("template accepted"))
                filteredIki.add(iki);
        }

        return filteredIki;
    }

    @Override
    public List<BorangPenilaianIKU> filterIKUByUnit(Long id) {
        // TODO Auto-generated method stub
        List<BorangPenilaianIKU> allIKu = borangPenilaianIKUDb.findAll();
        List<BorangPenilaianIKU> filteredIku = new ArrayList<>();

        for (BorangPenilaianIKU iku : allIKu) {
            if (id == iku.getEvaluatedUnit())
                filteredIku.add(iku);
        }

        return filteredIku;
    }

    @Override
    public List<BorangPenilaianNorma> filterNormaByUnit(Long id) {
        List<BorangPenilaianNorma> allNorma = borangPenilaianNormaDb.findAll();
        List<BorangPenilaianNorma> filteredNorma = new ArrayList<>();

        for (BorangPenilaianNorma norma : allNorma) {
            if (id == norma.getEvaluatedUser())
                filteredNorma.add(norma);
        }
        return filteredNorma;
    }

    @Override
    public Long getTotalBorangIKI(Long idBorang) {
        BorangPenilaianIKI iki = (BorangPenilaianIKI) getBorangById(idBorang);
        Long total = (long) 0;
        for (KriteriaScoresIKI kriteria : iki.getKriteriaScoresIKI()) {
            total += kriteria.getScore() * kriteria.getKriteria().getBobot();
        }
        return total;
    }

    @Override
    public Long getTotalBorangIKU(Long idBorang) {
        BorangPenilaianIKU iku = (BorangPenilaianIKU) getBorangById(idBorang);
        Long total = (long) 0;
        for (KriteriaScoresIKU kriteria : iku.getKriteriaScoresIKU()) {
            total += kriteria.getScore() * kriteria.getKriteria().getBobot();
        }
        return total;
    }

     @Override
    public Long getTotalBorangNorma(Long idBorangNorma) {
        BorangPenilaianNorma norma = borangPenilaianNormaDb.findById(idBorangNorma).get();
        System.out.println("iniid norma" + norma.getIdBorangPenilaian());
        Long total = (long) 0;
        for (KriteriaScoresNorma kriteria : norma.getKriteriaScoresNorma()) {
            total += kriteria.getScore() * kriteria.getKriteriaNorma().getBobot();
        }
        return total;
    }

    @Override
    public List<BorangPenilaian> getAllBorang() {
        return borangPenilaianDb.findAll();
    }
}
