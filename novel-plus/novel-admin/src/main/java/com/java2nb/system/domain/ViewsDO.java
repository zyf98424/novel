package com.java2nb.system.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * 
 * 
 * @author zyf
 * @email 82345335@qq.com
 * @date 2021-04-15 10:24:47
 */
public class ViewsDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//id
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//用户ip
			private String ip;
	//创建时间
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date date;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：用户ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：创建时间
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getDate() {
		return date;
	}
}
