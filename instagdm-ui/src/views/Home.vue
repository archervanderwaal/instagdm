<template>
  <div class="home-container">
    <div>
      <el-empty description="NOTHING..."></el-empty>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  data() {
    return {
      userInfo: ''
    }
  },
  methods: {}
}
</script>

<style scoped>
.home-container {
  padding: 10px;
  padding-top: 5px;
}
</style>