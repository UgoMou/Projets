//import java.util.Scanner;
import java.awt.*;
public class Marchand extends Magasin {
	private static final Image image = Images.getImage("Marchand");
	public static final Marchand marchand = new Marchand();  

	private Marchand(){
	  super("Marchand");
		initialize();
	}

	private void initialize(){
		for (int i = 0; i < Math.random() * 8 + 5; i++)
			stock.add(new Pomme());
		stock.add(new LivreMagique());
		stock.add(new Epee());
	}

	public void acheter (Avatar avatar) {
		//Scanner sc = new Scanner(System.in);
		String discution = "";
		int num, taille;
		double plus;
		do{
			if ( (taille = stock.size()) != 0){
				discution = String.format("Vous possédez %.2f €\nJe vends : \n", avatar.getMoney());

				for (int i = 0; i < taille; i++)
					discution += String.format("\t- %s : %.2f €\n", stock.get(i).getNom(), stock.get(i).getPrix());
			}
			else
				discution = "Je ne possède plus rien à vendre\nVoulez-vous :\n";
			discution += "\t- Vendre ?\n";
			discution += "\t- Partir ?";
			Interact.shop( discution );
			/*ystem.out.println(discution);
			System.out.println("Choisissez l'objet que vous désirez acquérir : ");
			do {
				num = sc.nextInt();
			}while(num < 0 || num > taille + 1);
			*/
			num = Interact.getCursor();
			if (num == taille){
				avatar.vendre(this);
				return;
			}
			if (num == taille + 1)
				return;
			if ( (plus = avatar.acheter( stock.get(num) )) != 0.0){
				money += plus;
				stock.remove(num);
			}
		}while(true);
	}

	public double vendre (Acc acc){
		double prix = acc.getPrix() * 0.9;
		if (prix > money){
			Interact.talk( "Je n'ai pas assez d'argent pour vous acheter " + acc.getNom() );
			//System.out.println("Je n'ai pas assez d'argent pour vous acheter " + acc.getNom());
		  	return 0.0;
		}
		money -= prix;
		stock.add(acc);
		return prix;
	}

  public void dessiner (Graphics g){
		int tc = Monde.tailleCase;
		g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
	}
}
