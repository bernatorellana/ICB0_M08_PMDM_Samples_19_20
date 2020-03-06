package iesmila.net.spinnerandrecycler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LlistaFragment extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener, AdaptadorPersonatges.SelectionChangedListener {

    private EditText edtFiltre;
    private Spinner spnMalo;
    private RecyclerView rcyLlista;
    private Toolbar toolbar;
    private AdaptadorPersonatges adapterR;
    private ILlistaFragment_PersonatgeSelectedListener mListener;
    private List<Personatge> filtrada;
    //--------



    public LlistaFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LlistaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LlistaFragment newInstance() {
        return new LlistaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_llista, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        //-----------------------------
        edtFiltre = getView().findViewById(R.id.edtFiltre);
        spnMalo = getView().findViewById(R.id.spnMalo);
        rcyLlista = getView().findViewById(R.id.rcyLlista);
        toolbar = getView().findViewById(R.id.toolbar);
        // fer això per què si

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                llistaTipus);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_layout);

        spnMalo.setAdapter(adapter);

        //---------------------------------------------------------------------
        // Preparació del RecyclerView
        rcyLlista.setHasFixedSize(true); // té alçada fixa....accelera el render de la llista
        //rcyLlista.setLayoutManager(new LinearLayoutManager(this));
        rcyLlista.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //adapterR = new AdaptadorPersonatges(Personatge.getPersonatges());
        //rcyLlista.setAdapter(adapterR);
        filtra();



    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILlistaFragment_PersonatgeSelectedListener) {
            mListener = (ILlistaFragment_PersonatgeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ILlistaFragment_PersonatgeSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ILlistaFragment_PersonatgeSelectedListener {

        void onPersonatgeSelected(Personatge p);
    }


    private Menu mMenu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        mMenu = menu;
        inflater.inflate(R.menu.main_menu, menu);
        updateMenu();
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
        filtrada = new ArrayList<Personatge>();
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


       mListener.onPersonatgeSelected(p);
    }

    private void updateMenu() {
        int p = adapterR.getPosicioSeleccionada();
        mMenu.findItem(R.id.itmEsborrar).setVisible(p!=-1 );
        mMenu.findItem(R.id.itmDown).setVisible( p>=0 &&p<adapterR.getItemCount()-1);
        mMenu.findItem(R.id.itmUp).setVisible( p>=1 );
    }

    public void onDeleted(Personatge p) {
        int index = filtrada.indexOf(p);
        if(index!=-1) {
            filtrada.remove(index);
            adapterR.notifyItemRemoved(index);
        }
        onSelectionChanged(-1, null);//notificar que no hi ha res seleccionat
    }

    public void onSaved(Personatge p) {
        int index = filtrada.indexOf(p);
        if(index!=-1) {
            adapterR.notifyItemChanged(index);
        }
    }


}
