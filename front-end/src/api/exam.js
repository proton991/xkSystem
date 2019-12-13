import request from '@/utils/request'

export function getExamInfo(token) {
  return request({
    url: '/api/student/getExamInfo',
    method: 'get',
    params: {
      'token': token
    }
  })
}

export function getExamInfoByTeacher(token) {
  return request({
    url: '/api/teacher/getExamInfo',
    method: 'get',
    params: {
      'token': token
    }
  })
}

export function importResult(file) {
  return request({
    url: '/api/teacher/importResult',
    method: 'post',
    data: file
  })
}

export function getExamResult(token) {
  return request({
    url: '/api/student/getExamResult',
    method: 'get',
    params: {
      'token': token
    }
  })
}
