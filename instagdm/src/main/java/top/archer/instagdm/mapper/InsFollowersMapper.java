package top.archer.instagdm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.archer.instagdm.model.InsFollowers;

import java.util.List;

@Mapper
public interface InsFollowersMapper {

    @Insert("insert ignore into ins_followers(user, follower, follower_id) <foreach collection=\"followers\" item=\"f\" index=\"index\" separator=\",\"> (#{f.user}, #{f.follower}, #{f.followerId}) </foreach>")
    void batchInsert(@Param("followers") List<InsFollowers> followers);

    @Select("select user, follower, follower_id as followerId from ins_followers")
    List<InsFollowers> queryAll();
}
