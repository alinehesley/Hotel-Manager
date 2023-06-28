package Classes;
import Classes.exceptions.CPFInvalidoException;
import Classes.exceptions.ClienteException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Arquivos {
    private Scanner input = new Scanner(System.in);
    private static final String pathArquivos= "src/Arquivos";

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public String getPathArquivos() {
        return pathArquivos;
    }


    public void salvaDadosClienteTitular(ArrayList<ClienteTitular> clientes){
        try{
            File dadosClienteTitular = new File(pathArquivos + "/dados_cliente_titular.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(dadosClienteTitular));
            // Escreve o cabeçalho da tabela
            writer.write("nome,nascimento,cpf,conta");
            writer.newLine();

            for(ClienteTitular clienteTitular: clientes) {
                String linha = clienteTitular.getNome() + ","
                        + clienteTitular.getDataNascimento() + ","
                        + clienteTitular.getCpf() + ","
                        + clienteTitular.getConta() + ",";
                writer.write(linha);
                writer.newLine();
            }

            // Salva o arquivo
            writer.flush();
         }catch (IOException e) {
        System.out.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }

    public static ArrayList<ClienteTitular> carregaDadosClienteTitular() {
        ArrayList<ClienteTitular> clientesTitulares = new ArrayList<>();

        try  {
            File dadosClienteTitular = new File(pathArquivos + "/dados_cliente_titular.csv");
            BufferedReader reader = new BufferedReader(new FileReader(dadosClienteTitular));
            // Lê a primeira linha (cabeçalho) e descarta
            String cabecalho = reader.readLine();

            // Lê as linhas de dados
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Divide a linha em campos separados por vírgula
                String[] campos = linha.split(",");

                // Obtém os valores dos campos
                String nome = campos[0];
                LocalDate dataNascimento = LocalDate.parse(campos[1]);
                String cpf = campos[2];
                double conta = Double.parseDouble(campos[3]);

                // Cria o objeto ClienteTitular e adiciona à lista
                try {
                    ClienteTitular clienteTitular = new ClienteTitular(nome, dataNascimento, cpf, conta);
                    clientesTitulares.add(clienteTitular);
                } catch ( ClienteException e){
                    System.out.println("Erro ao carregar o cliente: " + e.getMessage());
                }

            }

            System.out.println("Dados carregados do arquivo CSV com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return clientesTitulares;
    }


    public void salvaDadosClienteDependente(ArrayList<ClienteDependente> clientes){

        try{
            File dadosClienteDependente = new File(pathArquivos + "/dados_cliente_dependente.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(dadosClienteDependente));
            // Escreve o cabeçalho da tabela
            writer.write("nome,nascimento,cpf,conta,cpftitular");
            writer.newLine();

            for(ClienteDependente cliente: clientes) {
                String linha = cliente.getNome() + ","
                        + cliente.getDataNascimento() + ","
                        + cliente.getCpf() + ","
                        + cliente.getConta() + ",";
                if(cliente.getTitular() != null){
                    linha += cliente.getTitular().getCpf();
                }else{
                    linha += " ";
                }
                writer.write(linha);
                writer.newLine();
            }

            // Salva o arquivo
            writer.flush();
        }catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }
    // Função que recebe a lista de Clientes titulares e retorna o que tem um determinado CPF, se não encontrar retorna null
    public static ClienteTitular getTitularByCpfFromList(String cpf, ArrayList<ClienteTitular> titulares){
        for(ClienteTitular titular: titulares){
            if(titular.getCpf().equals(cpf)){
                return titular;
            }
        }
        return null;
    }
    public static ArrayList<ClienteDependente> carregaDadosClienteDependente(ArrayList<ClienteTitular> titulares) {
        ArrayList<ClienteDependente> clientesDependentes = new ArrayList<>();

        try  {
            File dadosClienteDependente = new File(pathArquivos + "/dados_cliente_dependente.csv");
            BufferedReader reader = new BufferedReader(new FileReader(dadosClienteDependente));
            // Lê a primeira linha (cabeçalho) e descarta
            String cabecalho = reader.readLine();

            // Lê as linhas de dados
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Divide a linha em campos separados por vírgula
                String[] campos = linha.split(",");

                // Obtém os valores dos campos
                String nome = campos[0];
                LocalDate dataNascimento = LocalDate.parse(campos[1]);
                String cpf = campos[2];
                double conta = Double.parseDouble(campos[3]);
                String cpfTitular = campos[4];
                ClienteTitular titular = getTitularByCpfFromList(cpfTitular, titulares);

                if(titular == null){
                    try {
                        ClienteDependente dependente = new ClienteDependente(nome, dataNascimento, cpf, conta);
                        clientesDependentes.add(dependente);
                    } catch ( ClienteException e){
                        System.out.println("Erro ao carregar o cliente: " + e.getMessage());
                    }

                } else{
                    try {
                        ClienteDependente dependente = new ClienteDependente(nome, dataNascimento, cpf, titular, conta);
                        clientesDependentes.add(dependente);
                    } catch ( ClienteException e){
                        System.out.println("Erro ao carregar o cliente: " + e.getMessage());
                    }
                }

            }

            System.out.println("Dados carregados do arquivo CSV com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return clientesDependentes;
    }

    public void salvaDadosQuartos(ArrayList<Quarto> quartos){

        try{
            File dadosQuartos = new File(pathArquivos + "/dados_quartos.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(dadosQuartos));
            // Escreve o cabeçalho da tabela
            writer.write("numero,disponivel,cpftitular,totalCamaCasal,totalCamaSolteiro,dataEntrada,dataSaida,precoEstadia");
            writer.newLine();

            for(Quarto quarto: quartos) {
                if(quarto.ehDisponivel()){
                    String linha = quarto.getNumero() + ","
                            + quarto.ehDisponivel() + ","
                            + "" + ","
                            + quarto.getTotalCamaCasal() + ","
                            + quarto.getTotalCamaSolteiro() + ","
                            + "" + ","
                            + "" + ","
                            + "350";
                    writer.write(linha);
                    writer.newLine();
                } else{
                    String linha = quarto.getNumero() + ","
                            + quarto.ehDisponivel() + ","
                            + quarto.getTitular().getCpf() + ","
                            + quarto.getTotalCamaCasal() + ","
                            + quarto.getTotalCamaSolteiro() + ","
                            + quarto.getDataEntrada() + ","
                            + quarto.getDataSaida()+ ","
                            + "350";
                    writer.write(linha);
                    writer.newLine();

                }
            }

            // Salva o arquivo
            writer.flush();
        }catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }

    public static ArrayList<Quarto> carregaDadosQuarto(ArrayList<ClienteTitular> titulares) {
        ArrayList<Quarto> quartos = new ArrayList<>();

        try  {
            File dadosClienteTitular = new File(pathArquivos + "/dados_quartos.csv");
            BufferedReader reader = new BufferedReader(new FileReader(dadosClienteTitular));
            // Lê a primeira linha (cabeçalho) e descarta
            String cabecalho = reader.readLine();

            // Lê as linhas de dados
            //"numero,disponivel,cpftitular,totalCamaCasal,totalCamaSolteiro,dataEntrada,dataSaida,precoEstadia"
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Divide a linha em campos separados por vírgula
                String[] campos = linha.split(",");

                // Obtém os valores dos campos
                int numero = Integer.parseInt(campos[0]);
                boolean disponivel = Boolean.parseBoolean(campos[1]);
                String cpftitular = campos[2];
                int totalCamaCasal = Integer.parseInt(campos[3]);
                int totalCamaSolteiro = Integer.parseInt(campos[4]);
                ClienteTitular titular = getTitularByCpfFromList(cpftitular, titulares);

                // Cria o objeto quarto e adiciona à lista
                if(!disponivel & titular != null){

                    LocalDate dataEntrada = LocalDate.parse(campos[5]);
                    LocalDate dataSaida = LocalDate.parse(campos[6]);
                    Quarto quarto = new Quarto(numero, titular, totalCamaCasal, totalCamaSolteiro, dataEntrada, dataSaida);
                    quartos.add(quarto);
                }else{
                    Quarto quarto = new Quarto(numero, totalCamaCasal, totalCamaSolteiro);
                    quartos.add(quarto);
                }

            }


            System.out.println("Dados carregados do arquivo CSV com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return quartos;
    }

}

