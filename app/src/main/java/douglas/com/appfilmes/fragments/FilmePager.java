package douglas.com.appfilmes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import douglas.com.appfilmes.R;
import douglas.com.appfilmes.Utils.Functions;
import douglas.com.appfilmes.model.Filme;

/**
 * Created by douglasEller on 30/01/17.
 */

public class FilmePager extends Fragment {

    private static final String ARG_OBJECT = "object_filme";

    private TextView  sinopse, ano, duracao, elenco, pais, tipo, genero, diretor;
    private ImageView poster;

    public FilmePager() {
    }

    public static FilmePager newInstance(Filme filme) {
        FilmePager fragment = new FilmePager();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OBJECT, filme);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_description_content, container, false);
        Bundle b = getArguments();
        Filme filme = b.getParcelable(ARG_OBJECT);

        sinopse = (TextView) rootView.findViewById(R.id.txtDescricaoFull);
        ano = (TextView) rootView.findViewById(R.id.txtAnoFull);
        duracao = (TextView) rootView.findViewById(R.id.txtDuracaoFull);
        elenco = (TextView) rootView.findViewById(R.id.txtElencoFull);
        pais = (TextView) rootView.findViewById(R.id.txtPaisFull);
        tipo = (TextView) rootView.findViewById(R.id.txtTipoFull);
        genero = (TextView) rootView.findViewById(R.id.txtGeneroFull);
        diretor = (TextView) rootView.findViewById(R.id.txtDiretorFull);

        poster = (ImageView) rootView.findViewById(R.id.imgPosterFull);

        sinopse.setText(filme.getSinopse());
        ano.setText(filme.getAno().isEmpty() ? filme.getAno() : "  /  " + filme.getAno() );
        duracao.setText(filme.getDuracao() );
        elenco.setText(filme.getElenco());
        pais.setText(filme.getPais());
        tipo.setText(filme.getTipo());
        genero.setText(filme.getGenero());
        diretor.setText(filme.getDiretor());
        poster.setImageBitmap(Functions.decodeToBase64(filme.getPoster()));

        return rootView;
    }
}