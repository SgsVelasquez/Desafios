import java.util.*;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static Arquivo arquivo = new Arquivo();
	static List<Pessoa> pessoas = arquivo.ler();
	
	public static void main (String[] args) {
		
		ExibirLista();
		
		//Pergunta se quer add uma pessoa.
		while(true) {
			System.out.println("Deseja adicionar uma nova pessoa? (s/n): ");
			String resposta = scanner.nextLine();
			
			if(resposta.equals("s")) {
				//add uma pessoa.
				System.out.println("Digite o nome: ");
				String nome = scanner.nextLine();
				
				System.out.println("Digite a idade: ");
				int idade = scanner.nextInt();
				scanner.nextLine(); // Limpa o buffer
				
				System.out.println("Digite o CPF: ");
				String cpf = scanner.nextLine();
				
				Pessoa novaPessoa = new Pessoa(nome, idade, cpf);
				arquivo.adicionarPessoa(novaPessoa);
				
				System.out.println("Adicionada com sucesso!");
			} else if(resposta.equals("n")) {
				//Saindo do loop.
				System.out.println("Saindo");
				break;
			} else {
				//Digitou qualquer coisa menos s ou n.
				System.out.println("Opcao invalida. Digite 's' para SIM ou 'n' para NAO.");
			}	
		}
		
		ExibirLista();
		System.out.println("Programa encerrado.");
	    scanner.close();  // Fecha o Scanner
	     
	}
	private static void ExibirLista() {
		List<Pessoa> pessoas = arquivo.ler();
		if(pessoas.isEmpty()) {
			
			System.out.println("Nao tem nada no arquivo");
		} else {
			System.out.println("Conteudo do arquivo: ");
			for (Pessoa pessoa : pessoas) {
				System.out.println(pessoa);
			}
		}
	}
}
