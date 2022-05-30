package com.example.filmback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.Seat;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.example.filmback.service.ISeatShowingsService;
import com.example.filmback.entity.SeatShowings;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-04-22
 */
@RestController
@RequestMapping("/seat-showings")
public class SeatShowingsController {
    @Resource
    private ISeatShowingsService seatShowingsService;

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody SeatShowings seatShowings) {

        //判定新增还是更新
        if (seatShowings.getSeatId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            seatShowings.setSeatId(id);
        }
        return seatShowingsService.saveOrUpdate(seatShowings);
    }

    //删除
    @DeleteMapping("/{seatShowingsId}")
    public boolean delete(@PathVariable Long seatShowingsId) {
        return seatShowingsService.removeById(seatShowingsId);
    }

    //设置座位
    @GetMapping("/setSeat")
    public boolean setSeat(){
        SeatShowings seatShowings = new SeatShowings();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String showingsId = "188888888888888888";
        seatShowings.setShowingsId(Long.valueOf(showingsId));
        for (int i=0;i<10;i++){
            for (int j=0;j<16;j++){
                seatShowings.setSeatId(idWorker.nextId());
                seatShowings.setSeatX(j);
                seatShowings.setSeatY(i);
                seatShowings.setSeatState(0);
                seatShowingsService.saveOrUpdate(seatShowings);
            }
        }

        return true;
    }

    /**
     * 通过场次查找座位
     * @param showingsId 场次ID
     * @return 返回座位每列列表
     */
    @GetMapping("/showingsId")
    public Result findseat(@RequestParam String showingsId){
        QueryWrapper<SeatShowings> showingsQueryWrapper= new QueryWrapper<>();
        showingsQueryWrapper.eq("SHOWINGS_ID",showingsId);
        //正序查找
        showingsQueryWrapper.orderByDesc("SEAT_Y");
        showingsQueryWrapper.orderByAsc("SEAT_X");

        List<SeatShowings> showings=seatShowingsService.list(showingsQueryWrapper);
        if(showings.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到");
        }else {
            SeatShowings seatTop = showings.get(0);
            SeatShowings seatBot = showings.get(showings.size()-1);
            // 个数为头减尾+1
            int y = seatTop.getSeatY()-seatBot.getSeatY()+1;
            int x = seatBot.getSeatX()-seatTop.getSeatX()+1;
            List<List> seatList = new ArrayList<>();
            for (int i = 0; i < y; i++) {
                List<SeatShowings> YList = new ArrayList<>();
                for (int j = 0; j < x; j++) {
                    YList.add(showings.get(i * x + j));
                }
                seatList.add(YList);
            }
            return Result.success("查询成功",seatList);
        }


    }

    /**
     * 改变座位状态
     * @param seatShowings 座椅列表
     * @return 返回是否改变
     */
    @PostMapping("/seatState")
    public Result seatState(@RequestBody List<SeatShowings> seatShowings){
        for (int i=0; i<seatShowings.size(); i++) {

            if(!seatShowingsService.update(seatShowings.get(i),
                    new QueryWrapper<SeatShowings>().eq("SEAT_ID",seatShowings.get(i).getSeatId())
                            .eq("SHOWINGS_ID",seatShowings.get(i).getShowingsId()))){
                throw new ServiceException(Constants.CODE_201,"更新失败");
            }
        }
        return Result.success("更新成功",true);
    }
    //查询所有
    @GetMapping
    public List<SeatShowings> findall() {
        return seatShowingsService.list();
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/seatShowingsPage")
    public Page<SeatShowings> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize
                                       //没有也可以查询
                                       //@RequestParam(defaultValue = "") String seatShowingsName,
                                       //@RequestParam(defaultValue = "") String userAddress,
                                       //@RequestParam(defaultValue = "") String tel
    ) {
        Page<SeatShowings> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SeatShowings> queryWrapper = new QueryWrapper<>();


        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_TIME");
        return seatShowingsService.page(page, queryWrapper);

    }

}