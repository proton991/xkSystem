<template>
  <el-tabs type="card">
    <el-tab-pane label="开设课程">
      <el-table
        :loading="tableLoading"
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
    </el-tab-pane>
    <el-tab-pane label="学生选课申请">
      <el-table
        stripe="true"
        border="true"
        height="300"
        width="600"
        :data="appTable"
      >
        <el-table-column
          v-for="{ prop, label } in colConfigs2"
          :key="prop"
          :prop="prop"
          :label="label"
        />
        <el-table-column
          fixed="right"
          label="操作"
          width="100"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="small" v-if="scope.row.passed === 'false'" @click="pass(scope.row)">通过申请</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { getTeachCourses, getApplicationInfoByTea, passApplication } from '@/api/course'

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
    this.colConfigs2 = [
      { prop: 'appId', label: '申请ID' },
      { prop: 'courseId', label: '课程ID' },
      { prop: 'courseName', label: '课程名称' },
      { prop: 'stuId', label: '申请人ID' },
      { prop: 'passed', label: '是否通过' },
      { prop: 'content', label: '申请原因' }
    ]
    return {
      tableLoading: false,
      courseTable: [],
      appTable: []
    }
  },
  mounted: function() {
    this.initData()
    getApplicationInfoByTea(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.appTable = data.appInfo
      }
    })
  },
  methods: {
    initData() {
      this.tableLoading = true
      getTeachCourses(this.$store.getters.token).then(response => {
        if (response && response.code === 200) {
          this.tableLoading = false
          const { data } = response
          this.courseTable = data.teachCourseList
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
    },
    pass(row) {
      this.$confirm('是否通过申请?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        passApplication(this.$store.getters.token, row.appId).then(response => {
          if (response && response.code === 200) {
            this.$message({
              type: 'success',
              message: '成功通过申请!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }

}
</script>
