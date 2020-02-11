package iesmila.net.a2_layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearFitxes();
    }

    private void crearFitxes() {

        LinearLayout llyMain = findViewById(R.id.llyMain);
        for(Personatge p:Personatge.getPersonatges()) {

            // Inflem una fitxa.xml per a cada personatge
            LayoutInflater inflater = LayoutInflater.from(this);

            View minFitxa = inflater.inflate(R.layout.fitxa,null,false);
            ImageView imv_photo = minFitxa.findViewById(R.id.imv_photo);
            TextView txvId = minFitxa.findViewById(R.id.txvId);
            TextView txvNom = minFitxa.findViewById(R.id.txvNom);
            imv_photo.setImageResource(p.getIdRecursImatge());
            txvId.setText(p.getId()+"");
            txvNom.setText(p.getNom());
            // afegim la fitxa al contenidor pare
            llyMain.addView(minFitxa);
        }



    }
}
