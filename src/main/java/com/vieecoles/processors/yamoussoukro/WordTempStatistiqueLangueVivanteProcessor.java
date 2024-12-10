package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.BoursiersDto;
import com.vieecoles.dto.EffectifElevLangueVivante2Dto;
import com.vieecoles.dto.LangVivanteDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.processors.dren3.services.StatistiqueBoursierServices;
import com.vieecoles.processors.yamoussoukro.services.EffectifParLangueServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;

@ApplicationScoped
public class WordTempStatistiqueLangueVivanteProcessor {
    @Inject
    EffectifParLangueServices resultatsServices ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        LangVivanteDto elevesPremierCycle = new LangVivanteDto();

        try {

          elevesPremierCycle= resultatsServices.getLangueVivante(idEcole,libelleAnnee,libelleTrimetre,idAnnee);
        } catch (Exception e) {
          e.printStackTrace();
        }



        String tableTitle = "CODE_LANGUE_VIVANTE";

        XWPFTable targetTable = null;
        for (XWPFParagraph paragraph : document.getParagraphs()) {
          if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
            // Supprimer le texte du titre
            paragraph.removeRun(0);

            // Utiliser un curseur pour trouver l'élément suivant
            XmlCursor cursor = paragraph.getCTP().newCursor();
            while (cursor.toNextSibling()) {
              if (cursor.getObject() instanceof CTTbl) {
                targetTable = new XWPFTable((CTTbl) cursor.getObject(), document);
                break;
              }
            }
            cursor.dispose(); // Libérer les ressources du curseur
            break;
          }
        }
if(targetTable!=null) {
  if(elevesPremierCycle!=null){
    Ecole MyEcole;
    String lastValue = null;
    MyEcole= Ecole.findById(idEcole);

    Long totalCycle1= elevesPremierCycle.getEffeESP4G()+elevesPremierCycle.getEffeALL4G()+
        elevesPremierCycle.getEffeESP3G()+elevesPremierCycle.getEffeALL3G();
    Long total2nd= elevesPremierCycle.getEffeESP2AG()+elevesPremierCycle.getEffeESP2CG()+
        elevesPremierCycle.getEffeALL2AG()+elevesPremierCycle.getEffeALL2CG();
    Long total1ere= elevesPremierCycle.getEffeESP1AG()+elevesPremierCycle.getEffeESP1CG()+
        elevesPremierCycle.getEffeESP1DG()+
        elevesPremierCycle.getEffeALL1AG()+elevesPremierCycle.getEffeALL1CG()+elevesPremierCycle.getEffeALL1DG();
    Long totalTle= elevesPremierCycle.getEffeESPTAG()+elevesPremierCycle.getEffeESPTCG()+
        elevesPremierCycle.getEffeESPTDG()+
        elevesPremierCycle.getEffeALLTAG()+elevesPremierCycle.getEffeALLTCG()+elevesPremierCycle.getEffeALLTDG();
    Long totalCycle2=total2nd+total1ere+totalTle;
    Long totalGlobale=totalCycle2+totalCycle1;

    XWPFTableRow garconsRow = targetTable.createRow();
    ensureCellCount(garconsRow, 31);
    garconsRow.getCell(0).setText("GARCONS");
    garconsRow.getCell(1).setText("");
    garconsRow.getCell(2).setText("");
    garconsRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4G()));
    garconsRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4G()));
    garconsRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4G()+elevesPremierCycle.getEffeALL4G()));
    garconsRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3G()));
    garconsRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3G()));
    garconsRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3G()+elevesPremierCycle.getEffeALL3G()));
    garconsRow.getCell(9).setText(String.valueOf(totalCycle1));
    garconsRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2AG()));
    garconsRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CG()));
    garconsRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2AG()));
    garconsRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CG()));
    garconsRow.getCell(14).setText(String.valueOf(total2nd));
    garconsRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1AG()));
    garconsRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CG()));
    garconsRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DG()));
    garconsRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1AG()));
    garconsRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CG()));
    garconsRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DG()));
    garconsRow.getCell(21).setText(String.valueOf(total1ere));
    garconsRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTAG()));
    garconsRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCG()));
    garconsRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDG()));
    garconsRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTAG()));
    garconsRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCG()));
    garconsRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDG()));
    garconsRow.getCell(28).setText(String.valueOf(totalTle));
    garconsRow.getCell(29).setText(String.valueOf(totalCycle2));
    garconsRow.getCell(30).setText(String.valueOf(totalGlobale));

    Long totalCycle1F= elevesPremierCycle.getEffeESP4F()+elevesPremierCycle.getEffeALL4F()+
        elevesPremierCycle.getEffeESP3F()+elevesPremierCycle.getEffeALL3F();
    Long total2ndF= elevesPremierCycle.getEffeESP2AF()+elevesPremierCycle.getEffeESP2CF()+
        elevesPremierCycle.getEffeALL2AF()+elevesPremierCycle.getEffeALL2CF();
    Long total1ereF= elevesPremierCycle.getEffeESP1AF()+elevesPremierCycle.getEffeESP1CF()+
        elevesPremierCycle.getEffeESP1DF()+
        elevesPremierCycle.getEffeALL1AF()+elevesPremierCycle.getEffeALL1CF()+elevesPremierCycle.getEffeALL1DF();
    Long totalTleF= elevesPremierCycle.getEffeESPTAF()+elevesPremierCycle.getEffeESPTCF()+
        elevesPremierCycle.getEffeESPTDF()+
        elevesPremierCycle.getEffeALLTAF()+elevesPremierCycle.getEffeALLTCF()+elevesPremierCycle.getEffeALLTDF();
    Long totalCycle2F=total2ndF+total1ereF+totalTleF;
    Long totalGlobaleF=totalCycle2F+totalCycle1F;

    XWPFTableRow FilleRow = targetTable.createRow();
    ensureCellCount(FilleRow, 31);
    FilleRow.getCell(0).setText("FILLES");
    FilleRow.getCell(1).setText("");
    FilleRow.getCell(2).setText("");
    FilleRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4F()));
    FilleRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4F()));
    FilleRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4F()+elevesPremierCycle.getEffeALL4F()));
    FilleRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3F()));
    FilleRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3F()));
    FilleRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3F()+elevesPremierCycle.getEffeALL3F()));
    FilleRow.getCell(9).setText(String.valueOf(totalCycle1F));
    FilleRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2AF()));
    FilleRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CF()));
    FilleRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2AF()));
    FilleRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CF()));
    FilleRow.getCell(14).setText(String.valueOf(total2ndF));
    FilleRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1AF()));
    FilleRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CF()));
    FilleRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DF()));
    FilleRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1AF()));
    FilleRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CF()));
    FilleRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DF()));
    FilleRow.getCell(21).setText(String.valueOf(total1ereF));
    FilleRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTAF()));
    FilleRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCF()));
    FilleRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDF()));
    FilleRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTAF()));
    FilleRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCF()));
    FilleRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDF()));
    FilleRow.getCell(28).setText(String.valueOf(totalTleF));
    FilleRow.getCell(29).setText(String.valueOf(totalCycle2F));
    FilleRow.getCell(30).setText(String.valueOf(totalGlobaleF));

    XWPFTableRow totalow = targetTable.createRow();
    ensureCellCount(totalow, 31);
    totalow.getCell(0).setText("TOTAL");
    totalow.getCell(1).setText("");
    totalow.getCell(2).setText("");
    totalow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4F()+elevesPremierCycle.getEffeESP4G()));
    totalow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4F()+elevesPremierCycle.getEffeALL4G()));
    totalow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4F()+elevesPremierCycle.getEffeALL4F()+
        elevesPremierCycle.getEffeESP4G()+elevesPremierCycle.getEffeALL4G()));
    totalow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3F()+elevesPremierCycle.getEffeESP3G()));
    totalow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3F()+elevesPremierCycle.getEffeALL3G()));
    totalow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3F()+elevesPremierCycle.getEffeALL3F()+
        elevesPremierCycle.getEffeESP3G()+elevesPremierCycle.getEffeALL3G()));
    totalow.getCell(9).setText(String.valueOf(totalCycle1F+totalCycle1));
    totalow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2AF()+elevesPremierCycle.getEffeESP2AG()));
    totalow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CF()+elevesPremierCycle.getEffeESP2CG()));
    totalow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2AF()+elevesPremierCycle.getEffeALL2AG()));
    totalow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CF()+elevesPremierCycle.getEffeALL2CG()));
    totalow.getCell(14).setText(String.valueOf(total2ndF+total2nd));
    totalow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1AF()+elevesPremierCycle.getEffeESP1AG()));
    totalow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CF()+elevesPremierCycle.getEffeESP1CG()));
    totalow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DF()+elevesPremierCycle.getEffeESP1DG()));
    totalow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1AF()+elevesPremierCycle.getEffeALL1AG()));
    totalow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CF()+elevesPremierCycle.getEffeALL1CG()));
    totalow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DF()+elevesPremierCycle.getEffeALL1DG()));
    totalow.getCell(21).setText(String.valueOf(total1ereF+total1ere));
    totalow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTAF()+elevesPremierCycle.getEffeESPTAG()));
    totalow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCF()+elevesPremierCycle.getEffeESPTCG()));
    totalow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDF()+elevesPremierCycle.getEffeESPTDG()));
    totalow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTAF()+elevesPremierCycle.getEffeALLTAG()));
    totalow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCF()+elevesPremierCycle.getEffeALLTCG()));
    totalow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDF()+elevesPremierCycle.getEffeALLTDG()));
    totalow.getCell(28).setText(String.valueOf(totalTleF+totalTle));
    totalow.getCell(29).setText(String.valueOf(totalCycle2F+totalCycle2));
    totalow.getCell(30).setText(String.valueOf(totalGlobaleF+totalGlobale));

    Long totalCycle1Affecte= elevesPremierCycle.getEffeESP4Affecte()+elevesPremierCycle.getEffeALL4Affecte()+
        elevesPremierCycle.getEffeESP3Affecte()+elevesPremierCycle.getEffeALL3Affecte();
    Long total2ndAffecte= elevesPremierCycle.getEffeESP2AAffecte()+elevesPremierCycle.getEffeESP2CAffecte()+
        elevesPremierCycle.getEffeALL2AAffecte()+elevesPremierCycle.getEffeALL2CAffecte();
    Long total1ereAffecte= elevesPremierCycle.getEffeESP1AAffecte()+elevesPremierCycle.getEffeESP1CAffecte()+
        elevesPremierCycle.getEffeESP1DAffecte()+
        elevesPremierCycle.getEffeALL1AAffecte()+elevesPremierCycle.getEffeALL1CAffecte()+elevesPremierCycle.getEffeALL1DAffecte();
    Long totalTleAffecte= elevesPremierCycle.getEffeESPTAAffecte()+elevesPremierCycle.getEffeESPTCAffecte()+
        elevesPremierCycle.getEffeESPTDAffecte()+
        elevesPremierCycle.getEffeALLTAAffecte()+elevesPremierCycle.getEffeALLTCAffecte()+elevesPremierCycle.getEffeALLTDAffecte();
    Long totalCycle2Affecte=total2ndAffecte+total1ereAffecte+totalTleAffecte;
    Long totalGlobaleAffecte=totalCycle2Affecte+totalCycle1Affecte;

    XWPFTableRow affecteRow = targetTable.createRow();
    ensureCellCount(affecteRow, 31);
    affecteRow.getCell(0).setText("AFFECTES");
    affecteRow.getCell(1).setText("");
    affecteRow.getCell(2).setText("");
    affecteRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4Affecte()));
    affecteRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4Affecte()));
    affecteRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4Affecte()+elevesPremierCycle.getEffeALL4Affecte()));
    affecteRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3Affecte()));
    affecteRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3Affecte()));
    affecteRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3Affecte()+elevesPremierCycle.getEffeALL3Affecte()));
    affecteRow.getCell(9).setText(String.valueOf(totalCycle1Affecte));
    affecteRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2AAffecte()));
    affecteRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CAffecte()));
    affecteRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2AAffecte()));
    affecteRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CAffecte()));
    affecteRow.getCell(14).setText(String.valueOf(total2ndAffecte));
    affecteRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1AAffecte()));
    affecteRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CAffecte()));
    affecteRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DAffecte()));
    affecteRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1AAffecte()));
    affecteRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CAffecte()));
    affecteRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DAffecte()));
    affecteRow.getCell(21).setText(String.valueOf(total1ereAffecte));
    affecteRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTAAffecte()));
    affecteRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCAffecte()));
    affecteRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDAffecte()));
    affecteRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTAAffecte()));
    affecteRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCAffecte()));
    affecteRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDAffecte()));
    affecteRow.getCell(28).setText(String.valueOf(totalTleAffecte));
    affecteRow.getCell(29).setText(String.valueOf(totalCycle2Affecte));
    affecteRow.getCell(30).setText(String.valueOf(totalGlobaleAffecte));

    Long totalCycle1NonAffecte= elevesPremierCycle.getEffeESP4NonAffecte()+elevesPremierCycle.getEffeALL4NonAffecte()+
        elevesPremierCycle.getEffeESP3NonAffecte()+elevesPremierCycle.getEffeALL3NonAffecte();
    Long total2ndNonAffecte= elevesPremierCycle.getEffeESP2ANonAffecte()+elevesPremierCycle.getEffeESP2CNonAffecte()+
        elevesPremierCycle.getEffeALL2ANonAffecte()+elevesPremierCycle.getEffeALL2CNonAffecte();
    Long total1ereNonAffecte= elevesPremierCycle.getEffeESP1ANonAffecte()+elevesPremierCycle.getEffeESP1CNonAffecte()+
        elevesPremierCycle.getEffeESP1DNonAffecte()+
        elevesPremierCycle.getEffeALL1ANonAffecte()+elevesPremierCycle.getEffeALL1CNonAffecte()+elevesPremierCycle.getEffeALL1DNonAffecte();
    Long totalTleNonAffecte= elevesPremierCycle.getEffeESPTANonAffecte()+elevesPremierCycle.getEffeESPTCNonAffecte()+
        elevesPremierCycle.getEffeESPTDNonAffecte()+
        elevesPremierCycle.getEffeALLTANonAffecte()+elevesPremierCycle.getEffeALLTCNonAffecte()+elevesPremierCycle.getEffeALLTDNonAffecte();
    Long totalCycle2NonAffecte=total2ndNonAffecte+total1ereNonAffecte+totalTleNonAffecte;
    Long totalGlobaleNonAffecte=totalCycle2NonAffecte+totalCycle1NonAffecte;

    XWPFTableRow nonaffecteRow = targetTable.createRow();
    ensureCellCount(nonaffecteRow, 31);
    nonaffecteRow.getCell(0).setText("NON AFFECTES");
    nonaffecteRow.getCell(1).setText("");
    nonaffecteRow.getCell(2).setText("");
    nonaffecteRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4NonAffecte()));
    nonaffecteRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4NonAffecte()));
    nonaffecteRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4NonAffecte()+elevesPremierCycle.getEffeALL4NonAffecte()));
    nonaffecteRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3NonAffecte()));
    nonaffecteRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3NonAffecte()));
    nonaffecteRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3NonAffecte()+elevesPremierCycle.getEffeALL3NonAffecte()));
    nonaffecteRow.getCell(9).setText(String.valueOf(totalCycle1NonAffecte));
    nonaffecteRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2ANonAffecte()));
    nonaffecteRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CNonAffecte()));
    nonaffecteRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2ANonAffecte()));
    nonaffecteRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CNonAffecte()));
    nonaffecteRow.getCell(14).setText(String.valueOf(total2ndNonAffecte));
    nonaffecteRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1ANonAffecte()));
    nonaffecteRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CNonAffecte()));
    nonaffecteRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DNonAffecte()));
    nonaffecteRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1ANonAffecte()));
    nonaffecteRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CNonAffecte()));
    nonaffecteRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DNonAffecte()));
    nonaffecteRow.getCell(21).setText(String.valueOf(total1ereNonAffecte));
    nonaffecteRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTANonAffecte()));
    nonaffecteRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCNonAffecte()));
    nonaffecteRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDNonAffecte()));
    nonaffecteRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTANonAffecte()));
    nonaffecteRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCNonAffecte()));
    nonaffecteRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDNonAffecte()));
    nonaffecteRow.getCell(28).setText(String.valueOf(totalTleNonAffecte));
    nonaffecteRow.getCell(29).setText(String.valueOf(totalCycle2NonAffecte));
    nonaffecteRow.getCell(30).setText(String.valueOf(totalGlobaleNonAffecte));

    Long totalCycle1Classe= elevesPremierCycle.getEffeESP4Classe()+elevesPremierCycle.getEffeALL4Classe()+
        elevesPremierCycle.getEffeESP3Classe()+elevesPremierCycle.getEffeALL3Classe();
    Long total2ndClasse= elevesPremierCycle.getEffeESP2AClasse()+elevesPremierCycle.getEffeESP2CClasse()+
        elevesPremierCycle.getEffeALL2AClasse()+elevesPremierCycle.getEffeALL2CClasse();
    Long total1ereClasse= elevesPremierCycle.getEffeESP1AClasse()+elevesPremierCycle.getEffeESP1CClasse()+
        elevesPremierCycle.getEffeESP1DClasse()+
        elevesPremierCycle.getEffeALL1AClasse()+elevesPremierCycle.getEffeALL1CClasse()+elevesPremierCycle.getEffeALL1DClasse();
    Long totalTleClasse= elevesPremierCycle.getEffeESPTAClasse()+elevesPremierCycle.getEffeESPTCClasse()+
        elevesPremierCycle.getEffeESPTDClasse()+
        elevesPremierCycle.getEffeALLTAClasse()+elevesPremierCycle.getEffeALLTCClasse()+elevesPremierCycle.getEffeALLTDClasse();
    Long totalCycle2Classe=total2ndClasse+total1ereAffecte+totalTleClasse;
    Long totalGlobaleClasse=totalCycle2Classe+totalCycle1Classe;

    XWPFTableRow classeRow = targetTable.createRow();
    ensureCellCount(classeRow, 31);
    classeRow.getCell(0).setText("NOMBRE DE CLASSES PEDAGOGIQUES");
    classeRow.getCell(1).setText("");
    classeRow.getCell(2).setText("");
    classeRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffeESP4Classe()));
    classeRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffeALL4Classe()));
    classeRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffeESP4Classe()+elevesPremierCycle.getEffeALL4Classe()));
    classeRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffeESP3Classe()));
    classeRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffeALL3Classe()));
    classeRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffeESP3Classe()+elevesPremierCycle.getEffeALL3Classe()));
    classeRow.getCell(9).setText(String.valueOf(totalCycle1Classe));
    classeRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffeESP2AClasse()));
    classeRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffeESP2CClasse()));
    classeRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffeALL2AClasse()));
    classeRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffeALL2CClasse()));
    classeRow.getCell(14).setText(String.valueOf(total2ndClasse));
    classeRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffeESP1AClasse()));
    classeRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffeESP1CClasse()));
    classeRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffeESP1DClasse()));
    classeRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffeALL1AClasse()));
    classeRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffeALL1CClasse()));
    classeRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffeALL1DClasse()));
    classeRow.getCell(21).setText(String.valueOf(total1ereClasse));
    classeRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffeESPTAClasse()));
    classeRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffeESPTCClasse()));
    classeRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffeESPTDClasse()));
    classeRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffeALLTAClasse()));
    classeRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffeALLTCClasse()));
    classeRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffeALLTDClasse()));
    classeRow.getCell(28).setText(String.valueOf(totalTleClasse));
    classeRow.getCell(29).setText(String.valueOf(totalCycle2Classe));
    classeRow.getCell(30).setText(String.valueOf(totalGlobaleClasse));


    for (XWPFTableRow row : targetTable.getRows()) {
      for (XWPFTableCell cell : row.getTableCells()) {
        // Obtenez les propriétés de la cellule
        CTTcPr cellProperties = cell.getCTTc().addNewTcPr();
        CTTcBorders borders = cellProperties.addNewTcBorders();

        // Définir les bordures pour chaque côté
        setBorder(borders.addNewTop(), "000000", 8);
        setBorder(borders.addNewBottom(), "000000", 8);
        setBorder(borders.addNewLeft(), "000000", 8);
        setBorder(borders.addNewRight(), "000000", 8);
      }
    }

  }
}


      }



    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
  public static void adjustTableRows(XWPFTable targetTable, XWPFTableRow headerRow) {
    // Liste pour stocker les largeurs des cellules de l'entête
    List<Integer> cellWidths = new ArrayList<>();

    // Récupérer les largeurs des cellules de l'entête
    for (XWPFTableCell headerCell : headerRow.getTableCells()) {
      CTTcPr tcPr = headerCell.getCTTc().getTcPr();
      if (tcPr != null && tcPr.isSetTcW()) {
        // Vérifier le type de l'objet retourné par getW()
        Object widthObj = tcPr.getTcW().getW();
        if (widthObj instanceof BigInteger) {
          // Si c'est un BigInteger, on le convertit en int
          cellWidths.add(((BigInteger) widthObj).intValue());
        } else {
          // Sinon, on ajoute une valeur par défaut
          cellWidths.add(2000);
        }
      } else {
        cellWidths.add(2000); // Largeur par défaut si non définie
      }
    }

    // Parcourir les lignes du tableau et ajuster les cellules
    for (XWPFTableRow row : targetTable.getRows()) {
      List<XWPFTableCell> cells = row.getTableCells();
      for (int i = 0; i < cells.size(); i++) {
        XWPFTableCell cell = cells.get(i);

        // Obtenir ou ajouter les propriétés de la cellule
        CTTcPr cellProperties = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();

        // Définir la largeur de la cellule en fonction de l'entête
        CTTblWidth cellWidth = cellProperties.isSetTcW() ? cellProperties.getTcW() : cellProperties.addNewTcW();
        cellWidth.setW(BigInteger.valueOf(cellWidths.get(i)));
        cellWidth.setType(STTblWidth.DXA);  // Indiquer que la largeur est en DXA (twips)

        // Ajouter les bordures aux cellules
        CTTcBorders borders = cellProperties.isSetTcBorders() ? cellProperties.getTcBorders() : cellProperties.addNewTcBorders();
        setBorder(borders.addNewTop(), "000000", 8);   // Bordure supérieure
        setBorder(borders.addNewBottom(), "000000", 8); // Bordure inférieure
        setBorder(borders.addNewLeft(), "000000", 8);   // Bordure gauche
        setBorder(borders.addNewRight(), "000000", 8);  // Bordure droite
      }
    }
  }
  private static void setBorder(CTBorder border, String color, int size) {
    border.setVal(STBorder.SINGLE);
    border.setSz(BigInteger.valueOf(size));
    border.setColor(color);
  }

  private static void adjustRowCellsToHeader(XWPFTableRow row, List<Integer> headerCellWidths) {
    for (int i = 0; i < row.getTableCells().size(); i++) {
      XWPFTableCell cell = row.getCell(i);
      CTTcPr tcPr = cell.getCTTc().getTcPr();

      // Ajuster la largeur de la cellule en fonction de la largeur de l'en-tête
      if (tcPr == null) {
        tcPr = cell.getCTTc().addNewTcPr();
      }

      CTTblWidth tblWidth = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
      tblWidth.setW(BigInteger.valueOf(headerCellWidths.get(i)));
      tblWidth.setType(STTblWidth.DXA);
    }
  }

    private  void ajoutTableauDynamique(LangVivanteDto elevesPremierCycle,
                                              XWPFTable table,Long idEcole ,String libelleAnnee, String trimestre) {





      String currentValue = "";
      int nombreTotalClasseParNiveau1=0;
      int nombreTotalClasseParNiveau2=0;
      Double moyenCycle1F=0d;
      Double moyenCycle1G=0d;
      Double moyenCycle2F=0d;
      Double moyenCycle2G=0d;
      Double moyenGeneralF=0d;
      Double moyenGeneralG=0d;



    }



  private static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
    for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
      XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
      if (rowIndex == fromRow) {
        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
      } else {
        cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
      }
    }
  }


    public static double arrondie(double d) {
      BigDecimal bd = new BigDecimal(Double.toString(d));
      bd = bd.setScale(2, RoundingMode.HALF_UP); // Arrondi à deux chiffres après la virgule
      return bd.doubleValue();
    }

    public int getNombreDeclasseParNiveau(Long idEcole,String libelleAnnee ,String libelleTrimetre,String niveau){
      List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
      TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.niveau=:niveau " +
          "group by b.libelleClasse", NiveauDto.class);
      classeNiveauDtoList = q.setParameter("idEcole", idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimetre)
          .setParameter("niveau", niveau)
          . getResultList() ;
      return classeNiveauDtoList.size() ;
    }

}
