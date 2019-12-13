<template>
  <div>
    <el-row>
      <el-col :span="10">
        <el-form ref="form" :model="form" label-width="40px">
          <el-form-item label="excel文件">
            <el-upload
              ref="upload"
              name="file"
              class="upload-demo"
              action="http://localhost:8080/admin/import"
              :on-success="onUploadSuccessfully"
              :auto-upload="false"
              :file-list="fileList"
              :multiple="true"
            >
              <el-button slot="trigger" size="small" type="primary" icon="el-icon-folder-opened">选取文件</el-button>
              <el-button
                style="margin-left: 10px;"
                size="small"
                type="success"
                @click="submitUpload"
                icon="el-icon-upload"
              >上传到服务器</el-button>
              <div slot="tip" class="el-upload__tip">请上传.xlsx格式的excel文件</div>
              <el-button
                style="margin-left: 10px"
                size="small"
                type="primary"
                icon="el-icon-search"
                @click="handleCheckConflict"
              >检查数据冲突</el-button>
            </el-upload>
          </el-form-item>
          <!--          <el-form-item>-->
          <!--            <el-button type="primary" @click="onSubmit">上传</el-button>-->
          <!--          </el-form-item>-->
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<style>
  .el-row {
    display: flex;
    justify-content: center;
    margin-top: 50px;
  }

  .el-form {
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 20px;
    background-color: cornsilk;
  }

  .upload-demo {
    padding: 10px;
    border: 1px solid #cccccc;
    border-radius: 6px;
  }
</style>

<script>
import { checkConflict } from '@/api/admin'
export default {
  data() {
    return {
      fileList: [],
      form: {
        file: ''
      }
    }
  },
  methods: {
    submitUpload() {
      // this.$refs.upload.submit()
      const formData = new FormData()
      this.fileList.forEach(file => {
        formData.append('file', file.raw)
      })
      this.$refs.upload.submit(formData)
    },
    onUploadSuccessfully(response) {
      this.file = response
    },
    handleCheckConflict() {
      checkConflict(this.$store.getters.token).then(response => {
        if (response && response.code === 200) {
          const { data } = response
          if (data.TeacherHasConflict === 'true') {
            this.$notify({
              title: '教师时间冲突！',
              message: '冲突教师' + data.ConflictTeacher,
              type: 'warning',
              duration: 0,
              position: 'bottom-right'
            })
          }
          if (data.ClassroomHasConflict === 'true') {
            this.$notify({
              title: '教室时间冲突！',
              message: '冲突教室' + data.ConflictClassroom,
              type: 'warning',
              duration: 0
            })
          }
          if (data.TeacherHasConflict === 'false' && data.ClassroomHasConflict === 'false') {
            this.$notify({
              title: '数据正确无冲突',
              type: 'success'
            })
          }
        }
      })
    }
    // onSubmit() {
    //   this.$store.dispatch('admin/uploadFile', this.form.file)
    // }
  }
}
</script>

