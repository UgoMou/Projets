public class Noms{
  private static String[] tab = { "Bird", "Cheval", "Lion", "Baleine", "Pingouin", "Poule", "Lapin", "Rat", "Tigre", "Singe"};
  private static int id = 0;
  private static int cpt = 1;

  public static String getNom(){
    if(id == tab.length){
      cpt++;
      id = 0;
    }
    return tab[id++] + "_" + cpt;
  }

  public static String getTab_icourant(){
    return  tab[(id-1)%10];
  }
}

// Dragon Minautore Licorne sonic flash Phoenix Pegasus
