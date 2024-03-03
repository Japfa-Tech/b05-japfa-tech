package com.propensi.sikpi.model;

import java.util.List;

import jakarta.persistence.Column;

public class Manajer extends UserModel{
    @Column(name="list_karyawan", nullable=false)
    private List<Karyawan> listKaryawan;

    @Column(name="kepalaUnit", nullable=false)
    private KepalaUnit kepalaUnit; 
}
