package util.crawl;

/**
 * Description: <br/>
 * date: 2020/7/5 21:30<br/>
 *
 * @author MuyerJ<br   />
 */
public class Proxy {
    private String host;
    private int port;
    private String userName;
    private String password;

    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Proxy(String host, int port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
