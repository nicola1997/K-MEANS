package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Cluster rappresenta un cluster di dati all'interno di un insieme di dati.
 * Ogni cluster è definito da un centroide (Tuple) e da un insieme di dati clusterizzati.
 */
public class Cluster implements Serializable {

  /**
   * Il centroide del cluster, rappresentato come una Tuple.
   */
  private final Tuple centroid;

  /**
   * Insieme di identificatori delle transazioni clusterizzate in questo cluster.
   */
  private final Set<Integer> clusteredData;

    /*mining.Cluster(){

    }*/

  /**
   * Costruttore della classe Cluster che inizializza un cluster con il centroide specificato.
   *
   * @param centroid Il centroide del cluster.
   */
  Cluster(Tuple centroid) {
    this.centroid = centroid;
    clusteredData = new HashSet<>();

  }
  /**
   * Restituisce il centroide del cluster.
   *
   * @return Il centroide del cluster.
   */
  Tuple getCentroid() {
    return centroid;
  }

  /**
   * Calcola il nuovo centroide del cluster basandosi sui dati attualmente clusterizzati.
   *
   * @param data Oggetto Data contenente le informazioni sui dati.
   */
  void computeCentroid(Data data) {
    for (int i = 0; i < centroid.getLength(); i++) {
      centroid.get(i).update(data, clusteredData);

    }

  }

  /**
   * Aggiunge una transazione al cluster e restituisce true se la transazione cambia cluster.
   *
   * @param id Identificatore della transazione da aggiungere.
   * @return true se la transazione cambia cluster, false altrimenti.
   */
  boolean addData(int id) {
    return clusteredData.add(id);

  }

  /**
   * Verifica se una transazione è clusterizzata all'interno di questo cluster.
   *
   * @param id Identificatore della transazione da verificare.
   * @return true se la transazione è clusterizzata in questo cluster, false altrimenti.
   */
  boolean contain(int id) {
    return clusteredData.contains(id);
  }


  /**
   * Rimuove la transazione che ha cambiato cluster.
   *
   * @param id Identificatore della transazione da rimuovere.
   */
  void removeTuple(int id) {
    clusteredData.remove(id);

  }

  public String toString() {
    String str = "Centroid=(";
    for (int i = 0; i < centroid.getLength(); i++)
      str += centroid.get(i) + " ";
    str += ")";
    return str;

  }

  /**
   * Genera una rappresentazione in formato stringa delle informazioni di clustering per un determinato set di dati.
   *
   * @param data L'oggetto Data che rappresenta il dataset.
   * @return Una stringa contenente le coordinate del centroide, gli esempi con i loro attributi e distanze,
   *         e la distanza media dei punti dati clusterizzati dal centroide.
   */
  public String toString(Data data) {
    String str = "Centroid=(";
    for (int i = 0; i < centroid.getLength(); i++) {
      str += centroid.get(i) + (i == centroid.getLength() - 1 ? "" : " ");
    }
    str += ")\nExamples:\n";
    for (int i : clusteredData) {
      str += "[";
      for (int j = 0; j < data.getNumberOfAttributes(); j++)
        str += data.getAttributeValue(i, j) + (j == data.getNumberOfAttributes() - 1 ? "" : " ");
      str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";

    }
    str += "AvgDistance=" + getCentroid().avgDistance(data, clusteredData) + "\n";
    return str;

  }

}
