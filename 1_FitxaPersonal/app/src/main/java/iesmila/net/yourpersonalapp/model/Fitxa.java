package iesmila.net.yourpersonalapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iesmila.net.yourpersonalapp.R;

public class Fitxa {

    // Atributs de la classe
    private int id;
    private String nom;
    private boolean esHome;
    private Date data;
    //private int recursImatge; // ****
    private Bitmap imatge;


    private static List<Fitxa> fitxes;
    public static List<Fitxa> getFitxes(Context c) {
        if( fitxes==null) {
            fitxes = new ArrayList<Fitxa>();
            fitxes.add(
                    new Fitxa(1,
                            "Trump",
                            true,
                            new Date(1960, 10, 10),
                            R.drawable.trump, c)
            );
            fitxes.add(
                    new Fitxa(2,
                            "Maria",
                            false,
                            new Date(1970, 12, 04),
                            R.drawable.maria, c
                    )
            );
        }
        return fitxes;
    }


    public Fitxa(int id, String nom, boolean esHome, Date data,  int recursImatge, Context c) {
        this.id = id;
        this.nom = nom;
        this.esHome = esHome;
        this.data = data;
        //this.recursImatge = recursImatge;
        // Recuperem el bitmap que correspon a el recurs amb l'identificador especificat
        this.imatge = BitmapFactory.decodeResource(c.getResources(), recursImatge);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isEsHome() {
        return esHome;
    }

    public void setEsHome(boolean esHome) {
        this.esHome = esHome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap novaImatge) {
        this.imatge = novaImatge;
    }
}
