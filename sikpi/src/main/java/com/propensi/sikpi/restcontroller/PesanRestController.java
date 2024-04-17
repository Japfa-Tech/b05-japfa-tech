package com.propensi.sikpi.restcontroller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.propensi.sikpi.DTO.response.BorangPenilaianIKIResponseDTO;
import com.propensi.sikpi.DTO.response.BorangPenilaianIKUResponseDTO;
import com.propensi.sikpi.DTO.response.BorangPenilaianNormaResponseDTO;
import com.propensi.sikpi.DTO.response.BorangPenilaianResponse;
import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianNorma;
import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresNorma;
import com.propensi.sikpi.model.Pesan;
import com.propensi.sikpi.model.Role;
import com.propensi.sikpi.model.TemplatePenilaian;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.BorangPenilaianDb;
import com.propensi.sikpi.repository.BorangPenilaianIKIDb;
import com.propensi.sikpi.repository.BorangPenilaianIKUDb;
import com.propensi.sikpi.repository.BorangPenilaianNormaDb;
import com.propensi.sikpi.repository.PesanDb;
import com.propensi.sikpi.repository.RoleDb;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.PesanService;
import com.propensi.sikpi.service.TemplateService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api")
@Slf4j
public class PesanRestController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private PesanDb pesanDb;

    @Autowired
    private PesanService pesanService;

    @Autowired
    private BorangPenilaianIKIDb borangPenilaianIKIDb;

    @Autowired
    private BorangPenilaianIKUDb borangPenilaianIKUDb;

    @Autowired
    private BorangPenilaianNormaDb borangPenilaianNormaDb;

    @Autowired
    private RoleDb roleDb;

    @Autowired
    private UserDb userDb;

    @PostMapping("/borang/template-penilaian-iki/{id}/pesan")
    // @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPesan(@RequestParam("pesan") String isiPesan, @PathVariable Long id) {
        log.info("isi pesan: " + isiPesan);
        log.info("id: " + id);
        TemplatePenilaian templatePenilaian = templateService.getIkiById(id);
        log.info("template" + templatePenilaian.toString());
        if(ObjectUtils.isEmpty(templatePenilaian)){
            return null;
        }
        else{
            log.info("masuk");
            Pesan pesan = new Pesan();
            pesan.setTemplatePenilaian(templatePenilaian);
            pesan.setIsiPesan(isiPesan);
            pesan.setWaktuPesan(LocalDateTime.now());
            pesanDb.save(pesan);
            return isiPesan;
        }
    }

    @GetMapping("/borang/template-penilaian-iki/{id}/pesan")
    // @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BorangPenilaianResponse viewPesan(@PathVariable Long id, @RequestParam("role") String requestRole) {
        TemplatePenilaian templatePenilaian = templateService.getIkiById(id);
        List<KriteriaScores> kriteriaScores = new ArrayList<>();
        Long idUserLogin = null;
        log.info("id" + id);
        log.info("gettemplate : {}", templatePenilaian);
        if(ObjectUtils.isEmpty(templatePenilaian)){
            log.info("masuk");
            return null;
        }
        else{
            List<Pesan> pesan = pesanDb.findByTemplatePenilaian(templatePenilaian);
            log.info("id= {}", id);
            List<BorangPenilaianIKI> listBorangPenilaianIKI = borangPenilaianIKIDb.findByIdTemplate(id);
            
            BorangPenilaianIKI borangPenilaianIKI = listBorangPenilaianIKI.stream()
            .reduce((first, second) -> second).orElse(null);
            Role role = roleDb.findByRole(requestRole).orElseThrow(() -> {
                    throw new IllegalArgumentException("Role not found");
                });
            UserModel user = userDb.findByRole(role).orElseThrow(() -> {
                throw new IllegalArgumentException("User not found");
            });
            // BorangPenilaian borangPenilaian = borangPenilaianDb.findByIdBorangPenilaian(id);

            if(CollectionUtils.isEmpty(pesan)){
                pesan = new ArrayList<>();
            }
            if(ObjectUtils.isEmpty(borangPenilaianIKI)){
                borangPenilaianIKI = new BorangPenilaianIKI();
            }
            else{
                log.info("masuk");
                for (KriteriaScores kriteriaScore : borangPenilaianIKI.getKriteriaScores()) {
                    KriteriaScores kriteriaScoreTemp = new KriteriaScores();
                    // kriteriaScoreTemp.setKriteria(kriteriaScore.getKriteria());
                    KriteriaPenilaian kriteriaPenilaianTemp = new KriteriaPenilaian();
                    kriteriaPenilaianTemp.setBobot(kriteriaScore.getKriteria().getBobot());
                    kriteriaPenilaianTemp.setIdKriteriaPenilaian(kriteriaScore.getKriteria().getIdKriteriaPenilaian());
                    kriteriaPenilaianTemp.setJudulKriteria(kriteriaScore.getKriteria().getJudulKriteria());
                    kriteriaPenilaianTemp.setSkor(kriteriaScore.getKriteria().getSkor());

                    kriteriaScoreTemp.setKriteria(kriteriaPenilaianTemp);
                    kriteriaScoreTemp.setScore(kriteriaScore.getScore());
                    kriteriaScores.add(kriteriaScoreTemp);
                }
            }         
            if(!CollectionUtils.isEmpty(borangPenilaianIKI.getListAkses())){
                idUserLogin = borangPenilaianIKI.getListAkses().stream()
                .filter(idUserTemp -> idUserTemp.equals(user.getId())).findFirst()
                .orElseThrow(()-> {
                    throw new IllegalArgumentException("List Akses not found");
                }); 
            }   

            return BorangPenilaianResponse.builder()
                    .borangPenilaianIKI(BorangPenilaianIKIResponseDTO.builder()
                        .status(borangPenilaianIKI.getStatus())
                        .kriteriaScores(kriteriaScores)
                        .evaluatedUser(borangPenilaianIKI.getEvaluatedUser())
                        .evaluator(borangPenilaianIKI.getEvaluator())
                        .acceptedByEvaluator(borangPenilaianIKI.isAcceptedByEvaluator())
                        .build())
                    // .borangPenilaian(mapper.convertValue(borangPenilaian, BorangPenilaian.class))
                    .listPesan(pesan)
                    .idUserLogin(idUserLogin)
                    .build();
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/borang/template-penilaian-iku/{id}/pesan")
    public String addPesanIku(@RequestParam("pesan") String isiPesan, @PathVariable Long id) {
        log.info("isi pesan: " + isiPesan);
        log.info("id: " + id);
        TemplatePenilaian templatePenilaian = templateService.getIkuById(id);
        log.info("template" + templatePenilaian.toString());
        if(ObjectUtils.isEmpty(templatePenilaian)){
            return null;
        }
        else{
            log.info("masuk");
            Pesan pesan = new Pesan();
            pesan.setTemplatePenilaian(templatePenilaian);
            pesan.setIsiPesan(isiPesan);
            pesan.setWaktuPesan(LocalDateTime.now());
            pesanDb.save(pesan);
            return isiPesan;
        }
    }

    @GetMapping("/borang/template-penilaian-iku/{id}/pesan")
    public BorangPenilaianResponse viewPesanIku(@PathVariable Long id, @RequestParam("role") String requestRole) {
        TemplatePenilaian templatePenilaian = templateService.getIkuById(id);
        List<KriteriaScores> kriteriaScores = new ArrayList<>();
        Long idUserLogin = null;
        log.info("id" + id);
        log.info("gettemplate : {}", templatePenilaian);
        if(ObjectUtils.isEmpty(templatePenilaian)){
            log.info("masuk");
            return null;
        }
        else{
            List<Pesan> pesan = pesanDb.findByTemplatePenilaian(templatePenilaian);
            log.info("id= {}", id);
            List<BorangPenilaianIKU> listBorangPenilaianIKU = borangPenilaianIKUDb.findByIdTemplate(id);
            
            BorangPenilaianIKU borangPenilaianIKU = listBorangPenilaianIKU.stream()
            .reduce((first, second) -> second).orElse(null);
            Role role = roleDb.findByRole(requestRole).orElseThrow(() -> {
                    throw new IllegalArgumentException("Role not found");
                });
            UserModel user = userDb.findByRole(role).orElseThrow(() -> {
                throw new IllegalArgumentException("User not found");
            });

            if(CollectionUtils.isEmpty(pesan)){
                pesan = new ArrayList<>();
            }
            if(ObjectUtils.isEmpty(borangPenilaianIKU)){
                borangPenilaianIKU = new BorangPenilaianIKU();
            }
            else{
                log.info("masuk");
                for (KriteriaScores kriteriaScore : borangPenilaianIKU.getKriteriaScores()) {
                    KriteriaScores kriteriaScoreTemp = new KriteriaScores();
                    KriteriaPenilaian kriteriaPenilaianTemp = new KriteriaPenilaian();
                    kriteriaPenilaianTemp.setBobot(kriteriaScore.getKriteria().getBobot());
                    kriteriaPenilaianTemp.setIdKriteriaPenilaian(kriteriaScore.getKriteria().getIdKriteriaPenilaian());
                    kriteriaPenilaianTemp.setJudulKriteria(kriteriaScore.getKriteria().getJudulKriteria());
                    kriteriaPenilaianTemp.setSkor(kriteriaScore.getKriteria().getSkor());

                    kriteriaScoreTemp.setKriteria(kriteriaPenilaianTemp);
                    kriteriaScoreTemp.setScore(kriteriaScore.getScore());
                    kriteriaScores.add(kriteriaScoreTemp);
                }
            }         
            if(!CollectionUtils.isEmpty(borangPenilaianIKU.getListAkses())){
                idUserLogin = borangPenilaianIKU.getListAkses().stream()
                .filter(idUserTemp -> idUserTemp.equals(user.getId())).findFirst()
                .orElseThrow(()-> {
                    throw new IllegalArgumentException("List Akses not found");
                }); 
            }   

            return BorangPenilaianResponse.builder()
                    .borangPenilaianIKU(BorangPenilaianIKUResponseDTO.builder()
                        .status(borangPenilaianIKU.getStatus())
                        .kriteriaScores(kriteriaScores)
                        .evaluatedUnit(borangPenilaianIKU.getEvaluatedUnit())
                        .evaluator(borangPenilaianIKU.getEvaluator())
                        .acceptedByEvaluator(borangPenilaianIKU.isAcceptedByEvaluator())
                        .build())
                    .listPesan(pesan)
                    .idUserLogin(idUserLogin)
                    .build();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@PostMapping("/borang/template-penilaian-norma/{id}/pesan")
// @RequestMapping(value = "/add", method = RequestMethod.POST)
public String addPesanNorma(@RequestParam("pesan") String isiPesan, @PathVariable Long id) {
    log.info("isi pesan: " + isiPesan);
    log.info("id: " + id);
    TemplatePenilaian templatePenilaian = templateService.getNormaById(id);
    log.info("template" + templatePenilaian.toString());
    if(ObjectUtils.isEmpty(templatePenilaian)){
        return null;
    }
    else{
        log.info("masuk");
        Pesan pesan = new Pesan();
        pesan.setTemplatePenilaian(templatePenilaian);
        pesan.setIsiPesan(isiPesan);
        pesan.setWaktuPesan(LocalDateTime.now());
        pesanDb.save(pesan);
        return isiPesan;
    }
}

@GetMapping("/borang/template-penilaian-norma/{id}/pesan")
// @RequestMapping(value = "/list", method = RequestMethod.GET)
public BorangPenilaianResponse viewPesanNorma(@PathVariable Long id, @RequestParam("role") String requestRole) {
    System.out.println(id);
    System.out.println("p");
    System.out.println();
    TemplatePenilaian templatePenilaian = templateService.getNormaById(id);
    List<KriteriaScoresNorma> kriteriaScoresNorma = new ArrayList<>();
    Long idUserLogin = null;
    log.info("id" + id);
    log.info("gettemplate : {}", templatePenilaian);
    if(ObjectUtils.isEmpty(templatePenilaian)){
        log.info("masuk");
        return null;
    }
    else{
        List<Pesan> pesan = pesanDb.findByTemplatePenilaian(templatePenilaian);
        log.info("id= {}", id);
        List<BorangPenilaianNorma> listBorangPenilaianNorma = borangPenilaianNormaDb.findByIdTemplate(id);
        // System.out.println(listBorangPenilaianNorma.get(0).getIdBorangPenilaian());
        
        BorangPenilaianNorma borangPenilaianNorma = listBorangPenilaianNorma.stream()
        .reduce((first, second) -> second).orElse(null);
        Role role = roleDb.findByRole(requestRole).orElseThrow(() -> {
                throw new IllegalArgumentException("Role not found");
            });
        UserModel user = userDb.findByRole(role).orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
        // BorangPenilaian borangPenilaian = borangPenilaianDb.findByIdBorangPenilaian(id);

        if(CollectionUtils.isEmpty(pesan)){
            pesan = new ArrayList<>();
        }
        if(ObjectUtils.isEmpty(borangPenilaianNorma)){
            borangPenilaianNorma = new BorangPenilaianNorma();
        }
        else{
            log.info("masuk");
            for (KriteriaScoresNorma kriteriaScoreNorma : borangPenilaianNorma.getKriteriaScoresNorma()) {
                KriteriaScoresNorma kriteriaScoreTemp = new KriteriaScoresNorma();
                // kriteriaScoreTemp.setKriteria(kriteriaScore.getKriteria());
                KriteriaPenilaianNorma kriteriaPenilaianTemp = new KriteriaPenilaianNorma();
                kriteriaPenilaianTemp.setBobot(kriteriaScoreNorma.getKriteriaNorma().getBobot());
                kriteriaPenilaianTemp.setIdKriteriaPenilaian(kriteriaScoreNorma.getKriteriaNorma().getIdKriteriaPenilaian());
                kriteriaPenilaianTemp.setJudulKriteria(kriteriaScoreNorma.getKriteriaNorma().getJudulKriteria());
                kriteriaPenilaianTemp.setSkor(kriteriaScoreNorma.getKriteriaNorma().getSkor());

                kriteriaScoreTemp.setKriteriaNorma(kriteriaPenilaianTemp);
                kriteriaScoreTemp.setScore(kriteriaScoreNorma.getScore());
                kriteriaScoresNorma.add(kriteriaScoreTemp);
            }
        }         
        if(!CollectionUtils.isEmpty(borangPenilaianNorma.getListAkses())){
            idUserLogin = borangPenilaianNorma.getListAkses().stream()
            .filter(idUserTemp -> idUserTemp.equals(user.getId())).findFirst()
            .orElseThrow(()-> {
                throw new IllegalArgumentException("List Akses not found");
            }); 
        }   

        return BorangPenilaianResponse.builder()
                .borangPenilaianNorma(BorangPenilaianNormaResponseDTO.builder()
                    .status(borangPenilaianNorma.getStatus())
                    .kriteriaScoresNorma(kriteriaScoresNorma)
                    .evaluatedUser(borangPenilaianNorma.getEvaluatedUser())
                    .evaluator(borangPenilaianNorma.getEvaluator())
                    .acceptedByEvaluator(borangPenilaianNorma.isAcceptedByEvaluator())
                    .build())
                // .borangPenilaian(mapper.convertValue(borangPenilaian, BorangPenilaian.class))
                .listPesan(pesan)
                .idUserLogin(idUserLogin)
                .build();
    }
}

}
