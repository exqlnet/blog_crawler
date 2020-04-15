package util;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:47
 * @Description:
 */
public class HttpResponse {

    public Map<String, List<String>> headers;

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer statusCode = 200;

    public String content;

    public HttpResponse (Map<String, List<String>> headers, String content) {
        this.headers = headers;
        this.content = content;
    }
}
