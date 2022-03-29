package com.example.filmback.service.impl;

import com.example.filmback.entity.Bill;
import com.example.filmback.mapper.BillMapper;
import com.example.filmback.service.IBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author Wyz
 * @since 2022-03-27
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
