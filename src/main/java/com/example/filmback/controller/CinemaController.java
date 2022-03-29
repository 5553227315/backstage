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

    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody Cinema cinema){

        //判定新增还是更新
        if (cinema.getCinemaId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            cinema.setCinemaId(id);
            }
        return cinemaService.saveOrUpdate(cinema);
    }

    //删除
    @DeleteMapping("/{cinemaId}")
    public boolean delete(@PathVariable Long cinemaId){
        return cinemaService.removeById(cinemaId);
    }


    //查询所有
    @GetMapping
    public List<Cinema> findall(){
        return cinemaService.list();
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/cinemaPage")
    public Page<Cinema> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                //没有也可以查询
                                @RequestParam(defaultValue = "") String cinemaName,
                                @RequestParam(defaultValue = "") String cinemaAddress
                                //@RequestParam(defaultValue = "") String tel
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
        return cinemaService.page(page,queryWrapper);

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

