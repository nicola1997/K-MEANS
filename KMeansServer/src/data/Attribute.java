package data;

import java.io.Serializable;

/**
 * La classe astratta Attribute rappresenta un attributo generico con un nome simbolico
 * e un identificativo numerico. Questa classe fornisce un'implementazione di base per
 * gli attributi e viene progettata per essere estesa da classi specifiche di attributi.
 * Implementa l'interfaccia Serializable per supportare la serializzazione degli oggetti.
 */
public abstract class Attribute implements Serializable {
  /** nome simbolico dell'attributo*/
  private final String name;
  /** indice dell'attributo*/
  private final int index;

    /**
   * Costruttore della classe Attribute.
   *
   * @param name  Il nome simbolico dell'attributo.
   * @param index L'identificativo numerico dell'attributo.
   */
  Attribute(String name, int index) {
    this.name = name;
    this.index = index;
  }

  /**
   * Restituisce il nome simbolico dell'attributo.
   *
   * @return Il nome simbolico dell'attributo.
   */
  public String getName() {
    return name;
  }

  /**
   * Restituisce l'identificativo numerico dell'attributo.
   *
   * @return L'identificativo numerico dell'attributo.
   */
  public int getIndex() {
    return index;
  }

  /**
   * Restituisce una rappresentazione in forma di stringa dell'attributo, che è il suo nome simbolico.
   *
   * @return Il nome simbolico dell'attributo in forma di stringa.
   */
  public String toString() {
    return name;
  }

}
