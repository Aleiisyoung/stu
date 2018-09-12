package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {

	//原因：因为有些项目不需要使用缓存，则没有配置这个bean对象，所以需要延时加载
	@Autowired(required=false)//当程序调用时才注入对象
	private JedisSentinelPool sentinelPool;
	//private ShardedJedisPool jedisPool;
	
	public void set(String key,String value){
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		sentinelPool.returnResource(jedis);
	}
	
	public String get(String key){
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.get(key);
		sentinelPool.returnResource(jedis);
		
		return result;
	}
	
	
	/*public void set(String key,String value){
		ShardedJedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		jedisPool.returnResource(jedis);
	}
	//设置超时
	public void set(String key,int seconds,String value){
		ShardedJedis jedis = jedisPool.getResource();
		jedis.setex(key,seconds,value);
		jedisPool.returnResource(jedis);
	}
	//获取值
	public String get(String key){
		ShardedJedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedisPool.returnResource(jedis);
		return result;
	}*/
	
}
