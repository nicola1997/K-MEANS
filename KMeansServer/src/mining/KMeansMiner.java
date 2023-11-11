package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

/**
 * La classe KMeansMiner implementa l'algoritmo di clustering K-Means.
 * Consente di eseguire il clustering su un insieme di dati e di salvare/caricare i risultati del clustering.
 */
public class KMeansMiner {

    /**
     * Insieme di cluster ottenuto dall'esecuzione dell'algoritmo K-Means.
     */
    private final ClusterSet C;

    /**
     * Costruttore della classe KMeansMiner per inizializzare il KMeansMiner con il numero desiderato di cluster.
     *
     * @param k Numero di cluster desiderato.
     * @throws OutOfRangeSampleSize Eccezione lanciata se k è minore o uguale a 0.
     */
    public KMeansMiner(int k) throws OutOfRangeSampleSize {
        C = new ClusterSet(k);
    }


    /**
     * Costruttore della classe KMeansMiner per caricare un oggetto KMeansMiner da un file.
     *
     * @param fileName Nome del file da cui caricare l'oggetto KMeansMiner.
     * @throws IOException            Eccezione lanciata in caso di errori di I/O.
     * @throws ClassNotFoundException Eccezione lanciata se la classe non viene trovata durante la deserializzazione.
     */
    public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            C = (ClusterSet) in.readObject();
            in.close();
        } catch (IOException e) {
            throw new IOException("Errore di I/O");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Classe non trovata");
        }
    }

    /**
     * Salva l'oggetto KMeansMiner in un file specificato.
     *
     * @param fileName Nome del file in cui salvare l'oggetto KMeansMiner.
     * @throws IOException Eccezione lanciata in caso di errori di I/O.
     */
    public void salva(String fileName) throws IOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(this.C);
            out.close();
        } catch (IOException e) {
            throw new IOException("Errore di I/O");
        }
    }

    /**
     * Restituisce l'insieme di cluster ottenuto dall'esecuzione dell'algoritmo K-Means.
     *
     * @return Insieme di cluster.
     */
    public ClusterSet getC() {
        return C;
    }


    /**
     * Esegue l'algoritmo K-Means sui dati forniti.
     *
     * @param data Oggetto Data contenente le informazioni sui dati.
     * @return Numero di iterazioni necessarie per raggiungere la convergenza.
     * @throws OutOfRangeSampleSize Eccezione lanciata se il numero di cluster è maggiore del numero di tuple nei dati.
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        //STEP 1
        C.initializeCentroids(data);
        boolean changedCluster;
        do {
            numberOfIterations++;
            //STEP 2
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange) changedCluster = true;
                //rimuovo la tupla dal vecchio cluster
                if (currentChange && oldCluster != null)
                    //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }
            //STEP 3
            C.updateCentroids(data);
        } while (changedCluster);
        return numberOfIterations;
    }
}
