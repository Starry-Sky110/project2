package salesSystem.sys.mapper;

import salesSystem.sys.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-03
 */
public interface UserMapper extends BaseMapper<User> {

    Integer queryUserMaxOrderNum();

    void deleteUserAndRoleByUserId(Integer id);

    void saveUserRole(Integer uid, Integer rid);
}
