package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe TableSchema rappresenta lo schema di una tabella del database,
 * fornendo informazioni sulle colonne e i loro tipi di dati.
 */
public class TableSchema {

  /**
   * Oggetto DbAccess utilizzato per la connessione al database.
   */

  private DbAccess db;

  /**
   * Classe interna Column rappresenta una colonna nella tabella, con il nome e il tipo di dato.
   */

  public class Column {
    private String name;
    private String type;

    /**
     * Costruttore della classe Column.
     *
     * @param name Nome della colonna.
     * @param type Tipo di dato della colonna.
     */
    Column(String name, String type) {
      this.name = name;
      this.type = type;
    }

    /**
     * Restituisce il nome della colonna.
     *
     * @return Il nome della colonna.
     */
    public String getColumnName() {
      return name;
    }

    /**
     * Verifica se il tipo di dato della colonna è numerico.
     *
     * @return true se il tipo di dato è numerico, false altrimenti.
     */
    public boolean isNumber() {
      return type.equals("number");
    }

    /**
     * Restituisce una rappresentazione in forma di stringa della colonna.
     *
     * @return Una stringa che rappresenta la colonna nel formato "nome:tipo".
     */
    public String toString() {
      return name + ":" + type;
    }
  }

  /**
   * Lista delle colonne che costituiscono lo schema della tabella.
   */
  List<Column> tableSchema = new ArrayList<>();

  /**
   * Costruttore della classe TableSchema.
   *
   * @param db        Oggetto DbAccess utilizzato per la connessione al database.
   * @param tableName Nome della tabella di cui ottenere lo schema.
   * @throws SQLException Eccezione lanciata in caso di errori SQL durante l'esecuzione della query.
   */
  public TableSchema(DbAccess db, String tableName) throws SQLException {
    this.db = db;
    HashMap<String, String> mapSQL_JAVATypes = new HashMap<>();
    //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
    mapSQL_JAVATypes.put("CHAR", "string");
    mapSQL_JAVATypes.put("VARCHAR", "string");
    mapSQL_JAVATypes.put("LONGVARCHAR", "string");
    mapSQL_JAVATypes.put("BIT", "string");
    mapSQL_JAVATypes.put("SHORT", "number");
    mapSQL_JAVATypes.put("INT", "number");
    mapSQL_JAVATypes.put("LONG", "number");
    mapSQL_JAVATypes.put("FLOAT", "number");
    mapSQL_JAVATypes.put("DOUBLE", "number");


    Connection conn = db.getConnection();
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + " WHERE 1=0;");
    ResultSetMetaData md = rs.getMetaData();

    for (int i=1; i<=md.getColumnCount(); i++) {
      if (mapSQL_JAVATypes.containsKey(md.getColumnTypeName(i)))
        tableSchema.add(new Column(md.getColumnName(i), mapSQL_JAVATypes.get(md.getColumnTypeName(i))));
    }
    st.close();
    rs.close();
  }

  /**
   * Restituisce il numero totale di colonne nello schema della tabella.
   *
   * @return Il numero totale di colonne nello schema della tabella.
   */
  public int getNumberOfAttributes() {
    return tableSchema.size();
  }

  /**
   * Restituisce l'oggetto Column corrispondente all'indice specificato.
   *
   * @param index L'indice della colonna nella lista dello schema.
   * @return L'oggetto Column corrispondente all'indice specificato.
   */
  public Column getColumn(int index) {
    return tableSchema.get(index);
  }


}

		     


