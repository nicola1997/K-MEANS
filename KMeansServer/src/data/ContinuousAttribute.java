package data;

/**
 * La classe ContinuousAttribute rappresenta un attributo continuo che estende la classe Attribute.
 * Questa classe include informazioni sugli estremi dell'intervallo di valori (dominio) che
 * l'attributo può assumere. Fornisce inoltre un metodo per ottenere il valore scalato
 * all'interno dell'intervallo specificato.
 */
public class ContinuousAttribute extends Attribute {
  /**
   * Rappresenta il valore massimo dell'attributo continuo.
   */
  private final double max;
  /**
   * Rappresenta il valore minimo dell'attributo continuo.
   */
  private final double min;// rappresentano gli estremi dell'intervallo di valori (dominio) che l'attributo può realmente assumere.

  /**
   * Costruttore della classe ContinuousAttribute.
   *
   * @param name  Il nome simbolico dell'attributo continuo.
   * @param index L'identificativo numerico dell'attributo continuo.
   * @param min   Il valore minimo dell'intervallo di valori che l'attributo può assumere.
   * @param max   Il valore massimo dell'intervallo di valori che l'attributo può assumere.
   */
  ContinuousAttribute(String name, int index, double min, double max) {
    super(name, index);
    this.max = max;
    this.min = min;
  }
  /**
   * Restituisce il valore scalato dell'attributo continuo all'interno dell'intervallo specificato.
   *
   * @param v Il valore dell'attributo continuo da scalare.
   * @return Il valore scalato dell'attributo continuo.
   */
  double getScaledValue(double v) {
    return (v - min) / (max - min);
  }
}
