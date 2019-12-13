import request from '@/utils/request'
import qs from 'qs'
export function login(data) {
  return request({
    url: '/api/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function getInfo(token) {
  return request({
    url: '/api/info',
    method: 'get',
    params: { 'token': token }
  })
}

export function logout(token) {
  return request({
    url: '/api/logout',
    method: 'post',
    params: { 'token': token }
  })
}
