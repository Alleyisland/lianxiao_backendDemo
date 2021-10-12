package com.lianxiao.demo.simpleserver.task;

import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.utils.FastJsonUtils;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import com.lianxiao.demo.simpleserver.utils.RedisUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@EnableAsync
public class ScheduledTask {

    private static final String WEIBO_HOT_RANK="https://s.weibo.com/top/summary";
    private static final String WEIBO_HOT_ITEM_CLASS="td-02";
    private static final String WEIBO_DETAIL_PREFIX="https://s.weibo.com";
    private static final String WEIBO_HOT_KEY_PREFIX = "weibo_hot_";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    @Autowired
    private PostService postService;


    /*@Async
    @Scheduled(cron="0/60 * * * * ? ")
    public void crawlNetEase() {

    }*/

    @Scheduled(cron="0 55 11 * * ?")
    public void crawlWeiBo() throws InterruptedException, IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
        System.out.println("爬取微博开始" + df.format(new Date()));
        Document doc = Jsoup.connect(WEIBO_HOT_RANK).header("User-Agent","Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)").get();

        Elements elements=doc.getElementsByClass(WEIBO_HOT_ITEM_CLASS);
        String title;
        String href;
        String abstr;
        List<Post> hotPosts=new ArrayList<>();

        for (Element element : elements) {
            Thread.sleep(1000);
            Elements titles = element.getElementsByTag("a");
            if(titles!=null)
                title=titles.get(0).text();
            else
                title=null;
            String point = element.getElementsByTag("span").text();
            if(point.equals(""))
                point="-1";
            Elements hrefs = element.getElementsByTag("a");
            if(hrefs!=null) {
                href = hrefs.get(0).attr("href_to");
                if(href==null|| href.equals(""))
                    href = hrefs.get(0).attr("href");
                Elements topic_card = Jsoup.connect(WEIBO_DETAIL_PREFIX + href)
                        .get()
                        .getElementsByClass("card-topic-lead");
                if (topic_card != null&&topic_card.size()!=0) {
                    abstr=topic_card.get(0)
                            .getElementsByTag("p")
                            .get(0)
                            .text();
                }
                else
                    abstr = null;
            }
            else{
                href=null;
                abstr=null;
            }
            if(title!=null&&href!=null&&abstr!=null){
                Post hotPost=new Post(idGeneratorUtils.nextId(),0,"-1","hww",new Date(),title,abstr);
                System.out.println(hotPost);
                hotPosts.add(hotPost);
            }
        }
        //  添加新榜 设置过期时间
        Date now=new Date();
        int month=now.getMonth()+1;
        int day=now.getDate();
        String key=WEIBO_HOT_KEY_PREFIX+month+"_"+day;
        Date expireTime=getNextExpire();
        redisUtils.listAddAll(key, hotPosts);
        redisUtils.expire(key,expireTime);

        //  旧榜存入es
        Date yesterday = getLastDate();
        int yesterday_month=yesterday.getMonth()+1;
        int yesterday_day=yesterday.getDate();
        String key_yesterday=WEIBO_HOT_KEY_PREFIX+yesterday_month+"_"+yesterday_day;
        List<Post> oldPosts=redisUtils.fetchOldList(key_yesterday);
        postService.saveAll(oldPosts);

        System.out.println("over");
    }

    private static Date getNextExpire(){
        Calendar expire=new GregorianCalendar();
        expire.setTime(new Date());
        expire.add(Calendar.DATE, 1);
        expire.set(Calendar.HOUR_OF_DAY, 12);
        expire.set(Calendar.MINUTE, 0);
        expire.set(Calendar.SECOND, 0);
        System.out.println(expire.getTime());
        return expire.getTime();
    }

    private static Date getLastDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        return calendar.getTime();
    }
}
