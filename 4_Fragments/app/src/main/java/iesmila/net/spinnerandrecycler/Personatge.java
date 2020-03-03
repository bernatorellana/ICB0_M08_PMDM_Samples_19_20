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
	private String urlImatge;

	private boolean esMalo=false;
	
		
	public Personatge(int id, String nom, int idRecursImatge, boolean esMalo, String urlImatge) {
		super();
		this.id = id;
		this.nom = nom;
		this.idRecursImatge = idRecursImatge;
		this.esMalo = esMalo;
		this.urlImatge = urlImatge;
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

	public String getImatgeUrl(){
		return urlImatge;
	}

	//------------------------------------------------------
	private static ArrayList<Personatge> personatges;
	public static ArrayList<Personatge> getPersonatges(){
		if(personatges==null) {
			personatges = new ArrayList<Personatge> ();
			personatges.add(new Personatge(0, "Blanca", R.drawable.blanca, false, "https://mouse.latercera.com/wp-content/uploads/2017/07/blanka.jpg"));
			personatges.add(new Personatge(1, "Chun-li", R.drawable.chunli, false, "https://66.media.tumblr.com/d573b0d8bde266b618963fc10478999f/tumblr_p5fhnp8PCz1wokz4bo4_250.png"));
			personatges.add(new Personatge(2, "Dalshim", R.drawable.dalshim, false, "https://cdn02.nintendo-europe.com/media/images/08_content_images/games_6/nintendo_switch_7/nswitch_ultrastreetfighter2thefinalchallengers/CI_NSwitch_UltraStreetFighter2TheFinalChallengers_CharacterIcons_Dhalsim_image800w.jpg"));
			personatges.add(new Personatge(3, "Ken", R.drawable.ken, true, "https://cdn02.nintendo-europe.com/media/images/08_content_images/games_6/nintendo_switch_7/nswitch_ultrastreetfighter2thefinalchallengers/CI_NSwitch_UltraStreetFighter2TheFinalChallengers_CharacterIcons_Ken_image800w.jpg"));
			personatges.add(new Personatge(4, "Zangief", R.drawable.zangief, true, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv8AyWPx3QRoe9MSo1n6--hqBb1HpHc6wOxQMganQgMWTQJlJVMw&s"));
			personatges.add(new Personatge(5, "Zangief1", R.drawable.zangief, true, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv8AyWPx3QRoe9MSo1n6--hqBb1HpHc6wOxQMganQgMWTQJlJVMw&s"));
			personatges.add(new Personatge(6, "Zangief2", R.drawable.zangief, true, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv8AyWPx3QRoe9MSo1n6--hqBb1HpHc6wOxQMganQgMWTQJlJVMw&s"));
			personatges.add(new Personatge(7, "Zangief3", R.drawable.zangief, true, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv8AyWPx3QRoe9MSo1n6--hqBb1HpHc6wOxQMganQgMWTQJlJVMw&s"));
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
