import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
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

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:24
 * @Description:
 */
public class BlogCrawler {

    Queue<String> taskList = new ArrayDeque<>();

    final static Logger logger = LoggerFactory.getLogger(BlogCrawler.class);

    volatile HashMap<Long, Boolean> status = new HashMap<>();

    public List<Article> getAllArticles() {

        // 发现文章URL
        String baseURL = "https://blog.exql.net/page/{}/";
        int page = 1;
        while (true) {
            HttpResponse response;
            if (page == 1) {
                response = HttpUtil.doGet("https://blog.exql.net/");
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


        for (int i = 0; i < 6; i++) {
            pool.execute(() -> {
                status.put(Thread.currentThread().getId(), true);
                while (true) {
                    long t = System.currentTimeMillis();
                    String taskURL = taskList.poll();
                    String realURL = "https://blog.exql.net" + taskURL;
                    if (taskURL == null) {
                        status.put(Thread.currentThread().getId(), false);
                        break;
                    } else {
                        status.put(Thread.currentThread().getId(), true);
                        Article article = getOne(realURL);
                        result.add(article);
                        System.out.println("👴 已爬：" + taskURL
                                + "，剩余：" + taskList.size()
                                + "耗时：" + (System.currentTimeMillis() - t) + "ms"
                                + "  " + article);
                    }
                }
            });
        }


        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 轮询，与子线程同步
        boolean flag;
        try {
            while (true) {
                flag = true;
                for (long key: status.keySet()) {
                    if (status.get(key) == Boolean.TRUE) {
                        flag = false;
                        break;
                    }
                }

                if (flag) break;

                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdownNow();
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
