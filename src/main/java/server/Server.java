package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.*;

public class Server {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                Server server = new Server();
                out.print("[ Iniciando Servidor TCP  .........................  ");
                ServerSocket socket = new ServerSocket(3300, 5, InetAddress.getByName("127.0.0.1"));
                out.println("[OK] ]");
                out.print("[ Aquardando pedidos de conexão    ..................  ");

                Socket sock = socket.accept(); // Operação bloqueante (aguardando pedido de conexão)
                out.println("[Conexão Aceita] na porta " + socket.getLocalPort() + "]");

                while (true) {
                    InputStream is = sock.getInputStream(); //Canal de entrada de dados
                    OutputStream os = sock.getOutputStream(); //Canal de saída de dados
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    out.print("[ Aguardando recebimento de mensagem   ..............  ");
                    String opcaoEscolhida = reader.readLine();

                    PrintWriter writer = new PrintWriter(os, true);

                    try {
                        if (Integer.valueOf(opcaoEscolhida) == 0) {
                            socket.close();
                            out.println("[Conexão encerrada]");
                            break;
                        } else if (Integer.valueOf(opcaoEscolhida) == 2) {
                            out.println("[Resposta enviada] Respondendo com a data e hora.");
                            writer.println(server.getDataHora());
                        } else if (Integer.valueOf(opcaoEscolhida) == 3) {
                            out.println("[Resposta enviada] Respondendo com a memória atual.");
                            writer.println(server.getMemory());
                        } else {
                            out.println("[Resposta enviada] Opção inválida. Escolha 2 ou 3.");
                            writer.println("Opção inválida. Escolha 2 ou 3.");
                        }
                    } catch (NumberFormatException e) {
                        socket.close();
                        out.println("[Conexão encerrada]");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        }
        out.println("[ FIM ]");
    }

    public String getDataHora() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        String dataHoraFormatado = "Oi, sou o servidor! Hoje é dia " + data + ", às " + hora + " horas. \n";
        return dataHoraFormatado;
    }

    public String getMemory() {
        Runtime rt = Runtime.getRuntime();
        // freeMemory = memória livre alocada atual, totalMemory = memória total alocada
        long usedMemory = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024; // convertendo em MB
        String memoriaAtual = "Oi, sou o servidor! Memória em uso: " + usedMemory + " MB \n";
        return memoriaAtual;
    }
}
