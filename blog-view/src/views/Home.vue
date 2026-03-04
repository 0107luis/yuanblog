<template>
  <div class="home-container">
    <div v-for="blog in blogs" :key="blog.id" class="blog-card-wrapper">
      <div class="blog-card">
        <div class="blog-card-cover">
          <img :src="blog.firstPicture" class="cover-image" v-viewer="{movable: false}" alt="blog cover">
          <div class="card-overlay">
            <span class="card-category">{{ blog.typeName }}</span>
          </div>
        </div>
        <div class="blog-card-content">
          <h2 class="blog-card-title">
            <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="blog-link">{{ blog.title }}</router-link>
          </h2>
          <div class="blog-card-meta">
            <span class="meta-item"><i class="el-icon-date"></i> {{ blog.createTime.split(' ')[0] }}</span>
            <span class="meta-item"><i class="el-icon-edit"></i> {{ blog.words }}字</span>
            <span class="meta-item"><i class="el-icon-time"></i> 更新于 {{ blog.updateTime.split(' ')[0] }}</span>
          </div>
          <markdown-it-vue-light class="blog-card-description" :content="blog.description" v-viewer="{movable: false}"/>
          <div class="blog-card-footer">
            <router-link :to="{name: 'Blog', params: {blogId: blog.id}}" class="read-more-btn">
              阅读全文 <i class="el-icon-arrow-right"></i>
            </router-link>
            <div class="blog-card-tags">
              <el-tag v-if="blog.tags" v-for="tag in blog.tags.slice(0, 3)" :key="tag.id" size="mini" class="tag-item">{{ tag.tagName }}</el-tag>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="pageShow" class="home-page">
      <el-pagination :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     background
                     layout="prev, pager, next"
                     @current-change=getData>
      </el-pagination>
    </div>
  </div>
</template>

<script>




export default {
  name: "Home",
  data() {
    return {
      blogs: [],
      types: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      pageShow: 0
    }
  },
  methods: {
    //跳转到博客详情页
    toBlog(blog) {
      this.$router.push(`/blog/${blog.id}`)
    },
//获取分类表
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      }).catch(error => {
        console.error('获取分类失败:', error)
        // 设置默认空数组，避免后续代码报错
        _this.types = []
      })
      //console.log(this.types)
    },
//分页获取博客
    getData(currentPage) {
      if(currentPage !== 1){
        $('#waypoint').hide();
        $('#header-photo').hide();
      }else {
        $('#waypoint').show();
        $('#header-photo').show();
      }
      const _this = this
      this.$axios.get('/blogs?currentPage=' + currentPage).then((res) => {

        _this.blogs = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size
        _this.pageShow = 1

        for (var i in _this.blogs) {
          for (var j in _this.types) {
            if (_this.blogs[i].typeId == _this.types[j].id) {
              _this.blogs[i].typeName = _this.types[j].typeName
            }
          }


        }
      }).catch(error => {
        console.error('获取博客列表失败:', error)
        // 设置默认值，避免页面报错
        _this.blogs = []
        _this.currentPage = 1
        _this.total = 0
        _this.pageSize = 5
        _this.pageShow = 1
      })

      //改变页号后返回顶部
      this.scrollToTop()

    }
  },
  created() {
    this.getTypes()
    this.getData(1);
    //console.log(this.blogs)
  },

  mounted() {

  },
  watch: {
  },

}
</script>


<style scoped>
.home-container {
  animation: fadeInUp 0.6s ease-out;
}

.blog-card-wrapper {
  margin-bottom: 40px;
  animation: slideIn 0.5s ease-out;
  animation-fill-mode: both;
}

.blog-card-wrapper:nth-child(1) { animation-delay: 0.1s; }
.blog-card-wrapper:nth-child(2) { animation-delay: 0.2s; }
.blog-card-wrapper:nth-child(3) { animation-delay: 0.3s; }
.blog-card-wrapper:nth-child(4) { animation-delay: 0.4s; }
.blog-card-wrapper:nth-child(5) { animation-delay: 0.5s; }

.blog-card {
  background: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  display: flex;
  flex-direction: row;
  min-height: 280px;
}

.blog-card:hover {
  transform: translateY(-8px) scale(1.01);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.blog-card-cover {
  position: relative;
  width: 320px;
  flex-shrink: 0;
  overflow: hidden;
}

.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease;
}

.blog-card:hover .cover-image {
  transform: scale(1.1);
}

.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.8) 0%, rgba(118, 75, 162, 0.8) 100%);
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  padding: 20px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.blog-card:hover .card-overlay {
  opacity: 1;
}

.card-category {
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.2);
  padding: 6px 16px;
  border-radius: 20px;
  backdrop-filter: blur(10px);
}

.blog-card-content {
  flex: 1;
  padding: 30px 35px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.blog-card-title {
  margin: 0 0 15px 0;
  font-size: 24px;
  font-weight: 700;
  line-height: 1.4;
}

.blog-card-title .blog-link {
  color: #2c3e50;
  text-decoration: none;
  transition: color 0.3s ease;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.blog-card-title .blog-link:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.blog-card-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
  font-size: 13px;
  color: #8a99a8;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.meta-item i {
  color: #667eea;
}

.blog-card-description {
  flex: 1;
  color: #5a6c7d;
  font-size: 15px;
  line-height: 1.8;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  margin-bottom: 20px;
}

.blog-card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
}

.read-more-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #ffffff;
  border-radius: 25px;
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.read-more-btn:hover {
  transform: translateX(5px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
}

.read-more-btn i {
  transition: transform 0.3s ease;
}

.read-more-btn:hover i {
  transform: translateX(3px);
}

.blog-card-tags {
  display: flex;
  gap: 8px;
}

.tag-item {
  cursor: pointer;
  transition: all 0.3s ease;
}

.tag-item:hover {
  transform: translateY(-2px);
}

.home-page {
  text-align: center;
  margin: 50px 0 30px;
  animation: fadeIn 0.5s ease;
}

.home-page /deep/ .el-pagination {
  display: inline-block;
}

.home-page /deep/ .el-pagination .el-pager li {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.home-page /deep/ .el-pagination .el-pager li:hover {
  transform: scale(1.1);
}

.home-page /deep/ .el-pagination .el-pager li.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@media (max-width: 900px) {
  .blog-card {
    flex-direction: column;
  }
  
  .blog-card-cover {
    width: 100%;
    height: 200px;
  }
  
  .blog-card-content {
    padding: 25px;
  }
  
  .blog-card-title {
    font-size: 20px;
  }
}
</style>