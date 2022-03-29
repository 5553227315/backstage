package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.entity.*;
import com.example.filmback.service.IEvaluateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

import com.example.filmback.service.IShowingsService;
import com.example.filmback.service.ICinemaService;
import com.example.filmback.service.IFilmService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 影院场次表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/showings")
public class ShowingsController {
    @Resource
    private IShowingsService showingsService;
    @Resource
    private IFilmService filmService;
    @Resource
    private ICinemaService cinemaService;
    @Resource
    private IEvaluateService evaluateService;

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody Showings showings) {

        //判定新增还是更新
        if (showings.getShowingsId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            showings.setShowingsId(id);
        }
        return showingsService.saveOrUpdate(showings);
    }



    //删除
    @DeleteMapping("/{showingsId}")
    public boolean delete(@PathVariable Long showingsId) {
        return showingsService.removeById(showingsId);
    }


    //查询所有
    @GetMapping
    public List<Showings> findall() {
        return showingsService.list();
    }

    //查询电影
    @GetMapping("/filmName")
    public List<Film> findfilm(@RequestParam(defaultValue = "") String filmName){
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)) {
            queryWrapper.like("FILM_NAME", filmName);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_FILM");
        return filmService.list(queryWrapper);
    }

    //查询评价
    @GetMapping("/evaluate")
    public ReturnInfo findevaluate(@RequestParam(defaultValue = "") String filmName){
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)) {
            queryWrapper.like("FILM_NAME", filmName);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_EVALUATE");
        List<Evaluate> score = evaluateService.list(queryWrapper);
        ReturnInfo returnInfo = new ReturnInfo();
        float getscore = 0;
        if (score.size()==0){
            returnInfo.setScore("尚未评价");
        }else {
            for (int i=0;i<score.size();i++){
                getscore = getscore+Float.parseFloat(score.get(i).getFilmScore());
            }
            returnInfo.setScore(String.valueOf((float)(Math.round((getscore/score.size())*100))/100));
        }
        return returnInfo;
    }
    //查询影院
    @GetMapping("/cinemaName")
    public List<Cinema> findcinema(@RequestParam(defaultValue = "") String cinemaName){
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        if (!"".equals(cinemaName)) {
            queryWrapper.like("CINEMA_NAME", cinemaName);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_CINEMA");
        return cinemaService.list(queryWrapper);
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/showingsPage")
    public Page<Showings> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   //没有也可以查询
                                   @RequestParam(defaultValue = "") String filmName,
                                   @RequestParam(defaultValue = "") String cinemaName
    ) {
        Page<Showings> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Showings> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)) {
            queryWrapper.like("FILM_NAME", filmName);
        }
        if (!"".equals(cinemaName)) {
            queryWrapper.like("CINEMA_NAME", cinemaName);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_SHOWINGS");
        return showingsService.page(page, queryWrapper);

    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<Showings> list = showingsService.list();
        //通过工具类创建writer 写出磁盘路径
//        ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("showingsId", "场次ID");
        writer.addHeaderAlias("cinemaId", "影院ID");
        writer.addHeaderAlias("cinemaName", "影院名");
        writer.addHeaderAlias("filmId", "电影ID");
        writer.addHeaderAlias("filmName", "电影名");
        writer.addHeaderAlias("filmScore", "电影评分");
        writer.addHeaderAlias("starTime", "开始时间");
        writer.addHeaderAlias("endTime", "结束时间");
        writer.addHeaderAlias("filmLanguage", "语言");
        writer.addHeaderAlias("showingsVision", "视觉：2D；3D");
        writer.addHeaderAlias("hallNumber", "放映厅号");
        writer.addHeaderAlias("minPrice", "最低价格");


        //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }


}

