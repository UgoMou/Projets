import java.awt.*;

public class Arbre extends Obstacle{
  protected Image image = null;


  public Arbre (){
    super("Arbre");
    image = Images.getImage("Arbre");
  }

  public Arbre(String nom){
    super(nom);
    image = Images.getImage("Arbre");
  }

  public Arbre(String nom, int x , int y){
    super(nom , x , y);
    image = Images.getImage("Arbre");
  }

  public Arbre( int x , int y ){
    super( "Arbre" , x ,y);
    image = Images.getImage("Arbre");
  }

  public void dessiner (Graphics g){
    int tc = Monde.tailleCase;
    g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
  }


}
