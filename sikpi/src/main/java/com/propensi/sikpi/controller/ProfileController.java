package com.propensi.sikpi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.LocalDateTime;
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
import com.propensi.sikpi.DTO.DokumenMapper;
import com.propensi.sikpi.DTO.RiwayatJabatanMapper;
import com.propensi.sikpi.DTO.RiwayatPenugasanMapper;
import com.propensi.sikpi.DTO.UserMapper;
import com.propensi.sikpi.DTO.request.CreateDokumenRequestDTO;
import com.propensi.sikpi.DTO.request.CreateRiwayatJabatanRequestDTO;
import com.propensi.sikpi.DTO.request.CreateRiwayatPenugasanRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.BorangPenilaianService;
import com.propensi.sikpi.service.DokumenService;
import com.propensi.sikpi.service.RiwayatJabatanService;
import com.propensi.sikpi.service.RiwayatPenugasanService;
import com.propensi.sikpi.service.UnitService;
import com.propensi.sikpi.service.UserService;
import jakarta.validation.Valid;
import javassist.NotFoundException;

@Controller
public class ProfileController {

    // Injeksi dependency
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DokumenMapper dokumenMapper;
    @Autowired
    private DokumenService dokumenService;
    @Autowired
    private RiwayatJabatanMapper riwayatJabatanMapper;
    @Autowired
    private RiwayatPenugasanMapper riwayatPenugasanMapper;
    @Autowired
    private RiwayatJabatanService riwayatJabatanService;
    @Autowired
    private RiwayatPenugasanService riwayatPenugasanService;
    @Autowired
    private BorangPenilaianService borangPenilaianService;
    @Autowired
    private UserDb userDb;
    @Autowired
    private UnitService unitService;

    // Mengambil id user yang sedang login
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
        // Return nilai default
        return null;
    }

    // Menampilkan halaman profile user
    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable("id") Long id, Model model) throws NotFoundException {
        var user = userService.getUserById(id);
        var evaluatedUnit = new Unit();
        if (user.getRole().getId() == 1) {
            Karyawan ku = (Karyawan) user;
            evaluatedUnit = unitService.getUnitByUserId(ku.getId());
        } else if (user.getRole().getId() == 5) {
            KepalaUnit man = (KepalaUnit) user;
            evaluatedUnit = unitService.getUnitByKepalaUnitId(man.getId());
        }
        var iki = borangPenilaianService.getBorangPenilaianIKIByEvaluatedUser(id);
        var iku = borangPenilaianService.getBorangPenilaianIKUByEvaluatedUnit(evaluatedUnit.getId());
        var norma = borangPenilaianService.getBorangPenilaianNormaByEvaluatedUser(id);
        List<Dokumen> listDok = dokumenService.getAllDokumenByUserId(user);
        List<RiwayatJabatan> listJabatan = riwayatJabatanService.getAllJabatanByUserId(user);
        List<RiwayatPenugasan> listPenugasan = riwayatPenugasanService.getAllPenugasanByUserId(user);
        if (iki != null && iku != null && norma != null && iki.getStatus().equals("finished")
                && iku.getStatus().equals("finished") && norma.getStatus().equals("finished")) {
            System.out.println("rapornya ready");
            model.addAttribute("raporReady", true);
        } else {
            System.out.println("rapornya tiduck ready");
            model.addAttribute("raporReady", false);
        }
        model.addAttribute("listDokumen", listDok);
        model.addAttribute("user", user);
        model.addAttribute("RiwayatJabatan", listJabatan);
        model.addAttribute("RiwayatPenugasan", listPenugasan);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        return "profile-page";
    }

    // Menambahkan dokumen lampiran
    @GetMapping("/profile/{id}/tambahDokumen")
    public String formEditDokumen(@PathVariable("id") Long id, Model model) {
        var user = userService.getUserById(id);
        List<Dokumen> listDok = dokumenService.getAllDokumenByUserId(user);
        var dokumenDTO = new CreateDokumenRequestDTO();
        dokumenDTO.setIdUser(user);
        model.addAttribute("dokumenDTO", dokumenDTO);
        model.addAttribute("user", user);
        model.addAttribute("listDokumen", listDok);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        return "form-tambah-dokumen";
    }

    // Meng-approve dokumen oleh SDM
    @GetMapping("/profile/{idUser}/approve/{idDokumen}")
    public String approveDokumen(@PathVariable("idUser") Long idUser, @PathVariable("idDokumen") Long idDokumen,
            Model model) {
        Dokumen dok = dokumenService.getDokumenById(idDokumen);
        dok.setStatus(1);
        dok.setReviewedDate(LocalDateTime.now());
        dokumenService.saveDokumen(dok);
        return "redirect:/profile/" + idUser;
    }

    // Menolak dokumen oleh SDM
    @GetMapping("/profile/{idUser}/reject/{idDokumen}")
    public String rejectDokumen(@PathVariable("idUser") Long idUser, @PathVariable("idDokumen") Long idDokumen,
            Model model) {
        Dokumen dok = dokumenService.getDokumenById(idDokumen);
        dok.setStatus(2);
        dok.setReviewedDate(LocalDateTime.now());
        dokumenService.saveDokumen(dok);
        return "redirect:/profile/" + idUser;
    }

    // Menambahkan riwayat jabatan
    @GetMapping("/profile/{id}/tambahJabatan")
    public String formEditJabatan(@PathVariable("id") Long id, Model model) {
        var user = userService.getUserById(id);
        List<RiwayatJabatan> listJabatan = riwayatJabatanService.getAllJabatanByUserId(user);
        var jabatanDTO = new CreateRiwayatJabatanRequestDTO();
        jabatanDTO.setIdUser(user);
        model.addAttribute("riwayatJabatanDTO", jabatanDTO);
        model.addAttribute("user", user);
        model.addAttribute("riwayatJabatan", listJabatan);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        return "form-tambah-jabatan";
    }

    // Menambahkan riwayat penugasan
    @GetMapping("/profile/{id}/tambahPenugasan")
    public String formEditPenugasan(@PathVariable("id") Long id, Model model) {
        var user = userService.getUserById(id);
        List<RiwayatPenugasan> listPenugasan = riwayatPenugasanService.getAllPenugasanByUserId(user);
        var penugasanDTO = new CreateRiwayatPenugasanRequestDTO();
        penugasanDTO.setIdUser(user);
        model.addAttribute("riwayatPenugasanDTO", penugasanDTO);
        model.addAttribute("user", user);
        model.addAttribute("riwayatPenugasan", listPenugasan);
        model.addAttribute("idUser", getUserId());
        return "form-tambah-penugasan";
    }

    // Menyimpan dokumen yang diunggah
    @PostMapping("/profile/tambahDokumen")
    public String tambahDokumen(@Valid @ModelAttribute CreateDokumenRequestDTO createDokumenRequestDTO,
            @RequestParam("dokumenApaKek") MultipartFile dokumen,
            Model model) throws IOException {
        byte[] dokumenBytes = dokumen.getBytes();
        Dokumen dokumenModel = dokumenMapper.createDokumenRequestDTOToDokumen(createDokumenRequestDTO);
        dokumenModel.setDokumen(dokumenBytes);
        dokumenModel.setNamaDokumen(dokumen.getOriginalFilename());
        dokumenModel.setUploadedDate(LocalDateTime.now());
        dokumenService.saveDokumen(dokumenModel);
        var user = userService.getUserById(dokumenModel.getIdUser().getId());
        model.addAttribute("user", user);
        return "success-add-dokumen";
    }

    // Menyimpan riwayat jabatan yang diunggah
    @PostMapping("/profile/tambahJabatan")
    public String tambahJabatan(@Valid @ModelAttribute CreateRiwayatJabatanRequestDTO createRiwayatJabatanRequestDTO,
            @RequestParam("JabatanDiganti") MultipartFile dokumenJabatan,
            Model model) throws IOException {
        byte[] dokumenJabatanBytes = dokumenJabatan.getBytes();
        RiwayatJabatan dokumenJabatanModel = riwayatJabatanMapper
                .createRiwayatJabatanRequestDTOToRiwayatJabatan(createRiwayatJabatanRequestDTO);
        dokumenJabatanModel.setDokumen(dokumenJabatanBytes);
        dokumenJabatanModel.setJabatan(dokumenJabatan.getOriginalFilename());
        dokumenJabatanModel.setUploadedDate(LocalDateTime.now());
        riwayatJabatanService.saveRiwayatJabatan(dokumenJabatanModel);
        var user = userService.getUserById(dokumenJabatanModel.getIdUser().getId());
        model.addAttribute("user", user);
        return "success-add-jabatan";
    }

    // Menyimpan riwayat penugasan yang diuanggah
    @PostMapping("/profile/tambahPenugasan")
    public String tambahPenugasan(
            @Valid @ModelAttribute CreateRiwayatPenugasanRequestDTO createRiwayatPenugasanRequestDTO,
            @RequestParam("PenugasanDiganti") MultipartFile dokumenPenugasan,
            Model model) throws IOException {
        byte[] dokumenPenugasanBytes = dokumenPenugasan.getBytes();
        RiwayatPenugasan dokumenPenugasanModel = riwayatPenugasanMapper
                .createRiwayatPenugasanRequestDTOToRiwayatPenugasan(createRiwayatPenugasanRequestDTO);
        dokumenPenugasanModel.setDokumen(dokumenPenugasanBytes);
        dokumenPenugasanModel.setPenugasan(dokumenPenugasan.getOriginalFilename());
        dokumenPenugasanModel.setUploadedDate(LocalDateTime.now());
        riwayatPenugasanService.saveRiwayatPenugasan(dokumenPenugasanModel);
        var user = userService.getUserById(dokumenPenugasanModel.getIdUser().getId());
        model.addAttribute("user", user);
        return "success-add-penugasan";
    }

    // Menghapus dokumen terunggah
    @GetMapping("/profile/{idUser}/deleteDokumen/{idDokumen}")
    public String deleteDokumen(@PathVariable("idUser") Long idUser, @PathVariable("idDokumen") Long idDokumen,
            Model model) {
        var user = userService.getUserById(idUser);
        Dokumen dok = dokumenService.getDokumenById(idDokumen);
        dokumenService.deleteDokumen(dok);
        model.addAttribute("user", user);
        return "success-delete-dokumen";
    }

    // Menghapus riwayat jabatan terunggah
    @GetMapping("/profile/{idUser}/deleteJabatan/{idRiwayatJabatan}")
    public String deleteRiwayatJabatan(@PathVariable("idUser") Long idUser,
            @PathVariable("idRiwayatJabatan") Long idRiwayatJabatan,
            Model model) {
        var user = userService.getUserById(idUser);
        RiwayatJabatan jabatan = riwayatJabatanService.getJabatanById(idRiwayatJabatan);
        riwayatJabatanService.deleteRiwayatJabatan(jabatan);
        model.addAttribute("user", user);
        return "success-delete-jabatan";
    }

    // Menghapus riwayat penugasan terunggah
    @GetMapping("/profile/{idUser}/deletePenugasan/{idRiwayatPenugasan}")
    public String deleteRiwayatPenugasan(@PathVariable("idUser") Long idUser,
            @PathVariable("idRiwayatPenugasan") Long idRiwayatPenugasan,
            Model model) {
        var user = userService.getUserById(idUser);
        RiwayatPenugasan penugasan = riwayatPenugasanService.getPenugasanById(idRiwayatPenugasan);
        riwayatPenugasanService.deleteRiwayatPenugasan(penugasan);
        model.addAttribute("user", user);
        return "success-delete-penugasan";
    }

    // Menyimpan row dokumen lampiran
    @PostMapping(value = "/profile/edit")
    public String saveRowDokumenUserUpdate(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            FieldError fieldError = (FieldError) error;
                            return fieldError.getField() + ": " + error.getDefaultMessage();
                        }
                        return error.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            model.addAttribute("errors", errors);
            return "error-viewall";
        }
        if (userDTO.getListDokumen() == null || userDTO.getListDokumen().size() == 0) {
            userDTO.setListDokumen(new ArrayList<>());
        }
        var userFromDTO = userMapper.userDTOToUser(userDTO);
        var user = userService.updateUser(userFromDTO);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("user", user);
        return "success-edit";
    }

    // Mengunduh dokumen lampiran
    @GetMapping("/profile/{idUser}/download/{idDokumen}")
    public ResponseEntity<ByteArrayResource> downloadAttachment(@PathVariable("idUser") Long idUser,
            @PathVariable("idDokumen") Long idDokumen,
            Model model) {
        var dok = dokumenService.getDokumenById(idDokumen);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dok.getNamaDokumen() + "\"")
                .body(new ByteArrayResource(dok.getDokumen()));
    }

    // Menampilkan daftar karyawan
    @GetMapping("/list-employee")
    public String getAllEmployee(Model model) {
        List<UserModel> listUser = userService.getAllUser();
        UserModel user = userDb.findById(getUserId()).get();
        model.addAttribute("listUser", listUser);
        model.addAttribute("idUser", getUserId());
        model.addAttribute("loggedInUserRole", user.getRole().getRole());
        return "list-user";
    }

    @GetMapping("/master-view")
    public String masterView(Model model) {
        List<UserModel> listUser = userService.getAllUser();
        List<BorangPenilaian> listBorang = borangPenilaianService.getAllBorang();
        model.addAttribute("listUser", listUser);
        model.addAttribute("listBorang", listBorang);
        return "master-view";
    }
}
