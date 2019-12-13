<template>
  <el-table
    :loading = "tableLoading"
    stripe="true"
    border="true"
    height="800"
    :data="courseTable"
  >
    <el-table-column
      v-for="{ prop, label } in colConfigs"
      :key="prop"
      :prop="prop"
      :label="label"
    />
  </el-table>
</template>

<script>
import { getCoursesInfo } from '../../../api/course'

export default {

  data() {
    this.colConfigs = [
      { prop: 'courseId', label: '课程ID' },
      { prop: 'courseName', label: '课程名称' },
      { prop: 'selectedQuantity', label: '已选人数' },
      { prop: 'stockQuantity', label: '人数上限' },
      { prop: 'time', label: '上课时间' },
      { prop: 'classroom', label: '教室' },
      { prop: 'teacher', label: '教师' }
    ]
    return {
      tableLoading: false,
      courseTable: []
    }
  },
  mounted: function() {
    this.initData()
  },
  methods: {
    initData() {
      this.tableLoading = true
      getCoursesInfo(this.$store.getters.token).then(response => {
        if (response && response.code === 200) {
          this.tableLoading = false
          const { data } = response
          this.courseTable = data.courseInfo
        } else {
          this.tableLoading = false
          this.$message({
            type: 'error',
            message: 'something bad happened...'
          })
        }
      }).catch(() => {
        this.tableLoading = false
      })
      // this.$store.dispatch('course/getCoursesInfo').then(() => {
      //   this.courseTable = this.$store.getters.courseInfo
      //   this.tableLoading = false
      // }).catch(() => {
      //   this.tableLoading = false
      // })
    }
  }

}
</script>
