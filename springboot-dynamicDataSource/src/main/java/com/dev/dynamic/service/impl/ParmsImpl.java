package com.dev.dynamic.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dev.dynamic.entity.Parms;
import com.dev.dynamic.mapper.ParmsMapper;
import com.dev.dynamic.service.ParmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dev.dynamic.util.DB;
import com.dev.dynamic.util.DataSourceType;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 */
@Service
public class ParmsImpl extends ServiceImpl<ParmsMapper, Parms> implements ParmsService {

    @Override
    @DB(dataSource = DataSourceType.SLAVE)
    public <E extends IPage<Parms>> E page(E page, Wrapper<Parms> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @DB(dataSource = DataSourceType.SLAVE)
    @Override
    public List<Parms> list(Wrapper<Parms> queryWrapper) {
        return super.list(queryWrapper);
    }

    @DB(dataSource = DataSourceType.SLAVE)
    @Override
    public Parms getById(Serializable id){
        return this.baseMapper.selectById(id);
    }

    @Override
    @DB(dataSource = DataSourceType.MASTER)
    public boolean save(Parms entity) {
        return super.save(entity);
    }

    @DB(dataSource = DataSourceType.MASTER)
    @Override
    public boolean updateById(Parms entity) {
        return super.updateById(entity);
    }
}
