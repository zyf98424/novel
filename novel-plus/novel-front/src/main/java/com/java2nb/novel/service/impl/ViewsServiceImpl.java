package com.java2nb.novel.service.impl;

import com.java2nb.novel.core.utils.ViewsDO;
import com.java2nb.novel.mapper.ViewsDao;
import com.java2nb.novel.service.ViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ViewsServiceImpl implements ViewsService {
	@Autowired
	ViewsDao viewsDao;
	
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
	
}
