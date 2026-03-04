<template>
  <div class="tags-container">
    <!-- 头部操作栏 -->
    <el-card class="header-card">
      <div class="header-actions">
        <div class="header-title">
          <i class="el-icon-price-tag"></i>
          <span>标签管理</span>
        </div>
        <el-button type="primary" icon="el-icon-plus" @click="addDialogVisible=true">添加标签</el-button>
      </div>
    </el-card>

    <!-- 标签卡片列表 -->
    <div class="tag-cloud">
      <el-card v-for="item in tagList" :key="item.id" class="tag-card" shadow="hover">
        <div class="tag-content">
          <div class="tag-badge">
            <i class="el-icon-price-tag"></i>
          </div>
          <div class="tag-info">
            <div class="tag-name">{{ item.tagName }}</div>
            <div class="tag-id">ID: {{ item.id }}</div>
          </div>
          <div class="tag-actions">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDialog(item)">编辑</el-button>
            <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除这个标签吗？" @confirm="deleteTagById(item.id)">
              <el-button slot="reference" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 空状态 -->
    <el-empty v-if="tagList.length === 0" description="暂无标签"></el-empty>

    <!--分页-->
    <div class="pagination-wrapper" v-if="total > 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.currentPage"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="queryInfo.pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        background>
      </el-pagination>
    </div>

    <!--添加标签对话框-->
    <el-dialog title="添加标签" width="40%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed" class="modern-dialog">
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
        <el-form-item label="名称" prop="tagName">
          <el-input v-model="addForm.tagName" placeholder="请输入标签名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="addDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="saveTag">确 定</el-button>
      </span>
    </el-dialog>

    <!--编辑标签对话框-->
    <el-dialog title="编辑标签" width="40%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed" class="modern-dialog">
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="名称" prop="tagName">
          <el-input v-model="editForm.tagName" placeholder="请输入标签名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="editDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="editTag">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Tag",
  data() {
    return {
      infoForm: {
        content: '',
        commentEnabled: true,
      },
      queryInfo: {
        currentPage: 1,
        pageSize: 10
      },
      tagList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        tagName: '',
      },
      editForm: {
        tagName: '',
      },
      formRules: {
        tagName: [{required: true, message: '名称', trigger: 'blur'}],
      }
    }
  },
  methods:{
    //获取当前分页的标签
    getTagList() {
      const _this = this
      this.$axios.get('/tags/list?currentPage=' + this.queryInfo.currentPage+"&pageSize=" + this.queryInfo.pageSize).then((res) => {
        _this.tagList = res.data.data.records
        _this.total = res.data.data.total
      });
    },
    handleCurrentChange(newPage) {
      this.queryInfo.currentPage = newPage
      this.getTagList()
    },
    handleSizeChange(newPageSize){
      this.queryInfo.pageSize = newPageSize
      this.getTagList()
    },
    showEditDialog(row){
      this.editForm = row
      this.editDialogVisible = true
    },
    editDialogClosed() {
      this.editForm = {}
      this.editDialogVisible = false
    },
    saveTag(){
      const _this = this
      this.$axios.post('/tag/create',this.addForm).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.addDialogVisible = false
            _this.getTagList()
          }
        })
      });
      this.$refs['addFormRef'].resetFields();
    },
    editTag(){
      const _this = this
      this.$axios.post('/tag/update', this.editForm).then(res => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.editDialogVisible = false
            _this.getTagList()
          }
        });
      });
    },
    deleteTagById(id) {
      const _this = this
      this.$axios.post('/tag/delete/' + id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getTagList()
          }
        })
      })
    },
    commentEnabledChanged(){
    },
    addDialogClosed(){
      this.addDialogVisible = false
    },
  },
  created() {
    this.getTagList()
  }
}
</script>

<style scoped>
.tags-container {
  animation: fadeIn 0.4s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.header-card {
  margin-bottom: 20px;
  border-radius: 12px;
  border: none;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
}

.header-title i {
  color: #f5576c;
}

.tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.tag-card {
  border-radius: 16px;
  border: none;
  transition: all 0.3s ease;
  min-width: 250px;
}

.tag-card:hover {
  transform: translateY(-5px);
}

.tag-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.tag-badge {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  font-size: 22px;
  flex-shrink: 0;
}

.tag-info {
  flex: 1;
  min-width: 0;
}

.tag-name {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.tag-id {
  font-size: 12px;
  color: #8a99a8;
  margin-top: 4px;
}

.tag-actions {
  display: flex;
  gap: 8px;
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
}

.pagination-wrapper /deep/ .el-pagination .el-pager li.active {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.modern-dialog /deep/ .el-dialog {
  border-radius: 16px;
  overflow: hidden;
}

.modern-dialog /deep/ .el-dialog__header {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  padding: 20px;
  margin: 0;
}

.modern-dialog /deep/ .el-dialog__title {
  color: #ffffff;
  font-weight: 600;
}

.modern-dialog /deep/ .el-dialog__headerbtn .el-dialog__close {
  color: #ffffff;
}

.modern-dialog /deep/ .el-dialog__body {
  padding: 30px;
}

.modern-dialog /deep/ .el-dialog__footer {
  padding: 20px;
  border-top: 1px solid #f0f0f0;
}
</style>