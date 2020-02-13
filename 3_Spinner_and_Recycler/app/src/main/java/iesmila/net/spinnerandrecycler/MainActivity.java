package iesmila.net.spinnerandrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtFiltre;
    private Spinner spnMalo;
    private RecyclerView rcyLlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----------------------------
        edtFiltre = findViewById(R.id.edtFiltre);
        spnMalo = findViewById(R.id.spnMalo);
        rcyLlista = findViewById(R.id.rcyLlista);
        //-----------------------------

        List<String> llistaTipus = Arrays.asList(
                //"", // Mario was here
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
        rcyLlista.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorPersonatges adapterR = new AdaptadorPersonatges(Personatge.getPersonatges());
        rcyLlista.setAdapter(adapterR);


    }

    private String getC(int cadenaId) {
        return this.getResources().getString(cadenaId);
    }
}



