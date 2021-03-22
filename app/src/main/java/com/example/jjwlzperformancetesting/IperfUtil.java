package com.example.jjwlzperformancetesting;

public class IperfUtil {
    public static final String IPERF3 = "iperf3";

    public static final String TCP = "tcp";
    public static final String UDP = "udp";

    public static final boolean DOWNLOAD = true;
    public static final boolean UPLOAD = false;

    private static final int PORT = 5201;
    // target bitrate in bits/sec [KMG]
    private static final String BITRATE = "5G";
    // time in seconds to transmit for (default 10 secs)
    private static final int TIME = 10;
    // output in JSON format
    private static final String JSON = "–J";
    private static final boolean REVERSE = true;

    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";

    public static String getCommand(String region, String protocol, boolean download) {
        if (TCP.equals(protocol)) {
            if (download) {
                // run in reverse mode (server sends, client receives)
                return getCommand(region, TCP, PORT, BITRATE, TIME, JSON) + " -R";
            } else {
                return getCommand(region, TCP, PORT, BITRATE, TIME, JSON);
            }
        } else if (UDP.equals(protocol)) {
            if (download) {
                // run in reverse mode (server sends, client receives)
                return getCommand(region, UDP, PORT, BITRATE, TIME, JSON) + " -R";
            } else {
                return getCommand(region, UDP, PORT, BITRATE, TIME, JSON);
            }
        } else {
            // TODO ERROR LOG
            System.out.println("Error:Unexpected protocol");
            return "";
        }
    }

    public static String getCommand(String region, String protocol, int port, String bitrate, int time, String format) {
        return Const.PATH + IPERF3
                + " -c " + region
                + " -p " + port
                + " " + protocol
                + " -b " + bitrate
                + " -t " + time
                + " " + format;
    }

    /**
     *
     * @param results
     * @return
     *
     *     (Example)
     *     [ ID] Interval           Transfer     Bitrate         Retr
     *     [  5]   0.00-10.00  sec  14.0 MBytes  11.7 Mbits/sec    0             sender
     *     [  5]   0.00-10.16  sec  13.5 MBytes  11.1 Mbits/sec                  receiver
     */
   public static String[] getBandwidth(String results) {

       String[] res = {"", ""};
        String[] lines = results.split("\n");
        for (int i = 0; i < lines.length; i++) {

            if (lines[i].contains(SENDER)) {

                //TODO Delete
                System.out.println("@@@@@ sender line: " + lines[i]);

                res[0] = pickUpBitrate(lines[i]);
            }
            if (lines[i].contains(RECEIVER)) {

                //TODO Delete
                System.out.println("@@@@@ receiver line: " + lines[i]);

                res[1] = pickUpBitrate(lines[i]);
            }
        }

       // TODO Debug LOG
       System.out.println("res[0]: " + res[0]);
       System.out.println("res[1]: " + res[1]);

       return res;
    }

    private static String pickUpBitrate (String line) {

       return line.split("Mbits")[0].split("MBytes")[1].trim();
    }
}

