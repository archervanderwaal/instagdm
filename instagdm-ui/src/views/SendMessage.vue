<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>Ins群发</span>
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
      <el-form-item label="消息文本" prop="message">
        <el-input v-model="form.message" placeholder="请输入需要发送的消息" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="收信人" prop="receivers">
        <el-input v-model="form.receivers" placeholder="请输入收信人用户名, 英文逗号分隔" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="收信群组" prop="threadIds">
        <el-input v-model="form.threadIds" placeholder="请输入群组Id, 英文逗号分隔" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitSendMessage">提交任务</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { sendMessage } from '@/api/services/message'
export default {
  name: 'send-message',
  data() {
    return {
      form: {
        username: '',
        password: '',
        message: '',
        receivers: '',
        threadIds: ''
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
        message: [
          {
            required: true,
            message: '请输入需要发送的消息',
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
    submitSendMessage() {
      let receivers = this.form.receivers === '' ? [] : this.form.receivers.split(',')
      let threadIds = this.form.threadIds === '' ? [] : this.form.threadIds.split(',')
      this.$message.success('提交中, 请稍后...')
      sendMessage(this.form.username, this.form.password, this.form.message, receivers, threadIds)
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
        message: '',
        receivers: '',
        threadIds: ''
      }
    }
  }
}
</script>
