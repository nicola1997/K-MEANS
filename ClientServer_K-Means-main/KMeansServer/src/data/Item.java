package data;

import java.io.Serializable;
import java.util.Set;

/**
 * La classe astratta Item rappresenta un elemento di dati associato a un attributo,
 * fornendo un'implementazione di base condivisa per elementi di dati discreti e continui.
 * Ogni elemento è caratterizzato da un attributo e un valore assegnato a quell'attributo.
 */
public abstract class Item implements Serializable {
  /**
   * Attributo coinvolto nell'item.
   */
  private final Attribute attribute;

  /**
   * Valore assegnato all'attributo nell'item.
   */
  private Object value;

  /**
   * Costruttore della classe astratta Item.
   *
   * @param attribute L'attributo associato all'elemento di dati.
   * @param value     Il valore assegnato all'attributo dell'elemento di dati.
   */
  Item(Attribute attribute, Object value) {
    this.attribute = attribute;
    this.value = value;
  }
  /**
   * Attributo coinvolto nell'item.
   */
  public Attribute getAttribute() {
    return attribute;
  }
  /**
   * Valore assegnato all'attributo nell'item.
   */
  public Object getValue() {
    return value;
  }

  /**
   * Restituisce una rappresentazione in forma di stringa dell'elemento di dati,
   * utilizzando il valore assegnato all'attributo.
   *
   * @return Una rappresentazione in forma di stringa dell'elemento di dati.
   */
  public String toString() {
    return value.toString();
  }

  /**
   * Calcola la distanza tra l'elemento di dati corrente e un altro elemento specificato.
   * L'implementazione di questo metodo varia per elementi discreti e continui.
   *
   * @param a L'oggetto da confrontare con l'elemento corrente.
   * @return La distanza tra l'elemento di dati corrente e l'elemento specificato.
   */
  abstract double distance(Object a); // L’implementazione sarà diversa per item discreto e item continuo


  /**
   * Aggiorna il valore dell'elemento di dati basato su un insieme di transazioni raggruppate.
   * L'aggiornamento avviene calcolando il prototipo per l'attributo dell'elemento di dati.
   *
   * @param data          L'insieme di dati contenente le transazioni.
   * @param clusteredData L'insieme di indici delle transazioni raggruppate.
   */
  public void update(Data data, Set<Integer> clusteredData) {
    value = data.computePrototype(clusteredData, attribute);
  }
}
