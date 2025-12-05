package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {

    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }

        String limpo = cpfCnpj.replaceAll("[^0-9]", "");

        if (limpo.isEmpty() || !limpo.matches("\\d+")) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }

        if (isCPF(limpo)) {
            ErroValidacaoCPFCNPJ erro = validarCPF(limpo);
            return new ResultadoValidacaoCPFCNPJ(true, false, erro);
        } else if (isCNPJ(limpo)) {
            ErroValidacaoCPFCNPJ erro = validarCNPJ(limpo);
            return new ResultadoValidacaoCPFCNPJ(false, true, erro);
        } else {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
    }

    public static boolean isCPF(String valor) {
        return valor != null && valor.length() == 11;
    }

    public static boolean isCNPJ(String valor) {
        return valor != null && valor.length() == 14;
    }

    public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        if (!isDigitoVerificadorValidoCPF(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        return null; // Sem erros
    }

    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        if (!isDigitoVerificadorValidoCNPJ(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        return null; // Sem erros
    }

    private static boolean isDigitoVerificadorValidoCPF(String cpf) {
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int resto = 11 - (soma % 11);
            char dv1 = (resto >= 10) ? '0' : (char) (resto + '0');

            if (dv1 != cpf.charAt(9)) return false;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            resto = 11 - (soma % 11);
            char dv2 = (resto >= 10) ? '0' : (char) (resto + '0');

            return dv2 == cpf.charAt(10);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += (cnpj.charAt(i) - '0') * pesos1[i];
            }
            int resto = soma % 11;
            char dv1 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            if (dv1 != cnpj.charAt(12)) return false;

            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += (cnpj.charAt(i) - '0') * pesos2[i];
            }
            resto = soma % 11;
            char dv2 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            return dv2 == cnpj.charAt(13);
        } catch (Exception e) {
            return false;
        }
    }
}