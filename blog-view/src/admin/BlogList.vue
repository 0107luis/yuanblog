<template>
  <div class="bloglist-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <div class="stat-card stat-blue">
          <div class="stat-icon"><i class="el-icon-document"></i></div>
          <div class="stat-info">
            <div class="stat-value">{{ total }}</div>
            <div class="stat-label">文章总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-green">
          <div class="stat-icon"><i class="el-icon-circle-check"></i></div>
          <div class="stat-info">
            <div class="stat-value">{{ publishedCount }}</div>
            <div class="stat-label">已发布</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-purple">
          <div class="stat-icon"><i class="el-icon-time"></i></div>
          <div class="stat-info">
            <div class="stat-value">{{ draftCount }}</div>
            <div class="stat-label">草稿</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-orange">
          <div class="stat-icon"><i class="el-icon-view"></i></div>
          <div class="stat-info">
            <div class="stat-value">{{ totalViews }}</div>
            <div class="stat-label">总阅读量</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!--搜索区域-->
    <el-card class="search-card">
      <el-row :gutter="20" align="middle">
        <el-col :span="8">
          <el-input v-model="queryString" :clearable="true" placeholder="请输入标题搜索" size="medium"
                    @clear="search" @keyup.native.enter="search">
            <el-button slot="append" icon="el-icon-search" @click="search(queryString)"></el-button>
          </el-input>
        </el-col>
        <el-col :span="16" style="text-align: right;">
          <el-button type="primary" icon="el-icon-plus" @click="$router.push('/writeBlog')">写新文章</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!--博客列表 - 卡片式布局-->
    <div class="blog-cards">
      <el-card v-for="blog in blogList" :key="blog.id" class="blog-card" shadow="hover">
        <div class="blog-card-inner">
          <div class="blog-cover" v-if="blog.firstPicture">
            <img :src="blog.firstPicture" alt="cover">
          </div>
          <div class="blog-content">
            <div class="blog-header">
              <h3 class="blog-title">
                <router-link :to="'/blog/edit/' + blog.id">{{ blog.title }}</router-link>
              </h3>
              <el-tag :type="blog.status === 1 ? 'success' : 'info'" size="mini">
                {{ blog.status === 1 ? '已发布' : '草稿' }}
              </el-tag>
            </div>
            <div class="blog-meta">
              <span><i class="el-icon-folder"></i> {{ blog.typeName }}</span>
              <span><i class="el-icon-date"></i> {{ formatDate(blog.createTime) }}</span>
              <span><i class="el-icon-view"></i> {{ blog.views || 0 }}</span>
              <span><i class="el-icon-edit"></i> {{ blog.words || 0 }}字</span>
            </div>
            <div class="blog-actions">
              <el-button type="primary" size="mini" icon="el-icon-edit" @click="goBlogEditPage(blog.id)">编辑</el-button>
              <el-button type="success" size="mini" icon="el-icon-view" @click="goBlogView(blog.id)">预览</el-button>
              <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除这篇博客吗？" @confirm="deleteBlogById(blog.id)">
                <el-button slot="reference" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
              </el-popconfirm>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!--空状态-->
    <el-empty v-if="blogList.length === 0" description="暂无文章"></el-empty>

    <!--分页-->
    <div class="pagination-wrapper" v-if="total > 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="queryInfo.pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        background>
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "BlogList",
  data() {
    return {
      queryInfo: {
        title: '',
        categoryId: null,
        pageNum: 1,
        pageSize: 10
      },
      queryString: '',
      blogList: [],
      total: 0,
      types: [],
      totalViews: 0
    }
  },
  computed: {
    publishedCount() {
      return this.blogList.filter(b => b.status === 1).length
    },
    draftCount() {
      return this.blogList.filter(b => b.status !== 1).length
    }
  },
  created() {
    this.getTypes()
    this.getData();
  },
  methods: {
    formatDate(date) {
      if (!date) return ''
      return date.split(' ')[0]
    },
    goBlogEditPage(blogId) {
      this.$router.push(`/blog/edit/${blogId}`)
    },
    goBlogView(blogId) {
      this.$router.push(`/blog/${blogId}`)
    },
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
    },
    getData() {
      const _this = this
      this.$axios.get('/blogList?currentPage=' + this.queryInfo.pageNum+"&pageSize=" + this.queryInfo.pageSize,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.blogList = res.data.data.records
        _this.total = res.data.data.total
        _this.totalViews = res.data.data.records.reduce((sum, blog) => sum + (blog.views || 0), 0)
        for (var i in _this.blogList) {
          for (var j in _this.types) {
            if (_this.blogList[i].typeId == _this.types[j].id) {
              _this.blogList[i].typeName = _this.types[j].typeName
            }
          }
        }
      });
    },
    deleteBlogById(blogId) {
      const _this = this
      this.$axios.get('/blog/delete/' + blogId).then((res) => {
        _this.$alert('删除成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getData()
          }
        })
      })
    },
    search(queryString) {
      const _this = this
      this.$axios.get('/blogList/search?queryString=' + queryString,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.blogList = res.data.data.records
        _this.total = res.data.data.total
        for (var i in _this.blogList) {
          for (var j in _this.types) {
            if (_this.blogList[i].typeId == _this.types[j].id) {
              _this.blogList[i].typeName = _this.types[j].typeName
            }
          }
        }
      });
    },
    handleSizeChange(newPageSize) {
      this.queryInfo.pageSize = newPageSize;
      this.getData();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getData()
    },
    blogPublishedChanged(blogId){
      const _this = this
      this.$axios.get('/blog/publish/'+blogId).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getData()
          }
        })
      });
    }
  }
}
</script>
<style scoped>
.bloglist-container {
  animation: fadeIn 0.4s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #ffffff;
}

.stat-blue .stat-icon { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.stat-green .stat-icon { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }
.stat-purple .stat-icon { background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%); }
.stat-orange .stat-icon { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); }

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 800;
  color: #2c3e50;
  line-height: 1.2;
}

.stat-label {
  font-size: 14px;
  color: #8a99a8;
  margin-top: 5px;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 12px;
  border: none;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.blog-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
}

.blog-card {
  border-radius: 16px;
  border: none;
  transition: all 0.3s ease;
  overflow: hidden;
}

.blog-card:hover {
  transform: translateY(-5px);
}

.blog-card-inner {
  display: flex;
  flex-direction: column;
}

.blog-cover {
  height: 150px;
  overflow: hidden;
  border-radius: 16px 16px 0 0;
}

.blog-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.blog-card:hover .blog-cover img {
  transform: scale(1.05);
}

.blog-content {
  padding: 15px;
}

.blog-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.blog-title {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.blog-title a {
  color: #2c3e50;
  text-decoration: none;
  transition: color 0.3s ease;
}

.blog-title a:hover {
  color: #667eea;
}

.blog-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  font-size: 12px;
  color: #8a99a8;
  margin-bottom: 15px;
}

.blog-meta span {
  display: flex;
  align-items: center;
  gap: 5px;
}

.blog-meta i {
  color: #667eea;
}

.blog-actions {
  display: flex;
  gap: 10px;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

.pagination-wrapper {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}

.pagination-wrapper /deep/ .el-pagination {
  padding: 20px;
  border-radius: 25px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.pagination-wrapper /deep/ .el-pagination .el-pager li {
  border-radius: 8px;
  margin: 0 3px;
}

.pagination-wrapper /deep/ .el-pagination .el-pager li.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

@media (max-width: 768px) {
  .stats-row .el-col {
    margin-bottom: 15px;
  }
  
  .blog-cards {
    grid-template-columns: 1fr;
  }
}
</style>