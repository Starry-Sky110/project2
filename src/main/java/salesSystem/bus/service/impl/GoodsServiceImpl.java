package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.mapper.GoodsMapper;
import salesSystem.bus.mapper.ProviderMapper;
import salesSystem.bus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.GoodsVO;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.FileUploadAndDownloadUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProviderMapper providerMapper;

    @Value("${upload.upload-root-path}")
    private String uploadPath;

    @Override
    public DataGridView loadAllGoods(GoodsVO goodsVO) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(goodsVO.getProviderid() != null, "providerid", goodsVO.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getGoodsname()), "goodsname", goodsVO.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getProductcode()), "productcode", goodsVO.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getPromitcode()), "promitcode", goodsVO.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getDescription()), "description", goodsVO.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVO.getSize()), "size", goodsVO.getSize());
        queryWrapper.orderByDesc("id");
        //分页
        Page page = new Page<>(goodsVO.getPage(), goodsVO.getLimit());
        //查询数据库
        List<Goods> goodsList = goodsMapper.selectPage(page, queryWrapper).getRecords();
        for (Goods g : goodsList) {
            String providername = providerMapper.selectById(g.getProviderid()).getProvidername();
            g.setProvidername(providername);
        }
        return new DataGridView(page.getTotal(), goodsList);
    }

    @Transactional
    @Override
    public void deleteGoods(Integer id) {

        goodsMapper.deleteById(id);

    }

    @Transactional
    @Override
    public void batchDeleteGoods(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            idList.add(i);
        }
        goodsMapper.deleteBatchIds(idList);
    }

    @Transactional
    @Override
    public void addGoods(Goods goods) {
        String goodsimg = goods.getGoodsimg();
        String path = FileUploadAndDownloadUtils.changeFileName(uploadPath, goodsimg);
        goods.setGoodsimg(path);
        goodsMapper.insert(goods);
    }

    @Transactional
    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
    }

    @Override
    public DataGridView loadAllAvailableGoods() {
        QueryWrapper<Goods> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);

        return new DataGridView(goodsMapper.selectList(qw));
    }

    @Override
    public DataGridView loadGoodsByProviderId(Integer providerId) {
        QueryWrapper<Goods> qw = new QueryWrapper<>();
        qw.eq(providerId != null, "providerid", providerId);
        qw.eq("available", Constant.AVAILABLE_TRUE);

        return new DataGridView(goodsMapper.selectList(qw));
    }

}
