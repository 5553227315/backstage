package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

import com.example.filmback.service.IEvaluateService;
import com.example.filmback.entity.Evaluate;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户评价表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private IEvaluateService evaluateService;


    //删除
    @DeleteMapping("/{evaluateId}")
    public boolean delete(@PathVariable Long evaluateId) {
        return evaluateService.removeById(evaluateId);
    }


    //查询所有
    @GetMapping
    public List<Evaluate> findall() {
        return evaluateService.list();
    }

    //分页查询 mybatis-plus方法
    @GetMapping("/evaluatePage")
    public Page<Evaluate> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   //没有也可以查询
                                   @RequestParam(defaultValue = "") String userName,
                                   @RequestParam(defaultValue = "") String filmName,
                                   @RequestParam(defaultValue = "") String userTel

                                   ) {
        Page<Evaluate> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        if(!"".equals(userName)){
            queryWrapper.like("USER_NAME",userName);
        }
        if(!"".equals(filmName)){
            queryWrapper.like("FILM_NAME",filmName);
        }
        if (!"".equals(userTel)) {
            queryWrapper.like("USER_TEL", userTel);
        }

        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_EVALUATE");
        return evaluateService.page(page, queryWrapper);

    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<Evaluate> list = evaluateService.list();
        //通过工具类创建writer 写出磁盘路径
//        ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("USER_NAME", "用户名");
        writer.addHeaderAlias("FILM_NAEM", "电影名称");
        writer.addHeaderAlias("SCORE", "电影评分");
        writer.addHeaderAlias("USER_EVALUATE", "用户评价");

        //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户评价信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }


}

