package com.qfedu.service.impl;

import com.qfedu.entity.Goods;
import com.qfedu.mapper.GoodsMapper;
import com.qfedu.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Integer insertGoods(Goods goods) {
        boolean isExist = false;
        List<Goods> list = goodsMapper.findGoodsByName(goods);
        String goodsCode;
        if (list.size() == 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmSS");
            String timeStamp = simpleDateFormat.format(new Date());
            String str = "";
            String str1 = str + (char) (Math.random() * 26 + 'A');
            String str2 = str + (char) (Math.random() * 26 + 'A');
            goodsCode = str1 + str2 + timeStamp;
        } else {
            goodsCode = list.get(0).getCode();
        }
        goods.setCode(goodsCode);
        goods.setDeleteFlag(0);
        for (Goods goodsTemp : list) {
            if (goodsTemp.getSize().equals(goods.getSize())
                    && goodsTemp.getColor().equals(goods.getColor())
                    && goodsTemp.getDeleteFlag() == goods.getDeleteFlag())
                isExist = true;
        }
        Integer rsCount;
        if (isExist) {
            rsCount = 0;
        } else {
            rsCount = goodsMapper.insertGoods(goods);
        }
        return rsCount;
    }


    @Override
    public Integer updateGoods(Goods goods) {
        boolean isExist = false;
        List<Goods> list = goodsMapper.findGoodsByName(goods);
        if (list.size() > 0) {
            goods.setCode(list.get(0).getCode());
        }
        for (Goods goodsTemp : list) {
            if (goodsTemp.getSize().equals(goods.getSize())
                    && goodsTemp.getColor().equals(goods.getColor())
                    && goodsTemp.getDeleteFlag() == goods.getDeleteFlag())
                isExist = true;
        }
        Integer rsCount;
        if (isExist) {
            rsCount = 0;
        } else {
            rsCount = goodsMapper.updateGoods(goods);
        }
        return rsCount;
    }

    @Override
    public Goods findGoodsById(Goods goods) {
        return goodsMapper.findGoodsById(goods);
    }

    @Override
    public Integer deleteGoods(Integer id) {
        Integer rsCount = goodsMapper.deleteGoods(id);
        return rsCount;
    }

    @Override
    public List<Goods> findGoodsByLike(Goods goods) {
        List<Goods> goodsList;
        if (StringUtils.isBlank(goods.getName())) {
            goods.setName(null);
        }
        if (StringUtils.isBlank(goods.getCode())) {
            goods.setCode(null);
        }
        if (StringUtils.isBlank(goods.getColor())) {
            goods.setColor(null);
        }
        if (StringUtils.isBlank(goods.getSize())) {
            goods.setSize(null);
        }
        if (goods.getCode() == null && goods.getName() == null && goods.getColor() == null && goods.getSize() == null) {
            goodsList = goodsMapper.findGoodsAll();
        } else {
            goodsList = goodsMapper.findGoodsByLike(goods);
        }
        return goodsList;
    }

    @Override
    public List<Goods> findGoodsAll() {
        List<Goods> goodsList = new ArrayList<Goods>();
        goodsList = goodsMapper.findGoodsAll();
        return goodsList;
    }
}
