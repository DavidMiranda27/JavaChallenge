package br.com.totemti;

import java.util.Objects;
import java.util.function.Predicate;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    private static String gerarIdentificador(final String frase) {
        

        String[] preposicoes = new String[] {
            "a", "ante", "ate", "apos", "com", "contra", "de", "desde", "desde", "em", "entre",
            "para", "pra", "perante", "por", "per", "sem", "sob", "sobre", "tras", "da", "do"
        };
    
        String[] artigos = new String[] {
            "o", "os", "a", "as", "um", "uns", "uma", "umas"
        };
    
        String[] conjuncoes = new String[] { "e" };

        if(frase == null) {
            return null;
        }

        if(frase.isEmpty()) {
            return "";
        }

        String palavras[] = frase.split(" ");

        if(palavras.length == 0) {
            return ""; 
        }

        ArrayList<String> palavrasList = new ArrayList<String>(Arrays.asList(palavras)); ///Convert array to list

        if(palavrasList.size() == 1) {
            String finalString = palavrasList.get(0);
            finalString = concatFinalString(finalString, "_t");
            System.out.println(finalString);
            return finalString;
        }

        ArrayList<String> preposicoesList = new ArrayList<String>(Arrays.asList(preposicoes)); ///Convert array to list
        palavrasList = removeWords(palavrasList, preposicoesList);

        ArrayList<String> artigosList = new ArrayList<String>(Arrays.asList(artigos)); ///Convert array to list
        palavrasList = removeWords(palavrasList, artigosList);

        ArrayList<String> conjuncoesList = new ArrayList<String>(Arrays.asList(conjuncoes));///Convert array to list
        palavrasList = removeWords(palavrasList, conjuncoesList);

        for (String palavra : palavrasList) {
            if (palavrasList.indexOf(palavra) != 0 &&
            palavrasList.indexOf(palavra) != palavrasList.size() - 1 &&
            palavra.length() > 4
            ) {
                palavrasList.set(palavrasList.indexOf(palavra), palavra.substring(0, 4));
            }
        }

        for (String palavra : palavrasList) {
            if (palavrasList.indexOf(palavra) != 0) {
                palavrasList.set(palavrasList.indexOf(palavra), palavra.substring(0, 1).toUpperCase() + palavra.substring(1));
            }
        }

        palavrasList.set(0, palavrasList.get(0).toLowerCase());

        String finalString = String.join("", palavrasList);
        finalString = concatFinalString(finalString, "_t");

        return finalString;
    }

    public static ArrayList<String> removeWords(ArrayList<String> palavrasList, ArrayList<String> wordsList) {
        for (int i = 0; i < palavrasList.size(); i++) {
            if (wordsList.contains(palavrasList.get(i))) {
                palavrasList.remove(i);
            }
        }
        return palavrasList;
    }

    public static String concatFinalString(String finalString, String word) {
        String newFinalString = finalString + word;
        return newFinalString;
    }

    public static void main(final String[] args) {
        verificarPreCondicoes(
                gerarIdentificador(null),
                Objects::isNull,
                "O valor gerado deve ser nulo"
        );
        verificarPreCondicoes(
                gerarIdentificador(""),
                String::isEmpty,
                "O valor gerado deve estar vazio"
        );
        verificarPreCondicoes(
                gerarIdentificador("Dia inicial da prestação de contas"),
                t -> t.equals("diaInicPresContas_t"),
                "O valor gerado deve ser igual a diaInicPresContas_t"
        );
        verificarPreCondicoes(
                gerarIdentificador("numero"),
                t -> t.equals("numero_t"),
                "O valor gerado deve ser igual a numero_t"
        );
        verificarPreCondicoes(
                gerarIdentificador("Informações alteradas do Dependente"),
                t -> t.equals("informaçõesAlteDependente_t"),
                "O valor gerado deve ser igual a informaçõesAlteDependente_t"
        );
    }

    private static void verificarPreCondicoes(
            String identificadorGerado,
            Predicate<String> condition,
            String mensagem
    ) {
        boolean test = condition.test(identificadorGerado);
        if (!test) {
            System.err.println(mensagem + " ❌");
        }
        else {
            System.out.println("Sucesso ✅");
        }
    }

}
