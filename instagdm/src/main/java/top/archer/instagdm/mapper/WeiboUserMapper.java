package top.archer.instagdm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.archer.instagdm.data.WeiboUserInfo;
import top.archer.instagdm.model.WeiboUser;

import java.util.List;

@Mapper
public interface WeiboUserMapper {

    @Select("select wbphone as phone, wbuid as uid from wb where wbuid = #{uid}")
    List<WeiboUser> queryByUid(@Param("uid") String uid);

    @Insert("insert ignore into output_bj_wb_users(weibo_id, phone, phone_location) values (#{u.weiboId}, #{u.phone}, #{u.phoneLocation})")
    void insertToOutput(@Param("u") WeiboUserInfo userInfo);

    @Select("select wbphone as phone, wbuid as uid from wb limit #{start}, #{limit}")
    List<WeiboUser> queryByLimit(@Param("start") int start, @Param("limit") int limit);
}
