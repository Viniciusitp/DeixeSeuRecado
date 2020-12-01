
package br.com.vinicius.deixeseurecado;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vinicius.deixeseurecado.model.Recado;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Recado> mRecadoList;

    public FeedAdapter(List<Recado> recadoList) {
        mRecadoList = recadoList;
    }


    @Override
    public void onBindViewHolder(br.com.vinicius.deixeseurecado.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        if (mRecadoList != null & mRecadoList.size() > 0) {
            return mRecadoList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Recado> recadoList) {
        mRecadoList.addAll(recadoList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (mRecadoList != null & mRecadoList.size() > 0) {
            mRecadoList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends br.com.vinicius.deixeseurecado.ViewHolder {

        @BindView(R.id.remetenteTextView)
        TextView mRemetenteTextView;

        @BindView(R.id.destinatarioTextView)
        TextView mDestinatarioTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            mRemetenteTextView.setText("");
            mDestinatarioTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            Recado mRecado = mRecadoList.get(position);

            if (mRecado.getRemetente() != null) {
                mRemetenteTextView.setText(mRecado.getRemetente());
            }

            if (mRecado.getDestinatario() != null) {
                mDestinatarioTextView.setText(mRecado.getDestinatario());
            }

            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), DetalhesActivity.class);
                intent.putExtra("key",  mRecado.getKey());
                itemView.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditarActivity.class);
                intent.putExtra("key",  mRecado.getKey());
                itemView.getContext().startActivity(intent);
                return false;
            });
        }
    }
}