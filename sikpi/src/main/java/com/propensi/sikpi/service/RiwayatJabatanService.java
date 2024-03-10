package com.propensi.sikpi.service;

import java.util.List;

import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.UserModel;

public interface RiwayatJabatanService {
    //Method untuk menambahkan Riwayat
    void saveRiwayatJabatan(RiwayatJabatan riwayatJabatan);

    List<RiwayatJabatan> getAllRiwayatJabatan();

    //Method untuk mendapatkan riwayat yang dimiliki employee
    List<RiwayatJabatan> getAllJabatanByUserId(UserModel idUser);

    RiwayatJabatan getJabatanById(Long idRiwayatJabatan);

    //Method untuk mengupdate riwayat 
    RiwayatJabatan updateRiwayatJabatan(RiwayatJabatan riwayatJabatan);

    //Method untuk mendelete riwayat
    void deleteRiwayatJabatan(RiwayatJabatan riwayatJabatan);
}