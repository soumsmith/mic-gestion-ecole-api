package com.vieecoles.steph.comparators;

import com.vieecoles.steph.dto.MoyenneEleveDto;

import java.util.Comparator;
import java.util.Map;

public class ValueEleveComparator implements Comparator<Double> {
    Map<Double, MoyenneEleveDto> base;

    public ValueEleveComparator(Map<Double, MoyenneEleveDto> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(Double a, Double b) {
        if (a >= b) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
