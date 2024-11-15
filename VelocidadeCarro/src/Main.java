import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Distancia entre os sensores em metros.
        double distanciaEntreSensores = 4.0;

        // Conversao de metros por segundo para quilometros por hora.
        double conversaoParaKmH = 3.6;

        // Solicitar o tempo decorrido entre os acionamentos dos sensores.
        System.out.print("Digite o tempo decorrido entre os sensores (em segundos): ");
        double tempoDecorrido = scanner.nextDouble();

        // Calcular a velocidade em metros por segundo.
        double velocidadeEmMS = distanciaEntreSensores / tempoDecorrido;

        // Converter a velocidade para quilometros por hora.
        double velocidadeEmKMH = velocidadeEmMS * conversaoParaKmH;

        //Resultado.
        System.out.println("A velocidade do veiculo e: " + velocidadeEmKMH + " Km/h");

        scanner.close();
    }
}