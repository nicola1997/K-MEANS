package database;

/**
 * La classe DatabaseConnectionException è un'eccezione personalizzata che può essere lanciata
 * quando si verificano problemi durante la connessione al database.
 */
public class DatabaseConnectionException extends Exception {

  /**
   * Costruttore predefinito della classe DatabaseConnectionException.
   * Crea un'istanza di eccezione senza messaggio specifico.
   */
  public DatabaseConnectionException() {
    super();
  }

  /**
   * Costruttore della classe DatabaseConnectionException con un messaggio specifico.
   *
   * @param msg Il messaggio di errore associato all'eccezione.
   */
  public DatabaseConnectionException(String msg) {
    super(msg);
  }

}
