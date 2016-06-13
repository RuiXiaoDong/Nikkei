import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DataSource {
    String newDelimiter;
    String contentBeginning;
    
    public DataSource(){
        newDelimiter = "印刷対象にする";
        contentBeginning = "その他の書誌情報を表示";
    }
    public ArrayList<News> chunk() {
        ArrayList<News> news = new ArrayList<News>();
        try {
            File fileDir = new File("data/1990/Jan/1-9391.txt");

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileDir), "UTF8"));

            String currentLine = "";
            String previousLine = "";
            
            boolean isNewsBeginning = true;
            boolean isNewsContentBeginning = false;
            boolean isNewsContentMetaData = true; //skip the first line of the content since it is the meta data such as content size
            String title = "";
            String date = "";
            String newsContent = "";
            
            while ((currentLine = in.readLine()) != null) {
                if(isNewsContentBeginning == false) {
                    if (currentLine.contains(newDelimiter)){
                        isNewsBeginning = true;
                    }
                    else if (currentLine.contains(contentBeginning)){
                        isNewsContentBeginning = true;
                    }
                    else if (currentLine.matches("((?:19|20)\\d\\d)/(0?[1-9]|1[012])/([12][0-9]|3[01]|0?[1-9]).*")) {
                        if (isNewsBeginning == true) {
                            date = currentLine.split("\\s+")[0];
                            title = previousLine;
                        }
                    }
                }
                else {
                    if (isNewsContentMetaData) {
                        isNewsContentMetaData = false;
                        continue;
                    }
                    else if (currentLine.contains(newDelimiter)){
                        //System.out.println("---" + title + "---");
                        //System.out.println("***" + date + "***");
                        //System.out.println("!!!" + newsContent + "!!!");
                        
                        news.add(new News(title,date,newsContent));
                        
                        isNewsContentBeginning = false;
                        isNewsBeginning = false;
                        isNewsContentMetaData = true;
                        title = "";
                        date = "";
                        newsContent = "";
                    }
                    else {
                        newsContent = newsContent + currentLine;
                    }
                }
                previousLine = currentLine;
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return news;
    }
    public static void main(String[] args){
        DataSource dataSource = new DataSource();
        dataSource.chunk();
    }
}
