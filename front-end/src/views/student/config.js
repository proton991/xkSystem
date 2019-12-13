const days = '一二三四五'
const times = ['第一节', '第二节', '第三节', '第四节', '第五节', '第六节', '第七节', '第八节', '第九节', '第十节', '第十一节', '第十二节', '第十三节']
// const times = ['8:00', '8:45', '8:55', '9:40', '9;55', '10:40', '10:50', '11:35', '11:45', '12:30', '13:30', '14:15', '14:25', '15:10', '15:25', '16:10', '16:20', '17:05', '17:15', '18:00', '18:30', '19:15', '19:25', '20:10', '20:20', '21:05']
const meeting = [
  // 安排的数组，开始时间和持续时间，标题
  [{
    courseName: '秘书处',
    start: 20,
    time: 4
  }],
  [
    {
      courseName: '团校',
      start: 18,
      time: 4
    }
  ],
  [
    {
      courseName: '数学',
      start: 14,
      time: 4
    },
    {
      courseName: '组织部',
      start: 8,
      time: 4
    }
  ],
  [
    {
      courseName: '信息中心',
      start: 9,
      time: 4

    },
    {
      courseName: '实践部',
      start: 2,
      time: 4
    }
  ],
  [
    {
      courseName: '文艺部',
      start: 6,
      time: 4
    }
  ]
]
export {
  days,
  times,
  meeting
}
