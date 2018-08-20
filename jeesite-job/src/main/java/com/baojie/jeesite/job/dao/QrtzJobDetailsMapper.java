package com.baojie.jeesite.job.dao;


import com.baojie.jeesite.entity.job.QrtzJobDetails;

import java.util.List;

/**
 * @author ：冀保杰
 * @date：2018-08-20
 * @desc：
 */
public interface QrtzJobDetailsMapper extends MyMapper<QrtzJobDetails> {
	//增加记录
	int insertByBatch(List<QrtzJobDetails> list);
	int insertSelectiveByBatch(List<QrtzJobDetails> list);
	//删除记录
	int deleteByPrimaryKey(String id);

	int updateByBatch(List<QrtzJobDetails> list);
	int updateSelectiveByBatch(List<QrtzJobDetails> list);
	
	//查询记录
	QrtzJobDetails selectByPrimaryKey(String id);
	
	//查询记录列表
	List<QrtzJobDetails> selectList(QrtzJobDetails qrtzJobDetails);
	
	List<QrtzJobDetails> selectMapList(QrtzJobDetails qrtzJobDetails);


}
