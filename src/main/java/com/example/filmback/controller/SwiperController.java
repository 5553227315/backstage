package com.example.filmback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.Files;
import com.example.filmback.entity.Swiper;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.mapper.FilesMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.websocket.EncodeException;
import java.io.File;
import java.util.List;

import com.example.filmback.service.ISwiperService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/Swiper")
public class SwiperController {
    @Resource
    private FilesMapper filesMapper;

    @Resource
    private ISwiperService SwiperService;

    /**
     * 新增轮播图
     * @param filesUrl 图片URL
     * @param swiperRank 图片排名
     * @return 是否新增成功
     */
    @GetMapping("/save")
    public Result save(@RequestParam String filesUrl,
                        @RequestParam Integer swiperRank){

        Long filesId = filesMapper.selectList(new QueryWrapper<Files>().eq("FILES_URL",filesUrl)).get(0).getFilesId();
        Swiper swiper = new Swiper();
        swiper.setFilesId(filesId);
        swiper.setFilesUrl(filesUrl);
        swiper.setSwiperRank(swiperRank);
        List<Swiper> swiperList = SwiperService.list(new QueryWrapper<Swiper>().orderByAsc("SWIPER_RANK"));
        for (int i = swiperRank; i<swiperList.size(); i++){
            Swiper swiper1 = swiperList.get(i);
            swiper1.setSwiperRank(i+1);
            SwiperService.updateById(swiper1);
        }
        boolean flag =  SwiperService.save(swiper);
        if (flag){
            return Result.success("添加成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"添加失败！");
        }

//        return SwiperService.saveOrUpdate(filesId);
    }

    /**
     * 改变轮播图排名
     * @param swiperRank 轮播图位置
     * @param move 前移还是后移
     * @return 是否移动成功
     */
    @GetMapping("/update")
    public Result update(@RequestParam Integer swiperRank,@RequestParam String move){
        Swiper swiper1 = SwiperService.list(new QueryWrapper<Swiper>().eq("SWIPER_RANK",swiperRank)).get(0);
        Swiper swiper2 = new Swiper();
        if (move.equals("front")){
            swiper2 = SwiperService.list(new QueryWrapper<Swiper>().eq("SWIPER_RANK",swiperRank-1)).get(0);
            swiper1.setSwiperRank(swiperRank-1);
            swiper2.setSwiperRank(swiperRank);
        }else if (move.equals("after")){
            swiper2 = SwiperService.list(new QueryWrapper<Swiper>().eq("SWIPER_RANK",swiperRank+1)).get(0);
            swiper1.setSwiperRank(swiperRank+1);
            swiper2.setSwiperRank(swiperRank);
        }
        boolean flag = SwiperService.updateById(swiper1) && SwiperService.updateById(swiper2);
        if (flag){
            return Result.success("移动成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"移动失败！");
        }
    }

    /**
     * 删除
     * @param filesId 要删除轮播图的ID
     * @return 是否删除
     */
    @DeleteMapping("/{filesId}")
    public Result delete(@PathVariable Long filesId){
        Swiper swiper = SwiperService.list(new QueryWrapper<Swiper>().eq("FILES_ID",filesId)).get(0);
        String fileUrl = swiper.getFilesUrl();
        Integer swiperRank = swiper.getSwiperRank();

//        String serverUrl = "http://47.106.198.145:9090";
        String serverUrl = "http://localhost:9090";
//        String serverAddress = "/home";
        String serverAddress = "D:/MyLife/GraduationProject/CinemaTicket/backstage";
        this.delServerFile(fileUrl,serverUrl,serverAddress);
        filesMapper.delete(new QueryWrapper<Files>()
                .eq("FILES_ID",filesId));

        List<Swiper> swiperList = SwiperService.list(new QueryWrapper<Swiper>().orderByAsc("SWIPER_RANK"));
        for (int i = swiperRank+1; i<swiperList.size(); i++){
            Swiper swiper1 = swiperList.get(i);
            swiper1.setSwiperRank(i-1);
            SwiperService.updateById(swiper1);
        }
        if(SwiperService.removeById(filesId)){
            return Result.success("删除成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"删除失败！");
        }


    }


    /**
     * 删除服务器上的文件
     *
     * @param fileUrl       文件URL
     * @param serverUrl     公网路径
     * @param serverAddress 服务器存储地址
     */
    public  void delServerFile(String fileUrl,String serverUrl,String serverAddress) {
        //用图片url换取真实地址

        fileUrl = fileUrl.replace(serverUrl,serverAddress);
        File file = new File(fileUrl);
        System.out.println("这是文件"+file);
        boolean flag = false;
        System.out.println("文件是否存在"+file.exists());

        //判断路径名标示的文件或目录是否存在
        if (file.exists()) {
            flag = file.isFile() && file.delete();
            System.out.println("这是flag3"+flag);
        }
        if (!flag) {
            throw new ServiceException(Constants.CODE_201,"删除失败");
        }
    }

    /**
     * 查询所有轮播图
     * @return 是否查询成功
     */
    @GetMapping
    public Result findall(){
        List<Swiper> swipers = SwiperService.list(new QueryWrapper<Swiper>().orderByAsc("SWIPER_RANK"));
        if (swipers.size()!=0){
            return Result.success("查询成功！",swipers);
        }else {
            throw new ServiceException(Constants.CODE_201,"查询失败！");
        }

    }



}

