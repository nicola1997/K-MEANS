package data;

import java.util.Iterator;
import java.util.Set;

/**
 * La classe DiscreteAttribute rappresenta un attributo discreto che estende la classe Attribute e implementa l'interfaccia Iterable.
 * Gli attributi discreti hanno un insieme di valori del dominio memorizzati seguendo un ordine lessicografico.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
  /**
   * Insieme di oggetti String rappresentanti i valori del dominio discreto.
   * I valori sono memorizzati seguendo un ordine lessicografico.
   */
  private final Set<String> values;// Array di oggetti String, uno per ciascun valore del dominio discreto. I valori del dominio sono memorizzati in values seguendo un ordine lessicografico.

  /**
   * Costruttore della classe DiscreteAttribute.
   *
   * @param name   Il nome simbolico dell'attributo discreto.
   * @param index  L'identificativo numerico dell'attributo discreto.
   * @param values Insieme di valori del dominio discreto, memorizzati seguendo un ordine lessicografico.
   */
  DiscreteAttribute(String name, int index, Set<String> values) {
    super(name, index);
    this.values = values;
  }

  /**
   * Restituisce un iteratore per attraversare gli elementi dell'insieme di valori del dominio.
   *
   * @return Un iteratore per gli elementi dell'insieme di valori del dominio.
   */
  public Iterator<String> iterator() {
    return this.values.iterator();
  }

  /**
   * Restituisce il numero di valori distinti nel dominio dell'attributo discreto.
   *
   * @return Il numero di valori distinti nel dominio dell'attributo discreto.
   */
  int getNumberOfDistinctValues() {
    return values.size();
  }

  /**
   * Calcola la frequenza di un valore specifico nel set di dati per l'attributo discreto.
   *
   * @param data   L'insieme di dati contenente le transazioni.
   * @param idList L'insieme di indici delle transazioni su cui calcolare la frequenza.
   * @param v      Il valore per il quale calcolare la frequenza.
   * @return La frequenza del valore specifico nell'attributo discreto.
   */
  int frequency(Data data, Set<Integer> idList, String v) {
    int count = 0;
    for (int i : idList)
      if (data.getAttributeValue(i, this.getIndex()).equals(v))
        count++;
    return count;
  }

}
