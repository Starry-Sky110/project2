package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Sales;
import salesSystem.bus.service.SalesService;
import salesSystem.bus.vo.SalesVO;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
@RestController
@RequestMapping("sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @RequestMapping("loadAllSales")
    public DataGridView loadAllSales(SalesVO salesVO) {

        return salesService.loadAllSales(salesVO);

    }

    @RequestMapping("addSales")
    public ResultObj addSales(Sales sales, HttpSession session) {

        //获取操作人
        User user = (User) session.getAttribute("user");
        sales.setOperateperson(user.getName());
        //操作时间
        sales.setSalestime(new Date());

        return salesService.addSales(sales);

    }

    @RequestMapping("updateSales")
    public ResultObj updateSales(Sales sales) {

        return salesService.updateSales(sales);
    }

    @RequestMapping("deleteSales")
    public ResultObj deleteSales(Integer id, Integer num) {

        try {
            salesService.deleteSales(id, num);
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }

    }

}

