import java.awt.*;

public class Creature extends Personnage{
  protected Sac sac;
  protected Avatar bff;
  private Image image = null;
  private double coefPills;

  public Creature(){
    super(Noms.getNom());
    sac = new Sac();
    bff = null;
    coefPills = 1;
    image = Images.getImage(Noms.getTab_icourant());
  }

  public Creature(String nom){
    super(nom);
    sac = new Sac();
    image = Images.getImage(nom);
  }

  public Image getImage(){
    return image;
  }

  public void ajouter(Acc acc){
    if (sac.getPoids() + acc.getPoids() < 0.5 * getPoids())
      sac.ajouter(acc, false);
    else{
      Interact.talk( acc.getNom() + " est trop lourd pour " + getNom() + ", l'objet est tombé par terre" );
      //System.out.println(acc.getNom() + " est trop lourd pour " + getNom() + ", l'objet est tombé par terre");
      Monde.drop(acc, getX(), getY());
    }
  }

  public double getVitesse(){
    double v = 0.25 * getPoids() - sac.getPoids();
    if (v < 0)
      return 0;
    return v * coefPills;
  }

  protected void newBFF (Avatar newBFF){
    if (bff != null && bff != newBFF){
      Interact.talk( String.format("Désolé %s je préfère %s, iel est plus sympa", bff.getNom(), newBFF.getNom()) );
      //System.out.println(String.format("Désolé %s je préfère %s, iel est plus sympa", bff.getNom(), newBFF.getNom()));
      bff.perdreAmi(this);
    }
    if (newBFF == bff){
      bff = null;
    }
    else
      bff = newBFF;
  }

  public void manger(){
    Acc obj;
    int nbElm = sac.getNbElements();
    String eat;
    if (nbElm == 0){
      eat = getNom() + " n'a rien mangé";
      Interact.talk( eat );
      return;
    }
    for( int i = 0; i < nbElm; i++){
      obj = sac.obtenir(0);
      if (obj instanceof Mangeable) {
        eat = getNom() + " a mangé " + obj.toString();
        manger((Mangeable) obj);
        Interact.talk( eat );
        //System.out.println( eat );
        continue;
      }
      if (obj instanceof Sac && ((Sac)obj).getNbElements() > 0) {
        Sac sol = sac;
        sac = (Sac) obj;
        manger();
        sac = sol;
      }
      sac.ajouter(obj, false);
    }
  }

  public void seDeplacer(){
    int taille = Monde.taille;
    int x = getX() + (int)(Math.random() * 7) - 3 ;
    int y = getY() + (int)(Math.random() * 7) - 3 ;
    if ( (x >= 0 && x < taille)  && (y >= 0 && y < taille) && (Monde.chercher(x,y)== null)){
      setX(x);
      setY(y);
    }
    Monde.world.repaint();
  }

  public void manger(Mangeable m){
    if (m instanceof Pills)
      coefPills = coefPills * 1.5;
    this.addPoids(m.getPoids());
  }

  public void courir () {
    String run = getNom() + String.format(" court à %.2f", getVitesse()) + "km/h avec le " + sac.getNom();
    Interact.talk( run );
		//System.out.println( run );
  }

  public void dessiner(Graphics g){
    	int tc = Monde.tailleCase;
    	g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
    }

  public String toString (){
    return super.toString() + " a pour ami " + bff.getNom();
  }
}
