package mini.asaas.utils

import mini.asaas.enums.PersonType

class CpfCnpjUtils {

    public static final int CNPJ_LENGTH = 14

    public static final int CPF_LENGTH = 11

    public static final INVALID_CPF_SEQUENCES = [	"00000000000", "11111111111", "22222222222", "33333333333",
                                                     "44444444444", "55555555555", "66666666666", "77777777777",
                                                     "88888888888", "99999999999"]

    public static Boolean validate(String cpfCnpj) {
        cpfCnpj = StringUtils.removeNonNumeric(cpfCnpj)

        if (cpfCnpj == null || (cpfCnpj.length() != CPF_LENGTH && cpfCnpj.length() != CNPJ_LENGTH)) return false

        if (isCpf(cpfCnpj)) return validateCpf(cpfCnpj)
        if (isCnpj(cpfCnpj)) return validateCnpj(cpfCnpj)

        return null
    }

    public static Boolean isCnpj(String cpfCnpj) {
        if (!cpfCnpj) return false

        cpfCnpj = StringUtils.removeNonNumeric(cpfCnpj)

        return cpfCnpj.length() == CNPJ_LENGTH
    }

    public static Boolean isCpf(String cpfCnpj) {
        if (!cpfCnpj) return false

        cpfCnpj = StringUtils.removeNonNumeric(cpfCnpj)

        return cpfCnpj.length() == CPF_LENGTH
    }

    public static getPersonType(String cpfCnpj) {
        if (isCnpj(cpfCnpj)) return PersonType.LEGAL
        if (isCpf(cpfCnpj)) return PersonType.NATURAL

        return null
    }

    public static Boolean validateCpf(String strCpf) {
        Integer iDigito1Aux = 0, iDigito2Aux = 0, iDigitoCPF
        Integer iDigito1 = 0, iDigito2 = 0, iRestoDivisao = 0
        String strDigitoVerificador, strDigitoResultado

        if (!validateCpfSequence(strCpf)) return false

        if (strCpf.substring(0, 1).equals("")) return false

        try {
            strCpf = strCpf.replace('.', ' ')
            strCpf = strCpf.replace('-', ' ')
            strCpf = strCpf.replaceAll(" ", "")

            for (Integer iCont = 1; iCont < strCpf.length() -1; iCont++) {
                iDigitoCPF = Integer.valueOf(strCpf.substring(iCont -1, iCont)).intValue()
                iDigito1Aux = iDigito1Aux + (11 - iCont) * iDigitoCPF
                iDigito2Aux = iDigito2Aux + (12 - iCont) * iDigitoCPF
            }

            iRestoDivisao = (iDigito1Aux % 11)
            if (iRestoDivisao < 2) {
                iDigito1 = 0
            } else {
                iDigito1 = 11 - iRestoDivisao
            }

            iDigito2Aux += 2 * iDigito1
            iRestoDivisao = (iDigito2Aux % 11)
            if (iRestoDivisao < 2) {
                iDigito2 = 0
            } else {
                iDigito2 = 11 - iRestoDivisao
            }

            strDigitoVerificador = strCpf.substring(strCpf.length()-2, strCpf.length())
            strDigitoResultado = String.valueOf(iDigito1) + String.valueOf(iDigito2)

            return strDigitoVerificador.equals(strDigitoResultado)
        } catch (Exception exception) {
            return false
        }
    }

    public static Boolean validateCnpj(String strCNPJ) {
        Integer iSoma = 0, iDigito
        char[] chCaracteresCNPJ
        String strCNPJ_Calculado

        if (strCNPJ.startsWith("00000000000000")) return false

        if (strCNPJ.substring(0, 1).equals("")) return false

        try {
            strCNPJ = strCNPJ.replace('.', ' ')
            strCNPJ = strCNPJ.replace('/', ' ')
            strCNPJ = strCNPJ.replace('-', ' ')
            strCNPJ = strCNPJ.replaceAll(" ", "")
            strCNPJ_Calculado = strCNPJ.substring(0, 12)

            if (strCNPJ.length() != 14) return false

            chCaracteresCNPJ = strCNPJ.toCharArray()
            for (Integer i = 0; i < 4; i++) {
                if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i] - 48 ) * (6 - (i + 1))
                }
            }

            for (Integer i = 0; i < 8; i++) {
                if ((chCaracteresCNPJ[i+4]-48 >= 0) && (chCaracteresCNPJ[i+4]-48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i+4] - 48 ) * (10 - (i + 1))
                }
            }

            iDigito = 11 - (iSoma % 11)
            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito)

            iSoma = 0
            for (Integer i = 0; i < 5; i++) {
                if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1))
                }
            }

            for (Integer i = 0; i < 8; i++) {
                if ((chCaracteresCNPJ[i+5]-48 >= 0) && (chCaracteresCNPJ[i+5]-48 <= 9)) {
                    iSoma += (chCaracteresCNPJ[i+5] - 48) * (10 - (i + 1))
                }
            }
            iDigito = 11 - (iSoma % 11)
            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito)

            return strCNPJ.equals(strCNPJ_Calculado)
        } catch (Exception exception) {
            return false
        }
    }

    private static Boolean validateCpfSequence(String strCpf) {
        if (strCpf?.trim().isEmpty()) return false
        if (INVALID_CPF_SEQUENCES.contains(strCpf)) return false

        return true
    }
}