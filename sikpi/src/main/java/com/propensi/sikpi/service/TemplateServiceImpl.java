package com.propensi.sikpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.propensi.sikpi.repository.TemplatePenilaianDb;
import com.propensi.sikpi.repository.IndeksKinerjaIndividuDb;
import com.propensi.sikpi.repository.IndeksKinerjaUnitDb;
import com.propensi.sikpi.repository.KriteriaPenilaianDb;
import com.propensi.sikpi.repository.KriteriaPenilaianIKUDb;
import com.propensi.sikpi.repository.NormaDb;
import com.propensi.sikpi.model.IndeksKinerjaIndividu;
import com.propensi.sikpi.model.IndeksKinerjaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianIKU;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.TemplatePenilaian;

@Service
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    TemplatePenilaianDb templatePenilaianDb;

    @Autowired
    KriteriaPenilaianDb kriteriaPenilaianDb;

    @Autowired
    KriteriaPenilaianIKUDb kriteriaPenilaianIKUDb;

    @Autowired
    IndeksKinerjaIndividuDb indeksKinerjaIndividuDb;

    @Autowired
    IndeksKinerjaUnitDb indeksKinerjaUnitDb;

    @Autowired
    NormaDb normaDb;

    @Override
    public IndeksKinerjaIndividu createTemplatePenilaian(IndeksKinerjaIndividu indeksKinerjaIndividu) {
        return indeksKinerjaIndividuDb.save(indeksKinerjaIndividu);
    }

    @Override
    public List<IndeksKinerjaIndividu> getAllIki() {
        return indeksKinerjaIndividuDb.findAll();
    }

    @Override
    public IndeksKinerjaIndividu updateTemplatePenilaian(IndeksKinerjaIndividu indeksKinerjaIndividuDTO) {
        IndeksKinerjaIndividu indeksKinerjaIndividu = indeksKinerjaIndividuDb.findById(indeksKinerjaIndividuDTO.getIdTemplatePenilaian()).get();

        if (indeksKinerjaIndividu != null) {
            for (int i = 0; i < indeksKinerjaIndividuDTO.getListKriteria().size(); i++) {
                KriteriaPenilaian kp = indeksKinerjaIndividu.getListKriteria().get(i);

                kp.setJudulKriteria(indeksKinerjaIndividuDTO.getListKriteria().get(i).getJudulKriteria());
                kp.setBobot(indeksKinerjaIndividuDTO.getListKriteria().get(i).getBobot());
                kriteriaPenilaianDb.save(kp);
            }
        }
        return indeksKinerjaIndividu;
        
    }

    @Override
    public IndeksKinerjaUnit createTemplatePenilaian(IndeksKinerjaUnit indeksKinerjaUnit) {
        return indeksKinerjaUnitDb.save(indeksKinerjaUnit);
    }

    @Override
    public List<IndeksKinerjaUnit> getAllIku() {
        return indeksKinerjaUnitDb.findAll();
    }

    @Override
    public IndeksKinerjaUnit updateTemplatePenilaian(IndeksKinerjaUnit indeksKinerjaUnitDTO) {
        IndeksKinerjaUnit indeksKinerjaUnit = indeksKinerjaUnitDb.findById(indeksKinerjaUnitDTO.getIdTemplatePenilaian()).get();

        if (indeksKinerjaUnit != null) {
            for (int i = 0; i < indeksKinerjaUnitDTO.getListKriteriaIKU().size(); i++) {
                KriteriaPenilaianIKU kp = indeksKinerjaUnit.getListKriteriaIKU().get(i);

                kp.setJudulKriteria(indeksKinerjaUnitDTO.getListKriteriaIKU().get(i).getJudulKriteria());
                kp.setBobot(indeksKinerjaUnitDTO.getListKriteriaIKU().get(i).getBobot());
                kriteriaPenilaianIKUDb.save(kp);
            }
        }
        return indeksKinerjaUnit;
        
    }

    @Override
    public IndeksKinerjaIndividu deleteTemplatePenilaian(IndeksKinerjaIndividu indeksKinerjaIndividu) {
        indeksKinerjaIndividuDb.delete(indeksKinerjaIndividu);
        return indeksKinerjaIndividu;
    }

    @Override
    public IndeksKinerjaUnit deleteTemplatePenilaian(IndeksKinerjaUnit indeksKinerjaUnit) {
        indeksKinerjaUnitDb.delete(indeksKinerjaUnit);
        return indeksKinerjaUnit;
    }

    @Override
    public IndeksKinerjaIndividu getIkiById(Long id) {
        return indeksKinerjaIndividuDb.findByIdTemplatePenilaian(id);
    }

    @Override
    public IndeksKinerjaUnit getIkuById(Long id) {
        return indeksKinerjaUnitDb.findById(id).get();
    }

    @Override
    public Norma createTemplatePenilaian(Norma norma) {
        return normaDb.save(norma);
    }

    @Override
    public List<Norma> getAllNorma() {
        return normaDb.findAll();
    }

    @Override
    public Norma updateTemplatePenilaian(Norma normaDTO) {
        Norma norma = normaDb.findById(normaDTO.getIdTemplatePenilaian()).get();

        if (norma != null) {
            for (int i = 0; i < normaDTO.getListKriteria().size(); i++) {
                KriteriaPenilaian kp = norma.getListKriteria().get(i);

                kp.setJudulKriteria(normaDTO.getListKriteria().get(i).getJudulKriteria());
                kp.setBobot(normaDTO.getListKriteria().get(i).getBobot());
                kriteriaPenilaianDb.save(kp);
            }
        }
        return norma;
        
    }

    @Override
    public Norma getNormaById(Long id) {
        return normaDb.findById(id).get();
    }
    
    @Override
    public Norma deleteTemplatePenilaian(Norma norma) {
        normaDb.delete(norma);
        return norma;
    }

}
