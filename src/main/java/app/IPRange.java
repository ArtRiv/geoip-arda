package app;

public class IPRange implements Comparable<IPRange> {
    private final long startIP;
    private final long endIP;
    private final int geonameId;
    
    public IPRange(long startIP, long endIP, int geonameId) {
        this.startIP = startIP;
        this.endIP = endIP;
        this.geonameId = geonameId;
    }
    
    public boolean contains(long ip) {
        return ip >= startIP && ip <= endIP;
    }
    
    public int getGeonameId() {
        return geonameId;
    }
    
    public long getStartIP() {
        return startIP;
    }
    
    public long getEndIP() {
        return endIP;
    }
    
    @Override
    public int compareTo(IPRange other) {
        return Long.compare(this.startIP, other.startIP);
    }
    
    @Override
    public String toString() {
        return "IPRange [startIP=" + startIP + ", endIP=" + endIP + ", geonameId=" + geonameId + "]";
    }
}