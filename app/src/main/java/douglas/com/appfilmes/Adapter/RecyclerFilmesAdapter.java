package douglas.com.appfilmes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import douglas.com.appfilmes.DataBase.FilmeDB;
import douglas.com.appfilmes.FullDescription;
import douglas.com.appfilmes.R;
import douglas.com.appfilmes.Utils.Functions;
import douglas.com.appfilmes.model.Filme;

/**
 * Created by douglasEller on 25/01/17.
 */

public class RecyclerFilmesAdapter extends RecyclerView.Adapter<RecyclerFilmesAdapter.ItemHolder>
        implements ItemTouchHelperAdapter {

    private List<Filme> listaFilmes;
    private static Context mContext;

    public RecyclerFilmesAdapter(Context context, List<Filme> itemsList) {
        this.listaFilmes = itemsList;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemHolder mh = new ItemHolder(v);
        v.setTag(mh);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int i) {

        Filme filme = listaFilmes.get(i);

        holder.titulo.setText(filme.getTitulo());
        holder.descricao.setText(filme.getSinopse());
        holder.duracao.setText(filme.getDuracao());
        holder.ano.setText(filme.getAno());
        holder.genero.setText(filme.getGenero());
        holder.pais.setText(filme.getPais());
        holder.poster.setImageBitmap(Functions.decodeToBase64(filme.getPoster()));
        holder.position = i;
        holder.setFilmesHolder(listaFilmes);
    }

    @Override
    public int getItemCount() {
        return (null != listaFilmes ? listaFilmes.size() : 0);
    }

    @Override
    public void onItemDismiss(final int position) {

        FilmeDB filmeDB = new FilmeDB(mContext);
        filmeDB.delete(listaFilmes.get(position).getImdbID());
        filmeDB.close();

        listaFilmes.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        protected TextView titulo, descricao, duracao, ano, genero, pais;
        protected ImageView poster;
        protected int position;
        protected static List<Filme> filmesHolder;

        public ItemHolder(View view) {
            super(view);

            this.poster = (ImageView) view.findViewById(R.id.imgPoster);
            this.titulo = (TextView) view.findViewById(R.id.txtTitulo);
            this.descricao = (TextView) view.findViewById(R.id.txtDescricao);
            this.duracao = (TextView) view.findViewById(R.id.txtDuracao);
            this.ano = (TextView) view.findViewById(R.id.txtAno);
            this.genero = (TextView) view.findViewById(R.id.txtGenero);
            this.pais = (TextView) view.findViewById(R.id.txtPais);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemHolder holderAux = (ItemHolder) v.getTag();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("filmeEscolhido", holderAux.position);
                    Intent i = new Intent();
                    i.putExtras(mBundle);
                    i.setClass(mContext, FullDescription.class);
                    mContext.startActivity(i);
                }
            });
        }

        public static List<Filme> getFilmesHolder() {
            return filmesHolder;
        }

        public void setFilmesHolder(List<Filme> filmesHolder) {
            this.filmesHolder = filmesHolder;
        }
    }

}