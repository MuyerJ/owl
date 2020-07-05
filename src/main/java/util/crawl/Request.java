package util.crawl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import util.StringUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.owl.constant.Constants.HTTP_STATE_CODE_OK;

public class Request {

    private static List<String> proxyList = new ArrayList<>();

    private static ThreadLocal<Proxy> currentProxy = new ThreadLocal<>();

    private static ConcurrentLinkedDeque<Proxy> proxyConcurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    //    private final String httpRouteHost = "http-dyn.abuyun.com";
//    private final Integer httpRoutePort = 9020;
    private final String httpRouteHost = "proxy.superfastip.com";

    private final Integer httpRoutePort = 7798;

    private final Proxy httpRouteProxy = new Proxy(httpRouteHost, httpRoutePort);

    private HttpClientBuilder httpClientBuilder = HttpClients.custom();
    /**
     * 默认编码 utf-8
     */

    private String charSet = "UTF-8";
    /**
     * 用于保存response的text
     */
    private String content;
    /**
     * 其他参数列表,用于在回调函数之间传递数据
     */
    private Map<String, Object> packageMap = new HashMap<>();

    private CredentialsProvider credsProvider = null;

    private String url;
    /**
     * response status 请求返回码
     */
    private int statusCode;

    private Map<String, String> responseHeadersMap = new HashMap<>();

    private Map<String, String> requestHeadersMap = new HashMap<>();

    private RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

    private Map<String, String> cookiesMap = new HashMap<>();

    private Proxy proxy = null;

    private BasicCookieStore cookieStore = new BasicCookieStore();

    private String errorDescription = "";

    private String requestType;

    private Map postMap;

    private boolean parameterIsJsonStr;

    private String jsonStr = "";

    private String parseContent = "";

    public Request() {
        new Request(5);
    }

    public Request(int retryTime) {
        requestConfigBuilder
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(10000);
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf)
                .register("https", sslsf).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        connectionManager.setMaxTotal(100);
        httpClientBuilder.setConnectionManager(connectionManager);
    }

    private static synchronized void suppleProxy() {
        if (proxyConcurrentLinkedDeque.size() > 3) {
            return;
        }
        String count = "40";
        String proxyAPI = "http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=d8a00f2f643c4df3aff5ba433f1afd89&count=" + count + "&expiryDate=0&format=1&newLine=2";

        System.out.println("补充代理ip,调用线程为" + Thread.currentThread().getName());
        Request request = new Request();
        request.get(proxyAPI);
        Map map = JSONObject.parseObject(request.getContent(), Map.class);
        List<Map> mapList = null;
        try {
            mapList = (List<Map>) map.get("msg");
        } catch (Exception e) {
            System.out.println("获取代理异常,重新获取");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignored) {
            }
            Request.suppleProxy();
            return;
        }
        mapList.forEach(k -> {
            int port = Integer.parseInt(k.get("port").toString());
            String ip = k.get("ip").toString();
            proxyConcurrentLinkedDeque.offer(new Proxy(ip, port));
        });
    }

    public static synchronized String[] changeProxy() {
        Proxy proxy = currentProxy.get();
        if (proxy != null) {
            proxyList.remove(proxy.getHost() + ":" + proxy.getPort());
        }
        if (proxyList == null || proxyList.size() == 0) {
            String url = "http://www.superfastip.com/welcome/get_private_ip/1";
            Request request = new Request();
            request.get(url);
            String[] ips = request.getContent().split("<br>");
            proxyList.addAll(Arrays.asList(ips));
        }
        String[] proxySplit = proxyList.get((int) Math.floor(Math.random() * proxyList.size())).split(":");
        String ip = proxySplit[0];
        String port = proxySplit[1];
        currentProxy.set(new Proxy(ip, Integer.parseInt(port)));
        return new String[]{ip, port};
    }

    public static String authHeader(String orderno, String secret, int timestamp) {
        //拼装签名字符串
        String planText = String.format("orderno=%s,secret=%s,timestamp=%d", orderno, secret, timestamp);

        //计算签名
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();

        //拼装请求头Proxy-Authorization的值
        return String.format("sign=%s&orderno=%s&timestamp=%d", sign, orderno, timestamp);
    }

    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }

    public void setCookiesMap(Map<String, String> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

//    public Request useSecondProxy2() {
//        this.setProxy(new Proxy("forward.apeyun.com", 9082, "2120010600028491531", "6ek8lg6tMzVuxFu0"));
//        return this;
//    }

    public Proxy getProxy() {
        Proxy proxy = proxyConcurrentLinkedDeque.poll();
        while (proxy == null) {
            Request.suppleProxy();
            proxy = proxyConcurrentLinkedDeque.poll();
        }
        return proxy;
    }

    public Request setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public Request useHttpRouteProxy() {
        Proxy proxy = currentProxy.get();
        if (proxy == null) {
            changeProxy();
            proxy = currentProxy.get();
        }
        this.setProxy(proxy);
        return this;
    }

    public Request useDefaultProxy() {
        this.setProxy(new Proxy(httpRouteHost, httpRoutePort));
        return this;
    }

    public Request setMyOwnProxy(Proxy proxy) {
        this.setProxy(new Proxy(proxy.getHost(), proxy.getPort()));
        return this;
    }

    public Request setTime(int connectionRequestTime, int connectionTime, int socketTime) {
        requestConfigBuilder
                .setConnectionRequestTimeout(connectionRequestTime)
                .setConnectTimeout(connectionTime)
                .setSocketTimeout(socketTime);
        return this;
    }

    /**
     * @param url
     * @return
     */
    public Request get(String url) {
        CallBack callBack = null;
        return get(url, callBack);
    }

    public Request getDownload(String url, String path) {
        this.requestType = "get";
        HttpGet httpGet = new HttpGet(url);
        //检测是否使用了代理
        if (this.proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(this.proxy.getHost(), this.proxy.getPort()));
        }

        RequestConfig requestConfig = requestConfigBuilder.build();
        httpGet.setConfig(requestConfig);

        httpGet.setHeaders(transRequestHeaderMapToHeaderArray(this.requestHeadersMap));
        try (CloseableHttpClient httpclient = httpClientBuilder.setDefaultCookieStore(this.cookieStore).build();
        ) {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            this.url = url;
            formCookiesMap();
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            //Todo 换一种方式实现
//            IOUtils.copy(response.getEntity().getContent(), os);
            os.flush();
            os.close();
            formResponseHeadersMap(response.getAllHeaders());
            this.statusCode = response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Request get(String url, int timeOut) {
        return get(url, null, 60);
    }


    public Request get(String url, CallBack callBack, int timeOut) {
        Runnable task = () -> rawGet(url, callBack);
        future(task);
        return this;
    }

    public Request get(String url, CallBack callBack) {
        return get(url, callBack, 60);
    }

    protected Request rawGet(String url, CallBack callBack) {
        this.requestType = "get";
        HttpGet httpGet = new HttpGet(url);
        //检测是否使用了代理
        if (this.proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(this.proxy.getHost(), this.proxy.getPort()));
        }

        RequestConfig requestConfig = requestConfigBuilder.build();
        RequestConfig cookieConfig = RequestConfig.copy(requestConfig)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .build();
        httpGet.setConfig(cookieConfig);

        httpGet.setHeaders(transRequestHeaderMapToHeaderArray(this.requestHeadersMap));
        if (this.proxy != null && this.proxy.getUserName() != null) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.proxy.getUserName(), this.proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }
        try (CloseableHttpClient httpclient = httpClientBuilder.setDefaultCookieStore(this.cookieStore).build()) {
            execute(url, httpGet, httpclient);
        } catch (IOException e) {
            e.printStackTrace();
            if (e.toString().contains("114.104.130.95")) {
                System.out.println("114.104.130.95的抓取页面为" + url);
            }
        }
        if (callBack != null) {
            callBack.func(this);
        }
        return this;
    }

    public void execute(String url, HttpGet httpGet, CloseableHttpClient httpClient) throws IOException {
        this.url = url;
        CloseableHttpResponse response = httpClient.execute(httpGet);
        formCookiesMap();
        this.content = EntityUtils.toString(response.getEntity(), this.charSet);
        formResponseHeadersMap(response.getAllHeaders());
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    private void formCookiesMap() {
        this.cookieStore.getCookies().forEach(k -> {
            this.cookiesMap.put(k.getName(), k.getValue());
        });
    }

    /**
     * 弃用,改使用setCharSet setHeadersMap setProxy的方式设置
     */
    @Deprecated
    public Request post(String url, Map<String, String> param, Proxy proxy, Map<String, String> headersMap) {
        this.proxy = proxy;
        this.requestHeadersMap = headersMap;
        return post(url, param, null);
    }

    public Request post(String url, Map<String, String> param, CallBack callBack) {
        Runnable task = () -> rawPost(url, param, callBack);
        future(task);
        return this;
    }

    private void future(Runnable task) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(task);
        try {
            future.get(45, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println(Thread.currentThread().getName() + " 超时45秒");
        }
    }

    private Request rawPost(String url, Map<String, String> param, CallBack callBack) {
        this.postMap = param;
        this.requestType = "post";
        this.url = url;
        HttpPost post = new HttpPost(url);

        if (this.proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(this.proxy.getHost(), this.proxy.getPort()));
        }
        RequestConfig requestConfig = requestConfigBuilder.build();
        post.setConfig(requestConfig);

        post.setHeaders(transRequestHeaderMapToHeaderArray(this.requestHeadersMap));
        //封装提交到服务器的参数信息
        List<NameValuePair> list = new ArrayList<>();
        param.keySet().forEach(k -> list.add((new BasicNameValuePair(k, param.get(k)))));
        if (parameterIsJsonStr && StringUtil.isNotEmpty(jsonStr)) {
            try {
                post.setEntity(new StringEntity(jsonStr));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                parameterIsJsonStr = false;
            }
        } else {
            //表单参数编码格式为utf8
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, Consts.UTF_8);
            //设置参数信息
            post.setEntity(formEntity);
        }
        //提交post方法
        if (this.cookieStore.getCookies().size() > 0) {
            httpClientBuilder.setDefaultCookieStore(this.cookieStore);
        }
        if (this.proxy != null && this.proxy.getUserName() != null) {
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.proxy.getUserName(), this.proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }
        try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
            execute(url, post, httpclient);
        } catch (IOException | ParseException e) {
            if (e.toString().contains("192.168.0.250:8050")) {
                //当出现了这条数据时意味着250的splash处理不过来了
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ignored) {
                }
            }

            //出现此原因立即回拨
            String massage = e.toString();
            for (int reTryTimes = 0; reTryTimes <= 10 && massage.contains("wenshu.court.gov.cn:80"); reTryTimes++) {
                try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
                    execute(url, post, httpclient);
                    Thread.sleep(1000);
                    massage = "";
                } catch (IOException | InterruptedException e1) {
                    massage = e1.toString();
                }
            }
            if (!"".equals(massage)) {
                if (e.toString().contains("wenshu.court.gov.cn:80")) {
                    //当出现此信息是意味着文书网需要校验验证码
                    System.out.println("文书网出现验证码");
                } else if (e.toString().contains("Connection refused")) {
                    System.out.println("Connection refused的抓取页面为" + url);
                } else {
                    System.out.println("异常未显示：" + e.toString());
                }

            }

        }
        if (callBack != null) {
            callBack.func(this);
        }
        return this;
    }

    private void execute(String url, HttpPost post, CloseableHttpClient httpclient) throws IOException {
        CloseableHttpResponse response = httpclient.execute(post);
        formResponseHeadersMap(response.getAllHeaders());
        formCookiesMap();
        this.content = EntityUtils.toString(response.getEntity(), this.charSet);
        this.url = url;
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    /**
     * @param url
     * @param param
     * @return
     */
    public Request post(String url, Map<String, String> param) {
        return post(url, param, null);
    }

    /**
     * 参数为json字符串格式时通过jsonStr传参而不需要postMap
     *
     * @param url
     * @return
     */
    public Request post(String url) {
        parameterIsJsonStr = true;
        return post(url, new HashMap<>(), null);
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }


    public Map<String, String> getRequestHeadersMap() {
        return requestHeadersMap;
    }

    public Request setRequestHeadersMap(Map<String, String> map) {
        this.requestHeadersMap = new HashMap<>(map);
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map getPostMap() {
        return postMap;
    }

    public void setPostMap(Map postMap) {
        this.postMap = postMap;
    }

    public void addRequestHeader(String name, String value) {
        this.requestHeadersMap.put(name, value);
    }


    public void removeRequestHeader(String name) {
        this.requestHeadersMap.remove(name);
    }

    public Map<String, Object> getPackageMap() {
        return packageMap;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getResponseHeadersMap() {
        return responseHeadersMap;
    }

    public Request addPackageParam(String name, Object value) {
        packageMap.put(name, value);
        return this;
    }

    public Object getPackageParam(String name) {
        return packageMap.get(name);
    }

    private void formResponseHeadersMap(Header[] headers) {
        Arrays.stream(headers).forEach(k -> responseHeadersMap.put(k.getName(), k.getValue()));
    }

    private List<Header> transRequestHeaderMapToHeaderList(Map<String, String> requestHeadersMap) {
        return requestHeadersMap.keySet().stream().map(k -> new BasicHeader(k, requestHeadersMap.get(k))).collect(Collectors.toList());
    }

    private Header[] transRequestHeaderMapToHeaderArray(Map<String, String> requestHeadersMap) {
        return requestHeadersMap.keySet().stream().map(k -> new BasicHeader(k, requestHeadersMap.get(k))).toArray(Header[]::new);
    }

    public BasicCookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(BasicCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getCharSet() {
        return charSet;
    }

    public Request setCharSet(String charSet) {
        this.charSet = charSet;
        return this;
    }

    public boolean isParameterIsJsonStr() {
        return parameterIsJsonStr;
    }

    public Request setParameterIsJsonStr(boolean parameterIsJsonStr) {
        this.parameterIsJsonStr = parameterIsJsonStr;
        return this;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public Request setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
        return this;
    }

    public Request addCookieFromCookieMapToHeaders() {
        String cookie = this.cookiesMap.keySet().stream().map(k -> k + "=" + cookiesMap.get(k)).collect(Collectors.joining(";"));
        this.getRequestHeadersMap().put("Cookie", cookie);
        return this;
    }

    public String getParseContent() {
        return parseContent;
    }

    public void setParseContent(String parseContent) {
        this.parseContent = parseContent;
    }

    private void retry(long time) throws Exception {
        if (getStatusCode() == HTTP_STATE_CODE_OK) {
            TimeUnit.SECONDS.sleep(time);
            return;
        }

        while (getStatusCode() != HTTP_STATE_CODE_OK) {
            boolean isGet = postMap == null;
            if (isGet) {
                this.useDefaultProxy().get(this.getUrl());
            } else {
                this.useDefaultProxy().post(this.getUrl(), this.getPostMap());
            }
            TimeUnit.SECONDS.sleep(time);
            System.out.println("重试中 ");
        }
    }

    public Request retryRequest(long time) {
        boolean retry = true;
        while (retry) {
            try {
                retry = false;
                retry(time);
            } catch (Exception e) {
                retry = true;
            }
        }
        return this;
    }

    public Request retryRequest() {
        return retryRequest(1);
    }


}
