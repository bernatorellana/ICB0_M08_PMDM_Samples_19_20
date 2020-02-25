package iesmila.net.spinnerandrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener, AdaptadorPersonatges.SelectionChangedListener {

    private EditText edtFiltre;
    private Spinner spnMalo;
    private RecyclerView rcyLlista;
    private Toolbar toolbar;
    private AdaptadorPersonatges adapterR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        inicialitzaUniversalImageLoader();

        setContentView(R.layout.activity_main);

        //-----------------------------
        edtFiltre = findViewById(R.id.edtFiltre);
        spnMalo = findViewById(R.id.spnMalo);
        rcyLlista = findViewById(R.id.rcyLlista);
        toolbar = findViewById(R.id.toolbar);
        // fer això per què si
        setSupportActionBar(toolbar);
        //-----------------------------
        // Programem el text changed del filtre
        edtFiltre.addTextChangedListener(this);
        // Programem l'spinner
        spnMalo.setOnItemSelectedListener(this);


        List<String> llistaTipus = Arrays.asList(
                "Tots", // Mario was here
                getC(R.string.spn_bons), // "Bons"
                getC(R.string.spn_dolents) //"Dolents"
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                llistaTipus);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_layout);

        spnMalo.setAdapter(adapter);

        //---------------------------------------------------------------------
        // Preparació del RecyclerView
        rcyLlista.setHasFixedSize(true); // té alçada fixa....accelera el render de la llista
        //rcyLlista.setLayoutManager(new LinearLayoutManager(this));
        rcyLlista.setLayoutManager(new GridLayoutManager(this, 2));

        //adapterR = new AdaptadorPersonatges(Personatge.getPersonatges());
        //rcyLlista.setAdapter(adapterR);
        filtra();

    }

    private void inicialitzaUniversalImageLoader() {


        DisplayImageOptions options = new DisplayImageOptions.Builder().
                showImageOnLoading(R.drawable.loading).build();


        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).
                defaultDisplayImageOptions(options).
                build();
        ImageLoader.getInstance().init(config);
    }

    private Menu mMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.itmEsborrar:
                adapterR.esborrarFilaSeleccionada();
                return true;
            case R.id.itmUp:
                adapterR.moure(-1);
                return true;
            case R.id.itmDown:
                adapterR.moure(+1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private String getC(int cadenaId) {
        return this.getResources().getString(cadenaId);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {

        filtra();
    }

    private void filtra() {
        List<Personatge> personatges = Personatge.getPersonatges();
        List<Personatge> filtrada = new ArrayList<Personatge>();
        boolean filtraPerTipus = false;
        boolean bonsSelected = false;
        if(spnMalo.getSelectedItemPosition()==1) {
            filtraPerTipus = true;
            bonsSelected = true;
        } else if(spnMalo.getSelectedItemPosition()==2) {
            filtraPerTipus = true;
            bonsSelected = false;
        }

        //personatges.stream().filter( p -> p.getNom().contains(edtFiltre.getText())).collect(Collectors.toCollection());
        for(Personatge p:personatges) {
            if(p.getNom().toLowerCase().contains(edtFiltre.getText().toString().toLowerCase())){
                if(!filtraPerTipus || (p.esMalo()!=bonsSelected)) {
                    filtrada.add(p);
                }
            }
        }
        adapterR = new AdaptadorPersonatges(filtrada, this);
        rcyLlista.setAdapter(adapterR);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filtra();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        filtra();
    }

    @Override
    public void onSelectionChanged(int selectedPosition, Personatge p) {
        updateMenu();
    }

    private void updateMenu() {
        int p = adapterR.getPosicioSeleccionada();
        mMenu.findItem(R.id.itmEsborrar).setVisible(p!=-1 );
        mMenu.findItem(R.id.itmDown).setVisible( p>=0 &&p<adapterR.getItemCount()-1);
        mMenu.findItem(R.id.itmUp).setVisible( p>=1 );
    }
}



