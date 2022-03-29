<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入电影名" suffix-icon="el-icon-search" v-model="filmName"
                 @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入片源地" suffix-icon="el-icon-search" v-model="filmSource"
                @keyup.enter.native="load" class="ml-10"></el-input>
      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleOpen('create',null)">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-dialog :title="dialogTitle" :visible.sync="addOrUpdataVisible" width="50%" @close="closeDialog">
        <el-form :model="filmForm" label-width="130px" :rules="rules" ref="filmForm" >
          <el-form-item label="电影名称：" prop="filmName">
              <el-input v-model="filmForm.filmName"
                        autocomplete="off"
                        style="width: 85%"
                        aria-required="true"></el-input>
          </el-form-item>
          <el-form-item label="导演：" prop="director">
            <el-input v-model="filmForm.performer" autocomplete="off" style="width: 85%"></el-input>
          </el-form-item>
          <el-form-item label="主演：" prop="performer">
            <el-input v-model="filmForm.director" autocomplete="off" style="width: 85%"></el-input>
          </el-form-item>
          <el-form-item label="片长：" prop="filmLength">
            <el-input v-model="filmForm.filmLength"
                      autocomplete="off"
                      style="width: 85%"
                      oninput="value=value.replace(/\D|^0/g,'')"><template slot="append">分钟</template></el-input>
          </el-form-item>
          <el-form-item label="电影类型：" prop="filmType">
            <el-select v-model="filmForm.filmType" multiple placeholder="请选择类型" style="width: 85%">
              <el-option
                  v-for="item in filmTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="片源地：" prop="filmSource">
            <el-input v-model="filmForm.filmSource" autocomplete="off" style="width: 85%"></el-input>
          </el-form-item>
          <el-form-item label="语言：" prop="filmLanguage">
            <el-input v-model="filmForm.filmLanguage" autocomplete="off" style="width: 85%"></el-input>
          </el-form-item>
          <el-form-item label="上映时间：" prop="releaseDate">
            <el-date-picker type="date" placeholder="选择日期" v-model="filmForm.releaseDate" style="width: 85%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="是否上映：" prop="canShow">
            <el-select v-model="filmForm.canShow" placeholder="是否上映" style="width: 85%">
              <el-option label="已上映" value="1"></el-option>
              <el-option label="已下架" value="2"></el-option>
              <el-option label="待上映" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="介绍：" prop="plotIntro">
            <el-input v-model="filmForm.plotIntro"
                      type="textarea"
                      autocomplete="off"
                      style="width: 85%"
                      :autosize="{ minRows: 2, maxRows: 4}"></el-input>
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
      <el-table-column type="expand">
        <template v-slot=props>
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="电影名称：">
              <span>{{ props.row.filmName}}</span>
            </el-form-item>
            <el-form-item label="导演：">
              <span>{{ props.row.director}}</span>
            </el-form-item>
            <el-form-item label="片长：">
              <span>{{ props.row.filmLength }}分钟</span>
            </el-form-item>
            <el-form-item label="电影类型：">
              <span>{{ props.row.filmType }}</span>
            </el-form-item>
            <el-form-item label="片源地：">
              <span >{{props.row.filmSource}}</span>
            </el-form-item>
            <el-form-item label="上映时间：">
              <span>{{ formatDate(props.row)}}</span>
            </el-form-item>
            <el-form-item label="是否上映：">
              <span>{{ canShow(props.row)}}</span>
            </el-form-item>
            <el-form-item label="演员：">
              <span>{{ props.row.performer }}</span>
            </el-form-item>
            <el-form-item label="电影语言：">
              <span>{{ props.row.filmLanguage }}</span>
            </el-form-item>
          </el-form>
          <el-button
              type="info"
              @click="routerToData(props.row)"
              style="margin-left: 968px;width: 70px;position:absolute;z-index: 1;margin-top: -40px">
            影评</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="filmName" label="电影名称" width="200">
      </el-table-column>
      <el-table-column prop="director" label="导演" width="150">
      </el-table-column>
      <el-table-column prop="filmLanguage" label="语言" width="170">
      </el-table-column>
      <el-table-column prop="filmSource" label="片源地" width="130">
      </el-table-column>
      <el-table-column prop="releaseDate" label="上映时间" width="150" :formatter="formatDate">
      </el-table-column>
      <el-table-column
          align="center"
          prop="canShow"
          label="是否上映"
          width="100"
          :formatter="canShow">
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
  name: "Film",
  components: {Skeleton},
  data() {

    return {
      loading: true,
      tableData: [],
      total: 0,
      filmName: "",
      filmSource: "",
      addOrUpdataVisible: false,
      dialogTitle: "",
      filmForm: this.emptyFilmForm(),
      filmTypeList: [
        {value:"动作", label:"动作"},
        {value:"冒险", label:"冒险"},
        {value:"喜剧", label:"喜剧"},
        {value:"剧情", label:"剧情"},
        {value:"幻想", label:"幻想"},
        {value:"恐怖", label:"恐怖"},
        {value:"爱情", label:"爱情"},
        {value:"历史", label:"历史"}
      ],
      //新增校验
      rules: {
        filmName: [
          {required: true, message: '请输入电影名称', trigger: 'blur'}
        ],
        filmSource: [
          {required: true, message: '请输入片源地', trigger: 'blur'}
        ],
        releaseDate: [
          {required: true, message: '请选择上映时间', trigger: 'change'}
        ],
        canShow: [
          {required: true, message: '请选择是否上映', trigger: 'change'}
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
      // row.filmType = row.filmType.split(',')
      row.filmType = row.filmType.split(',')

      if (type === 'create') {
        this.dialogTitle = '添加电影';
        this.filmForm = this.emptyFilmForm();
        setTimeout(() => {this.$refs.filmForm.clearValidate();}, 10);
      } else if (type === 'update') {
        this.filmForm = row;
        this.dialogTitle = '编辑电影资料';

      }
    },

    //弹窗关闭
    closeDialog() {
      this.filmForm = this.emptyFilmForm();
      setTimeout(() => {this.$refs.filmForm.clearValidate();}, 10);

      this.load();
    },

    emptyFilmForm(){
      return{
        filmId: "",
        filmName: "",
        canShow: "",
        releaseDate: "",
        filmSource: "",
        director:"",
        filmLength:"",
        filmType:"",
        plotIntro:"",
        filmCover:"",
        performer:"",
        filmLanguage: ""
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
    routerToData(row) {
      this.$router.push({
        path:'/evaluate',
        query:{
          filmName: row.filmName
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/film/export")
    },


    //弹窗保存按钮
    save() {
      // 校验
      this.$refs['filmForm'].validate((valid) => {
        if (valid) {
          //日期转换
          let date = new Date(this.filmForm.releaseDate)
          date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
          this.filmForm.releaseDate = date
          //数组转化String保存    .split(',').join('/')逗号换斜杠
          this.filmForm.filmType = this.filmForm.filmType.toString()
          console.log(this.filmForm)

          this.request.post("/film", this.filmForm).then(res => {
            if (res) {
              this.$message.success("操作成功")
              this.load()
            } else {
              this.$message.error("操作失败")
            }
            this.addOrUpdataVisible = false;
            this.filmForm = this.emptyFilmForm();
            setTimeout(() => {this.$refs.filmForm.clearValidate();}, 10);
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
        this.request.delete("/film/" + id).then(res => {
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

    //上映选择
    canShow(row) {
      return row.canShow === '1' ? "已上映" : row.canShow === '2' ? "已下架" : row.canShow === '3' ? "待上映" : "无";
    },

    //日期格式化和判断补0
    formatDate(row) {
      // 获取单元格数据
      let data = row.releaseDate
      if (data == null) {
        return null
      }
      let dt = new Date(data)
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

    //封装的加载数据类
    load() {
      this.request("/film/filmPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          filmName: this.filmName,
          filmSource: this.filmSource
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
        this.loading = false
      })
    },
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