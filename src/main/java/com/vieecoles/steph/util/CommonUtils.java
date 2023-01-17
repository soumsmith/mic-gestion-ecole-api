package com.vieecoles.steph.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonUtils {

	public static double roundDouble(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static BigDecimal roundBigDecimal(BigDecimal value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = value;
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd;
	}
}
