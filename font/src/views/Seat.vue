<template>
  <div style="display:flex;flex-direction: row">
    <div style="display:flex;flex: 1;flex-direction: column;align-items: center;">
      <div style="margin: 20px;font-size: 40px;font-weight: 600;" :style="{color: color}">{{cinemaName}}-{{hallNumber}}-{{cinemaType}}</div>
      <div style="margin-top: 20px">
        <el-button v-show="isReopen"
                   type="warning"
                   plain style="margin-top: 200px;
                   width: 200px;height: 60px;
                   font-size: 25px;
                   border-radius: 15px" @click="reopen">初始化座椅</el-button>
        <div style="display: flex;flex-direction: row;">
          <div style="width: 40px;margin: 8px"></div>
          <div v-for="X in XLenght" style="width: 40px;margin: 8px;display: flex; justify-content: center">{{X}}</div>
        </div>
        <div v-for="(seatList,index) in seatData" :key="index"
             ref="back_box" style="display: flex;flex-direction: row;">

          <div style="width: 40px; margin: 8px;display: flex;flex-direction: row-reverse;align-items: center">{{index+1}}</div>
          <div @mouseover="seatSelect(seat)"
               v-for="seat in seatList"
               style="margin: 8px;
             width: 40px;height: 40px;
             border-radius: 5px;
             border: 3px solid;"
               :style="{
               borderColor: seat.seatColor,
               backgroundColor:seat.seatState===3?'rgb(224, 224, 224)':'#fff'
             }"></div>
<!--          {{seat.seatX}},{{seat.seatY}},{{seat.hallNumber}}-->
        </div>
      </div>
    </div>

    <div style="display: flex;flex-direction: column;border-left: 1px solid #a7a7a7; padding-left: 20px">
      <span class="m-10" style="color:#555;">按住Ctrl键滑动鼠标即可选择</span>
      <transition name="domActive">
        <div v-if="seatState===0"  style="margin:0 10px;padding: 15px 0; display: flex;flex-direction: column;align-items: center;justify-content: center;border: 1px solid #9d9d9d;border-radius: 10px">
          <div style="display: flex;flex-direction: row;align-items: center;width: 180px;margin-bottom: 15px;padding-left: 15px;border: 1px solid #d0d0d0;border-radius: 5px">
            <span style="font-size: 13px;color: #666;margin-right: 15px;flex: 1">选择座椅颜色</span>
            <el-color-picker
                v-model="color"
                show-alpha
                @change="state0"
                :predefine="predefineColors">
            </el-color-picker>
          </div>
          <el-input
              v-model="seatType"
              style="width: 180px"
              aria-required="true"
              placeholder="请输入座椅类型"></el-input>
        </div>
      </transition>
      <el-button class="m-10" :style="{backgroundColor:seatState===0?'#409eff':'',color:seatState===0?'#fff':''}" size="medium" @click="state0">选择可用</el-button>
      <el-button class="m-10" size="medium" :style="{backgroundColor:seatState===3?'#409eff':'',color:seatState===3?'#fff':''}" @click="isBan">选择禁用</el-button>
      <el-button class="m-10" size="medium" :style="{backgroundColor:seatState===2?'#409eff':'',color:seatState===2?'#fff':''}" @click="isEmpty">选择空位</el-button>

      <div class="m-10" style="width: 200px;display: flex;flex-direction: column;
         justify-content: center;align-items: center;border: 1px solid #666;border-radius: 10px">
<!--        <span style="width: 100%;display:flex;justify-content: center;color: #666;font-size: 10px">添减座椅</span>-->
        <div style="display:flex;
            width: 100%;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 9px;border-bottom: 1px solid #666;border-radius: 10px">
          <el-button style="width: 100%;" type="success" size="medium" @click="addTop">TOP+</el-button>
          <el-button style="width: 100%;margin-top: 5px;margin-left: 0" type="danger" size="medium" @click="reTop">TOP-</el-button>
        </div>

        <div style="width: 100%; display:flex;flex-direction: row;justify-content: center;margin: 10px 0; border-top: 1px solid #666;
        border-bottom: 1px solid #666;border-radius: 10px">
          <div style="flex: 1;display: flex;flex-direction: column;padding: 5px 5px 5px 9px;">
            <el-button style="width: 100%;" type="success" size="medium" @click="addLeft">L+</el-button>
            <el-button style="width: 100%;margin-top: 5px;margin-left: 0" type="danger" size="medium" @click="reLeft">L-</el-button>
          </div>
          <div style="flex: 1;display: flex;flex-direction: column;padding: 5px 9px 5px 5px;">
            <el-button style="width: 100%;" type="success" size="medium" @click="addRight">R+</el-button>
            <el-button style="width: 100%;margin-top: 5px;margin-left: 0" type="danger" size="medium" @click="reRight">R-</el-button>
          </div>
        </div>

        <div style="display:flex;
            width: 100%;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 9px;border-top: 1px solid #666;border-radius: 10px">
          <el-button style="width: 100%;" type="success" size="medium" @click="addBottom">BOTTOM+</el-button>
          <el-button style="width: 100%;margin-left: 0;margin-top: 5px;" type="danger" size="medium" @click="reBottom">BOTTOM-</el-button>
        </div>
      </div>


      <el-button class="m-10" style="width: 200px;" type="success" size="medium" @click="saveSeat">上传保存</el-button>
      <el-button class="m-10" style="width: 200px;" type="warning" size="medium" @click="reSeat">恢复</el-button>
      <el-button class="m-10" style="width: 200px;" type="warning" size="medium" @click="reEmpty">清空</el-button>
      <el-button class="m-10" style="width: 200px" type="info" size="medium" @click="back">返回</el-button>
    </div>
  </div>

</template>

<script>
export default {
  name: "Seat",
  data() {
    return {
      hallId: "",
      isReopen:"",
      XLenght:0,
      YLenght:0,
      seatData: [],
      total: 0,
      seatColor:'#d2d2d2',
      seatState:2,
      seatXNumber:0,
      seatYNumber:0,
      seatType:'',
      color: 'rgba(170, 138, 126, 1)',
      predefineColors: [
        'rgba(170, 138, 126, 1)',
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ]
    }
  },
  created() {
    //请求分页查询数据
    this.hallId = this.$route.query.hallId
    this.cinemaName = this.$route.query.cinemaName
    this.hallNumber = this.$route.query.hallNumber
    this.cinemaType = this.$route.query.cinemaType
    this.seatType = this.cinemaType.substring(0,this.cinemaType.length-1)+'座'
    console.log(this.hallId)
    this.load()
  },
  methods: {
    seatSelect(seat){
      let e=window.event
      if (e.ctrlKey){
        console.log("ctrlKey:"+e.ctrlKey);
        seat.seatColor = this.seatColor
        seat.seatState=this.seatState
        seat.seatType=this.seatType
        console.log(seat)
      }
    },
    state0(){
      this.seatColor=this.color
      this.seatState=0
    },
    isBan(){
      this.seatColor='#d2d2d2'
      this.seatState=3

    },
    isEmpty(){
      this.seatColor='#d2d2d2'
      this.seatState=2
    },
    addTop(){
      let Y = this.seatData[0][0].seatY+1
      let X = this.seatData[0][0].seatX
      let length = this.seatData[0].length
      let topList=[]

      for (let i=0;i<length;i++){
        let seat={
          hallId: this.hallId,
          hallNumber: this.hallNumber,
          seatX: X,
          seatY: Y,
          seatColor: '#d2d2d2',
          seatState: 2
        }
        X=X+1
        topList.push(seat)
      }
      if (this.seatData.length<15){
        this.seatData.unshift(topList)
      }else{
        this.$message.error("最多不超过15行")
      }
    },
    reTop(){
      if (this.seatData.length>1){
        this.seatData.shift()
      }else {
          this.$message.error("最少保留1行")
      }
    },
    addBottom(){
      let datalen = this.seatData.length-1
      let Y = this.seatData[datalen][0].seatY-1
      let X = this.seatData[datalen][0].seatX
      let topList=[]
      let length = this.seatData[0].length
      for (let i=0;i<length;i++){
        let seat={
          hallId: this.hallId,
          hallNumber: this.hallNumber,
          seatX: X,
          seatY: Y,
          seatColor: '#d2d2d2',
          seatState: 2
        }
        X=X+1
        topList.push(seat)
      }
      if (this.seatData.length<15){
        this.seatData.push(topList)
      }else{
        this.$message.error("最多不超过15行")
      }
    },
    reBottom(){
      if (this.seatData.length>1){
        this.seatData.pop()
      }else {
        this.$message.error("最少保留一行")
      }
    },
    addLeft(){
      let Y = this.seatData[0][0].seatY
      let X = this.seatData[0][0].seatX-1
      if(this.seatData[0].length<24){
        this.XLenght=this.XLenght+1
        this.seatData.forEach(e=>{
          let seat={
            hallId: this.hallId,
            hallNumber: this.hallNumber,
            seatX: X,
            seatY: Y,
            seatColor: '#d2d2d2',
            seatState: 2
          }
          Y=Y-1
          e.unshift(seat)
        })
      }else {
        this.$message.error("最多不超过24列")
      }
    },
    reLeft(){
      if(this.seatData[0].length>1){
        this.XLenght=this.XLenght-1
        this.seatData.forEach(e=>{
          e.shift()
        })
      }else {
        this.$message.error("最少保留1列")
      }

    },
    addRight(){
      let lenX =this.seatData[0].length-1
      let Y = this.seatData[0][lenX].seatY
      let X = this.seatData[0][lenX].seatX+1
      if(this.seatData[0].length<24){
        this.XLenght=this.XLenght+1
        this.seatData.forEach(e=>{
          let seat={
            hallId: this.hallId,
            hallNumber: this.hallNumber,
            seatX: X,
            seatY: Y,
            seatColor: '#d2d2d2',
            seatState: 2
          }
          Y=Y-1
          e.push(seat)
        })
      }else {
        this.$message.error("最多不超过24列")
      }
    },
    reRight(){
      if(this.seatData[0].length>1){
        this.XLenght=this.XLenght-1
        this.seatData.forEach(e=>{
          e.pop()
        })
      }else {
        this.$message.error("最少保留一列")
      }
    },
    back(){
      this.$router.push({path:'/hall'})
    },
    reopen(){
      this.request("/seat/setSeat", {
        params: {
          hallId: this.hallId,
          hallNumber:this.hallNumber
        }
      }).then(res=>{
        if (res.code==="200"){
          this.$message.success(res.msg)
          this.load()
        }else {
          this.$message.error(res.msg)
        }
      })
    },
    load() {
      this.request("/seat/hallId", {
        params: {
          hallId: this.hallId,
        }
      }).then(res => {
        console.log(res)
        if (res.code==="404"){
          this.$message.error(res.msg)
          this.isReopen = true
        }else {
          this.isReopen = false
          res.data.forEach(e=>{
            this.YLenght = this.YLenght+1
            e.forEach(j=>{
              j.backgroundColor='#fff'
              this.XLenght = this.XLenght+1
            })
          })
          this.XLenght=this.XLenght/this.YLenght
          this.seatData = res.data
          this.total = res.data.length
        }
      })
    },
    reSeat(){
      this.seatType = this.cinemaType.substring(0,this.cinemaType.length-1)+'座'
      this.YLenght = 0
      this.XLenght = 0
      this.load()
    },
    reEmpty(){
      this.seatData.forEach(e=>{
        e.forEach(j=>{
          j.seatColor='#d2d2d2'
          j.seatState=2
          j.seatType=''
        })
      })
    },
    saveSeat(){
      let saveData = []
          this.seatData.forEach(e=>{
        e.forEach(j=>{
          saveData.push(j)
        })
      })
      console.log(saveData)
      this.request.post("/seat",saveData).then(res =>{
        if (res.code==="200"){
          console.log(res)
          this.$message.success(res.msg)
          this.load()
        } else {
          console.log(res)
          this.$message.error(res.msg)
          this.load()
        }

      })
    }
  }
}
</script>

<style scoped>

</style>