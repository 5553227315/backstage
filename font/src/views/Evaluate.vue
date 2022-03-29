<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入用户名称" suffix-icon="el-icon-search" v-model="userName"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入账号" suffix-icon="el-icon-search" class="ml-5" v-model="userTel"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入电影名" suffix-icon="el-icon-search" class="ml-5" v-model="filmName"
                @keyup.enter.native="load"></el-input>

      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="exp">导出 <i class="el-icon-download"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#f1f5f8',borderColor:'#CECECE'}">
      <el-table-column prop="userName" label="用户名称" width="80">
      </el-table-column>
      <el-table-column prop="userTel" label="用户账号" width="100">
      </el-table-column>
      <el-table-column prop="filmName" label="电影名称" width="230">
      </el-table-column>
      <el-table-column prop="filmScore" label="电影评分" width="100">
      </el-table-column>
      <el-table-column prop="userEvaluate" label="用户影评" width="300">
      </el-table-column>
      <el-table-column prop="createEvaluate" label="评价时间">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot=scope>
          <el-button
              type="danger"
              style="margin-left: 10px"
              @click="deleteopen(scope.row.evaluateId)">
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
  name: "Evaluate",
  components: {Skeleton},
  data() {
    return {
      loading: true,
      tableData: [],
      total: 0,
      userName: "",
      userTel: "",
      filmName: "",
      pageNum: 1,
      pageSize: 5
    }
  },
  created() {
    //请求分页查询数据
    this.filmName = this.$route.query.filmName
    console.log(this.filmName)
    this.load()
  },

  methods: {

    exp(){
      window.open("http://localhost:9090/user/export")
    },

    //删除按钮
    deleteopen(id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/evaluate/" + id).then(res => {
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

    //封装的加载数据类
    load() {
      this.request("/evaluate/evaluatePage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName,
          userTel: this.userTel,
          filmName: this.filmName
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
        this.loading = false
      })
    },
    reload() {
      this.userName = ""
      this.filmName = ""
      this.userTel = ""
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