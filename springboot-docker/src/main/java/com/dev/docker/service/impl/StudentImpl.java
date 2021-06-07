package com.dev.docker.service.impl;

import com.dev.docker.entity.Student;
import com.dev.docker.mapper.StudentMapper;
import com.dev.docker.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-06-03
 */
@Service
public class StudentImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
