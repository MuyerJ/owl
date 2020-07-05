package com.owl.crawldata;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import util.crawl.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: <br/>
 * date: 2020/7/5 21:46<br/>
 *
 * @author MuyerJ<br />
 */
public class CrawlDataTest {
    public static void main(String[] args) {
        Request request = new Request();
        //获取页面详情
        String content = request.get("https://www.cnblogs.com/").getContent();
        //解析页面
        Elements elements = Jsoup.parse(content).select("div#post_list div.post_item div.post_item_body");
        List<Map<String,String>> list = new ArrayList<>();
        list = elements.stream().map(item->{
            Map<String,String> map = new HashMap<>();
            map.put("url",item.select("h3 a").attr("href"));
            map.put("title",item.select("h3 a").text());
            map.put("sumary",item.select("p.post_item_summary").text());
            return map;
        }).collect(Collectors.toList());
        //打印结果
        list.forEach(map->{
            System.out.println("========");
            System.out.println("url"+map.get("url"));
            System.out.println("title"+map.get("title"));
            System.out.println("sumary"+map.get("sumary"));
            System.out.println("========");
        });
    }
}
