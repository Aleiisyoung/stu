package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	//在业务层中调后台的方法获取item数据
	@Override
	public Item findItemById(Long itemId) {
		//HttpClient:在Java代码中发起Http请求，获取远程服务端数据
		//顺序：前台--依赖的工具类--后台--返回
		//定义远程URl
		String url="http://manage.jt.com/web/item/findItemById";
		
		Map<String,String> params = new HashMap<>();
		params.put("itemId",itemId+"");
		
		String result=httpClient.get(url,params);
		Item item=null;
		try {
			item=objectMapper.readValue(result, Item.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	public ItemDesc finItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById";
		
		Map<String,String> params = new HashMap<>();
		params.put("itemId",itemId+"");
		
		String result=httpClient.get(url,params);
		ItemDesc itemDesc=null;
		try {
			itemDesc=objectMapper.readValue(result, ItemDesc.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemDesc;
	}

	//全文检索引擎对象
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Override//从全文检索服务器获取数据
	public List<Item> findKey(String key) {
		List<Item> itemList=null;
		SolrQuery solrQuery = new SolrQuery(key);
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		try {
			QueryResponse response = 
					httpSolrServer.query(solrQuery);
			itemList=response.getBeans(Item.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemList;
	}
}
