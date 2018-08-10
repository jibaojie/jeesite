package com.baojie.jeesite.entity.user;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.Column;

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
@Table(name = "test_jeesite.userinfo")
@NameStyle(Style.normal)
@Data
public class UserInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="user_id")
    private Integer userId;
    
    @Column(name="user_name")
    private String userName;
    
    private String password;
    
    private String salt;
    
    private Short state;
    
  
}