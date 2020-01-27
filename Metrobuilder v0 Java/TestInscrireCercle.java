public class TestInscrireCercle {
	public static void main(String[] args) {
		Cercle temp  = new Cercle();
		temp.inscrireCercle(300);
		Map map = Map.map;
 		map.drawCercleAllInside(temp);
    	map.drawAxe();
    	double angle = Math.PI/3; 
    	double norme= 10;
    	double diam = 100; 
		//Cercle temp2 = new Cercle(angle,norme,diam );
		/*Cercle temp3 = new Cercle(angle*2,norme,diam );
		Cercle temp4 = new Cercle(angle*3,norme,diam );
		Cercle temp5 = new Cercle(angle*4,norme,diam );
		Cercle temp6 = new Cercle(angle*5,norme,diam );
		Cercle temp7 = new Cercle(angle*6,norme,diam );
		*/
		//temp.inscireLigneDroiteDeCercles(temp2,  3);


		map.saveF("map");
    	System.out.println("########## END ##########");



	}
}


	/*	double tempX, tempY;
		double tempNorme = Cercle.cartesianToPolarNorme(10, 10);
  		double tempTheta = Cercle.cartesianToPolarTheta(10, 10);
  		temp = new Cercle(tempTheta, 10 ,tempNorme);
  		double tempNorme2 = Cercle.cartesianToPolarNorme(20, 20);
  		double tempTheta2 = Cercle.cartesianToPolarTheta(20, 20);
  		Cercle temp2 = new Cercle(tempTheta2, 10 ,tempNorme2);
  		temp.inscireLigneDroiteDeCercles(temp2, 10, 5);  µ*/