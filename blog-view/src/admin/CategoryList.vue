<template>
  <div class="category-container">
    <!-- 头部操作栏 -->
    <el-card class="header-card">
      <div class="header-actions">
        <div class="header-title">
          <i class="el-icon-folder-opened"></i>
          <span>分类管理</span>
        </div>
        <el-button type="primary" icon="el-icon-plus" @click="addDialogVisible=true">添加分类</el-button>
      </div>
    </el-card>

    <!-- 分类卡片列表 -->
    <div class="category-cards" v-if="typeList.length > 0">
      <el-card v-for="item in typeList" :key="item.id" class="category-card" shadow="hover">
        <div class="category-content">
          <div class="category-icon">
            <i class="el-icon-folder"></i>
          </div>
          <div class="category-info">
            <div class="category-name">{{ item.typeName }}</div>
            <div class="category-id">ID: {{ item.id }}</div>
          </div>
          <div class="category-actions">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDialog(item)">编辑</el-button>
            <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除这个分类吗？" @confirm="deleteTypeById(item.id)">
              <el-button slot="reference" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
            </el-popconfirm>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 空状态 -->
    <el-empty v-else description="暂无分类"></el-empty>

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

    <!--添加分类对话框-->
    <el-dialog title="添加分类" width="40%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed" class="modern-dialog">
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
        <el-form-item label="名称" prop="typeName">
          <el-input v-model="addForm.typeName" placeholder="请输入分类名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="addDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="saveType">确 定</el-button>
      </span>
    </el-dialog>

    <!--编辑分类对话框-->
    <el-dialog title="编辑分类" width="40%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed" class="modern-dialog">
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="名称" prop="typeName">
          <el-input v-model="editForm.typeName" placeholder="请输入分类名称"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="editDialogVisible=false">取 消</el-button>
        <el-button type="primary" @click="editType">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Category",
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
      typeList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        typeName: '',
      },
      editForm: {
        typeName: '',
      },
      formRules: {
        typeName: [{required: true, message: '名称', trigger: 'blur'}],
      }
    }
  },
  methods:{
    //获取当前分页的分类
    getTypeList() {
      const _this = this
      this.$axios.get('/type/list?currentPage=' + this.queryInfo.currentPage+"&pageSize=" + this.queryInfo.pageSize).then((res) => {
        _this.typeList = res.data.data.records
        _this.total = res.data.data.total
        //console.log(_this.typeList)
      });
    },

    handleCurrentChange(newPage) {
      this.queryInfo.currentPage = newPage
      this.getTypeList()
    },
    handleSizeChange(newPageSize){
      this.queryInfo.pageSize = newPageSize
      this.getTypeList()

    },
    showEditDialog(row){
      this.editForm = row
      this.editDialogVisible = true
    },
    editDialogClosed() {
      this.editForm = {}
      this.editDialogVisible = false
    },
    saveType(){
      const _this = this
      this.$axios.post('/type/create',this.addForm).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.addDialogVisible = false

            _this.getTypeList()
            //_this.$router.push("/blogList")
          }
        })
      });
      this.$refs['addFormRef'].resetFields();
    },
    editType(){
      const _this = this
      this.$axios.post('/type/update', this.editForm).then(res => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.editDialogVisible = false
            _this.getTypeList()
          }
        });
      });
    },
    deleteTypeById(id) {
      const _this = this
      this.$axios.get('/type/delete/' + id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getTypeList()
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
    this.getTypeList()
  }
}
</script>

<style scoped>
.category-container {
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
  color: #667eea;
}

.category-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.category-card {
  border-radius: 16px;
  border: none;
  transition: all 0.3s ease;
}

.category-card:hover {
  transform: translateY(-5px);
}

.category-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.category-icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  font-size: 24px;
  flex-shrink: 0;
}

.category-info {
  flex: 1;
  min-width: 0;
}

.category-name {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.category-id {
  font-size: 12px;
  color: #8a99a8;
  margin-top: 4px;
}

.category-actions {
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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.modern-dialog /deep/ .el-dialog {
  border-radius: 16px;
  overflow: hidden;
}

.modern-dialog /deep/ .el-dialog__header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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