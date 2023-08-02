<template>
	<div>
	  <el-row :gutter="20">
		<el-col :span="10">
		  <div class="grid-content bg-purple" >
			<!-- 首页user信息 -->
			<el-card shadow= 'hover' v-car="user">
			  <div class="userCard" >
				<el-avatar :size="150" :src="$store.state.Picture" ></el-avatar>
				<div class="userInfo">
				  <p class="important-font">{{user.username}}</p>
				  <p class="secondary-font">{{user.rolename}}</p>
				</div>
			  </div>
			<div class="login-info">
				<p>我们必须接受失望，因为它是有限的，但千万不可失去希望，因为它是无穷的。</p>
			</div>
			</el-card>
			<div class = "Log">
            <div>
				<div class="header"  style="font-size: 24px;margin:10px">
					<span>当前在线列表：</span>
				</div>
				<el-table :data="tableData" stripe border height="350px" style="width: 105%;box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);">
				<el-table-column prop="userName" label="姓名" width="180px" align = center>
				</el-table-column>
				<el-table-column  prop="role.rolename" m label="角色" width="340px" align = center>
				</el-table-column>
            </el-table>

			</div>
			</div> 
		  </div>
		</el-col>
		<el-col :span="14">
			  <!-- 四个订单信息 -->
			<div class="num">
			<el-card shadow= 'hover' v-for="item in countData" :key="item.name" :body-style="{ display: 'flex',padding: 0 }" class="OrderCard">
			  	<i class="icon" :class="'el-icon-'+item.icon" :style="{background: item.color, }" style="text-align: center	;"></i>
			  <div class="clo" >
				<p class="important-font">{{item.value}}</p>
				<p class="secondary-font">{{item.name}}</p>
			  </div>
			</el-card>
		  </div>
		  <div class= "graph">
				<el-card style="width:93%;height: 400px">
					<div style="height: 265px">
						<el-calendar v-model="value"></el-calendar>
					</div>
				</el-card>
		  </div>
		</el-col>
	  </el-row>
	</div>
	</template>
	 
<script>
	 // 引入http
import http from '../util/http.js'
import { mapState } from "vuex";
	export default {
	  name: "Index",
	  
	  data: function() {
		return {
			currentPicture:window.localStorage.getItem("Avater"),
			countRole:0,
			countLog:0,
			countUser:0,
			countScore:0,
			tableData:[],
			user:{
				username:"",
				rolename:"",
			},
			imgUrl:require('../assets/img/PI@8NE30H5Q(GAGMKB)XY@C.jpg'),
			value: new Date(),
			countData:[
				{
				name: '在线人数',
				value: 0,
				icon: 'success',
				color: '#2ec7c9'
				},
				{
				name: '用户数量',
				value: 0,
				icon: 'star-on',
				color: '#ffb980'
				},
				{
				name: '商品数量',
				value: 0,
				icon: 's-goods',
				color: '#5ab1ef'
				},
				{
				name: '订单数量',
				value: 0,
				icon: 'success',
				color: '#2ec7c9'
				},
			]
		}
	  },
	  computed:{
			...mapState(["Picture"])
		},
	  mounted: function(){
		// this.currentPicture=window.localStorage.getItem("Avater");
		http({
				method:'get',
				url:"/index/show",
				params:{
						userCode: localStorage.getItem("userName"),
					}
			}).then((res)=>{
				if(res.data.code===200){
					this.user.username=res.data.data.username;
					this.user.rolename=res.data.data.role;
					this.countData[0].value=res.data.data.online;
					this.countData[1].value=res.data.data.userNumber;
					this.countData[2].value=res.data.data.goodsNumber;
					this.countData[3].value=res.data.data.orderNumber;
					this.tableData = res.data.data.onlineUser;
				}
			}).catch(()=>{
				this.$message({
					type: 'error',
					message: '服务器错误！'
				});
			})
	  },
	  methods:{
		}
	}
	</script>
	 
	<!-- Add "scoped" attribute to limit CSS to this component only -->
	<style lang="less" scoped>
	.el-card__body {
		padding: 10px;
	}
	.userCard{
	  height: 180px;
	  display: flex;
	  border-bottom: 2px solid #DCDFE6;
	  border-radius: 2px;
	}
	.userInfo{
	  width: auto;
	  padding: 6% 0 0 6%;
	}
	.important-font{
		font-weight: 900;
		font-size: 25px;
		text-align:center
	}
	.secondary-font{
	  color: #909399;
	  text-align:center
	}
	.login-info{
	  height: 40px;
	  text-align: left;
	  color: #909399;
	}
	.tableInfo{
	  margin: 20px 0 0 0;
	}
	.OrderCard{
	   margin: 0 0 30px 30px;
	   border: #DCDFE6 1px solid;
	   border-radius: 10px;
	   i{
		 width: 30%;
		 line-height: 120px;
		 font-size: 30px;
		 color:#fff
	   }
	   div{
		 width: 300px;
	   }
	}
	.el-card{
	  border: none;
	}
	.num{
	  display: flex;
	  flex-wrap: wrap;
	}
	.graph{
	  margin: 15px 0 0 0;
	  transform: translateX(4%);
	}
	.el-calendar{
	  height: 265px ;
	}
	.clo{
		background-color: #eaeef7;
	}
	.Log{
		transform: translateY(9%);
	}
	/deep/ .el-calendar {
    font-size: 14px;
    .next {
      border: none;
    }
    td {
      border: none;
    }
    .el-calendar-day {
      height: 55px !important;
      text-align: center;
      border: none;
    }
    .el-calendar__header {
      justify-content: space-between;
    }
    .is-selected {
      background-color: #1d8dd8;
      color: #fff;
    }
	
  }
	</style>