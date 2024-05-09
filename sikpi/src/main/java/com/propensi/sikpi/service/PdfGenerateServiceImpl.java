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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.List;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {

    @Override
    public String buildHtmlFromTemplate(UserModel evaluatedUser, UserModel evaluator, Rapor rapor,
            BorangPenilaianIKI iki,
            BorangPenilaianIKU iku, BorangPenilaianNorma norma, Long totalIki, Long totalIku, Long totalNorma) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

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

        // Get the plain HTML with the resolved ${name} variable!
        return templateEngine.process("templates/rapor", context);
    }

    @Override
    public ByteArrayOutputStream generateVoucherDocumentBaos(String html) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        try {
            renderer.createPDF(baos);
            baos.close();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return baos;

    }
}
