package com.ljxy.score.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljxy.score.common.Result;
import com.ljxy.score.entity.User;
import com.ljxy.score.entity.Vo.UserVo;
import com.ljxy.score.service.UserService;
import com.ljxy.score.util.MD5Util;
import com.ljxy.score.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVo user) {

        System.out.println(user.getAccount());
        System.out.println("原密码"+user.getPassword());
//      对密码进行加密比较
        String pwd = MD5Util.string2MD5(user.getPassword());
        System.out.println("加密密码"+pwd);
        user.setPassword(pwd);
//        根据账号和密码查询数据
        User users = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, user.getAccount())
                                                                    .eq(User::getPassword, user.getPassword()));
        if (users == null) return Result.error("2000", "用户名或密码错误");
////        获取生成图形验证码的结果
//        String captcha = (String) request.getSession().getAttribute("captcha");
//        System.out.println("captcha=" + captcha);
////        判断验证码是否为空和正确
//        if (StrUtil.isEmpty(code) || !captcha.equalsIgnoreCase(code)) return Result.error("-2", "验证码错误");
        user.setName(users.getName());
        user.setType(users.getType());
//        生成token
        String token = TokenUtils.getToken(user.getAccount(), user.getPassword());
        user.setToken(token);

        return Result.success(user);
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User user) {
        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, user.getAccount()));
        if (res != null) return Result.error("-1", "账号已存在");
        String pwd = MD5Util.string2MD5(user.getPassword());
        user.setPassword(pwd);
        userService.save(user);
        return Result.success(user);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User user) {
        String pwd = MD5Util.string2MD5(user.getPassword());
        user.setPassword(pwd);
        if (userService.updateById(user)){
            return Result.success();
        }
        return Result.error("-1","修改失败");
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/resetpwd")
    public Result<?>resetPwd(@RequestBody User user){
        Integer id = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, user.getAccount())).getId();
        user.setId(id);
        String pwd = MD5Util.string2MD5(user.getPassword());
        user.setPassword(pwd);
        if (userService.updateById(user)){
            return Result.success();
        }
        return Result.error("-1","修改失败");
    }
    @ApiOperation(value = "删除班级")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        if (userService.removeById(id)){
            return Result.success();
        }
        return Result.error("-1","删除失败");
    }

    @ApiOperation(value = "多条件查询")
    @GetMapping("/userQuery")
    public Result<?> studentQuery(@RequestParam(defaultValue = "1") Integer PageNum, @RequestParam(defaultValue = "10") Integer PageSize, String account, Integer type) {

        Page<User> userPage = userService.findPage(new Page<>(PageNum, PageSize), account, type);
        return Result.success(userPage);
    }

    @ApiOperation(value = "重置密码")
    @PutMapping("updatepwd")
    public Result<?> updatePwd(Integer id){

        if (userService.updatePwd("e10adc3949ba59abbe56e057f20f883e",id)) {
            return Result.success();
        }
        return Result.error("-1","重置失败");
    }
}
