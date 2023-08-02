<template>
    <div id="All">
    	<el-container>
			<el-aside width="auto">
				<div line-height="20px" style="margin-top:20px;margin-right: 10px;">
					<i class="el-icon-shopping-cart-2" style="font-size: 25px; color: white; margin-left: 15px;"></i>
				    <span style=" font-size: 20px;" class="title"><strong>{{isCollapse?'':'超市管理系统'}}</strong></span>
				</div>
							
				<el-menu class="el-menu-vertical-demo" background-color="#545c64"  text-color="#fff" @open="handleOpen" @close="handleClose" 
				active-text-color="#ffd04b" default-active="1-4-1" :collapse="isCollapse" router >
				<!-- :default-active="this.$route.path" -->
				<el-menu-item-group v-for="(v,i) in data1" :key="i">
					<el-menu-item  router="true"  v-for="(children,n) in v.children"  :index="children.path" :key="n" >
						<i :class="children.icon" ></i>
						<span style="margin-left:25px">{{children.name}}</span>
					</el-menu-item>
				</el-menu-item-group>
			</el-menu>
			</el-aside>
			<el-container>
				<el-header height="55px">
					<div class="l-content">
						<el-button @click="handleMenu" plain icon="el-icon-menu" size="mini"></el-button>
						<!-- <span style="color:#fff">首页</span> -->
						
					</div>
					

					<div class="r-content">
						<div class="demo-basic--circle" style=" height: 60px; float: right;">
    								<el-avatar :size="50" :src="currentPicture" style="vertical-align: middle;margin-right: 0.625rem;">
    								</el-avatar>
    								<el-dropdown>
    									<el-button type="primary" round>
    										{{userName}}<i class="el-icon-arrow-down el-icon--right"></i>
    									</el-button>
    									<el-dropdown-menu slot="dropdown">
											<el-dropdown-item @click.native="uploadimg">更换头像</el-dropdown-item>
    										<el-dropdown-item @click.native="shutDown">注销</el-dropdown-item>
    									</el-dropdown-menu>
    								</el-dropdown>
    								
    					</div>
					</div>
			</el-header>

			<div class="container-box">
			<el-dialog title="上传" :visible.sync="imgupload" width="35%" style="text-align: center;">
				<el-upload class="upload-demo" action="#" drag multiple :headers="headers" :auto-upload="false"
				 :file-list="fileList" :on-change="handleChange">
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip">上传jpg或png格式文件</div>
				</el-upload>
				<div slot="footer" class="dialog-footer">
					<el-button @click="imgupload = false">取 消</el-button>
					<el-button type="primary" @click="confirmUpload()">上 传</el-button>
				</div>
			</el-dialog>
			</div>


			<el-main>
				<router-view></router-view>
			</el-main>
			</el-container>
			
			
    		
    	</el-container>
    </div>
</template>

<script>
   	import { mapState } from "vuex";
    import defaultHeadPicture from "../assets/img/PI@8NE30H5Q(GAGMKB)XY@C.jpg"
	import http from '../util/http.js'
    export default {
        name: "All",
		data() {
			return {
				currentPicture:"",
				fileList: [],
				headers: {
					'Content-Type': 'multipart/form-data'
				},
				tags: [
				{ name: '标签一', type: '' },
				{ name: '标签二', type: 'success' },
				{ name: '标签三', type: 'info' },
				{ name: '标签四', type: 'warning' },
				{ name: '标签五', type: 'danger' }
				],
				imgupload:false,
				circleUrl: defaultHeadPicture,
				data1:[],
				userName:"",
				isCollapse:false
			}
		},
		computed:{
			...mapState(["Picture"])
		},
		mounted(){
			http({
				method: 'post',
				url: "/img/download",
				params:{
					userCode:localStorage.getItem("userName")
				},
				responseType:"blob",
				}).then((res) => {	
					let blob = new Blob([res.data],{type:'image/jpeg'})
					this.currentPicture = URL.createObjectURL(blob)
					this.$store.state.Picture= this.currentPicture
				}).catch(() => {
					this.$store.state.Picture= ""
					this.$message({
						type: 'error',
						message: '您尚未上传过图片，请上传',
						
					});
		})
			console.log('111',this.$router.options.routes)
			this.data1=this.$router.options.routes
		},
		methods:{
			uploadimg(){
					this.imgupload = true;
				},
				handleChange(file, fileList) { //文件数量改变
					this.fileList = fileList;
				},
				confirmUpload() { //确认上传
					let param = new FormData();
					this.fileList.forEach(
						(val, index) => {
							param.append("files", val.raw);
						}
					);
					param.append("userCode",localStorage.getItem("userName"));
					http({
						method: 'post',
						url: "/img/upload",
						data: param,
						responseType:"blob",
						}).then((res) => {	
							this.$message({
								type: 'success',
								message: '上传成功!'
							});
							let blob = new Blob([res.data],{type:'image/jpeg'})
                			this.currentPicture = URL.createObjectURL(blob)
							this.$store.state.Picture= this.currentPicture
						}).catch(() => {
							this.$message({
								type: 'error',
								message: '服务器错误'
							});
				})
			},
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			handleMenu(){
				this.isCollapse=!this.isCollapse
			},
			shutDown(){
				http({
					method:'get',
					url:'/toLoginOut'
				}).then((res)=>{
					console.log('side',res)
					if(res.data.code === 200){
						console.log(res)
						this.$router.push("/")
						window.localStorage.removeItem("token")
					}else{
						alert("失败")
					}
				}).catch(()=>{
					console.log("注销失败")
				})
			}
		},
		created() {
			this.userName = localStorage.getItem("userName")
			console.log(this.userName)
		}
    }
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    /* width: 180px; */
    min-height: 400px;
  }
  header
{
    display: flex;
    height: 100%;
    justify-content: space-between;
    align-items:center;
}
    .name{
		display: flex;
	}
	
     .title{
		font-family:Georgia, 'Times New Roman', Times, serif;
		margin-left: 12px;
		color:white
	}
    .el-header,
	.el-footer {
		background-color: #4e4c4c;
		color: #333;
		text-align: center;
		line-height: 60px;
	}

	.el-aside {
		height: 792px;
		background-color: #545c64;
		color: #333;
		text-align: center;
		/* line-height: 200px; */
		overflow: hidden;
	}

	

	body>.el-container {
		margin-bottom: 40px;
	}

	.el-container:nth-child(4) .el-aside {
		line-height: 320px;
	}
</style>