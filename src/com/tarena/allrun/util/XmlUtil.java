package com.tarena.allrun.util;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.tarena.allrun.R;
import com.tarena.allrun.entity.XmppConfig;

public class XmlUtil {

	public static XmppConfig loadConfig(Context context, XmppConfig config)
			throws Exception {

		XmlResourceParser parser = context.getResources().getXml(R.xml.config);
		int eventType = parser.getEventType();
		while (eventType != XmlResourceParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlResourceParser.START_TAG:
				String tagName = parser.getName();
				if ("host".equals(tagName)) {
					config.setHost(parser.nextText());
				}
				if ("port".equals(tagName)) {
					config.setPort(Integer.parseInt(parser.nextText()));
				}
				if ("serviceName".equals(tagName)) {
					config.setServiceName(parser.nextText());
				}
				break;
			}

			eventType = parser.next();
		}

		return config;
	}

	public static void main(String[] args) {
		// System.out.println(loadConfig(context, config));
	}
}
