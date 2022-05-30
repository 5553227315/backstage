package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.entity.User;
import com.example.filmback.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.filmback.service.ICinemaService;
import com.example.filmback.entity.Cinema;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 影院信息表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Resource
    private ICinemaService cinemaService;

    /**
     * 新增或者更新
     * @param cinema 影院类
     * @return 保存是否成功
     */
    @PostMapping()
    public Result save(@RequestBody Cinema cinema) {

        //判定新增还是更新
        if (cinema.getCinemaId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            cinema.setCinemaId(id);
        }
        if(cinemaService.saveOrUpdate(cinema)){
            return Result.success("操作成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"操作失败！");
        }
    }

    /**
     * 通过影院ID查询影院信息
     * @param cinemaId 影院ID
     * @return 返回影院信息
     */
    @GetMapping("/cinemaId")
    public Result cinemaInfo(@RequestParam String cinemaId){
        List<Cinema> cinemas = cinemaService.list(new QueryWrapper<Cinema>().eq("CINEMA_ID",cinemaId));
        if (cinemas.size()==0){
            throw new ServiceException(Constants.CODE_201,"没有该影院");
        }else {
            return Result.success("查询成功",cinemas.get(0));
        }
    }

    /**
     * 删除
     * @param cinemaId 影院ID
     * @return 是否删除
     */
    @DeleteMapping("/{cinemaId}")
    public Result delete(@PathVariable Long cinemaId){
        if(cinemaService.removeById(cinemaId)){
            return Result.success("删除成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"删除失败！");
        }
    }


    /**
     * 查询所有影院
     * @return 返回影院列表
     */
    @GetMapping
    public Result findall(){
        List<Cinema> cinemaList = cinemaService.list();
        if (cinemaList.size()==0){
            throw new ServiceException(Constants.CODE_201,"影院列表为空");
        }else {
            return Result.success("查询成功",cinemaList);

        }
    }

    /**
     * 多字段查询影院
     * @param search 搜索关键字
     * @return 返回影院列表
     */
    @GetMapping("/searchCinema")
    public Result filmList(@RequestParam String search){
        List<Cinema> cinemaList = cinemaService.list(new QueryWrapper<Cinema>()
                .and(queryWrapper -> queryWrapper
                        .like("CINEMA_NAME",search)
                        .or().like("CINEMA_ADDRESS",search)));
        if (cinemaList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到");
        }else {
            return Result.success("查询成功",cinemaList);
        }
    }

    /**
     *  通过影院ID查找影院数据
     * @param cinemaId 影院ID
     * @return 返回影院数据
     */
    @GetMapping("/cinemaLocation")
    public Result findCinemaLocation(@RequestParam Long cinemaId){
        List<Cinema> cinemas = cinemaService.list(new QueryWrapper<Cinema>()
                .eq("CINEMA_ID",cinemaId));
        if (cinemas.size()==0){
            throw new ServiceException(Constants.CODE_404,"该影院已经下线");
        }else {
            return Result.success("查找成功!",cinemas.get(0));
        }

    }
    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param cinemaName 影院名称
     * @param cinemaAddress 影院地址
     * @return 影院列表
     */
    @GetMapping("/cinemaPage")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                //没有也可以查询
                                @RequestParam(defaultValue = "") String cinemaName,
                                @RequestParam(defaultValue = "") String cinemaAddress
        ){
        Page<Cinema> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        if(!"".equals(cinemaName)){
            queryWrapper.like("CINEMA_NAME",cinemaName);
        }
        if (!"".equals(cinemaAddress)){
            queryWrapper.like("CINEMA_ADDRESS",cinemaAddress);
        }

        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_CINEMA");
        Page<Cinema> cinemaPage = cinemaService.page(page,queryWrapper);
        if (cinemaPage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404, "列表为空！");
        } else {
            return Result.success("查询成功!", cinemaPage);
        }

    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<Cinema> list = cinemaService.list();
        //通过工具类创建writer 写出磁盘路径
//        ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("CINEMA_NAME", "影院名");
        writer.addHeaderAlias("CINEMA_ADDRESS", "地址");
        writer.addHeaderAlias("BOTTOM_PRICE", "影厅类型");
        writer.addHeaderAlias("CINEMA_LOCATION", "影院坐标");
        writer.addHeaderAlias("CINEMA_TEL", "影院电话");
        writer.addHeaderAlias("CREATE_CINEMA", "创建时间");

        //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("影院信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }


}

