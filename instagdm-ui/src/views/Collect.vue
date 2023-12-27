<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>Ins粉丝采集</span>
    </div>
    <el-form ref="form" :model="form" label-width="auto" :rules="rules">
      <el-form-item label="Ins用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入Instagram用户名" style="width: 20%"></el-input>
      </el-form-item>
      <el-form-item label="Ins密码" prop="password">
        <el-input
          v-model="form.password"
          :show-password="true"
          placeholder="请输入Instagram密码"
          style="width: 20%"
        ></el-input>
      </el-form-item>
      <el-form-item label="采集用户名" prop="insUser">
        <el-input v-model="form.insUser" placeholder="请输入需要采集的用户" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交任务</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { collectFollowers } from '@/api/services/followers'

export default {
  name: 'send-message',
  data() {
    return {
      form: {
        username: '',
        password: '',
        insUser: ''
      },
      rules: {
        username: [
          {
            required: true,
            message: '请输入Ins用户名',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 100,
            message: '长度在 1 到 100 个字符',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入Ins密码',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 100,
            message: '长度在 1 到 100 个字符',
            trigger: 'blur'
          }
        ],
        insUser: [
          {
            required: true,
            message: '请输入需要采集的用户',
            trigger: 'blur'
          },
          {
            min: 1,
            message: '至少包含1个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    submit() {
      collectFollowers(this.form.username, this.form.password, this.form.insUser)
        .then((res) => {
          if (res.data.code === 0) {
            this.$message.success(res.data.data)
            this.clearForm()
          } else if (res.data.code === -1) {
            this.$message.error(res.data.message)
          }
        })
        .catch((error) => {
          this.$message.error('Error: ', error)
        })
    },
    clearForm() {
      this.form = {
        username: '',
        password: '',
        insUser: ''
      }
    }
  }
}
</script>

<style scoped>
</style>