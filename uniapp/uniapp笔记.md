# Vue基础

# MVVM在Vue中的通俗理解

### MVVM模型

- 模型(Model):data中的数据
- 视图(view):模板代码
- 视图模型( ViewModel):Vue实例

### 观察发现

​	data中所有的属性,最后都出现在了vm身上。
​	vm身上所有的属性及ue原型上所有属性,在vue模板中都可以直接使用

# uniapp基础

##### easycom

传统vue组件，需要安装、引用、注册，三个步骤后才能使用组件。`easycom`将其精简为一步。 只要组件安装在项目的components目录下，并符合`components/组件名称/组件名称.vue`目录结构。



##### 父组件向子组件传递数据可以通过 props

```js
props: {
    color: {
        type: String,
            default: 'white'
    },
        size: {
            type: String,
                default: '14px'
        }
},
```



##### 子组件向父组件传递数据可以通过自定义事件，子组件触发父组件定义的事件，并传递数据

- 子组件自定义事件`myClick`，可在后面**加数据**传递给父组件，供父组件使用

```
handleClick() {
    console.log('点击了自定义组件')
    this.$emit('myClick')
}
```

##### 子组件可以定义插槽slot,让父组件自定义要显示的内容

 

##### 条件编译

条件编译是用特殊的注释作为标记，在编译时根据这些特殊的注释，将注释里面的代码编译到不同平台

**写法：**以 #ifdef 或 #ifndef 加 **%PLATFORM%** 开头，以 #endif 结尾。

- \#ifdef：if defined 仅在某平台存在
- \#ifndef：if not defined 除了某平台均存在
- **%PLATFORM%**：平台名称

可以控制不同平台下的组件，或者控制不同平台下的js逻辑



##### rpx

根据不同的分辨率进行换算：750宽度下 2rpx = 1px



# uView笔记

## Http请求

配置`common/http.interceptor.js`

需要注意的配置：

- **baseUrl**：在这里配置统一的域名
- **originalData**：true，包含data、message、statusCode
- 请求拦截，配置Token等参数
- 响应拦截，判断状态码是否通过，被拦截后的请求后的逻辑不会执行



```
// /common/http.interceptor.js

// 这里的vm，就是我们在vue文件里面的this，所以我们能在这里获取vuex的变量，比如存放在里面的token变量
const install = (Vue, vm) => {
	// 此为自定义配置参数，具体参数见上方说明
	Vue.prototype.$u.http.setConfig( {
	baseUrl: 'https://api.shop.eduwork.cn', // 请求的本域名
	// method: 'POST',
	// 设置为json，返回后会对数据进行一次JSON.parse()
	dataType: 'json',
	showLoading: true, // 是否显示请求中的loading
	loadingText: '请求中...', // 请求loading中的文字提示
	loadingTime: 800, // 在此时间内，请求还没回来的话，就显示加载中动画，单位ms
	originalData: true, // 是否在拦截器中返回服务端的原始数据
	loadingMask: true, // 展示loading的时候，是否给一个透明的蒙层，防止触摸穿透
	// 配置请求头信息
	header: {
		'content-type': 'application/json;charset=UTF-8'
	}
	});
	
	// 请求拦截，配置Token等参数
	Vue.prototype.$u.http.interceptor.request = (config) => {
		// 引用token
		// 方式一，存放在vuex的token，假设使用了uView封装的vuex方式
		// 见：https://uviewui.com/components/globalVariable.html
		// config.header.token = vm.token;
		config.header.authorization = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYXBpLnNob3AuZWR1d29yay5jblwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYzNjI0OTQ3OCwiZXhwIjoxNjM2NjA5NDc4LCJuYmYiOjE2MzYyNDk0NzgsImp0aSI6IlE1c2o0bENpdTFWclhSelciLCJzdWIiOjIsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.KCxxbuzowkAOqZ5D04N5ApT0SyDrfLMovGyj2CPoeZ0"
		// 方式二，如果没有使用uView封装的vuex方法，那么需要使用$store.state获取
		// config.header.token = vm.$store.state.token;
		
		// 方式三，如果token放在了globalData，通过getApp().globalData获取
		// config.header.token = getApp().globalData.username;
		
		// 方式四，如果token放在了Storage本地存储中，拦截是每次请求都执行的
		// 所以哪怕您重新登录修改了Storage，下一次的请求将会是最新值
		// const token = uni.getStorageSync('token');
		// config.header.token = token;
		config.header.Token = 'xxxxxx';
		
		// 可以对某个url进行特别处理，此url参数为this.$u.get(url)中的url值
		// if(config.url == '/user/login') config.header.noToken = true;
		// 最后需要将config进行return
		return config;
		// 如果return一个false值，则会取消本次请求
		// if(config.url == '/user/rest') return false; // 取消某次请求
	}
	
	// 响应拦截，判断状态码是否通过
	Vue.prototype.$u.http.interceptor.response = (res) => {
		const {statusCode, data} = res
		if(statusCode < 400) {
			// res为服务端返回值，可能有code，result等字段
			// 这里对res.result进行返回，将会在this.$u.post(url).then(res => {})的then回调中的res的到
			// 如果配置了originalData为true，请留意这里的返回值
			return data;
		}
		else if(statusCode == 400) {
			// 错误的请求
			vm.$u.toast(data.message)
			return false
		}
		else if(statusCode == 401) {
			// 假设201为token失效，这里跳转登录
			vm.$u.toast('验证失败，请重新登录');
			setTimeout(() => {
				// 此为uView的方法，详见路由相关文档
				vm.$u.route('/pages/user/login')
			}, 1500)
			return false;
		}
		else if(statusCode == 422) {
			// 验证表单未通过
			const {errors} = data
			vm.$u.toast(Object.values(errors)[0][0])
			}
			 else {
			// 如果返回false，则会调用Promise的reject回调，
			// 并将进入this.$u.post(url).then().catch(res=>{})的catch回调中，res为服务端的返回值
			return false;
		}
	}
	
	// 增加patch请求,
	vm.$u.patch = (url, params = {}, header = {}) =>{
		// 模拟patch请求
		const _params = {
			_methods: 'PATCH'
		}
		return vm.$u.post(url, _params, header)
	}
}

export default {
	install
}
```

## API统一管理

配置`common/http.api.js`

在这里可以统一定义API，供页面中复用

```js
// /common/http.api.js

// 如果没有通过拦截器配置域名的话，可以在这里写上完整的URL(加上域名部分)
let indexUrl = '/api/index';

// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	// 首页的
	let index = (params = {}) => vm.$u.get(indexUrl, params);
	
	// 认证相关的
	const authlogin = params => vm.$u.post('/api/auth/login', params)  // 登录
	
    // 用户相关的
	
	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {
		index,
		authlogin
	};
}

export default {
	install
}
```



## Vuex

[基于uView的全局变量的配置及使用](https://www.uviewui.com/guide/globalVariable.html)

全局变量配置后可以通过`this.globalVariable`使用

- 创建`store/index.js`、`store/$u.mixin.js`

- 在main.js中混入`store/$u.mixin.js`

  ```
  // main.js
  
  let vuexStore = require("@/store/$u.mixin.js");
  Vue.mixin(vuexStore);
  ```

  

- 在项目根目录的`main.js`中，引入"/store/index.js"，并放到Vue示例中

  ```
  // main.js
  
  import store from '@/store';
  
  // 将store放入Vue对象创建中
  const app = new Vue({
  	store,
  	...App
  })
  ```

### **index.js中的注意事项**

- 需要配置在缓存里永久存储的变量，建议以vuex_开头放在此处

  ```
  // 需要永久存储，且下次APP启动需要取出的，在state中的变量名
  let saveStateKeys = ['vuex_user', 'vuex_token', 'vuex_name']
  ```

- 新添加的全局变量

  ```
  const store = new Vuex.Store({
  	state: {
  		// 如果上面从本地获取的lifeData对象下有对应的属性，就赋值给state中对应的变量
  		// 加上vuex_前缀，是防止变量名冲突，也让人一目了然
  		vuex_user: lifeData.vuex_user ? lifeData.vuex_user : {name: '明月'},
  		vuex_token: lifeData.vuex_token ? lifeData.vuex_token : '',
  		// 如果vuex_version无需保存到本地永久存储，无需lifeData.vuex_version方式
  		vuex_version: '1.0.1',
  		vuex_name: lifeData.vuex_name ? lifeData.vuex_name : ''
  	},
  ```

- 示范demo

  ```
  // 测试vuex
  console.log(this.vuex_version)
  
  // 全局变量的使用，要事先在store/index.js定义这个变量，否则设置值的操作也是无效的
  this.$u.vuex('vuex_name', 'Tom')
  console.log(this.vuex_name)
  ```

  其中，可以通过`this.$u.vuex('vuex_name', 'Tom')`来setValue

  

## TabBar

图标去iconfont里去下载，文字选中颜色，通过拾色器获取到选中的颜色



## Props

在复用组件的时候，需要将这种组件封装。

子组件要显式地用 `props` 选项声明它期待获得的数据：

```
export default {
		name: "goods-card",
		props: ["goods"],
		data() {
			return {
.....................
```

静态Prop通过为子组件在父组件中的占位符添加特性的方式来达到传值的目的：

如下，引入goods-card组件，占位符为`goods`，在子组件中的props有定义，当前页面将this.goods数据传给子组件，然后子组件获取到数据后，可以当作当前页面的全局变量使用

```
<goods-card :goods = "goods"></goods-card>
```

## 关于vm和this的使用

如果写个js文件，将jis挂载到Vue上，此时js文件不是一个实例，js里想要使用其它插件或者全局变量，必须使用vm来获取到Vue实例：

比如此处使用uView的弹窗方法，在`$u`挂载到Vue上后，需要用`m.$u.toast`来调用

```js
const install = (Vue, vm) => {
	
	/**
	 * 是否登录
	 */
	const isLogin = () => {
		const token  = vm.vuex_token
		if(!token) {
			vm.$u.toast("请登录")
			setTimeout(() => {
				vm.$u.route({
					type: 'redirect',
					url: 'pages/auth/login',
				})
			}, 2000)
			return false
		}
		return true
	}
	vm.$u.utils = {
		isLogin: isLogin
	}
}
export default {
	install
}
```

而在.vue文件里使用函数时，this表示已经是一个实例，是指向vm的

## bug记录

在使用`const currentPage = getCurrentPages().pop()`方法获取当前页面路由时，开头不带`/`，而`uni.reLaunch({url: back_url})`方法，若前面没有`/`，会找不到路由



## u-form表单验证

1、要在data里封装好自定义的校验规则

```js
rules: {
    name: [
        { 
            required: true, 
            message: '请输入姓名', 
            // 可以单个或者同时写两个触发验证方式 
            trigger: ['change','blur'],
        }
    ],
        intro: [
            {
                min: 5, 
                message: '简介不能少于5个字', 
                trigger: 'change'
            }
        ]
}
```

2、在`onReady`生命周期调用组件的`setRules`方法绑定验证规则

```js
onReady() {
    this.$refs.uForm.setRules(this.rules);
}
```



## 商品详情页概述

### navigator

```
<navigator url="navigate/navigate?title=navigate" hover-class="navigator-hover">
    <button type="default">跳转到新页面</button>
</navigator>
```

`?`后面的参数，跳转的子组件可以在onLoad里通过option参数接受。

```
// navigate.vue页面接受参数
export default {
    onLoad: function (option) { //option为object类型，会序列化上个页面传递的参数
        console.log(option.id); //打印出上个页面传递的参数。
        console.log(option.name); //打印出上个页面传递的参数。
    }
}
```

比如浏览商品时，当前商品会有一个id。点进去会将这个id数据携带到子页面，子页面通过在onLoad时期将id，保持数据一致。

## throttle & debounce节流防抖

### 节流

节流的意思是，规定时间内，只触发一次。比如我们设定500ms，在这个时间内，无论点击按钮多少次，它都只会触发一次。具体场景可以是抢购时候，由于有无数人 快速点击按钮，如果每次点击都发送请求，就会给服务器造成巨大的压力，但是我们进行节流后，就会大大减少请求的次数。

### 防抖

防抖的意思是，在连续的操作中，无论进行了多长时间，只有某一次的操作后在指定的时间内没有再操作，这一次才被判定有效。具体场景可以搜索框输入关键字过程中实时 请求服务器匹配搜索结果，如果不进行处理，那么就是输入框内容一直变化，导致一直发送请求。如果进行防抖处理，结果就是当我们输入内容完成后，一定时间(比如500ms)没有再 输入内容，这时再触发请求。

所以，防止表单提交按钮被多次触发，应该使用节流。

```
uView内置的按钮组件u-button内部已做节流处理，无需外部再做处理
```

**throttle(func, wait = 500, immediate = true)**

将提交要执行的逻辑封装在func里，实现节流

## Goods区域解析

### 导航栏

后端传过来的导航数据结构如下：

```
"categories": [
        {
            "id": 1,
            "pid": 0,
            "name": "前沿",
            "level": 1,
            "status": 1,
            "children": [
                {
                    "id": 2,
                    "pid": 1,
                    "name": "区块链",
                    "level": 2,
                    "status": 1
                },
                {
                    "id": 3,
                    "pid": 1,
                    "name": "人工智能",
                    "level": 2,
                    "status": 1
                }
            ]
        },
```

categories是一个列表，每个列表里包含一级导航的名称和id，包含以children作为key的二级导航

实际在遍历时，用两个v-for，name作为展示，id作为key

```
<!-- 导航栏 -->
<scroll-view scroll-y scroll-with-animation class="u-table-vew menu-scroll-view" scroll-top="scrollTop">
	<block v-for="(item, index) in navData">
		<!-- 一级name -->
		<view class="u-line-1 navTitle">{{item.name}}</view>
		<!-- 二级name -->
		<view v-for="child in item.children" 
		:key="child.id" 
        class="u-tab-item"
        :class="[current == child.id ? 'u-tab-item-active': '']"
        data-current='child.id'
        @tap.stop="swichMenu(child.id)">
        	<text class="u-line-1">{{child.name}}</text>
		</view>
	</block>
</scroll-view>
```



### 商品区域

使用navigator，点击跳转到上商品详情页面

### 需要注意的action

1、获取商品数据步骤

- 参数：搜索keyword、分页参数page、category_id（通过选择二级分类传递的index来确定）
- 刷新全局的goodsList
- 刷新导航数据
- 关闭showLoading

2、触发底部加载更多

- 分页参数page++
- 准备请求goodsList的参数
- 请求后更新商品数据
- 将新请求的数据添加到全局goodsList里
- 根据最新请求的goodsList的大小来确定后续有没有更多数据，控制是否显示loadMore

## 购物车

<img src="F:\学习\Learning-Notes\uniapp\uniapp笔记.assets\image-20211114164543155.png" alt="image-20211114164543155" style="zoom:150%;" />

以上单个购物车条目称为item

对于item的复选框，name绑定的的是goods的id，方便

### 复选框逻辑

```
<u-checkbox v-model="goods.is_checked?true:false" 
    :name="goods.id"
    @change="checkChange"
    size="40" 
    shape="circle">
</u-checkbox>
```

