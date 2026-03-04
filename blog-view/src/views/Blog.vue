<template>
  <div class="blog-detail-container">
    <div class="blog-detail">
      <div class="blog-header">
        <div class="blog-header-content">
          <h1 class="blog-title">{{ blog.title }}</h1>
          <div class="blog-meta">
            <div class="meta-row">
              <span class="meta-label"><i class="el-icon-date"></i> 发布时间</span>
              <span class="meta-value">{{ blog.createTime.split(' ')[0] }}</span>
            </div>
            <div class="meta-row">
              <span class="meta-label"><i class="el-icon-refresh"></i> 更新时间</span>
              <span class="meta-value">{{ blog.updateTime.split(' ')[0] }}</span>
            </div>
            <div class="meta-row">
              <span class="meta-label"><i class="el-icon-folder"></i> 分类</span>
              <el-tag type="success" effect="plain">{{ blog.typeName }}</el-tag>
            </div>
            <div class="meta-row">
              <span class="meta-label"><i class="el-icon-edit"></i> 字数</span>
              <span class="meta-value">{{ blog.words }} 字</span>
            </div>
            <div class="meta-row">
              <span class="meta-label"><i class="el-icon-view"></i> 阅读</span>
              <span class="meta-value">{{ blog.views }} 次</span>
            </div>
          </div>
        </div>
      </div>

      <div class="blog-actions">
        <el-link v-if="ownBlog" type="primary" icon="el-icon-edit" :underline="false">
          <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">编辑文章</router-link>
        </el-link>
        <div class="blog-interaction">
          <button class="interaction-btn" @click="handleLike">
            <i :class="liked ? 'el-icon-star-on' : 'el-icon-star-off'"></i>
            <span>{{ likeCount }}</span>
          </button>
        </div>
      </div>

      <div class="blog-content-wrapper">
        <markdown-it-vue-light class="md-body" :content="blog.content" v-viewer="{movable: false}" />
      </div>

      <div class="blog-tags">
        <span class="tags-label"><i class="el-icon-price-tag"></i> 标签：</span>
        <el-tag v-for="tag in blog.tags" :key="tag.id" size="medium" class="tag-item" effect="plain">{{ tag.tagName }}</el-tag>
      </div>
    </div>

    <div class="author-message">
      <div class="author-card">
        <div class="author-avatar">
          <el-avatar :size="60" src="http://r9vd4j8w3.hd-bkt.clouddn.com/img/avatar.jpg"></el-avatar>
        </div>
        <div class="author-info">
          <h4 class="author-name">小源先生</h4>
          <p class="author-desc">热爱编程，分享技术心得</p>
          <router-link class="author-link" to="/about">联系作者 <i class="el-icon-arrow-right"></i></router-link>
        </div>
      </div>
      <div class="license-info">
        <ul class="license-list">
          <li><i class="el-icon-time"></i> 发表时间：{{ blog.createTime }}</li>
          <li><i class="el-icon-refresh"></i> 最后修改：{{ blog.updateTime }}</li>
          <li>
            <i class="el-icon-document"></i> 
            本站点采用 <a href="https://creativecommons.org/licenses/by/4.0/" target="_blank" rel="noopener noreferrer">署名 4.0 国际 (CC BY 4.0)</a> 创作共享协议
          </li>
          <li class="license-note">可自由转载、引用，并且允许商业性使用。但需署名作者且注明文章出处。</li>
        </ul>
      </div>
    </div>

    <div class="comment-section">
      <Comment></Comment>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/Comment";

export default {
  name: "Blog",
  components: {Comment},
  data() {
    return {
      types: [],
      blog: {
        id: "",
        title: "",
        content: "",
        createTime: "",
        updateTime: "",
        typeName: "",
        words: 0,
        views: 0,
        tags: []
      },
      ownBlog: false,
      liked: false,
      likeCount: 0
    }
  },
  watch: {
    $route (to, from) {
      const blogId = to.params.blogId
      this.getBlogData(blogId)
    }
  },
  methods: {
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
    },
    getBlog() {
      const blogId = this.$route.params.blogId
      this.getBlogData(blogId)
    },
    getBlogData(blogId) {
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title
        _this.blog.createTime = blog.createTime
        _this.blog.updateTime = blog.updateTime
        _this.blog.views = blog.views
        _this.blog.words = blog.words
        _this.blog.tags = blog.tags || []
        _this.blog.content = blog.content
        if (_this.$store.getters.getUser) {
          _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
        } else {
          _this.ownBlog = false
        }
        for (var i in _this.types) {
          if (blog.typeId == _this.types[i].id) {
            _this.blog.typeName = _this.types[i].typeName
          }
        }
        _this.likeCount = blog.likes || 0
      })
    },
    handleLike() {
      if (this.liked) {
        this.likeCount--
        this.liked = false
        this.$message.success('已取消点赞')
      } else {
        this.likeCount++
        this.liked = true
        this.$message.success('感谢点赞！')
      }
    }
  },
  created() {
    this.getTypes()
    this.getBlog()
  }
}
</script>

<style scoped>
.blog-detail-container {
  animation: fadeIn 0.5s ease;
}

.blog-detail {
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  margin-bottom: 30px;
}

.blog-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 50px 40px;
  color: #ffffff;
}

.blog-header-content {
  max-width: 900px;
  margin: 0 auto;
}

.blog-title {
  font-size: 36px;
  font-weight: 800;
  margin: 0 0 30px 0;
  line-height: 1.3;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.blog-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 25px;
}

.meta-row {
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(255, 255, 255, 0.15);
  padding: 8px 16px;
  border-radius: 20px;
  backdrop-filter: blur(10px);
}

.meta-label {
  font-size: 13px;
  opacity: 0.9;
  display: flex;
  align-items: center;
  gap: 5px;
}

.meta-value {
  font-size: 14px;
  font-weight: 600;
}

.blog-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  border-bottom: 1px solid #f0f0f0;
}

.blog-interaction {
  display: flex;
  gap: 15px;
}

.interaction-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  border: none;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: #ffffff;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(245, 87, 108, 0.3);
}

.interaction-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(245, 87, 108, 0.5);
}

.interaction-btn:active {
  transform: translateY(0);
}

.blog-content-wrapper {
  padding: 40px;
  max-width: 900px;
  margin: 0 auto;
}

.md-body {
  text-align: left;
  font-size: 16px;
  line-height: 1.8;
  color: #2c3e50;
}

.md-body /deep/ h1,
.md-body /deep/ h2,
.md-body /deep/ h3,
.md-body /deep/ h4 {
  color: #2c3e50;
  font-weight: 700;
  margin-top: 30px;
  margin-bottom: 15px;
}

.md-body /deep/ h1 {
  font-size: 28px;
  border-bottom: 3px solid #667eea;
  padding-bottom: 10px;
}

.md-body /deep/ h2 {
  font-size: 24px;
  border-left: 4px solid #667eea;
  padding-left: 15px;
}

.md-body /deep/ p {
  margin-bottom: 16px;
  text-align: justify;
}

.md-body /deep/ code {
  background: #f5f7fa;
  padding: 3px 8px;
  border-radius: 4px;
  font-family: 'Fira Code', 'Consolas', monospace;
  color: #e83e8c;
}

.md-body /deep/ pre {
  background: #282c34;
  border-radius: 8px;
  padding: 20px;
  overflow-x: auto;
  margin: 20px 0;
}

.md-body /deep/ pre code {
  background: transparent;
  color: #abb2bf;
  padding: 0;
}

.md-body /deep/ blockquote {
  border-left: 4px solid #667eea;
  padding-left: 20px;
  margin: 20px 0;
  color: #5a6c7d;
  background: #f8f9fa;
  padding: 15px 20px;
  border-radius: 0 8px 8px 0;
}

.md-body /deep/ img {
  max-width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin: 20px 0;
}

.md-body /deep/ table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

.md-body /deep/ th,
.md-body /deep/ td {
  border: 1px solid #e0e0e0;
  padding: 12px;
  text-align: left;
}

.md-body /deep/ th {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
}

.md-body /deep/ tr:nth-child(even) {
  background: #f8f9fa;
}

.blog-tags {
  padding: 20px 40px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.tags-label {
  color: #8a99a8;
  font-size: 14px;
}

.tag-item {
  cursor: pointer;
  transition: all 0.3s ease;
}

.tag-item:hover {
  transform: translateY(-2px);
}

.author-message {
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8eb 100%);
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 30px;
}

.author-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding-bottom: 25px;
  border-bottom: 1px solid #d0d7de;
  margin-bottom: 20px;
}

.author-avatar {
  flex-shrink: 0;
}

.author-avatar /deep/ .el-avatar {
  border: 3px solid #ffffff;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.author-info {
  flex: 1;
}

.author-name {
  margin: 0 0 5px 0;
  font-size: 18px;
  font-weight: 700;
  color: #2c3e50;
}

.author-desc {
  margin: 0 0 10px 0;
  color: #5a6c7d;
  font-size: 14px;
}

.author-link {
  color: #667eea;
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.author-link:hover {
  color: #764ba2;
}

.author-link i {
  transition: transform 0.3s ease;
}

.author-link:hover i {
  transform: translateX(3px);
}

.license-info {
  background: #ffffff;
  border-radius: 12px;
  padding: 20px;
}

.license-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.license-list li {
  padding: 8px 0;
  color: #5a6c7d;
  font-size: 14px;
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

.license-list li i {
  color: #667eea;
  margin-top: 3px;
}

.license-list li a {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
}

.license-list li a:hover {
  text-decoration: underline;
}

.license-note {
  color: #8a99a8;
  font-size: 13px;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px dashed #e0e0e0;
}

.comment-section {
  background: #ffffff;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 768px) {
  .blog-header {
    padding: 30px 20px;
  }
  
  .blog-title {
    font-size: 24px;
  }
  
  .blog-meta {
    gap: 10px;
  }
  
  .meta-row {
    padding: 6px 12px;
    font-size: 12px;
  }
  
  .blog-content-wrapper {
    padding: 20px;
  }
  
  .blog-actions {
    flex-direction: column;
    gap: 15px;
    padding: 20px;
  }
}
</style>