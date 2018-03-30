package com.kdkj.caijin.entity;/**
 * @author lin
 * @create 2018-03-29 18:26
 **/

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 新闻类型
 *
 * @author lin
 * @create 2018-03-29 18:26
 **/
@Data
@Entity
@Table(name = "information_type")
public class InformationType implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    @Column(length = 64)
    private String type;
}
