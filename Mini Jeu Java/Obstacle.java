import java.awt.*;

public abstract class Obstacle extends Item{

  public Obstacle (String nom, int x , int y){
    super(nom , x , y );
  }

 public Obstacle (String nom){
   super (nom);
 }
public abstract void dessiner (Graphics g);
}
