import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe MultiServer rappresenta un server che accetta connessioni da client.
 * Ogni connessione viene gestita da un thread separato utilizzando la classe ServerOneClient.
 */
public class MultiServer {

    /**
     * La porta su cui il server è in ascolto per le connessioni dei client.
     */

    int PORT = 8080;

    /**
     * Costruttore senza parametri che avvia il server sulla porta predefinita 8080.
     */
    public MultiServer() {
        run();
    }

    /**
     * Costruttore con parametro per specificare la porta su cui avviare il server.
     *
     * @param port La porta su cui avviare il server.
     */

    public MultiServer(int port) {
        PORT = port;
        run();
    }

    /**
     * Metodo che gestisce l'avvio del server, accettando connessioni dai client.
     */
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerOneClient(socket);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /**
     * Metodo principale che crea un'istanza di MultiServer avviando il server.
     *
     * @param args Argomenti della riga di comando (non utilizzati in questo caso).
     */
    public static void main(String[] args) {
        new MultiServer();
    }
}
