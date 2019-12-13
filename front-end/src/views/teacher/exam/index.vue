<template>
  <el-table
    stripe="true"
    border="true"
    height="300"
    width="600"
    :data="examTable"
  >
    <el-table-column
      v-for="{ prop, label } in examcolConfig"
      :key="prop"
      :prop="prop"
      :label="label"
    />
    <el-table-column
      fixed="right"
      label="操作"
      width="200"
    >
      <template>
        <el-upload
          ref="upload"
          name="file"
          class="upload-demo"
          action="http://localhost:8080/api/teacher/importResult"
          :auto-upload="false"
        >
          <el-button
            style="margin-left: 10px;margin-bottom: 10px"
            slot="trigger"
            size="small"
            type="primary"
            icon="el-icon-folder-opened"
          >选取文件</el-button>
          <el-button
            style="margin-left: 10px;margin-bottom: 10px"
            size="small"
            type="success"
            icon="el-icon-upload"
            @click="submitUpload"
          >导入成绩</el-button>
        </el-upload>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getExamInfoByTeacher } from '@/api/exam'

export default {
  data() {
    this.examcolConfig = [
      { prop: 'examId', label: '考试编号' },
      { prop: 'courseName', label: '课程名称' },
      { prop: 'time', label: '考试时间' },
      { prop: 'type', label: '考试类型' }
    ]
    return {
      examTable: []
    }
  },
  mounted() {
    getExamInfoByTeacher(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.examTable = data.examInfo
      }
    })
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit()
    }
  }
}
</script>

<style scoped>

</style>
