<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入影院名称" suffix-icon="el-icon-search" v-model="cinemaName"
                @keyup.enter.native="load"></el-input>
      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleOpen('create',null)">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-dialog :title="dialogTitle" :visible.sync="addOrUpdataVisible" width="50%" @close="closeDialog">
        <el-form :model="hallForm" label-width="130px" :rules="rules" ref="hallForm" >
          <el-form-item label="影院名称：" prop="cinemaName">
            <el-select
                v-model="hallForm.cinemaName"
                :clearable="true"
                filterable
                remote
                reserve-keyword
                placeholder="请输入影院名称关键词"
                style="width: 85%"
                :remote-method="(query)=>{
                  cinemaremoteMethod(query,'')}"
                :loading="loadingcinema"
                @change="cinemaselectchange($event)"
            >
              <el-option
                  v-for="item in emptycinemaList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="放映厅号：" prop="cinemaName">
            <el-input v-model="hallForm.hallNumber" autocomplete="off" style="width: 85%" aria-required="true"></el-input>
          </el-form-item>
          <el-form-item label="影厅类型：" prop="cinemaType">
            <el-select v-model="hallForm.cinemaType" placeholder="请选择类型" style="width: 85%">
              <el-option
                  v-for="item in emptycinemaTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="handleClose">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
              </span>
      </el-dialog>
      <el-button type="primary" class="ml-10" @click="exp">导出 <i class="el-icon-download"></i></el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#f1f5f8',borderColor:'#CECECE'}">
      <el-table-column prop="cinemaName" label="影院名称" width="400">
      </el-table-column>
      <el-table-column prop="hallNumber" label="放映厅号" width="200">
      </el-table-column>
      <el-table-column prop="cinemaType" label="影厅类型" width="250">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot=scope>
          <el-button
              type="primary"
              style="margin-left: 10px"
              @click="routerToSeat(scope.row)">
            编辑座椅 <i class="el-icon-edit-outline"></i>
          </el-button>
          <el-button
              type="success"
              style="margin-left: 10px"
              @click="handleOpen('update',scope.row)">
            编辑资料 <i class="el-icon-edit-outline"></i>
          </el-button>
          <el-button
              type="danger"
              style="margin-left: 10px"
              @click="deleteopen(scope.row.hallId)">
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
          :page-sizes="[ 6, 12, 18, 24]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import Skeleton from "../components/Skeleton";
import {serverIp} from "../../public/config";

export default {
  name: "Hall",
  components: {Skeleton},
  data() {

    return {
      loading: true,
      tableData: [],
      total: 0,
      cinemaName: "",
      addOrUpdataVisible: false,
      dialogTitle: "",
      hallForm: this.emptyhallForm(),
      //新增校验
      rules: {
        hallNumber: [
          {required: true, message: '放映厅号', trigger: 'blur'}
        ],
        cinemaName: [
          {required: true, message: '请输入影院名称', trigger: 'blur'}
        ],
        cinemaType: [
          {required: true, message: '请选择类型', trigger: 'change'}
        ],
      },
      loadingcinema:false,
      emptycinemaTypeList:[],
      emptycinemaList:[],
      pageNum: 1,
      pageSize: 6
    }
  },

  created() {
    //请求分页查询数据
    this.load()
  },

  methods: {
    //影院远程搜索
    cinemaremoteMethod(query) {
      //远程搜索机构
      console.log(query)
      this.request('/showings/cinemaName',{
        params:{
          cinemaName:query
        }}).then(res =>{
        this.emptycinemaList = res.data.map(item =>{
          this.hallForm.cinemaId=item.cinemaId
          console.log("这是cinemaId",this.hallForm.cinemaId)
          this.cinematyperemoteMethod(this.hallForm.cinemaId)
          return {
            value: item.cinemaName,
            label: item.cinemaName,
            cinemaId: item.cinemaId,
          }
        })
        console.log("这是emptycinemaList",this.emptycinemaList)
        console.log("这是hallForm",this.hallForm)

      })
    },
    cinematyperemoteMethod(query){
      //远程搜索机构
      console.log(query)
      this.request('/hall/cinemaType',{
        params:{
          cinemaId:query
        }}).then(res =>{
        this.emptycinemaTypeList = res.data.map(item =>{
          console.log("这是类型",res)
          return {
            value: item,
            label: item
          }
        })
        console.log("这是emptycinemaTypeList",this.emptycinemaTypeList)
        console.log("这是hallForm",this.hallForm)

      })
    },
    cinemaselectchange(event){
      this.cinemaremoteMethod(event)
    },
    //新增或更改信息弹窗
    handleOpen(type, row) {
      this.addOrUpdataVisible = true;

      if (type === 'create') {
        this.dialogTitle = '添加放映厅';
        this.hallForm = this.emptyhallForm();
        setTimeout(() => {this.$refs.hallForm.clearValidate();}, 10);
      } else if (type === 'update') {

        row.cinemaType = row.cinemaType.split(',')
        row.cinemaTel = row.cinemaTel.split(';')
        this.hallForm = row;
        this.dialogTitle = '编辑放映厅资料';

      }
    },

    //弹窗关闭
    closeDialog() {
      this.hallForm = this.emptyhallForm();
      setTimeout(() => {this.$refs.hallForm.clearValidate();}, 10);

      this.load();
    },
    routerToSeat(row) {
      console.log(row.hallId)
      this.$router.push({
        path:'/seat',
        query:{
          hallId:row.hallId,
          hallNumber:row.hallNumber,
          cinemaId:row.cinemaId,
          cinemaName:row.cinemaName,
          cinemaType:row.cinemaType,

        }

      })
    },

    emptyhallForm(){
      return{
        hallId:"",
        hallNumber: "",
        cinemaId: "",
        cinemaName: "",
        cinemaType:""
      }
    },
    //弹窗关闭按钮
    handleClose() {
      this.$confirm('确认关闭？')
          .then(_ => {
            this.addOrUpdataVisible = false;
          })
          .catch(_ => {
          });
    },


    exp(){
      window.open(`http://${serverIp}:9090/hall/export`)
    },


    //弹窗保存按钮
    save() {
      // 校验
      this.$refs['hallForm'].validate((valid) => {
        if (valid) {
          this.request.post("/hall", this.hallForm).then(res => {
            console.log(res)
            if (res.code==="200") {
              this.$message.success(res.msg)
              this.load()
            } else {
              this.$message.error(res.msg)
            }
            this.addOrUpdataVisible = false;
            this.hallForm = this.emptyhallForm();
            setTimeout(() => {this.$refs.hallForm.clearValidate();}, 10);
          })

        } else {
          return false;
        }
      });
    },

    //删除按钮
    deleteopen(id) {
      this.$confirm('此操作将永久删除该电影, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/hall/" + id).then(res => {
          console.log(res)
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


    //封装的加载数据类
    load() {
      this.request("/hall/hallPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cinemaName: this.cinemaName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    reload() {
      this.cinemaName = ""
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