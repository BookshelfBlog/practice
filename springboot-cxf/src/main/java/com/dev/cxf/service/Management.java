package com.dev.cxf.service;

import com.dev.cxf.model.DataResult;
import com.dev.cxf.model.MapAdapter;
import com.dev.cxf.model.MapListAdapter;
import com.dev.cxf.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description :  webservice接口 //描述
 */
@WebService(
        //服务名
        name = "management",
        //命名空间,一般是接口的包名倒序
        targetNamespace = "http://service.cxf.dev.com"
)
public interface Management {

    @WebMethod
    public String getName(@WebParam(name = "ip") String ip);

    @WebMethod
    public User getUser(@WebParam(name = "name") String name);

    @WebMethod
    public ArrayList<User> getUserList(@WebParam(name = "name") String name);

    @WebMethod
    public DataResult getDataResult();

    @WebMethod
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, User> getUserMap();

    @WebMethod
    @XmlJavaTypeAdapter(MapListAdapter.class)
    public Map<String, List<User>> getUserMapListUser();

    @WebMethod
    public Map<String, Object> getMap(@WebParam(name = "user") User user);
}
