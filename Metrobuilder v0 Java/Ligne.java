import java.util.ArrayList;
import java.awt.Color;
/**
 * 
 * @author Ugo
 */
public class Ligne {

  public final int color = (new Color((float) Math.random(),(float) Math.random(),(float) Math.random()).getRGB()); // Couleur aléatoire des trajets
  private String name; // Nom de la Lignee
  private ArrayList<Station> crossing = new ArrayList<Station>(); // Stations par lesquelles la Ligne passe
  private Station[] terminus = new Station[2]; // Terminus de la Ligne
/**
 * Constructeur de la classe Ligne
 * @param name Nom de la Ligne 
 * @param s1 Station de départ de la Ligne
 * @param s2 Station d'arrivé de la Ligne
 */
  public Ligne(String name, Station s1, Station s2){
  	this.name = name;
    terminus[0] = s1;
    terminus[1] = s2;
    for(Station s : terminus) {
      crossing.add(s);
      s.crossedBy(this);
    }
  }
/**
 * Méthode toString
 * @return String réprésentant la Ligne et sa composition
 */
  public String toString () {
    String toStr = name + " from " + terminus[0] + " to " + terminus[1] + " composed of :";
    for (Station station : crossing){
      toStr += "\n\t - " + station;
    }
    return toStr;
  }
/**
 * Renvoie le nom de la Ligne
 * @return nom de la ligne
 */
  public String getName(){
    return name;
  }
/**
 * Retourne les terminus de la Ligne
 * @return les 2 Stations terminus
 */
  public Station[] getTerminus(){
    return terminus;
  }
/**
 * Retourne toutes les Station formant la Ligne
 * @return liste des Stations formant la Ligne
 */
  public ArrayList<Station> getStations(){
    return crossing;
  }
/**
 * Retourne la distance totale de parcours de la Ligne
 * @return distance totale de la Ligne
 */
  public double getDistanceTotale() {
    double dis = 0;
    for(int i = 0; i < crossing.size() - 1; i++)
      dis += Station.getDistance(crossing.get(i), crossing.get(i + 1));
    return dis;
  }
/**
 * Détruit la Ligne passée en paramètre
 * @param ligne Ligne à détruitre
 */
  public void delete() {
    for (Station station : crossing)
      station.notCrossedBy(this); // Enlève la Ligne des Lignes passant par la Station
    crossing.clear(); // La ligne n'est composée d'aucune Station
  }
/**
 * Enlève la Station de la Ligne
 * @param station Station à enlever
 */
  public void notCrossing(Station station) {
    crossing.remove(station);
    station.notCrossedBy(this);
    if (terminus[0] == station)
      terminus[0] = crossing.get(0);
    if (terminus[1] == station)
      terminus[1] = crossing.get( crossing.size() - 1);    
  }
/**
 * Développe la Ligne de manière rectiligne
 */
  public void splitStraight() {
    ArrayList<Station> toAdd = new ArrayList<Station>();
    ArrayList<Station> between;
    Station station = null;
    int max;
    for (int i = 0; i < crossing.size() - 1; i++) { // Pour chaque "trajet" composant la Ligne, trouve les Stations se trouvant entre
      between = Station.getStationBetween(crossing.get(i), crossing.get(i + 1));
      max = between.size();
      for (int j = 0; j < max; j++) {
        station = between.get(between.size() / 2); // Sélectionne une Station 'aléatoirement' dans le lot de Stations
        if (! (crossing.contains(station) || station.isMaxCrossed()) ) { // Vérifie qu'il n'y a pas déjà trop de Ligne qui passe par cette station
          toAdd.add(station);
          break;
        }
        between.remove(station);
      }
    }
    for (Station stat : toAdd)
      addStation(stat); // Ajoute les Stations sélectionnées à la Ligne
  }
/**
 * Développe la Ligne de manière courbe
 */
  public void splitAround () {
    ArrayList<Station> toAdd = new ArrayList<Station>();
    ArrayList<Station> between;
    Station station = null;
    int max;
    for (int i = 0; i < crossing.size() - 1; i++) { // Pour chaque "trajet" composant la Ligne, trouve les Stations se trouvant entre
      between = Station.getStationAround(crossing.get(i), crossing.get(i + 1), terminus);
      max = between.size();
      for (int j = 0; j < max; j++) {
        station = between.get(between.size() / 2); // Sélectionne une Station 'aléatoirement' dans le lot de Stations
        if ( station.getCrossed().size() <  station.maxCross() && ! crossing.contains(station)) { // Vérifie qu'il n'y a pas déjà trop de Ligne qui passe par cette station
          toAdd.add(station);
          break; // Sélectionne une unique sStation par "trajet"
        }
        between.remove(station);
      }
    }
    for (Station stat : toAdd)
      addStation(stat); // Ajoute les Stations sélectionnées à la Ligne
  }
/**
 * Ajoute une Station à la Ligne
 * @param newStation Station à ajouter
 */
  public void addStation(Station newStation) {
    if (! crossing.contains(newStation)) { // Vérifie que la Station ne fait pas partie de la Ligne
      Station s1, s2, closest = crossing.get(0);
      double dist, mini = Station.getDistance(newStation, closest);
      int index = 0;
      // Trouve la Station la plus proche de newStation
      for (int i = 0; i < crossing.size(); i++) {
        s1 = crossing.get(i);
        dist = Station.getDistance(s1, newStation);
        if (dist < mini) {
          mini = dist;
          closest = s1;
          index = i;  // Indice dans le tableau 'crossing' de la Station la plus proche
        }
      }
      if (terminus[0] == closest || terminus[1] == closest) { // Cas où la Station la plus proche est un des terminus
        if (terminus[0] == closest) {
          s1 =  crossing.get( 1 );
          // Compare les distances entre stations pour savoir où positionner newStation dans crossing
          if ( Station.getDistance(s1, closest) < Station.getDistance(s1, newStation) && (MenuDroit.getState() != "around") ) { // Vérifie que ce n'est pas un split Around
            crossing.add(0, newStation); // Placement de 'newStation' avant 'closest' donc 'newStation devient le nouveau terminus'
            terminus[0] = newStation; // Changement de terminus
          }
          else {
            crossing.add(1, newStation); // Placement de 'newStation' après 'closest'
          }
        }
        else {
          s1 = crossing.get(index - 1);
          // Compare les distances entre stations pour savoir où positionner newStation dans crossing
          if ( Station.getDistance(s1, closest) < Station.getDistance(s1, newStation ) && (MenuDroit.getState() != "around") ) { // Vérifie que ce n'est pas un split Around
            crossing.add(newStation);  // Placement de 'newStation' avant 'closest' donc 'newStation devient le nouveau terminus'
            terminus[1] = newStation; // Changement de terminus
          }
          else {
            crossing.add(index, newStation); // Placement de 'newStation' après 'closest'
          }
        }
      }
      else {
        s1 = crossing.get(index - 1);
        s2 = crossing.get(index + 1);
        // Compare les distances entre Stations pour savoir où positionner 'newStation' dans 'crossing' (avant ou après 'closest')
        if (Station.getDistance(s1, newStation) + Station.getDistance(closest, s2) < Station.getDistance(s1, closest) + Station.getDistance(newStation, s2)) {
          crossing.add(index, newStation); // Placement de 'newStation' avant 'closest'
        }
        else {
          crossing.add(index + 1, newStation); // Placement de 'newStation' après 'closest'
        }
      }
    }
    newStation.crossedBy(this);
  }
}
