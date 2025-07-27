package app;

public class IPUtil {
    
    public static long ipToLong(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            throw new IllegalArgumentException("Formato de IP inválido: " + ipAddress);
        }
        
        long result = 0;
        for (int i = 0; i < 4; i++) {
            int value = Integer.parseInt(octets[i]);
            result = result << 8 | value;
        }
        return result;
    }
    
    public static long getEndIPFromCIDR(long startIP, int cidrMask) {
        int hostBits = 32 - cidrMask;
        return startIP | ((1L << hostBits) - 1);
    }
    
    public static IPRange cidrToRange(String cidr, int geonameId) {
        String[] parts = cidr.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato CIDR inválido: " + cidr);
        }
        
        String ipAddress = parts[0];
        int prefix = Integer.parseInt(parts[1]);
        
        long startIP = ipToLong(ipAddress);
        long endIP = getEndIPFromCIDR(startIP, prefix);
        
        return new IPRange(startIP, endIP, geonameId);
    }
    
    public static String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }
}