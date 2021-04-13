package com.java2nb.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;


import com.java2nb.system.domain.BookDO;
import com.java2nb.system.service.Book1Service;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

/**
 * 小说表
 *
 * @author zyf
 * @email 82345335@qq.com
 * @date 2021-04-12 16:41:01
 */

@Controller
@RequestMapping("/system/book")
public class Book1Controller {
    @Autowired
    private Book1Service bookService;

    @GetMapping()
    @RequiresPermissions("system:book:book")
    String Book() {
        return "system/book/book";
    }

    @ApiOperation(value = "获取小说表列表", notes = "获取小说表列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:book:book")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<BookDO> bookList = bookService.list(query);
        int total = bookService.count(query);
        PageBean pageBean = new PageBean(bookList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "新增小说表页面", notes = "新增小说表页面")
    @GetMapping("/add")
    @RequiresPermissions("system:book:add")
    String add() {
        return "system/book/add";
    }

    @ApiOperation(value = "修改小说表页面", notes = "修改小说表页面")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:book:edit")
    String edit(@PathVariable("id") Long id, Model model) {
            BookDO book = bookService.get(id);
        model.addAttribute("book", book);
        return "system/book/edit";
    }

    @ApiOperation(value = "查看小说表页面", notes = "查看小说表页面")
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:book:detail")
    String detail(@PathVariable("id") Long id, Model model) {
			BookDO book = bookService.get(id);
        model.addAttribute("book", book);
        return "system/book/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增小说表", notes = "新增小说表")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:book:add")
    public R save( BookDO book) {
        if (bookService.save(book) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改小说表", notes = "修改小说表")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:book:edit")
    public R update( BookDO book) {
            bookService.update(book);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除小说表", notes = "删除小说表")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:book:remove")
    public R remove( Long id) {
        if (bookService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除小说表", notes = "批量删除小说表")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:book:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
            bookService.batchRemove(ids);
        return R.ok();
    }

}
