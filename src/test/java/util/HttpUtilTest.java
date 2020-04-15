package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 18:17
 * @Description:
 */
class HttpUtilTest {

    @Test
    void testFetchTitle() {
        Document doc = Jsoup.parse(HttpUtil.doGet("https://blog.exql.net/").content);
        Elements els = doc.select("#board > div > div > div > div > div > a:nth-child(1)");
        for (Element el : els) {
            System.out.println(el.attr("href"));
        }
    }

}