import java.util.ArrayList;
/**
 *  Represente un Cercle. C'est la base du programme car tout les Objets vont heriité de cette classe. Les Cercle initées au début sont des potentiels stations.
 * @author Paulius Mickunas
 * @author Ugo Moutymbo
 * @version 1.2
 * @since 1.0
*/
 
public class Cercle {
  
 	private double theta;
	private double rayon;
	private double norme;
  private static ArrayList<Cercle> ptSect = new ArrayList<Cercle>();

	public static final double x = 1.0;
    /**
     * Le Constructeur de Cercle 
     * @param  t l'angle
     * @param  r le rayon
     * @param  n la norme
     * @return   Un cercle avec les paramètres données.
     */
  	protected Cercle(double t, double r, double n){
		  theta = t;
		  norme = n;
		  rayon = r;
		  ptSect.add(this);
 	}

 	protected Cercle(){
		this(0, Map.getSize() , 0);
	}
/**********************************************************************/
  /**
   * Renvoie l'absice correspondant au plan cartésien
   * @return [description]
   */
	public double getX() {
		return norme * Math.cos(theta);
	}
  /**
   * Renvoie l'ordonné correspondant au plan cartésien
   * @return [description]
  */
	public double getY() {
		return norme * Math.sin(theta);
  }

/**********************************************************************/
  /**
   * Conversion de coordonnées cartésiennes en polaire.
   * @param  x [description]
   * @param  y [description]
   * @return   [description]
   */
	public static double cartesianToPolarNorme(double x, double y) {
		return x / Math.cos (cartesianToPolarTheta(x,y));
	}
  /**
   * Conversion de coordonnées cartésiennes en polaire.
   * @param  x l'absice
   * @param  y l'ordonnée
   * @return   Renvoie l'angle corespondant au complexe.
   */
	public static double cartesianToPolarTheta(double x, double y) {
		return Math.atan(y / x);
	}


	
/**********************************************************************/
  /**
   * Accesseur du Diametre de l'Objet.
   * @return 
   */
	public double getDiam(){
		return 2*rayon;
  }
  /**
   * Enleve le cercle c de la liste.
   * @param c [description]
   */
	public static void delete(Cercle c) {
		ptSect.remove(c);
	}

  /**
   * Accesseur de la norme
   * @return retourne la norme
   */ 
	public double getRayon(){
		return rayon;
	}
  /**
   * Accesseur de la norme
   * @return retourne la norme
   */
	public double getNorme(){
		return norme;
	}
   /**
   * Accesseur de Theta
   * @return retourne la norme
   */
	public double getAngle(){
		return theta;
	}


/**********************************************************************/
  /**
  /**
   * Calcule la distance entre le centre de l'Objet et centre du cercle c.
   * @param  c [description]
   * @return  la distance entre c1 et c2
   */ 
  public double getDistance(Cercle c){
    return Math.sqrt(
      Math.pow(this.getX() - c.getX(), 2)
      +
      Math.pow(this.getY() - c.getY(), 2)
      );
    }

  public static double getDistance(Cercle c1, Cercle c2){
		return Math.sqrt(
	    Math.pow(c1.getX() - c2.getX(), 2)
	    +
	    Math.pow(c1.getY() - c2.getY(), 2)
	  );
	}

  /**
   * Calcula la distance entre deux points, en coordonées artésiennes. 
   * @param  x x du premier point
   * @param  y y du premier point]
   * @param  p x du deuxieme point
   * @param  q y du deuxieme point
   * @return   renvoie un double.
   */
	public static double getDistance(double x, double y, double p, double q) {
		return Math.sqrt(
	    Math.pow(x - p, 2)
	    +
	    Math.pow(y - q, 2)
	  );
	}


	public double getSurface() {
		return Math.pow((rayon/2.0),2)*Math.PI;
	}
/**
 * Calcule la moyenne des centres de Cercles situé dans l'Objet.
 * @return Un Cercle dont le centre est la moyenne.
 */
	public Cercle getMoyenne() {
		double tM = 0;
		double nM = 0;
		double dM = 0;
		int k = 0;
		for(Cercle i : ptSect){
			tM += i.theta;
			nM += i.norme;
			dM += i.rayon;
			k++;
		}
		return new Cercle(tM/k, dM/k, nM/k);
	}
  /**
   * Cherche  le cercle le plus proche 
   * @return un Cercle, le plus proche de l'Objet
   */
	public Cercle getClosest() {
		Cercle closest = ptSect.get(0);
		for(Cercle c : ptSect){
			if(getDistance(this, c) < getDistance(this, closest))
				closest = c;
		}
		return closest;
	}
  /**
   * Renvoie une liste des points secteurs.
   * @param  nbPtSect [description]
   * @return          [description]
   */
	public ArrayList<Cercle> getListPtSectInside(int nbPtSect) {
		ArrayList<Cercle> listeC = new ArrayList<Cercle>();

		for(Cercle c : ptSect){
			if(c.norme < this.norme)
				listeC.add(c);
		}
		return listeC;
	}

	public ArrayList<Cercle> getPtSectAll() {
		return ptSect;
	}
  /**
   * Renvoie le nombre de Cercles
   * @return [description]
   */
	public int getNbPtSect() {
		return ptSect.size();
	}
  /**
   * Clone l'Objet
   * @return [description]
   */
	public Cercle clone() {
		return new Cercle(theta, rayon, norme);
	}


/**************************** A REFAIRE SUPPRIMER INIDE ************************/
  protected void suprimerInside() {
		for(Cercle c : ptSect){
			if(c.norme < this.norme + this.rayon && c.norme > this.norme - this.rayon)
				ptSect.remove(c);
		}
  }
  /**
   * Inscrit n cercle dans l'objet. 
   * @param n [description]
   */

protected void inscrireCercle( int n) {
    //estimation les cercles ocupe environ 0,76 de la surface totale  pour 20 cercles.
      double rayonSmaller = Math.sqrt(((this.getSurface()*(Math.PI/Math.sqrt(12)) ) /(double) n)/ Math.PI );
      double degreRotation = Math.PI/3;
      int cpt = 0, cpt2 =1;
      double degreTemp = 0;
      double rayon = 2*rayonSmaller;
      double normeTemp = rayon;

      Cercle cercleTemp =new Cercle(0,rayon,0);
      Cercle cercleTemp2;
      boolean first = true;

      while (cpt < n-1){
        if (degreTemp >= 2*Math.PI){
          degreTemp = 0;
          normeTemp += rayon; 
          cpt2++;
          first = true;
        }
      if (first){
          cercleTemp = new Cercle(degreTemp, rayon , normeTemp  );
          first = false;
      }else{
        cercleTemp2 = cercleTemp;
        cercleTemp = new Cercle(degreTemp, rayon , normeTemp  );
          if (cpt2 > 1){
            if (degreTemp==0){

            }else if (degreTemp>=Math.PI){
              cercleTemp2.inscireLigneDroiteDeCercles(cercleTemp, cpt2);  
            }else{
              cercleTemp.inscireLigneDroiteDeCercles(cercleTemp2, cpt2);
            }
          }
        cpt++;
        degreTemp+=degreRotation;
        first = false;
        }
      }
    }

/**
 * Inscrit n cercle entre l'Objet et le Cercle b 
 * @param b b est un autre cercle, 
 * @param n le nombrede cercles à inscire.
 */
  public void inscireLigneDroiteDeCercles(Cercle b, int n){


      double dDelta = this.getDistance(b)/n;

      double absciceAvancer;
      double angleTrigo;
      double coefDir = (this.getY() - b.getY())/(this.getX() - b.getX());
      double cst = this.getY() - coefDir*this.getX();
        angleTrigo= Math.sin(Math.PI / 6);
      
     /* if (coefDir == 0){
        angleTrigo = -p1;
      }
      else  {
      }*/
      double tempX = this.getX(), tempY  =this.getY(); 
      double tempNorme, tempTheta;

}
}

/******************************************


  					switch(degreTemp){
  						case 0: 
  							break;
  						case Math.PI/3: 
  							cercleTemp.inscireLigneDroiteDeCercles(cercleTemp2, cpt2); break;
  						case 2*Math.PI/3:
  							cercleTemp2.inscireLigneDroiteDeCercles(cercleTemp, cpt2);break;
  						case Math.PI:
  							cercleTemp.inscireLigneDroiteDeCercles(cercleTemp2, cpt2);break;
  						case 4*Math.PI/3:
  							cercleTemp2.inscireLigneDroiteDeCercles(cercleTemp, cpt2);break;
  						case 5*Math.PI/3:
  							cercleTemp.inscireLigneDroiteDeCercles(cercleTemp2, cpt2);break;
  					}
	protected void inscrireCercle( int n) {
  	//estimation les cercles ocupe environ 0,76 de la surface totale  pour 20 cercles.
  		double rayonSmaller = Math.sqrt(((this.getSurface()*(Math.PI/Math.sqrt(12)) ) /(double) n)/ Math.PI );
  		double degreRotation = Math.PI/3;
  		int cpt = 0, cpt2 =1;
  		double degreTemp = 0;
  		double rayon = 2*rayonSmaller;
  		double normeTemp = rayon;

  		Cercle cercleTemp =new Cercle(0,rayon,0);
  		Cercle cercleTemp2;
  		boolean first = true;

  		while (cpt < n-1){
  			if (degreTemp >= 2*Math.PI){
  				degreTemp = 0;
  				normeTemp += rayon;	
  				cpt2++;
  				first = true;
  			}
 			if (first){
  				cercleTemp = new Cercle(degreTemp, rayon , normeTemp  );
  				first = false;
 			}else{
 				cercleTemp2 = cercleTemp;
 				cercleTemp = new Cercle(degreTemp, rayon , normeTemp  );
  				if (cpt2 > 1){
  					if (degreTemp==0){

  					}else if (degreTemp>=Math.PI){
	  					cercleTemp2.inscireLigneDroiteDeCercles(cercleTemp, cpt2);	
  					}else{
  						cercleTemp.inscireLigneDroiteDeCercles(cercleTemp2, cpt2);
  					}
  				}
  			cpt++;
  			degreTemp+=degreRotation;
  			first = false;
  			}
  		}
  	}





  r= 2√[u² + uv + v²];
tan Θ = √3 v / (2u + v);

******************************************************/
/*
 public void inscireLigneDroiteDeCercles(Cercle b, int n){

      if (n<= 1) return ;  
      //dist entre 2 cercle
      double distance = getDistance(this, b);
      double diametre = distance/n-1; // diam du noveau cercle 
      
      double angleRotation = Math.abs(this.getAngle() - b.getAngle())/ n;  

      double angleDestination = b.getAngle();

      double tempNorme =this.getNorme() ;
      double tempTheta =this.getAngle() ;



      for( int i = 1; (i < n ); i++){
        tempTheta+=angleRotation;
        
        
        Cercle newCercle = new Cercle(tempTheta,diametre,tempNorme);
      }
    }
}

      if (n<= 1) return ;  
      //dist entre 2 cercle
      double distance = getDistance(this, b);
      double diametre = distance/n-1; // diam du noveau cercle 
      
      double angleRotation = Math.abs(this.getAngle() - b.getAngle())/ n;  

      double angleDestination = b.getAngle();

      double tempNorme =this.getNorme() ;
      double tempTheta =this.getAngle() ;



      for( int i = 1; (i < n ); i++){
        tempTheta+=angleRotation;
        
        
        Cercle newCercle = new Cercle(tempTheta,diametre,tempNorme);
      }

fonctionne partiellement
  		while (cpt < n-1){
  			if (degreTemp >= 2*Math.PI  ){
  				degreTemp = 0;
  				if (cpt2<2){
  					degreRotation /= 2;
  				}
  				else if (cpt2 > 5){
  					if (cpt2%2 == 0){
  						degreTemp += degreRotation/2;	
   					}
   					degreRotation /= 2;
  				} 
  				normeTemp += norme;
  				cpt2 ++;
  			}
  			cercleTemp = new Cercle(degreTemp, norme, normeTemp);
  			cpt++;
  			degreTemp+=degreRotation;

    public void inscireLigneDroiteDeCercles(Cercle b, int n){


      double dDelta = getDistance(this,b)/n;

      double absciceAvancer;
    double angleTrigo;
      double coefDir = (this.getY() - b.getY())/(this.getX() - b.getX());
      double cst = this.getY() - coefDir*this.getX();
      
      if (coefDir == 0){
        angleTrigo = -p1;
      }
      else  {
        angleTrigo= Math.sin(Math.PI / 6);
      }
      double tempX = this.getX(), tempY  =this.getY(); 
      double tempNorme, tempTheta;



      for( int cpt = 1; cpt < n; cpt ++){
        absciceAvancer = dDelta*angleTrigo ;
        tempX += absciceAvancer;
        tempY = coefDir*tempX + cst;
        tempNorme = cartesianToPolarNorme(tempX, tempY);
        tempTheta = cartesianToPolarTheta(tempX, tempY);
        
          if tempTheta >= Math.PI &&  
        Cercle newCercle = new Cercle(tempTheta,dDelta,tempNorme);
      }
    }
}



*/



