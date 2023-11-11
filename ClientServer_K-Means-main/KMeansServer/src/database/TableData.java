package database;

import database.TableSchema.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * La classe TableData fornisce metodi per ottenere dati specifici da una tabella del database.
 */
public class TableData {

  /**
   * Oggetto DbAccess utilizzato per la connessione al database.
   */
  private DbAccess db;

  /**
   * Costruttore della classe TableData.
   *
   * @param db L'oggetto DbAccess utilizzato per la connessione al database.
   */
  public TableData(DbAccess db) {
    this.db = db;
  }

  /**
   * Ottiene una lista di esempi distinti dalla tabella specificata.
   *
   * @param table Nome della tabella da cui ottenere gli esempi distinti.
   * @return Una lista di esempi distinti dalla tabella specificata.
   * @throws SQLException      Eccezione lanciata in caso di errori SQL durante l'esecuzione della query.
   * @throws EmptySetException Eccezione lanciata se la tabella è vuota.
   */
  public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
    TableSchema ts = new TableSchema(db, table);
    Statement s = db.getConnection().createStatement();
    ResultSet rs = s.executeQuery("SELECT DISTINCT * " + "FROM " + table + ";");
    List<Example> list = new ArrayList<>();
    while (rs.next()) {
      Example ex = new Example();
      for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
        if (ts.getColumn(i).isNumber()) ex.add(rs.getDouble(ts.getColumn(i).getColumnName()));
        else ex.add(rs.getString(ts.getColumn(i).getColumnName()));
      }
      list.add(ex);
    }
    s.close();
    rs.close();
    if (list.isEmpty()) throw new EmptySetException("La tabella " + table + " è vuota");
    return list;
  }


  /**
   * Ottiene un insieme di valori distinti dalla colonna specificata nella tabella.
   *
   * @param table  Nome della tabella dalla quale ottenere i valori distinti.
   * @param column Colonna dalla quale ottenere i valori distinti.
   * @return Un insieme di valori distinti dalla colonna specificata nella tabella.
   * @throws SQLException Eccezione lanciata in caso di errori SQL durante l'esecuzione della query.
   */
  public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
    Statement s = db.getConnection().createStatement();
    ResultSet rs = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " " + "FROM " + table + " " + "ORDER BY " + column.getColumnName() + ";");
    HashSet<Object> set = new HashSet<>();
    while (rs.next()) {
      if (column.isNumber()) set.add(rs.getDouble(column.getColumnName()));
      else set.add(rs.getString(column.getColumnName()));
    }
    s.close();
    rs.close();
    return set;
  }

  /**
   * Ottiene il valore aggregato (MIN o MAX) della colonna specificata nella tabella.
   *
   * @param table    Nome della tabella dalla quale ottenere il valore aggregato.
   * @param column   Colonna dalla quale ottenere il valore aggregato.
   * @param aggregate Tipo di aggregazione desiderato (MIN o MAX).
   * @return Il valore aggregato della colonna specificata nella tabella.
   * @throws SQLException      Eccezione lanciata in caso di errori SQL durante l'esecuzione della query.
   * @throws NoValueException   Eccezione lanciata se non viene restituito alcun valore dalla query.
   */
  public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
    Object ret;
    Statement s = db.getConnection().createStatement();
    ResultSet rs = s.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") AS aggregata " + "FROM " + table + ";");
    try {
      if (rs.next()) {
        if (column.isNumber()) ret = rs.getDouble("aggregata");
        else ret = rs.getString("aggregata");
      } else throw new NoValueException("Nessun valore per la colonna " + column.getColumnName());
    } finally {
      s.close();
      rs.close();
    }
    return ret;
  }

}
