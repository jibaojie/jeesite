package com.baojie.jeesite.util.basemybatis;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public class BaseController<Biz extends BaseService , T> {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected Biz baseBiz;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ApiOperation(value = "新增")
 	public T add(@RequestBody T t) {
		baseBiz.insertSelective(t);
		return t;
	}

	@RequestMapping(value = "getById", method = RequestMethod.GET)
	@ApiOperation(value = "主键查询")
 	public T getById(@RequestParam String kid) {

		return (T)baseBiz.selectById(kid);

	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ApiOperation(value = "更新实体,传入实体(kid必传),null的字段不更新")
 	public Integer update(@RequestBody T t) {
		return baseBiz.updateSelectiveById(t);
	}

	@RequestMapping(value = "permanentDelete", method = RequestMethod.POST)
	@ApiOperation(value = "删除,传入kid,isdel=1")
	public Integer permanentDelete(@RequestBody T t) {
		return baseBiz.idelete(t);
	}


	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ApiOperation(value = "删除,传入kid,isdel=1")
 	public Integer delete(@RequestBody T t) {
		return baseBiz.updateSelectiveById(t);
    }

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	@ApiOperation(value = "查询所有")
 	public List<T> all() {
		return baseBiz.selectListAll();
	}
	

	@RequestMapping(value = "selectByQuery", method = RequestMethod.GET)
	@ApiOperation(value = "分页模糊查询,传入map")
 	public BasePageBean<T> selectByQuery(@RequestParam Map<String, Object> isDel) {
	    Query query = new Query(isDel);
        return baseBiz.selectByQuery(query);
	}
	
	@RequestMapping(value = "selectByMap", method = RequestMethod.GET)
	@ApiOperation(value = "分页模糊查询,传入map")
 	public BasePageBean<T> selectByMap(@RequestParam Map<String, Object> map) {
        return baseBiz.selectByMap(map);
	}

	@RequestMapping(value = "selectByExample", method = RequestMethod.POST)
	@ApiOperation(value = "根据实体类信息查询数据")
	public List<T> selectByExample(@RequestBody T t) {
		return baseBiz.selectByExample(t);
	}

	@RequestMapping(value = "selectBySql", method = RequestMethod.GET)
	@ApiOperation(value = "根据查询语句查询")
	public List<Map<String, Object>> selectBySql(@RequestParam String sql) {
		return baseBiz.selectBySql(sql);
	}
}
