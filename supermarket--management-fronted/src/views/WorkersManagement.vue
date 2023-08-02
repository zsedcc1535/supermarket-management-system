<template>
	<div id="Root">
		<el-card class="box-card">
			<div class="rootTop">
				<div class="baseOperate">
					<el-button type="primary" @click="addForm.addFrame=true" round class="el-icon-plus">增 加</el-button>
					<el-button type="warning" :disabled="isChecked" round @click="deleteMulti">批量删除</el-button>
					<!-- 增加弹框 -->
					<div id="addFrame">
						<el-dialog title="增加员工" :visible.sync="addForm.addFrame" width="50%">

							<el-form ref="form" label-width="80px" :model="addForm.worker">
								
								<el-form-item label="员工账号">
									<el-input v-model="addForm.worker.usercode" placeholder="请输入账号" clearable maxlength="12"></el-input>
								</el-form-item>

								<el-form-item label="员工名">
									<el-input v-model="addForm.worker.name" placeholder="请输入员工名" clearable maxlength="6"></el-input>
								</el-form-item>
								
								<el-form-item label="密码">
									<el-input v-model="addForm.worker.pwd" show-password placeholder="请输入密码" clearable maxlength="12"></el-input>
								</el-form-item>

								<el-form-item label="薪水">
									<el-input v-model="addForm.worker.salary" placeholder="请输入薪水(不输入即为默认薪水)" clearable maxlength="20"></el-input>
								</el-form-item>
								

								<el-form-item label="邮箱">
									<el-input v-model="addForm.worker.email" placeholder="请输入邮箱" clearable maxlength="20"></el-input>
								</el-form-item>
								
								
								<el-form-item label="设置角色">
									<el-radio-group v-model="addForm.worker.role">
										<el-radio v-for="(role) in roles" :label="role.id" :key="role.id"
										style="padding: 0.3125rem;">{{role.rolename}}</el-radio>
									</el-radio-group>
									
								</el-form-item>
							</el-form>

							<span slot="footer" class="dialog-footer">
								<el-button @click="cancelAdd">取 消</el-button>
								<el-button type="primary" @click="submitAdd()">提 交</el-button>
							</span>
						</el-dialog>
					</div>
					<!-- end增加角色弹框  -->
				</div>
				<div class="search">
					<!-- <el-input placeholder="请输入用户名" prefix-icon="el-icon-search" v-model="searchInput" clearable>
					</el-input> -->
					 <el-input placeholder="请输入内容" v-model="searchF.searchInput" clearabl :maxlength="searchF.maxlength" class="input-with-select">
					    <el-select v-model="searchF.select" slot="prepend" placeholder="请选择条件" @change="selectChange">
					      <el-option label="用户名" value="1"></el-option>
					      <el-option label="角色名" value="2"></el-option>
					    </el-select>
					    <el-button slot="append" icon="el-icon-search" @click="search" type="primary"></el-button>
					</el-input>
					<!-- <el-button type="primary" @click="search">查 询</el-button> -->
				</div>
			</div>
			<div class="rootContent">
				<el-table :data="workersData" border @selection-change="handleSelectionChange" align="center" style="width: 100%;" ref="multipleTable">
					<el-table-column type="selection" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="id" label="编号">
					</el-table-column>
					<el-table-column prop="userCode" label="用户账号">
					</el-table-column>
					<el-table-column prop="userName" label="用户名">
					</el-table-column>

					<el-table-column prop="email" label="邮箱">
					</el-table-column>
					<el-table-column prop="role.rolename" label="角色">
					</el-table-column>
					<el-table-column prop="salary" label="薪水">
					</el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-tooltip effect="dark" content="发邮件" placement="top-start">
							<el-button @click="emailModify(scope.row)" circle type="primary" class="el-icon-s-comment" size="mini"></el-button>
							</el-tooltip>
							 <el-tooltip effect="dark" content="编辑" placement="top-start">
							<el-button @click="startModify(scope)" circle type="primary" class="el-icon-edit" size="mini"></el-button>
							</el-tooltip>
							<el-tooltip effect="dark" content="解雇" placement="top-start">
							<el-button @click="deleteModify(scope.row.id)" type="danger" circle class="el-icon-delete" size="mini"></el-button>
							</el-tooltip>

						</template>
					</el-table-column>
				</el-table>
			</div>
			<!-- 解雇用户弹框 -->
			<div id="modify">
				<el-dialog title="解雇员工" :visible.sync="delemployee.modify" width="30%">
			
					<el-form ref="form" label-width="80px" >
						
						<el-form-item label="解雇理由" >
							<el-input v-model="delemployee.reason" type="textarea" placeholder="请输入解雇理由"  minlength="15" maxlength="100" show-word-limit></el-input>
						</el-form-item>
						
					</el-form>
			
					<span slot="footer" class="dialog-footer">
						<el-button @click="delcancelModify">取 消</el-button>
						<el-button type="primary" @click="deleteOneWorker">发 送</el-button>
					</span>
				</el-dialog>
			</div>
			<div id="modify">
				<el-dialog title="编辑员工" :visible.sync="modifyWorker.modify" width="50%">
			
					<el-form ref="form" label-width="80px">
						
						<el-form-item label="员工账号">
							<el-input v-model="modifyWorker.usercode" placeholder="请输入账号" clearable maxlength="12" readonly></el-input>
						</el-form-item>

						<el-form-item label="员工姓名">
							<el-input v-model="modifyWorker.name" placeholder="请输入员工名" clearable maxlength="6"></el-input>
						</el-form-item>
						
						<el-form-item label="邮箱">
							<el-input v-model="modifyWorker.email" placeholder="请输入邮箱" clearable maxlength="20"></el-input>
						</el-form-item>

						<el-form-item label="密码">
							<el-input v-model="modifyWorker.pwd" show-password placeholder="请输入密码" clearable maxlength="12"></el-input>
						</el-form-item>

						<el-form-item label="薪水">
							<el-input v-model="modifyWorker.salary" placeholder="请输入薪水" clearable maxlength="6"></el-input>
						</el-form-item>

						<el-form-item label="角色">
							<el-radio-group v-model="modifyWorker.modifyCurrentRole">
								<el-radio v-for="(role) in roles" :label="role.id" :key="role.id"
								style="padding: 0.3125rem;">{{role.rolename}}</el-radio>
							</el-radio-group>
						</el-form-item>
					</el-form>
			
					<span slot="footer" class="dialog-footer">
						<el-button @click="cancelModify">取 消</el-button>
						<el-button type="primary" @click="submitModify">提 交</el-button>
					</span>
				</el-dialog>
			</div>
			<!-- end修改弹框 -->
			<div class="rootBottom">
				
				<el-pagination background layout="total, prev, pager, next,jumper" :current-page="currentPage" :total="total" :page-size="pageSize"
				  @size-change="handleSizeChange" @current-change="handleCurrentChange">
				
				</el-pagination>
			</div>
		</el-card>
	</div>
</template>

<script>
	// 引入http
	import http from '../util/http.js'
	// 导入验证器validator
	import validator from '../util/validator.js'
	// 导入接口变量
	import {
		initWorkerManagement,
		getAllRole,
		addWorker,
		search,
		modify,
		multidel,
		deleteOneWorker
	} from '../util/api.js'
	export default {
		name: "WorkersManagement",
		data: function() {
			return {
				searchF:{
					// 搜索输入
					searchInput:"",
					// 选择搜索类型
					select:"",
					maxlength:6,
					flag:false
				}
				,
				// 增加员工所需数据
				addForm: {
					// 添加的员工
					worker: {
						salary:"",
						usercode:"",
						name: "",
						email:"",
						pwd: "",
						role: ""
					},
					
					// 添加弹框的变量
					addFrame: false,
				},
				// 多选的状态
				delemployee:{
					id:'',
					reason:'',
					modify:false
				},
				//多选的结果数组
				hasChecked: [],
				// 多选是否有数据被选中的标志
				isChecked: true,
				
				// 总条数
				total: 10,
				// 当前页码
				currentPage: 1,
				//页面大小
				pageSize: 10,
				// 角色信息
				roles: [],
				// 员工总信息
				workersData: [],
				// 修改的对象
				modifyWorker:{
					// 当前修改的员工id
					currentModifyWorker: '',
					// 修改的目标角色的index
					modifyRoleIndex: '',
					// 修改角色的弹窗变量
					modify: false,
					name: "",
					pwd: "",
					salary:"",
					usercode:"",
					email:"",
					// 修改的目标角色
					modifyCurrentRole: ""
				},
				// rules: {
				// 	reason: [
				// 		{ required: true, message: '请输入解雇理由', trigger: 'blur' },
				// 		{ min: 15, message: '解雇理由最少15字', trigger: 'change'  }
				//       ],
				// 	}

			}
		},
		methods: {
			// ///////////////////////////////////////////////
			//根据页码请求数据
			
			getDataByPage(page) {
				this.searchF.flag=false;
				
				http({
					method: 'get',
					url: initWorkerManagement,
					params:{
						pageNo:page
					}
				}).then((res) => {
					console.log(res);
					this.total = res.data.data.total;
					this.workersData = res.data.data.userList;
					this.currentPage = page;
					
				}).catch(() => {
					this.$message({
						type: 'error',
						message: '服务器错误'
					});
				})
			},
			// 初始化页面函数
			initPageWorkers() {
				this.searchF.flag=false;
				http({
					method: 'get',
					url: initWorkerManagement
				}).then((res) => {
					
					this.total = res.data.data.total;

					this.workersData = res.data.data.userList;
				}).catch(() => {

					this.$message({
						type: 'error',
						message: '服务器错误！'
					});
				})
			},
			///////////////////////////////////////////////////
			// 处理多选框被选中的事件
			handleSelectionChange(val) {
				this.isChecked = val.length === 0 ? true : false;
				let i = 0;
				val.forEach(ob => {
					this.hasChecked[i++] = ob.id
				})
			},
			emailModify(val) {
				// 根据员工的id发邮件
				let delWorkerId = val.id;
				this.$prompt('发送内容', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
                    inputErrorMessage: '输入不能为空',
					inputValidator: (value) => {       // 点击按钮时，对文本框里面的值进行验证
                        if(!value) {
                            return '输入不能为空';
                        }
					}
				}).then((value) => {
					http({
						method: 'post',
						url: "/email",
						params: {
							value: value.value,
							userId: delWorkerId,
						}
					}).then((res) => {
						console.log(res);
						if (res.data.code == 200) {
							this.$message({
								type: 'success',
								message: '发送成功!'
							});
						}else{
							this.$message({
								type: 'error',
								message: res.data.msg
							});
						}
					}).catch(() => {
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})
				})
			},
			// 批量删除
			deleteMulti() {
				this.$confirm('此操作将永久删除该' + this.hasChecked.length + '条数据, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					console.log(this.hasChecked);
					http({
						method: 'post',
						url: multidel,
						data: {
							userIdlist: this.hasChecked,
							pageNo: this.currentPage
						}
					}).then((res) => {
						console.log(res);
						if (res.data.code == 200) {
							// 请求当前页数据和最大页码
							this.workersData = res.data.data.userList;
							this.total = res.data.data.total;
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
						} else {
							this.$message({
								type: 'error',
								message: res.data.msg
							});
						}
					}).catch(() => {
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})

					this.$refs.multipleTable.clearSelection();
				}).catch(() => {
					this.$refs.multipleTable.clearSelection();
					this.hasChecked = [];

				})
			},

			//////////////////////////////////////////////////////
			// 添加
			// watch: {
			// 	searchInput: function(val) {
			// 		console.log(this.searchInput);
			// 	},
			// 	// 监听弹窗变化使添加变为初始状态
			// 	'addForm.addFrame': function(newV, oldV) {
			// 		console.log(oldV);
			// 		if (!oldV) {
			// 			this.clearInfo()
			// 		}
			// 	}
			// },

			// 回到添加前的状态
			clearInfo() {
				this.addForm.addFrame = false;
				this.addForm.worker = {
					name: "",
					pwd: "",
					role: ""
				};
				// this.addForm.tip = {
				// 	pIsHidden: false,
				// 	nIsHidden: false,
				// 	name: "",
				// 	pwd: ""
				// }
			},

			// 添加员工
			submitAdd() {
				let flagN = validator.vCharLength(this.addForm.worker.name, 1, 6);
				let flagP = validator.vCharLength(this.addForm.worker.pwd, 1, 12);
				let flagR = typeof(this.addForm.worker.role) === "number" ? true : false;
				var verify = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
				let flagM = verify.test(this.addForm.worker.email);
				// 验证提交信息
				if (!(flagN && flagP)) {
					this.$message({
						type: 'error',
						message: '用户名或密码格式有误！'
					});
					return;
				} else if (!flagR) {
					this.$message({
						type: 'error',
						message: '请选择角色！'
					});
					return;
				} else if(!flagM){
					this.$message({
						type: 'error',
						message: '邮箱格式错误！'
					});
					return;
				}else {
					// 验证通过

					http({
						method: 'post',
						url: addWorker,
						data: {
							salary: this.addForm.worker.salary,
							email: this.addForm.worker.email,
							userCode: this.addForm.worker.usercode,
							password: this.addForm.worker.pwd,
							userName: this.addForm.worker.name,
							roleId: this.addForm.worker.role
						}
					}).then((res) => {
						if (res.data.code == 200) {
							this.getDataByPage(11111);
							this.$message({
								type: 'success',
								message: '添加成功'
							});

						} else {
							this.$message({
								type: 'error',
								message: '添加失败'
							});
						}
					}).catch(() => {
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})
					this.clearInfo();
				}
			},
			// 取消添加
			cancelAdd() {
				this.clearInfo();
			},


			///////////////////////////////////////////////////////
			selectChange(){
				if(this.searchF.select==1){
					this.searchF.maxlength=6
				}else{
					this.searchF.maxlength=4
				}
			},
			// 查询
			search() {
				// 验证输入
				if(this.searchF.select==""){
					this.$message({
						type:'info',
						message:'请选择查询类型'
					})
				}else if (this.searchF.searchInput.length == 0) {
					this.searchF.flag=false;
					this.getDataByPage(this.currentPage);
				}else if(this.searchF.select==1){
					// 根据用户名查询
					this.searchF.flag=true;
				
					http({
						method: 'post',
						url: search,
						data: JSON.stringify({
							userName: this.searchF.searchInput,
						})
					}).then((res) => {
						if (res.data.code == 200) {
							console.log('查询成功');
							this.workersData = res.data.data.userList;
							this.total = res.data.data.total;
							this.currentPage = 1;
						} else {
							this.$message({
								type: 'error',
								message: res.data.msg
							});
						}
					}).catch(() => {
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})

				}else{
					this.searchF.flag=true;
					// 根据角色名
					let flag=false;
					let roleId=0;
					this.roles.forEach((role)=>{
						if(role.rolename===this.searchF.searchInput){
							flag=true;
							roleId=role.id;
						} 
					})
					console.log(flag,roleId);
					if(flag){
						http({
							method: 'post',
							url: search,
							data: JSON.stringify({
								roleId:roleId
							})
						}).then((res) => {
							if (res.data.code == 200) {
								console.log('查询成功');
								this.workersData = res.data.data.userList;
								this.total = res.data.data.total;
								this.currentPage = 1;
							} else {
								this.$message({
									type: 'error',
									message: '查询失败'
								});
							}
						}).catch(() => {
							this.$message({
								type: 'error',
								message: '服务器错误'
							});
						})
					}else{
						this.$message({
							type:'info',
							message:'请输入准确的角色'
						})
					}
				}
			},
			/////////////////////////////////////////////
			//修改
			deleteModify(val){
				this.delemployee.id=val;
				this.delemployee.modify=true
			},
			// 启动修改
			startModify(val) {
				console.log(this.workersData);
				this.modifyWorker={
					currentModifyWorker:val.row.id,
					modify:true,
					usercode:this.workersData[val.$index].userCode,
					email:this.workersData[val.$index].email,
					salary:this.workersData[val.$index].salary,
					name:this.workersData[val.$index].userName,
					pwd:this.workersData[val.$index].password,
					modifyCurrentRole:this.workersData[val.$index].roleId
				}
			},
		
			// 修改前的状态
			delcancelModify(){
				this.delemployee={
					id:'',
					reason:'',
					modify:false
				}
			},
			cancelModify() {
				this.modifyWorker={
					modifyRoleIndex: '',
					// 修改角色的弹窗变量
					modify: false,
					name: "",
					pwd: "",
					salary:"",
					usercode:"",
					email:"",
					// 修改的目标角色
					modifyCurrentRole: ""
				}
			},
			
			// 提交修改
			submitModify() {
				console.log(this.modifyWorker);
				var verify = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
				let flagM = verify.test(this.modifyWorker.email);
				if(!flagM){
					this.$message({
							type: 'error',
							message: '邮箱格式错误'
						})
					return;
				}
				http({
					method: 'post',
					url: modify,
					data: JSON.stringify({
						userCode: this.modifyWorker.usercode,
						email: this.modifyWorker.email,
						salary: this.modifyWorker.salary,
						password: this.modifyWorker.pwd,
						userName:this.modifyWorker.name,
						id: this.modifyWorker.currentModifyWorker,
						roleId: this.modifyWorker.modifyCurrentRole
					})
				}).then((res) => {
					console.log(res);
					if (res.data.code == 200) {
						// 请求成功改为修改后的数据
						this.getDataByPage(this.currentPage);
						this.$message({
							type: 'success',
							message: '修改成功'
						})
					} else {
						this.$message({
							type: 'error',
							message: res.data.msg
						});
					}
				}).catch(() => {
					this.$message({
						type: 'error',
						message: '服务器错误'
					});
				})
				this.cancelModify();
			},


			//////////////////////////////////////////////////
			// 分页
			// 页面大小变化的事件
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			// 当前页变化事件
			handleCurrentChange(val) {
				console.log(this.searchF.flag);
				
				let data;
				if(this.searchF.select==1){
					data={
						pageNo:val,
						userName:this.searchF.searchInput
					}
				}else{
					let roleId=0;
					this.roles.forEach((role)=>{
						if(role.rolename===this.searchF.searchInput){
							
							roleId=role.id;
						} 
					})
					data={
						pageNo:val,
						roleId:roleId
					}
				}
				if(this.searchF.flag){
					console.log(data);
					// 搜索结果的分页
					http({
						method:'post',
						url:search,
						data:JSON.stringify(data)
					}).then((res)=>{
						this.workersData = res.data.data.userList;
					}).catch(()=>{
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})
				}else{
					this.getDataByPage(val); //获取当前页数据的函数
				}
			},


			////////////////////////////////////////////////////
			// 单条数据删除
			deleteOneWorker() {
				// 删除的员工的id
				let delWorkerId = this.delemployee.id;
				let delreason = this.delemployee.reason;
				this.$confirm('此操作将永久删除该员工, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					console.log(delWorkerId);

					http({
						method: 'post',
						url: deleteOneWorker,
						params: {
							userId: delWorkerId,
							pageNo: this.currentPage,
							value: delreason
						}
					}).then((res) => {
						console.log(res);
						if (res.data.code == 200) {
							this.workersData = res.data.data.userList;
							this.total = res.data.data.total;
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
						}
						else {
							this.$message({
								type: 'warning',
								message: res.data.msg
							});
						}
					}).catch(() => {
						this.$message({
							type: 'error',
							message: '服务器错误'
						});
					})

				})
				this.delcancelModify()
			}
		},


		////////////////////////////////////////////////////
		// 页面初始化请求数据

		mounted() {
			http({
				method: 'get',
				url: initWorkerManagement
			}).then((res) => {
				console.log(res);
				this.total = res.data.data.total;

				this.workersData = res.data.data.userList;
			}).catch(() => {

				this.$message({
					type: 'error',
					message: '服务器错误！'
				});
			})
			http({
				method: 'get',
				url: '/role/showAllRole',
			}).then((res) => {
				console.log(res);
				if (res.data.code == 200) {
					this.roles = res.data.data;
				}
			}).catch(() => {
				this.$message({
					type: 'error',
					message: '服务器错误！'
				});
			})
		},
		computed: {

		}
	}
</script>

<style>
	/* 上面 */
	#Root .rootTop {
		display: flex;
	}

	#Root .rootTop .baseOperate {
		flex: 6;
	}

	#Root .rootTop .search {
		flex: 4;
		display: flex;
	}
	
	#Root .rootTop .search el-input {
		flex: 2;
	}

	/* #Root .rootTop .search .el-input__inner {
		border-radius: 4px 0 0 4px;
	} */

	/* #Root .rootTop .search .el-button {
		flex: 1;
		border-radius: 0 4px 4px 0;
		border-left: none;
	} */
	.el-select .el-input {
	    width: 130px;
	  }
	  .input-with-select .el-input-group__prepend {
	    background-color: #fff;
	  }
	/* 内容 */
	#Root .rootContent {
		margin-top: 1.875rem;
	}

	#Root .rootContent .el-table td,
	#Root .rootContent .el-table th {
		text-align: center;
	}

	/* 底部分页 */
	#Root .rootBottom {
		text-align:right;
		line-height: 2rem;
		margin-top: 3.125rem;
		display: flex;
		justify-content: flex-end;
	}
	#Root .rootBottom span{
		font-size: 0.875rem;
		color: #aaa;
	}
</style>
