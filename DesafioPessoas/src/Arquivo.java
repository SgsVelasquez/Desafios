import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
	
	public static final String caminhoArquivo = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "pessoas.txt"; //Aquivo com pessoas no Documents do usuario.
	
	public List<Pessoa> ler(){
		List<Pessoa> pessoas = new ArrayList<>();
		File arquivo = new File(caminhoArquivo);
	
		//Verificando se o arquivo 'pessoas.txt' existe.
		if (!arquivo.exists()) {
           try {
               arquivo.createNewFile(); //Caso nao exista, sera criado.
               System.out.println("Arquivo 'pessoas.txt' criado em: " + caminhoArquivo);
           } catch (IOException e) {
               System.out.println("Erro ao criar o arquivo: " + e.getMessage());
           }
		}
	 
		//Ler as pessoas do arquivo.
	 	try (BufferedReader ler = new BufferedReader(new FileReader(arquivo))) {
		 String linha;

	        while ((linha = ler.readLine()) != null) {
	            // Ignorar linhas vazias
	            if (linha.trim().isEmpty()) {
	                continue;
	            }

	            // Dividir a linha pelos delimitadores ";"
	            String[] partes = linha.split(", ");
	            
	            // Verificar se a linha possui 3 partes (nome, idade, CPF)
	            if (partes.length == 3) {
	                try {
	                    String nome = partes[0];
	                    int idade = Integer.parseInt(partes[1]);
	                    String cpf = partes[2];
	                    pessoas.add(new Pessoa(nome, idade, cpf));
	                } catch (NumberFormatException e) {
	                    System.out.println("Erro ao converter idade para número: " + partes[1]);
	                }
	            } else {
	                System.out.println(linha);
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
	    }

	    return pessoas;
	}
	
	// Escreve uma nova pessoa no arquivo.
    public void adicionarPessoa(Pessoa pessoa) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            escritor.write(pessoa.toString());
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}

