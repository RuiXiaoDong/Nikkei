import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class main {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        ArrayList<News> news = new ArrayList<News>();
        
        DataSource dataSource = new DataSource();
        news = dataSource.chunk();

        System.out.println(news.size());
        Mongo mongo = new Mongo();
        mongo.insertNewsDocs(news);
        mongo.close();
    }
}
