package douglas.com.appfilmes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by douglasEller on 25/01/17.
 */

public class Filme implements Parcelable {

    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Title")
    private String titulo;
    @SerializedName("Year")
    private String ano;
    @SerializedName("Runtime")
    private String duracao;
    @SerializedName("Actors")
    private String elenco;
    @SerializedName("Plot")
    private String sinopse;
    @SerializedName("Country")
    private String pais;
    @SerializedName("Type")
    private String tipo;
    @SerializedName("Genre")
    private String genero;
    @SerializedName("Director")
    private String diretor;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Response")
    private boolean resposta;
    @SerializedName("Error")
    private String error;

    public Filme() {}

    public Filme(Parcel source){
        imdbID = source.readString();
        titulo = source.readString();
        ano = source.readString();
        duracao = source.readString();
        elenco = source.readString();
        sinopse = source.readString();
        pais = source.readString();
        tipo = source.readString();
        genero = source.readString();
        diretor = source.readString();
        poster = source.readString();
    }

    public static final Parcelable.Creator<Filme>
            CREATOR = new Parcelable.Creator<Filme>() {

        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imdbID);
        parcel.writeString(titulo);
        parcel.writeString(ano);
        parcel.writeString(duracao);
        parcel.writeString(elenco);
        parcel.writeString(sinopse);
        parcel.writeString(pais);
        parcel.writeString(tipo);
        parcel.writeString(genero);
        parcel.writeString(diretor);
        parcel.writeString(poster);

    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isResposta() {
        return resposta;
    }

    public String getError() {
        return error;
    }

}
