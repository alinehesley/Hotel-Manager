package Classes;

public class Validacao {

    // VALIDAÇÃO DE CPF //
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (QuantDigitosCPF(cpf) && DigitosIguais(cpf) == false && CalculaDigitosCPF(cpf))
            return true;
        return false;
    }

    // Verifica se o cpf possui 11 dígitos
    private static boolean QuantDigitosCPF(String cpf) {
        if (cpf.length() != 11)
            return false;
        else {
            return true;
        }
    }

    // Verifica se todos os dígitos são iguais
    private static boolean DigitosIguais(String cpf) {
        int i;
        for (i = 1; i < cpf.length(); i++)
            if (cpf.charAt(0) != cpf.charAt(i))
                return false;
        return true;
    }

    // Calcula os digitos verificadores
    private static boolean CalculaDigitosCPF(String cpf) {
        int i;
        int soma_digito1 = 0;
        int soma_digito2 = 0;

        for (i = 0; i < 9; i++) {
            soma_digito1 += (cpf.charAt(i) - 48) * (10 - i);
        }
        int mod_soma1 = soma_digito1 % 11;
        int digito1 = cpf.charAt(9) - 48;

        if (mod_soma1 <= 1) {
            if (digito1 != 0)
                return false;
        } else {
            if (11 - mod_soma1 != digito1)
                return false;
        }
        for (i = 1; i < 10; i++) {
            soma_digito2 += (cpf.charAt(i) - 48) * (10 - i + 1);
        }
        int mod_soma2 = soma_digito2 % 11;
        int digito2 = cpf.charAt(10) - 48;

        if (mod_soma2 <= 1) {
            if (digito2 != 0)
                return false;
        } else {
            if (11 - mod_soma2 != digito2)
                return false;
        }

        return true;
    }

    // VALIDAÇÃO DE CNPJ//

    public static boolean validarCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (QuantDigitosCNPJ(cnpj) && CalculaDigitosCNPJ(cnpj))
            return true;
        return false;
    }

    // Verifica se o cnpj possui 14 digitos
    private static boolean QuantDigitosCNPJ(String cnpj) {
        if (cnpj.length() != 14) {
            return false;
        }
        return true;
    }

    // Calcula os digitos verificadores CNPJ
    public static boolean CalculaDigitosCNPJ(String cnpj) {
        int i;
        int soma_digito1 = 0;
        int soma_digito2 = 0;
        int digito1;
        int digito2;

        int[] m1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] m2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        for (i = 0; i < 12; i++) {
            soma_digito1 += (cnpj.charAt(i) - 48) * m1[i];
        }

        int mod_digito1 = soma_digito1 % 11;
        if (mod_digito1 < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - mod_digito1;
        }

        for (i = 0; i < 13; i++) {
            soma_digito2 += (cnpj.charAt(i) - 48) * m2[i];
        }

        int mod_digito2 = soma_digito2 % 11;
        if (mod_digito2 < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - mod_digito2;
        }

        if (digito1 == (cnpj.charAt(12) - 48) && digito2 == (cnpj.charAt(13) - 48)) {
            return true;
        }
        return false;
    }

    // VALIDAÇÃO DE NOME//
    public static boolean validaNome(String nome) {
        return nome.matches("[a-zA-z\\s]*"); // busca a ocorrencia de letras maisc, min e espacos 0 ou mais vezes
        // devolve true se n for encontrado nada diferente disso
    }
}
