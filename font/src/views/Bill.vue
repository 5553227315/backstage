<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入电影名" suffix-icon="el-icon-search" v-model="filmName"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="userName"
                @keyup.enter.native="load" class="ml-10"></el-input>
      <el-input style="width: 200px" placeholder="请输入用户账号" suffix-icon="el-icon-search" v-model="userTel"
                @keyup.enter.native="load" class="ml-10"></el-input>
      <el-input style="width: 200px" placeholder="请输入影院名" suffix-icon="el-icon-search" v-model="cinemaName"
                @keyup.enter.native="load" class="ml-10"></el-input>

      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" class="ml-10" @click="exp">导出 <i class="el-icon-download"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#f1f5f8',borderColor:'#CECECE'}">
      <el-table-column type="expand">
        <template v-slot=props>
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用户名称：">
              <span>{{ props.row.userName}}</span>
            </el-form-item>
            <el-form-item label="电影名称：">
              <span>{{ props.row.filmName}}</span>
            </el-form-item>
            <el-form-item label="电影语言：">
              <span>{{ props.row.filmLanguage }}</span>
            </el-form-item>
            <el-form-item label="视觉：">
              <span>{{ props.row.showingsVision }}</span>
            </el-form-item>
            <el-form-item label="影院名称：">
              <span>{{ props.row.cinemaName }}</span>
            </el-form-item>
            <el-form-item label="影院地址：">
              <span>{{ props.row.cinemaAddress }}</span>
            </el-form-item>
            <el-form-item label="放映厅号：">
              <span>{{ props.row.hallNumber }}</span>
            </el-form-item>
            <el-form-item label="座位位置：">
              <span>{{ props.row.hallPosition }}</span>
            </el-form-item>
            <el-form-item label="场次日期：">
              <span>{{ formatDate(props.row)}}</span>
            </el-form-item>
            <el-form-item label="开始时间：">
              <span>{{ startT(props.row) }}</span>
            </el-form-item>
            <el-form-item label="结束时间：">
              <span>{{ endT(props.row)}}</span>
            </el-form-item>
            <el-form-item label="价格：">
              <span>{{ props.row.billPrice }}</span>
            </el-form-item>
            <el-form-item label="票数：">
              <span>{{ props.row.billNumber }}</span>
            </el-form-item>
            <el-form-item label="下单时间：">
              <span>{{ props.row.createBill }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="userTel" label="账号" width="200">
      </el-table-column>
      <el-table-column prop="userName" label="用户名" width="150">
      </el-table-column>
      <el-table-column prop="filmName" label="电影名" width="170">
      </el-table-column>
      <el-table-column prop="cinemaName" label="影院名" width="130">
      </el-table-column>
      <el-table-column prop="hallNumber" label="厅号" width="130">
      </el-table-column>
      <el-table-column prop="hallPosition" label="位置" width="130">
      </el-table-column>
      <el-table-column prop="showingsDate" label="日期" width="130" :formatter="formatDate">
      </el-table-column>
      <el-table-column prop="filmstartTime" label="开始时间" width="100" :formatter="startT">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot=scope>
          <el-button
              type="danger"
              style="margin-left: 10px"
              @click="deleteopen(scope.row.filmId)">
            删除 <i class="el-icon-remove-outline"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Skeleton :loading="loading"/>
    <div class="block" style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[ 5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>

</template>

<script>
import Skeleton from "../components/Skeleton";

export default {
  name: "Bill",
  components: {Skeleton},
  data() {

    return {
      loading: true,
      tableData: [],
      total: 0,
      filmName: "",
      cinemaName: "",
      userName: "",
      userTel: "",
      addOrUpdataVisible: false,
      pageNum: 1,
      pageSize: 5
    }
  },

  created() {
    //请求分页查询数据
    this.load()
  },

  methods: {

    //导出路径
    exp(){
      window.open("http://localhost:9090/bill/export")
    },

    //删除按钮
    deleteopen(id) {
      this.$confirm('此操作将永久删除该电影, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/bill/" + id).then(res => {
          if (res) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.load();
          } else {
            this.$message({
              type: 'error',
              message: '删除失败!'
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

    //日期格式化和判断补0
    formatDate(row) {
      console.log("数据库渠道的时间",row.filmstartTime)
      let dt = new Date(row.filmstartTime)
      console.log("这是转换后的时间",dt)
      console.log("这是月份",dt.getMonth())
      //返回日期
      if (dt.getMonth() + 1 < 10) {
        if (dt.getDate() < 10) {
          return dt.getFullYear() + '-0' + (dt.getMonth() + 1) + '-0' + dt.getDate()
        } else {
          return dt.getFullYear() + '-0' + (dt.getMonth() + 1) + '-' + dt.getDate()
        }
      } else {
        if (dt.getDate() < 10) {
          return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-0' + dt.getDate()
        } else {
          return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate()
        }
      }


    },

    //开始时间
    startT(row){
      // 获取单元格数据
      let dt = new Date(row.filmstartTime)
      //返回开始时间
      if (dt.getHours()<10){
        if (dt.getMinutes()<10){
          return '0' + dt.getHours() + ' : 0'+ dt.getMinutes()
        }else {
          return '0' + dt.getHours() + ' : '+ dt.getMinutes()
        }
      }else {
        if (dt.getMinutes()<10){
          return dt.getHours() + ' : 0' + dt.getMinutes()
        }else {
          return dt.getHours() + ' : ' + dt.getMinutes()
        }
      }

    },

    //结束时间
    endT(row){
      let dt = new Date(row.filmendTime)
      if (dt.getHours()<10){
        if (dt.getMinutes()<10){
          return '0' + dt.getHours() + ' : 0'+ dt.getMinutes()
        }else {
          return '0' + dt.getHours() + ' : '+ dt.getMinutes()
        }
      }else {
        if (dt.getMinutes()<10){
          return dt.getHours() + ' : 0' + dt.getMinutes()
        }else {
          return dt.getHours() + ' : ' + dt.getMinutes()
        }
      }
    },

    //封装的加载数据类
    load() {
      this.request("/bill/billPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          filmName: this.filmName,
          cinemaName:this.cinemaName,
          userName: this.userName,
          userTel: this.userTel
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
        this.loading = false
      })
    },

    //重置搜索
    reload() {
      this.filmName = ""
      this.filmSource= ""
      this.load()
    },

    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },

  }

}
</script>

<style scoped>

</style>