package com.example.filmback.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.common.Constants;
import com.example.filmback.common.Result;
import com.example.filmback.entity.*;
import com.example.filmback.exception.ServiceException;
import com.example.filmback.mapper.SeatMapper;
import com.example.filmback.service.ICinemaService;
import com.example.filmback.service.ISeatService;
import com.example.filmback.service.ISeatShowingsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.filmback.service.IBillService;

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
    @Resource
    private ICinemaService cinemaService;
    @Resource
    private ISeatShowingsService seatShowingsService;

    /**
     * 新增或者更新
     * @param bill 订单类
     * @return 返回更新后的订单
     */
    @PostMapping
    public Result save(@RequestBody Bill bill) {

        //生成唯一ID
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        Long id = idWorker.nextId();
        //判定新增还是更新
        if (bill.getBillId() == null) {
            bill.setBillId(id);
        }
        if (billService.saveOrUpdate(bill)){
            return Result.success("更新成功",billService.list(new QueryWrapper<Bill>().eq("BILL_ID",id)).get(0));
        }else {
            throw new ServiceException(Constants.CODE_201,"更新失败");
        }
    }

    /**
     * 通过订单ID删除订单
     * @param billId 订单ID
     * @return 是否删除
     */
    @DeleteMapping("/{billId}")
    public Result delete(@PathVariable Long billId) {
        if (billService.removeById(billId)){
            return Result.success("删除成功！",true);
        }else {
            throw new ServiceException(Constants.CODE_201,"删除失败！");
        }
    }

    /**
     * 通过订单ID退票
     * @param billId 订单ID
     * @return 是否删除成功
     */
    @DeleteMapping("/reticket")
    public Result reticket(@RequestParam Long billId) throws ParseException {

        Bill bill = billService.list(new QueryWrapper<Bill>().eq("BILL_ID",billId)).get(0);
        if(bill.getBillState()==0){
            //开场前30分钟
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bill.getFilmstartTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, -30);// 30分钟前
            String timeStr1= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
            //现在的时间
            String timeStr2= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(new Date());

            System.out.println("这是比较的时间"+timeStr1.compareTo(timeStr2));
            System.out.println("这是前30分钟的时间"+timeStr1+"这是现在的时间"+timeStr2);

            if (timeStr1.compareTo(timeStr2)<0){
                throw new ServiceException(Constants.CODE_201,"开场时间不足30分钟，不满足退票条件！");
            }else {
                List seatId = Arrays.asList(bill.getSeatIdlist().split(","));
                seatId.forEach(e->{
                    if (!seatShowingsService.update(new UpdateWrapper<SeatShowings>()
                            .eq("SEAT_ID",e).eq("SHOWINGS_ID",bill.getShowingsId())
                            .set("SEAT_STATE",0))) {
                        throw new ServiceException(Constants.CODE_201,"退票失败");
                    }
                });
                if (billService.remove(new QueryWrapper<Bill>().eq("BILL_ID",billId))){
                    return Result.success("退票成功",true);

                }else {
                    throw new ServiceException(Constants.CODE_201,"订单删除失败！");
                }
            }
        }else {
            throw new ServiceException(Constants.CODE_201,"订单已经完成，无法退票！");
        }
    }
    //查询所有
    @GetMapping
    public List<Bill> findall() {
        return billService.list();
    }

    /**
     * 查询某个用户的订单
     * @param userId 用户ID
     * @return 查询到的用户订单
     */
    @GetMapping("/userBill")
    public Result userBill(@RequestParam String userId){
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID",userId);
        queryWrapper.orderByDesc("CREATE_BILL");
        List<Bill> bills= billService.list(queryWrapper);

        if (bills.size()!=0){

            for (Bill bill:bills){
                //获取现在的时间
                String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                //时间判定是否电影已经开始
                if (bill.getFilmstartTime().compareTo(timeStr1)<0){
                    bill.setBillState(1);
                    billService.updateById(bill);
                }
                //判定影院是否存在
                List<Cinema> cinemaList = cinemaService.list(new QueryWrapper<Cinema>()
                        .eq("CINEMA_ID",bill.getCinemaId()));
                if (cinemaList.size()==0){
                    bill.setCinemaLocation(Result.error(Constants.CODE_404,"该影院已下线"));
                }else {
                    bill.setCinemaLocation(Result.success("查询成功",cinemaList.get(0).getCinemaLocation()));
                }

            }

            return Result.success("查询成功",bills);
        }else {
            throw new ServiceException(Constants.CODE_201,"该用户没有订单");
        }
    }

    /**
     * 删除订单
     * @param billId 订单ID
     * @return 是否删除成功
     */
    @DeleteMapping("/deleBill")
    public Result event(@RequestParam Long billId){
        //获取现在的时间
        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (billService.list(new QueryWrapper<Bill>().eq("BILL_ID",billId).ge("FILMEND_TIME",timeStr1)).size()!=0){
            throw new ServiceException(Constants.CODE_201,"该影片还没结束，无法删除！");
        }else {
            if (billService.remove(new QueryWrapper<Bill>().eq("BILL_ID",billId))){
                return Result.success("删除成功！",true);
            }else {
                throw new ServiceException(Constants.CODE_201,"删除失败");
            }
        }

    }

    /**
     * 分页查询 mybatis-plus方法
     * @param pageNum 分页查询第几页
     * @param pageSize 每一页多少个
     * @param filmName 关键词查询电影名称
     * @param cinemaName 影院名称
     * @param userName 用户名称
     * @param userTel 用户手机号码
     * @return 返回订单列表
     */
    @GetMapping("/billPage")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               //没有也可以查询
                               @RequestParam(defaultValue = "") String filmName,
                               @RequestParam(defaultValue = "") String cinemaName,
                               @RequestParam(defaultValue = "") String userName,
                               @RequestParam(defaultValue = "") String userTel
    ) {
        Page<Bill> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        if (!"".equals(filmName)) {
            queryWrapper.like("FILM_NAME", filmName);
        }
        if (!"".equals(cinemaName)) {
            queryWrapper.like("CINEMA_NAME", cinemaName);
        }
        if (!"".equals(userName)) {
            queryWrapper.like("USER_NAME", userName);
        }
        if (!"".equals(userTel)) {
            queryWrapper.like("TEL", userTel);
        }
        //通过时间倒序
        queryWrapper.orderByDesc("CREATE_BILL");
        Page<Bill> billPage = billService.page(page, queryWrapper);
        System.out.println("查询出"+billPage.getTotal());
        if (billPage.getTotal()==0){
            throw new ServiceException(Constants.CODE_404,"查询不到订单");
        }else {
            return Result.success("查询成功",billPage);
        }
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

