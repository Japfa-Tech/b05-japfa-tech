package com.propensi.sikpi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
// import com.github.javafaker.Faker;
import com.propensi.sikpi.model.Aktivitas;
import com.propensi.sikpi.model.Role;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.Manajer;
import com.propensi.sikpi.model.SDM;
import com.propensi.sikpi.repository.AktivitasDb;
import com.propensi.sikpi.repository.RoleDb;
import com.propensi.sikpi.repository.UserDb;
import com.propensi.sikpi.service.UserService;
import com.propensi.sikpi.repository.KaryawanDb;
import com.propensi.sikpi.repository.KepalaUnitDb;
import com.propensi.sikpi.repository.ManajerDb;
import com.propensi.sikpi.repository.SDMDb;

import jakarta.transaction.Transactional;

@SpringBootApplication
// @ComponentScan(basePackages = { "com.propensi.sikpi.controller", "com.propensi.sikpi.dto",
// 		"com.propensi.sikpi.service" })
public class SikpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SikpiApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(AktivitasDb aktivitasDb, UserDb userDb, RoleDb roleDb, UserService userService, KaryawanDb karyawanDb, ManajerDb manajerDb, KepalaUnitDb kuDb,
			SDMDb sdmDb) {
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
			

			CreateUserRequestDTO userDTO = new CreateUserRequestDTO();
			userDTO.setName("nama");
			userDTO.setPassword("123456");
			userDTO.setRole("peran");
			userDTO.setUsername("user12");

			UserModel user = new UserModel();
			user.setUsername("user12");
			user.setNamaLengkap("aa");
			user.setPassword("123456");
			user.setBirthDate(LocalDate.now());
			user.setDivisi("divisi");
			user.setNrp("nrp");
			user.setRole(r1);
			user.setTitle("title");
			userService.addUser(user);


			userDb.save(user);

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
			userService.addUser(kar);
			karyawanDb.save(kar);

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
			kep.setIdUnit((long) 1);
			kep.setListDokumen(new ArrayList<>());
			kep.setListKaryawan(new ArrayList<>());
			kep.setNamaLengkap("Japfa Tech is my full name");
			kep.setNrp("890");
			userService.addUser(kep);
			kuDb.save(kep);

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
			userService.addUser(sdm);
			sdmDb.save(sdm);
		};
	}

}
