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
@ApiModel(value = "Role", description = "角色实体类")
@Table(name = "role")
@NameStyle(Style.normal)
@Data
public class Role implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="role_id")
    @NotNull
    private Integer roleId;

    private Integer pid;
    
    @Column(name="role_name")
    private String roleName;

    @Column(name="org_id")
    private Integer orgId;

    private String telephone;

    @Column(name="add_date")
    private Timestamp addDate;

    @Column(name="add_user_id")
    private Integer addUserId;

    @Column(name="add_user_name")
    private String addUserName;
    
  
}