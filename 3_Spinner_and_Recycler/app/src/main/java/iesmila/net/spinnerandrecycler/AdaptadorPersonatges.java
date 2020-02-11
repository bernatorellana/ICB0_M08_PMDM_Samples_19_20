package iesmila.net.spinnerandrecycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class AdaptadorPersonatges extends RecyclerView.Adapter<AdaptadorPersonatges.MyViewHolder>{

    private List<Personatge> mPersonatges;

    public AdaptadorPersonatges(List<Personatge> personatges){
        mPersonatges = personatges;
    }

    @NonNull
    @Override
    public AdaptadorPersonatges.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitxa, null, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonatges.MyViewHolder holder, int position) {
        Personatge p = mPersonatges.get(position);
        holder.imv_photo.setImageResource( p.getIdRecursImatge());
        holder.txvId.setText( ""+p.getId());
        holder.txvNom.setText(p.getNom());
    }

    @Override
    public int getItemCount() {
        return mPersonatges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_photo;
        TextView txvId;
        TextView txvNom;
        public MyViewHolder(View itemView) {
            super(itemView);
            imv_photo = itemView.findViewById(R.id.imv_photo);
            txvId = itemView.findViewById(R.id.txvId);
            txvNom = itemView.findViewById(R.id.txvNom);
        }
    }
}
