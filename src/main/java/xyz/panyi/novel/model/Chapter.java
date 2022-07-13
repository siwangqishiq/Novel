package xyz.panyi.novel.model;

/**
 * 章节
 */
public class Chapter {
    private long id;
    private long novelId;//小说id

    private int index;
    private int totalIndex;

    private String originUrl;
    private String title;
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotalIndex() {
        return totalIndex;
    }

    public void setTotalIndex(int totalIndex) {
        this.totalIndex = totalIndex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNovelId() {
        return novelId;
    }

    public void setNovelId(long novelId) {
        this.novelId = novelId;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
