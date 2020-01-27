import java.awt.*;

public abstract class Personnage extends Item{
  private double poids;

  protected Personnage(String nom, double poids){
    super(nom);
    this.poids = poids;
  }

  protected Personnage(String nom){
    this(nom, Math.random() * 100 + 30);
  }

  protected double getPoids(){
    return poids;
  }

  protected void addPoids(double p){
    if (p > 0)
      poids += p;
  }

  public String toString(){
    return getNom() + String.format(" %.2f", poids) + "kg";
  }

  public abstract void dessiner (Graphics g);
}
