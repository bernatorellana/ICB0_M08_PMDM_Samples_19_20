package iesmila.net.spinnerandrecycler;

import java.io.Serializable;
import java.util.ArrayList;

public class Personatge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1486530091436589402L;
	private String nom;
	private int id, idRecursImatge;


	private boolean esMalo=false;
	
		
	public Personatge(int id, String nom, int idRecursImatge, boolean esMalo) {
		super();
		this.id = id;
		this.nom = nom;
		this.idRecursImatge = idRecursImatge;
		this.esMalo = esMalo;
	}
	
	public int getId() {
		return id;
	}	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getIdRecursImatge() {
		return idRecursImatge;
	}
	public void setIdRecursImatge(int idRecursImatge) {
		this.idRecursImatge = idRecursImatge;
	}
	public boolean esMalo() {
		return esMalo;
	}

	public void setEsMalo(boolean esMalo) {
		this.esMalo = esMalo;
	}



	//------------------------------------------------------
	private static ArrayList<Personatge> personatges;
	public static ArrayList<Personatge> getPersonatges(){
		if(personatges==null) {
			personatges = new ArrayList<Personatge> ();
			personatges.add(new Personatge(0, "Blanca", R.drawable.blanca, true));
			personatges.add(new Personatge(1, "Chun-li", R.drawable.chunli, true));
			personatges.add(new Personatge(2, "Dalshim", R.drawable.dalshim, true));
			personatges.add(new Personatge(3, "Ken", R.drawable.ken, true));
			personatges.add(new Personatge(4, "Zangief", R.drawable.zangief, false));
			personatges.add(new Personatge(5, "Zangief1", R.drawable.zangief, false));
			personatges.add(new Personatge(6, "Zangief2", R.drawable.zangief, false));
			personatges.add(new Personatge(7, "Zangief3", R.drawable.zangief, false));
		}
		return personatges;
	}
	public static Personatge getPersonatge(int id){
		
		for(Personatge p:personatges){
			if(p.getId()==id) return p;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personatge other = (Personatge) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
