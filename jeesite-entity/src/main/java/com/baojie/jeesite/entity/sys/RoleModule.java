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

/**
 * 
 * @author:      冀保杰
 * @date:        2018-08-10 11:58:43
 * @Description: 用户实体类
 */
@ApiModel(value = "RoleModule", description = "角色权限实体类")
@Table(name = "role_module")
@NameStyle(Style.normal)
@Data
public class RoleModule implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;

    @Column(name="module_id")
    private Integer moduleId;

    @Column(name="role_id")
    private Integer RoleId;

}