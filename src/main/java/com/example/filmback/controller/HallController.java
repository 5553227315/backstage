package com.example.filmback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.Cinema;
import com.example.filmback.entity.Seat;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.service.ICinemaService;
import com.example.filmback.service.ISeatService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.filmback.service.IHallService;
import com.example.filmback.entity.Hall;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/hall")
public class HallController {
    @Resource
    private IHallService hallService;

    @Resource
    private ICinemaService cinemaService;

    @Resource
    private ISeatService seatService;


    /**
     * 新增或者更新
     * @param hall 场次类
     * @return 是否保存成功
     */
    @PostMapping
    public Result save(@RequestBody Hall hall) {

        //判定新增还是更新
        if (hall.getHallId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            hall.setHallId(id);
            //新建空座椅160标准
            Seat seat = new Seat();
            seat.setHallId(hall.getHallId());
            seat.setHallNumber(hall.getHallNumber());
            //新建场次的时候，初始化一个10*16的影厅座位
            for (int i=-4;i<6;i++){
                for (int j=-7;j<9;j++){
                    seat.setSeatId(idWorker.nextId());
                    seat.setSeatX(j);
                    seat.setSeatY(i);
                    seat.setSeatState(2);
                    seat.setSeatColor("#d2d2d2");
                    if(!seatService.save(seat)){
                        throw new ServiceException(Constants.CODE_201,"座位保存失败！");
                    }

                }
            }
        }
        if (hallService.saveOrUpdate(hall)){
            return Result.success("操作成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"操作失败！");
        }
    }

    @GetMapping("/saveall")
    public boolean savaall() {
        List<Cinema> cinemaList = cinemaService.list();
        QueryWrapper<Hall> queryWrapper =new QueryWrapper<>();
        Cinema cinema;
        for (Cinema value : cinemaList) {
            queryWrapper.eq("CINEMA_ID", value.getCinemaId());
            List<Hall> hallList = hallService.list(queryWrapper);
            List<String> cinemaType = new ArrayList<>();
            for (Hall hall : hallList) {
                int a = 0;
                if (cinemaType.size() != 0) {
                    for (String s : cinemaType) {
                        if (s.equals(hall.getCinemaType())) {
                            a = 1;
                            break;
                        }

                    }
                }
                if (a == 0) {
                    cinemaType.add(hall.getCinemaType());
                }
            }
            cinema = value;
            cinema.setCinemaType(StringUtils.join(cinemaType, ","));
            queryWrapper.clear();
            cinemaService.saveOrUpdate(cinema);
        }
        return true;
    }

    /**
     * 删除
     * @param hallId 场次ID
     * @return 返回删除是否成功
     */
    @DeleteMapping("/{hallId}")
    public Result delete(@PathVariable Long hallId) {
        QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
        seatQueryWrapper.eq("HALL_ID",hallId);
        if (!seatService.remove(seatQueryWrapper)){
            throw new ServiceException(Constants.CODE_201,"删除座位失败！");
        }else {
            if (!hallService.removeById(hallId)){
                throw new ServiceException(Constants.CODE_201,"删除失败！");
            }else {
                return Result.success("删除成功",true);
            }
        }
    }

    /**
     * 通过影院ID查询该影院有的影厅类型
     * @param cinemaId 影院ID
     * @return 返回影院影厅类型
     */
    @GetMapping("/cinemaType")
    public Result cinemaType(@RequestParam String cinemaId){
        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        cinemaQueryWrapper.eq("CINEMA_ID",cinemaId);
        List<Cinema> cinemaList = cinemaService.list(cinemaQueryWrapper);
        if (cinemaList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到该影院！");
        }else {
            return Result.success("查询成功！",Arrays.asList(cinemaList.get(0).getCinemaType().split(",")));
        }

    }

    //查询所有
    @GetMapping
    public List<Hall> findall() {
        return hallService.list();
    }

    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param cinemaName 影院名称
     * @return 返回场次列表
     */
    @GetMapping("/hallPage")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String cinemaName

    ) {
        Page<Hall> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Hall> queryWrapper = new QueryWrapper<>();
        if (!"".equals(cinemaName)) {
            queryWrapper.like("CINEMA_NAME", cinemaName);
        }
        queryWrapper.orderByAsc("CINEMA_NAME","HALL_NUMBER");
        Page<Hall> hallPage = hallService.page(page, queryWrapper);
        if (hallPage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404,"查询失败");
        }else {
            return Result.success("查询成功",hallPage);
        }

    }
}
