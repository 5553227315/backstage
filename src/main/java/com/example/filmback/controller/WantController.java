package com.example.filmback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.filmback.service.IWantService;
import com.example.filmback.entity.Want;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/want")
public class WantController {
    @Resource
    private IWantService wantService;

    //新增
    @GetMapping("/saveWant")
    public Result save(@RequestParam Long filmId,
                       @RequestParam Long userId){
        Want want = new Want();
        want.setFilmId(filmId);
        want.setUserId(userId);
        want.setIsWant(1);

        if (wantService.save(want)){
            return Result.success("保存成功",want);
        }else {
            throw new ServiceException(Constants.CODE_201,"保存失败");
        }
    }

    /**
     * 删除
     * @param filmId 电影ID
     * @param userId 用户ID
     * @return 是否删除成功
     */
    @DeleteMapping("/deleWant")
    public Result delete(@RequestParam Long filmId,
                          @RequestParam Long userId){

        System.out.println(filmId+"和"+userId);
        if (wantService.remove(new QueryWrapper<Want>().eq("FILM_ID",filmId).eq("USER_ID",userId))){
            return Result.success("取消成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"取消失败！");
        }
    }

    /**
     *
     * @param filmId 查询的电影ID
     * @return 先看人数
     */
    @GetMapping("/wangSize")
    public Result size(@RequestParam Long filmId){
        return Result.success("查询成功",wantService.list(new QueryWrapper<Want>().eq("FILM_ID",filmId)).size());
    }

    /**
     * 查找用户是否想看
     * @param filmId 电影ID
     * @param userId 用户ID
     * @return 返回想看
     */
    @GetMapping("/findWant")
    public Result findWant(@RequestParam Long filmId,
                           @RequestParam Long userId){
        List<Want> wants = wantService.list(new QueryWrapper<Want>().eq("FILM_ID",filmId).eq("USER_ID",userId));
        if (wants.size()!=0) {
            return Result.success("查询成功",wants.get(0));
        }else {
            throw new ServiceException(Constants.CODE_404,"未找到");
        }
    }


    @GetMapping("/suaveeee")
    public boolean saveeeee( Long filmId,Integer num){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        Want want = new Want();
        for (int i=0; i<num;i++){
            Long id = idWorker.nextId();
            want.setIsWant(1);
            want.setFilmId(filmId);
            want.setUserId(id);
            wantService.save(want);
        }
        return true;
    }
    //查询所有
    @GetMapping
    public List<Want> findall(){
        return wantService.list();
    }

}

