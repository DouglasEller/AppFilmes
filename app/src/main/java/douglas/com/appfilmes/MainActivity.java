package douglas.com.appfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import douglas.com.appfilmes.Adapter.RecyclerFilmesAdapter;
import douglas.com.appfilmes.DataBase.FilmeDB;
import douglas.com.appfilmes.Utils.DialogMSG;
import douglas.com.appfilmes.model.Filme;
import douglas.com.appfilmes.services.FilmeRequest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppFilmes";
    private List<Filme> filmes = new ArrayList<>();
    private FilmeDB filmeDB ;

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toast toast;
    private DialogMSG dialogMSG = new DialogMSG();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toast = Toast.makeText(this,"",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerFilmes);

        mRecycler.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        filmeDB = new FilmeDB(this);
        filmes = filmeDB.getLista();

    }

    @Override
    protected void onResume() {
        super.onResume();

        CarregarCards();
    }

    private void CarregarCards() {
        filmeDB = new FilmeDB(this);
        filmes = filmeDB.getLista();
        filmeDB.close();

        mAdapter = new RecyclerFilmesAdapter(this, filmes);
        mRecycler.setAdapter(null);
        mRecycler.setAdapter(mAdapter);
    }

    public void CarregarFilmePesquisado(String nomeFilme) {
        FilmeRequest requestFilme = new FilmeRequest();
        String resultJSON = requestFilme.Request(this,"Buscando...",nomeFilme);

        Gson gson = new Gson();
        Filme filme = gson.fromJson(resultJSON, Filme.class);

        if(filme.isResposta()){

            FilmeDB filmeDB = new FilmeDB(this);

            filmeDB.insere(filme, filmes);
            filmeDB.close();

            CarregarCards();
        }else{

            Intent i = new Intent();
            dialogMSG.msgDialog = filme.getError();
            i.setClass(this, DialogMSG.class);
            startActivity(i);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Carrega o xml do menu.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //Pega o Componente.
        MenuItem searchItem = menu.findItem(R.id.search);

        //Define um texto de ajuda:
        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG,"Texto digitado on submit = " + query);
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
