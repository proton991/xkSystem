import request from '@/utils/request'
export function uploadFile(file) {
  return request({
    url: '/admin/import',
    method: 'post',
    data: file
  })
}

export function checkConflict(token) {
  return request({
    url: '/admin/checkConflict',
    method: 'get',
    params: { 'token': token }
  })
}

