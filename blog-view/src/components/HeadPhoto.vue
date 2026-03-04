<template>
  <div class="head-photo" style="width: 100%;height: 800px">
    <!-- banner -->

    <img src="http://r9vd4j8w3.hd-bkt.clouddn.com/img/background0.jpg" style="width: 100%;height: 100%">


    <div class="banner-container">
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow="none" class="welcome">
          <h1 class="tit">
            欢迎来到小源先生的家里
            <div class="border"></div>
          </h1>
          <h2 class="intro">{{intro}}
          </h2>


          <div id="waypoint" class="mini-nav" align="center">
            <router-link  class="item el-icon-s-home" to="/">
              首页
            </router-link>
            <router-link  class="item el-icon-time" to="/archives">
              时间轴
            </router-link>
            <router-link  class="item el-icon-edit" to="/friends">
              友人账
            </router-link>
            <router-link class="item el-icon-info" to="/about">
              关于我
            </router-link>
          </div>

          <!-- 向下滚动 -->
          <div class="bounce down"  @click="startRead"  >
            <i class="el-icon-arrow-down" style="color: white"></i>
          </div>

        </el-card>
      </el-col>
    </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: "HeadPhoto",
  data() {
    return {
      intro: '',
      intersectionOptions: {
        root: document.querySelector("#app"), //用作视口的元素，用于检查目标的可见性。必须是目标的祖先。如果未指定，则默认为浏览器视口null
        rootMargin: "0px",
        threshold: 0.33 //范围为 0-1: 阈值为1.0意味着当100％的目标在root选项指定的元素中可见时，将调用回调
      }
    }
  },
  methods:{
    // 开始进入主页
    startRead() {
      this.$nextTick(() => {
        this.$emit('fatherMethod')
      })
    },
  },
  mounted() {
    let str = '这是我的个人博客，希望能够相互学习...';
    let idx = 0;
    let that = this
    let timer = setTimeout( function fn() {
      // console.log(this.intro)
      that.intro = that.intro+ str.substring(idx,idx+1)
      idx++
      if (idx>str.length){
        that.intro = ''
        idx = 0
      }
      setTimeout(fn,200)
    },2000)

    // 导航栏显示
    var waypoint = new Waypoint({
      element: document.getElementById('waypoint'),
      handler: function (direction) {
        if(document.getElementById('waypoint')!==null){
          if(document.getElementById('header-photo').style.display!=='none'){
            if (direction === 'down') {
              $('#nav').show(500);
            } else {
              $('#nav').hide(500);
            }
            // console.log('Scrolled to waypoint!  ' + direction);
          }

        }
      }
    });

  }

}
</script>

<style scoped>
.head-photo {
  margin-bottom: 0;
  text-align: center;
  position: relative;
  top: 0;
  height: 100vh;
  min-height: 600px;
  overflow: hidden;
}

.head-photo img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  will-change: transform;
}

.banner-container {
  line-height: 1.5;
  color: #eee;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.4) 0%, rgba(118, 75, 162, 0.4) 50%, rgba(245, 87, 108, 0.3) 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.banner-container /deep/ .el-row {
  height: 100%;
  display: flex;
  align-items: center;
}

.banner-container /deep/ .el-col {
  display: flex;
  align-items: center;
  justify-content: center;
}

.welcome {
  background: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 24px;
  height: auto !important;
  position: relative;
  padding: 60px 40px;
  max-width: 900px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  animation: fadeInUp 1s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(40px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.tit {
  box-sizing: border-box;
  position: relative;
  width: auto;
  max-width: 700px;
  height: auto;
  min-height: 80px;
  line-height: 1.3;
  margin: 0 auto 30px;
  color: white;
  text-align: center;
  font-size: 48px;
  font-weight: 800;
  letter-spacing: 3px;
  text-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.border {
  display: none;
}

.intro {
  letter-spacing: 2px;
  line-height: 2;
  width: 90%;
  max-width: 600px;
  margin: 0 auto 40px;
  text-align: center;
  font-weight: 400;
  color: rgba(255, 255, 255, 0.95);
  font-size: 18px;
  min-height: 40px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.mini-nav {
  position: relative;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 30px;
  flex-wrap: wrap;
}

.item {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border-radius: 25px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  margin: 5px;
  line-height: 1.4;
  text-align: center;
  font-style: normal;
  text-decoration: none;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  opacity: 0.8;
  color: #ffffff;
  padding: 12px 24px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
}

.item:hover {
  opacity: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  transform: translateY(-5px) scale(1.05);
  box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
  border-color: transparent;
}

.item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: 25px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  opacity: 0;
  transition: opacity 0.4s ease;
  z-index: -1;
}

.down {
  animation: bounce 2s infinite;
  font-size: 24px;
  position: absolute;
  bottom: -60px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.6);
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  cursor: pointer;
  transition: all 0.3s ease;
  color: #ffffff;
}

.down:hover {
  animation: none;
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 0 0 30px rgba(255, 255, 255, 0.5);
  transform: translateX(-50%) scale(1.1);
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateX(-50%) translateY(0);
  }
  40% {
    transform: translateX(-50%) translateY(-15px);
  }
  60% {
    transform: translateX(-50%) translateY(-7px);
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@media (max-width: 768px) {
  .head-photo {
    height: 70vh;
    min-height: 500px;
  }
  
  .welcome {
    padding: 40px 20px;
    margin: 0 15px;
  }
  
  .tit {
    font-size: 28px;
    letter-spacing: 1px;
  }
  
  .intro {
    font-size: 14px;
    line-height: 1.8;
  }
  
  .mini-nav {
    gap: 10px;
  }
  
  .item {
    padding: 10px 18px;
    font-size: 13px;
  }
}
</style>