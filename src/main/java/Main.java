public class Main {
    public static void main(String[] args) {
        Extractor ext = new Extractor();
        System.out.println("OVDE SAMM");
        ext.getPageLinks("https://homegate.ch/en");
        ext.getArticles();
        ext.writeToFile("products.json");
    }
}
