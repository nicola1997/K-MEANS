package data;

import database.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

import static database.QUERY_TYPE.MAX;
import static database.QUERY_TYPE.MIN;

/**
 * La classe Data rappresenta un insieme di dati associato a un determinato schema di tabella nel database.
 * Include informazioni sulle transazioni, sugli attributi e fornisce metodi per l'accesso e la manipolazione dei dati.
 */
public class Data /*implements Serializable*/ {
  // Le visibilità di classi, attributi e metodi devono essere decise dagli studenti
  private List<Example> data; // una matrice nXm di tipo Object dove ogni riga modella una transazioni
  private final int numberOfExamples; // cardinalità dell’insieme di transazioni (numero di righe in data)
  private final List<Attribute> attributeSet; // un vettore degli attributi in ciascuna tupla (schema della tabella di dati)

  /**
   * Costruttore della classe Data che inizializza il set di dati basato su connessione al database e informazioni sulla tabella.
   *
   * @param server   Il server del database.
   * @param database Il nome del database.
   * @param table    Il nome della tabella.
   * @param userId   L'ID utente per la connessione al database.
   * @param password La password per la connessione al database.
   * @throws DatabaseConnectionException Eccezione lanciata in caso di errore nella connessione al database.
   * @throws SQLException                Eccezione lanciata in caso di errore SQL.
   * @throws NoValueException             Eccezione lanciata in caso di mancanza di valori.
   * @throws EmptySetException            Eccezione lanciata in caso di insieme vuoto.
   */
  public Data(String server, String database, String table, String userId, String password) throws DatabaseConnectionException, SQLException, NoValueException, EmptySetException {
    // Connessione al database e inizializzazione dei dati, della cardinalità e degli attributi
    // basati su informazioni sulla tabella.
    DbAccess db = new DbAccess(server, database, userId, password);
    db.initConnection();
    TableData td = new TableData(db);
    TableSchema ts = new TableSchema(db, table);
    data = td.getDistinctTransazioni(table);
    numberOfExamples = data.size();
    attributeSet = new ArrayList<>();
    for (int i = 0; i < ts.getNumberOfAttributes(); i++) {
      if (ts.getColumn(i).isNumber()) {
        attributeSet.add(new ContinuousAttribute(ts.getColumn(i).getColumnName(), i, (double) td.getAggregateColumnValue(table, ts.getColumn(i), MIN), (double) td.getAggregateColumnValue(table, ts.getColumn(i), MAX)));
      } else {
        HashSet<Object> distValues = (HashSet<Object>) td.getDistinctColumnValues(table, ts.getColumn(i));
        HashSet<String> values = new HashSet<>();
        for (Object o : distValues) {
          values.add((String) o);
        }
        attributeSet.add(new DiscreteAttribute(ts.getColumn(i).getColumnName(), i, values));
      }
    }
  }

  /**
   * Restituisce il numero di transazioni nel set di dati.
   *
   * @return Il numero di transazioni nel set di dati.
   */
  public int getNumberOfExamples() {
    return numberOfExamples;
  }

  /**
   * Restituisce il numero di attributi presenti in ciascuna tupla.
   *
   * @return Il numero di attributi presenti in ciascuna tupla.
   */
  public int getNumberOfAttributes() {
    return attributeSet.size();
  }

  /**
   * Restituisce il valore dell'attributo per una determinata transazione e attributo.
   *
   * @param exampleIndex  L'indice della transazione.
   * @param attributeIndex L'indice dell'attributo.
   * @return Il valore dell'attributo per la transazione e l'attributo specificati.
   */
  public Object getAttributeValue(int exampleIndex, int attributeIndex) {
    return data.get(exampleIndex).get(attributeIndex);
  }

  /**
   * Restituisce l'attributo corrispondente all'indice specificato.
   *
   * @param index L'indice dell'attributo.
   * @return L'attributo corrispondente all'indice specificato.
   */
  Attribute getAttribute(int index) {
    return attributeSet.get(index);
  }

  /**
   * Restituisce un oggetto Tuple che rappresenta l'insieme di elementi di dati per una transazione specifica.
   *
   * @param index L'indice della transazione.
   * @return Un oggetto Tuple che rappresenta l'insieme di elementi di dati per la transazione specifica.
   */
  public Tuple getItemSet(int index) {
    Tuple tuple = new Tuple(attributeSet.size());
    for (int i = 0; i < attributeSet.size(); i++)
      if (attributeSet.get(i) instanceof ContinuousAttribute)
        tuple.add(new ContinuousItem((ContinuousAttribute) attributeSet.get(i), (Double) data.get(index).get(i)), i);
      else
        tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet.get(i), (String) data.get(index).get(i)), i);
    return tuple;
  }

  /**
   * Esegue un campionamento casuale di k indici di transazioni uniche nel set di dati.
   *
   * @param k La dimensione del campione.
   * @return Un array di indici di transazioni campionate casualmente.
   * @throws OutOfRangeSampleSize Eccezione lanciata se la dimensione del campione è fuori intervallo.
   */
  public int[] sampling(int k) throws OutOfRangeSampleSize {
    if (k <= 0 || k > data.size()) {
      throw new OutOfRangeSampleSize("Inserire k compreso tra 1 e " + data.size()
      );
    }
    int[] centroidIndexes = new int[k]; //choose k random different centroids in data.
    // Esegue un campionamento casuale di indici di transazioni uniche nel set di dati.
    Random rand = new Random();
    rand.setSeed(System.currentTimeMillis());
    for (int i = 0; i < k; i++) {
      boolean found;
      int c;
      do {
        found = false;
        c = rand.nextInt(getNumberOfExamples()); //Verifica che il centroide[c] non sia uguale a un centroide già memorizzato in CentroidIndexes
        for (int j = 0; j < i; j++)
          if (compare(centroidIndexes[j], c)) {
            found = true;
            break;
          }
      }
      while (found);
      centroidIndexes[i] = c;
    }
    return centroidIndexes;
  }

  /**
   * Confronta due centroidi specificati dagli indici delle transazioni i e j.
   * Restituisce true se i centroidi sono uguali per tutti gli attributi, altrimenti false.
   *
   * @param i L'indice della prima transazione (centroide).
   * @param j L'indice della seconda transazione (centroide).
   * @return true se i centroidi sono uguali per tutti gli attributi, altrimenti false.
   */
  private boolean compare(int i, int j) {
    for (int k = 0; k < attributeSet.size(); k++)
      if (!data.get(i).get(k).equals(data.get(j).get(k)))
        return false;
    return true;
  }

  /**
   * Calcola il prototipo per un attributo specifico in base a un insieme di indici di transazioni.
   *
   * @param idList    L'insieme di indici di transazioni.
   * @param attribute L'attributo per il quale calcolare il prototipo.
   * @return Il prototipo calcolato per l'attributo.
   */
  Object computePrototype(Set<Integer> idList, Attribute attribute) {
    if (attribute instanceof ContinuousAttribute)
      return computePrototype(idList, (ContinuousAttribute) attribute);
    else
      return computePrototype(idList, (DiscreteAttribute) attribute);
  }

  /**
   * Calcola il prototipo per un attributo discreto in base a un insieme di indici di transazioni.
   *
   * @param idList    L'insieme di indici di transazioni.
   * @param attribute L'attributo discreto per il quale calcolare il prototipo.
   * @return Il prototipo calcolato per l'attributo discreto.
   */
  String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
    int max = 0, tmp;
    String prototype = "";
    for (String a : attribute) {
      tmp = attribute.frequency(this, idList, a);
      if (tmp > max) {
        max = tmp;
        prototype = a;
      }
    }
    return prototype;
  }

  /**
   * Calcola il prototipo per un attributo continuo in base a un insieme di indici di transazioni.
   *
   * @param idList    L'insieme di indici di transazioni.
   * @param attribute L'attributo continuo per il quale calcolare il prototipo.
   * @return Il prototipo calcolato per l'attributo continuo.
   */
  Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
    double sum = 0;
    for (int i : idList) {
      sum += (double) data.get(i).get(attribute.getIndex());
    }
    return sum / (double) idList.size();
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < attributeSet.size(); i++)
      s += attributeSet.get(i).getName() + (i == attributeSet.size() - 1 ? "\n" : ", ");
    for (int i = 0; i < numberOfExamples; i++) {
      s += (i + 1) + ": ";
      for (int j = 0; j < attributeSet.size(); j++)
        s += data.get(i).get(j) + (j == attributeSet.size() - 1 ? "\n" : ", ");
    }
    return s;
  }

}
