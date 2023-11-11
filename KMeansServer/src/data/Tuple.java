package data;

import java.io.Serializable;
import java.util.Set;

/**
 * La classe Tuple rappresenta una tupla di elementi di dati associati a un insieme di attributi,
 * fornendo metodi per gestire e calcolare distanze tra tuple e la distanza media all'interno di un gruppo di transazioni.
 */
public class Tuple implements Serializable {
  /**
   * Array di elementi di dati rappresentanti la tupla.
   */
  private final Item[] tuple;

  /**
   * Costruttore della classe Tuple che inizializza un array di dimensione specificata.
   *
   * @param size La dimensione dell'array di elementi di dati nella tupla.
   */
  Tuple(int size) {
    tuple = new Item[size];
  }

  /**
   * Restituisce la lunghezza della tupla, ovvero il numero di elementi di dati.
   *
   * @return La lunghezza della tupla.
   */
  public int getLength() {
    return tuple.length;
  }

  /**
   * Restituisce l'elemento di dati alla posizione specificata nella tupla.
   *
   * @param i L'indice dell'elemento di dati nella tupla.
   * @return L'elemento di dati alla posizione specificata nella tupla.
   */
  public Item get(int i) {
    return tuple[i];
  }

  /**
   * Aggiunge un elemento di dati alla posizione specificata nella tupla.
   *
   * @param c L'elemento di dati da aggiungere alla tupla.
   * @param i L'indice della posizione in cui aggiungere l'elemento di dati nella tupla.
   */
  void add(Item c, int i) {
    tuple[i] = c;
  }

  /**
   * Calcola la distanza media tra la tupla corrente e un gruppo di transazioni specificato.
   *
   * @param t          L'insieme di dati contenente le transazioni.
   * @return La distanza media tra la tupla corrente e il gruppo di transazioni.
   */
  public double getDistance(Tuple t) {
    double distance = 0;
    for (int i = 0; i < tuple.length; i++) {
      distance += tuple[i].distance(t.get(i).getValue());
    }
    return distance;
  }

  /**
   * Calcola la distanza media tra la tupla corrente e un gruppo di transazioni specificato.
   *
   * @param data          L'insieme di dati contenente le transazioni.
   * @param clusteredData L'insieme di indici delle transazioni raggruppate.
   * @return La distanza media tra la tupla corrente e il gruppo di transazioni.
   */
  public double avgDistance(Data data, Set<Integer> clusteredData) {
    double p, sumD = 0.0;
    for (int i : clusteredData) {
      double d = getDistance(data.getItemSet(i));
      sumD += d;
    }
    p = sumD / clusteredData.size();
    return p;
  }
}
