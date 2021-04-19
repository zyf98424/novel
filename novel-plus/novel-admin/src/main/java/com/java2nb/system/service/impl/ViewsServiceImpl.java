package com.java2nb.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.java2nb.system.dao.ViewsDao;
import com.java2nb.system.domain.ViewsDO;
import com.java2nb.system.service.ViewsService;



@Service
public class ViewsServiceImpl implements ViewsService {
	@Autowired
	private ViewsDao viewsDao;
	
	@Override
	public ViewsDO get(Long id){
		return viewsDao.get(id);
	}
	
	@Override
	public List<ViewsDO> list(Map<String, Object> map){
		return viewsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return viewsDao.count(map);
	}
	
	@Override
	public int save(ViewsDO views){
		return viewsDao.save(views);
	}
	
	@Override
	public int update(ViewsDO views){
		return viewsDao.update(views);
	}
	
	@Override
	public int remove(Long id){
		return viewsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return viewsDao.batchRemove(ids);
	}

	@Override
	public Map<Object, Object> tableSta(Date minDate) {
		List<Map<Object, Object>> maps = viewsDao.tableSta(minDate);

		return maps.stream().collect(Collectors.toMap(x -> x.get("staDate"), x -> x.get("orderCount")));

	}

}
