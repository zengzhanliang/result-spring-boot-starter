package com.zeng.util;

import java.net.*;
import java.util.Enumeration;

/**
 * 直接根据第一个网卡地址作为其内网ipv4地址，避免返回 127.0.0.1
 * @author zengzhanliang
 */
public class IpUtil {
    private static String IP = "";

    public static String getLocalIpByNetcard() {
        if ( !"".equals( IP ) )
        {
            return IP;
        }

        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp()) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        IP = inet4Address.getHostAddress();
                        return IP;
                    }
                }
            }

            IP = InetAddress.getLocalHost().getHostAddress();
            return IP;
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}