package douglas.com.appfilmes.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import douglas.com.appfilmes.R;
import douglas.com.appfilmes.Utils.Functions;
import douglas.com.appfilmes.model.Filme;

/**
 * Created by douglasEller on 25/01/17.
 */

public class RecyclerFilmesAdapter extends RecyclerView.Adapter<RecyclerFilmesAdapter.ItemHolder> {

    private List<Filme> listaFilmes;
    private Context mContext;

    public RecyclerFilmesAdapter(Context context, List<Filme> itemsList) {
        this.listaFilmes = itemsList;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemHolder mh = new ItemHolder(v);

        //Filme filme = itemsList.get(i);

        //mh.titulo.setText(filme.getTitulo());
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

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != listaFilmes? listaFilmes.size() : 0);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        protected TextView titulo,descricao,duracao,ano,genero,pais;

        protected ImageView poster;


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

                    Toast.makeText(v.getContext(), titulo.getText(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }
}