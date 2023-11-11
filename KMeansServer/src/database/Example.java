package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Example rappresenta un esempio all'interno di un insieme di dati,
 * costituito da una lista di oggetti.
 */
public class Example implements Comparable<Example>, Serializable {

  /**
   * Lista di oggetti che costituiscono l'esempio.
   */
  private List<Object> example = new ArrayList<>();

  /**
   * Aggiunge un oggetto all'esempio.
   *
   * @param o L'oggetto da aggiungere all'esempio.
   */
  public void add(Object o) {
    example.add(o);
  }

  /**
   * Restituisce l'oggetto presente all'indice specificato nell'esempio.
   *
   * @param i L'indice dell'oggetto nell'esempio.
   * @return L'oggetto presente all'indice specificato nell'esempio.
   */
  public Object get(int i) {
    return example.get(i);
  }

  /**
   * Confronta l'istanza corrente di Example con un'altra istanza specificata.
   * L'ordinamento viene effettuato confrontando gli oggetti uno per uno.
   *
   * @param ex L'istanza di Example da confrontare con l'istanza corrente.
   * @return Un valore negativo se l'istanza corrente è minore, un valore positivo se è maggiore,
   *         o zero se sono uguali.
   */
  public int compareTo(Example ex) {

    int i = 0;
    for (Object o : ex.example) {
      if (!o.equals(this.example.get(i))) return ((Comparable) o).compareTo(example.get(i));
      i++;
    }
    return 0;
  }

  /**
   * Restituisce una rappresentazione in forma di stringa dell'esempio.
   *
   * @return Una rappresentazione in forma di stringa dell'esempio.
   */
  public String toString() {
    String str = "";
    for (Object o : example)
      str += o.toString() + " ";
    return str;
  }

}