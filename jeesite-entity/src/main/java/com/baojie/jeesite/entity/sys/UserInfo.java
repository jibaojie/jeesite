package com.baojie.jeesite.entity.sys;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 
 * @author:      冀保杰
 * @date:        2018-08-10 11:58:43
 * @Description: 用户实体类
 */
@ApiModel(value = "UserInfo", description = "用户实体类") 
@Table(name = "user_info")
@NameStyle(Style.normal)
@Data
public class UserInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="user_id")
    @NotNull
    private Integer userId;
    
    @Column(name="user_name")
    private String userName;

    @Column(name="org_id")
    private Integer orgId;

    private String telephone;
    
    private String password;
    
    private String salt;
    
    private Short state;

    /**
     * 管理员类型
     * 0：超级管理员，拥有所有权限，1：单位管理员，拥有单位的所有权限
     */
    @Column(name="admin_type")
    private Short adminType;

    @Column(name="is_del")
    private Short del;

    @Column(name="add_date")
    private Timestamp addDate;

    @Column(name="add_user_id")
    private Integer addUserId;

    @Column(name="add_user_name")
    private String addUserName;

    @Column(name="del_date")
    private Timestamp delDate;

    @Column(name="del_user_id")
    private Integer delUserId;

    @Column(name="del_user_name")
    private String delUserName;
    
  
}