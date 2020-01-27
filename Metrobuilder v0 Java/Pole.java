import java.util.ArrayList;
/**
 * Ceci est un pole: le cercle principal qui représente la ville est divisé 
 * een plus petit cercle, de diamètre différents dont les centres ce condfondent. Le pole correspond à 
 *l'un de ces cercles. 
 * @author Paulius Mickunas
 */
public class Pole extends Cercle {

  private double facteur;
  private ArrayList<Cercle> ptSectlocales;

	public Pole(double facteur, double diam){
    super(0, diam, 0);
    this.facteur = facteur;
	}

  public void addPole() {
  }

}
