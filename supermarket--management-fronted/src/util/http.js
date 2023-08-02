import axios from 'axios'

const http=axios.create({
    baseURL:'/api',
    timeout:10000,
    headers:{
        'Content-Type':'application/json'
    }
})

http.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    // 判断是否存在token,如果存在将每个页面header添加token
   
    if (localStorage.getItem("token")) {
      config.headers.common['token'] = window.localStorage.getItem("token");
     
    }
    return config
  }, function (error) {//当前端有错误的时候，几乎不存在
    router.push('/login')
    return Promise.reject(error)
  })

export default http