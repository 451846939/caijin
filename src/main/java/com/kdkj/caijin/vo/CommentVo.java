package com.kdkj.caijin.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 评论Vo
 *
 * @author lin
 * @create 2018-04-03 14:24
 **/
@Data
public class CommentVo {
    private String id;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 内容
     */
    private String content;
    /**
     * 本表id表明上一条
     */
    private String commentid;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;
    /**
     * 点赞数
     */
    private Integer praise;
    /**
     * 资讯id
     */
    private String informationid;
    /**
     * 审核状态0表示通过1表示没通过
     */
    private Integer examine;
    /**
     * 子回复
     */
    private List<CommentVo> childrenComment;

    public CommentVo() {
    }

    public CommentVo(String id, String userid, String content, String commentid, Date createtime, Date updatetime, Integer praise, String informationid, Integer examine) {
        this.id = id;
        this.userid = userid;
        this.content = content;
        this.commentid = commentid;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.praise = praise;
        this.informationid = informationid;
        this.examine = examine;
    }
}
