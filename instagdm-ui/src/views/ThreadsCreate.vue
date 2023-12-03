<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>Ins创建群组</span>
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
      <el-form-item label="群组名称" prop="title">
        <el-input v-model="form.title" placeholder="请输入群组名称" style="width: 20%"></el-input>
      </el-form-item>
      <el-form-item label="成员" prop="inviters">
        <el-input
          v-model="form.inviters"
          placeholder="请输入群组成员用户名, 英文逗号分隔"
          style="width: 50%"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitThreadsCreate">提交任务</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { createThreads } from '@/api/services/threads'
export default {
  name: 'threads-create',
  data() {
    return {
      form: {
        username: '',
        password: '',
        title: '',
        inviters: ''
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
        title: [
          {
            required: true,
            message: '请输入群组名称',
            trigger: 'blur'
          },
          {
            min: 1,
            message: '至少包含1个字符',
            trigger: 'blur'
          }
        ],
        inviters: [
          {
            required: true,
            message: '请输入成员用户名(多个英文逗号分隔)',
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
    submitThreadsCreate() {
      let inviters = this.form.inviters === '' ? [] : this.form.inviters.split(',')
      this.$message.success('提交中, 请稍后...')
      createThreads(this.form.username, this.form.password, this.form.title, inviters)
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
        title: '',
        inviters: ''
      }
    }
  }
}
</script>
