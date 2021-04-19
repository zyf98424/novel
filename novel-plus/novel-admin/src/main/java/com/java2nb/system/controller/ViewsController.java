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


import com.java2nb.system.domain.ViewsDO;
import com.java2nb.system.service.ViewsService;
import com.java2nb.common.utils.PageBean;
import com.java2nb.common.utils.Query;
import com.java2nb.common.utils.R;

/**
 * 
 *
 * @author zyf
 * @email 82345335@qq.com
 * @date 2021-04-15 10:24:47
 */

@Controller
@RequestMapping("/system/views")
public class ViewsController {
    @Autowired
    private ViewsService viewsService;

    @GetMapping()
    @RequiresPermissions("system:views:views")
    String Views() {
        return "system/views/views";
    }

    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:views:views")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ViewsDO> viewsList = viewsService.list(query);
        int total = viewsService.count(query);
        PageBean pageBean = new PageBean(viewsList, total);
        return R.ok().put("data", pageBean);
    }

    @ApiOperation(value = "新增页面", notes = "新增页面")
    @GetMapping("/add")
    @RequiresPermissions("system:views:add")
    String add() {
        return "system/views/add";
    }

    @ApiOperation(value = "修改页面", notes = "修改页面")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:views:edit")
    String edit(@PathVariable("id") Long id, Model model) {
            ViewsDO views = viewsService.get(id);
        model.addAttribute("views", views);
        return "system/views/edit";
    }

    @ApiOperation(value = "查看页面", notes = "查看页面")
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:views:detail")
    String detail(@PathVariable("id") Long id, Model model) {
			ViewsDO views = viewsService.get(id);
        model.addAttribute("views", views);
        return "system/views/detail";
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增", notes = "新增")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:views:add")
    public R save( ViewsDO views) {
        if (viewsService.save(views) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:views:edit")
    public R update( ViewsDO views) {
            viewsService.update(views);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:views:remove")
    public R remove( Long id) {
        if (viewsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:views:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
            viewsService.batchRemove(ids);
        return R.ok();
    }

}
