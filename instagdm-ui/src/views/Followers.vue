<template>
  <el-table :data="followers" height="250" border style="width: 80%">
    <el-table-column prop="user" label="Ins用户" width="180"> </el-table-column>
    <el-table-column prop="follower" label="Ins粉丝" width="180"> </el-table-column>
    <el-table-column prop="followerId" label="Ins粉丝Id"> </el-table-column>
  </el-table>
</template>

<script>
import { listFollowers } from '@/api/services/followers'

export default {
  data() {
    return {
      followers: []
    }
  },
  methods: {
    queryFollowers() {
      listFollowers()
        .then((res) => {
          if (res.data.code === 0) {
            this.followers = res.data.data
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
    this.queryFollowers()
  }
}
</script>

<style scoped>
</style>