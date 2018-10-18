const app = getApp()

Page({

  doRegist:function(e){
    var flag=false;
    var result=false;
    var formObject=e.detail.value;
    var username=formObject.username;
    var password=formObject.password;
    if(username.length == 0 || password.length == 0){
     wx.showToast({
       title: '用户名或密码不能为空',
       icon:'none',
       duration:3000
     })
    }else{
      flag=true;
    }
    if (username.length < 6 ||password.lengt< 6) {
      wx.showToast({
        title: '用户名或密码不能小余6位',
        icon: 'none',
        duration: 3000
      })
    }else{
      result=true;
    }
    if(flag&&result){
       var serverUrl=app.serverUrl;
       wx.request({
         url: serverUrl+'/regist',
         method:"POST",
         data:{
           username:username,
           password:password
         },
         header:{
          'content-type':'application/json'
         },
         success:function(res){
           console.log(res.data);
           var status=res.data.status;
           if(status==200){
             wx.showLoading({
               title: '请等待',
             })
             wx.showToast({
               title: '用户注册成功',
               icon: 'none',
               duration: 3000
             })
             app.userInfo=res.data.data;
           }else if(status==500){
             wx.showToast({
               title: res.data.msg,
               icon: 'none',
               duration: 3000
             })
           }
         }
       })
    }
       
  },
   goLoginPage:function(){
     wx.navigateTo({
       url: '../userLogin/login',
     })
   }
})