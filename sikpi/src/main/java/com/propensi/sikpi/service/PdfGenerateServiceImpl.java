package com.propensi.sikpi.service;

import com.lowagie.text.DocumentException;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.BorangPenilaianIKU;
import com.propensi.sikpi.model.BorangPenilaianNorma;
import com.propensi.sikpi.model.Rapor;
import com.propensi.sikpi.model.UserModel;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.IOException;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {

    /**
     * Membangun string HTML dari template Thymeleaf yang diisi dengan data.
     *
     * @param evaluatedUser Pengguna yang dinilai.
     * @param evaluator Evaluator.
     * @param rapor Model rapor yang berisi data evaluasi.
     * @param iki Data evaluasi IKI.
     * @param iku Data evaluasi IKU.
     * @param norma Data evaluasi Norma.
     * @param totalIki Total skor IKI.
     * @param totalIku Total skor IKU.
     * @param totalNorma Total skor Norma.
     * @return String yang berisi HTML yang dihasilkan.
     */
    @Override
    public String buildHtmlFromTemplate(UserModel evaluatedUser, UserModel evaluator, Rapor rapor,
                                        BorangPenilaianIKI iki,
                                        BorangPenilaianIKU iku, BorangPenilaianNorma norma, Long totalIki, Long totalIku, Long totalNorma) {
        // Mengatur resolver template Thymeleaf
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);

        // Inisialisasi engine template dengan resolver
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Membuat konteks Thymeleaf dan mengisinya dengan variabel
        Context context = new Context();
        context.setVariable("user", evaluatedUser);
        context.setVariable("evaluator", evaluator);
        context.setVariable("rapor", rapor);
        context.setVariable("list_kriteria_iki", iki.getKriteriaScoresIKI());
        context.setVariable("list_kriteria_iku", iku.getKriteriaScoresIKU());
        context.setVariable("list_kriteria_norma", norma.getKriteriaScoresNorma());
        context.setVariable("totalIki", totalIki);
        context.setVariable("totalIku", totalIku);
        context.setVariable("totalNorma", totalNorma);

        // Memproses template dengan konteks untuk menghasilkan HTML
        return templateEngine.process("templates/rapor", context);
    }

    /**
     * Menghasilkan dokumen PDF dari string HTML dan mengembalikannya sebagai ByteArrayOutputStream.
     *
     * @param html String HTML yang akan dikonversi menjadi PDF.
     * @return ByteArrayOutputStream yang berisi dokumen PDF.
     */
    @Override
    public ByteArrayOutputStream generateVoucherDocumentBaos(String html) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Inisialisasi renderer PDF dan mengatur dokumen HTML
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        try {
            // Menghasilkan PDF dan menulisnya ke ByteArrayOutputStream
            renderer.createPDF(baos);
            baos.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos;
    }
}
