package douglas.com.appfilmes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import douglas.com.appfilmes.Utils.DialogMSG;
import douglas.com.appfilmes.model.Filme;


/**
 * Created by douglasEller on 26/01/17.
 */

public class FilmeDB extends SQLiteOpenHelper {

    private static final String DATABASE = "BancoFilmes";
    private static final int VERSAO = 1;
    private static final String TABELA = "tbl_filmes";
    private DialogMSG dialogMSG;

    public FilmeDB(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA + " ("
                + "id INTEGER PRIMARY KEY, "
                + "imdbID TEXT,  "
                + "titulo TEXT, "
                + "ano TEXT, "
                + "duracao TEXT, "
                + "elenco TEXT, "
                + "sinopse TEXT, "
                + "pais TEXT, "
                + "tipo TEXT, "
                + "genero TEXT, "
                + "diretor TEXT, "
                + "poster TEXT"
                + ");";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insere(Filme filme) {

            ContentValues cv = new ContentValues();
            cv.put("imdbID", filme.getImdbID());
            cv.put("titulo", filme.getTitulo());
            cv.put("ano", filme.getAno());
            cv.put("duracao", filme.getDuracao());
            cv.put("elenco", filme.getElenco());
            cv.put("sinopse", filme.getSinopse());
            cv.put("pais", filme.getPais());
            cv.put("tipo", filme.getTipo());
            cv.put("genero", filme.getGenero());
            cv.put("diretor", filme.getDiretor());
            cv.put("poster", filme.getPoster());
            getWritableDatabase().insert(TABELA, null, cv);
    }

    public void delete(String id){
        getWritableDatabase().delete(TABELA,"imdbID=?",new String[] {id});
    }

    public List<Filme> getLista() {

        List<Filme> filmes = new ArrayList<Filme>();

        String sql = "SELECT * FROM " + TABELA + " ORDER BY id DESC;";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            Filme filme = new Filme();
            filme.setImdbID(c.getString(c.getColumnIndex("imdbID")));
            filme.setTitulo(c.getString(c.getColumnIndex("titulo")));
            filme.setAno(c.getString(c.getColumnIndex("ano")));
            filme.setDuracao(c.getString(c.getColumnIndex("duracao")));
            filme.setElenco(c.getString(c.getColumnIndex("elenco")));
            filme.setSinopse(c.getString(c.getColumnIndex("sinopse")));
            filme.setPais(c.getString(c.getColumnIndex("pais")));
            filme.setTipo(c.getString(c.getColumnIndex("tipo")));
            filme.setGenero(c.getString(c.getColumnIndex("genero")));
            filme.setDiretor(c.getString(c.getColumnIndex("diretor")));
            filme.setPoster(c.getString(c.getColumnIndex("poster")));
            filmes.add(filme);
        }
        return filmes;
    }
}
