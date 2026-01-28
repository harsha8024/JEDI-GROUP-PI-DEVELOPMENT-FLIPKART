package com.flipfit.utils;

import java.util.regex.Pattern;

/**
 * Utility class for input validations.
 */
public final class ValidationUtils {

    private ValidationUtils() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$", Pattern.CASE_INSENSITIVE);
    private static final Pattern GSTIN_PATTERN = Pattern.compile("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String digits = phone.replaceAll("\\D", "");
        return digits.length() == 10;
    }

    public static boolean isValidPan(String pan) {
        if (pan == null) return false;
        return PAN_PATTERN.matcher(pan.trim()).matches();
    }

    public static boolean isValidAadhar(String aadhar) {
        if (aadhar == null) return false;
        String digits = aadhar.replaceAll("\\D", "");
        return digits.length() == 12;
    }

    public static boolean isValidGstin(String gstin) {
        if (gstin == null) return false;
        return gstin.trim().length() == 15;
        // For stricter validation use GSTIN_PATTERN but there are variations; basic length check is safe.
    }
}
