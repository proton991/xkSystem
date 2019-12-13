import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    name: '复旦大学选课系统',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  }

  // {
  //   path: '/student',
  //   component: Layout,
  //   redirect: '/student/schedule',
  //   name: 'Student',
  //   meta: { title: 'Student', icon: 'student' },
  //   children: [
  //     {
  //       path: 'schedule',
  //       name: 'Schedule',
  //       // component: () => import('@/views/table/index'),
  //       meta: { title: 'schedule', icon: 'table' }
  //     },
  //     {
  //       path: 'exams',
  //       name: 'Exams',
  //       component: () => import('@/views/tree/index'),
  //       meta: { title: 'Exams', icon: 'exam' }
  //     }
  //   ]
  // },
  // {
  //   path: '/teacher',
  //   component: Layout,
  //   redirect: '/teacher/schedule',
  //   name: 'Teacher',
  //   meta: { title: 'Teacher', icon: 'teacher' },
  //   children: [
  //     {
  //       path: 'schedule',
  //       name: 'Schedule',
  //       // component: () => import('@/views/table/index'),
  //       meta: { title: 'schedule', icon: 'table' }
  //     },
  //     {
  //       path: 'classMembers',
  //       name: 'ClassMembers',
  //       meta: { title: 'classMembers', icon: 'member' }
  //     },
  //     {
  //       path: 'exams',
  //       name: 'Exams',
  //       // component: () => import('@/views/tree/index'),
  //       meta: { title: 'Exams', icon: 'exam' }
  //     }
  //   ]
  // },
  // {
  //   path: '/admin',
  //   component: Layout,
  //   redirect: '/admin/upload',
  //   name: 'admin',
  //   meta: { title: 'Admin', icon: 'admin' },
  //   children: [
  //     {
  //       path: 'upload',
  //       name: 'Course',
  //       // component: () => import('@/views/table/index'),
  //       meta: { title: 'Course', icon: 'manage' }
  //     },
  //     {
  //       path: 'account',
  //       name: 'Account',
  //       meta: { title: 'Account', icon: 'manage' }
  //     }
  //   ]
  // },
  // {
  //   path: '/upload',
  //   component: Layout,
  //   redirect: '/upload/select',
  //   name: 'Course',
  //   meta: {
  //     title: 'Course',
  //     icon: 'upload'
  //   },
  //   children: [
  //     {
  //       path: 'select',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Select',
  //       meta: { title: 'Select', icon: 'select' }
  //     },
  //     {
  //       path: 'apply',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'apply', icon: 'form' }
  //     }
  //   ]
  // },
  //
  // // 404 page must be placed at the end !!!
  // { path: '*', redirect: '/404', hidden: true }
]
// 异步挂载的路由
// 动态需要根据权限加载的路由表
export const asyncRoutes = [

  {
    path: '/student',
    component: Layout,
    // redirect: '/student/schedule',
    name: 'Student',
    meta: { title: 'Student', icon: 'student', roles: ['student', 'admin'] },
    children: [
      {
        path: 'schedule',
        name: 'Schedule',
        component: () => import('@/views/student/schedule/index'),
        meta: { title: 'schedule', icon: 'table', roles: ['student', 'admin'] }
      },
      {
        path: 'exams',
        name: 'Exams',
        component: () => import('@/views/student/exams/index'),
        meta: { title: 'Exams', icon: 'exam', roles: ['student', 'admin'] }
      }
    ]
  },
  {
    path: '/teacher',
    component: Layout,
    // redirect: '/teacher/schedule',
    name: 'Teacher',
    meta: { title: 'Teacher', icon: 'teacher', roles: ['teacher', 'admin'] },
    children: [
      {
        path: 'schedule',
        name: 'Schedule',
        component: () => import('@/views/teacher/schedule/index'),
        meta: { title: 'schedule', icon: 'table', roles: ['teacher', 'admin'] }
      },
      {
        path: 'classMembers',
        name: 'ClassMembers',
        meta: { title: 'classMembers', icon: 'member', roles: ['teacher', 'admin'] }
      },
      {
        path: 'exams',
        name: 'Exams',
        component: () => import('@/views/teacher/exam/index'),
        meta: { title: 'Exams', icon: 'exam', roles: ['teacher', 'admin'] }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    // redirect: '/admin/upload',
    name: 'admin',
    meta: { title: 'Admin', icon: 'admin', roles: ['admin'] },
    children: [
      {
        path: 'courseManagement',
        name: 'CourseManagement',
        // component: () => import('@/views/upload/index'),
        meta: { title: 'courseManagement', icon: 'manage', roles: ['admin'] }
      },
      {
        path: 'accountManagement',
        name: 'AccountManagement',
        meta: { title: 'AccountManagement', icon: 'manage', roles: ['admin'] }
      },
      {
        path: 'upload',
        name: 'UploadExcel',
        component: () => import('@/views/upload/index'),
        meta: { title: 'UploadExcel', icon: 'excel', roles: ['admin'] }
      }
    ]
  },
  {
    path: '/course',
    component: Layout,
    // redirect: '/dashboard',
    name: 'Course',
    meta: {
      title: 'Course',
      icon: 'course',
      roles: ['student', 'admin']
    },
    children: [
      {
        path: 'CourseTable',
        component: () => import('@/views/course/CourseTable/index'), // Parent router-view
        name: 'CourseTable',
        meta: { title: 'CourseTable', icon: 'select', roles: ['student', 'admin'] }
      },
      {
        path: 'apply',
        component: () => import('@/views/course/apply/index'),
        meta: { title: 'apply', icon: 'form', roles: ['student', 'admin'] }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
