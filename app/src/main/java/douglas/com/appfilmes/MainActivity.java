package douglas.com.appfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import douglas.com.appfilmes.Adapter.ItemTouchHelperAdapter;
import douglas.com.appfilmes.Adapter.ItemTouchHelperCallback;
import douglas.com.appfilmes.Adapter.RecyclerFilmesAdapter;
import douglas.com.appfilmes.DataBase.FilmeDB;
import douglas.com.appfilmes.Utils.DialogMSG;
import douglas.com.appfilmes.Utils.Functions;
import douglas.com.appfilmes.model.Filme;
import douglas.com.appfilmes.services.FilmeRequest;

public class MainActivity extends AppCompatActivity {

    private List<Filme> filmes = new ArrayList<>();
    private FilmeDB filmeDB;

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DialogMSG dialogMSG = new DialogMSG();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerFilmes);

        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        filmeDB = new FilmeDB(this);
        filmes = filmeDB.getLista();

        mAdapter = new RecyclerFilmesAdapter(MainActivity.this, filmes);
        mRecycler.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback((ItemTouchHelperAdapter) mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecycler);
    }

    public void CarregarFilmePesquisado(String nomeFilme) {
        FilmeRequest requestFilme = new FilmeRequest();
        String resultJSON = requestFilme.Request(this, "Buscando...", nomeFilme);

        Gson gson = new Gson();
        Filme filme = gson.fromJson(resultJSON, Filme.class);

        if (filme.isResposta()) {
            boolean filmeRepetido = false;
            for (Filme filmeAux : filmes)
                if (filme.getImdbID().equalsIgnoreCase(filmeAux.getImdbID()))
                    filmeRepetido = true;

            if (filmeRepetido) {
                Intent i = new Intent();
                dialogMSG.msgDialog = "Este filme já foi adicionado à lista.";
                i.setClass(this, DialogMSG.class);
                startActivity(i);
            } else {
                filme.setPoster(Functions.encodeToBase64(filme.getPoster()));
                filmeDB.insere(filme);
                filmeDB.close();

                filmes.add(0, filme);
                mAdapter.notifyDataSetChanged();
            }
        } else {

            Intent i = new Intent();
            dialogMSG.msgDialog = filme.getError();
            i.setClass(this, DialogMSG.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                CarregarFilmePesquisado(query);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}
