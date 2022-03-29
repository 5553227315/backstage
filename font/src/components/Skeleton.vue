<template>
<!--  骨架屏-->
  <div>
    <template>
      <el-skeleton style="width: 100%;position:absolute;z-index: 1;margin-top: -55px;background-color: white " :loading="loading" animated :count="1">
        <template slot="template">
          <div style="display: flex;flex-direction: row; ">
            <el-skeleton-item variant="text" style="height: 20px; width: 90px; margin: 5px 0 5px 8px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 80px; margin: 5px 0 5px 8px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 30px; margin: 5px 0 5px 23px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 85px; margin: 5px 0 5px 10px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 70px; margin: 5px 0 5px 16px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 280px; margin: 5px 0 5px 32px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 150px; margin: 5px 0 5px 20px"/>
          </div>
        </template>
      </el-skeleton>
    </template>
    <template>
      <el-skeleton style="width: 100%;position:absolute;z-index: 1;margin-top: -30px;background-color: white " :loading="loading" animated :count="4">
        <template slot="template">
          <div style="display: flex;flex-direction: row; ">
            <el-skeleton-item variant="text" style="height: 20px; width: 90px; margin-left: 8px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 80px; margin-left: 8px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 30px; margin-left: 23px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 85px; margin-left: 10px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 70px; margin-left: 16px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 280px; margin-left: 32px; margin-top: 20px"/>
            <el-skeleton-item variant="text" style="height: 20px; width: 150px; margin-left: 20px; margin-top: 20px"/>
          </div>
        </template>
      </el-skeleton>
    </template>
  </div>

</template>

<script>
export default {
  name: "Skeleton",
  props: {
    loading: Boolean
  }
}
</script>

<style scoped>

</style>