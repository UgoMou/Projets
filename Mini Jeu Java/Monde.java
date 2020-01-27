import java.util.ArrayList;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;


public class Monde extends JPanel{
  private static final long serialVersionUID = 1L;
  private static ArrayList<Item> listeItems;
  private static ArrayList<Creature> listeCreature;
  public static final int taille = 30;
  public static final int tailleCase = 30;
  public final static Monde world = new Monde();
  private Image imageHerbe = null;
  private Image imageredcursor = null;


  private Monde(){
    setPreferredSize(new Dimension(taille*tailleCase , taille*tailleCase));
    listeItems = new ArrayList<Item>();
    listeCreature = new ArrayList<Creature>();
    initialize();
    imageHerbe = Images.getImage("Herbe");
    imageredcursor = Images.getImage("Redcursor");
  }

  private static int getPositionAlea(){
    return (int) (Math.random() * taille);
  }

  private void initialize(){
    //############# Chevredelamort #############
    ajouterItemAtCoord(new ArbreMagique( 0, taille-1));
    ajouterItemAtCoord(new ArbreMagique( 0, 0));
    ajouterItemAtCoord(new ArbreMagique( taille-1 , 0));
    ajouterItemAtCoord(new ArbreMagique( taille-1, taille-1 ));
    ajouterCreatureOP(Chevredelamort.chevredelamort);

    //############# Arbre #############
    for (int j = 1; j < taille-1; j++)
      ajouterItemAtCoord(new Arbre(0,j));
    for (int j = 1; j < taille-1; j++)
      ajouterItemAtCoord(new Arbre(j,0));
    for (int j = 1; j < taille-1; j++)
      ajouterItemAtCoord(new Arbre(taille-1,j));
    for (int j = 1; j < taille-1; j++)
      ajouterItemAtCoord(new Arbre(j,taille-1));
    //############# ArbreMagique #############
    for (int j = 0; j < Math.random() * (taille / 2) + taille / 2; j++)
      ajouterItem(new ArbreMagique());
    //############# Eau #############
    for (int j = 0; j < Math.random() * (taille / 2) + taille / 2; j++)
      ajouterItem(new Eau());
    //############# Creature #############
    Creature creature;
    for (int j = 0; j < Math.random() * (taille / 2) + 6; j++){
      creature = new Creature();
      listeCreature.add(creature);
      ajouterItem(creature);
    }
    ajouterCreatureOP(Yoda.yoda);
    ajouterCreatureOP(Sonic.sonic);
    ajouterCreatureOP(Gobelin.gobelin);
    //############# Coffre #############
    for (int j = 0; j < Math.random() * (taille / 2) + taille / 2; j++)
      ajouterItem(new Coffre());
    //############# Accessoire #############
    for (int j = 0; j < Math.random() * (taille / 4) + taille / 4; j++)
      ajouterAcc(new Sac());
    for (int j = 0; j < Math.random() * (taille / 4) + taille / 2; j++)
      ajouterAcc(new Pomme());
    for (int j = 0; j < Math.random() * (taille / 4) + taille / 6; j++)
      ajouterAcc(new Pills());
    for (int j = 0; j < 3; j++){
      ajouterAcc(new Epee());
      ajouterAcc(new LivreMagique());
    }
    //############# Magasin #############
    ajouterItem(Marchand.marchand);
  }

  public static void deplacerCreature(){
    for ( Creature c : listeCreature)
      c.seDeplacer();
  }


  public static void ajouterItem (Item item) {
    do {
      item.setX(getPositionAlea());
      item.setY(getPositionAlea());
    } while (chercher(item.getX(), item.getY()) != null);
    listeItems.add(item);
  }

  public static void ajouterItemAtCoord(Item item){
    if (chercher(item.getX(), item.getY()) == null)
      listeItems.add(item);
    else
      ajouterItem(item);
  }

  public static void ajouterCreatureOP(Item chanceoupaschance){
    ArrayList<ArbreMagique> listeArbreMagique = new ArrayList<ArbreMagique>();
    for (Item item : listeItems)
      if ((item instanceof ArbreMagique) && ((ArbreMagique)item).getContenu()==null)
        listeArbreMagique.add((ArbreMagique) item);
    ( listeArbreMagique.get( (int) (Math.random() * listeArbreMagique.size()) ) ).ajouter(chanceoupaschance);

  }

  private static void ajouterAcc (Acc acc) {
    ArrayList<Coffre> coffres = new ArrayList<Coffre>();
    for (Item item : listeItems)
      if (item instanceof Coffre)
        coffres.add((Coffre) item);
    ( coffres.get( (int) (Math.random() * coffres.size()) ) ).ajouter(acc);
  }

  public static void supprimerItem(Item item){
    listeItems.remove(item);
    item.setX(-1);
    item.setY(-1);
  }

  public static void drop (Item item, int x, int y) {
    for (int i = x - 1; i <= x + 1; i++)
      for (int j = y - 1; j <= x + 1; j++)
        if (chercher(i, j) == null){
          item.setX(i);
          item.setY(j);
          listeItems.add(item);
          return;
        }
  }

  public static Item chercher (int x,int y) {
    for(Item i : listeItems)
      if(i.getX() == x && i.getY() == y)
        return i;
    return null;
  }

  public static ArrayList<Item> getVoisins(Item item){
    ArrayList<Item> voisins = new ArrayList<Item>();
    for(Item i : listeItems)
      if(item.distance(i) <= 2 && i != item)
        voisins.add(i);

    return voisins;
  }

  private static String getNomCourts(String nom){
    switch (nom.length()){
      case 1 :
        return "  " + nom + " ";
      case 2 :
        return " " + nom + " ";
      case 3 :
        return nom + " ";
      default :
        return nom.substring(0, 3) + nom.charAt(nom.length()-1);
    }
  }

  public static void afficher(){
    Item i;
    String aff="    |";
    for ( int x = 0; x<taille; x++)
      aff +=  getNomCourts("" + x) + "|" ;

    aff += "\n";
    for ( int x = 0; x < taille; x++){
      aff += getNomCourts("" + x) + "|";
      for( int y = 0; y < taille; y++){
        if((i = chercher(x, y)) != null)
          aff += getNomCourts(i.getNom());
        else
          aff += "    ";
        aff += "|";
      }
      aff += "\n";
    }
  System.out.println(aff);
  }

  public void dessinerMap(Graphics g){
    int longueur = getWidth();
    int hauteur = getHeight();
    for (int i = 0; i < longueur / 6; i++)
      for (int j = 0; j < hauteur / 6; j++)
        g.drawImage( imageHerbe, i * tailleCase * 5, j * tailleCase * 5, tailleCase * 5, tailleCase * 5, this) ;
  }

  public void dessinerTalk(Graphics g, int height){
    int size = taille * tailleCase;
    Image imageShop = null;
    try {
      imageShop = ImageIO.read(new File("./Image/dialogue.png"));
    }
    catch(IOException exc) {
      exc.printStackTrace();
    }
    g.setColor(new Color(95, 0, 0));
    g.fillRect(15, size - height, size - 30, height - 15);
    g.drawImage(imageShop, 20, size - height + 3, size - 40, height - 20, this);
  }

  private void dessinerFin(Graphics g){
      Image imagedumenudefin = Images.getImage("Fin");
      Image imageAvatar1 = Images.getImage("Avatar2");
      Image imageAvatar2 = Images.getImage("Avatar1");
      Image imagecoupewinner = Images.getImage("Coupewinner");
      Image imagecoupeperdant = Images.getImage("Coupeperdant");
      Avatar[] avatar = Jeu. getPlayers();
      int amisMario = avatar[0].getAmis().size();
      int amisLuigi = avatar[1].getAmis().size();
      double distMario;
      double distLuigi;
      String ggwp = "";
      double chicken;
      Avatar winner;
      Creature daFast;

      Font font = new Font ("Arial", Font.BOLD, 50);
      Font font1 = new Font ("Arial", Font.BOLD, 26);
      g.setFont(font);
      g.setColor(Color.BLACK);



      g.drawImage( imagedumenudefin, 0, 0, getWidth(),getHeight(), this);
      g.drawString("RESULTATS", 300, 120);
      g.setFont(font1);

      g.drawImage( imageAvatar1  , 200, 200, 300, 300,this);
      g.drawImage( imageAvatar2, 200, 530, 300, 300, this);
      if (amisMario != 0 && amisLuigi != 0) {
        distMario = avatar[0].getDist();
        distLuigi = avatar[1].getDist();
        g.drawString("Les animaux de  "+  avatar[0].getNom(), 480, 300);
        g.drawString("ont parcouru " + String.format(" %.2f",distMario) + "km", 480, 330);
        g.drawString("Les animaux de  "+  avatar[1].getNom(), 480, 630);
        g.drawString(" ont parcouru " + String.format(" %.2f",distLuigi) + "km", 480, 660);

        if( distMario == distLuigi){
          ggwp = avatar[0].getNom() + " et " + avatar[1].getNom() + " ont gagné(e)s par égalité";
          g.drawImage( imagecoupewinner, 10, 220, 300, 300, this);
          g.drawImage( imagecoupewinner, 10, 540, 300, 300, this);
          g.drawString(ggwp, 60, 170);
        }
        else{
          if (distMario > distLuigi){
            chicken = distMario;
            winner = avatar[0];
            g.drawImage( imagecoupewinner, 10, 220, 300, 300, this);
            g.drawImage( imagecoupeperdant, 80, 560, 150, 250, this);
          }
          else {
            chicken = distLuigi;
            winner = avatar[1];
            g.drawImage( imagecoupewinner, 10, 540, 300, 300, this);
            g.drawImage( imagecoupeperdant, 80, 240, 150, 250, this);
          ggwp += winner.getNom() + " a gagné.e la course grâce à ses amis :\n";
          daFast = winner.getCreaturePlusRapide();
          for (Creature c : winner.getAmis()){
            ggwp += "\t - ";
            if (c == daFast)
              ggwp += c.getNom() + " is da Speeeed";
            else
              ggwp += c.getNom();
            ggwp += "\n";
          ggwp = "Ils ont parcou.e.s " + chicken + "km.\n";
        }
      }
    }

      }
      else {
        if (amisMario == 0 && amisLuigi == 0){
          ggwp = "La course n'a pas eu lieu car " + avatar[0].getNom()+ " et "  ;
          g.drawString(ggwp, 60, 170);
          ggwp = avatar[1].getNom() + " n'ont pas d'ami.e.s";
          g.drawString(ggwp, 60, 200);
          g.drawImage( imagecoupeperdant, 80, 240, 150, 250, this);
          g.drawImage( imagecoupeperdant, 80, 560, 150, 250, this);
        }
        else {
          if(amisMario == 0){
            ggwp = avatar[1].getNom() + " a gagné.e la course car ";
            g.drawString(ggwp, 60, 170);
            ggwp = avatar[0].getNom() + " n'a pas d'ami.e.s";
            g.drawString(ggwp, 60, 200);
            g.drawImage( imagecoupeperdant, 80, 240, 150, 250, this);
            g.drawImage( imagecoupewinner, 10, 540, 300, 300, this);
          }else{
            ggwp = avatar[1].getNom() + " a gagné.e la course car ";
            g.drawString(ggwp, 60, 170);
            ggwp = avatar[0].getNom() + " n'a pas d'ami.e.s";
            g.drawString(ggwp, 60, 200);
            g.drawImage( imagecoupewinner, 10, 220, 300, 300, this);
            g.drawImage( imagecoupeperdant, 80, 560, 150, 250, this);
          }
        }
      }


  }

  public void paintComponent(Graphics g){
		super.paintComponent(g); //redessine le panneau
    dessinerMap(g);
    //super.paintComponent(g); //redessine le panneau
    /*
    for (int i = 0; i<getWidth(); i++){
      g.setColor(Color.ORANGE);
      g.drawLine( i*tailleCase, 0, i*tailleCase, getHeight());
    }
    for (int i = 0; i<getHeight(); i++){
      g.setColor(Color.ORANGE);
      g.drawLine(0,  i*tailleCase, getWidth(), i*tailleCase);
    }*/
		for(Item itemVoisin : listeItems)
			if( itemVoisin != null)
        itemVoisin.dessiner(g);

    Avatar currentPlayer = Jeu.getCurrPlay();
    g.drawImage( imageredcursor, currentPlayer.getX() * tailleCase + 7  , currentPlayer.getY() * tailleCase - 17  , 20 , 20 , this);
    String state = Interact.getState();
    if (state == "talk" || state == "shop" || state == "init" || state == "tour"){
      Interact.dessinerTalk(g);
    }

    if (Interact.getState() == "end") {
      dessinerFin(g);
    }

  }
}
