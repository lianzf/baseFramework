package com.framework.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.mapper.DataDictionaryMapper;
import com.framework.po.DataDictionary;
import com.framework.util.DateUtil;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public int insert(DataDictionary record) throws SQLException {
		record.setCreateTime(DateUtil.getCurrentDate());
		record.setUpdateTime(DateUtil.getCurrentDate());
		return dataDictionaryMapper.insert(record);
	}

	@Override
	public int update(DataDictionary record) throws SQLException {
		record.setUpdateTime(DateUtil.getCurrentDate());
		return dataDictionaryMapper.update(record);
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		return dataDictionaryMapper.deleteById(id);
	}

	@Override
	public DataDictionary selectById(Integer id) throws SQLException {
		return dataDictionaryMapper.getById(id);
	}

	@Override
	public List<DataDictionary> loadList(Map<String, Object> dataMap) throws SQLException {
		return dataDictionaryMapper.getListByMap(dataMap);
	}

	@Override
	public int count(Map<String, Object> condition) throws SQLException {
		return dataDictionaryMapper.count(condition);
	}

	@Override
	public DataDictionary getDicByKey(String key) throws SQLException {
		DataDictionary dictionary = new DataDictionary();
		dictionary.setName(key);
		List<DataDictionary> dataList = dataDictionaryMapper.getListByPo(dictionary);
		if (dataList != null && dataList.size() > 0)
			return dataList.get(0);
		else {
			return null;
		}
	}

	@Override
	public Map<String, String> getDicValueByKey(String key) throws SQLException {
		DataDictionary dictionary = new DataDictionary();
		Map<String, String> resMap = new HashMap<String, String>();
		dictionary.setName(key);
		List<DataDictionary> dataList = dataDictionaryMapper.getListByPo(dictionary);
		dictionary = dataList.get(0);
		if (dictionary != null) {
			String values = dictionary.getValue();
			String[] content = values.split(";");

			for (String con : content) {
				String[] kv = con.split("=");
				resMap.put(kv[0], kv[1]);
			}

		}

		return resMap;
	}

	@Override
	public int countKey(Map<String, Object> conditionMap) {
		return dataDictionaryMapper.countKey(conditionMap);
	}

}
