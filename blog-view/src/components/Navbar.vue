<template>
  <div class="blog-navbar" :class="{'transparent':$route.name==='Home' }">

    <router-link style="text-decoration-line: none;color: white" to="/">
      <h3 :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}"
          class="ui header item m-blue" style="display: inline;padding:20px 20px 20px 60px">yuan's
        home</h3>
    </router-link>

    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Index'}" class="item" style="text-decoration-line: none;color: white;padding:20px;"
                 to="/">
      首页
    </router-link>


    <el-dropdown trigger="click" @mousedown.native="getTypes">

				<span :class="{'m-mobile-show': mobileHide,'active':$route.name==='Category'}" class="el-dropdown-link item"
              style="text-decoration-line: none;color: white; font-size: 16px;padding:20px">
					分类<i class="el-icon-arrow-down el-icon--right"></i>
				</span>

      <el-dropdown-menu slot="dropdown" style="background-color: #333333;padding: 20px">
        <el-dropdown-item v-for="(type,index) in types" :key="index" @click.native="categoryRoute(type.typeName)" style="color: white">
          {{ type.typeName }}
        </el-dropdown-item>
      </el-dropdown-menu>

    </el-dropdown>


    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Archives'}" class="item" style="text-decoration-line: none;color: white;padding:20px"
                 to="/archives">
      归档
    </router-link>

    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Friends'}" class="item" style="text-decoration-line: none;color: white;padding:20px"
                 to="/friends">
      友链
    </router-link>
    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}" class="item" style="text-decoration-line: none;color: white;padding: 20px"
                 to="/about">
      关于我
    </router-link>

    <!--自带防抖-->
    <el-autocomplete  v-model="queryString" :class="{'m-mobile-hide': mobileHide}" :fetch-suggestions="debounceQuery"
                     class="right item m-search"
                     placeholder="请输入内容" popper-class="m-search-item" style="margin-left: 500px; "
                     suffix-icon="el-icon-search"  @select="handleSelect">
      <i slot="suffix" class="search icon el-input__icon"></i>
      <template slot-scope="{ item }">
        <div class="title">{{ item.title }}</div>
      </template>
    </el-autocomplete>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      types: {},
      input: '',
      queryString: '',
      queryResult: [],
      total: 0,
      timer: null,
      mobileHide: false,
      ifShowInput: false
    };
  },

  methods: {
    debounceQuery(queryString, callback) {
      this.timer && clearTimeout(this.timer)
      this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
    },
    getIsPhone() {

      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },
    querySearchAsync(queryString, callback) {
      if (queryString == null
          || queryString.trim() === ''
          || queryString.indexOf('%') !== -1
          || queryString.indexOf('_') !== -1
          || queryString.indexOf('[') !== -1
          || queryString.indexOf('#') !== -1
          || queryString.indexOf('*') !== -1
          || queryString.trim().length > 20) {
        return
      }
      const _this = this
      this.$axios.get('/search?queryString=' + queryString).then((res) => {
        _this.queryResult = res.data.data.records
        _this.total = res.data.data.total
        if (_this.queryResult.length === 0) {
          _this.queryResult.push({title: '无相关搜索结果'})
        }
        _this.$message({
          message: "总共搜索到"+_this.total+"条记录",
          type: 'success'
        })
        callback(_this.queryResult)
      }).catch(() => {
        _this.$message.error("请求失败")
      })
    },
    categoryRoute(name) {
      this.$router.push(`/category/${name}`)
    },
    handleSelect(item) {
      if (item.blogId) {
        this.$router.push(`/blog/${item.blogId}`)
      }
    },
getTypes() {
      const _this = this
      this.$axios.get('/types').then((res) => {
        _this.types = res.data.data;
      }).catch(error => {
        console.error('获取分类失败:', error)
        // 设置默认空对象，避免页面报错
        _this.types = {}
      })
    },
  },
  mounted() {
    if (this.getIsPhone()) {
      this.mobileHide = true
    }
  },
}
</script>

<style scoped>
/deep/ .el-popper .popper__arrow::after {
  content: none !important;
}

/deep/ .popper__arrow {
  display: none !important;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 10px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
  background: rgba(27, 28, 29, 0.95) !important;
  backdrop-filter: blur(10px);
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
  animation: dropdownSlideIn 0.3s ease;
}

@keyframes dropdownSlideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.el-dropdown-menu__item {
  padding: 12px 20px !important;
  transition: all 0.3s ease;
}

.el-dropdown-menu__item:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

.blog-navbar {
  height: 70px;
  padding: 0 40px;
  background: rgba(51, 51, 51, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.blog-navbar.transparent {
  background: rgba(51, 51, 51, 0.6);
  backdrop-filter: blur(10px);
}

.blog-navbar.scrolled {
  background: rgba(51, 51, 51, 0.95);
  box-shadow: 0 8px 40px rgba(0, 0, 0, 0.2);
}

.blog-navbar h3 {
  font-size: 22px;
  font-weight: 800;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  transition: all 0.3s ease;
  position: relative;
}

.blog-navbar h3::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 0;
  height: 3px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  transition: width 0.3s ease;
}

.blog-navbar h3:hover::after {
  width: 100%;
}

.blog-navbar .item {
  color: rgba(255, 255, 255, 0.9) !important;
  font-size: 15px;
  font-weight: 500;
  padding: 8px 16px !important;
  margin: 0 4px;
  border-radius: 8px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
}

.blog-navbar .item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 3px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 3px;
  transition: width 0.3s ease;
}

.blog-navbar .item:hover::before {
  width: 80%;
}

.blog-navbar .item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #ffffff !important;
  transform: translateY(-2px);
}

.blog-navbar .item.active {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.3) 0%, rgba(118, 75, 162, 0.3) 100%);
  color: #ffffff !important;
}

.blog-navbar .item.active::before {
  width: 80%;
}

.m-search {
  min-width: 240px;
  padding: 0 !important;
  margin-left: auto !important;
}

.m-search /deep/ .el-input__inner {
  color: rgba(255, 255, 255, 0.9) !important;
  border: 1px solid rgba(255, 255, 255, 0.2) !important;
  background: rgba(255, 255, 255, 0.1) !important;
  border-radius: 25px !important;
  padding: 10px 40px 10px 20px !important;
  font-size: 14px;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.m-search /deep/ .el-input__inner:focus {
  background: rgba(255, 255, 255, 0.15) !important;
  border-color: rgba(102, 126, 234, 0.5) !important;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2) !important;
}

.m-search /deep/ .el-input__icon {
  color: rgba(255, 255, 255, 0.7) !important;
  transition: all 0.3s ease;
}

.m-search /deep/ .el-input__icon:hover {
  color: rgba(255, 255, 255, 1) !important;
  transform: scale(1.1);
}

.m-search-item {
  min-width: 400px !important;
  border-radius: 12px !important;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2) !important;
}

.m-search-item li {
  line-height: normal !important;
  padding: 12px 20px !important;
  transition: all 0.3s ease;
}

.m-search-item li:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%) !important;
}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: #2c3e50;
  font-weight: 600;
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: #8a99a8;
}

.m-mobile-hide {
  display: none !important;
}

.m-mobile-show {
  padding: 8px 10px !important;
  margin: 0 2px !important;
  font-size: 14px !important;
}

/deep/ .el-autocomplete {
  position: relative;
  right: auto;
}

@media (max-width: 768px) {
  .blog-navbar {
    padding: 0 15px;
    height: 60px;
  }
  
  .blog-navbar h3 {
    font-size: 18px;
  }
  
  .m-search {
    min-width: 150px;
  }
}
</style>

