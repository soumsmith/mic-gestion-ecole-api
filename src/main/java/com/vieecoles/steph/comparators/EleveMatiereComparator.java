package com.vieecoles.steph.comparators;

import com.vieecoles.steph.dto.MoyenneEleveDto;

import java.util.Comparator;

public class EleveMatiereComparator implements Comparator<MoyenneEleveDto> {
    MoyenneEleveDto base;

    public EleveMatiereComparator(MoyenneEleveDto base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(MoyenneEleveDto a, MoyenneEleveDto b) {
        if (a.getMoyenne() >= b.getMoyenne()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
