package com.baojie.jeesite.common.base;

import com.baojie.jeesite.util.http.ResponseMessage;
import com.baojie.jeesite.util.http.Result;
import com.baojie.jeesite.util.mybatis.BasePageBean;
import com.baojie.jeesite.util.mybatis.Query;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */
public class BaseController<Biz extends BaseService, T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Biz baseBiz;

    /**
     * 做权限控制，注释掉父类接口
     */

//    /**
//     * 返回Integer还是实体看需求定
//     */
//    @RequestMapping(value = "insert", method = RequestMethod.POST)
//    @ApiOperation(value = "新增")
//    public ResponseMessage<T> insert(@RequestBody T t) {
//        return Result.success((T) baseBiz.insert(t));
//    }
//
//    @RequestMapping(value = "deleteLogical", method = RequestMethod.POST)
//    @ApiOperation(value = "逻辑删除， 传入del=1")
//    public ResponseMessage<Integer> deleteLogical(@RequestBody T t) {
//        return Result.success(baseBiz.deleteLogical(t));
//    }
//
//    @RequestMapping(value = "delete", method = RequestMethod.POST)
//    @ApiOperation(value = "删除")
//    public ResponseMessage<Integer> delete(@RequestBody T t) {
//        return Result.success(baseBiz.delete(t));
//    }
//
//    @RequestMapping(value = "update", method = RequestMethod.POST)
//    @ApiOperation(value = "更新实体,传入实体(kid必传),null的字段不更新")
//    public Integer update(@RequestBody T t) {
//        return baseBiz.updateSelectiveById(t);
//    }
//
//    @RequestMapping(value = "getById", method = RequestMethod.GET)
//    @ApiOperation(value = "主键查询")
//    public ResponseMessage<T> getById(@RequestParam String kid) {
//        return Result.success((T) baseBiz.selectById(kid));
//    }
//
//
//    @RequestMapping(value = "getAll", method = RequestMethod.GET)
//    @ApiOperation(value = "查询所有")
//    public ResponseMessage<List<T>> all() {
//        return Result.success(baseBiz.selectListAll());
//    }
//
//    @RequestMapping(value = "selectByQuery", method = RequestMethod.GET)
//    @ApiOperation(value = "分页模糊查询,传入map")
//    public ResponseMessage<BasePageBean<T>> selectByQuery(@RequestParam Map<String, Object> isDel) {
//        Query query = new Query(isDel);
//        return Result.success(baseBiz.selectByQuery(query));
//    }
//
//    @RequestMapping(value = "selectByMap", method = RequestMethod.GET)
//    @ApiOperation(value = "分页模糊查询,传入map")
//    public ResponseMessage<BasePageBean<T>> selectByMap(@RequestParam Map<String, Object> map) {
//        return Result.success(baseBiz.selectByMap(map));
//    }
//
//    @RequestMapping(value = "selectByExample", method = RequestMethod.POST)
//    @ApiOperation(value = "根据实体类信息查询数据")
//    public ResponseMessage<List<T>> selectByExample(@RequestBody T t) {
//        return Result.success(baseBiz.selectByExample(t));
//    }
//
//    @RequestMapping(value = "selectBySql", method = RequestMethod.GET)
//    @ApiOperation(value = "根据查询语句查询")
//    public ResponseMessage<List<Map<String, Object>>> selectBySql(@RequestParam String sql) {
//        return Result.success(baseBiz.selectBySql(sql));
//    }
}
