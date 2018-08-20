package com.baojie.jeesite.common.base;

import com.baojie.jeesite.util.mybatis.BasePageBean;
import com.baojie.jeesite.util.mybatis.Query;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


public abstract class BaseService<M extends MyMapper<T>, T> {

    protected final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected M mapper;

    public int  delete( T entity) {
       return  mapper.deleteByPrimaryKey(entity);
    }

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public T selectById(String kid) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("kid", kid);
        criteria.andEqualTo("isDel", 0);
        List<T> l=mapper.selectByExample(example);
        if(l.size()==0) {
            return null;
        }
        return mapper.selectByExample(example).get(0);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    public T insertSelective(T entity) {
        mapper.insertSelective(entity);
        return entity;
    }

    public void updateById(T entity) {
         mapper.updateByPrimaryKey(entity);
    }


    public int  updateSelectiveById(T entity) {
       return   mapper.updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public BasePageBean<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit()).setOrderBy("addDate desc");
        List<T> list = mapper.selectByExample(example);
        return new BasePageBean<T>(result.getTotal(), list) ;
    }

    /**
     * 修改通用接口，完善过滤条件
     * 0，默认 =
     * 1，如果查询条件是 = ，则传递条件参数加_eq
     * 2，如果查询条件是 like，则传递条件参数加_like
     * 3，如果查询条件是 or，则传递条件参数加_or 未实现
     * 4，如果查询条件是 > ,则传递条件参数加_gt
     * 5，如果查询条件是 >= ,则传递条件参数加_gtoreq
     * 6，如果查询条件是 < ,则传递条件参数加_lt
     * 7，如果查询条件是 <= ,则传递条件参数加_ltoreq
     * @param map
     * @return
     */
    public BasePageBean<T> selectByMap(Map<String, Object> map) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        Query query = new Query(map);
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            String key = entry.getKey();
            if (key.indexOf("_") > 0){
                String paramate = key.split("_")[1];
                switch (paramate){
                    case "eq":
                        criteria.andEqualTo(entry.getKey(), entry.getValue().toString());   break;
                    case "like":
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");  break;
                    case "or":
//                        Example.Criteria criteria2 = example.createCriteria();
//                        example.or(criteria2);
                        break;
                    case "gt":
                        criteria.andGreaterThan(entry.getKey(), entry.getValue().toString());   break;
                    case "gtoreq":
                        criteria.andGreaterThanOrEqualTo(entry.getKey(), entry.getValue().toString());   break;
                    case "lt":
                        criteria.andLessThan(entry.getKey(), entry.getValue().toString());   break;
                    case "ltoreq":
                        criteria.andLessThanOrEqualTo(entry.getKey(), entry.getValue().toString());   break;
                    default:    break;
                }
            }else {
                criteria.andEqualTo(entry.getKey(), entry.getValue().toString());
            }
        }
        return getResult(example, map);
    }

    public BasePageBean<T> getResult(Example example, Map<String, Object> map){
        List<T> list = null;
        Long count = 0L;
        if(map.get("page") != null && map.get("limit") != null){
            int page =  Integer.parseInt((String) map.get("page"));
            int limit =  Integer.parseInt((String) map.get("limit"));
            Page<Object> result = PageHelper.startPage(page, limit);
            list = mapper.selectByExample(example);
            count = result.getTotal();
        } else {
            list = mapper.selectByExample(example);
            count = (long) list.size();
        }
        return new BasePageBean<T>(count, list);
    }

    public List<Map<String, Object>> selectBySql(String sql){
        return mapper.selectBySql(sql);
    }

    public Integer getNextKey(String key) {
        return  mapper.getNextKey(key);
    }

}

