package br.com.vinicius.deixeseurecado;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.com.vinicius.deixeseurecado.model.Recado;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Recado> recadoList;

    public FeedAdapter(List<Recado> listRecado) {
        recadoList = listRecado;
    }

    @Override
    public void onBindViewHolder(br.com.vinicius.deixeseurecado.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public br.com.vinicius.deixeseurecado.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (recadoList != null & recadoList.size() > 0) {
            return recadoList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Recado> recadoList) {
        recadoList.addAll(recadoList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (recadoList != null & recadoList.size() > 0) {
            recadoList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends br.com.vinicius.deixeseurecado.ViewHolder {

        @BindView(R.id.remetenteTextView)
        TextView remetenteTextView;

        @BindView(R.id.destinatarioTextView)
        TextView destinatarioTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            remetenteTextView.setText("");
            destinatarioTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            Recado recado = recadoList.get(position);

            if (recado.getRemetente() != null) {
                remetenteTextView.setText(recado.getRemetente());
            }

            if (recado.getDestinatario() != null) {
                destinatarioTextView.setText(recado.getDestinatario());
            }

            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), DetalhesActivity.class);
                intent.putExtra("key",  recado.getKey());
                itemView.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditarActivity.class);
                intent.putExtra("key",  recado.getKey());
                itemView.getContext().startActivity(intent);
                return false;
            });
        }

    }
}