package im.tao.testcache.bean;

import java.util.concurrent.Future;

public class Data {
    private String value;
    private Future future;

    public Data(String value, Future future) {
        this.value = value;
        this.future = future;
    }

    public Data(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }
}
