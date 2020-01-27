public abstract class Acc extends Item{
  private static int cpt = 0;
  private final int numero;
  private String categorie;
/*
  protected Acc(String categorie, int x, int y){
    super(categorie + cpt++, x, y);
    this.categorie = categorie;
    numero = cpt;
  }
*/
  protected Acc(String categorie){
    super(categorie + cpt++);
    this.categorie = categorie;
    numero = cpt;
  }

  public int getNum(){
    return numero;
  }

  public String getCategorie(){
    return categorie;
  }

  public abstract double getPoids();

  public abstract double getPrix();

  public String toString(){
    return getNom() + " " + String.format("pèse %.2fkg",getPoids()) + "kg";
  }
}
