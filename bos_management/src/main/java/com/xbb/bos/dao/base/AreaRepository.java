package com.xbb.bos.dao.base;

        import com.xbb.bos.domain.base.Area;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
        import org.springframework.data.jpa.repository.Query;

        import java.util.List;

/**
 * 区域管理dao
 * Created by xbb on 2017/12/27.
 */
public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area>{

    /**
     * 查询所有的省份信息
     * @return
     */
    @Query("select distinct province from Area")
    List<String> findProvince();

    /**
     * 根据省份信息查询所有的城市新
     * @param province
     * @return
     */
    @Query("select distinct city from Area where province = ?")
    List<String> findCity(String province);

    /**
     * 根据省份和城市信息查询所有区域信息
     * @param province
     * @param city
     * @return
     */
    @Query("from Area where province = ? and city = ?")
    List<Area> findDistrict(String province, String city);

    /**
     * 根据省市区查询区域的方法
     * @param province
     * @param city
     * @param district
     * @return
     */
    Area  findByProvinceAndCityAndDistrict(String province,String city,String district);

}
