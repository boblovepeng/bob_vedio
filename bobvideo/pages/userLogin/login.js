const app = getApp()

Page({
  doLogin:function(e){
    var formObject=e.detail.value;
    var username=formObject.username;
    var password=formObject.password;
    if(username.length==0||password.length==0){
      wx.showToast({
           title:'用户名或者密码不能为空',
           icon:'none',
           duration:3000
      })
    }else{
      var serverUrl=app.serverUrl;
      wx.showLoading({
        title: '请等待',
      })
      wx.request({
        url: serverUrl+'/login',
        method:'POST',
        data:{
          username:username,
          password:password
        },
        header:{
           'content-type':'application/json'
        },
        success:function(res){
          console.log(res.data);
          if(res.data.status==200){
            wx.showToast({
              title: '登录成功',
              icon:'none',
              duration:3000
            });
            app.userInfo = res.data.data;
          }else if(res.data.status==500){
            wx.showToast({
              title: res.data.msg,
              icon:'none',
              duration:2000
            })
          }
        
        }
      })
    }
  },
  goRegistPage:function(){
    wx.navigateTo({
      url: '../userRegist/regist',
    })
  }
})