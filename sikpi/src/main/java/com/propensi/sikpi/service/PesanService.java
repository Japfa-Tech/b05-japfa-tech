package com.propensi.sikpi.service;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.UserModel;

import java.util.*;

public interface PesanService {
    void addPesan(String pesan);
    List<String> viewAllPesan();
}
