package com.example.filmback.service.impl;

import com.example.filmback.entity.Cinema;
import com.example.filmback.mapper.CinemaMapper;
import com.example.filmback.service.ICinemaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影院信息表 服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-24
 */
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema> implements ICinemaService {

}
