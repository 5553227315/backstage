package com.example.filmback.service.impl;

import com.example.filmback.entity.Film;
import com.example.filmback.mapper.FilmMapper;
import com.example.filmback.service.IFilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电影信息表 服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements IFilmService {

}
