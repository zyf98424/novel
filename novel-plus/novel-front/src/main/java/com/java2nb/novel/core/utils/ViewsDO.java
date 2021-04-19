package com.java2nb.novel.core.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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

	

			private Long id;
	//用户ip
			private String ip;
	//创建时间
		private String date;

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
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 获取：创建时间
	 */
	public String getDate() {
		return date;
	}
}
