import java.awt.*;

public class Sac extends Acc{
  private Acc[] tab;
  private boolean full;
  public static final Image image = Images.getImage("Sac");

  public Sac(int n) {
    super("Sac");
    tab= new Acc[n];
    full = false;
  }

  public Sac(){
    super("Sac");
    tab = new Acc[(int)(Math.random() * 10) + 1];
  }

  public Acc[] getTab(){
    return tab;
  }

  public boolean ajouter (Acc acc, boolean msg) {
    int nb;
    if((nb = getNbElements()) < tab.length){
      tab[nb] = acc;
      return true;
    }
    else{
      if (msg){
        Interact.talk( "Pas la place dans le " + getNom() );
        //System.out.println("Pas la place dans le " + getNom());
        full = true;
      }
      return false;
    }
  }

  public Acc obtenir(int i){
    if (i < getNbElements()){
      Acc finale = tab[i];
      for (int j = i; j < tab.length - 1; j++)
        tab[j] = tab[j+1];
      tab[tab.length-1] = null;
      full = false;
      return finale;
    }
    return null;
  }

  public int getNbElements(){
    int cpt=0;
    for (int i = 0; i < tab.length; i++) {
        if (tab[i] != null)
          cpt++;
    }
    return cpt;
  }

  public double getPoids(){
    double poids = 0.1;
    for(int i = 0; i < getNbElements(); i++){
        poids += tab[i].getPoids();
    }
    return poids;
  }

  public double getPrix(){
    return 0.5 * tab.length + 0.5;
  }

  public boolean getFull(){
    return full;
  }

  public String toString(){
    String s = super.toString() + " contient " + getNbElements() + " accessoires sur " + tab.length + " :\n";
    for(int i=0; i<getNbElements(); i++){
       s += "\t" + tab[i].toString() + "\n";
    }
    return s;
  }

  public void vider(){
    full = false;
    Interact.talk("Tous les objets du " + getNom() + " ont disparus");
    for ( int i = 0 ; i < tab.length ; i++)
      tab[i] = null;
  }

  public void dessiner(Graphics g){
      int tc = Monde.tailleCase;
      g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
    	//g.setColor(new Color(225,255,0)); //couleur courante devient bleu
    	//g.fillRect(getX()*tc, getY()*tc, tc, tc); //carre plein
    }
}
