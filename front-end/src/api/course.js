import request from '@/utils/request'
export function getTeachCourses(token) {
  return request({
    url: '/api/teacher/getCourses',
    method: 'get',
    params: { 'token': token }
  })
}
export function getCoursesInfo(token) {
  return request({
    url: '/api/getCourses',
    method: 'get',
    params: { 'token': token }
  })
}

export function getCoursesInfoFuzzy(token, name) {
  return request({
    url: '/api/getCoursesFuzzy',
    method: 'get',
    params: {
      'token': token,
      'courseName': name
    }
  })
}

export function selectCourse(token, id) {
  return request({
    url: '/api/student/selectCourse',
    method: 'post',
    params: {
      'token': token,
      'courseId': id
    }
  })
}

export function dropCourse(token, id) {
  return request({
    url: '/api/student/dropCourse',
    method: 'post',
    params: {
      'token': token,
      'courseId': id
    }
  })
}

export function getSelectedCourses(token) {
  return request({
    url: '/api/student/getSelectedCourse',
    method: 'get',
    params: {
      'token': token
    }
  })
}

export function submitApplication(token, courseId, content) {
  return request({
    url: '/api/student/apply',
    method: 'post',
    params: {
      'token': token,
      'courseId': courseId,
      'content': content
    }
  })
}

export function getApplicationInfo(token) {
  return request({
    url: '/api/student/getApplicationInfo',
    method: 'get',
    params: {
      'token': token
    }
  })
}

export function dropApplication(token, id) {
  return request({
    url: '/api/student/dropApplication',
    method: 'post',
    params: {
      'token': token,
      'courseId': id
    }
  })
}
export function getApplicationInfoByTea(token) {
  return request({
    url: '/api/teacher/getApplications',
    method: 'get',
    params: {
      'token': token
    }
  })
}

export function passApplication(token, id) {
  return request({
    url: '/api/teacher/passApplication',
    method: 'post',
    params: {
      'token': token,
      'appId': id
    }
  })
}

export function getDynamicTable(token) {
  return request({
    url: '/api/student/getDynamicTable',
    method: 'get',
    params: {
      'token': token
    }
  })
}
