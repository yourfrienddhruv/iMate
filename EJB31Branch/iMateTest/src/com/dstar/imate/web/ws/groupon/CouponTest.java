package com.dstar.imate.web.ws.groupon;

import com.dstar.imate.web.ws.base.StandaloneWebSocketServer;

/**
 * @author Dhruv Gohil
 * 
 */
public class CouponTest {

	public static void main(String[] args) throws Exception {
		StandaloneWebSocketServer.runServer(new CouponService(null));
        System.out.println("Press any key to stop the server...");
        System.in.read();
        System.out.println("Stopped the server...");
	}
}
