package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.*;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.service.IEvaluateService;
import com.example.filmback.service.IUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.filmback.service.IFilmService;

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

    @Resource
    private IEvaluateService evaluateService;

    @Resource
    private IUserService userService;

    /**
     * 新增或者更新
     * @param film 电影类
     * @return 返回操作是否成功
     */
    @PostMapping
    public Result save(@RequestBody Film film){

        //判定新增还是更新
        if (film.getFilmId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            Long id = idWorker.nextId();
            film.setFilmId(id);
            }
        if (filmService.saveOrUpdate(film)){
            return Result.success("操作成功",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"操作失败");
        }
    }


    /**
     * 通过名单ID删除订单
     * @param filmId 订单ID
     * @return 是否删除
     */
    @DeleteMapping("/{filmId}")
    public Result delete(@PathVariable Long filmId) {
        if (filmService.removeById(filmId)){
            return Result.success("删除成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"删除失败！");
        }
    }


    @GetMapping("/eve")
    public Result eve(){

        List<Film> films = filmService.list(new QueryWrapper<Film>().eq("CAN_SHOW",1));
        if (films.size()==0){
            throw new ServiceException(Constants.CODE_404,"没找到");
        }
        List<User> users = userService.list();
        String[] eve = {"挺好看的！","不错","很好看","目前感觉还很不错","剧情无尿点，一点都不狗血"};
        // 生成 Random 对象
        Random random = new Random();
        SnowflakeIdWorker worker = new SnowflakeIdWorker(0,0);
        for (Film film:films){
//        Film film = filmService.list(new QueryWrapper<Film>().eq("FILM_ID",filmId)).get(0);
//            for (int i=0;i<10;i++) {
                User user=users.get(random.nextInt(65));
                Evaluate evaluate = new Evaluate();
                evaluate.setEvaluateId(worker.nextId());
                evaluate.setUserId(user.getUserId());
                evaluate.setUserName(user.getUserName());
                evaluate.setUserTel(user.getUserTel());
                evaluate.setFilmId(film.getFilmId());
                evaluate.setFilmName(film.getFilmName());
                Float f = random.nextFloat();
                evaluate.setFilmScore(String.format("%.2f",(f*3+7)));
                evaluate.setBillId(worker.nextId());
                evaluate.setUserEvaluate(eve[random.nextInt(4)]);
                evaluateService.save(evaluate);
            }

//        }
        return Result.success("成功",true);

    }
    //查询所有
    @GetMapping
    public List<Film> findall(){
        return filmService.list();
    }

    /**
     * 查询电影资料
     * @param filmId 电影ID
     * @return 返回电影资料
     */
    @GetMapping("/filmId")
    public Result findId(@RequestParam Long filmId){
        List<Film> films= filmService.list(new QueryWrapper<Film>().eq("Film_ID",filmId));
        if(films.size()==0){
            throw new ServiceException(Constants.CODE_404,"没有该电影资料");
        }else {
            return Result.success("查询成功",films.get(0));
        }

    }


    /**
     * 查询已上映电影
     * @return 返回已上映电影列表
     */
    @GetMapping("/canShow")
    public Result findShow(){
        List<Film> filmList = filmService.list(new QueryWrapper<Film>()
                .eq("CAN_SHOW","1")
                .orderByDesc("RELEASE_DATE"));
        if (filmList.size()==0){
            throw new ServiceException(Constants.CODE_201,"暂无已上映电影");
        }else {
            int maxlength=0;
            for (Film film:filmList){
                List list = (List) this.findevaluate(film.getFilmId()).getData();
                film.setFilmScore((String) list.get(0));
                if (maxlength<(Integer)list.get(1)){
                    maxlength=(Integer)list.get(1);
                }
                film.setEvaLength((Integer)list.get(1));
                film.setMaxlength(maxlength);
            }
            return Result.success("查询成功",filmList);
        }
    }

    //查询平均评分
    public Result findevaluate(Long filmId){
        System.out.println("这是电影ID"+filmId);
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("FILM_ID", filmId);
        List<Evaluate> score = evaluateService.list(queryWrapper);
        float getscore = 0;

        String  scores;
        if (score.size()==0){
            scores ="尚未评价";
        }else {
            for (int i=0;i<score.size();i++){
                getscore = getscore+Float.parseFloat(score.get(i).getFilmScore());
            }
            scores = String.valueOf((float)(Math.round((getscore/score.size())*100))/100);
        }
        List list = new ArrayList();
        list.add(scores);
        list.add(score.size());
        return Result.success("查询成功！",list);
    }


    /**
     * 查询待上映电影
     * @return 返回已上映电影列表
     */
    @GetMapping("/cantShow")
    public Result findCantShow(){
        List<Film> filmList = filmService.list(new QueryWrapper<Film>()
                .eq("CAN_SHOW","3")
                .orderByAsc("RELEASE_DATE"));
        if (filmList.size()==0){
            throw new ServiceException(Constants.CODE_201,"暂无待上映电影");
        }else {
            return Result.success("查询成功",filmList);
        }
    }


    /**
     * 多字段查询电影
     * @param search 搜索关键字
     * @return 返回电影列表
     */
    @GetMapping("/searchFilm")
    public Result filmList(@RequestParam String search){
        List<Film> filmList = filmService.list(new QueryWrapper<Film>().and(queryWrapper -> queryWrapper
                .like("FILM_NAME", search)
                .or().like("DIRECTOR", search)
                .or().like("PERFORMER", search))
                .orderByAsc("CAN_SHOW"));
        if (filmList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到");
        }else {
            return Result.success("查询成功",filmList);
        }
    }


    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param filmName 电影名称
     * @param filmSource 电影片源地
     * @param canfilmShow  是否上映
     * @return 查询列表
     */
    @GetMapping("/filmPage")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                //没有也可以查询
                                @RequestParam(defaultValue = "") String filmName,
                               @RequestParam(defaultValue = "") String filmSource,
                               @RequestParam(defaultValue = "") String canfilmShow){
        Page<Film> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        if(!"".equals(filmName)){
            queryWrapper.like("FILM_NAME",filmName);
        }
        if(!"".equals(filmSource)){
            queryWrapper.like("FILM_SOURCE",filmSource);
        }
        if(!"".equals(canfilmShow)){
            queryWrapper.like("CAN_SHOW",canfilmShow);
        }

        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_FILM");

        Page<Film> filmPage = filmService.page(page,queryWrapper);
        System.out.println("查询出"+filmPage.getTotal());
        if (filmPage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到电影");
        }else {
            return Result.success("查询成功",filmPage);
        }
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

