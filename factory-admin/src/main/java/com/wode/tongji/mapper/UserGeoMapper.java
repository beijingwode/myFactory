package com.wode.tongji.mapper;

import java.util.List;

import com.wode.tongji.model.UserGeo;

public interface UserGeoMapper {

    int insertSelective(UserGeo record);

    UserGeo selectByPrimaryKey(String city);

    int updateByPrimaryKeySelective(UserGeo record);
    /**
     * 功能说明：获取最大的注册量
     * 日期:	2015年5月21日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    long selectMaxRegistNumber();
    /**
     * 功能说明：获取最小的注册量
     * 日期:	2015年5月21日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    long selectMinRegistNumber();
    /**
     * 功能说明：查询注册量最大的几个城市
     * 日期:	2015年5月21日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    List<UserGeo> selectRegistNumberMaxCity();
    /**
     * 功能说明：查询所有的城市信息
     * 日期:	2015年5月21日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    List<UserGeo> selectAllCity();
    
}