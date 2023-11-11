package data;

/**
 * La classe DiscreteItem rappresenta un elemento di dati associato a un attributo discreto,
 * estendendo la classe astratta Item.
 * Gli elementi discreti contengono un valore che deve appartenere al dominio discreto dell'attributo associato.
 */
public class DiscreteItem extends Item {

  /**
   * Costruttore della classe DiscreteItem.
   *
   * @param attribute L'attributo discreto associato all'elemento di dati.
   * @param value     Il valore dell'elemento di dati per l'attributo discreto.
   */
  DiscreteItem(DiscreteAttribute attribute, String value) {
    super(attribute, value);
  }

  /**
   * Calcola la distanza tra due elementi di dati basata sugli attributi discreti.
   * La distanza è 0 se i valori sono uguali, altrimenti è 1.
   *
   * @param a L'oggetto da confrontare con l'elemento corrente.
   * @return La distanza tra gli elementi di dati basata sugli attributi discreti.
   */
  double distance(Object a) {
    return this.getValue().equals(a) ? 0 : 1;
  }

}
