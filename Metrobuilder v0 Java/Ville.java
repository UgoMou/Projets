import java.util.ArrayList;
/**
 * @author Ugo
 */
public class Ville extends Pole {

  private final int population;
  private ArrayList<Station> stations;
  private ArrayList<Ligne> metro;
  private ArrayList<Pole> poles;
  private int budgetTotale;
/**
 * Constructeur de la classe Ville
 * @param rayon rayon du cercle représentant la Ville
 * @param pop population de la Ville
 * @param budget budget de la Ville
 */
  public Ville(int rayon, int pop, int budget) {
    super(0, rayon);
    stations = new ArrayList<Station>();
    metro = new ArrayList<Ligne>();
    poles = new ArrayList<Pole>();
  	population = pop;
    budgetTotale = budget;
  }
/**
 * Méthode toString
 * @return String représentant la Ville, ses Poles et ses Lignes
 */
  public String toString() {
    String str = "City of " + population + " inhabitants\nMain pole :";
    for (Pole pole : poles)
      str += "\n\t" + pole;
    str += "\nMetro :";
    for (Ligne ligne: metro)
      str += "\n\t" + ligne;
    return str;
  }
/**
 * Retourne les Stations présentent dans la Ville
 * @return liste des Stations de la Ville
 */
  public ArrayList<Station> getStations(){
    return stations;
  }
/**
 * Retourne l'ensemble des Lignes de la Ville
 * @return liste des Lignes passant par une Station de la Ville
 */
  public ArrayList<Ligne> getMetro(){
    return metro;
  }
/**
 * Ajoute une station aux Stations de la Ville
 * @param station Station à ajouter
 */
  public void addStation (Station station) {
    stations.add(station);
  }
/**
 * Enlèbe une station aux Stations de la Ville
 * @param station Station à enlever
 */
  public void removeStation (Station station) {
    stations.remove(station);
  }
/**
 * Ajoute une Ligne au metro de la Ville
 * @param ligne Ligne à ajouter
 */
  public void addLigne (Ligne ligne) {
    metro.add(ligne);
  }
/**
 * Enlève une Ligne du metro de la Ville
 * @param ligne Ligne à enlever
 */
  public void removeLigne (Ligne ligne) {
    metro.remove(ligne);
  }
}
