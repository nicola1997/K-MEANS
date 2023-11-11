package database;

/**
 * La classe NoValueException è un'eccezione personalizzata che può essere lanciata
 * quando ci si aspetta un valore ma non ne viene fornito uno.
 */
public class NoValueException extends Exception {

  /**
   * Costruttore predefinito della classe NoValueException.
   * Crea un'istanza di eccezione senza messaggio specifico.
   */
  public NoValueException() {
    super();
  }

  /**
   * Costruttore della classe NoValueException con un messaggio specifico.
   *
   * @param msg Il messaggio di errore associato all'eccezione.
   */
  public NoValueException(String msg) {
    super(msg);
  }

}
