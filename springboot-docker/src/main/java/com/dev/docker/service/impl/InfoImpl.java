package com.dev.docker.service.impl;

import com.dev.docker.entity.Info;
import com.dev.docker.mapper.InfoMapper;
import com.dev.docker.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-06-30
 */
@Service
public class InfoImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

}
