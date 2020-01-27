import java.util.ArrayList;
import javax.swing.*;
/**
 * Station est l'objet qui hérite de Cercle. Dans cette Objet sont stockés toutes les stations.
 * 
 * @author Paulius Mickunas
 * @version 1.2
 * @since 1.0
*/

public class Station extends Cercle {
  
	public enum Degre{
		NODEG,
		PRI,
		SEC,
	};

  private static ArrayList<Station> stationsAll = new ArrayList<Station>();
  private ArrayList<Ligne> crossed;
  private Ville city;
  private String name;
  private Degre degre;
/**
 * Constructeur de la classe Station
 * @param name nom de la station
 * @param theta angle de la position (coordonée polaire)
 * @param norme norme de la position (coordonnée polaire)
 * @param rayon rayon d'action de la Station
 */
	public Station(String name, double theta, double norme, double rayon, Ville ville ){
    super(theta, rayon, norme);
    this.name = name;
    degre = Degre.NODEG;
    city = ville;
    stationsAll.add(this);
    crossed = new ArrayList<Ligne>();
	}
/**
 * Méthode toString
 * @return String réprésentant la Station et son Degre
 */
  public String toString() {
    return name + " : " + degre;
  }
/**
 * Supprime la Station
 * @param station Station à supprimer
 */
  public void delete() {
    city.removeStation(this); // Supprime la Station de la ville 
    ArrayList<Ligne> toClear = crossed; 
    crossed = new ArrayList<Ligne>();
    stationsAll.remove(this);
    Cercle.delete(this); // Supprime la Station des Cercles
    for (Ligne ligne : toClear) 
      ligne.notCrossing(this); // Enlève la Station des Lignes qui passent par celle-ci
  }
/**
 * Ajoute la Ligne aux Lignes passant par la Station 
 * @param ligne Ligne à ajouter
 */
  public void crossedBy(Ligne ligne) {
    if(! crossed.contains(ligne))
      crossed.add(ligne);
  }
/**
 * Enlève la Ligne des Lignes passant par la Station 
 * @param ligne Ligne à enlever
 */
  public void notCrossedBy(Ligne ligne) {
    crossed.remove(ligne);
  }
/**
 * Retourne le nom de la Station
 * @return
 */
  public String getName(){
    return name;
  }
/**
 * Augmente ou diminue le Degre de la Station
 * @param up booléen indiquant si on augmente ou diminue le Degre
 */
  public void setDegre(boolean up){
    if (up)
      switch (degre) {
        case NODEG :
          degre = Degre.SEC;
          break;
        case SEC :
          degre = Degre.PRI;
          break;
        case PRI:
          break;
      }
    else
      switch (degre){
        case NODEG :
          break;
        case SEC :
          if (crossed.size() > 1) // Vérifie qu'il n'y a pas trop de Ligne qui passe par la Station
            JOptionPane.showMessageDialog(new JPanel(), name + " need to be connected to only 1 line for its degree to decrease !");
          else
            degre = Degre.NODEG;
          break;
        case PRI:
          if (crossed.size() > 3) // Vérifie qu'il n'y a pas trop de Ligne qui passe par la Station
            JOptionPane.showMessageDialog(new JPanel(), name + " need to be connected to 3 at most line for its degree to decrease !");
          else
            degre = Degre.SEC;
          break;
      }
  }
/**
 * Retourne le Degre de la Station
 * @return
 */
  public Degre getDegre(){
    return degre;
  }
  /**
   * Retourne la Station à la position (x, y)
   * @param x coordonnée x de la position
   * @param y coordonnée y de la position
   * @return Station au point (x, y) s'il y en a une
   */
  public static Station getStationAt(double x, double y) {
    for (Station station : stationsAll)
      if ( Math.sqrt( Math.pow(x - station.getX(), 2)  + Math.pow(y - station.getY(), 2) ) < 5)
        return station;
    return null;
  }
  /**
   * Retourne toutes les Stations non détruites
   * @return liste des Stations non détruites
   */
  public static ArrayList<Station> getAllStations() {
    return stationsAll;
  }
/**
 * Retourne la liste des Lignes passant par la Station
 * @return liste des Lignes passant par la Station
 */
  public ArrayList<Ligne> getCrossed(){
    return crossed;
  }
// /**
//  * 
//  * @param s1
//  * @param s2
//  * @return
//  */
//   public static ArrayList<Station> getShortestRoad(Station s1, Station s2) {
//     return null;
//   }
/**
 * Retourne la liste des Stations présentent entre les 2 Stations
 * @param s1 
 * @param s2
 * @return liste des Stations présentent entre les 2 Stations
 */
  public static ArrayList<Station> getStationBetween (Station s1, Station s2) {
    double max = getDistance(s1, s2) + (s1.getRayon() + s2.getRayon()) / 2;
    ArrayList<Station> between = new ArrayList<Station>(); 
    // System.out.println(String.format("dist = %.2f\nmax = %.2f", getDistance(s1, s2), max));
    for (Station station : stationsAll){
      // System.out.println( String.format("%s = %.2f", station.getName(), getDistance(station, s1) + getDistance(station, s2)));
      if ( getDistance(station, s1) + getDistance(station, s2) <= max)
        between.add(station);
    }
    return between;
  }
/**
 * Retourne la liste des Stations présentent entre 2 cerlces dont le centre est le milieu du segment formé par les terminus
 * @param s1
 * @param s2
 * @param terminus 
 * @return liste des Stations présentent entre 2 cerlces dont le centre est le milieu du segment formé par les terminus
 */
  public static ArrayList<Station> getStationAround (Station s1, Station s2, Station[] terminus) {
    ArrayList<Station> around = new ArrayList<Station>();
    double x = (terminus[0].getX() + terminus[1].getX()) / 2;
    double y = (terminus[0].getY() + terminus[1].getY()) / 2;
    double dist = Cercle.getDistance(x, y, s1.getX(), s1.getY());
    double rayon = (s1.getRayon() + s2.getRayon()) / 2;
    double min = dist - rayon;
    double max = dist + rayon;
    double maxBetween = getDistance(s1, s2) + (s1.getRayon() + s2.getRayon()) / 2;
    System.out.println("max = " + maxBetween);
    for (Station station : stationsAll) {
      dist = Cercle.getDistance(x, y, station.getX(), station.getY());
      if (dist < min || dist > max || ( (terminus[0] != s1 || terminus[1] != s2) && Station.getDistance(station, s2) + Station.getDistance(station, s1) > maxBetween ) )
        continue;
      around.add(station);
    }
    return around;
  }
/**
 * Retourne la distance entre les 2 Stations
 * @param s1
 * @param s2
 * @return distance entre les 2 Stations
 */
  public static double getDistance(Station s1, Station s2) {
  return Math.sqrt(
    Math.pow(s1.getX() - s2.getX(), 2)
    +
    Math.pow(s1.getY() - s2.getY(), 2)
    );
  }
/**
 * Retourne le nombre maximum de Ligne pouvant passée par cette Station
 * @return nombre maximum de Ligne pouvant passée par cette Station
 */
  public int maxCross() {
    switch (degre) {
      case NODEG:
        return 1;
      case SEC:
        return 3;
      case PRI:
        return 6;
      default:
        return 1;
    }
  }
/**
 * Retourne si oui ou non la Station est déjà à sa capacité maximumu de Ligne passante
 * @return booléen indiquant si la Station est déjà à sa capacité maximumu de Ligne passante
 */
  public boolean isMaxCrossed() {
    return maxCross() == crossed.size();
  }

  public static boolean isNear(double x, double y) {
    for (Station station : stationsAll)
      if (Cercle.getDistance(x, y, station.getX(), station.getY()) < station.getRayon() / 2)
        return true;
    return false;
  }
}
