package iesmila.net.spinnerandrecycler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallFragment extends Fragment {

    //----------------------------------
    EditText edtId;
    EditText edtNom;
    EditText edtURL;
    ImageView imvPhoto;
    Button btnDelete;
    Button btnSave;
    //----------------------------------


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Personatge";

    private Personatge p;

    private PersonatgeChangedEventListener mListener;

    public DetallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param p El personatge
     * @return A new instance of fragment DetallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallFragment newInstance(Personatge p) {
        Log.d("FRAG",  "newInstace"+p);
        DetallFragment fragment = new DetallFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FRAG", "("+this+") : onCreate"+getArguments());
        if (getArguments() != null) {
            p = (Personatge) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FRAG", "("+this+") : onCreateView"+getArguments());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detall, container, false);
        edtId = v.findViewById(R.id.edtId);
        edtNom = v.findViewById(R.id.edtNom);
        edtURL = v.findViewById(R.id.edtURL);
        imvPhoto = v.findViewById(R.id.imvPhoto);
        btnSave = v.findViewById(R.id.btnSave);
        btnDelete = v.findViewById(R.id.btnDelete);
        //--------------------------------------
        edtId.setText(""+p.getId());
        edtNom.setText(p.getNom());
        edtURL.setText(p.getImatgeUrl());
        ImageLoader.getInstance().displayImage(p.getImatgeUrl(), imvPhoto);
        //--------------------------------------------
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Personatge pCur : Personatge.getPersonatges()) {
                    if(pCur.getId()==p.getId()) {
                        pCur.setNom(edtNom.getText().toString());
                        pCur.setUrlImatge(edtURL.getText().toString());
                        break;
                    }
                }
                mListener.onSaved(p);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Personatge.getPersonatges().remove(p)) {
                    mListener.onDeleted(p);
                }
            }
        });


        return v;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PersonatgeChangedEventListener) {
            mListener = (PersonatgeChangedEventListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface PersonatgeChangedEventListener {
        void onDeleted(Personatge p);
        void onSaved(Personatge p);
    }
}
