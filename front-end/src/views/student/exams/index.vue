<template>
  <el-table
    stripe="true"
    border="true"
    height="300"
    width="600"
    :data="resultTable"
  >
    <el-table-column
      v-for="{ prop, label } in examcolConfig"
      :key="prop"
      :prop="prop"
      :label="label"
    />
  </el-table>
</template>

<script>
import { getExamResult } from '@/api/exam'

export default {
  data() {
    this.examcolConfig = [
      { prop: 'courseId', label: '课程ID' },
      { prop: 'courseName', label: '课程名称' },
      { prop: 'Score', label: '考试分数' }
    ]
    return {
      resultTable: []
    }
  },
  mounted() {
    getExamResult(this.$store.getters.token).then(response => {
      if (response && response.code === 200) {
        const { data } = response
        this.resultTable = data.examResult
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
