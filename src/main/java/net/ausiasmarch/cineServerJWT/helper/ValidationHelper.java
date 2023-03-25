package net.ausiasmarch.cineServerJWT.helper;

import java.time.Duration;
import java.time.LocalDateTime;

import net.ausiasmarch.cineServerJWT.exceptions.ValidationException;

public class ValidationHelper {
    
    public static final String exprRegEmail = "^.+@.+\\..+$";
    
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void validateRPP(int iRPP) {
        if (iRPP < 1 || iRPP > 1000) {
            throw new ValidationException("RPP value is not valid (must be between 1 and 1000)");
        }
    }

    public static void validateStringLength(String strNombre, int minlength, int maxlength, String error) {
        if (strNombre.length() < minlength || strNombre.length() > maxlength) {
            throw new ValidationException("Longitud inadecuada: " + error);
        }
    }

    public static boolean validatePattern(String strInput, String strPattern) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(strPattern);
        java.util.regex.Matcher m = p.matcher(strInput);
        return m.matches();
    }

    public static void validateDNI(String itDNI, String error) {
        String strDNI = itDNI.trim().replaceAll(" ", "");
        //VALIDACION DNI SIMPLIFICADA
        if (strDNI.length() != 9 || !isNumeric(strDNI.substring(0, 8))) {
            throw new ValidationException(error);
        }
        else{
            int intPartDNI = Integer.parseInt(strDNI.substring(0, 8));
            char cLetraDNI = strDNI.charAt(8);
            int valNumDni = intPartDNI % 23;
            if ("TRWAGMYFPDXBNJZSQVHLCKE".charAt(valNumDni) != cLetraDNI) {
                throw new ValidationException(error);
            }
        }

        /*if (strDNI.length() == 9) {
            if (isNumeric(strDNI.substring(0, 8))) {
                int intPartDNI = Integer.parseInt(strDNI.substring(0, 8));
                char cLetraDNI = strDNI.charAt(8);
                int valNumDni = intPartDNI % 23;
                if ("TRWAGMYFPDXBNJZSQVHLCKE".charAt(valNumDni) != cLetraDNI) {
                    throw new ValidationException("error de validación: " + error);
                }
            } else {
                throw new ValidationException("error de validación: " + error);
            }
        } else {
            throw new ValidationException("error de validación: " + error);
        }*/
    }

    public static void validateEmail(String email, String error) {
        validateStringLength(email, 3, 255, error);
        //String ePattern = "^.+@.+\\..+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(exprRegEmail);
        java.util.regex.Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new ValidationException(error);
        }
    }
    
    //Validacion para año de Pelicula
    public static void validateInt(int numero, int minLenght, int maxLenght, String error) {
        if (numero < minLenght || numero > maxLenght) {
            throw new ValidationException(error);
        } 
    }

    public static void validateLogin(String strLogin, String error) {
        validateStringLength(strLogin, 4, 20, error);
        String ePattern = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){4,18}[a-zA-Z0-9]$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(strLogin);
        if (!m.matches()) {
            throw new ValidationException(error);
        }
    }

    public static void validateIntRange(int iNumber, int iMin, int iMax, String error) {
        if (iNumber >= iMin && iNumber <= iMax) {
        } else {
            throw new ValidationException("Error: " + error);
        }
    }

    public static void validatePrecio(double precio, double precio2, String error) {
        if(precio != precio2) {
            throw new ValidationException("Error: " + error);
        }
    }

    public static void validateDoubleRange(double iNumber, double iMin, double iMax, String error) {
        if (iNumber >= iMin && iNumber <= iMax) {
        } else {
            throw new ValidationException("Error: " + error);
        }
    }

    public static void validateDate(LocalDateTime oDate, LocalDateTime oDateStart, LocalDateTime oDateEnd,
            String error) {
        Long lDur1 = Duration.between(oDateStart, oDate).toMillis();
        Long lDur2 = Duration.between(oDate, oDateEnd).toMillis();
        if (lDur1 > 0L && lDur2 > 0L) {
        } else {
            throw new ValidationException("error de validación: " + error);
        }
    }

    public static void validateFechaFinal(LocalDateTime fechaAlta, LocalDateTime fechaBaja, String error) {
        if( fechaBaja != null && Duration.between(fechaAlta, fechaBaja).isNegative() ) {
            throw new ValidationException(error);    
        }  
    }
}
