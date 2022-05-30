package com.example.filmback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.exception.ServiceException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.example.filmback.service.ISeatService;
import com.example.filmback.entity.Seat;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Resource
    private ISeatService seatService;

    /**
     * 更新座椅
     * @param seatList 座位列表
     * @return 是否保存成功
     */
    @PostMapping
    public Result save(@RequestBody List<Seat> seatList) {
        Long hallId = seatList.get(0).getHallId();
        QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
        seatQueryWrapper.eq("HALL_ID",hallId);
        if (!seatService.remove(seatQueryWrapper)) {
            throw new ServiceException(Constants.CODE_201, "更新座椅失败");
        }
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (Seat seat : seatList) {
            //判定新增还是更新
            if (seat.getSeatId() == null) {
                //生成唯一ID
                Long id = idWorker.nextId();
                seat.setSeatId(id);
            }
            if (!seatService.save(seat)){
                throw new ServiceException(Constants.CODE_201,"座椅保存失败");
            }
        }
        return Result.success("保存成功",true);
    }


    //删除
    @DeleteMapping("/{seatId}")
    public boolean delete(@PathVariable Long seatId) {
        return seatService.removeById(seatId);
    }

    /**
     * 设置座位
     * @param hallId 场次ID
     * @param hallNumber 场次名称
     * @return 是否新建成功
     */
    @GetMapping("/setSeat")
    public Result setSeat(@RequestParam String hallId,
                           @RequestParam String hallNumber){
        Seat seat = new Seat();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

        seat.setHallId(Long.valueOf(hallId));
        seat.setHallNumber(hallNumber);
        seat.setSeatColor("#d2d2d2");

        //设置一个宽16长10的初始化场次
        for (int i = -4; i < 6; i++) {
            for (int j = -7; j < 9; j++) {
                seat.setSeatId(idWorker.nextId());
                seat.setSeatX(j);
                seat.setSeatY(i);
                seat.setSeatState(2);
                if (!seatService.save(seat)){
                    throw new ServiceException(Constants.CODE_201,"初始化错误");
                }
            }
        }
        return Result.success("初始化成功",true);
    }

    /**
     * 通过场次ID查找座位
     * @param hallId 场次ID
     * @return 返回座椅列表
     */
    @GetMapping("/hallId")
    public Result findseat(@RequestParam String hallId){
        QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
        seatQueryWrapper.eq("HALL_ID",hallId);
        //正序查找
        seatQueryWrapper.orderByDesc("SEAT_Y");
        seatQueryWrapper.orderByAsc("SEAT_X");

        List<List> seatList = new ArrayList<>();
        List<Seat> seats=seatService.list(seatQueryWrapper);
        if(seats.size()==0){
            throw new ServiceException(Constants.CODE_404,"该场次没有设置座椅");
        }else {
            Seat seatTop = seats.get(0);
            Seat seatBot = seats.get(seats.size()-1);
//        头减尾+1
            int y = seatTop.getSeatY()-seatBot.getSeatY()+1;
            int x = seatBot.getSeatX()-seatTop.getSeatX()+1;

            for (int i = 0; i < y; i++) {
                List<Seat> YList = new ArrayList<>();
                for (int j = 0; j < x; j++) {
                    YList.add(seats.get(i * x + j));
                }
                seatList.add(YList);
            }

            return Result.success("查询成功",seatList);
        }

    }

    //查询所有
    @GetMapping
    public List<Seat> findall() {
        return seatService.list();
    }




}

