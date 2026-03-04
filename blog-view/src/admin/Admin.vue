<template>
  <el-container class="admin-container">
    <!--头部-->
    <el-header class="admin-header">
      <!--标题-->
      <div class="header-left">
        <div class="logo-wrapper">
          <img alt="" height="40" src="../assets/logo.png" class="logo-img">
        </div>
        <span class="site-title">博客管理</span>
        <span class="user-welcome"> {{ user.username }}</span>
      </div>
      <!--右侧操作区-->
      <div class="header-right">
        <el-link href="/" class="header-link" :underline="false">
          <i class="el-icon-home"></i> 主页
        </el-link>
        <el-divider direction="vertical"></el-divider>
        <el-dropdown class="user" trigger="click" @command="logout">
          <div class="user-dropdown-link">
            <el-avatar :size="36" :src="user.avatar" fit="contain" shape="circle"></el-avatar>
            <span class="user-name">{{ user.username }}</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-switch-button">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>

    <!--页面主体-->
    <el-container class="main-container">
      <!--侧边栏-->
      <el-aside :width="isCollapse? '64px' : '220px'" class="admin-sidebar">
        <div class="toggle-button" @click="isCollapse=!isCollapse">
          <i :class="isCollapse?'el-icon-s-unfold':'el-icon-s-fold'"></i>
        </div>
        <!--菜单-->
        <el-menu :collapse="isCollapse" :collapse-transition="false" :default-active="$store.state.activePath"
                 :default-openeds="defaultOpeneds"
                 :router="true" :unique-opened="false" active-text-color="#667eea"
                 class="admin-menu">
          <el-submenu v-for="item in menuList" :key="item.id" :index="item.id + ''">
            <template slot="title">
              <i :class="iconsObj[item.id]" class="menu-icon"></i>
              <span>{{ item.title }}</span>
            </template>
            <el-menu-item v-for="subItem in item.children" :key="subItem.id" :index="subItem.path">
              <template slot="title">
                <i :class="iconsObj[subItem.id]" class="sub-menu-icon"></i>
                <span>{{ subItem.title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--右侧内容主体-->
      <el-main :class="isCollapse?'m-el-main-width-64':'m-el-main-width-220'" class="admin-main">
        <router-view :key="$route.fullPath"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Admin",
  data() {
    return {
      menuList: [
        {
          id: 1,
          title: '博客管理',
          children: [
            {
              id: 11,
              title: '写博客',
              path: '/writeBlog'
            },
            {
              id: 12,
              title: '文章管理',
              path: '/blogList'
            },
            {
              id: 13,
              title: '分类管理',
              path: '/type'
            },
            {
              id: 14,
              title: '标签管理',
              path: '/tags'
            },
            {
              id: 15,
              title: '评论管理',
              path: '/comments'
            },
          ]
        },
        {
          id: 2,
          title: '页面管理',
          children: [
            {
              id: 21,
              title: '友链管理',
              path: '/friendList'
            },
          ]
        },
        {
          id: 3,
          title: '系统管理',
          children: [
            {
            id: 31,
            title: '用户管理',
            path: '/userManager'
            },
          ]
        },
        {
          id: 4,
          title: '日志管理',
          children: [
            {
              id: 41,
              title: '访问日志',
              path: '/visitLog'
            }
          ]
        },
        {
          id: 5,
          title: '数据统计',
          children: [
            {
              id: 51,
              title: '访客统计',
              path: '/visitor'
            },
          ]
        },
        {
          id: 6,
          title: '初始数据',
          children: [
            {
              id: 61,
              title: '同步数据',
              path: '/initesdata'
            }
          ]
        },
      ],
      iconsObj: {
        '1': 'el-icon-menu',
        '2': 'el-icon-document-copy',
        '3': 'el-icon-s-tools',
        '4': 'el-icon-document',
        '5': 'el-icon-s-data',
        '6': 'el-icon-s-cooperation',
        '11': 'el-icon-edit',
        '12': 'el-icon-s-order',
        '13': 'el-icon-s-opportunity',
        '14': 'el-icon-s-flag',
        '15': 'el-icon-s-comment',
        '21': 'el-icon-share',
        '31': 'el-icon-user-solid',
        '41': 'el-icon-data-line',
        '51': 'el-icon-s-marketing',
        '52': 'el-icon-view',
        '61': 'el-icon-set-up',
      },
      //是否折叠
      isCollapse: false,
      //默认打开的菜单
      defaultOpeneds: ['1'],
      user: {},
    }
  },
  methods: {
    //获取缓存的用户名和头像
    getUserInfo() {
      if (this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.avatar = this.$store.getters.getUser.avatar
      } else {
        this.$router.push('/login')
      }
    },
    //登出
    logout() {
      const _this = this
      this.$axios.get('/logout').then((res) => {
        _this.$store.commit('REMOVE_INFO')
        _this.$router.push('/login')
      });
    }
  },
  created() {
    this.getUserInfo()
  },
}
</script>

<style scoped>
.admin-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
  align-items: center;
  color: #ffffff;
  font-size: 18px;
  user-select: none;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  height: 60px !important;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.logo-wrapper {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 10px;
  padding: 5px 10px;
  backdrop-filter: blur(10px);
}

.logo-img {
  height: 36px;
  border-radius: 8px;
}

.site-title {
  font-weight: 700;
  font-size: 20px;
  letter-spacing: 1px;
}

.user-welcome {
  font-size: 14px;
  opacity: 0.9;
  padding: 4px 12px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 20px;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-link {
  color: #ffffff !important;
  font-size: 14px;
  padding: 8px 15px;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 5px;
}

.header-link:hover {
  background: rgba(255, 255, 255, 0.15);
}

.header-right .el-divider--vertical {
  background: rgba(255, 255, 255, 0.3);
  margin: 0 10px;
}

.user-dropdown-link {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 5px 15px;
  border-radius: 25px;
  transition: all 0.3s ease;
}

.user-dropdown-link:hover {
  background: rgba(255, 255, 255, 0.15);
}

.user-name {
  font-size: 14px;
  font-weight: 500;
}

.main-container {
  margin-top: 60px;
  height: calc(100vh - 60px);
}

.admin-sidebar {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  position: fixed;
  top: 60px;
  bottom: 0;
  left: 0;
  user-select: none;
  box-shadow: 2px 0 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  z-index: 999;
}

.admin-menu {
  border-right: none;
  background: transparent !important;
}

.admin-menu /deep/ .el-submenu__title,
.admin-menu /deep/ .el-menu-item {
  border-radius: 10px;
  margin: 5px 10px;
  transition: all 0.3s ease;
}

.admin-menu /deep/ .el-submenu__title:hover,
.admin-menu /deep/ .el-menu-item:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%) !important;
}

.admin-menu /deep/ .el-submenu__title {
  font-weight: 600;
}

.admin-menu /deep/ .el-menu-item.is-active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  color: #ffffff !important;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.menu-icon {
  font-size: 18px;
  color: #667eea;
}

.sub-menu-icon {
  font-size: 14px;
  color: #8a99a8;
}

.admin-menu /deep/ .el-menu-item.is-active .sub-menu-icon {
  color: #ffffff;
}

.admin-main {
  background: linear-gradient(180deg, #f5f7fa 0%, #e4e8eb 100%);
  position: fixed;
  top: 60px;
  bottom: 0;
  right: 0;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 20px;
  transition: all 0.3s ease;
}

.m-el-main-width-220 {
  left: 220px;
  width: calc(100% - 220px);
}

.m-el-main-width-64 {
  left: 64px;
  width: calc(100% - 64px);
}

.admin-container {
  height: 100%;
}

.toggle-button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-size: 18px;
  line-height: 45px;
  color: #ffffff;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.toggle-button:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
}

.el-dropdown-menu {
  margin: 10px 0 0 0 !important;
  padding: 10px !important;
  border: 0 !important;
  border-radius: 12px !important;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15) !important;
  backdrop-filter: blur(20px);
}

.el-dropdown-menu__item {
  padding: 10px 20px !important;
  border-radius: 8px !important;
  transition: all 0.3s ease;
}

.el-dropdown-menu__item:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%) !important;
  color: #667eea !important;
}

.admin-main::-webkit-scrollbar-track-piece {
  background-color: transparent;
}

.admin-main::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px transparent;
  box-shadow: inset 0 0 6px transparent;
  background-color: transparent;
}

.admin-main::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(102, 126, 234, 0.3);
  box-shadow: inset 0 0 6px rgba(102, 126, 234, 0.3);
  background-color: rgba(102, 126, 234, 0.3);
  border-radius: 3px;
}

.admin-sidebar {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.admin-sidebar::-webkit-scrollbar {
  display: none;
}

.admin-main::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

@media (max-width: 768px) {
  .admin-header {
    padding: 0 10px;
  }
  
  .site-title {
    display: none;
  }
  
  .user-welcome {
    display: none;
  }
  
  .m-el-main-width-220,
  .m-el-main-width-64 {
    left: 0;
    width: 100%;
  }
}
</style>