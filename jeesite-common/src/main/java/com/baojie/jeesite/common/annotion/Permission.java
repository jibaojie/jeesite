package com.baojie.jeesite.common.annotion;

import java.lang.annotation.*;

/**
 * @author ：冀保杰
 * @date：2018-08-21
 * @desc：自定义权限注解，用于检查权限，规定权限访问
 * 自定义注解的说明：
 *   @Target：表示该注解可以用于什么地方，可能的ElementType参数有：
 *      CONSTRUCTOR：构造器的声明
 *      FIELD：域声明（包括enum实例）
 *      LOCAL_VARIABLE：局部变量声明
 *      METHOD：方法声明
 *      PACKAGE：包声明
 *      PARAMETER：参数声明
 *      TYPE：类、接口（包括注解类型）或enum声明
 *   @Retention：需要在什么级别保存该注解信息，可选的RetentionPolicy参数包括：
 *      SOURCE：注解将被编译器丢弃
 *      CLASS：注解在class文件中可用，但会被VM丢弃
 *      RUNTIME：VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
 *   @Document：将注解包含在Javadoc中
 *   @Inherited：允许子类继承父类中的注解
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {
    String[] value() default {};
}
