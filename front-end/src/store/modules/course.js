import { getCoursesInfo } from '@/api/course'
import { getCoursesInfoFuzzy } from '@/api/course'
import { getToken } from '@/utils/auth'
const state = {
  token: getToken(),
  courseInfo: []
}
const mutations = {
  SET_COURSEINFO: (state, info) => {
    state.courseInfo = info
  },
  SET_SEARCH_COURSEINFO: (state, info) => {
    state.search_course = info
  }
}
const actions = {
  getCoursesInfo({ commit, state }) {
    return new Promise((resovle, reject) => {
      getCoursesInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        commit('SET_COURSEINFO', data.courseInfo)
        resovle(data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
