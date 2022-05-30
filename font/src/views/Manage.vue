<template>
    <el-container>
      <el-aside :width="asideWidth+'px'" style="background-color: rgb(238, 241, 246); min-height: 100vh;box-shadow: 2px 0 6px rgb(0 21 41 /35%)">
        <Aside :isCollapse="isCollapse" :srcMarR="srcMarR" :logoTextShow="logoTextShow" :active="active" :indexOpen="indexOpen"/>
      </el-aside>
      <el-container>

        <el-header style="border-bottom: 1px solid #cccccc">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :adminName="adminName" :exit="exit"/>
        </el-header>

        <el-main style="padding-top: 5px">
<!--          子路由在<router-view />中展示-->
          <router-view />
        </el-main>


      </el-container>
    </el-container>


</template>

<script>

import Aside from "../components/Aside";
import Header from "../components/Header";
import router from "../router";

export default {
  name: 'Manage',
  components: {Header, Aside},
  data(){
    return {
      collapseBtnClass:'el-icon-s-fold',//收缩按钮图标
      isCollapse:false,//折叠的边框
      asideWidth:200,
      srcMarR:5,
      logoTextShow:true,
      active: "",
      indexOpen: "1",
      adminName: ""
    }
  },
  mounted() {
    this.active = this.$route.name
    switch (this.active){
      case "film": this.indexOpen = "2";break
      case "cinema": this.indexOpen = "3";break
      case "showings": this.indexOpen = "3";break
      case "user": this.indexOpen = "4";break
      case "evaluate": this.indexOpen = "4";break
      case "bill": this.indexOpen = "4";break
    }
    console.log(this.active)
  },
  created() {
    this.adminName = window.localStorage.getItem("adminName")
  },
  methods:{
    collapse(){  //点击收缩按钮触发
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.asideWidth=64
        this.srcMarR=0
        this.collapseBtnClass='el-icon-s-unfold'
        this.logoTextShow=!this.logoTextShow
      }else {
        this.asideWidth=200
        this.srcMarR=5
        this.collapseBtnClass='el-icon-s-fold'
        this.logoTextShow=!this.logoTextShow
      }
    },
    exit(){
      this.$router.push('/login')
      window.localStorage.clear()
      this.$message.success("退出成功！")
      window.localStorage.removeItem("token")

    }
  }
}
</script>
