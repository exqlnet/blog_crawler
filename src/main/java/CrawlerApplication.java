import model.Article;

import java.util.List;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:23
 * @Description:
 */
public class CrawlerApplication {

    public static void main(String[] args) {
        BlogCrawler crawler = new BlogCrawler();

        List<Article> result = crawler.getAllArticles();
        System.out.println("✅ 爬取完成，共" + result.size() + "条记录");

    }
}
