package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.entity.SnowflakeIdWorker;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

import com.example.filmback.service.IFilmService;
import com.example.filmback.entity.Film;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 电影信息表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/film")
public class FilmController {
    @Resource
    private IFilmService filmService;

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody Film film){

        //判定新增还是更新
        if (film.getFilmId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            film.setFilmId(id);
            }
        return filmService.saveOrUpdate(film);
    }

    //删除
    @DeleteMapping("/{filmId}")
    public boolean delete(@PathVariable Long filmId){
        return filmService.removeById(filmId);
    }


    //查询所有
    @GetMapping
    public List<Film> findall(){
        return filmService.list();
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/filmPage")
    public Page<Film> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                //没有也可以查询
                                @RequestParam(defaultValue = "") String filmName,
                               @RequestParam(defaultValue = "") String filmSource){
        Page<Film> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        if(!"".equals(filmName)){
            queryWrapper.like("FILM_NAME",filmName);
        }
        if(!"".equals(filmSource)){
            queryWrapper.like("FILM_SOURCE",filmSource);
        }

        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_FILM");
        return filmService.page(page,queryWrapper);
        }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<Film> list = filmService.list();
        //通过工具类创建writer 写出磁盘路径
//        ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("filmId","FILM_ID");
        writer.addHeaderAlias("filmName", "电影名称");
        writer.addHeaderAlias("director", "导演");
        writer.addHeaderAlias("filmLength", "片长（分钟）");
        writer.addHeaderAlias("filmType", "电影类型");
        writer.addHeaderAlias("filmSource", "片源地");
        writer.addHeaderAlias("plotIntro", "电影介绍");
        writer.addHeaderAlias("canShow", "是否上映");
        writer.addHeaderAlias("releaseDate", "上映时间");
        writer.addHeaderAlias("createFilm", "创建时间");
        writer.addHeaderAlias("performer", "演员");
        writer.addHeaderAlias("filmLanguage", "电影语言");



        //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("电影信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }


}

