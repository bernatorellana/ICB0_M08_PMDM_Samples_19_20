package iesmila.net.yourpersonalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import iesmila.net.yourpersonalapp.model.Fitxa;

public class LlistaFitxes extends AppCompatActivity implements View.OnClickListener {


    LinearLayout llyLlista;
    int colorPerDefecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_fitxes);

        // Anem a buscar el color per defecte
        colorPerDefecte = this.getResources().getColor(R.color.colorBoto);

        // Cerquem les referències dels elements de la UI
        llyLlista = findViewById(R.id.llyLlista);

        //-----------------------------------------
        // Crear dinàmicament tants botons com fitxes tinguem
        List<Fitxa> fitxes  = Fitxa.getFitxes(this);
        for(Fitxa f:fitxes) {
            Button b = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            b.setBackgroundColor(colorPerDefecte);
            b.setLayoutParams(params);
            b.setText(f.getNom());
            llyLlista.addView(b);
            b.setOnClickListener(this);
            b.setTag(f.getId());
        }

    }


    @Override
    public void onClick(View v) {
        Button botoApretat = (Button)v;
        int id = (int)botoApretat.getTag();

        netejaBotons();

        // Intent: anem a obrir la fitxa amb identificador id
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(MainActivity.PARAM_ID, id);
        //startActivity(i);
        startActivityForResult(i,1);
    }

    private void netejaBotons() {
        for(int i=0;i<llyLlista.getChildCount();i++){
            View v = llyLlista.getChildAt(i);
            if(v instanceof Button) {
                v.setBackgroundColor(colorPerDefecte);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra(MainActivity.PARAM_ID, -1);
            Button b = llyLlista.findViewWithTag(id);
            b.setBackgroundColor(Color.GREEN);
        }
    }
}
