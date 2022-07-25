package client;

import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socketClient;

        System.out.println("Olá! Toque 1 para iniciar a conexão com o servidor.");
        int opcaoEscolhida = scanner.nextInt();
        if (opcaoEscolhida == 1) {
            socketClient = new Socket("127.0.0.1", 3300);
            System.out.print("[Conexão realizada com êxito] \n \n");

            while (true) {
                OutputStream os = socketClient.getOutputStream(); //Canal de saída de dados
                PrintWriter writer = new PrintWriter(os, true);

                System.out.println("O que você deseja fazer? \n" +
                        "- Consultar dia/hora (Tecle 2) \n" +
                        "- Consultar memória em uso (Tecle 3) \n" +
                        "- Encerrar conexão (Tecle 0) \n");
                opcaoEscolhida = scanner.nextInt();

                if (Integer.valueOf(opcaoEscolhida) == 0) {
                    socketClient.close();
                    out.println("[Conexão encerrada]");
                    break;
                }

                writer.println(opcaoEscolhida);

                InputStream input = socketClient.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String time = reader.readLine();
                System.out.println(time);
            }
        }
    }
}
