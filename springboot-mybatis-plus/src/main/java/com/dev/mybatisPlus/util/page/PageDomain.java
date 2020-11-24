package com.dev.mybatisPlus.util.page;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页数据
 * 
 * @author niuhao
 */
@Data
public class PageDomain implements Serializable
{
    /** 当前记录起始索引 */
    @TableField(exist = false)
    private Integer pageNum;

    /** 每页显示记录数 */
    @TableField(exist = false)
    private Integer pageSize;

    /** 排序列 */
    @TableField(exist = false)
    private String orderByColumn;

    /** 排序的方向 "desc" 或者 "asc". */
    @TableField(exist = false)
    private String isAsc;
}
