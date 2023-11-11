package server;
import data.Data;
import mining.KMeansMiner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * La classe ServerOneClient rappresenta un thread del server che gestisce una connessione con un singolo client.
 * È responsabile di ricevere richieste dal client, eseguire operazioni di clustering K-Means sui dati e inviare risposte al client.
 */
public class ServerOneClient extends Thread {

    /**
     * Socket per la comunicazione con il client.
     */
    Socket socket;

    /**
     * Stream di input dal client.
     */
    ObjectInputStream in;

    /**
     * Stream di output verso il client.
     */
    ObjectOutputStream out;

    /**
     * Oggetto KMeansMiner per eseguire l'algoritmo di clustering K-Means.
     */
    KMeansMiner kmeans;


    /**
     * Oggetto Data che contiene le informazioni sui dati.
     */
    Data data;

    /**
     * Costruttore della classe ServerOneClient.
     *
     * @param s Socket per la comunicazione con il client.
     * @throws IOException Eccezione lanciata in caso di errori di I/O.
     */
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    /**
     * Gestisce la ricezione e la memorizzazione dei dati della tabella dal database fornito dal client.
     */
    private void storeTableFromDb() {
        String result = "OK";
        try {
            String server = (String) in.readObject();
            String db = (String) in.readObject();
            String table = (String) in.readObject();
            String user = (String) in.readObject();
            String pass = (String) in.readObject();
            System.out.println(db+" "+table);
            data = new Data(server, db, table, user, pass);
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        try {
            out.writeObject(result);
            if (result.equals("OK")) out.writeObject(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestisce l'esecuzione dell'algoritmo di clustering K-Means sui dati della tabella dal database.
     */
    private void learningFromDbTable() {
        String result = "OK";
        int k, numIter = 0;
        try {
            k = (int) in.readObject();
            kmeans = new KMeansMiner(k);
            numIter = kmeans.kmeans(data);
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        try {
            out.writeObject(result);
            if (result.equals("OK")) {
                out.writeObject(numIter);
                out.writeObject(kmeans.getC().toString(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gestisce il salvataggio dei cluster ottenuti dall'algoritmo K-Means in un file.
     */
    private void storeClusterInFile() {
        String result = "OK";
        try {
            String fileName = (String) in.readObject();
            kmeans.salva("ClientServer_K-Means-main/KMeansServer/Salvataggi\\" + fileName + ".dat");//ClientServer_K-Means-main/KMeansServer/Salvataggi/MapDBplaytennis3.dat
        } catch (IOException | ClassNotFoundException e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        try {
            out.writeObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestisce il caricamento dei cluster da un file.
     */
    private void learningFromFile() {
        String result = "OK";
        try {
            String fileName = (String) in.readObject();
            kmeans = new KMeansMiner("ClientServer_K-Means-main/KMeansServer/Salvataggi\\" + fileName + ".dat");
        } catch (IOException | ClassNotFoundException e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        try {
            out.writeObject(result);
            if (result.equals("OK")) {
                out.writeObject(kmeans.getC().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Override del metodo run della classe Thread.
     * Gestisce le richieste del client e esegue le operazioni corrispondenti.
     */
    public void run() {
        try {
            while (true) {
                int request = (Integer) in.readObject();
                switch (request) {
                    case 0 -> storeTableFromDb();
                    case 1 -> learningFromDbTable();
                    case 2 -> storeClusterInFile();
                    case 3 -> learningFromFile();
                    default -> {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
