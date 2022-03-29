<template>
  <div style="line-height: 60px;display: flex">
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
<!--      <el-breadcrumb separator="/" style="display: inline-block" class="ml-10">-->
<!--        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
<!--        <el-breadcrumb-item><a href="/user">活动管理</a></el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->
    </div>

    <el-dropdown style="width: 70px;cursor:pointer">
      <span>{{adminName}}</span><i class="el-icon-arrow-down" style="margin-left:5px"></i>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">个人信息</el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <router-link to="/login">退出</router-link>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
    adminName: String,
  },
  data() {
    return {
    }
  },
  methods: {

  }
}

</script>

<style scoped>

</style>