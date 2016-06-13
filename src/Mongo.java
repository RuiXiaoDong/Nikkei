import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import static java.util.Arrays.asList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Mongo {
    MongoClient mongoClient;
    MongoDatabase database;
    
    public Mongo(){
        String serverName = "localhost"; //Usually it's 'localhost'
        int serverPort = 27017; //Usually it's 27017
        mongoClient = new MongoClient(serverName, serverPort);
        database = mongoClient.getDatabase("test");
    }
    
    public void insertNewsDoc(String title, String date, String content){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        database.getCollection("newsDoc").insertOne(
                new Document("title",title)
                        .append("date", date)
                        .append("content", content));
    }
    
    public void insertNewsDocs(ArrayList<News> news){
        for(int i = 0; i < news.size(); i++){
            News newsDoc = news.get(i);
            insertNewsDoc(newsDoc.title, newsDoc.date, newsDoc.content);
        }
    }
    public void insertDoc(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        try {
            database.getCollection("restaurants").insertOne(
                    new Document("address",
                            new Document()
                                    .append("street", "2 Avenue")
                                    .append("zipcode", "10075")
                                    .append("building", "1480")
                                    .append("coord", asList(-73.9557413, 40.7720266)))
                            .append("borough", "Manhattan")
                            .append("cuisine", "Italian")
                            .append("grades", asList(
                                    new Document()
                                            .append("date", format.parse("2014-10-01T00:00:00Z"))
                                            .append("grade", "A")
                                            .append("score", 11),
                                    new Document()
                                            .append("date", format.parse("2014-01-16T00:00:00Z"))
                                            .append("grade", "B")
                                            .append("score", 17)))
                            .append("name", "Vella")
                            .append("restaurant_id", "41704620"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public final void close() {
        try {
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Mongo mongo = new Mongo();
        mongo.insertNewsDoc("test1", "test2", "test3");
        mongo.close();
    }
}
