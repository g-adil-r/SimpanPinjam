package com.example.myapplication.Helper;

import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;

public class CurrencyHelper {
    private static DecimalFormat kursIndonesia;
    private static DecimalFormatSymbols formatRp;

    public static String stringFormatIDR(int num) {
        if (formatRp == null) {
            formatRp = new DecimalFormatSymbols();
            formatRp.setCurrencySymbol("Rp");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');
        }

        if (kursIndonesia == null) {
            kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            kursIndonesia.setDecimalFormatSymbols(formatRp);
        }

        return kursIndonesia.format(num);
    }
}
