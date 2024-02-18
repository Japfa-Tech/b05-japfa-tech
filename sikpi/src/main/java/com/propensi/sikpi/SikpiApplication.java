package com.propensi.sikpi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// import com.github.javafaker.Faker;
import com.propensi.sikpi.model.Aktivitas;
import com.propensi.sikpi.repository.AktivitasDb;

import jakarta.transaction.Transactional;


@SpringBootApplication
public class SikpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SikpiApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(AktivitasDb aktivitasDb){
		return args -> {
			// var faker = new Faker(new Locale("in-ID"));
			Aktivitas act = new Aktivitas();
			act.setAksi("REQ KPI");
			aktivitasDb.save(act);

		};
	}

}
