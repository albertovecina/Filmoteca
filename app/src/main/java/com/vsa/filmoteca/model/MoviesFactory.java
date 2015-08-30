package com.vsa.filmoteca.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seldon on 26/03/15.
 */
public class MoviesFactory {

    private static final String CLASS_EVENT = "contenttype-evento";
    public static final String CLASS_DATE = "description";

    public static List<Movie> getMoviesList(String source){
        List<Movie> moviesList=new ArrayList<Movie>();

        if(source==null || source.isEmpty())
            return moviesList;

        Document document = Jsoup.parse(source);
        Elements events = document.getElementsByClass(CLASS_EVENT);
        Elements dates = document.getElementsByClass(CLASS_DATE);

        Movie movie;

        for(int x = 0; x < events.size(); x++){
            Element event = events.get(x);
            movie = new Movie();
            Element link = event.getElementsByClass("url").first();
            String title = link.text();
            if (title.indexOf("(") > 0) {
                movie.setTitle(title.substring(0, title.indexOf("(")));
                movie.setSubtitle(title.substring(title.indexOf("(")));
            } else {
                movie.setTitle(title);
            }

            movie.setUrl(link.attr("href"));
            String date = "- " + dates.get(x).text();
            movie.setDate(date);

            moviesList.add(movie);
        }

        return moviesList;
    }

}
