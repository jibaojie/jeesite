package com.baojie.jeesite.entity.sys;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @author:      冀保杰
 * @date:        2018-08-10 11:58:43
 * @Description: 用户实体类
 */
@ApiModel(value = "Module", description = "权限实体类")
@Table(name = "module")
@NameStyle(Style.normal)
@Data
public class Module implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    
    private String code;

    private String pcode;

    private String pcodes;
    
    private String name;
    
    private String icon;
    
    private String url;

    private Integer num;

    private Integer levels;

    @Column(name="is_menu")
    private Boolean menu;

    private String tips;

    private Short status;

    @Column(name="is_open")
    private Boolean open;

    @Column(name="del_user_id")
    private Integer delUserId;

    @Column(name="del_user_name")
    private String delUserName;
    
  
}