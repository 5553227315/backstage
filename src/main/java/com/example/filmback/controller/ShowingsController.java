package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.*;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private IHallService hallService;
    @Resource
    private ISeatService seatService;
    @Resource
    private ISeatShowingsService seatShowingsService;


    /**
     * 新增或者更新
     * @param showings 场次类
     * @return 返回是否保存成功
     */
    @PostMapping
    public Result save(@RequestBody Showings showings) {

        //判定新增还是更新
        if (showings.getShowingsId() == null) {
            //生成唯一ID
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            showings.setShowingsId(idWorker.nextId());
            QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
            seatQueryWrapper.eq("HALL_ID",showings.getHallId());
            List<Seat> seatList = seatService.list(seatQueryWrapper);
            if (seatList.size()==0){
                throw new ServiceException(Constants.CODE_404,"该场次没有设置座椅！");
            }else {
                SeatShowings seatShowings = new SeatShowings();
                seatShowings.setShowingsId(showings.getShowingsId());
                //初始化设置场次座椅
                for (Seat seat : seatList) {
                    seatShowings.setSeatId(seat.getSeatId());
                    seatShowings.setSeatX(seat.getSeatX());
                    seatShowings.setSeatY(seat.getSeatY());
                    seatShowings.setSeatState(seat.getSeatState());
                    seatShowings.setSeatColor(seat.getSeatColor());
                    seatShowings.setSeatType(seat.getSeatType());
                    if (!seatShowingsService.save(seatShowings)){
                        throw new ServiceException(Constants.CODE_201,"保存座椅失败！");
                    }
                }
            }

        }

        if (showingsService.saveOrUpdate(showings)){
            return Result.success("操作成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"场次保存失败！");
        }
    }


    /**
     * 删除
     * @param showingsId 场次ID
     * @return 删除是否成功
     */
    @DeleteMapping("/{showingsId}")
    public Result delete(@PathVariable Long showingsId) {
        List<SeatShowings> seatShowings = seatShowingsService.list(new QueryWrapper<SeatShowings>().eq("SHOWINGS_ID", showingsId));
        //获取现在的时间
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Showings> showings = showingsService.list(new QueryWrapper<Showings>()
                .eq("SHOWINGS_ID", showingsId).ge("FILMSTART_TIME",timeStr1));
        if (showings.size()==0){
            throw new ServiceException(Constants.CODE_201, "该场次已经生效，不能删除！");
        }else {
            for (SeatShowings seatShowings1 : seatShowings){
                if (seatShowings1.getSeatState()==1){
                    throw new ServiceException(Constants.CODE_201, "该场次已经有人售票，不能删除！");
                }
            }
            if (!seatShowingsService.remove(new QueryWrapper<SeatShowings>().eq("SHOWINGS_ID", showingsId))) {
                throw new ServiceException(Constants.CODE_201, "删除场次座椅失败！");
            }
            if (showingsService.removeById(showingsId)) {
                return Result.success("删除成功！", true);
            } else {
                throw new ServiceException(Constants.CODE_201, "删除失败！");
            }

        }

    }


    //查询所有
    @GetMapping
    public List<Showings> findall() {
        return showingsService.list();
    }

    /**
     * 查询电影
     * @param filmName 电影名称
     * @return 返回电影列表
     */
    @GetMapping("/filmName")
    public Result findfilm(@RequestParam(defaultValue = "") String filmName){
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)) {
            queryWrapper.like("FILM_NAME", filmName);
        }
        queryWrapper.eq("CAN_SHOW","1");
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_FILM");
        List<Film> films = filmService.list(queryWrapper);
        if (films.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到该电影！");
        }else {
            return Result.success("查询成功！",films);
        }

    }

    /**
     * 查询评价
     * @param filmId 电影ID
     * @return 返回电影平均评分
     */
    @GetMapping("/evaluate")
    public Result findevaluate(@RequestParam(defaultValue = "") Long filmId){
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
        return Result.success("查询成功！",scores);
    }

    /**
     * 查询影院
     * @param cinemaName 电影名称
     * @return 返回含有该电影的场次
     */
    @GetMapping("/cinemaName")
    public Result findcinema(@RequestParam(defaultValue = "") String cinemaName){
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        if (!"".equals(cinemaName)) {
            queryWrapper.like("CINEMA_NAME", cinemaName);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_CINEMA");
        List<Cinema> cinemaList = cinemaService.list(queryWrapper);
        if(cinemaList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到该电影的场次！");
        }else {
            return Result.success("查询成功！",cinemaList);
        }
    }


    /**
     * 查询放映厅
     * @param cinemaId 影院ID
     * @param hallNumber 厅号
     * @return 返回放映厅列表
     */
    @GetMapping("/hall")
    public Result findhall(@RequestParam(defaultValue = "") String cinemaId,
                               @RequestParam(defaultValue = "") String hallNumber
    ){
        QueryWrapper<Hall> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CINEMA_ID", cinemaId);
        if (!"".equals(hallNumber)) {
            queryWrapper.like("HALL_NUMBER", hallNumber);
        }
        queryWrapper.orderByAsc("HALL_NUMBER");
        List<Hall> hallList = hallService.list(queryWrapper);
        if (hallList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到该厅！");
        }else {
            return Result.success("查询成功！",hallList);
        }
    }

    /**
     * 通过电影ID时间查询场次影院shopping.js
     * @param filmId 电影ID
     * @param filmstartDate 查询的日期
     * @return 返回影院列表
     */
    @GetMapping("/filmShowing")
    public Result findFilmShowing(@RequestParam(defaultValue = "") String filmId,
                                        @RequestParam(defaultValue = "") String filmstartDate){

        QueryWrapper<Showings> showingsQueryWrapper = new QueryWrapper<>();
        showingsQueryWrapper.eq("FILM_ID",filmId);
        filmstartDate=filmstartDate.substring(0,10);
        //获取现在的时间
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(timeStr1.substring(0, 10).equals(filmstartDate)){
            showingsQueryWrapper.ge("FILMSTART_TIME",timeStr1);
            showingsQueryWrapper.le("FILMSTART_TIME",timeStr1.substring(0, 10)+" 23:59:59");
        }else {
            showingsQueryWrapper.like("FILMSTART_TIME",filmstartDate);
        }
        List<Showings> showings = showingsService.list(showingsQueryWrapper);
        if(showings.size()==0){
            throw new ServiceException(Constants.CODE_404,"没有安排该电影场次！");
        }else {
            QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
            List<Cinema> cinemaList = new ArrayList<>();
            Cinema cinema;
            System.out.println("这是场次条数"+showings.size());
            for (int i = 0; i < showings.size(); i++){
                System.out.println("运行第"+(i+1)+"次");
                //判定返回列表里有无重复影院数据
                int a=0;
                if (cinemaList.size()!=0){
                    //循环遍历cinemaList
                    for (Cinema value : cinemaList) {
                        if (showings.get(i).getCinemaId().equals(value.getCinemaId())) {
                            a = 1;
                            break;
                        }
                    }
                }

                System.out.println("这是a"+a+"这是已经储存的影院列表长度"+cinemaList.size());
                if (a==0){
                    //最近场次
                    float bottomPrice=0;
                    String filmstartTime="";
                    for (Showings showing : showings) {
                        if (showings.get(i).getCinemaId().equals(showing.getCinemaId())) {
                            if (!filmstartTime.equals("|" + showing.getFilmstartTime().substring(11,16))){
                                filmstartTime = filmstartTime + " | " + showing.getFilmstartTime().substring(11,16);
                            }
                            //计算最低价格
                            if (Float.parseFloat(showings.get(i).getShowingsPrice())<=Float.parseFloat(showing.getShowingsPrice())){
                                bottomPrice=Float.parseFloat(showings.get(i).getShowingsPrice());
                            }else {
                                bottomPrice=Float.parseFloat(showing.getShowingsPrice());
                            }
                        }
                    }

                    cinemaQueryWrapper.eq("CINEMA_ID",showings.get(i).getCinemaId());
                    cinema = cinemaService.list(cinemaQueryWrapper).get(0);
                    cinemaQueryWrapper.clear();
                    cinema.setFilmstartTime(filmstartTime.substring(2));
                    cinema.setFilmstartDate(filmstartDate);

                    cinema.setBottomPrice((int) bottomPrice);

                    cinemaList.add(cinema);
                }
            }
            return Result.success("查询成功！",cinemaList);
        }

    }

    /**
     * 通过电影ID影院ID时间查询场次
     * @param filmId 电影ID
     * @param cinemaId 影院ID
     * @param filmstartDate 查询场次时间
     * @return 返回场次
     */
    @GetMapping("/timeShowing")
    public Result findTimeShowing(@RequestParam(defaultValue = "") String filmId,
                                          @RequestParam(defaultValue = "") String cinemaId,
                                          @RequestParam(defaultValue = "") String filmstartDate){

        QueryWrapper<Showings> showingsQueryWrapper = new QueryWrapper<>();
        showingsQueryWrapper.eq("FILM_ID",filmId);
        showingsQueryWrapper.eq("CINEMA_ID",cinemaId);
        filmstartDate=filmstartDate.substring(0,10);
        //获取现在的时间
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(timeStr1.substring(0, 10).equals(filmstartDate)){
            showingsQueryWrapper.ge("FILMSTART_TIME",timeStr1);
            showingsQueryWrapper.le("FILMSTART_TIME",timeStr1.substring(0, 10)+" 23:59:59");
        }else {
            showingsQueryWrapper.like("FILMSTART_TIME",filmstartDate);
        }
        List<Showings> showingsList = showingsService.list(showingsQueryWrapper.orderByAsc("FILMSTART_TIME"));
        if (showingsList.size()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到场次！");
        }else {
            return Result.success("查询成功！",showingsList);

        }

    }

    /**
     * 通过影院ID查询电影资料，电影ID放首位
     * @param filmId 电影ID
     * @param cinemaId 影院ID
     * @return 返回电影列表并且排序
     */
    @GetMapping("/filmInfo")
    public Result findFilm(@RequestParam(defaultValue = "") String filmId,
                               @RequestParam(defaultValue = "") String cinemaId){
        QueryWrapper<Film> filmQueryWrapper = new QueryWrapper<>();
        List<Film> filmList = new ArrayList<>();
        //判断是否为电影资料点击进入，有则放置首位
        if(!"".equals(filmId)){
            filmQueryWrapper.eq("FILM_ID",filmId);
            filmList.add(filmService.list(filmQueryWrapper).get(0));
            filmQueryWrapper.clear();
        }

        QueryWrapper<Showings> showingsQueryWrapper = new QueryWrapper<>();
        showingsQueryWrapper.eq("CINEMA_ID",cinemaId);

        //获取现在的时间
        String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String timeStr2 = LocalDateTime.now()
                .plusDays(2)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .substring(0, 10) + " 23:59:59";
        showingsQueryWrapper.ge("FILMSTART_TIME",timeStr1).le("FILMSTART_TIME",timeStr2);

        System.out.println(timeStr2+"`"+timeStr1);
        List<Showings> showingsList = showingsService.list(showingsQueryWrapper);

        if(showingsList.size()!=0){
            for (Showings showings : showingsList) {
                int a = 0;
                if (filmList.size() != 0) {
                    for (Film film : filmList) {
                        if (showings.getFilmId().equals(film.getFilmId())) {
                            a = 1;
                            break;
                        }
                    }
                }
                if (a == 0) {
                    filmQueryWrapper.eq("FILM_ID", showings.getFilmId());
                    filmList.add(filmService.list(filmQueryWrapper).get(0));
                }
                filmQueryWrapper.clear();
            }
            for (Film film:filmList){
                film.setFilmScore((String) this.findfilmevaluate(film.getFilmId()).getData());
            }
            return Result.success("查询成功！",filmList);
        }else {
            throw new ServiceException(Constants.CODE_201,"查询不到该场次！");
        }
    }

    public Result findfilmevaluate(Long filmId){
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
        return Result.success("查询成功！",scores);
    }

    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param filmName 电影名称
     * @param cinemaName 影院名称
     * @return 返回查询场次列表
     */
    @GetMapping("/showingsPage")
    public Result findPage(@RequestParam Integer pageNum,
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
        Page<Showings> showingsPage = showingsService.page(page, queryWrapper);
        if (showingsPage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到场次!");
        }else {
            return Result.success("查询成功！",showingsPage);
        }

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

