package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

/**
 * La classe ClusterSet rappresenta un insieme di cluster, ciascuno con il proprio centroide e dati clusterizzati.
 */
public class ClusterSet implements Serializable {

  /**
   * Array di cluster all'interno del ClusterSet.
   */
  private final Cluster[] C;

  /**
   * Posizione valida per la memorizzazione di un nuovo cluster in C.
   */
  private int i = 0; //posizione valida per la memorizzazione di un nuovo cluster in C

  /**
   * Costruttore della classe ClusterSet che inizializza l'array di cluster con dimensione k.
   *
   * @param k Numero di cluster desiderato.
   * @throws OutOfRangeSampleSize Eccezione lanciata se k è minore o uguale a 0.
   */
  ClusterSet(int k) throws OutOfRangeSampleSize {
    try {
      C = new Cluster[k];
    } catch (NegativeArraySizeException e) {
      throw new OutOfRangeSampleSize("k deve essere maggiore di 0");
    }
  }

  /**
   * Aggiunge un nuovo cluster all'insieme di cluster.
   *
   * @param c Cluster da aggiungere.
   */
  void add(Cluster c) {
    C[i] = c;
    i++;
  }


  /**
   * Restituisce il cluster nella posizione specificata.
   *
   * @param i Posizione del cluster nell'array.
   * @return Il cluster nella posizione specificata.
   */
  Cluster get(int i) {
    return C[i];
  }


  /**
   * Inizializza i centroidi dei cluster selezionando casualmente alcune tuple dai dati.
   *
   * @param data Oggetto Data contenente le informazioni sui dati.
   * @throws OutOfRangeSampleSize Eccezione lanciata se il numero di cluster è maggiore del numero di tuple nei dati.
   */
  void initializeCentroids(Data data) throws OutOfRangeSampleSize {
    int[] centroidIndexes = data.sampling(C.length);
    for (int centroidIndex : centroidIndexes) {
      Tuple centroidI = data.getItemSet(centroidIndex);
      add(new Cluster(centroidI));
    }
  }


  /**
   * Restituisce il cluster più vicino a una tuple specificata.
   *
   * @param tuple Tuple di cui trovare il cluster più vicino.
   * @return Il cluster più vicino alla tuple.
   */
  Cluster nearestCluster(Tuple tuple) {
    double min = tuple.getDistance(C[0].getCentroid()), tmp;
    Cluster nearest = C[0];
    for (int i = 1; i < C.length; i++) {
      tmp = tuple.getDistance(C[i].getCentroid());
      if (tmp < min) {
        min = tmp;
        nearest = C[i];
      }
    }
    return nearest;
  }

  /**
   * Restituisce il cluster corrente di una transazione specificata.
   *
   * @param id Identificatore della transazione.
   * @return Il cluster corrente della transazione o null se non è clusterizzata in nessun cluster.
   */
  Cluster currentCluster(int id) {
    for (Cluster cluster : C) {
      if (cluster.contain(id))
        return cluster;
    }
    return null;
  }

  /**
   * Aggiorna i centroidi di tutti i cluster.
   *
   * @param data Oggetto Data contenente le informazioni sui dati.
   */
  void updateCentroids(Data data) {
    for (Cluster cluster : C) {
      cluster.computeCentroid(data);
    }
  }

  /**
   * Restituisce una rappresentazione in forma di stringa dell'insieme di cluster.
   *
   * @return Una stringa che rappresenta l'insieme di cluster.
   */
  public String toString() {
    String str = "";
    for (int i = 0; i < C.length; i++) {
      str += i + ": " + C[i] + "\n";
    }
    return str;
  }

  /**
   * Restituisce una rappresentazione dettagliata in forma di stringa dell'insieme di cluster,
   * mostrando i centroidi, gli esempi clusterizzati e le distanze medie.
   *
   * @param data Oggetto Data contenente le informazioni sui dati.
   * @return Una stringa dettagliata che rappresenta l'insieme di cluster.
   */
  public String toString(Data data) {
    String str = "";
    for (int i = 0; i < C.length; i++) {
      if (C[i] != null) {
        str += i + ": " + C[i].toString(data) + "\n";
      }
    }
    return str;
  }

}
