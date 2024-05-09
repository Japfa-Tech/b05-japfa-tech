package com.propensi.sikpi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.propensi.sikpi.DTO.request.BorangPenilaianDTO;
import com.propensi.sikpi.DTO.request.BorangPenilaianIKIDTO;
import com.propensi.sikpi.DTO.request.BorangPenilaianIKUDTO;
import com.propensi.sikpi.DTO.request.BorangPenilaianNormaDTO;
import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.IndeksKinerjaIndividu;
import com.propensi.sikpi.model.IndeksKinerjaUnit;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianNorma;
import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresIKI;
import com.propensi.sikpi.model.KriteriaScoresIKU;
import com.propensi.sikpi.model.KriteriaScoresNorma;
import com.propensi.sikpi.model.Pesan;
import com.propensi.sikpi.model.TemplatePenilaian;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.BorangPenilaianDb;
import com.propensi.sikpi.repository.BorangPenilaianIKIDb;
import com.propensi.sikpi.repository.BorangPenilaianIKUDb;
import com.propensi.sikpi.repository.BorangPenilaianNormaDb;
import com.propensi.sikpi.repository.KriteriaPenilaianIKUDb;
import com.propensi.sikpi.repository.KriteriaPenilaianNormaDb;
import com.propensi.sikpi.repository.KriteriaScoresDb;
import com.propensi.sikpi.repository.KriteriaScoresIKIDb;
import com.propensi.sikpi.repository.KriteriaScoresIKUDb;
import com.propensi.sikpi.repository.KriteriaScoresNormaDb;
import com.propensi.sikpi.repository.PesanDb;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.BorangPenilaianService;
import com.propensi.sikpi.service.PesanService;
import com.propensi.sikpi.service.TemplateService;
import com.propensi.sikpi.service.UnitService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BorangPenilaianController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private PesanService pesanService;

    @Autowired
    private BorangPenilaianIKIDb borangPenilaianIKIDb;

    @Autowired
    private BorangPenilaianIKUDb borangPenilaianIKUDb;

    @Autowired
    private BorangPenilaianNormaDb borangPenilaianNormaDb;

    @Autowired
    private BorangPenilaianService borangPenilaianService;

    @Autowired
    private UserDb userDb;

    @Autowired
    private PesanDb pesanDb;

    @Autowired
    private KriteriaScoresDb kriteriaScoresDb;
    
    @Autowired
    private UnitService unitService;

    @Autowired
    private KriteriaPenilaianNormaDb kriteriaPenilaianNormaDb;

    @Autowired
    private KriteriaScoresNormaDb kriteriaScoresNormaDb;

    @Autowired
    private KriteriaScoresIKUDb kriteriaScoresIKUDb;

    @Autowired
    private KriteriaScoresIKIDb kriteriaScoresIKIDb;

    @Autowired
    private KriteriaPenilaianIKUDb kriteriaPenilaianIKUDb;

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                return userDb.findByUsername(username).getId();
            }
        }
        // Return a default value or handle as needed
        return null;
    }

    @GetMapping("/borang/template-penilaian-iki")
    public String viewAllIki(Model model) {
        UserModel user = userDb.findById(getUserId()).get();

        // List<IndeksKinerjaIndividu> listIki = templateService.getAllIki();
        List<BorangPenilaianIKI> listIki = borangPenilaianIKIDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);


        model.addAttribute("listIki", listIki);
        model.addAttribute("idUser", user.getId());
        model.addAttribute("userName", user.getNamaLengkap());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        // model.addAttribute("idUser", getUserId());
        return "viewall-iki-borang";
    }

    @GetMapping("/borang/template-penilaian-iki/{id}")
    public String fillIki(@PathVariable Long id, Model model) {
        UserModel user = userDb.findById(getUserId()).get();

        IndeksKinerjaIndividu iki = templateService.getIkiById(id); // Add a method in your service to get the template
                                                                    // by ID
        for (KriteriaPenilaian kp : iki.getListKriteria()) {
            System.out.println(kp.getJudulKriteria());
        }
        System.out.println(iki.getListKriteria().size());
        model.addAttribute("ikiDTO", iki);
        model.addAttribute("existingList", iki.getListKriteria());
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        // model.addAttribute("idUser", getUserId());
        return "form-borang";
    }

    @PostMapping("/borang/template-penilaian-iki/{id}")
    public String submitIki(@PathVariable Long id, @ModelAttribute BorangPenilaianIKIDTO borangPenilaianIKIDTO) {
        List<BorangPenilaianIKI> checkBorang = borangPenilaianIKIDb.findByIdTemplate(id);
        if (!checkBorang.isEmpty()) {
            for (BorangPenilaianIKI borang : checkBorang) {
                borang.setIsDeleted(true);
            }
        }
        IndeksKinerjaIndividu iki = templateService.getIkiById(id);
        Long idUser = getUserId();
        UserModel u = userDb.findById(getUserId()).get();
        BorangPenilaianIKI borang = new BorangPenilaianIKI();
        UserModel evaluatedUser = userDb.findById(iki.getEvaluatedUser()).get();

        // borang.setKriteriaScores(borangPenilaianDTO.getKriteriaScores());
        borang.setStatus("pending");
        if (u.getRole().getRole().equals("KepalaUnit") && evaluatedUser instanceof Karyawan) {
            borang.setStatus("on progress");
            borang.setTemplateAccepted(true);
        }

        if (u.getRole().getRole().equals("Manajer") && evaluatedUser instanceof KepalaUnit) {
            borang.setStatus("on progress");
            borang.setTemplateAccepted(true);
        }
        borang.setIdTemplate(id);
        borangPenilaianIKIDb.save(borang);
        for (List<Integer> ksDTO : borangPenilaianIKIDTO.getKriteriaScoresIKI()) {
            if(ksDTO.size() == 1){
                continue;
            }
            Long kriteria = Long.valueOf(ksDTO.get(0));
            Integer skor = ksDTO.get(1);
            KriteriaScoresIKI ks = new KriteriaScoresIKI();
            ks.setBorangPenilaian(borang);
            ks.setKriteria(borangPenilaianService.getKriteriaPenilaianById(kriteria));
            ks.setScore(skor);
            kriteriaScoresIKIDb.save(ks);
        }
        borang.setKriteriaScoresIKI(kriteriaScoresIKIDb.findByBorangPenilaian(borang));

        borangPenilaianService.handleConnection(idUser, borang.getIdBorangPenilaian(), "IKI");
        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUser());

        if (idUser == borang.getEvaluatedUser()) return "redirect:/template-penilaian-iki";
        else return "redirect:/borang/template-penilaian-iki";
    }

    @GetMapping("/borang/edit/template-penilaian-iki/{idBorangPenilaian}")
    public String editIki(Model model, @PathVariable Long idBorangPenilaian) {
        BorangPenilaianIKI borangPenilaian = borangPenilaianIKIDb.findById(idBorangPenilaian).get();
        IndeksKinerjaIndividu iki = templateService.getIkiById(borangPenilaian.getIdTemplate()); // Add a method in your
                                                                                                 // service to get the
                                                                                                 // template
        // by ID
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("ikiDTO", iki);
        model.addAttribute("existingList", iki.getListKriteria());
        model.addAttribute("borangPenilaian", borangPenilaian); // Pass borangPenilaian as a model attribute
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        System.out.println(borangPenilaian.getKriteriaScores().toString());
        System.out.println(borangPenilaian.getListAkses().toString());
        // model.addAttribute("idUser", getUserId());
        return "form-borang-edit";
    }

    @PostMapping("/borang/edit/template-penilaian-iki/{idBorangPenilaian}")
    public String editIki(Model model, @PathVariable Long idBorangPenilaian,
            @ModelAttribute BorangPenilaianDTO borangPenilaianDTO) {
        BorangPenilaianIKI borang = borangPenilaianIKIDb.findById(idBorangPenilaian).get();
        List<List<Integer>> kriteriaScoresDTO = borangPenilaianDTO.getKriteriaScores();
        List<KriteriaScores> kriteriaScores = borang.getKriteriaScores();

        // borang.setKriteriaScores(borangPenilaianDTO.getKriteriaScores());
        borang.setStatus("pending");
        for (int i = 0; i < kriteriaScores.size(); i++) {
            Integer skor = kriteriaScoresDTO.get(i).get(1);
            KriteriaScores ks = kriteriaScores.get(i);
            ks.setScore(skor);
            kriteriaScoresDb.save(ks);
        }

        borangPenilaianIKIDb.save(borang);

        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUser());
        return "redirect:/borang/template-penilaian-iki";
    }

    @GetMapping("/borang/template-penilaian-iku")
    public String viewAllIku(Model model) {

        List<IndeksKinerjaUnit> listIku = templateService.getAllIku();
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("listIku", listIku);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        // model.addAttribute("idUser", getUserId());
        return "viewall-iku-borang";
    }

    @GetMapping("/borang/template-penilaian-iku/{id}")
    public String fillIku(@PathVariable Long id, Model model) {
        IndeksKinerjaUnit iku = templateService.getIkuById(id); // Add a method in your service to get the template
                                                                // by ID
        UserModel user = userDb.findById(getUserId()).get();

        System.out.println(iku.getListKriteriaIKU().toString());
        model.addAttribute("ikuDTO", iku);
        model.addAttribute("existingList", iku.getListKriteriaIKU());
        model.addAttribute("idUser", user.getId());
        System.out.println("pep");
        System.out.println(iku.getIdUnit());
        model.addAttribute("unitUser", iku.getIdUnit());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        // model.addAttribute("idUser", getUserId());
        return "form-borang-iku";
    }

    @PostMapping("/borang/template-penilaian-iku/{id}")
    public String submitIku(@PathVariable Long id, @ModelAttribute BorangPenilaianIKUDTO borangPenilaianIKUDTO) {
        List<BorangPenilaianIKU> checkBorang = borangPenilaianIKUDb.findByIdTemplate(id);
        if (!checkBorang.isEmpty()) {
            for (BorangPenilaianIKU borang : checkBorang) {
                borang.setIsDeleted(true);
            }
        }
        Long idUser = getUserId();
        BorangPenilaianIKU borang = new BorangPenilaianIKU();

        borang.setIdTemplate(id);
        borang.setStatus("pending");
        borangPenilaianIKUDb.save(borang);
        for (List<Integer> ksDTO : borangPenilaianIKUDTO.getKriteriaScoresIKU()) {
            Long kriteria = Long.valueOf(ksDTO.get(0));
            Integer skor = ksDTO.get(1);
            KriteriaScoresIKU ks = new KriteriaScoresIKU();
            ks.setBorangPenilaian(borang);
            ks.setKriteria(kriteriaPenilaianIKUDb.findById(kriteria).get());
            // ks.setKriteria(borangPenilaianService.getKriteriaPenilaianById(kriteria));
            ks.setScore(skor);
            System.out.println(skor);
            System.out.println(kriteria);
            kriteriaScoresIKUDb.save(ks);
        }
        borang.setKriteriaScoresIKU(kriteriaScoresIKUDb.findByBorangPenilaian(borang));

        borangPenilaianService.handleConnection(idUser, borang.getIdBorangPenilaian(), "IKU");
        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUnit());
        return "redirect:/template-penilaian-iku";
    }

    @GetMapping("/borang/edit/template-penilaian-iku/{idBorangPenilaian}")
    public String editIku(Model model, @PathVariable Long idBorangPenilaian) {
        BorangPenilaianIKU borangPenilaian = borangPenilaianIKUDb.findById(idBorangPenilaian).get();
        IndeksKinerjaUnit iku = templateService.getIkuById(borangPenilaian.getIdTemplate()); // Add a method in your
                                                                                             // service to get the
                                                                                             // template
        // by ID
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("ikuDTO", iku);
        model.addAttribute("existingList", iku.getListKriteria());
        model.addAttribute("borangPenilaian", borangPenilaian); // Pass borangPenilaian as a model attribute
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        System.out.println(borangPenilaian.getKriteriaScores().toString());
        System.out.println(borangPenilaian.getListAkses().toString());
        // model.addAttribute("idUser", getUserId());
        return "form-edit-borang-iku";
    }

    @PostMapping("/borang/edit/template-penilaian-iku/{idBorangPenilaian}")
    public String editIku(Model model, @PathVariable Long idBorangPenilaian,
            @ModelAttribute BorangPenilaianDTO borangPenilaianDTO) {
        BorangPenilaianIKU borang = borangPenilaianIKUDb.findById(idBorangPenilaian).get();
        List<List<Integer>> kriteriaScoresDTO = borangPenilaianDTO.getKriteriaScores();
        List<KriteriaScores> kriteriaScores = borang.getKriteriaScores();

        // borang.setKriteriaScores(borangPenilaianDTO.getKriteriaScores());
        borang.setStatus("pending");
        for (int i = 0; i < kriteriaScores.size(); i++) {
            Integer skor = kriteriaScoresDTO.get(i).get(1);
            KriteriaScores ks = kriteriaScores.get(i);
            ks.setScore(skor);
            kriteriaScoresDb.save(ks);
        }

        borangPenilaianIKUDb.save(borang);

        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUnit());
        return "redirect:/borang/template-penilaian-iku";
    }

    @GetMapping("/borang/template-penilaian-norma")
    public String viewAllNorma(Model model) {

        List<Norma> listNorma = templateService.getAllNorma();
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("listNorma", listNorma);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        // model.addAttribute("idUser", getUserId());
        return "viewall-norma-borang";
    }

    @GetMapping("/borang/template-penilaian-norma/{id}/{idEvaluatedUser}")
    public String fillNorma(@PathVariable Long id, @PathVariable Long idEvaluatedUser, Model model) {
        Norma norma = templateService.getNormaById(id); // Add a method in your service to get the template
                                                        // by ID
        UserModel user = userDb.findById(getUserId()).get();
        model.addAttribute("normaDTO", norma);
        model.addAttribute("idTemplatePenilaian", id);
        // model.addAttribute(id, user)
       for (KriteriaPenilaianNorma kp : norma.getListKriteriaNorma()) {
            System.out.println(kp.getJudulKriteria());
       }
        model.addAttribute("existingList", norma.getListKriteriaNorma());
        model.addAttribute("listKriteria", norma.getListKriteriaNorma());
        model.addAttribute("idUser", getUserId());
        model.addAttribute("idEvaluatedUser", idEvaluatedUser);
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        // model.addAttribute("idUser", getUserId());
        return "form-borang-norma";
    }

    @PostMapping("/borang/template-penilaian-norma/{id}/{idEvaluatedUser}")
    public String submitNorma(@PathVariable Long id, @PathVariable Long idEvaluatedUser, @ModelAttribute BorangPenilaianNormaDTO borangPenilaianNormaDTO) {
        List<BorangPenilaianNorma> checkBorang = borangPenilaianNormaDb.findByIdTemplate(id);
        if (!checkBorang.isEmpty()) {
            for (BorangPenilaianNorma borang : checkBorang) {
                borang.setIsDeleted(true);
            }
        }
        Long idUser = getUserId();
        BorangPenilaianNorma borang = new BorangPenilaianNorma();

        // borang.setKriteriaScores(borangPenilaianDTO.getKriteriaScores());
        borang.setStatus("pending");
        borang.setIdTemplate(id);
        borangPenilaianNormaDb.save(borang);
        for (List<Integer> ksDTO : borangPenilaianNormaDTO.getKriteriaScoresNorma()) {
            Long kriteria = Long.valueOf(ksDTO.get(0));
            Integer skor = ksDTO.get(1);
            KriteriaScoresNorma ks = new KriteriaScoresNorma();
            ks.setBorangPenilaian(borang);
            ks.setKriteriaNorma(kriteriaPenilaianNormaDb.findById(kriteria).get());
            ks.setScore(skor);
            kriteriaScoresNormaDb.save(ks);
        }
        borang.setKriteriaScores(kriteriaScoresDb.findByBorangPenilaian(borang));

        borangPenilaianService.handleConnectionNorma(idUser, idEvaluatedUser, borang.getIdBorangPenilaian());
        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUser());
        return "redirect:/template-penilaian-norma";
    }

    @GetMapping("/borang/edit/template-penilaian-norma/{idBorangPenilaian}")
    public String editNorma(Model model, @PathVariable Long idBorangPenilaian) {
        BorangPenilaianNorma borangPenilaian = borangPenilaianNormaDb.findById(idBorangPenilaian).get();
        Norma norma = templateService.getNormaById(borangPenilaian.getIdTemplate());
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("normaDTO", norma);
        model.addAttribute("existingList", norma.getListKriteria());
        model.addAttribute("borangPenilaian", borangPenilaian); // Pass borangPenilaian as a model attribute
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        System.out.println(borangPenilaian.getKriteriaScores().toString());
        System.out.println(borangPenilaian.getListAkses().toString());
        // model.addAttribute("idUser", getUserId());
        return "form-edit-borang-norma";
    }

    @PostMapping("/borang/edit/template-penilaian-norma/{idBorangPenilaian}")
    public String editNorma(Model model, @PathVariable Long idBorangPenilaian,
            @ModelAttribute BorangPenilaianDTO borangPenilaianDTO) {
        BorangPenilaianNorma borang = borangPenilaianNormaDb.findById(idBorangPenilaian).get();
        List<List<Integer>> kriteriaScoresDTO = borangPenilaianDTO.getKriteriaScores();
        List<KriteriaScores> kriteriaScores = borang.getKriteriaScores();

        // borang.setKriteriaScores(borangPenilaianDTO.getKriteriaScores());
        borang.setStatus("pending");
        for (int i = 0; i < kriteriaScores.size(); i++) {
            Integer skor = kriteriaScoresDTO.get(i).get(1);
            KriteriaScores ks = kriteriaScores.get(i);
            ks.setScore(skor);
            kriteriaScoresDb.save(ks);
        }

        borangPenilaianNormaDb.save(borang);

        System.out.println(borang.getKriteriaScores().toString());
        System.out.println(borang.getListAkses().toString());
        System.out.println(borang.getEvaluatedUser());
        return "redirect:/borang/template-penilaian-norma";
    }

    @GetMapping("/borang/template-penilaian-iki/{id}/status")
    public String getAcceptedStatus(Model model, Long idBorangPenilaian) {
        BorangPenilaianIKI acceptstatus = borangPenilaianService.getAcceptedStatus(idBorangPenilaian);
        model.addAttribute("acceptstatus", acceptstatus);
        return "form-borang";
    }

    @PostMapping("/borang/template-penilaian-iki/{id}/status")
    public String updateAcceptStatus(boolean acceptedByEvaluator, Model model, Long idBorangPenilaian) {
        borangPenilaianService.updateAcceptedStatus(acceptedByEvaluator, idBorangPenilaian);
        BorangPenilaianIKI acceptStatus = borangPenilaianService.getAcceptedStatus(idBorangPenilaian);
        model.addAttribute("acceptStatus", acceptStatus);
        return "redirect:/form-borang";
    }


    @GetMapping("/dashboard-penilaian/{id}")
    public String viewDashboardPenilaian(@PathVariable Long id, Model model) {
    UserModel user = userDb.findById(id).get();
    Long unitId = unitService.getUnitIdForUser(id);
    List<BorangPenilaianIKI> borangIki = borangPenilaianService.filterIKIByUser(id);
    List<BorangPenilaianIKU> borangIku = borangPenilaianService.filterIKUByUnit(unitId);
    List<BorangPenilaianNorma> borangNorma = borangPenilaianService.filterNormaByUnit(id);

    model.addAttribute("borangIki", borangIki);
    model.addAttribute("borangIku", borangIku);
    model.addAttribute("borangNorma", borangNorma);
    model.addAttribute("idUser", getUserId());
    model.addAttribute("loggedInUserRole", user.getRole().getRole());
    // model.addAttribute("idUser", getUserId());
    return "dashboard-list-penilaian";
    }

    @GetMapping("/dashboard-penilaian/top/{id}")
    public String viewDashboardPenilaianPenilai(@PathVariable Long id, Model model) {
    UserModel user = userDb.findById(id).get();
    Long unitId = unitService.getUnitIdForUser(id);

    model.addAttribute("idUser", getUserId());
    model.addAttribute("loggedInUserRole", user.getRole().getRole());
    model.addAttribute("idUser", getUserId());

    List<BorangPenilaianIKI> borangIkiEvaluate = borangPenilaianIKIDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);
    List<BorangPenilaianIKU> borangIkuEvaluate = borangPenilaianIKUDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);
    List<BorangPenilaianNorma> borangNormaEvaluate = borangPenilaianNormaDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);

    model.addAttribute("borangIkiEvaluate", borangIkiEvaluate);
    model.addAttribute("borangIkuEvaluate", borangIkuEvaluate);
    model.addAttribute("borangNormaEvaluate", borangNormaEvaluate);


    return "dashboard-list-penilaian-top";
    }

    @GetMapping("/")
    public String viewDashboardPenilaianPenilai(Model model) {
    Long id = getUserId();
    UserModel user = userDb.findById(id).get();
    Long unitId = unitService.getUnitIdForUser(id);

    model.addAttribute("idUser", getUserId());
    model.addAttribute("loggedInUserRole", user.getRole().getRole());

    List<BorangPenilaianIKI> borangIkiEvaluate = borangPenilaianIKIDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);
    List<BorangPenilaianIKU> borangIkuEvaluate = borangPenilaianIKUDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);
    List<BorangPenilaianNorma> borangNormaEvaluate = borangPenilaianNormaDb.findByEvaluatorAndIsDeletedNot(user.getId(), true);

    model.addAttribute("borangIkiEvaluate", borangIkiEvaluate);
    model.addAttribute("borangIkuEvaluate", borangIkuEvaluate);
    model.addAttribute("borangNormaEvaluate", borangNormaEvaluate);

    List<BorangPenilaianIKI> borangIki = borangPenilaianService.filterIKIByUser(id);
    List<BorangPenilaianIKU> borangIku = borangPenilaianService.filterIKUByUnit(unitId);
    List<BorangPenilaianNorma> borangNorma = borangPenilaianService.filterNormaByUnit(id);

    model.addAttribute("borangIki", borangIki);
    model.addAttribute("borangIku", borangIku);
    model.addAttribute("borangNorma", borangNorma);

    if (user.getRole().getRole().equals("Karyawan")) return "redirect:/dashboard-penilaian/"+id;
    else return "dashboard-list-penilaian-top";
    }

}
