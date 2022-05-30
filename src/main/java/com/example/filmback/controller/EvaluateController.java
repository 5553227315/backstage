package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.SeatShowings;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.entity.User;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.service.IUserService;
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
    @Resource
    private IUserService userService;

    //新增或者更新
    @PostMapping
    public Result save(@RequestBody Evaluate evaluate) {

        //判定新增还是更新
        if (evaluate.getEvaluateId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            evaluate.setEvaluateId(id);
        }
        evaluateService.saveOrUpdate(evaluate);
        return Result.success("新增成功",evaluate);
    }

    //查询某个订单的评价
    @GetMapping("/haveEvaluate")
    public Result findEvaluate(@RequestParam String billId) {
        QueryWrapper<Evaluate> evaluateQueryWrapper =new QueryWrapper<>();
        evaluateQueryWrapper.eq("BILL_ID",billId);
        if(evaluateService.list(evaluateQueryWrapper).size()==0){
            throw new ServiceException(Constants.CODE_201,"该订单无评论");
        }else {
            return Result.success("查询成功",evaluateService.list(evaluateQueryWrapper).get(0));
        }
    }

    /**
     * 查询电影的评论
     * @param filmId 电影ID
     * @return 返回该电影评论
     */
    @GetMapping("/filmEva")
    public Result filmEva(@RequestParam Long filmId){
        List<Evaluate> evaluates = evaluateService.list(new QueryWrapper<Evaluate>().eq("FILM_ID",filmId));
        if (evaluates.size()==0){
            throw new ServiceException(Constants.CODE_404,"该电影暂无评论");
        }else {
            for (Evaluate evaluate:evaluates){
                evaluate.setAvatarUrl(userService.list(new QueryWrapper<User>().eq("USER_ID",evaluate.getUserId())).get(0).getAvatarUrl());
            }
            return Result.success("查询成功",evaluates);
        }
    }
    /**
     * 删除评论
     * @param evaluateId 评论ID
     * @return 是否删除
     */
    @DeleteMapping("/{evaluateId}")
    public Result delete(@PathVariable Long evaluateId) {

        if(evaluateService.removeById(evaluateId)){
            return Result.success("删除成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"删除失败");
        }
    }


    //查询所有
    @GetMapping
    public List<Evaluate> findall() {
        return evaluateService.list();
    }

    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param userName 用户名称
     * @param filmName 电影名称
     * @param userTel 用户账号
     * @return 返回评论列表
     */
    @GetMapping("/evaluatePage")
    public Result findPage(@RequestParam Integer pageNum,
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
        Page<Evaluate> evaluatePage = evaluateService.page(page, queryWrapper);
        if (evaluatePage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404,"查询失败");
        }else {
            return Result.success("查询成功",evaluatePage);
        }
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

