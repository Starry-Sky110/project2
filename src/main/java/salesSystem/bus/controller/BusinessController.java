package salesSystem.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("business")
public class BusinessController {


    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {

        return "business/customer/customerManager";
    }

    @RequestMapping("toProviderManager")
    public String toProviderManager() {
        return "business/provider/providerManager";
    }

    //toGoodsManager
    @RequestMapping("toGoodsManager")
    public String toGoodsManager() {
        return "business/goods/goodsManager";
    }

    //toInportManager
    @RequestMapping("toInportManager")
    public String toInportManager() {
        return "business/inport/inportManager";
    }

    //toOutportManager
    @RequestMapping("toOutportManager")
    public String toOutportManager() {
        return "business/outport/outportManager";
    }

    //
    @RequestMapping("toSalesManager")
    public String toSalesManager(){
        return "business/sales/salesManager";
    }

    @RequestMapping("toSalesBackManager")
    public String toSalesBackManager(){
        return "business/salesback/salesBackManager";
    }
}
