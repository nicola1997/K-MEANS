package database;

/**
 * La classe EmptySetException è un'eccezione personalizzata che può essere lanciata
 * quando ci si aspetta un insieme non vuoto ma viene fornito un insieme vuoto.
 */
public class EmptySetException extends Exception {

  /**
   * Costruttore predefinito della classe EmptySetException.
   * Crea un'istanza di eccezione senza messaggio specifico.
   */
  public EmptySetException() {
    super();
  }

  /**
   * Costruttore della classe EmptySetException con un messaggio specifico.
   *
   * @param msg Il messaggio di errore associato all'eccezione.
   */
  public EmptySetException(String msg) {
    super(msg);
  }

}
