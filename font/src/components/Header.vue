<template>
  <div style="line-height: 60px;display: flex">
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
<!--      <el-breadcrumb separator="/" style="display: inline-block" class="ml-10">-->
<!--        <el-breadcrumb-item v-for="item in paths">{{item.name}}</el-breadcrumb-item>-->
<!--        <el-breadcrumb-item><a href="/user">活动管理</a></el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->
    </div>

    <el-dropdown style="cursor:pointer">
      <span style="margin-right:20px">{{adminName}}</span><i class="el-icon-arrow-down" ></i>
      <el-dropdown-menu slot="dropdown" style="width: 130px; text-align: center">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <span @click="exit">退出</span></el-dropdown-item>
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
    exit:Function
  },
  data() {
    return {
      paths:[]
    }
  },
  methods: {

  }
}

</script>

<style scoped>

</style>