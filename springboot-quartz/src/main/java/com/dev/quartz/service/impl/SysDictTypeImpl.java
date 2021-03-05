package com.dev.quartz.service.impl;

import com.dev.quartz.entity.SysDictData;
import com.dev.quartz.entity.SysDictType;
import com.dev.quartz.mapper.SysDictDataDao;
import com.dev.quartz.mapper.SysDictTypeDao;
import com.dev.quartz.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-03
 */
@Service
public class SysDictTypeImpl extends ServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeDao dictTypeMapper;

    @Autowired
    private SysDictDataDao dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init()
    {
//        List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
//        for (SysDictType dictType : dictTypeList)
//        {
//            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
//        }
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType)
    {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll()
    {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (!CollectionUtils.isEmpty(dictDatas))
        {
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(Long dictId)
    {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType)
    {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids) throws Exception {
        String[] str = ids.split(",");
        Long[] dictIds = (Long[]) ConvertUtils.convert(str,long.class);
        for (Long dictId : dictIds)
        {
            SysDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0)
            {
                throw new Exception(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }
        return dictTypeMapper.deleteDictTypeByIds(dictIds);
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dictType)
    {
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType)
    {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return dictTypeMapper.updateDictType(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict)
    {
//        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
//        SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
//        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue())
//        {
//            return UserConstants.DICT_TYPE_NOT_UNIQUE;
//        }
//        return UserConstants.DICT_TYPE_UNIQUE;
        return "";
    }

    public String transDictName(SysDictType dictType)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }
}
