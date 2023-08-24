package com.vieecoles.dto;

import com.vieecoles.projection.LivretScolaireSpiderSelectDto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Iterator;
import java.util.List;

public class spiderLivretDto implements JRDataSource {
    private List<LivretScolaireSpiderSelectDto> beanList ;
    private Iterator<LivretScolaireSpiderSelectDto> iterator;
    private LivretScolaireSpiderSelectDto currentBean;

    public spiderLivretDto(List<LivretScolaireSpiderSelectDto> beanList) {
        this.beanList = beanList;
        this.iterator = beanList.iterator();
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            currentBean = iterator.next();
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        // Implement how to retrieve fields from the currentBean
        // based on the field name in jrField and return the value
        return null;
    }
}
