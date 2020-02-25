package iesmila.net.spinnerandrecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

class AdaptadorPersonatges extends RecyclerView.Adapter<AdaptadorPersonatges.MyViewHolder>{


    public static final int NO_SELECCIONAT = -1;

    public int getPosicioSeleccionada() {
        return posicioSeleccionada;
    }

    private int posicioSeleccionada;

    private List<Personatge> mPersonatges;

    private SelectionChangedListener mListener;

    private ImageLoader imageLoader;

    public interface SelectionChangedListener {
        void onSelectionChanged(int selectedPosition, Personatge p);
    }


    public AdaptadorPersonatges(List<Personatge> personatges, SelectionChangedListener listener){
        mPersonatges = personatges;
        posicioSeleccionada = NO_SELECCIONAT;
        this.mListener = listener;

        //---------------------------------------------
        imageLoader = ImageLoader.getInstance();
    }

    @NonNull
    @Override
    public AdaptadorPersonatges.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                viewType==TIPUS_BO ? R.layout.fitxa_bo_grid: R.layout.fitxa_dolent_grid,
                null, false);
        v.setSelected(true);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonatges.MyViewHolder holder, int position) {
        Personatge p = mPersonatges.get(position);
        //holder.imv_photo.setImageResource( p.getIdRecursImatge());

        imageLoader.displayImage(p.getImatgeUrl(), holder.imv_photo);

        holder.txvId.setText( ""+p.getId());
        holder.txvId2   .setText( ""+p.getId());
        holder.txvNom.setText(p.getNom());
        holder.itemView.setSelected(position==posicioSeleccionada);
    }

    @Override
    public int getItemCount() {
        return mPersonatges.size();
    }

    /**
     * Definim el tipus de cada fila
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        Personatge p = mPersonatges.get(position);
        return p.esMalo()?TIPUS_DOLENT:TIPUS_BO;
    }

    private static final int TIPUS_BO = 0;
    private static final int TIPUS_DOLENT = 1;

    public void esborrarFilaSeleccionada() {

        if(this.posicioSeleccionada!=NO_SELECCIONAT) {

            Personatge  p = mPersonatges.remove(posicioSeleccionada);

            Personatge.getPersonatges().remove(p);

            notifyItemRemoved(posicioSeleccionada);
            this.posicioSeleccionada = NO_SELECCIONAT;
            fireSelectionChanged();
        }
    }

    public void moure(int i) {

        if(this.posicioSeleccionada!=NO_SELECCIONAT) {

            if(posicioSeleccionada+i<0 || posicioSeleccionada+i>= mPersonatges.size()) return;


            Personatge p = mPersonatges.remove(posicioSeleccionada);
            mPersonatges.add(posicioSeleccionada+i, p);
            notifyItemMoved(posicioSeleccionada, posicioSeleccionada+i);
            posicioSeleccionada += i;
            fireSelectionChanged();
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imv_photo;
        TextView txvId;
        TextView txvId2;
        TextView txvNom;

        public MyViewHolder(View itemView) {
            super(itemView);
            imv_photo = itemView.findViewById(R.id.imv_photo);
            txvId = itemView.findViewById(R.id.txvId);
            txvId2 = itemView.findViewById(R.id.txvId2);
            txvNom = itemView.findViewById(R.id.txvNom);

            // programem el click sobre el conjunt de la fila
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // si estic aquí és que algú ha clickat sobre una fila
            int posicioFilaClick = getAdapterPosition();
            int seleccionadaAnterior = posicioSeleccionada;
            if( posicioSeleccionada!=posicioFilaClick) {
                posicioSeleccionada=posicioFilaClick;
                notifyItemChanged(posicioSeleccionada);

            } else if( posicioSeleccionada==posicioFilaClick) {
              posicioSeleccionada = NO_SELECCIONAT;
            }
            notifyItemChanged(seleccionadaAnterior);
            fireSelectionChanged();
        }
    }

    private void fireSelectionChanged() {
        // Avisem a listener que la selecció ha canviat
        if(mListener!=null) {
            if(posicioSeleccionada==NO_SELECCIONAT) {
                mListener.onSelectionChanged(NO_SELECCIONAT,null);
            } else {
                mListener.onSelectionChanged(posicioSeleccionada, mPersonatges.get(posicioSeleccionada));
            }
        }
    }
}
