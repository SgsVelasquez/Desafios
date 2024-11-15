import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class Tratamento {
    public static void main(String[] args) {
    	
        String pastaMonitorada = System.getProperty("user.dir") + "\\Imagens"; //Diretorio feito no proprio projeto.
        String codigoEquipamento = "1234567890";
        long seqImagem = 1; // Inicializa o contador de sequência.

        try {
            // Cria o WatchService para monitorar o diretório, toda vez que for add uma imagem, vai ser processada automaticamente pelo programa.
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(pastaMonitorada);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Monitorando a pasta: " + pastaMonitorada);

            while (true) {
                WatchKey key = watchService.take(); // Aguarda eventos no diretório, fica esperando uma nova imagem.
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // Verifica se é um novo arquivo.
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        String nomeArquivo = event.context().toString();
                        File arquivo = new File(pastaMonitorada + File.separator + nomeArquivo);

                        // Processa o arquivo apenas se for uma imagem.
                        if (nomeArquivo.endsWith(".jpg") || nomeArquivo.endsWith(".png")) {
                            System.out.println("Nova imagem detectada: " + nomeArquivo);

                            // Lê e processa a imagem.
                            try {
                                BufferedImage imagemOriginal = ImageIO.read(arquivo);

                                // Redimensiona e adiciona tarja preta.
                                BufferedImage imagemFinal = adicionarTarjaPreta(imagemOriginal);

                                // Gera o caminho do arquivo.
                                String caminhoArquivo = gerarCaminhoArquivo(codigoEquipamento, seqImagem);

                                // Grava a imagem final.
                                ImageIO.write(imagemFinal, "jpg", new File(caminhoArquivo));

                                System.out.println("Imagem processada e salva em: " + caminhoArquivo);

                                // Incrementa a sequência.
                                seqImagem++;
                            } catch (IOException e) {
                                System.err.println("Erro ao processar a imagem: " + nomeArquivo);
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // Reseta a chave para continuar monitorando.
                boolean valid = key.reset();
                if (!valid) {
                    break; // Se o diretorio nao puder mais ser monitorado, sai do loop.
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage adicionarTarjaPreta(BufferedImage imagem) {
    	 // Redimensiona a imagem para 600x480 pixels.
        BufferedImage imagemRedimensionada = redimensionarImagem(imagem, 600, 480);

        // Define as dimensoes finais da imagem (600x540). Nao fiz com 660x480 pq achei que fosse um erro na elaboracao do desafio.
        int larguraFinal = 600;
        int alturaFinal = 480 + 60; // Adiciona 60px para a tarja preta.

     // Cria a imagem final com a tarja preta na parte inferior. se fosse 660, seria uma tarja preta na direita.
        BufferedImage imagemComTarja = new BufferedImage(larguraFinal, alturaFinal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagemComTarja.createGraphics();

        // Desenha a imagem redimensionada na parte superior.
        g2d.drawImage(imagemRedimensionada, 0, 0, null);

        // Adiciona a tarja preta de 60px na parte inferior.
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 480, larguraFinal, 60); // Tarja preta na parte inferior.

        g2d.dispose();
        return imagemComTarja;
    }

    public static BufferedImage redimensionarImagem(BufferedImage imagem, int largura, int altura) {
        BufferedImage imagemRedimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagemRedimensionada.createGraphics();
        g2d.drawImage(imagem, 0, 0, largura, altura, null);
        g2d.dispose();
        return imagemRedimensionada;
    }

    public static String gerarCaminhoArquivo(String codigoEquipamento, long seqImagem) {
        // Data atual no formato AAAAMMDDHHMMSS.
        String formatoData = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
        String dataFormatada = sdf.format(new Date());

        // Diretório baseado na data.
        String ano = dataFormatada.substring(0, 4);
        String mes = dataFormatada.substring(4, 6);
        String dia = dataFormatada.substring(6, 8);

        // Cria a estrutura de diretórios.
        String caminhoDiretorio = System.getProperty("user.dir") + "\\" + ano + File.separator + mes + File.separator + dia;
        new File(caminhoDiretorio).mkdirs(); // Cria os diretórios caso não existam.

        // Nome do arquivo com SEQ_IMAGEM formatado com 20 dígitos. sendo o '00000000000000000001' o primeiro.
        String nomeArquivo = dataFormatada + "-" + codigoEquipamento + "-" + String.format("%020d", seqImagem) + "_IMAGEM.jpg";

        return caminhoDiretorio + File.separator + nomeArquivo;
    }
}