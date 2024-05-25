package com.propensi.sikpi.service;

import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;
import java.util.*;

public interface RaporService {

    void saveRapor(Rapor rapor);

    Rapor getRaporByEvaluatedUser(UserModel idUser);

    List<Rapor> getUnsignedBySDM();

    List<Rapor> getUnsignedByKepalaBidang();

}
