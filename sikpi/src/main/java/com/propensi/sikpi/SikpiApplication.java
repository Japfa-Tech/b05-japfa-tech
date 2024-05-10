package com.propensi.sikpi;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
// import com.github.javafaker.Faker;
import com.propensi.sikpi.model.Aktivitas;
import com.propensi.sikpi.model.IndikatorNorma;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.KriteriaPenilaianNorma;
import com.propensi.sikpi.model.Manajer;
import com.propensi.sikpi.model.Norma;
import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.Role;
import com.propensi.sikpi.model.SDM;
import com.propensi.sikpi.model.Unit;
import com.propensi.sikpi.repository.AktivitasDb;
import com.propensi.sikpi.repository.IndikatorNormaDb;
import com.propensi.sikpi.repository.KaryawanDb;
import com.propensi.sikpi.repository.KepalaUnitDb;
import com.propensi.sikpi.repository.KriteriaPenilaianDb;
import com.propensi.sikpi.repository.KriteriaPenilaianNormaDb;
import com.propensi.sikpi.repository.ManajerDb;
import com.propensi.sikpi.repository.NormaDb;
import com.propensi.sikpi.repository.RaporDb;
import com.propensi.sikpi.repository.RoleDb;
import com.propensi.sikpi.repository.SDMDb;
import com.propensi.sikpi.repository.UnitDb;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootApplication
// @ComponentScan(basePackages = { "com.propensi.sikpi.controller",
// "com.propensi.sikpi.dto",
// "com.propensi.sikpi.service" })
public class SikpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SikpiApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(AktivitasDb aktivitasDb, UserDb userDb, RoleDb roleDb, UnitDb unitDb, UserService userService,
			KaryawanDb karyawanDb, ManajerDb manajerDb, KepalaUnitDb kuDb,
			SDMDb sdmDb, RaporDb raporDb, IndikatorNormaDb indikatorNormaDb,
			KriteriaPenilaianNormaDb kriteriaPenilaianNormaDb, NormaDb normaDb) {
		return args -> {
			// var faker = new Faker(new Locale("in-ID"));
			Aktivitas act = new Aktivitas();
			act.setAksi("REQ KPI");
			aktivitasDb.save(act);

			Role role = new Role();
			role.setRole("Karyawan");
			roleDb.save(role);

			Role r1 = new Role();
			r1.setRole("Admin");
			roleDb.save(r1);

			Role r2 = new Role();
			r2.setRole("SDM");
			roleDb.save(r2);

			Role r3 = new Role();
			r3.setRole("Manajer");
			roleDb.save(r3);

			Role r4 = new Role();
			r4.setRole("KepalaUnit");
			roleDb.save(r4);

			Unit unit1 = new Unit();
			unit1.setNamaUnit("TIDUR");
			unitDb.save(unit1);

			Unit unit2 = new Unit();
			unit2.setNamaUnit("SDM");
			unitDb.save(unit2);

			Unit unit3 = new Unit();
			unit3.setNamaUnit("IT");
			unitDb.save(unit3);

			UserModel user = new UserModel();
			user.setUsername("admin");
			user.setNamaLengkap("admin lengkap");
			user.setPassword("123456");
			user.setBirthDate(LocalDate.now());
			user.setDivisi("divisi");
			user.setNrp("nrp");
			user.setRole(r1);
			user.setTitle("title");
			userService.addUser(user);
			userDb.save(user);

			Manajer man = new Manajer();
			man.setUsername("manajer");
			man.setPassword("123456");
			man.setBirthDate(LocalDate.now());
			man.setDivisi("divisi");
			man.setRole(r3);
			man.setTitle("title");
			man.setNrp("456");
			man.setKepalaUnit((long) 1);
			man.setListDokumen(new ArrayList<>());
			man.setNamaLengkap("Japfa Tech is my full name");
			man.setUnit(unit1);
			userService.addUser(man);
			manajerDb.save(man);

			KepalaUnit kep = new KepalaUnit();
			kep.setUsername("kepalaunit");
			kep.setPassword("123456");
			kep.setBirthDate(LocalDate.now());
			kep.setDivisi("divisi");
			kep.setRole(r4);
			kep.setTitle("title");
			kep.setIdManajer((long) 2);
			kep.setUnit(unit1);
			kep.setListDokumen(new ArrayList<>());
			kep.setListKaryawan(new ArrayList<>());
			kep.setNamaLengkap("Japfa Tech is my full name");
			kep.setNrp("890");
			kep.setIdManajer(man.getId());
			userService.addUser(kep);
			kuDb.save(kep);

			// unit1.setIdKepalaUnit(kep.getId());
			unit1.setKepalaUnit(kep);
			unitDb.save(unit1);
			kep.setUnit(unit1);
			kuDb.save(kep);

			Karyawan kar = new Karyawan();
			kar.setUsername("karyawan");
			kar.setPassword("123456");
			kar.setBirthDate(LocalDate.now());
			kar.setDivisi("divisi");
			kar.setRole(role);
			kar.setTitle("title");
			kar.setIdKepalaUnit((long) 1);
			kar.setNrp("123");
			kar.setListDokumen(new ArrayList<>());
			kar.setNamaLengkap("Japfa Tech is my full name");
			kar.setIdKepalaUnit(kep.getId());
			kar.setUnit(unit1);
			userService.addUser(kar);
			karyawanDb.save(kar);

			SDM sdm = new SDM();
			sdm.setUsername("sdm");
			sdm.setPassword("123456");
			sdm.setBirthDate(LocalDate.now());
			sdm.setDivisi("divisi");
			sdm.setRole(r2);
			sdm.setTitle("title");
			sdm.setIdKepalaUnit((long) 3);
			sdm.setListDokumen(new ArrayList<>());
			sdm.setNamaLengkap("Japfa Tech is my full name");
			sdm.setNrp("768");
			sdm.setUnit(unit1);
			userService.addUser(sdm);
			sdmDb.save(sdm);

			Rapor raporSDM = new Rapor();
			raporSDM.setEvaluatedUser(sdm);
			raporDb.save(raporSDM);

			Rapor raporKaryawan = new Rapor();
			raporKaryawan.setEvaluatedUser(kar);
			raporDb.save(raporKaryawan);

			Rapor raporKu = new Rapor();
			raporKu.setEvaluatedUser(kep);
			raporDb.save(raporKu);

			Rapor raporManajer = new Rapor();
			raporManajer.setEvaluatedUser(man);
			raporDb.save(raporManajer);

			IndikatorNorma amanah = new IndikatorNorma();
			amanah.setJudulIndikator("Amanah");
			indikatorNormaDb.save(amanah);

			IndikatorNorma profesional = new IndikatorNorma();
			profesional.setJudulIndikator("Profesional");
			indikatorNormaDb.save(profesional);

			IndikatorNorma ikhlas = new IndikatorNorma();
			ikhlas.setJudulIndikator("Ikhlas");
			indikatorNormaDb.save(ikhlas);

			IndikatorNorma empati = new IndikatorNorma();
			empati.setJudulIndikator("Empati");
			indikatorNormaDb.save(empati);

			IndikatorNorma komunikasiEfektif = new IndikatorNorma();
			komunikasiEfektif.setJudulIndikator("Komunikasi Efektif");
			indikatorNormaDb.save(komunikasiEfektif);

			Norma norma = new Norma();
			norma.setNamaTemplate("norma");
			
			normaDb.save(norma);

			KriteriaPenilaianNorma kpAmanah1 = new KriteriaPenilaianNorma();
			kpAmanah1.setSkor(0);
			kpAmanah1.setBobot(1);
			kpAmanah1.setIndikatorNorma(amanah);
			kpAmanah1.setJudulKriteria("Menjaga sikap, kerapihan penampilan dan aurat baik di dalam lingkungan RS maupun di luar/medsos");
			kpAmanah1.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpAmanah1);


			KriteriaPenilaianNorma kpAmanah2 = new KriteriaPenilaianNorma();
			kpAmanah2.setBobot(2);
			kpAmanah2.setIndikatorNorma(amanah);
			kpAmanah2.setJudulKriteria("Tidak melakukan pelanggaran yang dapat merugikan pasien, karyawan secara materi maupun immateri");
			kpAmanah2.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpAmanah2);

			KriteriaPenilaianNorma kpProfesional1 = new KriteriaPenilaianNorma();
			kpProfesional1.setBobot(2);
			kpProfesional1.setIndikatorNorma(profesional);
			kpProfesional1.setJudulKriteria("Bekerja sesuai jam kerja yang ditentukan (disiplin kehadiran)");
			kpProfesional1.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpProfesional1);

			KriteriaPenilaianNorma kpProfesional2 = new KriteriaPenilaianNorma();
			kpProfesional2.setBobot(2);
			kpProfesional2.setIndikatorNorma(profesional);
			kpProfesional2.setJudulKriteria("Bekerja sesuai mekanisme (alur kerja) dan SPO");
			kpProfesional2.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpProfesional2);

			KriteriaPenilaianNorma kpIkhlas1 = new KriteriaPenilaianNorma();
			kpIkhlas1.setBobot(1);
			kpIkhlas1.setIndikatorNorma(ikhlas);
			kpIkhlas1.setJudulKriteria("Bersikap ramah, mengucapkan salam dan senyum");
			kpIkhlas1.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpIkhlas1);

			KriteriaPenilaianNorma kpEmpati1 = new KriteriaPenilaianNorma();
			kpEmpati1.setBobot(1);
			kpEmpati1.setIndikatorNorma(empati);
			kpEmpati1.setJudulKriteria("Cepat tanggap / responsif terhadap permasalahan pasien / rekan kerja");
			kpEmpati1.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpEmpati1);

			KriteriaPenilaianNorma kpKomunikasiEfektif1 = new KriteriaPenilaianNorma();
			kpKomunikasiEfektif1.setBobot(1);
			kpKomunikasiEfektif1.setIndikatorNorma(komunikasiEfektif);
			kpKomunikasiEfektif1.setJudulKriteria("Bertutur kata baik, santun dan jelas baik verbal maupun non verbal");
			kpKomunikasiEfektif1.setTemplatePenilaian(norma);
			kriteriaPenilaianNormaDb.save(kpKomunikasiEfektif1);







		};
	}

}
