package br.edu.cs.poo.ac.utils;

import java.util.regex.Pattern;

public class StringUtils {

    public static boolean estaVazia(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean tamanhoExcedido(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }
        if (str == null) {
            return tamanho > 0;
        }
        return str.length() > tamanho;
    }

    public static boolean emailValido(String email) {
        if (estaVazia(email)) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean telefoneValido(String tel) {
        if (estaVazia(tel)) {
            return false;
        }
        String telRegex = "^\\(\\d{2}\\)\\d{8,9}$";
        Pattern pattern = Pattern.compile(telRegex);
        return pattern.matcher(tel).matches();
    }

    public static boolean tamanhoMenor(String str, int tamanho) {
        int tamStr = (str == null) ? 0 : str.length();
        return tamStr < tamanho;
    }
}