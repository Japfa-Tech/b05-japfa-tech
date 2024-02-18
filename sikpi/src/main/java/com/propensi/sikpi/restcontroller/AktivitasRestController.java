package com.propensi.sikpi.restcontroller;

import org.springframework.web.bind.annotation.RestController;

import com.propensi.sikpi.model.Aktivitas;
import com.propensi.sikpi.repository.AktivitasDb;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api")
public class AktivitasRestController {
    @Autowired
    private AktivitasDb aktivitasDb;

    @GetMapping(value = "/act")
    private List<Aktivitas> retrieveAll() {
        return aktivitasDb.findAll();
    }
}
