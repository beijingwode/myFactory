package com.wode.search;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Search Client 工厂类, 用来构造搜索所需要的Client对象, 该对象需要依赖spring配置文件来初始化并注入相关初始参数. 配置方式:在配置文件中添加
 * <bean id="SearchClientFactory" class="com.wode.search.SearchClientFactory" scope="singleton">
 *	<property name="uri" value="http://localhost:9200"/>
 *	<property name="discoveryFrequency" value="60"/>
 * </bean>
 * <bean id="searchClient" factory-bean="SearchClientFactory" factory-method="getClient"/>
 *
 * @author Bing King
 *
 */
public class SearchClientFactory {

	/** 集群ip地址 **/
	private String ipAddress;
	/** 集群名 **/
	private String clusterName="wode-search";
	
	@SuppressWarnings("resource")
	public TransportClient getClient() {
		// 创建client
		try {

			// 设置集群名称
			Settings settings = Settings.builder().put("cluster.name", getClusterName()).build();
			TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(getIpAddress()), 9300));
			return client;

		} catch (UnknownHostException e) {
			e.printStackTrace();

			return null;
		}
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}
