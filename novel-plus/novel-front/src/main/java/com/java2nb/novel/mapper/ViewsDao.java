package com.java2nb.novel.mapper;

import com.java2nb.novel.core.utils.ViewsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zyf
 * @email 82345335@qq.com
 * @date 2021-04-15 10:24:47
 */
@Mapper
public interface ViewsDao {

	ViewsDO get(Long id);
	
	List<ViewsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ViewsDO views);
	
	int update(ViewsDO views);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
