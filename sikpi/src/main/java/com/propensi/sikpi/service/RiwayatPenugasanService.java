package com.propensi.sikpi.service;

import java.util.List;

import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.UserModel;

public interface RiwayatPenugasanService {
    //Method untuk menambahkan Riwayat
    void saveRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan);

    List<RiwayatPenugasan> getAllRiwayatPenugasan();

    //Method untuk mendapatkan riwayat yang dimiliki employee
    List<RiwayatPenugasan> getAllPenugasanByUserId(UserModel idUser);

    RiwayatPenugasan getPenugasanById(Long idRiwayatPenugasan);

    //Method untuk mengupdate riwayat 
    RiwayatPenugasan updateRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan);

    //Method untuk mendelete riwayat
    void deleteRiwayatPenugasan(RiwayatPenugasan riwayatPenugasan);
}