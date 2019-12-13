import { uploadFile } from '@/api/admin'

const state = {
  filename: ''
}

const mutations = {

}

const actions = {
  // upload File
  uploadFile({ commit }, file) {
    return new Promise((resolve, reject) => {
      uploadFile(file).then(() => {
        resolve()
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
