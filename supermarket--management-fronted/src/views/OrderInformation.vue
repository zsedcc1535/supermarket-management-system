
<template>
	<div>
		<el-card class="box-card">
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<!--        第一个页面商品基础信息-->
			<el-tab-pane label="订单信息" name="first" >
				<!--            商品信息table-->
				<el-table ref="multipleTable" 
				:data="tableData" 
				tooltip-effect="dark" 
				style="width: 100%" 
				@selection-change="handleSelectionChange">
					<!-- <el-table-column type="selection" width="55">
					</el-table-column> -->
					<el-table-column prop="id" label="订单号" >
					</el-table-column>
                    <el-table-column prop="username" label="用户名称" >
					</el-table-column>
					<el-table-column prop="goodsName" label="商品名称" >
					</el-table-column>
					
					<el-table-column prop="goodsAmount" label="购买数量" >
					</el-table-column>
					<el-table-column prop="myData" label="购买时间" >
					</el-table-column>


					<el-table-column prop="goodsEdit"  width="200">
                        <template slot="header">
                            <div style="display:flex">
                                <input v-model="searchMes" placeholder="请输入订单号"  @input="forceUpdate($event)" style="width:140px;border-radius: 5px;">
									
							
                            	<el-button icon="el-icon-search" circle @click="searchId"></el-button>
                            </div>
                        </template>
						<template slot-scope="scope">
							<!-- dialogFormVisible = true -->
							<el-button type="primary" icon="el-icon-edit" circle @click="update_search(scope)" style="margin-left:40px"></el-button>
                            <el-button type="danger" icon="el-icon-delete" circle @click="delete1(scope)" style="margin-left:40px"></el-button>
						</template>
                        
						<!--                    <el-button type="text" @click="dialogFormVisible = true">打开嵌套表单的 Dialog</el-button>-->
					</el-table-column>

					
					<!--                分页-->
				</el-table>
				<div class="block">
					<el-pagination 
					@size-change="handleSizeChange" 
					@current-change="handleCurrentChange" 
					:current-page.sync="currentPage"
					:page-size="size" 
					layout="total, prev, pager, next" 
					:total="total"
					style="float: right;margin-top: 2%;">
					</el-pagination>
				</div>

				<el-form>
					
					<el-dialog title="编辑订单" :visible.sync="dialogFormVisible" :before-close="clean">
						<el-form :model="form">
                            <el-form-item label="订单号" :label-width="formLabelWidth">
								<el-input v-model="form.id" autocomplete="off" readonly></el-input>
							</el-form-item>
                            <el-form-item label="用户名称" :label-width="formLabelWidth">
								<el-input v-model="form.username" autocomplete="off"></el-input>
							</el-form-item>
							<el-form-item label="商品名称" :label-width="formLabelWidth">
								<el-input v-model="form.goodsName" autocomplete="off"></el-input>
							</el-form-item>
						
							
							<el-form-item label="商品数量" :label-width="formLabelWidth">
								<el-input-number v-model="form.goodsAmount" autocomplete="off" style="width:100%" ></el-input-number>
						                    
							</el-form-item>
							<el-form-item label="购买日期" :label-width="formLabelWidth"> 
                                <el-date-picker
                                type="datetime"
                               style="width:100%;"
                                v-model="form.time"
                                placeholder="选择时间">
                                </el-date-picker>
                            </el-form-item>
						</el-form>
						<div slot="footer" class="dialog-footer" >
							<!-- ialogFormVisible = false -->
							<el-button @click="clean()">取 消</el-button>
							<el-button type="primary" @click="update()">确 定</el-button>
						</div>
					</el-dialog>
				</el-form>
			</el-tab-pane>

			<!--        第二个页面添加商品-->
			<el-tab-pane label="添加订单" name="second">
				<div id="add" style="vertical-align: middle">
					<span> 商品名称：</span>
					<el-input v-model="form.goodsName" placeholder="请输入商品名称" style="width:50%;margin-left: 50px;"></el-input>
					<br>
                    <span> 用户名：</span>
					
					<el-input v-model="form.username" placeholder="请输入用户名" style="width:50%;margin-left: 66px;"></el-input>
					<br>
					<span> 订单号：</span>
					<el-input v-model="form.id" placeholder="请订单号" style="width:50%;margin-left: 66px;"></el-input>
					<br>
					<span> 商品数量：</span>
					<el-input v-model="form.goodsAmount" placeholder="请输入商品数量" style="width:50%;margin-left: 50px;"></el-input>
					<br>
					
					<span> 订单日期：</span>
					<el-date-picker
					      v-model="form.time"
					      type="datetime"
					      placeholder="请输入订单日期"
						   style="width:50%;margin-left: 50px;">
					    </el-date-picker>
					<!-- <el-input v-model="form.productTime" placeholder="请输入生产日期" style="width:50%;margin-left: 50px;"></el-input> -->
					<br>
					

					<el-button type="primary" plain style="margin-left:260px;margin-top: 50px;" @click="add">添加商品
					</el-button>

				</div>
			</el-tab-pane>

		</el-tabs>
		</el-card>
	</div>
</template>
<script>
	import http from "../util/http";


	export default {
		data() {
			return {
				
				activeName: 'first',
				types:'',
				index:'',
                searchMes:'',
				tableData: [
				],
				currentPage: 1,
				multipleSelection: [],
				dialogFormVisible: false,
				form: {
                    goodsAmount:'',
                    goodsName:'',
                    time:'',
                    username:'',
                    id:''
				},
				formLabelWidth: '120px',
				dialogVisible: false,
				total:0,
				size:1,
				value: '',
				visible: false,
			};
		},
		created() {
			http({
				method: 'post',
				url: 'order',
				params: {
					pageNo: 1
				}
			}).then((res) => {
				this.total = res.data.data.pages;
				// this.size = res.data.data.list.length
				console.log(res.data.data)
				// console.log(res);
				if (res.status === 200) {
					this.tableData = res.data.data.list;
				}
			}).catch(() => {
				alert("err")
			})
		},
		methods: {
			forceUpdate(e){
					this.$forceUpdate(); // @change="forceUpdate($event)"主要是解决clear图标响应问题
				},
			update(index){
				this.dialogFormVisible=false,
				http({
					method:'get',
					url:'/order/update',
					params:{
						id: this.form.id,
						goodsName: this.form.goodsName,
						goodsAmount: this.form.goodsAmount,
						time: this.form.myData,
						username: this.form.username
					}
				}).then((res)=>{
					this.form={
                    goodsAmount:'',
                    goodsName:'',
                    time:'',
                    username:'',
                    id:'',
					
                }
					if(res.data.code===200)
					{this.$message({
						type: 'success',
						message: '更新成功!'
					});
				}else{
					this.$message({
						type: 'warning',
						message:res.data.msg
					});
				}
					http({
						method: 'post',
						url: 'order',
						params: {
							pageNo: 1
						}
					}).then((res) => {
						// console.log(res);
						if (res.status === 200) {
							this.tableData = res.data.data.list;
						}
						
					}).catch(() => {
						alert("err")
					})
				}).catch(()=>{
                    this.form={
                    goodsAmount:'',
                    goodsName:'',
                    time:'',
                    username:'',
                    id:''
                }
					this.$message({
						type: 'info',
						message: '更新失败'
					});
				})
			
			},
			update_search(scope){
				this.dialogFormVisible = true 
				console.log(scope.row)
                this.form=scope.row
				
				
			},
			delete1(scope) {
				this.dialogVisible = false;
				http({
					method:"get",
					url:'/order/delete',
					params: {
						id:this.tableData[scope.$index].id
					}
				}).then((res)=>{
					if(res.data.code!==200) {
								this.$message({
									type: 'warning',
									message: res.data.msg
								});
					}
					 else this.tableData.splice(scope.$index-1,1)
					console.log(this.tableData[scope.$index].id)
					http({
						method: 'post',
						url: 'order',
						params: {
							pageNo: 1
						}
					}).then((res) => {
						console.log(res);
						if (res.data.code === 200) {
							this.tableData = res.data.data.list;
						}
						
					}).catch(() => {
						alert("err")
					})
				}).catch(()=>{
					console.log("error")
					console.log(this.tableData[scope.$index].id)
				})
			},
			handleClick(tab, event) {
				console.log(tab, event);
			},
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.multipleTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.multipleTable.clearSelection();
				}
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				console.log(val);
				http({
					method: 'post',
					url: '/order',
					params: {
						pageNo: val
					}
				}).then((res) => {
					if (res.data.code === 200) {
						this.tableData = res.data.data.list;
					}
					else {
						this.$message({
							type: 'warning',
							message: res.data.msg
						});
					}
				})
			},
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
					})
					.catch(_ => {});
			},
			add() {
				http({
					method:"get",
					url:'/order/add',
					params:{
						goodsName:this.form.goodsName,
						time: this.form.time,
						username: this.form.username,
						goodsAmount: this.form.goodsAmount,
                        id:this.form.id
					}
				}).then((res)=>{
					console.log(res)
					if(res.data.code==200) {
						this.$message({
							type: 'success',
							message: '添加成功'
						});}else{

							this.$message({
							type: 'warning',
							message: res.data.msg
						});
						}
                    http({
						method: 'post',
						url: 'order',
						params: {
							pageNo: 1
						}
					}).then((res) => {
						// console.log(res);
						if (res.data.code === 200) {
							this.tableData = res.data.data.list;
						}
						
					}).catch(() => {
						alert("err")
					})
						/* console.log(res.data.data.msg)
						console.log(res.data.data.code) */
				}).catch(()=>{
					this.$message({
						type: 'info',
						message: '添加出错'
					});
				})
			},
            searchId(){
				
				http({
					method: 'get',
					url: '/order/like',
					params: {
						id: this.searchMes
					}
				}).then((res) => {
					console.log('serach',res.data)
					if (res.data.code === 200) {
						this.total=res.data.data.pages
						this.tableData = res.data.data.orderList;
					}
					else {
						this.$message({
							type: 'warning',
							message: res.data.msg
						});
					}
				})
            },
			clean(){
                this.dialogFormVisible=false
				this.form={
                    goodsAmount:'',
                    goodsName:'',
                    time:'',
                    username:'',
                    id:''
                }
			}
			
		}

	};
	/* this.$confirm('是否添加此商品？', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning'
	}).then(() => {
		this.$message({
			type: 'success',
			message: '添加成功!'
		});
	}).catch(() => {
		this.$message({
			type: 'info',
			message: '已取消添加'
		});
	}); */
	/* http({
		
	}) */

</script>
