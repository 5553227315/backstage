package com.example.filmback.service.impl;

import com.example.filmback.entity.Showings;
import com.example.filmback.mapper.ShowingsMapper;
import com.example.filmback.service.IShowingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影院场次表 服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-25
 */
@Service
public class ShowingsServiceImpl extends ServiceImpl<ShowingsMapper, Showings> implements IShowingsService {

}
