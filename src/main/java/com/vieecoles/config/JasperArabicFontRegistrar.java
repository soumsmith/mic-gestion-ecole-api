package com.vieecoles.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Registers the Arabic TTF with the AWT at startup so JasperReports can resolve
 * {@code fontName="Noto Sans Arabic"} for text measurement and avoid falling back to
 * Helvetica with {@code Identity-H} (invalid for PDF).
 */
@ApplicationScoped
public class JasperArabicFontRegistrar {

    private static final Logger LOG = Logger.getLogger(JasperArabicFontRegistrar.class.getName());
    private static final String FONT_RESOURCE = "/fonts/NotoSansArabic-Variable.ttf";

    void onStart(@Observes StartupEvent event) {
        try (InputStream in = JasperArabicFontRegistrar.class.getResourceAsStream(FONT_RESOURCE)) {
            if (in == null) {
                LOG.warning("Missing Jasper font on classpath: " + FONT_RESOURCE
                        + " — PDF may fail (Helvetica + Identity-H) for Arabic.");
                return;
            }
            Font base = Font.createFont(Font.TRUETYPE_FONT, in);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(base);
            LOG.info(() -> "Jasper/AWT font registered: "
                    + base.getFontName(Locale.ROOT) + " (family: " + base.getFamily(Locale.ROOT) + ")");
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Failed to register Arabic font for JasperReports", e);
        }
    }
}
