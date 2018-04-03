package com.kdkj.caijin.entity;/**
 * @author lin
 * @create 2018-03-29 18:28
 **/

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文件表
 *
 * @author lin
 * @create 2018-03-29 18:28
 **/
@Data
@Entity
@Table(name = "files")
public class Files implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 路径
     */
    @Column(length = 255)
    private String path;
    /**
     * 名字
     */
    @Column(length = 255)
    private String name;
    /**
     * 新名字
     */
    @Column(length = 255)
    private String newname;
}
