<template>
<!--  选中后的颜色active-text-color
隐藏滚动条

-->
  <el-menu :default-openeds="[indexOpen]"
           style="min-height: 100%; overflow-x: hidden"
           :default-active="active"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd40b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router>
    <div style="height: 60px; line-height: 60px; text-align: center;">
      <img src="../assets/logo.png" alt="" style="width: 17px; position: relative;top: 3px;" :style="{marginRight:srcMarR+'px'}">
      <b style="color: white" v-show="logoTextShow" >后台管理系统</b>
    </div>
    <el-menu-item index="/">
      <template slot="title"><i class="el-icon-s-home"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-film"></i>
        <span slot="title">电影管理</span>
      </template>
      <el-menu-item index="film"><i class="el-icon-receiving"></i>电影信息</el-menu-item>
    </el-submenu>
    <el-submenu index="3">
      <template slot="title"><i class="el-icon-office-building"></i>
        <span>影院管理</span>
      </template>
      <el-menu-item index="cinema"><i class="el-icon-document"></i>影院基本信息</el-menu-item>
      <el-menu-item index="showings"><i class="el-icon-video-camera-solid"></i>电影场次信息</el-menu-item>
    </el-submenu>
    <el-submenu index="4">
      <template slot="title"><i class="el-icon-user-solid"></i>
        <span>用户管理</span>
      </template>
      <el-menu-item index="user"><i class="el-icon-postcard"></i>用户信息</el-menu-item>
      <el-menu-item index="bill"><i class="el-icon-s-order"></i>订单管理</el-menu-item>
      <el-menu-item index="evaluate"><i class="el-icon-chat-line-square"></i>评价管理</el-menu-item>
    </el-submenu>
  </el-menu>

</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    srcMarR: Number,
    logoTextShow: Boolean,
    active: String,
    indexOpen: String
  }

}
</script>

<style scoped>

</style>