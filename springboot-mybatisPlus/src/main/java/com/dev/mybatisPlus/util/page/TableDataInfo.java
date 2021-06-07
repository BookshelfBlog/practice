package com.dev.mybatisPlus.util.page;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 */

@Data
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    @TableField(exist = false)
    private long total;

    /** 总页数 */
    @TableField(exist = false)
    private int pages;

    /** 当前页 */
    @TableField(exist = false)
    private int pageNum;

    /** 当前页记录数 */
    @TableField(exist = false)
    private int pageSize;

    /** 列表数据 */
    @TableField(exist = false)
    private List<?> rows;

    /** 消息状态码 */
    @TableField(exist = false)
    private int code;

    /** 消息内容 */
    @TableField(exist = false)
    private String msg;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total, int pages)
    {
        this.rows = list;
        this.total = total;
        this.pages = pages;
    }
}