<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入电影称" suffix-icon="el-icon-search" v-model="filmName"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入影院名称" suffix-icon="el-icon-search" v-model="cinemaName"
                @keyup.enter.native="load" class="ml-10"></el-input>
      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleOpen('create',null)">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-dialog id="showings" :title="dialogTitle" :visible.sync="addOrUpdataVisible" width="50%" @close="closeDialog">
<!--        <el-dialog :visible.sync="progress">-->
<!--          <el-progress :percentage="percentage" :status="status" :format="format"></el-progress>-->
<!--        </el-dialog>-->
        <el-form :model="showingsForm" label-width="130px" :rules="rules" ref="showingsForm" >
          <el-form-item label="电影名称：" prop="filmName">
<!--            可搜索filterable；可远程搜索remote-->
            <el-select
                :disabled="dialogTitle!=='添加场次'"
                v-model="showingsForm.filmName"
                :clearable="true"
                filterable
                remote
                reserve-keyword
                placeholder="请输电影名称入关键词"
                style="width: 85%"
                :remote-method="(query)=>{
                  filmremoteMethod(query,'')}"
                :loading="loadingfilm"
                @change="filmselectchange($event)"
            >
              <el-option
                  v-for="item in emptyfilmlist"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="影院名称：" prop="cinemaName">
            <el-select
                :disabled="dialogTitle!=='添加场次'"
                v-model="showingsForm.cinemaName"
                :clearable="true"
                filterable
                remote
                clearable
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
          <el-form-item label="场次日期：" prop="showingsDate">
            <el-date-picker type="date"
                            :disabled="dialogTitle!=='添加场次'"
                            placeholder="选择日期"
                            v-model="showingsForm.showingsDate"
                            style="width: 85%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="开始时间：" prop="filmstartTime">
            <el-time-picker placeholder="选择开场时间" :disabled="dialogTitle!=='添加场次'" v-model="showingsForm.filmstartTime" style="width: 85%;">
            </el-time-picker>
          </el-form-item>
          <el-form-item label="语言视觉：" prop="showingsVision">
            <el-select v-model="showingsForm.showingsVision" :disabled="dialogTitle!=='添加场次'" placeholder="选择视觉" style="width: 85%">
              <el-option label="2D" value="2D"></el-option>
              <el-option label="3D" value="3D"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="放映厅：" prop="hallNumber">
            <el-select v-model="showingsForm.hallNumber" :disabled="dialogTitle!=='添加场次'" clearable placeholder="选择放映厅" style="width: 85%" @change="hallremoteMethod(showingsForm.cinemaId,$event)">
              <el-option
                  v-for="item in emptyhallForm"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">

              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="本场价格：" prop="showingsPrice">
            <el-input v-model="showingsForm.showingsPrice" autocomplete="off" style="width: 85%"></el-input>
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
          <el-form label-position="left" inline class="demo-table-expand2">
            <el-form-item label="电影名称：">
              <span>{{ props.row.filmName}}</span>
            </el-form-item>
            <el-form-item label="电影评分：">
              <span>{{ props.row.filmScore}}</span>
            </el-form-item>
            <el-form-item label="影院名称：">
              <span>{{ props.row.cinemaName}}</span>
            </el-form-item>
            <el-form-item label="电影语言：">
              <span>{{ props.row.filmLanguage }}</span>
            </el-form-item>
            <el-form-item label="放映厅号：">
              <span>{{ props.row.hallNumber }}</span>
            </el-form-item>
            <el-form-item label="片长：">
              <span >{{props.row.filmLength}}分钟 </span>
            </el-form-item>
            <el-form-item label="场次日期：">
              <span>{{ formatDate(props.row)}}</span>
            </el-form-item>
            <el-form-item label="视觉：">
              <span>{{ props.row.showingsVision }}</span>
            </el-form-item>
            <el-form-item label="开始时间：">
              <span>{{ startT(props.row) }}</span>
            </el-form-item>
            <el-form-item label="本场价格：">
              <span>{{ props.row.showingsPrice }}</span>
            </el-form-item>
            <el-form-item label="结束时间：">
              <span>{{ endT(props.row)}}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="cinemaName" label="影院名" width="200">
      </el-table-column>
      <el-table-column prop="filmName" label="电影名" width="150">
      </el-table-column>
      <el-table-column prop="hallNumber" label="放映厅" width="80">
      </el-table-column>
      <el-table-column prop="showingsDate" label="日期" width="130" :formatter="formatDate">
      </el-table-column>
      <el-table-column prop="filmstartTime" label="开始时间" width="100" :formatter="startT">
      </el-table-column>
      <el-table-column prop="filmendTime" label="结束时间" width="100" :formatter="endT">
      </el-table-column>
      <el-table-column prop="filmLanguage" label="语言" width="100">
      </el-table-column>
      <el-table-column align="center" prop="showingsVision" label="视觉" width="50">
      </el-table-column>
      <el-table-column prop="showingsPrice" label="本场价格" width="80">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot=scope >
          <el-button
              v-show="isShowBtn(scope.row)"
              type="success"
              style="margin-left: 10px"
              @click="handleOpen('update',scope.row)">
            编辑 <i class="el-icon-edit-outline"></i>
          </el-button>
          <el-button
              v-show="isShowBtn(scope.row)"
              type="danger"
              style="margin-left: 10px"
              @click="deleteopen(scope.row.showingsId)">
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
import {serverIp} from "../../public/config";

export default {
  name: "Showings",
  components: {Skeleton},
  data() {
    var timeValidator = (rule,value,callback)=>{
      let date = this.showingsForm.showingsDate
      console.log("这是现在的时间",(date).toString().substring(0,10))

      if (!date){
        callback(new Error("请先选择开始时间"))
      }else {
        let newDate = (new Date()).toString().substring(0,10)
        console.log("这是现在的时间2",newDate)
        let h = new Date().getHours()
        let m = new Date().getMinutes()
        let valueh = new Date(value).getHours()
        let valuem = new Date(value).getMinutes()
        if ((date).toString().substring(0,10)===newDate){
          if (valueh>h){
            callback()
          }else {
            if (valuem>m){
              callback()
            }else {
              callback(new Error("开场时间不得设置在目前时间之前"))
            }
          }
        }else {
          callback();
        }
      }

    };
    var dateValidator = (rule,value,callback)=>{


      if(!value){
        callback(new Error("请选择日期"))
      }else {
        let newdate = new Date()
        newdate = Date.parse(newdate.toLocaleDateString())
        value = Date.parse(value.toLocaleDateString())
        console.log("这是现在的日期",newdate)
        console.log("这是比较的日期",value)
        if(value<newdate){
          callback(new Error("不得选择今天之前的日期"))
        }else {
          callback()
        }
      }




    };
    return {
      loading: true,
      tableData: [],
      total: 0,
      filmName: "",
      cinemaName: "",
      addOrUpdataVisible: false,
      dialogTitle: "",
      showingsForm: this.emptyShowingsForm(),
      //新增校验
      rules: {
        filmName: [
          {required: true, message: '请输入电影名称', trigger: 'change'}
        ],
        cinemaName: [
          {required: true, message: '请输入片源地', trigger: 'blur'}
        ],
        showingsDate: [
          { required: true, validator: dateValidator, trigger: 'change'}
        ],
        filmstartTime: [
          {required: true, validator: timeValidator, trigger: 'change'}
        ],
        showingsVision: [
          {required: true, message: '请选择活视觉', trigger: 'change'}
        ],
        hallNumber: [
          {required: true, message: '请输入厅号', trigger: 'change'}
        ],
        showingsPrice: [
          {required: true, message: '请输入价格', trigger: 'change'}
        ]

      },
      pageNum: 1,
      pageSize: 5,
      //远程搜索组件
      filmList: [],
      cinemaList: [],
      loadingfilm: false,
      loadingcinema:false,
      emptyfilmlist:[],
      emptycinemaList: [],
      emptyhallForm: [],
      averagefilmSource:""
    }
  },

  created() {
    //请求分页查询数据
    this.load()

  },
  methods: {

    isShowBtn(row){
      var date1 = new Date();
      var date2 = new Date(row.filmstartTime);
      console.log("这是row",row)
      console.log(date1.getTime()>date2.getTime())

      if (date1.getTime()<date2.getTime()){
        return true
      }else {
        return false
      }
    },

    filmremoteMethod(query) {
      //远程搜索机构
      console.log(query)
      this.request('/showings/filmName',{
        params:{
          filmName:query
        }}).then(res =>{
          console.log(res)
        this.filmList = res.data;
        console.log(this.filmList)
        this.emptyfilmlist = this.filmList.map(item =>{
          this.showingsForm.filmId=item.filmId
          this.showingsForm.filmLength=item.filmLength
          this.showingsForm.filmLanguage=item.filmLanguage
          console.log("这是this.showingsForm.filmId",this.showingsForm.filmId)
          return {
            value: item.filmName,
            label: item.filmName,
            filmId: item.filmId,
            filmLength:item.filmLength,
            filmLanguage:item.filmLanguage
          }
        })
        console.log("这是item",this.emptyfilmlist)
      })
    },
    filmselectchange(event){
      console.log("event是：",event)
      this.filmremoteMethod(event)
    },
    //影院远程搜索
    cinemaremoteMethod(query) {
      //远程搜索机构
      console.log(query)
      this.request('/showings/cinemaName',{
        params:{
          cinemaName:query
        }}).then(res => {
        console.log(res)
        this.cinemaList = res.data;
        console.log(this.cinemaList)
        this.emptycinemaList = this.cinemaList.map(item => {
          this.showingsForm.cinemaId = item.cinemaId
          this.hallremoteMethod(this.showingsForm.cinemaId, null)
          console.log("这是cinemaId", this.showingsForm.cinemaId)
          return {
            value: item.cinemaName,
            label: item.cinemaName,
            cinemaId: item.cinemaId,
          }
        })
        console.log("这是emptycinemaList",this.emptycinemaList)
        console.log("这是showingFrom",this.showingsForm)

      })
    },
    cinemaselectchange(event){
      this.cinemaremoteMethod(event)
    },
    hallremoteMethod(cinemaId,hallNumber){
      this.request('/showings/hall',{
        params:{
          cinemaId:cinemaId,
          hallNumber: hallNumber
        }}).then(res =>{
          console.log(res)
        this.emptyhallForm = res.data.map(item=>{
          this.showingsForm.hallId=item.hallId
          this.showingsForm.cinemaType=item.cinemaType
          return {
            value: item.hallNumber,
            label: item.hallNumber,
            hallId: item.hallId,
            cinemaType:item.cinemaType
          }
        })
        console.log("emptyhallForm",this.emptyhallForm)
        console.log("hallId:",this.showingsForm.hallId)
        console.log("cinemaType:",this.showingsForm.cinemaType)
      })
    },
    //新增或更改信息弹窗
    handleOpen(type, row) {
      this.addOrUpdataVisible = true;
      if (type === 'create') {
        this.dialogTitle = '添加场次';
        this.showingsForm = this.emptyShowingsForm();
        setTimeout(() => {this.$refs.showingsForm.clearValidate();}, 10);
      } else if (type === 'update') {
        this.showingsForm = row;
        this.showingsForm.showingsDate = this.formatDate(row)
        this.dialogTitle = '编辑电场次资料';

      }
    },

    //弹窗关闭
    closeDialog() {
      setTimeout(() => {
        this.showingsForm = this.emptyShowingsForm();
        this.$refs.showingsForm.clearValidate();
      }, 10);
    },

    emptyShowingsForm(){
      return{
        showingsId: "",
        cinemaId: "",
        cinemaName: "",
        filmId: "",
        filmName: "",
        filmScore:"",
        showingsDate: "",
        filmstartTime:"",
        filmLength:"",
        filmendTime:"",
        filmLanguage:"",
        showingsVision:"",
        hallNumber: "",
        hallId:"",
        cinemaType:"",
        showingsPrice: ""
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
      window.open(`http://${serverIp}:9090/cinema/export`)
    },


    //弹窗保存按钮
     save() {
      // 校验
      let showingsdate = new Date(this.showingsForm.showingsDate)
      let showingsstarttime = new Date(this.showingsForm.filmstartTime)
      showingsdate = showingsdate.getFullYear() + '-' + (showingsdate.getMonth()+1)
          + '-' + showingsdate.getDate()
      this.showingsForm.filmstartTime = showingsdate + ' ' + this.getH(showingsstarttime)
          +':'+this.getM(showingsstarttime)+':'+this.getS(showingsstarttime)
      showingsstarttime = new Date(this.showingsForm.filmstartTime)
      console.log("开始时间是", showingsstarttime)
      console.log("获取的分钟是",showingsstarttime.getMinutes())
      console.log("加完之后的分钟是"
          ,showingsstarttime.getMinutes()+parseInt(this.showingsForm.filmLength))
      let showingsendtime = new Date(showingsstarttime.setMinutes(showingsstarttime.getMinutes()
          +parseInt(this.showingsForm.filmLength)))
      console.log("加上后的时间是",showingsendtime)
      console.log("这是加上后的小时",showingsendtime.getHours())
      this.showingsForm.filmendTime = showingsendtime.getFullYear()+ '-'
          + (showingsendtime.getMonth()+1) + '-'
          + showingsendtime.getDate()+ ' '
          + showingsendtime.getHours()+':'
          +showingsendtime.getMinutes()+':'
          +showingsendtime.getSeconds()
      console.log("这是保存的form",this.showingsForm.filmendTime)
      console.log("这是保存是电影名",this.showingsForm.filmName)

      this.$refs['showingsForm'].validate( (valid) => {
        if (valid) {
          this.request('/showings/evaluate', {
            params: {
              filmId: this.showingsForm.filmId
            }}).then(res => {
              this.showingsForm.filmScore = res.data
            console.log("这是保存的form", this.showingsForm)
            this.request.post("/showings", this.showingsForm).then(res => {
              if (res.code==="200") {
                this.$message.success(res.msg)
                this.load()
              } else if (res.code==="404"){
                this.$message.error(res.msg)
              }else{
                this.$message.error(res.msg)
              }
              this.addOrUpdataVisible = false;
              this.showingsForm = this.emptyShowingsForm();
              setTimeout(() => {
                this.$refs.showingsForm.clearValidate();
              }, 10);
            })
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
        this.request.delete("/showings/" + id).then(res => {
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


    //日期格式化和判断补0
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
    //日期补0
    getH(h) {
      let dt = new Date(h)
      if (dt.getHours()<10){
        return '0' + dt.getHours()
      }else {
        return dt.getHours()
      }
    },

    getM(m) {
      let dt = new Date(m)
      if (dt.getMinutes() < 10) {
        return '0' + dt.getMinutes()
      }else{
        return dt.getMinutes()
      }
    },
    getS(t){
      let dt = new Date(t)
      if (dt.getSeconds()<10){
        return '0' + dt.getSeconds()
      }else {
        return dt.getSeconds()
      }
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



    //封装的加载数据类
    load() {
      this.request("/showings/showingsPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          filmName: this.filmName,
          cinemaName: this.cinemaName
        }
      }).then(res => {
        if (res.code==="200"){
          console.log(res)
          this.tableData = res.data.records
          console.log(this.tableData)
          this.total = res.data.total
          this.loading = false
          console.log("这是最开始的",this.tableData)
        }else {
          this.loading = false
        }

      })
    },
    reload() {
      this.filmName = ""
      this.cinemaName= ""
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