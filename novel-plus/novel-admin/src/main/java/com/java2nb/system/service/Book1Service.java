package com.java2nb.system.service;

import com.java2nb.system.domain.BookDO;

import java.util.List;
import java.util.Map;

/**
 * 小说表
 * 
 * @author zyf
 * @email 82345335@qq.com
 * @date 2021-04-12 16:41:01
 */
public interface Book1Service {
	
	BookDO get(Long id);
	
	List<BookDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BookDO book);
	
	int update(BookDO book);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
