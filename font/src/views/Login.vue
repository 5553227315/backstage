<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border: 10px ;border-radius: 10px;">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><p>登录</p></div>
      <el-form :model="form"
               :rules="rules"
               ref="form">
        <el-form-item  prop="admin">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"   placeholder="请输入管理员账号" v-model="form.admin"@keyup.enter.native="login"/>
        </el-form-item>
        <el-form-item  prop="adminPassword">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" show-password placeholder="请输入管理员密码" v-model="form.adminPassword"@keyup.enter.native="login"/>
        </el-form-item>
      </el-form>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" style="width: 100%" @click="login" >登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "../router";

export default {
  name: "Login",
  data() {
    return {
      form: {
        admin: '',
        adminPassword: '',
      },
      //表单验证，需要在el-form-item元素中增加 prop 属性
      rules: {
        admin: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        adminPassword: [
          {required: true, message: '密码不可为空', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {

    loginerror(message) {
      this.$message({
        showClose: true,
        message: message,
        type: 'error'
      });
    },
    loginsuccess(message) {
      this.$message({
        showClose: true,
        message: message,
        type: 'success'
      });
    },

    login() {
      //为表单绑定验证功能
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log("这是登录信息",this.form)
          //使用vue-router路由到指定页面，该方式称之为编程式导航
          this.request.post("/admin/login", this.form).then(res => {
              window.localStorage.setItem("token",res.data.token)
            window.localStorage.setItem("adminName",res.data.adminName)
            setRoutes()
            console.log("路由已经设置")
              this.loginsuccess(res.msg)
              this.$router.push("/")
              console.log("状态是",res)
          })
        } else {
          return false;
        }
      });

    }
  }
}
</script>
<style>
.wrapper{
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC4668 , #3F5EFB);
  overflow: hidden;
}
</style>