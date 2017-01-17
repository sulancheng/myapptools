package news.heima.itcast.autoroll;

/**
 * Created by Administrator on 2016/5/4.
 * <p/>
 * 轮播的数据：
 * 图片的url地址
 * 标题
 */
public class RollItem {

    public String title;

    public String url;

    public RollItem(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
