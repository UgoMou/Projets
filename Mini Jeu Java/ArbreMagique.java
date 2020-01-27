public class ArbreMagique extends Arbre{
  private Item contenu;

  public ArbreMagique (){
    super("ArbreMagique");
    contenu= null;
  }

  public ArbreMagique (int x , int y){
    super("ArbreMagique", x , y );
    contenu= null;
  }

  public Item getContenu(){
    return contenu;
  }

  public void ajouter(Item item){
      contenu= item;
  }

  public void changeImage(){
    if (contenu instanceof Gobelin){
      super.image = Gobelin.gobelin.getImage();
      return;
    }
    if (contenu instanceof Sonic){
      super.image = Sonic.sonic.getImage();
      return;
    }
    if (contenu instanceof Yoda){
      super.image = Yoda.yoda.getImage();
      return;
    }
    if (contenu instanceof Chevredelamort){
      super.image = Chevredelamort.chevredelamort.getImage();
      return;
    }
  }
}
