package salesSystem.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.Loginfo;
import salesSystem.sys.domain.Permission;
import salesSystem.sys.domain.User;
import salesSystem.sys.service.LoginfoService;
import salesSystem.sys.service.PermissionService;
import salesSystem.sys.utils.*;
import salesSystem.sys.vo.PermissionVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("login")
    public ResultObj login(HttpSession session,
                           String username,
                           String password,
                           HttpServletRequest request) {

        try {
            // shiro 获取当前对象
            Subject subject = SecurityUtils.getSubject();
            //创建token
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //登陆
            subject.login(token);
            //获得用户
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            User user = activeUser.getUser();
            // 用户放入session
            session.setAttribute("user", user);
            //写入登陆日志
            Loginfo loginfo = new Loginfo();
            //获取登录名
            loginfo.setLoginname(user.getLoginname());
            //获取IP
            loginfo.setLoginip(request.getRemoteAddr());
            //获取当前时间
            loginfo.setLogintime(TimeZoneInChina.ChangeTimeZoneInChina());

            loginfoService.save(loginfo);

            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException | ParseException e) {
            //登陆失败
            return ResultObj.LOGIN_FAIL;

        }
    }

    // 拼json格式
    @RequestMapping("loadIndexLeftMenuTree")
    public List<TreeNode> loadIndexLeftMenuTree(PermissionVO permissionVO, HttpSession session) {
        permissionVO.setType(Constant.TYPE_MENU); //menu
        permissionVO.setAvailable(Constant.AVAILABLE_TRUE); //1

        //获取登录用户
//        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User user = (User) session.getAttribute("user");

        List<Permission> permissionList = null;
        if (user.getType().equals(Constant.USER_TYPE_SUPER)) {
            //如果是超级管理员查询所有菜单
            permissionList = permissionService.queryAllPermissionList(permissionVO);
        } else {
            //普通用户 有什么权限查询什么菜单
            permissionList = permissionService.queryPermissionByUserIdForList(user);

        }


        //格式变换
        //创建list对象
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : permissionList) {
            Integer id = permission.getId();
            Integer pid = permission.getPid();
            String title = permission.getTitle();
            String icon = permission.getIcon();
            String href = permission.getHref();
            Boolean spread = permission.getOpen() == 1 ? true : false;
            TreeNode treeNode = new TreeNode(id, pid, title, icon, href, spread);
            treeNodes.add(treeNode);

        }
        // treeNode 变成前端所需json格式navs2.json
        List<TreeNode> treeNodeList = TreeNodeBuilder.build(treeNodes, 1);
        return treeNodeList;
    }


}
