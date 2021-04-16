package com.java2nb.novel.controller;

import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.core.enums.ResponseStatus;
import com.java2nb.novel.core.utils.ViewsDO;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookComment;
import com.java2nb.novel.service.ViewsService;
import com.java2nb.novel.vo.BookSpVO;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.vo.BookVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11797
 */
@RequestMapping("book")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BookController extends BaseController{

    private final BookService bookService;
    private final ViewsService viewsService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.enable}")
    private Integer enableMq;


    /**
     * 查询首页小说设置列表数据
     * */
    @GetMapping("listBookSetting")
    public ResultBean listBookSetting(HttpServletRequest request){
        getIp(request);
        return ResultBean.ok(bookService.listBookSettingVO());
    }

    /**
     * 查询首页点击榜单数据
     * */
    @GetMapping("listClickRank")
    public ResultBean listClickRank(){
        return ResultBean.ok(bookService.listClickRank());
    }

    /**
     * 查询首页新书榜单数据
     * */
    @GetMapping("listNewRank")
    public ResultBean listNewRank(){
        return ResultBean.ok(bookService.listNewRank());
    }

    /**
     * 查询首页更新榜单数据
     * */
    @GetMapping("listUpdateRank")
    public ResultBean listUpdateRank(){
        return ResultBean.ok(bookService.listUpdateRank());
    }

    /**
     * 查询小说分类列表
     * */
    @GetMapping("listBookCategory")
    public ResultBean listBookCategory(){
        return ResultBean.ok(bookService.listBookCategory());
    }

    /**
     * 分页搜索
     * */
    @GetMapping("searchByPage")
    public ResultBean searchByPage(BookSpVO bookSP, @RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize){
        return ResultBean.ok(bookService.searchByPage(bookSP,page,pageSize));
    }

    /**
     * 查询小说详情信息
     * */
    @GetMapping("queryBookDetail/{id}")
    public ResultBean queryBookDetail(@PathVariable("id") Long id){
        return ResultBean.ok(bookService.queryBookDetail(id));
    }


    /**
     * 查询小说排行信息
     * */
    @GetMapping("listRank")
    public ResultBean listRank(@RequestParam(value = "type",defaultValue = "0") Byte type,@RequestParam(value = "limit",defaultValue = "30") Integer limit){
        return ResultBean.ok(bookService.listRank(type,limit));
    }

    /**
     * 增加点击次数
     * */
    @PostMapping("addVisitCount")
    public ResultBean addVisitCount(Long bookId){
        if(enableMq == 1) {
            rabbitTemplate.convertAndSend("ADD-BOOK-VISIT-EXCHANGE", null, bookId);
        }else {
            bookService.addVisitCount(bookId, 1);
        }
        return ResultBean.ok();
    }

    /**
     * 查询章节相关信息
     * */
    @GetMapping("queryBookIndexAbout")
    public ResultBean queryBookIndexAbout(Long bookId,Long lastBookIndexId) {
        Map<String,Object> data = new HashMap<>(2);
        data.put("bookIndexCount",bookService.queryIndexCount(bookId));
        String lastBookContent = bookService.queryBookContent(lastBookIndexId).getContent();
        if(lastBookContent.length()>42){
            lastBookContent=lastBookContent.substring(0,42);
        }
        data.put("lastBookContent",lastBookContent);
        return ResultBean.ok(data);
    }

    /**
     * 根据分类id查询同类推荐书籍
     * */
    @GetMapping("listRecBookByCatId")
    public ResultBean listRecBookByCatId(Integer catId) {
        return ResultBean.ok(bookService.listRecBookByCatId(catId));
    }


    /**
     *分页查询书籍评论列表
     * */
    @GetMapping("listCommentByPage")
    public ResultBean listCommentByPage(@RequestParam("bookId") Long bookId,@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5") int pageSize) {
        return ResultBean.ok(bookService.listCommentByPage(null,bookId,page,pageSize));
    }

    /**
     * 新增评价
     * */
    @PostMapping("addBookComment")
    public ResultBean addBookComment(BookComment comment, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(request);
        if (userDetails == null) {
            return ResultBean.fail(ResponseStatus.NO_LOGIN);
        }
        bookService.addBookComment(userDetails.getId(),comment);
        return ResultBean.ok();
    }

    /**
     * 根据小说ID查询小说前十条最新更新目录集合
     * */
    @GetMapping("queryNewIndexList")
    public ResultBean queryNewIndexList(Long bookId){
        return ResultBean.ok(bookService.queryIndexList(bookId,"index_num desc",1,10));
    }

    /**
     * 目录页
     * */
    @GetMapping("/queryIndexList")
    public ResultBean indexList(Long bookId,@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5") int pageSize,@RequestParam(value = "orderBy",defaultValue = "index_num desc") String orderBy) {
        return ResultBean.ok(new PageBean<>(bookService.queryIndexList(bookId,orderBy,page,pageSize)));
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    try {
                        ipAddress = InetAddress.getLocalHost().getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null) {
                if (ipAddress.contains(",")) {
                    return ipAddress.split(",")[0];
                } else {
                    return ipAddress;
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //插入ip
    public  void getIp(HttpServletRequest request){
        String ipAddr = getIpAddr(request);
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Map<String,Object> map=new HashMap<>();
        map.put("ip",ipAddr);
        map.put("date",format);
        int count = viewsService.count(map);
        if (count==0){
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            ViewsDO s = new ViewsDO();
            s.setIp(ipAddr);
            s.setDate(format);
            viewsService.save(s);
        }
    }

}
