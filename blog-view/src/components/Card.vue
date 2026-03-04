<template>
  <div class="card">
    <div class="card-banner">
      <div class="banner-gradient"></div>
    </div>
    <div class="card-header">
      <div class="avatar-wrapper">
        <el-avatar :size="80" :src="user.avatar" class="user-avatar"></el-avatar>
        <div class="avatar-glow"></div>
      </div>
      <h3 class="user-name">小源先生</h3>
      <p class="user-bio">热爱编程 · 分享技术 · 探索世界</p>
    </div>
    <div class="card-stats">
      <div class="stat-item">
        <span class="stat-value">100+</span>
        <span class="stat-label">文章</span>
      </div>
      <div class="stat-divider"></div>
      <div class="stat-item">
        <span class="stat-value">10K+</span>
        <span class="stat-label">阅读</span>
      </div>
      <div class="stat-divider"></div>
      <div class="stat-item">
        <span class="stat-value">50+</span>
        <span class="stat-label">评论</span>
      </div>
    </div>
    <div class="card-social">
      <a class="social-link github" @click="goToGithub" title="GitHub">
        <i class="el-icon-link"></i>
      </a>
      <a class="social-link email" href="mailto:contact@example.com" title="Email">
        <i class="el-icon-message"></i>
      </a>
      <a class="social-link rss" href="#" title="RSS">
        <i class="el-icon-connection"></i>
      </a>
    </div>
    <div class="card-footer">
      <el-collapse>
        <el-collapse-item name="2" title="个人简介">
          <div class="intro-content">你吃饭了吗？没吃就吃点代码吧 🍜</div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Card",
  data() {
    return {
      hasLogin: false,
      fit: 'fill',
      user: {
        avatar: "http://r9vd4j8w3.hd-bkt.clouddn.com/img/avatar.jpg"
      },
    }
  },
  methods: {
    goToGithub() {
      window.open("https://github.com/", '_blank')
    }
  },
  created() {
  }
}
</script>

<style scoped>
.card {
  width: 100%;
  background: #ffffff;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  animation: slideInLeft 0.6s ease-out;
}

@keyframes slideInLeft {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.card:hover {
  transform: translateY(-8px);
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.15);
}

.card-banner {
  height: 100px;
  position: relative;
  overflow: hidden;
}

.banner-gradient {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
}

.banner-gradient::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
  animation: shimmer 3s infinite;
}

@keyframes shimmer {
  0% { transform: translate(-30%, -30%) rotate(0deg); }
  100% { transform: translate(30%, 30%) rotate(360deg); }
}

.card-header {
  text-align: center;
  padding: 0 20px 20px;
  background: #ffffff;
}

.avatar-wrapper {
  position: relative;
  display: inline-block;
  margin-top: -40px;
}

.user-avatar {
  position: relative;
  z-index: 2;
  border: 4px solid #ffffff;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}

.avatar-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90px;
  height: 90px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  z-index: 1;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.5;
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    opacity: 0.8;
    transform: translate(-50%, -50%) scale(1.1);
  }
}

.user-name {
  margin: 15px 0 8px;
  font-size: 20px;
  font-weight: 700;
  color: #2c3e50;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.user-bio {
  margin: 0;
  font-size: 13px;
  color: #8a99a8;
  font-weight: 500;
}

.card-stats {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 20px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.stat-value {
  font-size: 20px;
  font-weight: 800;
  color: #667eea;
}

.stat-label {
  font-size: 12px;
  color: #8a99a8;
}

.stat-divider {
  width: 1px;
  height: 30px;
  background: linear-gradient(to bottom, transparent, #e0e0e0, transparent);
}

.card-social {
  display: flex;
  justify-content: center;
  gap: 12px;
  padding: 20px;
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #f5f7fa;
  color: #667eea;
  font-size: 18px;
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  cursor: pointer;
}

.social-link:hover {
  transform: translateY(-3px) scale(1.1);
}

.social-link.github:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
}

.social-link.email:hover {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: #ffffff;
  box-shadow: 0 8px 20px rgba(245, 87, 108, 0.4);
}

.social-link.rss:hover {
  background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
  color: #ffffff;
  box-shadow: 0 8px 20px rgba(253, 160, 133, 0.4);
}

.card-footer {
  border-top: 1px solid #f0f0f0;
}

.card-footer /deep/ .el-collapse {
  border: none;
}

.card-footer /deep/ .el-collapse-item__header {
  padding: 15px 20px;
  font-size: 14px;
  font-weight: 600;
  color: #5a6c7d;
  background: #f8f9fa;
  border: none;
  transition: all 0.3s ease;
}

.card-footer /deep/ .el-collapse-item__header:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  color: #667eea;
}

.card-footer /deep/ .el-collapse-item__content {
  padding: 15px 20px;
  background: #ffffff;
  font-size: 13px;
  color: #5a6c7d;
  line-height: 1.6;
}

.intro-content {
  text-align: center;
}

@media (max-width: 768px) {
  .card {
    margin: 0 15px;
  }
}
</style>