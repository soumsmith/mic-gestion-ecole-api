package com.vieecoles.services.etats;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRException;

/**
 * Compilation Jasper du PV conseil classe : coûteuse ; une seule fois au démarrage / premier appel.
 */
@ApplicationScoped
public class SpiderPvConseilJasperCache {

    private static final String JRXML =
            "etats/spider/PvConseil/Spider_PV_Conseil_classe.jrxml";

    private volatile JasperReport compiled;

    public JasperReport getSpiderPvConseilReport() throws JRException {
        JasperReport local = compiled;
        if (local != null) {
            return local;
        }
        synchronized (this) {
            if (compiled == null) {
                try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(JRXML)) {
                    if (in == null) {
                        throw new JRException("Ressource introuvable: " + JRXML);
                    }
                    compiled = JasperCompileManager.compileReport(in);
                } catch (java.io.IOException e) {
                    throw new JRException("Erreur lecture " + JRXML + ": " + e.getMessage());
                }
            }
            return compiled;
        }
    }
}
