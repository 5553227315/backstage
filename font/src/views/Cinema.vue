<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入影院名称" suffix-icon="el-icon-search" v-model="cinemaName"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" class="ml-10" placeholder="请输入影院地址" suffix-icon="el-icon-search" v-model="cinemaAddress"
                @keyup.enter.native="load"></el-input>
      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleOpen('create',null)">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-dialog :title="dialogTitle" :visible.sync="addOrUpdataVisible" width="50%" @close="closeDialog">
        <el-form :model="cinemaForm" label-width="130px" :rules="rules" ref="cinemaForm" >
          <el-form-item label="影院名称：" prop="cinemaName">
            <el-input v-model="cinemaForm.cinemaName" autocomplete="off" style="width: 85%" aria-required="true"></el-input>
          </el-form-item>
          <el-form-item label="地址：" prop="cinemaAddress">
            <el-input v-model="cinemaForm.cinemaAddress" autocomplete="off" style="width: 85%"></el-input>
          </el-form-item>
          <el-form-item label="影厅类型：" prop="cinemaType">
            <el-select v-model="cinemaForm.cinemaType" multiple placeholder="请选择类型" style="width: 85%">
              <el-option
                  v-for="item in typeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="影院电话：" prop="cinemaTel">
            <el-select
                v-model="cinemaForm.cinemaTel"
                multiple
                style="width: 85%"
                filterable
                allow-create
                default-first-option
                placeholder="请填写电话">
              <el-option
                  v-for="item in telLiest"
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
      <el-table-column prop="cinemaName" label="影院名称" width="300">
      </el-table-column>
      <el-table-column prop="cinemaAddress" label="地址" width="600">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot=scope>
          <el-button
              type="success"
              style="margin-left: 10px"
              @click="handleOpen('update',scope.row)">
            编辑 <i class="el-icon-edit-outline"></i>
          </el-button>
          <el-button
              type="danger"
              style="margin-left: 10px"
              @click="deleteopen(scope.row.cinemaId)">
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
  name: "Cinema",
  components: {Skeleton},
  data() {

    return {
      loading: true,
      tableData: [],
      total: 0,
      cinemaName: "",
      cinemaAddress: "",
      addOrUpdataVisible: false,
      dialogTitle: "",
      cinemaForm: this.emptycinemaForm(),
      typeList: [
        {value:"4K厅", label:"4K厅"},
        {value:"4D厅", label:"4D厅"},
        {value:"巨幕厅", label:"巨幕厅"},
        {value:"4DX厅", label:"4DX厅"},
        {value:"IMAX厅", label:"IMAX厅"},
        {value:"ReaID厅", label:"ReaID厅"},
        {value:"LUXE巨幕厅", label:"LUXE巨幕厅"},
        {value:"ReaID 6FL厅", label:"ReaID 6FL厅"},
        {value:"CGS中国巨幕厅", label:"CGS中国巨幕厅"},
        {value:"DTS:X临境音厅", label:"DTS:X临境音厅"},
        {value:"Dolby Cinema厅", label:"Dolby Cinema厅"},
      ],
      telLiest: [
        {value:"",label:""}
      ],
      //新增校验
      rules: {
        cinemaName: [
          {required: true, message: '请输入电影名称', trigger: 'blur'}
        ],
        cinemaAddress: [
          {required: true, message: '请输入影院地址', trigger: 'blur'}
        ]
      },
      pageNum: 1,
      pageSize: 5
    }
  },

  created() {
    //请求分页查询数据
    this.load()
  },

  methods: {
    //新增或更改信息弹窗
    handleOpen(type, row) {
      this.addOrUpdataVisible = true;
      // row.cinemaType = row.cinemaType.split(',')
      row.cinemaType = row.cinemaType.split(',')
      row.cinemaTel = row.cinemaTel.split(';')

      if (type === 'create') {
        this.dialogTitle = '添加影院';
        this.cinemaForm = this.emptycinemaForm();
        setTimeout(() => {this.$refs.cinemaForm.clearValidate();}, 10);
      } else if (type === 'update') {
        this.cinemaForm = row;
        this.dialogTitle = '编辑影院资料';

      }
    },

    //弹窗关闭
    closeDialog() {
      this.cinemaForm = this.emptycinemaForm();
      setTimeout(() => {this.$refs.cinemaForm.clearValidate();}, 10);

      this.load();
    },

    emptycinemaForm(){
      return{
        cinemaId: "",
        cinemaName: "",
        cinemaType:"",
        cinemaAddress: "",
        cinemaTel: "",
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
      window.open("http://localhost:9090/cinema/export")
    },


    //弹窗保存按钮
    save() {
      // 校验
      this.$refs['cinemaForm'].validate((valid) => {
        if (valid) {

          //数组转化String保存    .split(',').join('/')逗号换斜杠
          this.cinemaForm.cinemaType = this.cinemaForm.cinemaType.toString()
          //数组转化String保存    .split(',').join(';')逗号换分号
          this.cinemaForm.cinemaTel = this.cinemaForm.cinemaTel.toString().split(',').join(';')
          console.log(this.cinemaForm)

          this.request.post("/cinema", this.cinemaForm).then(res => {
            if (res) {
              this.$message.success("操作成功")
              this.load()
            } else {
              this.$message.error("操作失败")
            }
            this.addOrUpdataVisible = false;
            this.cinemaForm = this.emptycinemaForm();
            setTimeout(() => {this.$refs.cinemaForm.clearValidate();}, 10);
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
        this.request.delete("/cinema/" + id).then(res => {
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
      this.request("/cinema/cinemaPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cinemaName: this.cinemaName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
        this.loading = false
      })
    },
    reload() {
      this.cinemaName = ""
      this.cinemaAddress = ""
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