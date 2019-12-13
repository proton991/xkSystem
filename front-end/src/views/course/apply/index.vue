<template>
  <el-container>
    <el-header style="text-align: left; font-size: 12px">
      <el-button
        icon="el-icon-edit-outline"
        type="primary"
        style="margin-top: 20px"
        @click="dialogFormVisible = true"
      >创建新的选课申请</el-button>
      <el-dialog title="选课申请" :visible.sync="dialogFormVisible">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="课程ID" size="medium" style="width: 300px">
            <el-input v-model="form.courseId" />
          </el-form-item>
          <el-form-item label="申请理由">
            <el-input v-model="form.content" type="textarea" :rows="10" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="danger" icon="el-icon-delete" @click="clearInput">重置</el-button>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" icon="el-icon-s-promotion" @click="onSubmit">提交申请</el-button>
        </div>
      </el-dialog>
    </el-header>
    <el-main>
      <el-divider content-position="center"><span style="color:green;">已提交申请</span></el-divider>
      <el-table
        stripe="true"
        border="true"
        height="300"
        width="600"
        :data="tableData"
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
            <el-button type="primary" size="small" v-if="scope.row.passed === 'false'" @click="dropApplication(scope.row)">撤回申请</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import { submitApplication, getApplicationInfo, dropApplication } from '@/api/course'

export default {
  data() {
    return {
      tableData: [],
      colConfigs: [
        { prop: 'courseId', label: '课程ID' },
        { prop: 'courseName', label: '课程名称' },
        { prop: 'passed', label: '是否通过' },
        { prop: 'content', label: '申请原因' }
      ],
      formatDate: '',
      timeStmp: '',
      dialogFormVisible: false,
      form: {
        courseId: '',
        content: ''
      },
      formLabelWidth: '60px'
    }
  },
  mounted() {
    getApplicationInfo(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.tableData = data.appInfo
      }
    })
  },
  methods: {
    getTime() {
      var time = new Date()
      this.timeStmp = time.getTime()
      console.log(this.timeStmp)
      this.$notify({
        type: 'success',
        message: this.timeStmp,
        title: '时间'
      })
    },
    onSubmit() {
      console.log('submit!')
      submitApplication(this.$store.getters.token, this.form.courseId, this.form.content).then(response => {
        if (response && response.code === 200) {
          this.dialogFormVisible = false
          this.$message({
            type: 'success',
            message: '选课申请提交成功'
          })
          getApplicationInfo(this.$store.getters.token).then(response => {
            if (response && response.code === 200) {
              const { data } = response
              this.tableData = data.appInfo
            }
          })
        }
        if (response && response.code === 500209) {
          this.$message({
            type: 'error',
            message: response.message
          })
        }
      })
    },
    clearInput() {
      this.form.courseId = ''
      this.form.content = ''
    },
    dropApplication(row) {
      console.log(row)
      this.$confirm('是否撤销申请?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        dropApplication(this.$store.getters.token, row.courseId).then(response => {
          if (response && response.code === 200) {
            this.$message({
              type: 'success',
              message: '撤销申请成功!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消撤销'
        })
      })
      getApplicationInfo(this.$store.getters.token).then(response => {
        if (response && response.code === 200) {
          const { data } = response
          this.tableData = data.appInfo
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
