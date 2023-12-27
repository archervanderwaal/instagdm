package top.archer.instagdm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.archer.instagdm.model.InsFollowers;

import java.util.List;

@Mapper
public interface InsFollowersMapper {

    @Insert("insert ignore into ins_followers(user, follower, follower_id) values <foreach collection=\"followers\" item=\"f\" index=\"index\" separator=\",\"> (#{f.user}, #{f.follower}, #{f.followerId}) </foreach>")
    void batchInsert(@Param("followers") List<InsFollowers> followers);

    @Insert("insert ignore into ins_followers(user, follower, follower_id) values (#{f.user}, #{f.follower}, #{f.followerId})")
    void insert(@Param("f") InsFollowers followers);

    @Select("select user, follower, follower_id as followerId from ins_followers")
    List<InsFollowers> queryAll();
}
