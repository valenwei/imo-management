package com.bsb.permit.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Awaitility {

	private static Logger logger = LoggerFactory.getLogger(Awaitility.class);

	private Awaitility() {

	}

	public static void await(long milliSeconds) throws InterruptedException {
		CountDownLatch lock = new CountDownLatch(1);
		if (lock.await(milliSeconds, TimeUnit.MILLISECONDS)) {
			logger.info("Wait {} milliseconds timeout.", milliSeconds);
		}
	}

	public static void await60s() {
		try {
			await(60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
