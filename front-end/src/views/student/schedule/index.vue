<template>
  <div>
    <div align="center">
      <table class="table table-bordered table-striped table-sm" style="height: 100px">
        <thead>
          <tr>
            <th>时间</th>
            <th v-for="i in 5" :key="i">
              {{ days[i-1] }}
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(item, index) in info" :key="index">
            <th scope="row">{{ times[index] }}</th>
            <td v-for="j in item" v-if="j" :key="j" class="mytd" :class="[j&&j!==1?'cell':'cell2']" align="center">
              {{ j?j.courseName:'' }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <br>
    <el-divider content-position="center"><span style="color:orangered;">华丽分割线</span></el-divider>
    <el-tabs type="card">
      <el-tab-pane label="选课">
        <el-input v-model="keyword" placeholder="请输入课程名称进行搜索" class="searchClass" clearable>
          <el-button slot="append" type="primary" round icon="el-icon-search" @click="searchCourse" />
        </el-input>
        <el-table
          stripe="true"
          border="true"
          height="600"
          :data="courseTable"
        >
          <el-table-column
            v-for="{ prop, label } in colConfigs"
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
              <el-button type="primary" size="small" @click="selectCourse(scope.row)">选课</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="已选课程">
        <el-table
          stripe="true"
          border="true"
          height="600"
          :data="selectedCourseTable"
        >
          <el-table-column
            v-for="{ prop, label } in colConfigs"
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
              <el-button type="primary" size="small" @click="dropCourse(scope.row)">退课</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="课程考试">
        <el-table
          stripe="true"
          border="true"
          height="600"
          :data="examTable"
        >
          <el-table-column
            v-for="{ prop, label } in examcolConfig"
            :key="prop"
            :prop="prop"
            :label="label"
          />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { days, times } from '../config.js'
import { getCoursesInfoFuzzy, selectCourse, dropCourse, getSelectedCourses, getDynamicTable } from '@/api/course'
import { getExamInfo } from '@/api/exam'
// 根据日期显示预定情况
export default {
  name: 'Week',
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
    this.examcolConfig = [
      { prop: 'examId', label: '考试编号' },
      { prop: 'courseName', label: '课程名称' },
      { prop: 'time', label: '考试时间' },
      { prop: 'type', label: '考试类型' }
    ]
    // this.dynamic = []
    return {
      dynamic: [],
      keyword: '',
      selectedCourseTable: [],
      courseTable: [],
      examTable: [],
      course: [],
      info: [],
      days: days,
      times: times
    }
  },
  computed: {
  },
  mounted() {
    getDynamicTable(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const info = []
        for (let i = 0; i < times.length; i++) {
          info[i] = []
          for (let j = 0; j < 5; j++) {
            // 1 表示空白
            info[i].push(1)
          }
        }
        const { data } = response
        console.log('动态课表')
        Object.keys(data).forEach(v => {
          Object.keys(data[v]).forEach(k => {
            console.log(data[v][k])
            info[data[v][k].start - 1][data[v][k].weekday] = data[v][k]
            for (let p = 0; p < data[v][k].time; p++) { info[data[v][k].start + p][data[v][k].weekday] = data[v][k] }
          })
          this.info = info
        })
      }
    })

    getSelectedCourses(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.selectedCourseTable = data.courseInfo
      }
    })

    getExamInfo(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.examTable = data.examInfo
      }
    })
  },
  methods: {
    searchCourse() {
      getCoursesInfoFuzzy(this.$store.getters.token, this.keyword).then(response => {
        if (response && response.code === 200) {
          const { data } = response
          this.courseTable = data.courseInfo
        }
      })
    },
    selectCourse(row) {
      console.log(row)
      this.course = row
      this.$confirm('是否选课?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        selectCourse(this.$store.getters.token, this.course.courseId).then(response => {
          console.log(response)
          if (response && response.code === 200) {
            this.$message({
              type: 'success',
              message: '选课成功!'
            })
            getSelectedCourses(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const { data } = response
                this.selectedCourseTable = data.courseInfo
              }
            })
            getExamInfo(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const { data } = response
                this.examTable = data.examInfo
              }
            })
            getDynamicTable(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const info = []
                for (let i = 0; i < times.length; i++) {
                  info[i] = []
                  for (let j = 0; j < 5; j++) {
                    // 1 表示空白
                    info[i].push(1)
                  }
                }
                const { data } = response
                console.log('动态课表')
                Object.keys(data).forEach(v => {
                  Object.keys(data[v]).forEach(k => {
                    console.log(data[v][k])
                    info[data[v][k].start - 1][data[v][k].weekday] = data[v][k]
                    for (let p = 0; p < data[v][k].time; p++) { info[data[v][k].start + p][data[v][k].weekday] = data[v][k] }
                  })
                  this.info = info
                })
              }
            })
          }
          if (response && response.code !== 200) {
            this.$message({
              type: 'error',
              message: response.message
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消选课'
        })
      })
    },
    dropCourse(row) {
      console.log(row)
      this.course = row

      this.$confirm('是否退课?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dropCourse(this.$store.getters.token, this.course.courseId).then(response => {
          if (response && response.code === 200) {
            this.$message({
              type: 'success',
              message: '退课成功!'
            })
            getSelectedCourses(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const { data } = response
                this.selectedCourseTable = data.courseInfo
              }
            })
            getExamInfo(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const { data } = response
                this.examTable = data.examInfo
              }
            })
            getDynamicTable(this.$store.getters.token).then(response => {
              if (response && response.code === 200) {
                const info = []
                for (let i = 0; i < times.length; i++) {
                  info[i] = []
                  for (let j = 0; j < 5; j++) {
                    // 1 表示空白
                    info[i].push(1)
                  }
                }
                const { data } = response
                console.log('动态课表')
                Object.keys(data).forEach(v => {
                  Object.keys(data[v]).forEach(k => {
                    console.log(data[v][k])
                    info[data[v][k].start - 1][data[v][k].weekday] = data[v][k]
                    for (let p = 0; p < data[v][k].time; p++) { info[data[v][k].start + p][data[v][k].weekday] = data[v][k] }
                  })
                  this.info = info
                })
              }
            })
          }
          if (response && response.code === 500204) {
            this.$message({
              type: 'error',
              message: '退课错误!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消退课'
        })
      })
    }
  }

}
</script>

<style scoped>
  .cell {
    /*min-width: 250px;*/
    /*min-height: 200px;*/
    /*height: 90%;*/
    /*display: flex;*/
    min-width: 220px;
    height: 40px;
    flex-direction: column;
    background: lightskyblue;
    box-sizing: content-box;
  }

  .cell2{
    min-width: 220px;
    height: 40px;
    /*height: 90%;*/
    background: greenyellow;
    box-sizing: border-box;
  }
  tbody * {
    padding: 0;
  }

  .mytd:hover{
    /*background: red;*/
    border: 2px solid blue;
  }
  .searchClass{
    align: center;
    border: 1px solid #c5c5c5;
    background: #f4f4f4;
    max-width: 400px;
    margin-left: 20px;
    margin-bottom: 20px;
  }

</style>
