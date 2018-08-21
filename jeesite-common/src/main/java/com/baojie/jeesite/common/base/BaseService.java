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

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public abstract class BaseService<M extends MyMapper<T>, T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    /**
     * 新增
     *
     * @param entity
     * @return entity
     */
    public T insert(T entity) {
        mapper.insertSelective(entity);
        return entity;
    }

    /**
     * 删除数据，实体中包含主键
     * 主键不传返回0，删除失败
     *
     * @param entity
     * @return entity
     */
    public Integer delete(T entity) {
        return mapper.deleteByPrimaryKey(entity);
    }

    /**
     * 删除数据，逻辑删除传入 del=1 表示数据删除
     * 主键不传，更新返回0，删除失败
     *
     * @param entity
     * @return entity
     */
    public Integer deleteLogical(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 修改实体中全部信息，包括属性为null的
     * 主键不传，返回0，更新失败
     *
     * @param entity
     */
    public Integer updateById(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 修改实体中信息，不包括null属性
     * 主键不传，返回0， 更新失败
     *
     * @param entity
     * @return
     */
    public Integer updateSelectiveById(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    /**
     * 根据主键查询
     *
     * @param t
     * @return
     */
    public T selectById(T t) {
        return  mapper.selectByPrimaryKey(t);
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
        return new BasePageBean<T>(result.getTotal(), list);
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
     *
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
            if (key.indexOf("_") > 0) {
                String paramate = key.split("_")[1];
                switch (paramate) {
                    case "eq":
                        criteria.andEqualTo(entry.getKey(), entry.getValue().toString());
                        break;
                    case "like":
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                        break;
                    case "or":
//                        Example.Criteria criteria2 = example.createCriteria();
//                        example.or(criteria2);
                        break;
                    case "gt":
                        criteria.andGreaterThan(entry.getKey(), entry.getValue().toString());
                        break;
                    case "gtoreq":
                        criteria.andGreaterThanOrEqualTo(entry.getKey(), entry.getValue().toString());
                        break;
                    case "lt":
                        criteria.andLessThan(entry.getKey(), entry.getValue().toString());
                        break;
                    case "ltoreq":
                        criteria.andLessThanOrEqualTo(entry.getKey(), entry.getValue().toString());
                        break;
                    default:
                        break;
                }
            } else {
                criteria.andEqualTo(entry.getKey(), entry.getValue().toString());
            }
        }
        return getResult(example, map);
    }

    public BasePageBean<T> getResult(Example example, Map<String, Object> map) {
        List<T> list = null;
        Long count = 0L;
        if (map.get("page") != null && map.get("limit") != null) {
            int page = Integer.parseInt((String) map.get("page"));
            int limit = Integer.parseInt((String) map.get("limit"));
            Page<Object> result = PageHelper.startPage(page, limit);
            list = mapper.selectByExample(example);
            count = result.getTotal();
        } else {
            list = mapper.selectByExample(example);
            count = (long) list.size();
        }
        return new BasePageBean<T>(count, list);
    }

    public List<Map<String, Object>> selectBySql(String sql) {
        return mapper.selectBySql(sql);
    }

    public Integer getNextKey(String key) {
        return mapper.getNextKey(key);
    }

}

