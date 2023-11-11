/**
 * Eccezione personalizzata per gestire errori lato server.
 */
class ServerException extends Exception{
    /**
     * Costruttore che accetta un messaggio di errore.
     *
     * @param msg Messaggio di errore associato all'eccezione.
     */
    ServerException(String msg)
    {
        super(msg);
    }
}