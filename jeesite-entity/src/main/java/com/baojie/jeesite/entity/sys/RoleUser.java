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
@ApiModel(value = "RoleUser", description = "用户角色类")
@Table(name = "role_user")
@NameStyle(Style.normal)
@Data
public class RoleUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="user_id")
    @NotNull
    private Integer userId;

    @Id
    @Column(name="role_id")
    @NotNull
    private Integer roleId;

}