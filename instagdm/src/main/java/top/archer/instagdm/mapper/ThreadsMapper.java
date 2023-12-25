package top.archer.instagdm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.archer.instagdm.model.Threads;

import java.util.List;

@Mapper
public interface ThreadsMapper {

    @Insert("INSERT INTO `ins_threads`(`thread_id`, `thread_title`, `invitation_link`, `creator_username`) VALUES (#{t.threadId}, #{t.threadTitle}, #{t.invitationLink}, #{t.creatorUsername})")
    void insert(@Param("t")Threads threads);

    @Select("select thread_id as threadId, thread_title as threadTitle, invitation_link as invitationLink, creator_username as creatorUsername from ins_threads")
    List<Threads> queryAll();
}
