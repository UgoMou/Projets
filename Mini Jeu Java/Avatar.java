import java.awt.*;
import java.util.ArrayList;
// import java.util.Scanner;

public class Avatar extends Personnage{
  private ArrayList<Creature> listeAmis;
  private ArrayList<Acc> listeAcc;
  private double money;
  private Image image = null;
  private double coef;
  private double dist;

  public Avatar(String nom, double poids, String nomFichier){
    super(nom, poids);
    listeAmis = new ArrayList<Creature>();
    listeAcc = new ArrayList<Acc>();
    money = Math.random() * 10 + 5;
    image = Images.getImage(nomFichier);
    coef = 1;
    dist = 0;
  }

  public void setNom(String str){
    nom = str;
  }

  public void setCoef(double  x){
    coef = x;
  }

  public void setDist(double dis){
    dist = dis;
  }

  public double getDist(){
    return (dist * coef);
  }

  public String toString(){
    int nbAcc = 0;
    for (Acc acc : listeAcc){
      nbAcc++;
      if (acc instanceof Sac)
        nbAcc += ((Sac) acc).getNbElements();
    }
    return super.toString() + " : " + listeAmis.size() + " amis(s) " + nbAcc + " accessoire(s)";
  }
  public Image getImage(){
    return image;
  }

  public ArrayList<Creature> getAmis(){
    return listeAmis;
  }

  public ArrayList<Acc> getAcc(){
    return listeAcc;
  }

  public double getMoney(){
    return money;
  }

  public void supprimePremierAmi(){
    listeAmis.remove(0);
  }

  public boolean estAmi(Creature crea){
    if (listeAmis.contains(crea))
      return true;
    return false;
  }

  private void devenirAmi(Creature crea){
    crea.newBFF(this);
    listeAmis.add(crea);
    Interact.talk( crea.getNom() + " est devenu l'ami de " + getNom() );
    //System.out.println(crea.getNom() + " est devenu l'ami de " + getNom());
  }

  protected void perdreAmi(Creature crea){
    crea.newBFF(this);
    listeAmis.remove(crea);
    Interact.talk( crea.getNom() + " n'est plus l'ami de " + getNom() );
    //System.out.println(crea.getNom() + " n'est plus l'ami de " + getNom());
  }

  private void rencontrer(Creature crea){
    Acc a = null;
    if (listeAcc.size() > 0){
      a = listeAcc.get(0);
      listeAcc.remove(0);
    }
    if (a != null){
      Interact.talk( getNom() + " a donné " + a.getNom() + " à " + crea.getNom() );
      //System.out.println(getNom() + " a donné " + a.getNom() + " à " + crea.getNom());
      crea.ajouter(a);
      if (!this.estAmi(crea) && a.getPoids() < 50)
        this.devenirAmi(crea);
    }
    else
      if (this.estAmi(crea))
        this.perdreAmi(crea);
  }

  public double course(){
    double dist = 0;
    for ( Creature crea : listeAmis){
      crea.courir();
      crea.manger();
      crea.courir();
      dist += crea.getVitesse();
    }
    return dist * coef;
  }

  public Creature getCreaturePlusRapide(){
    Creature rapide = listeAmis.get(0);
    for ( Creature crea : listeAmis)
      if (rapide.getVitesse() > crea.getVitesse())
        rapide = crea;

    return rapide;
  }

  public int compterAccMangeable(){
    int j=0;
    for (int i = 0; i < listeAcc.size(); i++) {
      if (listeAcc.get(i) instanceof Mangeable){
        j++;
      }
    }
    return j;
  }

  private void ouvrir(Coffre coffre){
    ArrayList<Item> contenu = coffre.ouvrir();
    for (Item item : contenu){
      if (item instanceof Tresor){
        dealMoney( ((Tresor) item).getTresor() );
      }
      else
        ramasser((Acc) item, true);
    }
  }

  private void ramasser(Acc acc, boolean msg){
    boolean place = false;
    if ( acc instanceof LivreMagique || acc instanceof Epee){
      listeAcc.add(acc);
      Interact.talk( getNom() + " ramasse " + acc.getNom() );
      return;
    }
    for (Item i : listeAcc)
        if (i instanceof Sac && ! ((Sac) i).getFull()){
          if( (place = ((Sac) i ).ajouter(acc, msg)) ){
            if (msg){
              Interact.talk( acc.getNom() + " a été placé(e) dans le " + i.getNom() + " de " + this.getNom() );
              //System.out.println(acc.getNom() + " a été placé(e) dans le " + i.getNom() + " de " + this.getNom());
            }
            return;
          }
        }
    if (! place){
      listeAcc.add(acc);
      if (msg){
        Interact.talk( getNom() + " ramasse " + acc.getNom() );
        //System.out.println(getNom() + " ramasse " + acc.getNom());
      }
    Monde.supprimerItem(acc);
    }
  }

  public void rencontrerVoisins() {
    ArrayList<Item> voisins = Monde.getVoisins(this);
    for (Item item : voisins){

      if (item instanceof Avatar){
        Interact.talk( "Salutation mon ami " + item.getNom() );
        //System.out.println("Salutation mon ami " + item.getNom());
        killCreature( (Avatar) item);
        continue;
      }

      if (item instanceof Creature){
        rencontrer((Creature) item);
        continue;
      }

      if (item instanceof Coffre){
        ouvrir((Coffre) item);
        continue;
      }

      if (item instanceof Acc){
        ramasser((Acc) item, true);
        continue;
      }

      if (item instanceof Eau){
        ((Eau) item).changeImage();
        continue;
      }

      if (item instanceof ArbreMagique){
        boolean derniereminute = false;
        ArbreMagique arbremagique = (ArbreMagique) item ;
        if(arbremagique.getContenu() instanceof Gobelin){
          arbremagique.changeImage();
          for (Acc acc : listeAcc){
            if (acc instanceof Epee){
              Interact.talk("Un gobelin vous a sauté dessus, heureusement vous possédez une épee.\nVous l'avez tué en plein vol mais l'épée est restée bloquer !");
              try {
                Thread.sleep(2000);
              } catch (Exception e) {
                System.out.println(e);
              }
              Monde.supprimerItem(arbremagique);
              listeAcc.remove(acc);
              derniereminute = true;
              break;
            }
          }
          if (! derniereminute){
            Interact.talk("Un gobelin vous a sauté dessus, malheureusement vous n'aviez rien pour vous défendre.\nIl vous a tout volé !");
            toutPerdre();
            continue;
          }
        }
        if (arbremagique.getContenu() instanceof Sonic){
          rencontrerCreatureOP( (Creature)(arbremagique.getContenu()) , item);
          continue;
        }
        if (arbremagique.getContenu() instanceof Yoda){
          rencontrerCreatureOP( (Creature)(arbremagique.getContenu()) , item);
          continue;
        }
        if (arbremagique.getContenu() instanceof Chevredelamort){
          rencontrerCreatureOP( (Creature)(arbremagique.getContenu()) , item);
          continue;
        }
      }

      if (item instanceof Magasin){
        Interact.shop("Marchand : Bienvenu.e dans mon magasin " + item.getNom() + ". \nSouahaitez-vous :\n\t- Acheter ?\n\t- Vendre ?\n\t- Partir" );
        switch ( Interact.getCursor() ) {
          case 0 :
            ((Magasin) item).acheter(this);
            break;
          case 1 :
            vendre((Magasin) item);
            break;
          case 2 :
            Interact.play();
            break;
        }
        /*
        Scanner sc = new Scanner(System.in);
        //System.out.println("Bienvenu.e dans mon magasin " + item.getNom() + "\n Souahaitez-vous :\n\t( 0 )-acheter ?\n\t( 1 )-vendre ?\n\t( 2 )-Partir" );
        switch (sc.nextInt()) {
          case 0 :
            ((Magasin) item).acheter(this);
            break;
          case 1 :
            vendre((Magasin) item);
            break;
          case 2 :
            Interact.play();
            break;
        }*/
      }
      Monde.world.repaint();
    }
    Interact.play();
  }
/*
  public void seDeplacer(){
    int absi, ordo, taille = Monde.taille;
    Scanner sc = new Scanner(System.in);
    String size = String.format("[0,%d]", taille);
    // Récupère les coordonnées
    do{
      System.out.println("Veuillez saisir une absicisse entre " + size + " : ");
      absi = sc.nextInt();
    }while(absi > taille || absi < 0);
    do{
      System.out.println("Veuillez saisir une ordonnée entre " + size + " : ");
      ordo = sc.nextInt();
    }while(ordo > taille - 1 || ordo < 0);
    // Déplace le personnage aux coordonnées
    this.setX(absi);
    this.setY(ordo);
    // Rencontre ses voisins
    rencontrerVoisins();
  }
*/
  public void seDeplacer(int dx, int dy){
    int taille = Monde.taille;
    int x = getX() + dx;
    int y = getY() + dy;
    if ( (x >= 0 && x < taille)  && (y >= 0 && y < taille) && (Monde.chercher(x,y)== null)){
      setX(x);
      setY(y);
    }
    Monde.world.repaint();
  }

  public double acheter (Acc acc){
    double prix = acc.getPrix();
    if (prix > money){
      Interact.talk( "Vous n'avez pas assez d'argent pour acheter " + acc.getNom() );
      //System.out.println("Vous n'avez pas assez d'argent pour acheter " + acc.getNom());
      return 0.0;
    }
    dealMoney(-prix);
    ramasser(acc, false);
    return prix;
  }

  public void vendre (Magasin mag) {
    //Scanner sc = new Scanner(System.in);
    String discution;
    int num, i, nbItem;
    do{
        i = 0;
        discution = String.format("Le magasin possède %.2f €\nVous pouvez vendre : \n", mag.getMoney());
        for (Acc acc : listeAcc){
          discution += String.format("\t- %s : %.2f\n", acc.getNom(), acc.getPrix());
          i++;
          if(acc instanceof Sac && ( nbItem = ((Sac) acc).getNbElements() ) != 0){
            Acc[] contenu = ((Sac) acc).getTab();
            for(int j = 0; j < nbItem; j++){
              discution += String.format("\t- %s : %.2f\n", contenu[j].getNom(), contenu[j].getPrix());
              i++;
            }
          }
        }
        discution += "\t- Acheter ?\n";
        i++;
        discution += "\t- Partir ?";
        Interact.shop( discution );
        /*System.out.println(discution);
        System.out.println("Choisissez l'objet que vous désirez vendre : ");
        num = sc.nextInt();*/
        num = Interact.getCursor();
        if (num == i - 1){
          mag.acheter(this);
          return;
        }
        if (num == i){
          return;
        }
        for (Acc acc : listeAcc){
          if (num == 0){
            dealMoney( mag.vendre(acc) );
            if (acc instanceof Sac)
              for (Acc a : ((Sac) acc).getTab())
                ramasser(a , false);
            listeAcc.remove(acc);
            break;
          }
          if (acc instanceof Sac){
            if ((num = inSac(mag, num, (Sac) acc)) == 0){
              break;
            }
          }
          num--;
        }
    }while(true);
  }

  private int inSac(Magasin mag, int num, Sac sac){
    for (int i = 0; i < sac.getNbElements(); i++){
      num--;
      if (num == 0){
        dealMoney( mag.vendre( sac.obtenir(i) ) );
        return 0;
      }
    }
    return num;
  }

  private void dealMoney(double argent){
    if (argent > 0){
      Interact.talk( String.format("%s a gagné %.2f €", getNom(), argent) );
      //System.out.println( String.format("%s a gagné %.2f €", getNom(), argent));
    }
    if (argent < 0){
      Interact.talk( String.format("%s a perdu %.2f €", getNom(), -argent) );
      //System.out.println( String.format("%s a perdu %.2f €", getNom(), -argent));
    }
    money += argent;
  }

  public void toutPerdre(){
    money = 0;
    int a = listeAcc.size();
    for ( int i =0 ; i< a ; i++){
      //if ( listeAcc.get(0) instanceof Sac)
      //  ((Sac)(listeAcc.get(0))).vider();
      listeAcc.remove(0);
    }
  }

  public void rencontrerCreatureOP(Creature creature, Item item){
    if (listeAmis.contains(creature))
      return;
    for ( int i =0 ; i< listeAcc.size(); i++){
      if (listeAcc.get(i) instanceof LivreMagique){
        ((ArbreMagique) item ).changeImage();
        creature.newBFF(this);
        listeAmis.add(creature);
        listeAcc.remove(i);
        return;
      }
    }
  }

  public void killCreature(Avatar avatar){
    ArrayList<Creature> listeCreature = avatar.getAmis();
    Creature killed;
    if ( listeCreature != null){
      for( Acc acc : listeAcc){
        if (acc instanceof Epee){
          for (Acc ac : avatar.listeAcc){
            if (ac instanceof Epee){
              Interact.talk("Vous possédez tous les 2 une épee !\nVos 2 épées se cassent dans votre bataille\nRien ne se passe.");
              listeAcc.remove(acc);
              avatar.listeAcc.remove(ac);
              return;
            }
          }
          killed = listeCreature.get(0);
          Interact.talk( getNom() + " a tué " + killed.getNom() + " de " + avatar.getNom() );
          listeAcc.remove(acc);
          Monde.supprimerItem(acc);
          avatar.supprimePremierAmi();
          return;
        }
      }
    }
  }


  public void dessiner(Graphics g){
    int tc = Monde.tailleCase;
    g.drawImage(image, getX() * tc + 1, getY() * tc + 1, tc - 2, tc - 2, Monde.world);
  }
}
