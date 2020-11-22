import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class Extractor {
    private HashSet<String> links; // skup linkova koji pocinju sa zadatim prefiksom
    private List<List<String>> articles;

    public Extractor() {
        links = new HashSet<String>();
        articles = new ArrayList<List<String>>();
    }

    public void getPageLinks(String URL) {
        //URL: https://homegate.ch/en
        System.out.println(URL);
        if (!links.contains(URL)) { //ako ne sadrzi URL
            System.out.println(links);
            try {
                Document document = Jsoup.connect(URL).get();
                Elements otherLinks = document.select("a[href]");

                for (Element page : otherLinks) { //prolazim kroz sve linkove na datoj stranici
                    System.out.println(page.attr("href"));
                    String link = page.attr("href");
                    String host = "https://www.homegate.ch/rent";
                    if (link.contains(host)) {
                        System.out.println("PROSLOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        if (links.add(URL)) { //dodaj URL
                            System.out.println("dodato:" + URL);
                        }
                        getPageLinks(page.attr("href")); // pozivam fju rekurzivno za svaki link na koji naidjem
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //izdvajanje svih h2 elemenata
    public void getArticles() {
        for (String x : links) {
            Document document;
            try {
                document = Jsoup.connect(x).get();
                Elements articleLinks = document.select("h2");
                System.out.println();
                System.out.println(articleLinks);
                for (Element article : articleLinks) {
                    System.out.println("article");
                    System.out.println(article);

//                    if (article.text().matches("*")) {
                    System.out.println("CLAN"+  article.attr("href")+" KRAJ CLANA");
                    //TODO ne mogu da izdvojim  clanove sa href oznakom

                    ArrayList<String> temporary = new ArrayList<String>();
                    temporary.add(article.text()); //naclov clana
                    temporary.add(article.attr("href")); //URL clana koji TODO ne mogu da izdvojim
                    articles.add(temporary);
//                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void writeToFile(String filename) {
        FileWriter writer;
        JSONObject obj = new JSONObject();
        try {
            writer = new FileWriter(filename);
            writer.write("[");
            int i=0;
            for (List<String> a : articles) {
                i++;
                obj.put("Title", a.get(0));
                writer.write(obj.toString());
                if(i < articles.size() ) {
                    writer.write(", ");
                }
            }
            writer.write("]");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

