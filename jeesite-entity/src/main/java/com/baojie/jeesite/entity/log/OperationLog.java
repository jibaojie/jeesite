package com.baojie.jeesite.entity.log;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
@ApiModel(value = "OperationLog", description = "日志实体")
@Table(name = "test_jeesite.operation_log")
@NameStyle(Style.normal)
@Data
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @Id
    private String kid;
    /**
     * 日志类型
     */
    @Column(name="log_type")
    private Integer logType;
    /**
     * 日志名称
     */
    @Column(name="log_name")
    private String  logName;
    /**
     * 管理员id
     */
    @Column(name="user_id")
    private Integer userId;
    /**
     * 创建时间
     */
    @Column(name="add_date")
    private Timestamp addDate;
    /**
     * 类名称
     */
    @Column(name="class_name")
    private String  className;
    /**
     * 方法名称
     */
    private String  method;
    /**
     * 是否成功
     */
    private String  succeed;
    /**
     * 备注
     */
    private String  message;
}
