package salesSystem.sys.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.User;
import salesSystem.sys.service.PermissionService;
import salesSystem.sys.service.RoleService;
import salesSystem.sys.service.UserService;
import salesSystem.sys.utils.ActiveUser;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户填写的用户名
        String username = token.getPrincipal().toString();
        //查询数据库byUsername
        User user = userService.queryUserByUsername(username);
        if (user != null) {
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            //当为普通用户的时候查询所拥有的角色
            if (user.getType().equals(Constant.USER_TYPE_NORMAL)) {
                //角色名
                List<String> roles = roleService.queryRoleNameByUserId(user.getId());
//                System.out.println("roles.size========" + roles.size());
//                for (String s : roles) {
//                    System.out.println(s);
//                }
                activeUser.setRoles(roles);
                //查询出该用户所有的权限
                List<String> permissions = permissionService.queryPermissionsByUserId(user.getId());
                activeUser.setPermission(permissions);
            }
            //密码比对
            //1.获取盐值
            String salt = user.getSalt();
            //2.盐值转化
            ByteSource byteSource = ByteSource.Util.bytes(salt);
            //3.密码比对
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, user.getPwd(), byteSource, getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

        if (activeUser.getUser().getType() == Constant.USER_TYPE_SUPER) {
            //超级用户
            info.addStringPermission("*:*");//所有权限
        } else {
            //普通用户
            List<String> roles = activeUser.getRoles();
            List<String> permissions = activeUser.getPermission();

            if (roles != null && roles.size() > 0) {
                //把角色放入shiro
                info.addRoles(roles);
            }
            if (permissions != null && permissions.size() > 0) {
                info.addStringPermissions(permissions);
            }

        }

        return info;
    }


}
