package com.framework.mapper;

import java.util.Map;

import com.framework.base.BaseMapper;
import com.framework.po.DataDictionary;

public interface DataDictionaryMapper extends BaseMapper<DataDictionary> {

	int countKey(Map<String, Object> conditionMap);

}
