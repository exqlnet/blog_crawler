package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:46
 * @Description:
 */
public class HttpUtil {

    public static HttpResponse doGet(String url) {
        try {
            URL realURL = new URL(url);
            URLConnection connection = realURL.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while (( line = reader.readLine()) != null) {
                result.append(line);
            }
            return new HttpResponse(connection.getHeaderFields(), result.toString());

        } catch (IOException e) {
            return null;
        }
    }
}
