/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  if (str === '' || str === undefined || str == null) {
    return false
  } else {
    const flag = str.startsWith('S') || str.startsWith('T') || str === 'admin'
    return flag
  }
}
