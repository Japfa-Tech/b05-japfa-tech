package com.propensi.sikpi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.propensi.sikpi.DTO.DokumenMapper;
import com.propensi.sikpi.DTO.RiwayatJabatanMapper;
import com.propensi.sikpi.DTO.RiwayatPenugasanMapper;
import com.propensi.sikpi.DTO.UserMapper;
import com.propensi.sikpi.DTO.request.CreateDokumenRequestDTO;
import com.propensi.sikpi.DTO.request.CreateRiwayatJabatanRequestDTO;
import com.propensi.sikpi.DTO.request.CreateRiwayatPenugasanRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.model.Cabinet;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresIKI;
import com.propensi.sikpi.model.KriteriaScoresIKU;
import com.propensi.sikpi.model.Manajer;
import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.SDM;
import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.BorangPenilaianService;
import com.propensi.sikpi.service.DokumenService;
import com.propensi.sikpi.service.PdfGenerateService;
import com.propensi.sikpi.service.RaporService;
import com.propensi.sikpi.service.RiwayatJabatanService;
import com.propensi.sikpi.service.RiwayatPenugasanService;
import com.propensi.sikpi.service.UnitService;
import com.propensi.sikpi.service.UserService;
import jakarta.validation.Valid;
import javassist.NotFoundException;

@Controller
public class RaporController {

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Autowired
    private RaporService raporService;

    @Autowired
    private BorangPenilaianService borangPenilaianService;

    @Autowired
    private UserDb userDb;

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

    @GetMapping("/profile/{id}/rapor")
    public String getRapor(@PathVariable("id") Long id, Model model) throws NotFoundException {
        UserModel user = userDb.findById(getUserId()).get();

        var evaluatedUser = userService.getUserById(id);
        var evaluatedUnit = new Unit();
        // var evaluatedUnit = unitService.getUnitByKepalaUnitId(id);
        var evaluator = new UserModel();
        if (evaluatedUser.getRole().getId() == 1) {
            Karyawan ku = (Karyawan) evaluatedUser;
            evaluatedUnit = unitService.getUnitByUserId(ku.getId());
            evaluator = (KepalaUnit) userService.getUserById(ku.getIdKepalaUnit());
        } else if (evaluatedUser.getRole().getId() == 5) {
            KepalaUnit man = (KepalaUnit) evaluatedUser;
            evaluatedUnit = unitService.getUnitByKepalaUnitId(man.getId());
            evaluator = (Manajer) userService.getUserById(man.getIdManajer());
        }
        var rapor = raporService.getRaporByEvaluatedUser(evaluatedUser);
        var iki = borangPenilaianService.getBorangPenilaianIKIByEvaluatedUser(evaluatedUser.getId());
        var iku = borangPenilaianService.getBorangPenilaianIKUByEvaluatedUnit(evaluatedUnit.getId());
        var norma = borangPenilaianService.getBorangPenilaianNormaByEvaluatedUser(evaluatedUser.getId());
        model.addAttribute("totalIki", borangPenilaianService.getTotalBorangIKI(iki.getIdBorangPenilaian()));
        model.addAttribute("totalIku", borangPenilaianService.getTotalBorangIKU(iku.getIdBorangPenilaian()));
        model.addAttribute("totalNorma", borangPenilaianService.getTotalBorangNorma(norma.getIdBorangPenilaian()));
        model.addAttribute("list_kriteria_iki", iki.getKriteriaScoresIKI());
        model.addAttribute("list_kriteria_iku", iku.getKriteriaScoresIKU());
        model.addAttribute("list_kriteria_norma", norma.getKriteriaScoresNorma());
        model.addAttribute("unit", evaluatedUnit);
        model.addAttribute("user", evaluatedUser);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("evaluator", evaluator);
        model.addAttribute("rapor", rapor);
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        return "rapor-view";
    }

    @GetMapping("/profile/{idUser}/rapor/signEvaluated")
    public String signEvaluated(@PathVariable("idUser") Long idUser,
            Model model) {
        var evaluatedUser = userService.getUserById(idUser);
        var rapor = raporService.getRaporByEvaluatedUser(evaluatedUser);
        rapor.setSignEvaluated(true);
        rapor.setSignEvaluatedTime(LocalDateTime.now());
        raporService.saveRapor(rapor);
        return "redirect:/profile/" + idUser + "/rapor";
    }

    @GetMapping("/profile/{idUser}/rapor/signEvaluator")
    public String signEvaluator(@PathVariable("idUser") Long idUser,
            Model model) {
        var evaluatedUser = userService.getUserById(idUser);
        var rapor = raporService.getRaporByEvaluatedUser(evaluatedUser);
        rapor.setSignPenilai(true);
        rapor.setSignPenilaiTime(LocalDateTime.now());
        raporService.saveRapor(rapor);
        return "redirect:/profile/" + idUser + "/rapor";
    }

    @GetMapping("/profile/{idUser}/rapor/signSDM")
    public String signSDM(@PathVariable("idUser") Long idUser,
            Model model) {
        SDM sdm = (SDM) userService.getUserById(getUserId());
        var evaluatedUser = userService.getUserById(idUser);
        var rapor = raporService.getRaporByEvaluatedUser(evaluatedUser);
        rapor.setSignPenyetuju(true);
        rapor.setSdm(sdm);
        rapor.setSignPenyetujuTime(LocalDateTime.now());
        raporService.saveRapor(rapor);
        return "redirect:/profile/" + idUser + "/rapor";
    }

    @GetMapping("/profile/{idUser}/export")
    public ResponseEntity<ByteArrayResource> exportReport(@PathVariable("idUser") Long idUser,
            Model model) throws NotFoundException {
        var evaluatedUser = userService.getUserById(idUser);
        var evaluatedUnit = new Unit();
        // var evaluatedUnit = unitService.getUnitByKepalaUnitId(id);
        var evaluator = new UserModel();
        if (evaluatedUser.getRole().getId() == 1) {
            Karyawan ku = (Karyawan) evaluatedUser;
            evaluatedUnit = unitService.getUnitByUserId(ku.getId());
            evaluator = (KepalaUnit) userService.getUserById(ku.getIdKepalaUnit());
        } else if (evaluatedUser.getRole().getId() == 5) {
            KepalaUnit man = (KepalaUnit) evaluatedUser;
            evaluatedUnit = unitService.getUnitByKepalaUnitId(man.getId());
            evaluator = (Manajer) userService.getUserById(man.getIdManajer());
        }
       var rapor = raporService.getRaporByEvaluatedUser(evaluatedUser);
        var iki = borangPenilaianService.getBorangPenilaianIKIByEvaluatedUser(evaluatedUser.getId());
        var iku = borangPenilaianService.getBorangPenilaianIKUByEvaluatedUnit(evaluatedUnit.getId());
        var norma = borangPenilaianService.getBorangPenilaianNormaByEvaluatedUser(evaluatedUser.getId());
        var totalIki=borangPenilaianService.getTotalBorangIKI(iki.getIdBorangPenilaian());
        var totalIku=borangPenilaianService.getTotalBorangIKU(iku.getIdBorangPenilaian());
        var totalNorma = borangPenilaianService.getTotalBorangNorma(norma.getIdBorangPenilaian());
        var list_kriteria_norma = norma.getKriteriaScoresNorma();
        model.addAttribute("list_kriteria_iki", iki.getKriteriaScoresIKI());
        model.addAttribute("list_kriteria_iku", iku.getKriteriaScoresIKU());
        model.addAttribute("list_kriteria_norma", list_kriteria_norma);
        model.addAttribute("unit", evaluatedUnit);
        model.addAttribute("user", evaluatedUser);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("evaluator", evaluator);
        model.addAttribute("rapor", rapor);
        System.out.println(list_kriteria_norma + "INI Normanya PLIS");
        System.out.println(norma.getIdBorangPenilaian() + "INI IDNYA PLIS");
        // var user = userService.getUserById(idUser);
        String htmlInvoice = pdfGenerateService.buildHtmlFromTemplate(evaluatedUser, evaluator, rapor, iki, iku, norma,
                totalIki, totalIku, totalNorma);
        ByteArrayOutputStream bos = pdfGenerateService.generateVoucherDocumentBaos(htmlInvoice);
        byte[] pdfReport = bos.toByteArray();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Report.pdf" + "\"")
                .body(new ByteArrayResource(pdfReport));
    }

    @GetMapping("/dashboard-manajemen/{id}")
    public String dashboardManajemenDetail(@PathVariable("id") Long id, Model model) throws NotFoundException {
        var iku = borangPenilaianService.getBorangPenilaianIKUByEvaluatedUnit(id);
        UserModel user = userDb.findById(getUserId()).get();
        model.addAttribute("list_kriteria_iku", iku.getKriteriaScoresIKU());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        model.addAttribute("idUser", getUserId());

        return "dashboard-manajemen-detail";
    }

    @GetMapping("/dashboard-manajemen")
    public String dashboardManajemen(Model model) throws NotFoundException {
        UserModel user = userDb.findById(getUserId()).get();
        List<Unit> listUnit = unitService.getAllUnits();
        Map<Unit, Integer> unitScoreMap = new HashMap<>();
        for (Unit unit : listUnit) {
            var iku = borangPenilaianService.getBorangPenilaianIKUByEvaluatedUnit(unit.getId());
            if (iku != null && !iku.getKriteriaScoresIKU().isEmpty()) {
                var listKriteriaIku = iku.getKriteriaScoresIKU();
                int poin = 0;
    
                for (KriteriaScoresIKU kriteria : listKriteriaIku) {
                    poin += kriteria.getKriteria().getBobot() * kriteria.getScore();
                }
    
                unitScoreMap.put(unit, poin);
            } else {
                // If no data is available for this unit, handle accordingly (e.g., set a default score or message)
                unitScoreMap.put(unit, 0); // Assuming a default score of 0
            }
        }
        model.addAttribute("unitScoreMap", unitScoreMap);
        model.addAttribute("noDataMessage", unitScoreMap.isEmpty() ? "No data available" : "");
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        model.addAttribute("idUser", getUserId());

        return "dashboard-manajemen";
    }

    @GetMapping("/dashboard-penilai")
    public String dashboardEvaluator(Model model) {
        List<Rapor> unsignedByEvaluator = raporService.getUnsignedByKepalaBidang();
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("unsignedByEvaluator", unsignedByEvaluator);
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        model.addAttribute("idUser", getUserId());

        return "dashboard-penilai";
    }

    @GetMapping("/dashboard-penyetuju")
    public String dashboardSDM(Model model) {
        List<Rapor> unsignedBySDM = raporService.getUnsignedBySDM();
        UserModel user = userDb.findById(getUserId()).get();

        model.addAttribute("unsignedBySDM", unsignedBySDM);
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        model.addAttribute("idUser", getUserId());

        return "dashboard-penyetuju";
    }

}
