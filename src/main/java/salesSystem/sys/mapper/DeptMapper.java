package salesSystem.sys.mapper;

import org.apache.ibatis.annotations.Param;
import salesSystem.sys.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-10
 */
public interface DeptMapper extends BaseMapper<Dept> {


    Integer queryDeptCountByPid(@Param("id") Integer id);

    Integer queryDeptMaxOrderNum();
}
