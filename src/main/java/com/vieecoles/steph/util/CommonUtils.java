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
	
	public static String appreciation(Double moyenne) {
		String apprec = "";
		if (moyenne >= 17.0)
			apprec = "Excellent";
		else if (moyenne >= 16.0)
			apprec = "Très Bien";
		else if (moyenne >= 14.0)
			apprec = "Bien";
		else if (moyenne >= 12.0)
			apprec = "Assez Bien";
		else if (moyenne >= 10.0)
			apprec = "Passable";
		else if (moyenne >= 8.0)
			apprec = "Insuffisant";
		else if (moyenne >= 6.0)
			apprec = "Faible";
		else
			apprec = "Très Faible";

		return apprec;
	}

}
