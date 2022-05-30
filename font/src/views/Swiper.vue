<template>
  <div>
    <div style="margin-left: 30px;font-size: 30px;font-weight: 600;margin-top: 20px">轮播图</div>
    <div style="display: flex;flex-direction: row;margin: 10px 20px;flex-wrap: wrap">
      <div v-for="img in swiperList" style="display: flex;flex-direction: column;margin: 10px; border: 1px solid #666;border-radius: 10px ">
        <img :src="img.filesUrl" alt="" style="height: 180px;width: 320px;border-radius: 10px;margin: 10px">
        <div style="display: flex;flex-direction: row;justify-content: center; padding: 10px 0;border-top: 1px solid #666">
          <el-button :disabled="img.swiperRank===0" type="primary" @click="update(img.swiperRank,'front')">排名前移</el-button>
          <el-upload
              :action="'http://'+serverIp+':9090/files/upload'"
              :on-success="function (res){return  save(res,img.swiperRank)}"
              :limit="1"
              :show-file-list="false"
              style="margin: 0 10px">
            <el-button  type="success">前面插入</el-button>
          </el-upload>
            <el-button type="danger" @click="deleteopen(img.filesId)">删除</el-button>
          <el-button :disabled="img.swiperRank===swiperList.length-1" type="primary" @click="update(img.swiperRank,'after')">排名后移</el-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

import {serverIp} from "../../public/config";

export default {
  name: "Swiper",
  data(){
    return{
      serverIp:serverIp,
      swiperList:[],
    }
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.request.get("/Swiper").then(res =>{
        this.swiperList = res.data
      })
    },
    update(swiperRank,move){
        this.request.get("/Swiper/update",{
          params:{
            swiperRank:swiperRank,
            move:move
          }
        }).then(res =>{
          console.log(res)
          if (res.code==="200"){
            this.$message({
              type: 'success',
              message: res.msg
            });
          }else if(res.code==="201"){
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
          this.load()
        })
    },
    //新增
    save(res,swiperRank){
      this.request.get("/Swiper/save",{
        params:{
          filesUrl:res,
          swiperRank:swiperRank
        }
      }).then(res =>{
        console.log(res)
        if (res.code==="200"){
          this.$message({
            type: 'success',
            message: res.msg
          });
        }else if(res.code==="201"){
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
        this.load()
      })
    },

    //删除按钮
    deleteopen(id) {
      this.$confirm('此操作将永久删除该图, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/Swiper/" + id).then(res => {
          if (res.code==="200") {
            this.$message({
              type: 'success',
              message: res.msg
            });
            this.load();
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },

  },

}

</script>

<style scoped>

</style>