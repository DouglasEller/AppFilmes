package douglas.com.appfilmes.services;

/**
 * Created by douglasEller on 26/01/17.
 */

public class ServicesURLs {
    private String URLPESQUISAFILME;

    public void setURLPESQUISAFILME(String nomeFilme) {

        String url = "http://www.omdbapi.com/?t=" + nomeFilme +"&y=&plot=full&r=json";
        url = url.replaceAll(" " ,"%20");
        this.URLPESQUISAFILME = url;

    }

    public String getURLPESQUISAFILME() {
        return URLPESQUISAFILME;
    }

}
