package com.wode.search;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//import com.google.common.base.Charsets;
//import com.google.common.io.CharStreams;
//import com.google.common.io.Closeables;
/**
 * Hello world!
 *
 * @author Bing King
 */
public class App {
	public static void main(String[] args) {
		System.out.println(305l/1000.0*5.0);
	}
	
	public static String getContent() throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://api.t.sina.com.cn/statuses/public_timeline.json?source=202088835&count=200");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		String content = null;
		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    InputStream contentStream = entity1.getContent();
//		    try{
//		    	content = CharStreams.toString(new InputStreamReader(contentStream, Charsets.UTF_8));
//		    	Closeables.closeQuietly(contentStream);
//		    } finally {
//		    	contentStream.close();
//		    }
		    EntityUtils.consume(entity1);
		} finally {
		    response1.close();
		    httpclient.close();
		}
		System.out.println(content);
		return content;
	}
}
