package run;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import keyboardInput.Keyboard;


public class MainTest {

    /**
     * @param args
     */
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in; // stream con richieste del client
    private String db;
    private String table;
    private int k;


    public MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        socket = new Socket(addr, port); //Port
        System.out.println(socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // stream con richieste del client
    }

    private int menu() {
        int answer;
        System.out.println("Scegli una opzione");
        do {
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta: ");
            answer = Keyboard.readInt();
        } while (answer <= 0 || answer > 2);
        return answer;
    }

    private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        System.out.print("Inserisci nome database: ");
        db=Keyboard.readString();
        System.out.print("Inserisci nome tabella: ");
        table=Keyboard.readString();
        System.out.print("Inserisci numero di cluster: ");
        k=Keyboard.readInt();
        out.writeObject(db+table+k);
        String result = (String) in.readObject();
        if (result.equals("OK")) return (String) in.readObject();
        else throw new ServerException(result);
    }

    private String storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
        String result;
        out.writeObject(0);
        if (option("Vuoi usare i valori di default? (y/n) ")) {
            out.writeObject("localhost");
            db="MapDB";
            out.writeObject(db);
            table="playtennis";
            out.writeObject(table);
            out.writeObject("MapUser");
            out.writeObject("map");
        } else {
            System.out.print("Inserisci il nome del server (ad esempio localhost): ");
            out.writeObject(Keyboard.readString());
            System.out.print("Inserisci il nome del database (ad esempio MapDB): ");
            db=Keyboard.readString();
            out.writeObject(db);
            System.out.print("Inserisci il nome della tabella (ad esempio playtennis): ");
            table=Keyboard.readString();
            out.writeObject(table);
            System.out.print("Inserisci il nome dell'utente (ad esempio MapUser): ");
            out.writeObject(Keyboard.readString());
            System.out.print("Inserisci la password dell'utente (ad esempio map): ");
            out.writeObject(Keyboard.readString());
        }
        result = (String) in.readObject();
        if (result.equals("OK")) return (String) in.readObject();
        else throw new ServerException(result);
    }

    private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.print("Numero di cluster: ");
        k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            System.out.println("Numero di Iterazioni: " + in.readObject());
            return (String) in.readObject();
        } else throw new ServerException(result);
    }

    private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        out.writeObject(db+table+k);
        String result = (String) in.readObject();
        if (!result.equals("OK")) throw new ServerException(result);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Inserire ip e porta come argomenti");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        MainTest main;
        try {
            main = new MainTest(ip, port);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
        do {
            int menuAnswer = main.menu();
            switch (menuAnswer) {
                case 1 -> {
                    do {
                        try {
                            String kmeans = main.learningFromFile();
                            System.out.println(kmeans);
                        } catch (ServerException e) {
                            System.out.println(e);
                        } catch (SocketException | FileNotFoundException | ClassNotFoundException e) {
                            System.out.println(e);
                            return;
                        } catch (IOException e) {
                            System.out.println(e);
                            return;
                        }
                    } while (option("Vuoi ripetere l'esecuzione? (y/n) "));
                }
                case 2 -> { // learning from db
                    while (true) {
                        try {
                            String data = main.storeTableFromDb();
                            System.out.println(data);
                            break; //esce fuori dal while
                        } catch (ServerException e) {
                            System.out.println(e);
                            System.out.println("\nReinserisci i dati\n");
                        } catch (SocketException | FileNotFoundException | ClassNotFoundException e) {
                            System.out.println(e);
                            return;
                        } catch (IOException e) {
                            System.out.println(e);
                            return;
                        }
                    } //end while [viene fuori dal while con un db e la stringa data associata (in alternativa il programma termina)
                    do {
                        try {
                            String clusterSet = main.learningFromDbTable();
                            System.out.println(clusterSet);
                            main.storeClusterInFile();
                        } catch (ServerException e) {
                            System.out.println(e);
                        } catch (SocketException | FileNotFoundException | ClassNotFoundException e) {
                            System.out.println(e);
                            return;
                        } catch (IOException e) {
                            System.out.println(e);
                            return;
                        }
                    } while (option("Vuoi ripetere l'esecuzione? (y/n) "));
                } //fine case 2
            }
        } while (option("Vuoi scegliere una nuova operazione da menu? (y/n) "));
        try {
            out.writeObject(4);
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static boolean option(String str) {
        char c;
        do {
            System.out.print(str);
            c = Character.toLowerCase(Keyboard.readChar());
        } while (c != 'y' && c != 'n');
        return c == 'y';
    }
}



