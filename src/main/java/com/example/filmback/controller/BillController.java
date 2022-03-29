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

import com.example.filmback.service.IBillService;
import com.example.filmback.entity.Bill;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author Wyz
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    @Resource
    private IBillService billService;

    //删除
    @DeleteMapping("/{billId}")
    public boolean delete(@PathVariable Long billId){return billService.removeById(billId);}

    //查询所有
    @GetMapping
    public List<Bill> findall(){return billService.list();}

    //分页查询 mybatis-plus方法
    @GetMapping("/billPage")
    public Page<Bill> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                                //没有也可以查询
                               @RequestParam(defaultValue = "") String filmName,
                               @RequestParam(defaultValue = "") String cinemaName,
                               @RequestParam(defaultValue = "") String userName,
                               @RequestParam(defaultValue = "") String userTel
        ){
        Page<Bill> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)){
            queryWrapper.like("FILM_NAME",filmName);
        }if (!"".equals(cinemaName)){
            queryWrapper.like("CINEMA_NAME",cinemaName);
        }if (!"".equals(userName)){
            queryWrapper.like("USER_NAME",userName);
        }
        if (!"".equals(userTel)){
            queryWrapper.like("TEL",userTel);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_BILL");
        return billService.page(page,queryWrapper);

        }
@GetMapping("/export")
public void export(HttpServletResponse response) throws Exception {

        //从数据库查询所有的数据
        List<Bill> list = billService.list();
        //通过工具类创建writer 写出磁盘路径
        //ExcelWriter writer = Exception.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义别名
        writer.addHeaderAlias("BILL_ID", "订单ID");
        writer.addHeaderAlias("USER_ID", "用户ID");
        writer.addHeaderAlias("USER_NAME", "用户名称");
        writer.addHeaderAlias("USER_TEL", "用户账号");
        writer.addHeaderAlias("FILM_ID", "电影ID");
        writer.addHeaderAlias("FILM_NAME", "电影名");
        writer.addHeaderAlias("FILM_LANGUAGE", "语言");
        writer.addHeaderAlias("SHOWINGS_VISION", "视觉：（2；3）D");
        writer.addHeaderAlias("CINEMA_ID", "影院ID");
        writer.addHeaderAlias("CINEMA_NAME", "影院名");
        writer.addHeaderAlias("CINEMA_ADDRESS", "地址");
        writer.addHeaderAlias("HALL_NUMBER", "放映厅号");
        writer.addHeaderAlias("HALL_POSITION", "座位位置");
        writer.addHeaderAlias("FILMSTART_TIME", "开始时间");
        writer.addHeaderAlias("FILMEND_TIME", "结束时间");
        writer.addHeaderAlias("BILL_PRICE", "价格");
        writer.addHeaderAlias("BILL_NUMBER", "订单数量");

    //一次性写出list对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("订单信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
        }


}

