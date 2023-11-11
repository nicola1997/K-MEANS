package data;

/**
 * La classe OutOfRangeSampleSize è un'eccezione personalizzata che viene lanciata
 * quando la dimensione del campione specificata è fuori dall'intervallo consentito.
 */
public class OutOfRangeSampleSize extends Exception {
  public OutOfRangeSampleSize() {
    super();
  }

  /**
   * Costruttore della classe OutOfRangeSampleSize con un messaggio specifico.
   *
   * @param msg Il messaggio di errore associato all'eccezione.
   */  public OutOfRangeSampleSize(String msg) {
    super(msg);
  }
}
