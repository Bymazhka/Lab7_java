

public class URLDepthPair {

    private String URL;
    private int depth;

    public URLDepthPair(String URL, int depth) {
        this.URL = URL;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Link - " + URL + " on depth " + depth;
    }

    public synchronized String GetURL() {
        return URL;
    }

    public synchronized int GetDepth() {
        return depth;
    }
}