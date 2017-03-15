package com.framework.service;

import java.sql.SQLException;
import java.util.Map;

import com.framework.base.BaseService;
import com.framework.po.DataDictionary;


public interface DictionaryService extends BaseService<DataDictionary> {

	public int countKey(Map<String, Object> conditionMap);
	
	DataDictionary getDicByKey(String key) throws SQLException;

	Map<String, String> getDicValueByKey(String key) throws SQLException;
}
