package com.framework.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * service基类
 * @author LianZhiFei
 *
 * @param <T>
 */
public interface BaseService<T> {

	int insert(T record) throws SQLException;

	int update(T record) throws SQLException;

	int deleteById(Integer id) throws SQLException;

	T selectById(Integer id) throws SQLException;

	List<T> loadList(Map<String, Object> dataMap) throws SQLException;
	
	int count(Map<String,Object> condition) throws SQLException;
}
