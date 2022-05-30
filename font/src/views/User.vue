<template>
  <div>
    <div style="margin: 10px 0">
      <!--            回车触发-->
      <el-input style="width: 200px" placeholder="请输入用户名称" suffix-icon="el-icon-search" v-model="userName"
                @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-location-outline" class="ml-5"
                v-model="userAddress" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" placeholder="请输入账号" suffix-icon="el-icon-search" class="ml-5" v-model="userTel"
                @keyup.enter.native="load"></el-input>
      <el-button class="ml-10" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <div style="margin: 10px 0">
     <!-- <el-button type="primary" @click="handleOpen('create',null)">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-dialog :title="dialogTitle" :visible.sync="addOrUpdataVisible" width="35%" @close="closeDialog">
        <el-form
            :model="userForm"
            label-width="130px"
            :rules="rules"
            ref="userForm">
          <el-form-item label="用户名称：" prop="userName">
            <el-input v-model="userForm.userName" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="账号：" prop="userTel" :required="true">
            <el-input v-model="userForm.userTel" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="密码：" prop="userPassword" :required="true">
            <el-input type="password" v-model="userForm.userPassword" autocomplete="off" style="width: 80%"
                      @change="passChange"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="checkPass" :required="checkPassShow" v-show="checkPassShow">
            <el-input type="password" v-model="userForm.checkPass" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="地址：" prop="userAddress">
            <el-input v-model="userForm.userAddress" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="生日：" prop="birthday">
            <el-date-picker type="date" placeholder="选择日期" v-model="userForm.birthday" style="width: 80%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="性别：" prop="userGender">
            <el-select v-model="userForm.userGender" placeholder="请选择性别" style="width: 80%">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="头像上传：" prop="avatarUrl">
            <div style="display: flex;flex-direction:row;">
              <img
                  :src="userForm.avatarUrl"
                  v-show="avatarShow"
                  style="width: 150px; height: 150px; border-radius: 10px;margin-right: 10px"/>
              <el-upload
                  :action="'http://'+serverIp+':9090/files/upload'"
                  :limit="1"
                  ref="avaurl"
                  list-type="picture-card"
                  :on-success="retUrl"
                  :on-remove="handleRemove" >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible" append-to-body>
                <img width="100%" :src="dialogImageUrl" alt="">
              </el-dialog>
            </div>

          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
                <el-button @click="handleClose">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
              </span>
      </el-dialog>
                  <el-button type="primary" class="ml-10">删除 <i class="el-icon-remove-outline"></i></el-button>-->
      <el-button type="primary" @click="exp">导出 <i class="el-icon-download"></i></el-button>

    </div>


        <el-table :data="tableData" border stripe :header-cell-style="{background:'#f1f5f8',borderColor:'#CECECE'}">
          <el-table-column prop="userTel" label="账号" width="100">
          </el-table-column>
          <el-table-column prop="userName" label="用户名称" width="100">
          </el-table-column>
          <el-table-column prop="avatarUrl" label="头像" width="100">
            <template   v-slot=scope>
              <el-image
                  :src="scope.row.avatarUrl"
                  :preview-src-list="showBig(scope.row)"
                  style="width: 50px; height: 50px" />
            </template>
          </el-table-column>
          <el-table-column
              align="center"
              prop="userGender"
              label="性别"
              width="45"
              :formatter="genderf">
            <template slot-scope="scope">
              {{genderf(scope.row)}}
            </template>
          </el-table-column>
          <!--            :formatter="formatDate"-->
          <el-table-column prop="birthday" label="生日" width="100" :formatter="formatDate">
          </el-table-column>
          <el-table-column prop="userAddress" label="地址" width="300">
          </el-table-column>
          <el-table-column prop="createTime" label="注册时间">
          </el-table-column>
          <!--<el-table-column label="操作">
            <template v-slot=scope>
              <el-button
                  type="success"
                  style="margin-left: 10px"
                  @click="handleOpen('update',scope.row)">
                编辑 <i class="el-icon-edit-outline"></i>
              </el-button>-->
                              <!--坑！！！！el-popover是存在于vue外的，一般的监听事件捕捉不到
                              <el-popover
                                  placement="top"
                                  width="160"
                                  trigger="click">
                                <p>这是一段内容这是一段内容确定删除吗？</p>
                                <div style="text-align: right; margin: 0">
                                  <el-button size="mini" type="text" >取消</el-button>
                                  <el-button type="primary" size="mini" slot="reference">确定</el-button>
                                </div>
                                <el-button slot="reference" type="danger"  class="ml-5">删除 <i class="el-icon-remove-outline"></i></el-button>
                              </el-popover>-->
              <!--<el-button
                  type="danger"
                  style="margin-left: 10px"
                  @click="deleteopen(scope.row.userId)">
                删除 <i class="el-icon-remove-outline"></i>
              </el-button>
            </template>
          </el-table-column>-->
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
  name: "User",
  components: {Skeleton},
  data() {

    // //密码校验函数
    // let validatePass = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请输入密码'));
    //   } else {
    //     if (this.userForm.checkPass !== '') {
    //       this.$refs['userForm'].validateField('checkPass');
    //     }
    //     callback();
    //   }
    // };
    // //确认密码校验函数
    // let validatePass2 = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请再次输入密码'));
    //   } else if (value !== this.userForm.userPassword) {
    //     callback(new Error('两次输入密码不一致!'));
    //   } else {
    //     callback();
    //   }
    // };

    //账号校验函数
    // let validateName = (rule, value, callback) => {
    //   console.log("validateName")
    //   //加延时器是因为checkUsername()所需的处理时间要长与validateName2（），
    //   // 因此如果用户名合法，checkUsername()还没算出来值，validateName2就用了之前的值，造成错误
    //   this.request.get("/user/userTel",
    //       {
    //         params: {
    //           userTel: this.userTel
    //         }
    //       }).then(res => {
    //     setTimeout(() => {
    //       console.log("validateNamegoon")
    //       if (value === '') {
    //         callback(new Error('请输入账号'));
    //       } else if (res.code!=="401") {
    //         callback(new Error('该账号已被使用'));
    //       } else {
    //         callback();
    //       }
    //     }, 400);
    //   })
    //
    //
    // };
    return {
      serverIp:serverIp,
      loading: true,
      tableData: [],
      total: 0,
      userName: "",
      userAddress: "",
      userTel: "",
      addOrUpdataVisible: false,
      dialogTitle: "",
      checkPassShow: false,
      userForm: this.emptyUserForm(),
      rules: {},

      // //新增校验
      // rules0: {
      //   userName: [
      //     {required: true, message: '请输入用户名称', trigger: 'blur'},
      //     // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
      //   ],
      //   userTel: [
      //     {required: true, message: '请输入用户名称', trigger: 'blur'},
      //     {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur'}
      //   ],
      //   userAddress: [
      //     {required: true, message: '请输入地址', trigger: 'blur'}
      //   ],
      //   birthday: [
      //     {required: true, message: '请选择日期', trigger: 'change'}
      //   ],
      //
      //   userPassword: [
      //     {validator: validatePass, trigger: 'blur'}
      //   ],
      //   checkPass: [
      //     {validator: validatePass2, trigger: 'blur'}
      //   ],
      //   userGender: [
      //     {required: true, message: '请选择性别', trigger: 'change'}
      //   ]
      // },
      //
      // //编辑校验
      // rules1: {
      //   userName: [
      //     {required: true, message: '请输入用户名称', trigger: 'blur'},
      //     // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
      //   ],
      //   userTel: [
      //     {required: true, message: '请输入账号', trigger: 'blur'},
      //     {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur'}
      //   ],
      //
      //   userAddress: [
      //     {required: true, message: '请输入地址', trigger: 'blur'}
      //   ],
      //   birthday: [
      //     {required: true, message: '请选择日期', trigger: 'change'}
      //   ],
      //
      //   userPassword: [
      //     {required: true, message: '请输入密码', trigger: 'blur'}
      //   ],
      //   // checkPass: [
      //   //   { validator: validatePass2, trigger: 'blur' }
      //   // ],
      //   userGender: [
      //     {required: true, message: '请选择性别', trigger: 'change'}
      //   ]
      // },
      pageNum: 1,
      pageSize: 5,
      dialogImageUrl: '',
      avatarShow:true,
      dialogVisible: false
    }
  },

  created() {
    //请求分页查询数据
    this.load()
  },

  methods: {
    handleRemove(file) {
      console.log(file);

    },
    // retUrl(res) {
    //   this.userForm.avatarUrl = res;
    //   this.$refs['avaurl'].clearFiles();
    // },

    // //新增或更改信息弹窗
    // handleOpen(type, row) {
    //   this.addOrUpdataVisible = true;
    //   if (type === 'create') {
    //     this.dialogTitle = '添加账号';
    //     this.checkPassShow = true;
    //     this.rules = this.rules0;
    //     this.avatarShow = false;
    //     console.log(this.avatarShow)
    //     this.userForm = this.emptyUserForm();
    //     setTimeout(() => {this.$refs.userForm.clearValidate();}, 10);
    //
    //   } else if (type === 'update') {
    //     this.userForm = row;
    //     this.dialogTitle = '编辑资料';
    //     this.rules = this.rules1;
    //     console.log(this.userForm.avatarUrl)
    //     // this.$refs.searchForm.clearValidate();
    //     if (this.userForm.avatarUrl!=null){
    //       this.avatarShow = true;
    //     }
    //
    //   }
    // },

    //预览大图只能是列表，就返回列表
    showBig(row){
      return [row.avatarUrl]

    },
    emptyUserForm(){
      return {
        userId: "",
        userName: "",
        userTel: "",
        userGender: "",
        userAddress: "",
        birthday: "",
        userCity: "",
        userPassword: "",
        checkPass: "",
        avatarUrl:""
      }
    },
    // //弹窗关闭
    // closeDialog() {
    //   this.checkPassShow = false;
    //   this.avatarShow = true;
    //   this.userForm = this.emptyUserForm();
    //   setTimeout(() => {this.$refs.userForm.clearValidate();}, 10);
    //
    // },

    // //弹窗关闭按钮
    // handleClose() {
    //   this.$confirm('确认关闭？')
    //       .then(_ => {
    //         this.addOrUpdataVisible = false;
    //       })
    //       .catch(_ => {
    //       });
    // },

    exp(){
      window.open(`http://${serverIp}:9090/user/export`)
    },

    // //编辑密码改变事件
    // passChange() {
    //   this.checkPassShow = true;
    //   this.rules = this.rules0;
    // },


    // //弹窗保存按钮
    // save() {
    //   // 校验
    //   this.$refs['userForm'].validate((valid) => {
    //     if (valid) {
    //       //日期转换
    //       let date = new Date(this.userForm.birthday)
    //       date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    //       this.userForm.birthday = date
    //       console.log(this.userForm.birthday)
    //
    //       this.request.post("/user/save", this.userForm).then(res => {
    //         if (res) {
    //           this.$message.success("操作成功")
    //           this.load()
    //         } else {
    //           this.$message.error("操作失败")
    //         }
    //         this.addOrUpdataVisible = false;
    //       })
    //
    //     } else {
    //       return false;
    //     }
    //   });
    // },

    //删除按钮
    // deleteopen(id) {
    //   this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.request.delete("/user/" + id).then(res => {
    //       if (res) {
    //         this.$message({
    //           type: 'success',
    //           message: '删除成功!'
    //         });
    //         this.load();
    //       } else {
    //         this.$message({
    //           type: 'error',
    //           message: '删除失败!'
    //         })
    //       }
    //     })
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消删除'
    //     });
    //   });
    //
    // },

    //性别选择
    genderf(row) {
      return row.userGender === '1' ? "男" : row.userGender === '2' ? "女" : "无";
    },

    //日期格式化和判断补0
    formatDate(row, column) {
      // 获取单元格数据
      let data = row[column.property]
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
      this.request("/user/userPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.userName,
          userAddress: this.userAddress,
          userTel: this.userTel
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
        this.loading = false
      })
    },
    reload() {
      this.userName = ""
      this.userAddress = ""
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

