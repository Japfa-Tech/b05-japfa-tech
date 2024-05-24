package com.propensi.sikpi.controller;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import com.propensi.sikpi.model.IndeksKinerjaIndividu;
import com.propensi.sikpi.model.IndeksKinerjaUnit;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianIKU;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.TemplatePenilaian;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.TemplatePenilaianDb;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.repository.IndeksKinerjaIndividuDb;
import com.propensi.sikpi.repository.KriteriaPenilaianDb;
import com.propensi.sikpi.repository.KriteriaPenilaianIKUDb;
import com.propensi.sikpi.service.TemplateService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TemplatePenilaianController {
    
    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplatePenilaianDb templatePenilaianDb;

    @Autowired
    private KriteriaPenilaianDb kriteriaPenilaianDb;

    @Autowired
    private UserDb userDb;

    @Autowired
    IndeksKinerjaIndividuDb indeksKinerjaIndividuDb;

    @Autowired
    private KriteriaPenilaianIKUDb kriteriaPenilaianIKUDb;

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
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


    @GetMapping("/viewall-template")
    public String viewAllTemplates(Model model) {
        List<IndeksKinerjaIndividu> listIki = templateService.getAllIki();
        List<IndeksKinerjaUnit> listIku = templateService.getAllIku();
        List<Norma> listNorma = templateService.getAllNorma();
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("listIki", listIki);
        model.addAttribute("listIku", listIku);
        model.addAttribute("listNorma", listNorma);
        model.addAttribute("idUser", user.getId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "viewall-template";
    }

    @GetMapping("/template-penilaian-iki")
    public String viewAllIki(Model model) {

        List<IndeksKinerjaIndividu> listIki = indeksKinerjaIndividuDb.findByEvaluatedUser(getUserId());
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("listIki", listIki);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "viewall-iki";
    }

    @GetMapping("/template-penilaian-iki/create")
    public String formIki(Model model) {
        
        IndeksKinerjaIndividu ikiDTO = new IndeksKinerjaIndividu();
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("ikiDTO", ikiDTO);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-iki";
    }

    @PostMapping(value = "/template-penilaian-iki/create", params = {"addRow"})
    public String addRowIki(@ModelAttribute IndeksKinerjaIndividu ikiDTO, Model model) {
        if (ikiDTO.getListKriteria() == null || ikiDTO.getListKriteria().size() == 0) {
            ikiDTO.setListKriteria(new ArrayList<>());
        }

        ikiDTO.getListKriteria().add(new KriteriaPenilaian());

        model.addAttribute("ikiDTO", ikiDTO);
        return "form-iki";
    }

    @PostMapping("/template-penilaian-iki/create")
    public String postFormIki(@ModelAttribute IndeksKinerjaIndividu ikiDTO, Model model) {
        List<KriteriaPenilaian> listDTO = ikiDTO.getListKriteria();
        var indeksKinerjaIndividu = new IndeksKinerjaIndividu();
        templateService.createTemplatePenilaian(indeksKinerjaIndividu);
        UserModel evaluatedUser = userDb.findById(getUserId()).get();

        for (KriteriaPenilaian kpDTO : listDTO) {
            KriteriaPenilaian kriteriaPenilaian = new KriteriaPenilaian();
            kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
            kriteriaPenilaian.setBobot(kpDTO.getBobot());
            kriteriaPenilaian.setSkor(0);

            kriteriaPenilaian.setTemplatePenilaian(indeksKinerjaIndividu);
            kriteriaPenilaianDb.save(kriteriaPenilaian);
        }
        indeksKinerjaIndividu.setEvaluatedUser(evaluatedUser.getId());
        indeksKinerjaIndividu.setEvaluatedUserName(evaluatedUser.getNamaLengkap());
        indeksKinerjaIndividu.setNamaTemplate("Template IKI " + evaluatedUser.getUsername());
        templateService.createTemplatePenilaian(indeksKinerjaIndividu);

        return "success-create-iki";
    }

    @GetMapping("template-penilaian-iki/delete/{id}")
    public String deleteIki(@PathVariable Long id, Model model) {
        IndeksKinerjaIndividu indeksKinerjaIndividu = templateService.getIkiById(id);
        templateService.deleteTemplatePenilaian(indeksKinerjaIndividu);
        return "success-delete-iki";
    }

    @GetMapping("/template-penilaian-iki/edit/{id}")
    public String editIki(@PathVariable Long id, Model model) {
        IndeksKinerjaIndividu iki = templateService.getIkiById(id); // Add a method in your service to get the template by ID
        for (KriteriaPenilaian kp : iki.getListKriteria()) {
            System.out.println(kp.getJudulKriteria());
        }
        System.out.println(iki.getListKriteria().size());
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("ikiDTO", iki);
        model.addAttribute("existingList", iki.getListKriteria());
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-ubah-iki";
    }

    @PostMapping("/template-penilaian-iki/edit/{id}")
    public String updateIki(@PathVariable Long id, @ModelAttribute IndeksKinerjaIndividu ikiDTO) {
    // Get the existing IndeksKinerjaIndividu from the database
    IndeksKinerjaIndividu indeksKinerjaIndividu = templateService.getIkiById(id);

    // Update the properties of the existing IndeksKinerjaIndividu
    indeksKinerjaIndividu.setNamaTemplate("Template IKI 1");

    // Clear existing KriteriaPenilaian entities and add the new ones
    List<KriteriaPenilaian> existingKriteria = indeksKinerjaIndividu.getListKriteria();
    existingKriteria.clear();  // Remove existing KriteriaPenilaian entities

    List<KriteriaPenilaian> newKriteriaList = ikiDTO.getListKriteria();
    for (KriteriaPenilaian kpDTO : newKriteriaList) {
        KriteriaPenilaian kriteriaPenilaian = new KriteriaPenilaian();
        kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
        kriteriaPenilaian.setBobot(kpDTO.getBobot());
        kriteriaPenilaian.setSkor(0);
        kriteriaPenilaian.setTemplatePenilaian(indeksKinerjaIndividu);

        // Add the new KriteriaPenilaian entity to the list
        existingKriteria.add(kriteriaPenilaian);
    }

    // Save the updated IndeksKinerjaIndividu entity
    templateService.updateTemplatePenilaian(indeksKinerjaIndividu);

    return "success-update-iki";
    }

    @PostMapping(value = "/template-penilaian-iki/edit/{id}", params = {"addRow"})
    public String addRowUpdateIki(@PathVariable Long id, @ModelAttribute IndeksKinerjaIndividu ikiDTO, Model model) {
        if (ikiDTO.getListKriteria() == null || ikiDTO.getListKriteria().size() == 0) {
            ikiDTO.setListKriteria(new ArrayList<>());
        }

        ikiDTO.getListKriteria().add(new KriteriaPenilaian());

        model.addAttribute("ikiDTO", ikiDTO);
        return "form-ubah-iki";
    }

    
    
    
    
    @GetMapping("/template-penilaian-iku")
    public String viewAllIku(Model model) {

        List<IndeksKinerjaUnit> listIku = templateService.getAllIku();
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("listIku", listIku);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "viewall-iku";
    }

    @GetMapping("/template-penilaian-iku/create")
    public String formIku(Model model) {
        
        IndeksKinerjaUnit ikuDTO = new IndeksKinerjaUnit();
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("ikuDTO", ikuDTO);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-iku";
    }

    @PostMapping(value = "/template-penilaian-iku/create", params = {"addRow"})
    public String addRowIku(@ModelAttribute IndeksKinerjaUnit ikuDTO, Model model) {
        if (ikuDTO.getListKriteriaIKU() == null || ikuDTO.getListKriteriaIKU().size() == 0) {
            ikuDTO.setListKriteriaIKU(new ArrayList<>());
        }

        ikuDTO.getListKriteriaIKU().add(new KriteriaPenilaianIKU());

        model.addAttribute("ikuDTO", ikuDTO);
        return "form-iku";
    }

    @PostMapping("/template-penilaian-iku/create")
    public String postFormIku(@ModelAttribute IndeksKinerjaUnit ikuDTO, Model model) {
        KepalaUnit kepalaUnit = (KepalaUnit) userDb.findById(getUserId()).get();
        List<KriteriaPenilaianIKU> listDTO = ikuDTO.getListKriteriaIKU();
        var indeksKinerjaUnit = new IndeksKinerjaUnit();
        templateService.createTemplatePenilaian(indeksKinerjaUnit);

        for (KriteriaPenilaianIKU kpDTO : listDTO) {
            KriteriaPenilaianIKU kriteriaPenilaian = new KriteriaPenilaianIKU();
            kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
            kriteriaPenilaian.setBobot(kpDTO.getBobot());
            kriteriaPenilaian.setSkor(0);

            kriteriaPenilaian.setTemplatePenilaian(indeksKinerjaUnit);
            kriteriaPenilaianIKUDb.save(kriteriaPenilaian);
        }
        indeksKinerjaUnit.setNamaTemplate("Template IKU ");
        indeksKinerjaUnit.setIdUnit(kepalaUnit.getUnitKu().getId());
        templateService.createTemplatePenilaian(indeksKinerjaUnit);

        return "success-create-iku";
    }

    @GetMapping("template-penilaian-iku/delete/{id}")
    public String deleteIku(@PathVariable Long id, Model model) {
        IndeksKinerjaUnit indeksKinerjaUnit = templateService.getIkuById(id);
        templateService.deleteTemplatePenilaian(indeksKinerjaUnit);
        return "success-delete-iku";
    }

    @GetMapping("/template-penilaian-iku/edit/{id}")
    public String editIku(@PathVariable Long id, Model model) {
        IndeksKinerjaUnit iku = templateService.getIkuById(id); // Add a method in your service to get the template by ID
        for (KriteriaPenilaianIKU kp : iku.getListKriteriaIKU()) {
            System.out.println(kp.getJudulKriteria());
        }
        System.out.println(iku.getListKriteriaIKU().size());
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("ikuDTO", iku);
        model.addAttribute("existingList", iku.getListKriteriaIKU());
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-ubah-iku";
    }

    @PostMapping("/template-penilaian-iku/edit/{id}")
    public String updateIku(@PathVariable Long id, @ModelAttribute IndeksKinerjaUnit ikuDTO) {
    // Get the existing IndeksKinerjaUnit from the database
    IndeksKinerjaUnit indeksKinerjaUnit = templateService.getIkuById(id);

    // Update the properties of the existing IndeksKinerjaUnit
    indeksKinerjaUnit.setNamaTemplate("Template IKU 1");

    // Clear existing KriteriaPenilaian entities and add the new ones
    List<KriteriaPenilaianIKU> existingKriteria = indeksKinerjaUnit.getListKriteriaIKU();
    existingKriteria.clear();  // Remove existing KriteriaPenilaian entities

    List<KriteriaPenilaianIKU> newKriteriaList = ikuDTO.getListKriteriaIKU();
    for (KriteriaPenilaianIKU kpDTO : newKriteriaList) {
        KriteriaPenilaianIKU kriteriaPenilaian = new KriteriaPenilaianIKU();
        kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
        kriteriaPenilaian.setBobot(kpDTO.getBobot());
        kriteriaPenilaian.setSkor(0);
        kriteriaPenilaian.setTemplatePenilaian(indeksKinerjaUnit);

        // Add the new KriteriaPenilaian entity to the list
        existingKriteria.add(kriteriaPenilaian);
    }

    // Save the updated IndeksKinerjaUnit entity
    templateService.updateTemplatePenilaian(indeksKinerjaUnit);

    return "success-update-iku";
    }

    @PostMapping(value = "/template-penilaian-iku/edit/{id}", params = {"addRow"})
    public String addRowUpdateIku(@PathVariable Long id, @ModelAttribute IndeksKinerjaUnit ikuDTO, Model model) {
        if (ikuDTO.getListKriteriaIKU() == null || ikuDTO.getListKriteriaIKU().size() == 0) {
            ikuDTO.setListKriteriaIKU(new ArrayList<>());
        }

        ikuDTO.getListKriteriaIKU().add(new KriteriaPenilaianIKU());

        model.addAttribute("ikuDTO", ikuDTO);
        return "form-ubah-iku";
    }

    @GetMapping("/template-penilaian-norma")
    public String viewAllNorma(Model model) {

        List<Norma> listNorma = templateService.getAllNorma();
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("listNorma", listNorma);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "viewall-norma";
    }

    @GetMapping("/template-penilaian-norma/create")
    public String formNorma(Model model) {
        
        Norma normaDTO = new Norma();
        UserModel user = userDb.findById(getUserId()).get();


        model.addAttribute("normaDTO", normaDTO);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-norma";
    }

    @PostMapping(value = "/template-penilaian-norma/create", params = {"addRow"})
    public String addRowNorma(@ModelAttribute Norma normaDTO, Model model) {
        if (normaDTO.getListKriteria() == null || normaDTO.getListKriteria().size() == 0) {
            normaDTO.setListKriteria(new ArrayList<>());
        }

        normaDTO.getListKriteria().add(new KriteriaPenilaian());

        model.addAttribute("normaDTO", normaDTO);
        return "form-norma";
    }

    @GetMapping("/template-penilaian-norma/edit/{id}")
    public String editNorma(@PathVariable Long id, Model model) {
        IndeksKinerjaIndividu norma = templateService.getIkiById(id); // Add a method in your service to get the template by ID
        for (KriteriaPenilaian kp : norma.getListKriteria()) {
            System.out.println(kp.getJudulKriteria());
        }
        System.out.println(norma.getListKriteria().size());
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("normaDTO", norma);
        model.addAttribute("existingList", norma.getListKriteria());
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());

        return "form-ubah-norma";
    }


    @PostMapping("/template-penilaian-norma/create")
    public String postFormNorma(@ModelAttribute Norma normaDTO, Model model) {
        List<KriteriaPenilaian> listDTO = normaDTO.getListKriteria();
        var norma = new Norma();
        templateService.createTemplatePenilaian(norma);

        for (KriteriaPenilaian kpDTO : listDTO) {
            KriteriaPenilaian kriteriaPenilaian = new KriteriaPenilaian();
            kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
            kriteriaPenilaian.setBobot(kpDTO.getBobot());
            kriteriaPenilaian.setSkor(0);

            kriteriaPenilaian.setTemplatePenilaian(norma);
            kriteriaPenilaianDb.save(kriteriaPenilaian);
        }
        norma.setNamaTemplate("Norma");
        templateService.createTemplatePenilaian(norma);

        return "success-create-norma";
    }

    @GetMapping("template-penilaian-norma/delete/{id}")
    public String deleteNorma(@PathVariable Long id, Model model) {
        Norma norma = templateService.getNormaById(id);
        templateService.deleteTemplatePenilaian(norma);
        return "success-delete-norma";
    }

    @PostMapping("/template-penilaian-norma/edit/{id}")
    public String updateNorma(@PathVariable Long id, @ModelAttribute Norma normaDTO) {
    // Get the existing IndeksKinerjaUnit from the database
    Norma norma = templateService.getNormaById(id);

    // Update the properties of the existing IndeksKinerjaUnit
    norma.setNamaTemplate("Norma");

    // Clear existing KriteriaPenilaian entities and add the new ones
    List<KriteriaPenilaian> existingKriteria = norma.getListKriteria();
    existingKriteria.clear();  // Remove existing KriteriaPenilaian entities

    List<KriteriaPenilaian> newKriteriaList = normaDTO.getListKriteria();
    for (KriteriaPenilaian kpDTO : newKriteriaList) {
        KriteriaPenilaian kriteriaPenilaian = new KriteriaPenilaian();
        kriteriaPenilaian.setJudulKriteria(kpDTO.getJudulKriteria());
        kriteriaPenilaian.setBobot(kpDTO.getBobot());
        kriteriaPenilaian.setSkor(0);
        kriteriaPenilaian.setTemplatePenilaian(norma);

        // Add the new KriteriaPenilaian entity to the list
        existingKriteria.add(kriteriaPenilaian);
    }

    // Save the updated IndeksKinerjaUnit entity
    templateService.updateTemplatePenilaian(norma);

    return "success-update-norma";
    }

    @PostMapping(value = "/template-penilaian-norma/edit/{id}", params = {"addRow"})
    public String addRowUpdateNorma(@PathVariable Long id, @ModelAttribute Norma normaDTO, Model model) {
        if (normaDTO.getListKriteria() == null || normaDTO.getListKriteria().size() == 0) {
            normaDTO.setListKriteria(new ArrayList<>());
        }

        normaDTO.getListKriteria().add(new KriteriaPenilaian());

        model.addAttribute("normaDTO", normaDTO);
        return "form-ubah-norma";
    }

}
