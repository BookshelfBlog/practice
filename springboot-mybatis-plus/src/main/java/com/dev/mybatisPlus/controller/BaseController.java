package com.dev.mybatisPlus.controller;

import com.dev.mybatisPlus.util.AjaxResult;
import com.dev.mybatisPlus.util.page.PageDomain;
import com.dev.mybatisPlus.util.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * web层通用数据处理
 *
 * @author niuhao
 */
@Slf4j
public class BaseController
{

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
//        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
//        {
//            @Override
//            public void setAsText(String text)
//            {
//                setValue(DateUtils.parseDate(text));
//            }
//        });
    }

    /**
     * 设置默认分页页码
     * @param pageDomain
     * @return PageDomain
     */
    protected PageDomain CheckPageDomain(PageDomain pageDomain) {
        if (pageDomain.getPageNum() == null || pageDomain.getPageNum() < 1) {
            pageDomain.setPageNum(1);
        }
        if (pageDomain.getPageSize() == null || pageDomain.getPageSize() < 1) {
            pageDomain.setPageSize(10);
        }
        return pageDomain;
    }

    /**
     *  asc or desc
     * @param oderby
     * @return boolean
     */
    protected boolean orderByCheck(String oderby){
        if (oderby.isBlank() || "asc".equals(oderby)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        PageInfo pi = new PageInfo(list);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setMsg("查询成功!");
        rspData.setRows(list);
        rspData.setTotal(pi.getTotal());
        rspData.setPages(pi.getPages());
        rspData.setPageNum(pi.getPageNum());
        rspData.setPageSize(pi.getPageSize());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult check(int result)
    {
        return result > 0 ? AjaxResult.success(false) : AjaxResult.success(true);
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(Object data)
    {
        return AjaxResult.success(data);
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message,Object data)
    {
        return AjaxResult.success(message,data);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(AjaxResult.Type type, String message)
    {
        return new AjaxResult(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return redirect(url);
    }

    /**
     * 获取配置文件参数
     * */
    public String getConfigValue(String key)
    {
        String val = null;
        String path = File.separator + "application.yml";
        ClassPathResource resource = new ClassPathResource(path);
        try
        {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            val = (String)props.get(key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return val;
    }

}
