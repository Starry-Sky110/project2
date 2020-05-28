package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.fileupload.UploadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.User;

import salesSystem.sys.mapper.DeptMapper;
import salesSystem.sys.mapper.UserMapper;
import salesSystem.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.*;
import salesSystem.sys.vo.UserVO;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Value("${upload.upload-root-path}")
    private String uploadPath;

    @Override
    public User queryUserByUsername(String username) {
        //根据用户名查询数据库
        //mybatis-plus
        //创建queryWrapper
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("loginname", username);
        User user = userMapper.selectOne(userQueryWrapper);
        // select * from sys_user where loginname = #{username};
        return user;
    }

    @Override
    public DataGridView loadAllUser(UserVO userVO) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(userVO.getDeptid() != null, "deptid", userVO.getDeptid());
        qw.like(StringUtils.isNotBlank(userVO.getName()), "name", userVO.getName());
        qw.like(StringUtils.isNotBlank(userVO.getAddress()), "address", userVO.getAddress());

        Page page = new Page<>(userVO.getPage(), userVO.getLimit());
        userMapper.selectPage(page, qw);
        List<User> list = page.getRecords();
        for (User u : list) {
            //部门名称
            String deptName = deptMapper.selectById(u.getDeptid()).getTitle();
            u.setDeptname(deptName);
            if (null != u.getMgr()) {
                User user = userMapper.selectById(u.getMgr());
                if (null != user) {
                    u.setLeadername(user.getName());
                }
            }
        }

        //page.getTotal() 总数 page.getRecords() 数据
        return new DataGridView(page.getTotal(), list);
    }


    @Transactional
    @Override
    public void deleteUser(Integer id) {
        //先删除中间表 sys_role_user
        userMapper.deleteUserAndRoleByUserId(id);

        userMapper.deleteById(id);

    }

    @Transactional
    @Override
    public void batchDeleteUser(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            //先删除中间表 sys_role_user
            userMapper.deleteUserAndRoleByUserId(i);
            idList.add(i);
        }
        userMapper.deleteBatchIds(idList);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Transactional
    @Override
    public void saveUserRole(Integer userId, Integer[] rids) {
        //删除之前的
        userMapper.deleteUserAndRoleByUserId(userId);

        if (rids != null && rids.length > 0) {
            for (Integer i : rids) {

                userMapper.saveUserRole(userId, i);
            }
        }

    }

    @Override
    public void rePassword(Integer id) {
        User user = userMapper.selectById(id);
        String salt = user.getSalt();
        user.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD, salt, 2));
        userMapper.updateById(user);
    }

    //修改用户信息
    @Transactional
    @Override
    public void changeUser(User user, HttpSession session) {
        String imgpath = user.getImgpath();
//        System.out.println("user:  " + imgpath);
        if (!imgpath.equals(Constant.USER_DEFAULT_IMAGE)) {
            if (imgpath.endsWith("_temp")) {
                //临时图片变为永久图片
                String path = FileUploadAndDownloadUtils.changeFileName(uploadPath, imgpath);
                user.setImgpath(path);
                //删除临时图片
                User user2 = (User) session.getAttribute("user");
//                System.out.println("session:  " + user2.getImgpath());
                if (null != user2.getImgpath()) {
                    FileUploadAndDownloadUtils.deleteTempImage(uploadPath, user2.getImgpath());
                }
            }
        }
        this.userMapper.updateById(user);
        User user1 = this.userMapper.selectById(user.getId());
        //更新session中的user
        session.setAttribute("user", user1);
    }

    @Override
    public Integer queryUserMaxOrderNum() {
        return userMapper.queryUserMaxOrderNum();
    }

    @Override
    public List<User> queryUserByDeptId(Integer deptid) {
        if (deptid == null) {
            return null;
        } else {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("type", Constant.USER_TYPE_NORMAL);
            qw.eq("deptid", deptid);
            List<User> userList = userMapper.selectList(qw);
            //select * from sys_user where type = 1 and deptid = #{deptid}
            return userList;
        }
    }

    @Override
    public User queryUserByUserId(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public ResultObj changePassword(Integer id, String oldPassword, String newPassword, String confirmPassword) {

        User user = userMapper.selectById(id);
        //盐值
        String salt = user.getSalt();
        //对用户输入的老密码进行加密
        String oldPws = new Md5Hash(oldPassword, salt, 2).toString();
        //输入旧密码比较
        if (user.getPwd().equals(oldPws)) {
            //确认密码比较
            if (newPassword.equals(confirmPassword)) {
                String newPwd = new Md5Hash(newPassword, salt, 2).toString();
                user.setPwd(newPwd);
                userMapper.updateById(user);
                return ResultObj.UPDATE_SUCCESS;
            } else {
                return ResultObj.CONFIRM_PASSWORD_ERROR;
            }
        } else {
            return ResultObj.OLD_PWD_ERROR;
        }
    }
}
