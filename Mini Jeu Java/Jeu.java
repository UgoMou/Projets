import javax.swing.*;

public class Jeu {
  private static Avatar currentPlayer;
  private static Avatar[] players = new Avatar[2];
  private static int NB_TOUR_MAX = 1;
  private static int NB_TOUR = 0;

  public static void main (String [] args) throws InterruptedException{
    System.setProperty("file.encoding", "UTF-8");
/*
    String nom1 = JOptionPane.showInputDialog("Nom du joueur 1 :");
    String nom2 = JOptionPane.showInputDialog("Nom du joueur 2 :");

		try {
			NB_TOUR_MAX = Integer.parseInt(JOptionPane.showInputDialog("Nombre de Tour :"));
		} catch (NumberFormatException e) {
			NB_TOUR_MAX = 10;
			JOptionPane.showMessageDialog(null, "Erreur : Nombre de tour 10");
		}
*/
    Avatar mario = new Avatar("", 60.5, "Avatar2");
    Avatar luigi = new Avatar("", 100.5, "Avatar1");

    //*********************************************** ITEMS *********************************************** */
    Monde.ajouterItem(mario);
    players[0] = mario;
    Monde.ajouterItem(luigi);
    players[1] = luigi;

    currentPlayer = mario;
    //m.afficher();

    ///// Test Graphique
    Fenetre fenetre = new Fenetre();

    Interact.init();
    //*********************************************** JEU *********************************************** */

    for (int i = 1; i <= NB_TOUR_MAX * 2; i++) {
      fenetre.repaint();
      while (Interact.getState() == "play"){
        Thread.sleep(250);  //ralenti l'affichage
      }
      currentPlayer.rencontrerVoisins();
      Interact.talk(currentPlayer.toString());
      //System.out.println(currentPlayer;
      nextPlayer();
      if (i % 2 == 0){
        Monde.deplacerCreature();
        NB_TOUR++;
      }
    }
    mario.setDist(mario.course());
    luigi.setDist(luigi.course());
    Yoda.yoda.PouvoirdeYoda();
    Interact.end();
    fenetre.repaint();
    //*********************************************** GAGNANT *********************************************** */
    /*
    int amisMario = mario.getAmis().size();
    int amisLuigi = luigi.getAmis().size();
    double distMario;
    double distLuigi;
    String ggwp = "";
    double chicken;
    Avatar winner;
    Creature daFast;
    if (amisMario != 0 && amisLuigi != 0) {
      mario.PouvoirdeYoda();
      distMario = mario.course();
      distLuigi = luigi.course();
      if( distMario == distLuigi){
        ggwp += mario.getNom() + " et " + luigi.getNom() + " ont gagné(e)s par égalité";
      }
      else{
        if (distMario > distLuigi){
          chicken = distMario;
          winner = mario;
        }
        else {
          chicken = distLuigi;
          winner = luigi;
        }

        ggwp += winner.getNom() + " a gagné.e la course grâce à ses amis :\n";
        daFast = winner.getCreaturePlusRapide();
        for (Creature c : winner.getAmis()){
          ggwp += "\t - ";
          if (c == daFast)
            ggwp += c.getNom() + " is da Speeeed";
          else
            ggwp += c.getNom();
          ggwp += "\n";
        }
        ggwp += "Ils ont parcou.e.s " + chicken + "km.\n";
      }
    }
    else {
      if (amisMario == 0 && amisLuigi == 0)
        ggwp += "La course n'a pas eu lieu car " + mario.getNom() + " et " + luigi.getNom() + " n'ont pas d'ami.e.s";
      else {  if(amisMario == 0)
          ggwp += luigi.getNom() + " a gagné.e la course car " + mario.getNom() + " n'a pas d'ami.e.s";
        else
        ggwp += mario.getNom() + " a gagné.e la course car " + luigi.getNom() + " n'a pas d'ami.e.s";
      }
    Interact.talk(ggwp);
    }*/
    //System.out.println(ggwp);
  }

  public static Avatar getCurrPlay(){
    return currentPlayer;
  }

  public static void nextPlayer(){
    currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
  }

  /*public static Avatar getWinner(){
    return winner;
  }*/

  public static Avatar[] getPlayers(){
    return players;
  }

  public static void setNbTours(int nb){
    if ( (NB_TOUR_MAX + nb) > 0)
      NB_TOUR_MAX += nb;
  }

  public static int getNb_tours(){
    return NB_TOUR;
  }

  public static int getNb_tours_max(){
    return NB_TOUR_MAX;
  }
}
