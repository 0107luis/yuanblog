<template>
  <div id="app">
    <transition name="fade-transform" mode="out-in">
      <router-view/>
    </transition>
  </div>
</template>


<script>
export default {
  name: 'App'
}
</script>
<style>
html, body, #app {
  padding: 0;
  margin: 0;
  background: linear-gradient(180deg, #f5f7fa 0%, #e4e8eb 100%);
  height: 100%;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif;
}

/* 路由过渡动画 */
.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(20px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

/* 页面加载动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

#app {
  animation: fadeIn 0.3s ease;
}

/* 全局滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
}

/* 全局链接样式 */
a {
  color: #667eea;
  text-decoration: none;
  transition: color 0.3s ease;
}

a:hover {
  color: #764ba2;
}

/* 全局选中样式 */
::selection {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.3) 0%, rgba(118, 75, 162, 0.3) 100%);
  color: #2c3e50;
}

/* Element UI 覆盖样式 */
.el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.el-button--primary:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
}

.el-tag {
  border-radius: 8px;
}

.el-card {
  border-radius: 12px;
  border: none;
}

.el-pagination .el-pager li.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
</style>