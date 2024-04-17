package com.propensi.sikpi.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.Pesan;
import com.propensi.sikpi.model.TemplatePenilaian;
import com.propensi.sikpi.repository.BorangPenilaianIKIDb;
import com.propensi.sikpi.repository.BorangPenilaianIKUDb;
import com.propensi.sikpi.repository.BorangPenilaianNormaDb;
import com.propensi.sikpi.repository.KepalaUnitDb;
import com.propensi.sikpi.repository.PesanDb;
import com.propensi.sikpi.service.PesanService;
import com.propensi.sikpi.service.TemplateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AcceptedStatusController { 

    @Autowired
    private BorangPenilaianIKIDb borangPenilaianIKIDb;

    @Autowired
    private BorangPenilaianIKUDb borangPenilaianIKUDb;

    @Autowired
    private BorangPenilaianNormaDb borangPenilaianNormaDb;

    @Autowired
    private KepalaUnitDb kepalaUnitDb;

    @PutMapping("/borang/template-penilaian-iki/{id}/status")
    public String updateStatusBorang(@PathVariable Long id, 
    @RequestParam("idUserLogin") Long idUserLogin, 
    @RequestParam("evaluator") Long evaluator, 
    @RequestParam("evaluatedUser") Long evaluatedUser) {
        List<BorangPenilaianIKI> listBorangPenilaianIKI = borangPenilaianIKIDb.findByIdTemplate(id);
        BorangPenilaianIKI borangPenilaianIKI = listBorangPenilaianIKI.stream()
        .reduce((first, second) -> second).orElse(null);
        if(idUserLogin.equals(evaluator)) {
            if(borangPenilaianIKI.isTemplateAccepted() == true) {
                borangPenilaianIKI.setAcceptedByEvaluator(true);
                borangPenilaianIKI.setStatus("finished");
            }else{
                borangPenilaianIKI.setTemplateAccepted(true);
                borangPenilaianIKI.setStatus("template accepted");
            }
            
        }


        borangPenilaianIKIDb.save(borangPenilaianIKI);
        return "Status Borang Penilaian IKI berhasil diubah";
    }

    @PutMapping("/borang/template-penilaian-iku/{id}/status")
    public String updateStatusBorangIKU(@PathVariable Long id, 
    @RequestParam("idUserLogin") Long idUserLogin, 
    @RequestParam("evaluator") Long evaluator, 
    @RequestParam("evaluatedUnit") Long evaluatedUnit) {
        // KepalaUnit ku = kepalaUnitDb.findById(idUserLogin).get();

        List<BorangPenilaianIKU> listBorangPenilaianIKU = borangPenilaianIKUDb.findByIdTemplate(id);
        BorangPenilaianIKU borangPenilaianIKU = listBorangPenilaianIKU.stream()

        .reduce((first, second) -> second).orElse(null);
        // borangPenilaianIKUDb.setTanggalSubmit(LocalDateTime.now());
        borangPenilaianIKU.setAcceptedByEvaluator(true);
        borangPenilaianIKU.setStatus("finished");
        

        borangPenilaianIKUDb.save(borangPenilaianIKU);
        return "Status Borang Penilaian IKU berhasil diubah";
    }

    @PutMapping("/borang/template-penilaian-norma/{id}/status")
    public String updateStatusBorangNorma(@PathVariable Long id, 
    @RequestParam("idUserLogin") Long idUserLogin, 
    @RequestParam("evaluator") Long evaluator, 
    @RequestParam("evaluatedUser") Long evaluatedUser) {
        List<BorangPenilaianNorma> listBorangPenilaianNorma = borangPenilaianNormaDb.findByIdTemplate(id);
        BorangPenilaianNorma borangPenilaianNorma = listBorangPenilaianNorma.stream()
        .reduce((first, second) -> second).orElse(null);
        
        borangPenilaianNorma.setAcceptedByEvaluator(true);
        borangPenilaianNorma.setStatus("finished");

        borangPenilaianNormaDb.save(borangPenilaianNorma);
        return "Status Borang Penilaian IKI berhasil diubah";
    }
}
