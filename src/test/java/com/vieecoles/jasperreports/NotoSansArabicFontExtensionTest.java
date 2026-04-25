package com.vieecoles.jasperreports;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontFamily;

/**
 * Noto Sans Arabic is merged into {@code net/sf/jasperreports/fonts/fonts.xml} (same extension path as the
 * {@code jasperreports-fonts} artifact) so JasperReports registers {@link FontFamily} with Identity-H embedding for PDF.
 */
class NotoSansArabicFontExtensionTest {

    private static final String NOTO_FAMILY = "Noto Sans Arabic";

    @Test
    void notoSansArabic_font_family_is_registered_as_extension() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        assertNotNull(cl.getResource("net/sf/jasperreports/fonts/fonts.xml"),
                "Font extension descriptor expected on classpath");

        JasperReportsContext ctx = DefaultJasperReportsContext.getInstance();

        boolean registered = ctx.getExtensions(FontFamily.class).stream()
                .map(FontFamily::getName)
                .anyMatch(NOTO_FAMILY::equals);

        assertTrue(
                registered,
                "Expected FontFamily \"" + NOTO_FAMILY + "\" from classpath override net/sf/jasperreports/fonts/fonts.xml");
    }
}
