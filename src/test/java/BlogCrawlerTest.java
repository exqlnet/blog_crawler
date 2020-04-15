import model.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 16:54
 * @Description:
 */
class BlogCrawlerTest {

    @Test
    void getAllArticles() {
        BlogCrawler crawler = new BlogCrawler();
        Article article = crawler.getOne("https://blog.exql.net/p/5866869/");
        System.out.println(article);

    }

    @Test
    void getOne() {
    }
}