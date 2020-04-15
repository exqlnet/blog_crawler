import model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HttpResponse;
import util.HttpUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:24
 * @Description:
 */
public class BlogCrawler {

    private Queue<String> taskList = new ArrayDeque<>();

    private String domain = "https://blog.exql.net";

    volatile HashMap<Long, Boolean> status = new HashMap<>();

    public List<Article> getAllArticles() {

        // 发现文章URL
        String baseURL = domain + "/page/{}/";
        int page = 1;
        while (true) {
            HttpResponse response;
            if (page == 1) {
                response = HttpUtil.doGet(domain);
            } else {
                response = HttpUtil.doGet(baseURL.replace("{}", Integer.toString(page)));
            }

            if (response == null) {
                break;
            }
            Document doc = Jsoup.parse(response.content);
            Elements els = doc.select("#board > div > div > div > div > div > a:nth-child(1)");
            for (Element el : els) {
                if (el.text().trim().equals("")) continue;
                taskList.add(el.attr("href"));
                System.out.println("😂 发现：" + el.text());
            }
            page ++;
        }



        ExecutorService pool = Executors.newFixedThreadPool(6);

        List<Article> result = new ArrayList<>();

        System.out.println("🧾 任务数：" + taskList.size());
        System.out.println("🐛 爬虫启动");


        while (taskList.size() > 0) {
            String taskURL = taskList.poll();
            pool.execute(() -> {
                long t = System.currentTimeMillis();
                String realURL = domain + taskURL;
                status.put(Thread.currentThread().getId(), true);
                Article article = getOne(realURL);
                result.add(article);
                System.out.println("👴 已爬：" + taskURL
                        + "，剩余：" + taskList.size()
                        + "耗时：" + (System.currentTimeMillis() - t) + "ms"
                        + "  " + article);
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Article getOne(String url) {
        HttpResponse response = HttpUtil.doGet(url);
        assert response != null;
        Document doc = Jsoup.parse(response.content);

        Article article = new Article();
        article.setContent(doc.select(".markdown-body").first().html());
        article.setTitle(doc.title().split(" -")[0]);
        article.setDigest(article.getContent().substring(0, 100));

//        String tag = doc.select(".icon-inbox").first().nextElementSibling().text();
//
//        article.setTags(Collections.singletonList(tag));
        return article;
    }
}
