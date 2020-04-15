package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: blogCrawler
 * @Author: exqlnet
 * @CreateTime: 2020-04-15 14:24
 * @Description:
 */

public class Article {

    String title;

    String createDate;

    String digest;

    String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDigest() {
        return digest;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    List<String> tags = new ArrayList<>();

    List<String> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", createDate='" + createDate + '\'' +
                ", digest='" + digest + '\'' +
                ", tags=" + tags +
                ", categories=" + categories +
                '}';
    }
}
