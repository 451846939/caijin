package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色表
 *
 * @author lin
 * @create 19:28 2018/3/29
 * @params * @param null
 **/
@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 角色名称
     */
    @Column(length = 64)
    private String name;
    /**
     * 权限
     */
    @Column(length = 64)
    private String power;
}
