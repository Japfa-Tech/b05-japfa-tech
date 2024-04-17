package com.propensi.sikpi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.Karyawan;
import com.propensi.sikpi.model.KepalaUnit;
import com.propensi.sikpi.model.KriteriaPenilaian;
import com.propensi.sikpi.model.Manajer;
import com.propensi.sikpi.model.SDM;
import com.propensi.sikpi.model.UserModel;
import com.propensi.sikpi.repository.BorangPenilaianDb;
import com.propensi.sikpi.repository.BorangPenilaianIKIDb;
import com.propensi.sikpi.repository.BorangPenilaianIKUDb;
import com.propensi.sikpi.repository.DokumenDb;
import com.propensi.sikpi.repository.KriteriaPenilaianDb;
import com.propensi.sikpi.repository.UserDb;

@Service
public class PesanServiceImpl implements PesanService {

    @Autowired
    private BorangPenilaianDb borangPenilaianDb;

    @Autowired
    private BorangPenilaianIKIDb borangPenilaianIKIDb;

    @Autowired
    private BorangPenilaianIKUDb borangPenilaianIKUDb;

    @Autowired
    private KriteriaPenilaianDb kriteriaPenilaianDb;

    @Autowired
    private UserDb userDb;

    private List<String> pesanList = new ArrayList<>();


    @Override
    public void addPesan(String pesan) {
        pesanList.add(pesan);
        
    }

    @Override
    public List<String> viewAllPesan() {
        return pesanList;
    }


}

