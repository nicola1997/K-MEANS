package data;

import static java.lang.Math.abs;
/**
 * La classe ContinuousItem rappresenta un elemento di dati associato a un attributo continuo.
 * Estende la classe Item e implementa la distanza tra due elementi basata sugli attributi continui.
 */
public class ContinuousItem extends Item {

  /**
   * Costruttore della classe ContinuousItem.
   *
   * @param attribute L'attributo continuo associato all'elemento di dati.
   * @param value     Il valore dell'elemento di dati per l'attributo continuo.
   */
  ContinuousItem(ContinuousAttribute attribute, double value) {
    super(attribute, value);
  }


  /**
   * Calcola la distanza tra due elementi di dati basata sugli attributi continui.
   *
   * @param a L'oggetto da confrontare con l'elemento corrente.
   * @return La distanza tra gli elementi di dati basata sugli attributi continui.
   */
  double distance(Object a) {
    // Ottiene l'attributo continuo associato all'elemento corrente
    ContinuousAttribute attribute = (ContinuousAttribute) this.getAttribute();
    return abs(attribute.getScaledValue((double) this.getValue()) - attribute.getScaledValue((double) a));
  }

}
