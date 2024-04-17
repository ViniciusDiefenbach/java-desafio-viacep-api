package validations;

import exceptions.InvalidCepInputFormat;

import java.util.regex.Pattern;

public class CepValidation {
    static Pattern CEP_WITH_NO_SPECIAL_CHARS_PATTERN = Pattern.compile("^\\d{8}$");
    static Pattern CEP_WITH_ONLY_A_DASH_PATTERN = Pattern.compile("^\\d{5}-\\d{3}$");
    static Pattern CEP_WITH_A_DOT_AND_A_DASH_PATTERN = Pattern.compile("^\\d{2}\\.\\d{3}-\\d{3}$");
    public static String validate(String cep) {
        if (CEP_WITH_NO_SPECIAL_CHARS_PATTERN.matcher(cep).find()) {
            return cep;
        } else if (CEP_WITH_ONLY_A_DASH_PATTERN.matcher(cep).find()) {
            return cep.replace("-", "");
        } else if (CEP_WITH_A_DOT_AND_A_DASH_PATTERN.matcher(cep).find()) {
            return cep.replace(".", "").replace("-", "");
        } else {
            throw new InvalidCepInputFormat();
        }
    }



}
