package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 比特币数据
 *
 * @author lin
 * @create 2018-04-11 13:23
 **/
@Data
@Entity
@Table(name = "Btb_info")
public class BTBInfo {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 币种
     */
    @Column(length = 64)
    private String type;
    /**
     * 15分前价格
     */
    @Column(length = 64)
    private String m15;
    @Column(length = 64)
    /**最后一次成交价*/
    private String last;
    @Column(length = 64)
    /**买*/
    private String buy;
    @Column(length = 64)
    /**卖*/
    private String sell;
    @Column(length = 64)
    /**货币符号*/
    private String symbol;
    @Temporal(TemporalType.TIMESTAMP)
    /**时间*/
    private Date date;
}
