package com.propensi.sikpi.service;

import java.util.List;

import com.propensi.sikpi.model.IndeksKinerjaIndividu;
import com.propensi.sikpi.model.IndeksKinerjaUnit;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.TemplatePenilaian;

public interface TemplateService {
 
    IndeksKinerjaIndividu createTemplatePenilaian(IndeksKinerjaIndividu indeksKinerjaIndividu);
    
    List<IndeksKinerjaIndividu> getAllIki();

    IndeksKinerjaIndividu updateTemplatePenilaian(IndeksKinerjaIndividu IndeksKinerjaIndividu);

    IndeksKinerjaUnit createTemplatePenilaian(IndeksKinerjaUnit IndeksKinerjaUnit);
    
    List<IndeksKinerjaUnit> getAllIku();

    IndeksKinerjaUnit updateTemplatePenilaian(IndeksKinerjaUnit IndeksKinerjaUnit);

    IndeksKinerjaIndividu deleteTemplatePenilaian(IndeksKinerjaIndividu IndeksKinerjaIndividu);

    IndeksKinerjaUnit deleteTemplatePenilaian(IndeksKinerjaUnit IndeksKinerjaUnit);

    IndeksKinerjaIndividu getIkiById(Long id);

    IndeksKinerjaUnit getIkuById(Long id);

    Norma createTemplatePenilaian(Norma norma);

    List<Norma> getAllNorma();

    Norma updateTemplatePenilaian(Norma norma);
    
    Norma deleteTemplatePenilaian(Norma norma);

    Norma getNormaById(Long id);

}

