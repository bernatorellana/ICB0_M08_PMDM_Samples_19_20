package iesmila.net.yourpersonalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

import iesmila.net.yourpersonalapp.model.Fitxa;

public class MainActivity extends AppCompatActivity {

    // ------------------------------------
    //  Una variable per a cada control de l'Activity
    // ------------------------------------
    EditText        edtNom;
    RadioGroup      rgoSexe;
    RadioButton     rdoHome;
    RadioButton     rdoDona;
    DatePicker      dtpData;
    Button          btnNext;
    Button          btnPrev;
    ImageView       imgFoto;
    // ------------------------------------

    Fitxa fitxaActual;
    int indexFitxaActual=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------------------------
        edtNom = findViewById(R.id.edtNom);
        rgoSexe = findViewById(R.id.rgoSexe);
        rdoHome = findViewById(R.id.rdoHome);
        rdoDona = findViewById(R.id.rdoDona);
        dtpData = findViewById(R.id.dtpData);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        imgFoto = findViewById(R.id.imgFoto);
        //---------------------------------------
        // Prenem la primera fitxa de la llista
        //fitxaActual = Fitxa.getFitxes().get(1);
        indexFitxaActual = 0;
        mostrarFitxaActual();
        //----------------------------------------
        // Programar els botons
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexFitxaActual =  (indexFitxaActual+1)%Fitxa.getFitxes().size();
                mostrarFitxaActual();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexFitxaActual--;
                if(indexFitxaActual<0) indexFitxaActual = Fitxa.getFitxes().size()-1;
                mostrarFitxaActual();
            }
        });


        //----------------------------------------------
        // Events del EditText
        edtNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
               String nom = edtNom.getText().toString();
                Fitxa f = Fitxa.getFitxes().get(indexFitxaActual);
               f.setNom(nom);
            }
        });
        // --------------------------------------------------
        // Events del RadioButton
        rgoSexe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fitxa f = Fitxa.getFitxes().get(indexFitxaActual);
                f.setEsHome(  checkedId == R.id.rdoHome );
            }
        });
        //----------------------------------------------------
        Fitxa f = Fitxa.getFitxes().get(indexFitxaActual);
        dtpData.init(f.getData().getYear(), f.getData().getMonth()-1, f.getData().getDay(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Fitxa f = Fitxa.getFitxes().get(indexFitxaActual);
                        f.setData( new Date(year, monthOfYear+1, dayOfMonth ) );
                    }
                }
        );



        /*dtpData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Fitxa f = Fitxa.getFitxes().get(indexFitxaActual);
                f.setData( new Date(year, monthOfYear, dayOfMonth ) );
            }
        });*/


    }

    private void mostrarFitxaActual() {
        // Verifiquem abans que l'index de la fitxa no surti de mare.
        if(indexFitxaActual>=0 && indexFitxaActual<Fitxa.getFitxes().size()) {

            fitxaActual = Fitxa.getFitxes().get(indexFitxaActual);

            edtNom.setText(fitxaActual.getNom());
            if (fitxaActual.isEsHome()) {
                rgoSexe.check(R.id.rdoHome);
            } else {
                rgoSexe.check(R.id.rdoDona);
            }
            imgFoto.setImageResource(fitxaActual.getRecursImatge());

            dtpData.updateDate(
                    fitxaActual.getData().getYear(),
                    fitxaActual.getData().getMonth() - 1,
                    fitxaActual.getData().getDate()
            );
        }
    }
}
