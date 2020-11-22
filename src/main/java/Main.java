public class Main {
    public static void main(String[] args) {
        Extractor ext = new Extractor();
        ext.getPageLinks("https://homegate.ch/en");
        ext.getArticles();
        ext.writeToFile("products.json");
    }
}
