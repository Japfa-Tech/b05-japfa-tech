package com.propensi.sikpi.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;

public interface PdfGenerateService {
    String buildHtmlFromTemplate(UserModel evaluatedUser, UserModel evaluator, Rapor rapor, BorangPenilaianIKI iki,
            BorangPenilaianIKU iku, BorangPenilaianNorma norma, Long totalIki, Long totalIku, Long totalNorma);

    ByteArrayOutputStream generateVoucherDocumentBaos(String html);
}