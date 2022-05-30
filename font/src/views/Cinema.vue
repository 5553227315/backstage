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
          <el-form-item label="影院品牌：" prop="cinemaBrand">
            <el-input v-model="cinemaForm.cinemaBrand"
                      autocomplete="off"
                      placeholder="非连锁请填‘其他’"
                      style="width: 85%"
                      aria-required="true"></el-input>
          </el-form-item>
          <el-form-item label="影院名称：" prop="cinemaName">
            <el-input v-model="cinemaForm.cinemaName"
                      autocomplete="off"
                      placeholder="请输入影院名称"
                      style="width: 85%"
                      aria-required="true"></el-input>
          </el-form-item>
          <el-form-item label="影院商圈：" prop="cinemaBd">
            <el-input v-model="cinemaForm.cinemaBd"
                      autocomplete="off"
                      placeholder="不靠近商圈，请填‘其他’"
                      style="width: 85%"
                      aria-required="true"></el-input>
          </el-form-item>
          <div style="display: flex;flex-direction: row; width: 87.5%;">
            <el-form-item  label="影院地址："  prop="cinemaProvince">
              <el-select  v-model="cinemaForm.cinemaProvince"
                          placeholder="请选择所在省份"
                          aria-required="true"
                          @change="changeProvinceName">
                <el-option
                    v-for="item in provinceNameList"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="ml-10" label-width="auto" prop="cinemaCity">
              <el-select v-model="cinemaForm.cinemaCity"
                         aria-required="true"
                         placeholder="请选择所在市区"
                         @change="changeCityName">
                <el-option

                    v-for="item in cityNameList"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name">
                </el-option>
              </el-select>
            </el-form-item >
            <el-form-item class="ml-10" label-width="auto" prop="cinemaCounty">
              <el-select v-model="cinemaForm.cinemaCounty"
                         aria-required="true"
                         placeholder="请选择所在县级"
                         @change="changeCountyName">
                <el-option
                    v-for="item in countyNameList"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name">
                </el-option>
              </el-select>
            </el-form-item>
          </div>
          <el-form-item label="详情地址：" prop="cinemaDetailed">
            <el-input v-model="cinemaForm.cinemaDetailed"
                      :placeholder="this.cinemaForm.cinemaCounty===''?'请选择所在县级':'请输入下级详细地址'"
                      autocomplete="off"
                      aria-required="true"
                      :disabled="this.cinemaForm.cinemaCounty===''"
                      style="width: 85%;
                      display: flex;
                      align-items: center;
                      justify-content: center" @change="getll">
              <i slot="suffix" style="font-weight: 800" :style="'color:'+(this.cinemaForm.cinemaLocation===''?'#F56C6C':'#67C23A')" :class="this.cinemaForm.cinemaLocation===''?'el-input__icon el-icon-close':'el-input__icon el-icon-check'"></i>
            </el-input>
          </el-form-item>
          <el-form-item label="改签：" prop="isRebook">
            <el-select v-model="cinemaForm.isRebook"
                       aria-required="true"
                       placeholder="请选择能否改签"
                       style="width: 85%">
              <el-option label="可以" value="1"></el-option>
              <el-option label="不可以" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="退票：" prop="isReticket">
            <el-select v-model="cinemaForm.isReticket"
                       aria-required="true"
                       placeholder="请选择能否退票"
                       style="width: 85%">
              <el-option label="可以" value="1"></el-option>
              <el-option label="不可以" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="影厅类型：" prop="cinemaType">
            <el-select v-model="cinemaForm.cinemaType"
                       aria-required="true"
                       multiple placeholder="请选择类型" style="width: 85%">
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
                placeholder="请填写电话"
                aria-required="true">
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
      <el-table-column prop="cinemaBrand" label="影院品牌" width="100">
      </el-table-column>
      <el-table-column prop="cinemaName" label="影院名称" width="300">
      </el-table-column>
      <el-table-column prop="cinemaBd" label="影院商圈" width="200">
      </el-table-column>
      <el-table-column prop="cinemaAddress" label="地址" width="450">
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
import {serverIp} from "../../public/config";


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
      provinceNameList:[{name:"1"}],
      cityNameList:[],
      countyNameList:[],
      telLiest: [
        {value:"",label:""}
      ],
      //新增校验
      rules: {
        cinemaName: [
          {required: true, message: '请输入电影名称', trigger: 'blur'}
        ],
        isRebook: [
          {required: true, message: '请选择能否改签', trigger: 'change'}
        ],
        isReticket: [
          {required: true, message: '请选择能否退票', trigger: 'blur'}
        ],
        cinemaBrand: [
          {required: true, message: '请输入影院品牌', trigger: 'blur'}
        ],
        cinemaProvince: [
          {required: true, message: '请选择省份', trigger: 'change'}
        ],
        cinemaCity: [
          {required: true, message: '请选择市级', trigger: 'change'}
        ],
        cinemaCounty: [
          {required: true, message: '请选择县级级', trigger: 'change'}
        ],
        cinemaDetailed: [
          {required: true, message: '请输入详细地址', trigger: 'blur'}
        ],
        cinemaBd: [
          {required: true, message: '请输入影院商圈', trigger: 'blur'}
        ],
        cinemaType: [
          {required: true, message: '请选择类型', trigger: 'change'}
        ]
      },
      pageNum: 1,
      pageSize: 5
    }
  },

  created() {
    this.jsget()
    //请求分页查询数据
    this.load()

  },

  methods: {
    //新增或更改信息弹窗
    handleOpen(type, row) {
      this.addOrUpdataVisible = true;

      if (type === 'create') {
        this.dialogTitle = '添加影院';
        this.cinemaForm = this.emptycinemaForm();
        setTimeout(() => {this.$refs.cinemaForm.clearValidate();}, 10);
      } else if (type === 'update') {

        row.cinemaType = row.cinemaType.split(',')
        row.cinemaTel = row.cinemaTel.split(',')
        this.cinemaForm = row;
        this.dialogTitle = '编辑影院资料';

      }
    },
    changeProvinceName(item){
      let url = 'https://www.mxnzp.com/api/address/search?type=0&value='+item
          +'&app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09'
      let request = new XMLHttpRequest();
      request.open('GET', url);
      request.send(null);
      const that = this; /*保留正確的this指向*/
      new Promise((resolve, reject) => {
        request.onload = function () {
          that.json = JSON.parse(this.response);
          /*XHR对象获取到返回信息后执行*/
          if (that.json.code === 1) {
            /*返回状态为1，即为数据获取成功*/
            resolve(that.json);
          } else {
            /*请求失败的提醒*/
            reject("JSON:Some errors have occurred");
          }
        };
      }).then((res) => {
        console.log("获取城市列表",res.data[0].pchilds)
        this.cityNameList = res.data[0].pchilds
      }).catch((err) => {
        console.log("err=>", err);
      });

    },
    changeCityName(item){
      let url = 'https://www.mxnzp.com/api/address/search?type=1&value='+item+'&app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09'
      let request = new XMLHttpRequest();
      request.open('GET', url);
      request.send(null);
      const that = this; /*保留正確的this指向*/
      new Promise((resolve, reject) => {
        request.onload = function () {
          that.json = JSON.parse(this.response);
          /*XHR对象获取到返回信息后执行*/
          if (that.json.code === 1) {
            /*返回状态为1，即为数据获取成功*/
            resolve(that.json);
          } else {
            /*请求失败的提醒*/
            reject("JSON:Some errors have occurred");
          }
        };
      }).then((res) => {
        console.log("获取区列表",res.data[0].pchilds[0].cchilds)
        this.countyNameList = res.data[0].pchilds[0].cchilds
      }).catch((err) => {
        console.log("err=>", err);
      });

    },
    //最开始是使用axios进行调用腾讯地图api(后面简称api)，
    // 会出现已拦截跨源请求：同源策略禁止读取位于 xxxx的远程资源。
    // CORS 头缺少 'Access-Control-Allow，这是不允许跨域访问，
    // 只能使用 vue-jsonp 组件了。
    getll(){
      let address = this.cinemaForm.cinemaProvince+this.cinemaForm.cinemaCity
          +this.cinemaForm.cinemaCounty+this.cinemaForm.cinemaDetailed
      console.log(address)
      this.$jsonp('https://apis.map.qq.com/ws/geocoder/v1/', {
        address:address,
        key: '5XRBZ-FZI6S-46IOA-6ZLZ6-54W7Z-PTBSD',  // key
        output: 'jsonp'
      }).then(res => {
        console.log("success",res)
        //347,306
          this.cinemaForm.cinemaLocation = res.result.location.lng + ',' + res.result.location.lat
          this.cinemaForm.cinemaAddress = address
          console.log(this.cinemaForm)
          this.$message.success("查询成功！坐标为"+this.cinemaForm.cinemaLocation)
      }).catch(err => {
        console.log("err",err)
      })

    },
    changeCountyName(){},
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
        isRebook: "",
        isReticket: "",
        cinemaType:"",
        cinemaAddress: "",
        cinemaTel: "",
        cinemaBrand:"",
        cinemaBd:"",
        cinemaProvince:"",
        cinemaCity:"",
        cinemaCounty:"",
        cinemaDetailed:"",
        cinemaLocation:""
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
      this.$refs['cinemaForm'].validate((valid) => {
        if (valid) {

          this.cinemaForm.cinemaType = this.cinemaForm.cinemaType.toString()
          this.cinemaForm.cinemaTel = this.cinemaForm.cinemaTel.toString()
          console.log(this.cinemaForm)

          this.request.post("/cinema", this.cinemaForm).then(res => {
            if (res.code==="200") {
              this.$message.success(res.msg)
              this.load()
            } else {
              this.$message.error(res.msg)
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
      this.request("/cinema/cinemaPage", {
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
    jsget(){
      let url = 'https://www.mxnzp.com/api/address/list?app_id=vqjzvfqgiaufgavr&app_secret=OGhYZmVwYUhYVjQrYmVMYnowYWIxUT09'
      let request = new XMLHttpRequest();
      request.open('GET', url);

      request.send(null);
      const that = this; /*保留正確的this指向*/
      new Promise((resolve, reject) => {
        request.onload = function () {
          that.json = JSON.parse(this.response);
          /*XHR对象获取到返回信息后执行*/
          if (that.json.code === 1) {
            /*返回状态为1，即为数据获取成功*/
            resolve(that.json);
          } else {
            /*请求失败的提醒*/
            reject("JSON:Some errors have occurred");
          }
        };
      }).then((res) => {
        console.log("获取成功");
        console.log(res)
        this.provinceNameList = res.data
      }).catch((err) => {
        console.log("err=>", err);
      });

    },


}




}
</script>

<style scoped>


</style>