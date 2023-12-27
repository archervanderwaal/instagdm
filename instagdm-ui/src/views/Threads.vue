<template>
  <el-table :data="threadsList" height="250" border style="width: 80%">
    <el-table-column prop="threadId" label="群组id" width="180"> </el-table-column>
    <el-table-column prop="threadTitle" label="群组名称" width="180"> </el-table-column>
    <el-table-column prop="invitationLink" label="邀请链接"> </el-table-column>
    <el-table-column prop="creatorUsername" label="创建人"></el-table-column>
  </el-table>
</template>

<script>
import { listThreads } from '@/api/services/threads'

export default {
  data() {
    return {
      threadsList: []
    }
  },
  methods: {
    queryThreads() {
      listThreads()
        .then((res) => {
          if (res.data.code === 0) {
            this.threadsList = res.data.data
          } else if (res.data.code === -1) {
            this.$message.error(res.data.message)
          }
        })
        .catch((error) => {
          this.$message.error('Error: ', error)
        })
    }
  },
  created() {
    this.queryThreads()
  }
}
</script>

<style scoped>
</style>