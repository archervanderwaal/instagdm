package top.archer.instagdm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.archer.instagdm.model.InsClient;

@Mapper
public interface InsClientMapper {

    @Insert("INSERT IGNORE INTO ins_client(username, password, client, cookie_jar) values (#{c.username}, #{c.password}, #{c.client}, #{c.cookieJar})")
    void insert(@Param("c") InsClient client);

    @Select("SELECT username, password, client, cookie_jar as cookieJar from ins_client where username = #{u} and password = #{p}")
    InsClient select(@Param("u") String username, @Param("p") String password);
}
