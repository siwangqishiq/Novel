package xyz.panyi.novel.model;

/**
 * 小说
 */
public class Novel {
    private long id;
    private String name;//小说名称
    private String author;//作者
    private String novelDesc;//简介
    private String coverUrl;//封面图片
    private String tag;//标签
    private int chapterCount;//章节数量

    private String nid;//抓取网站对应的唯一标识
    private String originUrl;

    private long updateTime;

    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + novelDesc + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", tag='" + tag + '\'' +
                ", chapterCount=" + chapterCount +
                ", nid='" + nid + '\'' +
                ", originUrl='" + originUrl + '\'' +
                '}';
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNovelDesc() {
        return novelDesc;
    }

    public void setNovelDesc(String novelDesc) {
        this.novelDesc = novelDesc;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}//end class
